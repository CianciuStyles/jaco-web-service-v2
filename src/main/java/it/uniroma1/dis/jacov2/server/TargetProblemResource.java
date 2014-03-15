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

public class TargetProblemResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String targetRootPath;

	public TargetProblemResource(UriInfo uriInfo, Request request, String rootPath, String clientId) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.targetRootPath = rootPath + File.separator + clientId + File.separator + "Target";
	}
	
	@GET
	public Response getProblemFile() {
		File[] targetFiles = new File(targetRootPath).listFiles();
				
		for (File targetFile : targetFiles) {
			if (targetFile.getName().endsWith("_problem.txt")) {
				return Response.ok(targetFile, MediaType.valueOf(MediaType.TEXT_PLAIN)).build();
			}
		}
		
		return Responses.notFound().build();
	}
	
	@DELETE
	public Response deleteProblemFile() {
		File[] targetFiles = new File(targetRootPath).listFiles();
		
		for (File targetFile : targetFiles) {
			if (targetFile.getName().endsWith("_problem.txt")) {
				boolean success = targetFile.delete();
				
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
	public Response postProblemFile(@FormDataParam("file") InputStream uploadedFile, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			new File(targetRootPath).mkdirs();
			String fullName = targetRootPath + File.separator + "target_problem.txt";
			OutputStream fileWriter = new FileOutputStream(new File(fullName));

			int read = 0;
			byte[] readBytes = new byte[1024];
			while ((read = uploadedFile.read(readBytes)) != -1) {
				fileWriter.write(readBytes, 0, read);
			}

			fileWriter.flush();
			fileWriter.close();
			
			URI directoryLocation = new URI(uriInfo.getAbsolutePath() + "/" + "target_problem.txt");
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
	public Response putProblemFile(@FormDataParam("file") InputStream uploadedFile, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		try {
			String fullName = targetRootPath + File.separator  + "target_problem.txt";
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
