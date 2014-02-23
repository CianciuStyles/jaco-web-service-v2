package it.uniroma1.dis.jacov2.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class BehaviorResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String rootPath = new File("").getAbsolutePath() + File.separator + "JacoStorage";
	String behaviorsRootPath;
	
	public BehaviorResource(UriInfo uriInfo, Request request, String clientId) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.behaviorsRootPath = rootPath + File.separator + clientId + File.separator + "Behaviors";
	}
	
	public Response createNewBehavior(final String behaviorName) throws URISyntaxException {
		return createAndGetResponse(behaviorName);
	}
	
	private Response createAndGetResponse(final String behaviorName) throws URISyntaxException {
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
}
