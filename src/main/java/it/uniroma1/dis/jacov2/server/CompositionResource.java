package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.dao.JobsQueue;
import it.uniroma1.dis.jacov2.model.json.JsonComposition;
import it.uniroma1.dis.jacov2.model.xml.XmlComposition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONUnmarshaller;

@Path("/{client_id}/composition")
public class CompositionResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	@POST
	public Response requestComposition(@PathParam("client_id") String clientId) {
		JobsQueue.instance.getModel().add(clientId);
		
		return Response.ok().build();
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response getXmlComposition(@PathParam("client_id") String clientId) throws JAXBException {
		File compositionXmlFile = new File(rootPath + File.separator + clientId + File.separator + "Composition" + File.separator + "Composition.xml");
		Response res = null;

		if (compositionXmlFile.exists()) {
			XmlComposition composition = null;
			
			// Read the composition file and send it to the client
			synchronized (compositionXmlFile) {
					JAXBContext jaxbContext = JAXBContext.newInstance(XmlComposition.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					composition = (XmlComposition) jaxbUnmarshaller.unmarshal(compositionXmlFile);
			}

			res = Response.ok().entity(composition).build();
		} else {
			// Return the number of the request in the JobQueue
			String queueNumber = "Queue number: ";
			int queueSize = JobsQueue.instance.getModel().size();
			String[] queue = new String[queueSize]; 
			JobsQueue.instance.getModel().toArray(queue);
			
			for (int i = 0; i < queueSize; i++) {
				if (queue[i].equals(clientId)) {
					queueNumber += i;
					break;
				}
			}
			
			res = Response.ok().entity(queueNumber).build();
		}
		
		return res;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJsonComposition(@PathParam("client_id") String clientId) throws JAXBException, FileNotFoundException {
		File compositionJsonFile = new File(rootPath + File.separator + clientId + File.separator + "Composition" + File.separator + "Composition.json");
		Response res = null;

		if (compositionJsonFile.exists()) {
			JsonComposition composition = null;
			
			// Read the composition file and send it to the client
			synchronized (compositionJsonFile) {
					JSONJAXBContext jaxbContext = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(), JsonComposition.class);
					JSONUnmarshaller jaxbUnmarshaller = jaxbContext.createJSONUnmarshaller();
					composition = (JsonComposition) jaxbUnmarshaller.unmarshalFromJSON(new FileReader(compositionJsonFile), JsonComposition.class);
			}

			res = Response.ok().entity(composition).build();
		} else {
			// Return the number of the request in the JobQueue
			String queueNumber = "Queue number: ";
			int queueSize = JobsQueue.instance.getModel().size();
			String[] queue = new String[queueSize]; 
			JobsQueue.instance.getModel().toArray(queue);
			
			for (int i = 0; i < queueSize; i++) {
				if (queue[i].equals(clientId)) {
					queueNumber += i;
					break;
				}
			}
			
			res = Response.ok().entity(queueNumber).build();
		}

		return res;
	}
}
