package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.assertEquals;

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

public class TargetResourceTest extends JerseyTest {
	
	public TargetResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();

		// Delete all behavior already present on the server
		resource.path("testClientId").path("target").delete();
		
		// Upload environment domain file
		File targetDomainFile = new File(ClassLoader.getSystemResource("pa_target_dom.txt").toURI());
		FormDataMultiPart targetDomainForm = new FormDataMultiPart();
		targetDomainForm.bodyPart(new FileDataBodyPart("file", targetDomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("target").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(targetDomainForm);
		
		// Upload environment problem file
		File targetProblemFile = new File(ClassLoader.getSystemResource("pa_target_prob.txt").toURI());
		FormDataMultiPart targetProblemForm = new FormDataMultiPart();
		targetProblemForm.bodyPart(new FileDataBodyPart("file", targetProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("target").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(targetProblemForm);
	}
	
	@Test
	public void testGetXmlEnvironment() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("target").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
		
		// Delete the domain file
		resource.path("testClientId").path("target").path("domain").delete();
		response = resource.path("testClientId").path("target").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
		
		// Delete the problem file
		resource.path("testClientId").path("target").path("problem").delete();
		response = resource.path("testClientId").path("target").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
	}
	
	@Test
	public void testGetJsonEnvironment() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("target").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
		
		// Delete the domain file
		resource.path("testClientId").path("target").path("domain").delete();
		response = resource.path("testClientId").path("target").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
		
		// Delete the problem file
		resource.path("testClientId").path("target").path("problem").delete();
		response = resource.path("testClientId").path("target").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
	}
}
