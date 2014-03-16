package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.dao.JobsQueue;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	public Response getComposition(@PathParam("client_id") String clientId) {
		return null;
	}
}
