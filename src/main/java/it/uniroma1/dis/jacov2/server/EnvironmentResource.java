package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.model.json.JsonEnvironment;
import it.uniroma1.dis.jacov2.model.xml.XmlEnvironment;

import java.io.File;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.Responses;

@Path("/{client_id}/env")
public class EnvironmentResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response getXmlEnvironment(@PathParam("client_id") String clientId) {
		File environmentDirectory = new File(rootPath + File.separator + clientId + File.separator + "Environment");
		Response response;
		
		if (environmentDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			XmlEnvironment enviroment = new XmlEnvironment();
			File[] environmentFiles = environmentDirectory.listFiles();
			
			for (File environmentFile : environmentFiles) {
				String environmentFileName = environmentFile.getName();
				
				if (environmentFileName.endsWith("_domain.txt")) {
					enviroment.setDomainFilePath(environmentFileName);
				} else if (environmentFileName.endsWith("_problem.txt")) {
					enviroment.setProblemFilePath(environmentFileName);
				}
			}
			
			response = Response.ok(enviroment).build();
		}
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJsonEnvironment(@PathParam("client_id") String clientId) {
		File environmentDirectory = new File(rootPath + File.separator + clientId + File.separator + "Environment");
		Response response;
		
		if (environmentDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			JsonEnvironment environment = new JsonEnvironment();
			File[] environmentFiles = environmentDirectory.listFiles();
			
			for (File environmentFile : environmentFiles) {
				String environmentFileName = environmentFile.getName();
				
				if (environmentFileName.endsWith("_domain.txt")) {
					environment.setDomainFilePath(environmentFileName);
				} else if (environmentFileName.endsWith("_problem.txt")) {
					environment.setProblemFilePath(environmentFileName);
				}
			}
			
			response = Response.ok(environment).build();
		}
		
		return response;
	}
	
	@DELETE
	public Response deleteEnvironment(@PathParam("client_id") String clientId) {
		Response response;
		
		File environmentDirectory = new File(rootPath + File.separator + clientId + File.separator + "Environment");
		if (environmentDirectory.exists() == false) {
			response = Responses.notFound().build();
		} else {
			File[] environmentFiles = environmentDirectory.listFiles();
			
			boolean allChildrenDeleted = true;
			
			for (File environmentFile : environmentFiles) {
				boolean success = environmentFile.delete();
				if (success == false) {
					allChildrenDeleted = false;
					response = Response.serverError().build();
					break;
				}
			}
			
			if (allChildrenDeleted == true) {
				environmentDirectory.delete();
				response = Response.ok().build();
			} else {
				response = Response.serverError().build();
			}
		}
		
		return response;
	}
	
	@Path("domain")
	public EnvironmentDomainResource getDomain(@PathParam("client_id") String clientId) {
		return new EnvironmentDomainResource(uriInfo, request, rootPath, clientId);
	}
	
	@Path("problem")
	public EnvironmentProblemResource getProblem(@PathParam("client_id") String clientId) {
		return new EnvironmentProblemResource(uriInfo, request, rootPath, clientId);
	}
}
