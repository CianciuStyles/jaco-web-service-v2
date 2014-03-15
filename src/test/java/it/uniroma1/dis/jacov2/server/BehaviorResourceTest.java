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

public class BehaviorResourceTest extends JerseyTest {
	
	public BehaviorResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();

		// Delete all behavior already present on the server
		resource.path("testClientId").path("behaviors").delete();
		
		// Create behavior1 via the POST request
		resource.path("testClientId").path("behaviors").queryParam("name", "behavior1").post();
		
		// Upload behavior1 domain file
		File behavior1DomainFile = new File(ClassLoader.getSystemResource("pa_ba_dom.txt").toURI());
		FormDataMultiPart behavior1DomainForm = new FormDataMultiPart();
		behavior1DomainForm.bodyPart(new FileDataBodyPart("file", behavior1DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior1").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior1DomainForm);
		
		// Upload behavior1 problem file
		File behavior1ProblemFile = new File(ClassLoader.getSystemResource("pa_ba_prob.txt").toURI());
		FormDataMultiPart behavior1ProblemForm = new FormDataMultiPart();
		behavior1ProblemForm.bodyPart(new FileDataBodyPart("file", behavior1ProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior1").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior1ProblemForm);
	}
	
	@Test
	public void testGetXmlBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void testGetJsonBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void testGetNonAvailableXmlBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("absentBehavior").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetNonAvailableJsonBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("absentBehavior").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
}
