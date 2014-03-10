package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.test.framework.JerseyTest;

public class EnvironmentResourceTest extends JerseyTest{
	
	public EnvironmentResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();

		// Delete all behavior already present on the server
		resource.path("testClientId").path("env").delete();
		
		// Upload environment domain file
		File environmentDomainFile = new File(ClassLoader.getSystemResource("pa_env_dom.txt").toURI());
		FormDataMultiPart environmentDomainForm = new FormDataMultiPart();
		environmentDomainForm.bodyPart(new FileDataBodyPart("file", environmentDomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("env").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(environmentDomainForm);
		
		// Upload environment problem file
		File environmentProblemFile = new File(ClassLoader.getSystemResource("pa_env_prob.txt").toURI());
		FormDataMultiPart environmentProblemForm = new FormDataMultiPart();
		environmentProblemForm.bodyPart(new FileDataBodyPart("file", environmentProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("env").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(environmentProblemForm);
	}
	
	@Test
	public void testGetXmlEnvironment() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("env").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
		
		// Delete the domain file
		resource.path("testClientId").path("env").path("domain").delete();
		response = resource.path("testClientId").path("env").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
		
		// Delete the problem file
		resource.path("testClientId").path("env").path("problem").delete();
		response = resource.path("testClientId").path("env").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
	}
	
	@Test
	public void testGetJsonEnvironment() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("env").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
		
		// Delete the domain file
		resource.path("testClientId").path("env").path("domain").delete();
		response = resource.path("testClientId").path("env").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
		
		// Delete the problem file
		resource.path("testClientId").path("env").path("problem").delete();
		response = resource.path("testClientId").path("env").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
	}
}
