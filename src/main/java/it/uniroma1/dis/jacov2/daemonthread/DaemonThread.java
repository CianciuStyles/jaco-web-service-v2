package it.uniroma1.dis.jacov2.daemonthread;

import it.uniroma1.dis.jacov2.composition.Composition;
import it.uniroma1.dis.jacov2.dao.JobsQueue;
import it.uniroma1.dis.jacov2.model.json.JsonComposition;
import it.uniroma1.dis.jacov2.model.xml.XmlBehaviorState;
import it.uniroma1.dis.jacov2.model.xml.XmlComposition;
import it.uniroma1.dis.jacov2.model.xml.XmlCompositionState;
import it.uniroma1.dis.jacov2.model.xml.XmlEnvironmentState;
import it.uniroma1.dis.jacov2.model.xml.XmlTargetState;
import it.uniroma1.dis.jacov2.model.xml.XmlTransition;
import it.uniroma1.dis.jacov2.pddl2smv.pddl2smv;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;

public class DaemonThread extends Thread {

	BlockingQueue<String> jobsQueue = JobsQueue.instance.getModel();
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	public void run() {
		while (true) {
			try {
				if (jobsQueue.isEmpty()) {
					Thread.sleep(500);
				} else {
					String nextJobId = jobsQueue.peek();
					
					prepareFilesForComposition(nextJobId);
					askForComposition(nextJobId);
					convertCompositionToXML(nextJobId);
					
					jobsQueue.remove();
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (IOException e) {
				//Thread.currentThread().interrupt();
				
				// Cancel the current job and carry on with the next one
				jobsQueue.remove();
			}
		}
	}
	
	private Response prepareFilesForComposition(String jobId) {
		String clientRootPath = rootPath + File.separator + jobId;
		List<String> pddl2smvArgs = new ArrayList<String>();
		
		// Check environment files
		List<String> environmentArgs = new ArrayList<String>(2);
		File environmentDirectory = new File(clientRootPath + File.separator + "Environment");
		File[] environmentFiles = environmentDirectory.listFiles();
		if (environmentFiles == null || environmentFiles.length != 2) {
			return Response.serverError().build();
		} else {
			for (File environmentFile : environmentFiles) {
				String fileName = environmentFile.getAbsolutePath();
				if (fileName.endsWith("_domain.txt")) {
					environmentArgs.add(0, fileName);
				} else if (fileName.endsWith("_problem.txt")) {
					environmentArgs.add(1, fileName);
				}
			}
		}
		pddl2smvArgs.addAll(environmentArgs);
		
		// Check target files
		List<String> targetArgs = new ArrayList<String>(2);
		File targetDirectory = new File(clientRootPath + File.separator + "Target");
		File[] targetFiles = targetDirectory.listFiles();
		if (targetFiles == null || targetFiles.length != 2) {
			return Response.serverError().build();
		} else {
			for (File targetFile : targetFiles) {
				String fileName = targetFile.getAbsolutePath();
				if (fileName.endsWith("_domain.txt")) {
					targetArgs.add(0, fileName);
				} else if (fileName.endsWith("_problem.txt")) {
					targetArgs.add(1, fileName);
				}
			}
		}
		pddl2smvArgs.addAll(targetArgs);
		
		List<String> behaviorsArgs = new ArrayList<String>();
		File behaviorsDirectory = new File(clientRootPath + File.separator + "Behaviors");
		File[] behaviorsDirectories = behaviorsDirectory.listFiles();
		if (behaviorsDirectories == null || behaviorsDirectories.length == 0) {
			return Response.serverError().build();
		} else {
			for (File behaviorDirectory : behaviorsDirectories) {
				File[] behaviorFiles = behaviorDirectory.listFiles();
				
				if (behaviorFiles == null || behaviorFiles.length != 2) {
					return Response.serverError().build();
				} else {
					List<String> behaviorArgs = new ArrayList<String>(2);
					
					for (File behaviorFile : behaviorFiles) {
						String fileName = behaviorFile.getAbsolutePath();
						if (fileName.endsWith("_domain.txt")) {
							behaviorArgs.add(0, fileName);
						} else if (fileName.endsWith("_problem.txt")) {
							behaviorArgs.add(1, fileName);
						}	
					}
					
					behaviorsArgs.addAll(behaviorArgs);
				}
			}
		}
		pddl2smvArgs.addAll(behaviorsArgs);
		
		File compositionDirectory = new File(clientRootPath + File.separator + "Composition");
		compositionDirectory.mkdirs();
		
		try {
			String[] pddl2svmArgsArray = new String[pddl2smvArgs.size()];
			pddl2smvArgs.toArray(pddl2svmArgsArray);
			pddl2smv.main(compositionDirectory + File.separator + "Composition.smv", pddl2svmArgsArray);
			
			return Response.ok().build();
		} catch (Exception e) {
			System.out.println("In Exception branch..." + e.getMessage());
			
			return Response.serverError().build();
		}
	}
	
	private void askForComposition(String jobId) {
		Composition composition = new Composition(rootPath + File.separator + jobId + File.separator + "Composition");
		composition.doComposition();
	}
	
	private void convertCompositionToXML(String jobId) throws IOException {
		String compositionPath = rootPath + File.separator + jobId + File.separator + "Composition";
		String compositionSmvFilePath = compositionPath + File.separator + "Composition.smv";
		String compositionTxtFilePath = compositionPath + File.separator + "Composition.txt";
		String compositionXmlFilePath = compositionPath + File.separator + "Composition.xml";
		String compositionJsonFilePath = compositionPath + File.separator + "Composition.json";

		// Read the SMV file to retrieve services names and number of variables
		ArrayList[] res = ParseSmvFile(compositionSmvFilePath);
		
		// Check whether the requested composition is realizable or not
		XmlComposition computedComposition = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(compositionTxtFilePath))));
			computedComposition = new XmlComposition(br.readLine().equals("realizzabile"));
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read the Composition TXT file to build the XML structure
		computedComposition = ParseTxtFile(compositionTxtFilePath, res, computedComposition);
		
		// Write Composition to XML file
		try {
			WriteCompositionToXmlAndJson(computedComposition, compositionPath, compositionXmlFilePath, compositionJsonFilePath);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList[] ParseSmvFile(String smvFilePath) throws IOException {
		BufferedReader SmvFileReader = new BufferedReader(new FileReader(smvFilePath));
		
		ArrayList<String> services = new ArrayList<String>();
		ArrayList<Integer> numberOfVars = new ArrayList<Integer>();
		
		Pattern envNamePattern = Pattern.compile("\\s*MODULE\\s+ServEnv\\(operation\\)");
		Pattern targetNamePattern = Pattern.compile("\\s*MODULE\\s+Target\\(operation\\)");
		Pattern behaviorNamePattern = Pattern.compile("\\s*MODULE\\s+(\\w+)\\(index,operation\\)");
		Pattern varDefinitionPattern = Pattern.compile("\\s*(\\w)+\\s*:\\s*(\\w+);");
		
		String fileLine;
		String currentModule = null;
		while ((fileLine = SmvFileReader.readLine()) != null)
		{
			Matcher matcher = envNamePattern.matcher(fileLine);
			while (matcher.find())
			{
				currentModule = "Env";
				services.add(currentModule);
				numberOfVars.add(services.indexOf(currentModule), 0);
			}
			
			matcher = targetNamePattern.matcher(fileLine);
			while (matcher.find())
			{
				currentModule = "Target";
				services.add(currentModule);
				numberOfVars.add(services.indexOf(currentModule), 0);
			}
			
			matcher = behaviorNamePattern.matcher(fileLine);
			while (matcher.find())
			{
				currentModule = matcher.group(1);
				services.add(currentModule);
				numberOfVars.add(services.indexOf(currentModule), 0);
			}
			
			matcher = varDefinitionPattern.matcher(fileLine);
			while (matcher.find())
			{
				if (currentModule != null)
				{
					int currentIndex = services.indexOf(currentModule);
					numberOfVars.set(currentIndex, numberOfVars.get(currentIndex)+1);
				}
			}
		}
		SmvFileReader.close();
		
		ArrayList[] result = new ArrayList[2];
		result[0] = services;
		result[1] = numberOfVars;
		return result;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private XmlComposition ParseTxtFile(String txtFilePath, ArrayList[] servicesNumOfVars, XmlComposition composition) throws IOException {
		BufferedReader TxtFileReader = new BufferedReader(new FileReader(txtFilePath));
		
		ArrayList<String> behaviorNames = servicesNumOfVars[0];
		ArrayList<Integer> behaviorNumberOfVars = servicesNumOfVars[1];
		
		// Regular expressions necessary for parsing the document generated by JTLV
		Pattern stateDescriptionPattern = Pattern.compile("\\s*State\\s*(\\d+)\\s*:\\s*State\\s*->\\s*<operation:(\\w+),\\s*([\\w+:\\w+,\\s*]*)index:(\\d+)>");
		//Pattern transitionPattern = Pattern.compile("\\s*From\\s*(\\d+)\\s*to\\s*(\\d+\\s*)+");
		
		// Objects to contain the information parsed from the JTLV document
		XmlCompositionState compositionState = null;
		XmlTargetState targetState = null;
		XmlEnvironmentState envState = null;
		XmlBehaviorState behaviorState = null;
		XmlTransition transition = null;
		List<XmlBehaviorState> behaviorStates = null;
		
		String fileLine;
		while ((fileLine = TxtFileReader.readLine()) != null)
		{
			Matcher matcher = stateDescriptionPattern.matcher(fileLine);
			while (matcher.find())
			{
				//String stateNumber = matcher.group(1);
				String operationName = matcher.group(2);
				if (operationName.equals("start_op"))
					break;

				String communityState = matcher.group(3);
				int index = Integer.parseInt(matcher.group(4));
				transition = new XmlTransition(behaviorNames.get(index+1), operationName);

				// Process the information about the state of each of the services
				String[] statesList = communityState.split(", ");
				int start = 0;
				behaviorStates = new ArrayList<XmlBehaviorState>();
				for (String service : behaviorNames)
				{
					switch (service) {
						case "Env":
							envState = new XmlEnvironmentState();
							break;
						case "Target":
							targetState = new XmlTargetState();
							break;
						default:
							behaviorState = new XmlBehaviorState(service);
							break;
					}

					int end = start + behaviorNumberOfVars.get(behaviorNames.indexOf(service));
					while (start < end)
					{
						String[] vars = statesList[start].split(":");
						switch (service) {
							case "Env":
								envState.addTerm(vars[0], vars[1]);
								break;
							case "Target":
								targetState.addTerm(vars[0], vars[1]);
								break;
							default:
								behaviorState.addTerm(vars[0], vars[1]);
								break;
						}

						start++;
					}

					if (service.equals("Env") == false && service.equals("Target") == false) {
						behaviorStates.add(behaviorState);
					}
				}
								
				boolean found = false;
				for (XmlCompositionState state : composition.compositionStates) {
					boolean behaviorStatesEquals = state.behaviorStates.equals(behaviorStates);
					boolean targetStateEquals = state.targetState.equals(targetState);
					boolean envStateEquals = state.environmentState.equals(envState);

					if (behaviorStatesEquals && targetStateEquals && envStateEquals) {
						state.addTransition(transition);
						found = true;
					}
				}
				
				if (found == false) {
					compositionState = new XmlCompositionState();
					compositionState.setEnvironmentState(envState);
					compositionState.setTargetState(targetState);
					compositionState.setBehaviorStates(behaviorStates);
					compositionState.addTransition(transition);
					composition.addCompositionState(compositionState);
				}
			}
		}
		
		System.out.println(composition.compositionStates.size() + " states found.");
		TxtFileReader.close();
		return composition;
	}
	
	private void WriteCompositionToXmlAndJson(XmlComposition composition, String compositionPath, String compositionXmlFilePath, String compositionJsonFilePath) throws IOException, JAXBException {
		new File(compositionPath).mkdirs();
		
		// Write the XML file
		File compositionXmlFile = new File(compositionXmlFilePath);
		synchronized (compositionXmlFile) {
			compositionXmlFile.createNewFile();

			JAXBContext jaxbContext = JAXBContext.newInstance(XmlComposition.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(composition, compositionXmlFile);
		}
		
		// Write the JSON file
		JsonComposition jsonComposition = new JsonComposition(composition);
		File compositionJsonFile = new File(compositionJsonFilePath);
		synchronized (compositionJsonFile) {
			compositionJsonFile.createNewFile();

			JSONJAXBContext jaxbContext = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(), JsonComposition.class);
			JSONMarshaller jsonMarshaller = jaxbContext.createJSONMarshaller();
			jsonMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jsonMarshaller.marshallToJSON(jsonComposition, new FileOutputStream(compositionJsonFile));
		}
	}
}
