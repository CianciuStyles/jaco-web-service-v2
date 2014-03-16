package it.uniroma1.dis.jacov2.daemonthread;

import it.uniroma1.dis.jacov2.composition.Composition;
import it.uniroma1.dis.jacov2.dao.JobsQueue;
import it.uniroma1.dis.jacov2.pddl2smv.pddl2smv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DaemonThread extends Thread {

	BlockingQueue<String> jobsQueue = JobsQueue.instance.getModel();
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	public void run() {
		while (true) {
			try {
				if (jobsQueue.isEmpty()) {
					//System.out.println("DaemonThread going to sleep...");
					Thread.sleep(500);
				} else {
					String nextJobId = jobsQueue.peek();
					
					prepareFilesForComposition(nextJobId);
					askForComposition(nextJobId);
					
					jobsQueue.remove();
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
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
}
