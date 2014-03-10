package it.uniroma1.dis.jacov2.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.Responses;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

public class EnvironmentProblemResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String environmentRootPath;

	public EnvironmentProblemResource(UriInfo uriInfo, Request request, String rootPath, String clientId) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.environmentRootPath = rootPath + File.separator + clientId + File.separator + "Environment";
	}
	
	@GET
	public Response getDomainFile() {
		File[] environmentFiles = new File(environmentRootPath).listFiles();
				
		for (File environmentFile : environmentFiles) {
			if (environmentFile.getName().endsWith("_problem.txt")) {
				return Response.ok(environmentFile, MediaType.valueOf(MediaType.TEXT_PLAIN)).build();
			}
		}
		
		return Responses.notFound().build();
	}
	
	@DELETE
	public Response deleteDomainFile() {
		File[] environmentFiles = new File(environmentRootPath).listFiles();
		
		for (File environmentFile : environmentFiles) {
			if (environmentFile.getName().endsWith("_problem.txt")) {
				boolean success = environmentFile.delete();
				
				if (success == true) {
					return Response.ok().build();
				} else {
					return Response.notModified().build();
				}
			}
		}
		
		return Responses.notFound().build();
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postDomainFile(@FormDataParam("file") InputStream uploadedFile, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			new File(environmentRootPath).mkdirs();
			String fullName = environmentRootPath + File.separator + "env_problem.txt";
			OutputStream fileWriter = new FileOutputStream(new File(fullName));

			int read = 0;
			byte[] readBytes = new byte[1024];
			while ((read = uploadedFile.read(readBytes)) != -1) {
				fileWriter.write(readBytes, 0, read);
			}

			fileWriter.flush();
			fileWriter.close();
			
			URI directoryLocation = new URI(uriInfo.getAbsolutePath() + "/" + "env_problem.txt");
			return Response.created(directoryLocation).build();
		} catch (FileNotFoundException ex) {
			return Response.serverError().build();
		} catch (IOException e) {
			return Response.serverError().build();
		} catch (URISyntaxException e) {
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response putDomainFile(@FormDataParam("file") InputStream uploadedFile, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			String fullName = environmentRootPath + File.separator  + "env_problem.txt";
			File problemFile = new File(fullName);
			
			if (problemFile.exists() == false) {
				return Responses.notFound().build();
			} else {
				OutputStream fileWriter = new FileOutputStream(new File(fullName));
	
				int read = 0;
				byte[] readBytes = new byte[1024];
				while ((read = uploadedFile.read(readBytes)) != -1) {
					fileWriter.write(readBytes, 0, read);
				}
	
				fileWriter.flush();
				fileWriter.close();
				
				return Response.ok().build();
			}
		} catch (FileNotFoundException ex) {
			return Responses.notFound().build();
		} catch (IOException e) {
			return Response.serverError().build();
		}
	}
}
