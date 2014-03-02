package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.model.json.JsonBehavior;
import it.uniroma1.dis.jacov2.model.xml.XmlBehavior;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.Responses;

public class BehaviorResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	String behaviorsRootPath;
	String behaviorName;
	
	public BehaviorResource(UriInfo uriInfo, Request request, String clientId, String behaviorName) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.behaviorsRootPath = rootPath + File.separator + clientId + File.separator + "Behaviors";
		this.behaviorName = behaviorName;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response getXmlBehavior() {
		File behaviorDirectory = new File(behaviorsRootPath + File.separator + behaviorName);
		Response response;
		
		if (behaviorDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			XmlBehavior behavior = new XmlBehavior();
			behavior.setName(behaviorName);
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			for (File behaviorFile : behaviorFiles) {
				String behaviorFileName = behaviorFile.getName();
				
				if (behaviorFileName.endsWith("_domain.txt")) {
					behavior.setDomainFilePath(behaviorFileName);
				} else if (behaviorFileName.endsWith("_problem.txt")) {
					behavior.setProblemFilePath(behaviorFileName);
				}
			}
			
			response = Response.ok(behavior).build();
		}
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJsonBehavior() {
		File behaviorDirectory = new File(behaviorsRootPath + File.separator + behaviorName);
		Response response;
		
		if (behaviorDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			JsonBehavior behavior = new JsonBehavior();
			behavior.setName(behaviorName);
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			for (File behaviorFile : behaviorFiles) {
				String behaviorFileName = behaviorFile.getName();
				
				if (behaviorFileName.endsWith("_domain.txt")) {
					behavior.setDomainFilePath(behaviorFileName);
				} else if (behaviorFileName.endsWith("_problem.txt")) {
					behavior.setProblemFilePath(behaviorFileName);
				}
			}
			
			response = Response.ok(behavior).build();
		}
		
		return response;
	}
	
	public Response createNewBehavior() throws URISyntaxException {
		return createAndGetResponse(behaviorName);
	}
	
	private Response createAndGetResponse(String behaviorName) throws URISyntaxException {
		Response response;
		
		File behaviorDirectory = new File(behaviorsRootPath + File.separator + behaviorName);
		
		if (behaviorDirectory.exists()) {
			response = Response.noContent().build();
		} else {
			boolean directoryCreated = behaviorDirectory.mkdirs();
			
			if (directoryCreated == true) {
				URI directoryLocation = new URI(uriInfo.getAbsolutePath() + "/" + behaviorName);
				response = Response.created(directoryLocation).build();
			} else {
				response = Response.serverError().build();
			}
		}
		
		return response;
	}
	
	@DELETE
	public Response deleteBehavior() {
		Response response;
		
		File behaviorDirectory = new File(behaviorsRootPath + File.separator + behaviorName);
		if (behaviorDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			boolean allChildrenDeleted = true;
			
			for (File behaviorFile : behaviorFiles) {
				boolean success = behaviorFile.delete();
				if (success == false) {
					allChildrenDeleted = false;
					response = Response.serverError().build();
					break;
				}
			}
			
			if (allChildrenDeleted == true) {
				behaviorDirectory.delete();
				response = Response.ok().build();
			} else {
				response = Response.serverError().build();
			}
		}
		
		return response;
	}
	
	@Path("domain")
	public BehaviorDomainResource getDomain() {
		return new BehaviorDomainResource(uriInfo, request, behaviorsRootPath, behaviorName);
	}
}
