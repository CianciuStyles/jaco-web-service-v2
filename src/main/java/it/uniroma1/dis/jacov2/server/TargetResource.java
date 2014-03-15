package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.model.json.JsonTarget;
import it.uniroma1.dis.jacov2.model.xml.XmlTarget;

import java.io.File;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.api.Responses;

@Path("/{client_id}/target")
public class TargetResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response getXmlTarget(@PathParam("client_id") String clientId) {
		File targetDirectory = new File(rootPath + File.separator + clientId + File.separator + "Target");
		Response response;
		
		if (targetDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			XmlTarget target = new XmlTarget();
			File[] targetFiles = targetDirectory.listFiles();
			
			for (File targetFile : targetFiles) {
				String targetFileName = targetFile.getName();
				
				if (targetFileName.endsWith("_domain.txt")) {
					target.setDomainFilePath(targetFileName);
				} else if (targetFileName.endsWith("_problem.txt")) {
					target.setProblemFilePath(targetFileName);
				}
			}
			
			response = Response.ok(target).build();
		}
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJsonTarget(@PathParam("client_id") String clientId) {
		File targetDirectory = new File(rootPath + File.separator + clientId + File.separator + "Target");
		Response response;
		
		if (targetDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			JsonTarget target = new JsonTarget();
			File[] targetFiles = targetDirectory.listFiles();
			
			for (File targetFile : targetFiles) {
				String targetFileName = targetFile.getName();
				
				if (targetFileName.endsWith("_domain.txt")) {
					target.setDomainFilePath(targetFileName);
				} else if (targetFileName.endsWith("_problem.txt")) {
					target.setProblemFilePath(targetFileName);
				}
			}
			
			response = Response.ok(target).build();
		}
		
		return response;
	}
	
	@DELETE
	public Response deleteTarget(@PathParam("client_id") String clientId) {
		Response response;
		
		File targetDirectory = new File(rootPath + File.separator + clientId + File.separator + "Target");
		if (targetDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			File[] targetFiles = targetDirectory.listFiles();
			
			boolean allChildrenDeleted = true;
			
			for (File targetFile : targetFiles) {
				boolean success = targetFile.delete();
				if (success == false) {
					allChildrenDeleted = false;
					response = Response.serverError().build();
					break;
				}
			}
			
			if (allChildrenDeleted == true) {
				targetDirectory.delete();
				response = Response.ok().build();
			} else {
				response = Response.serverError().build();
			}
		}
		
		return response;
	}
	
	@Path("domain")
	public TargetDomainResource getDomain(@PathParam("client_id") String clientId) {
		return new TargetDomainResource(uriInfo, request, rootPath, clientId);
	}
	
	@Path("problem")
	public TargetProblemResource getProblem(@PathParam("client_id") String clientId) {
		return new TargetProblemResource(uriInfo, request, rootPath, clientId);
	}
}
