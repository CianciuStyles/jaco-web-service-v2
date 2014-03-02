package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.model.json.JsonBehavior;
import it.uniroma1.dis.jacov2.model.xml.XmlBehavior;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/{client_id}/behaviors")
public class BehaviorsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	public List<XmlBehavior> getXmlBehaviors(@PathParam("client_id") String clientId) {
		List<XmlBehavior> result = new ArrayList<XmlBehavior>();
		
		File behaviorsRootPath = new File(rootPath + File.separator + clientId + File.separator + "Behaviors");
		File[] behaviorsDirectories = behaviorsRootPath.listFiles();
		
		for (File behaviorDirectory : behaviorsDirectories) {
			XmlBehavior behavior = new XmlBehavior();
			behavior.setName(behaviorDirectory.getName());
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			for (File behaviorFile : behaviorFiles) {
				String behaviorFileName = behaviorFile.getName();
				
				if (behaviorFileName.endsWith("_domain.txt")) {
					behavior.setDomainFilePath(behaviorFileName);
				} else if (behaviorFileName.endsWith("_problem.txt")) {
					behavior.setProblemFilePath(behaviorFileName);
				}
			}
			
			result.add(behavior);
		}
		
		return result;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<JsonBehavior> getJsonBehaviors(@PathParam("client_id") String clientId) {
		List<JsonBehavior> result = new ArrayList<JsonBehavior>();
		
		File behaviorsRootPath = new File(rootPath + File.separator + clientId + File.separator + "Behaviors");
		File[] behaviorsDirectories = behaviorsRootPath.listFiles();
		
		for (File behaviorDirectory : behaviorsDirectories) {
			JsonBehavior behavior = new JsonBehavior();
			behavior.setName(behaviorDirectory.getName());
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			for (File behaviorFile : behaviorFiles) {
				String behaviorFileName = behaviorFile.getName();
				
				if (behaviorFileName.endsWith("_domain.txt")) {
					behavior.setDomainFilePath(behaviorFileName);
				} else if (behaviorFileName.endsWith("_problem.txt")) {
					behavior.setProblemFilePath(behaviorFileName);
				}
			}
			
			result.add(behavior);
		}
		
		return result;
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount(@PathParam("client_id") String clientId) {
		File behaviorsRootPath = new File(rootPath + File.separator + clientId + File.separator + "Behaviors");
		File[] behaviorsDirectories = behaviorsRootPath.listFiles();
		
		return String.valueOf(behaviorsDirectories.length);
	}
	
	@POST
	public Response createNewBehavior(@PathParam("client_id") String clientId, @QueryParam("name") final String behaviorName) throws URISyntaxException {
		BehaviorResource behaviorResource = new BehaviorResource(uriInfo, request, clientId, behaviorName);
		return behaviorResource.createNewBehavior();
	}
	
	@DELETE
	public Response clearBehaviors(@PathParam("client_id") String clientId) {
		File behaviorsRootPath = new File(rootPath + File.separator + clientId + File.separator + "Behaviors");
		File[] behaviorsDirectories = behaviorsRootPath.listFiles();
		
		for (File behaviorDirectory : behaviorsDirectories) {
			File[] behaviorFiles = behaviorDirectory.listFiles();
			
			for (File behaviorFile : behaviorFiles) {
				behaviorFile.delete();
			}
			
			behaviorDirectory.delete();
		}
		
		return Response.ok().build();
	}
	
	@Path("{behaviorName}")
	public BehaviorResource getBehavior(@PathParam("client_id") String clientId, @PathParam("behaviorName") String behaviorName) {
		return new BehaviorResource(uriInfo, request, clientId, behaviorName);
	}
}
