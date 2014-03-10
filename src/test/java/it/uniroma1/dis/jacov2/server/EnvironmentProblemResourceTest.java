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

public class EnvironmentProblemResourceTest extends JerseyTest {

	public EnvironmentProblemResourceTest() {
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
	public void testGetProblemFile() throws URISyntaxException {
		WebResource resource = resource();				
		ClientResponse response = resource.path("testClientId").path("env").path("problem").get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.valueOf(MediaType.TEXT_PLAIN), response.getType());
	}
	
	@Test
	public void testGetDomainFileFailing() {
		WebResource resource = resource();
		resource.path("testClientId").path("env").path("problem").delete();
		ClientResponse response = resource.path("testClientId").path("env").path("problem").get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("env").path("problem").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteProblemFileFailing() {
		WebResource resource = resource();
		resource.path("testClientId").path("env").path("problem").delete();
		ClientResponse response = resource.path("testClientId").path("env").path("problem").delete(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPostDomainFile() throws URISyntaxException {
		WebResource resource = resource();
		resource.path("testClientId").path("env").path("problem").delete();
		
		File environmentProblemFile = new File(ClassLoader.getSystemResource("pa_env_prob.txt").toURI());
		FormDataMultiPart environmentProblemForm = new FormDataMultiPart();
		environmentProblemForm.bodyPart(new FileDataBodyPart("file", environmentProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("env").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, environmentProblemForm);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutProblemFile() throws URISyntaxException {
		WebResource resource = resource();
		
		File environmentProblemFile = new File(ClassLoader.getSystemResource("pa_env_prob.txt").toURI());
		FormDataMultiPart environmentProblemForm = new FormDataMultiPart();
		environmentProblemForm.bodyPart(new FileDataBodyPart("file", environmentProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("env").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, environmentProblemForm);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutProblemFileFailing() throws URISyntaxException {
		WebResource resource = resource();
		resource.path("testClientId").path("env").path("problem").delete();
		
		File environmentProblemFile = new File(ClassLoader.getSystemResource("pa_env_prob.txt").toURI());
		FormDataMultiPart environmentProblemForm = new FormDataMultiPart();
		environmentProblemForm.bodyPart(new FileDataBodyPart("file", environmentProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("env").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, environmentProblemForm);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
