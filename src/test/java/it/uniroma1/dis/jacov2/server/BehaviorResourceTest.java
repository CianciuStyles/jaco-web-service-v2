package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class BehaviorResourceTest extends JerseyTest {
	
	public BehaviorResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
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
	
	/*
	@Test
	public void testGetXmlProblemFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("problem").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetJsonProblemFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("problem").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPostProblemFile() throws URISyntaxException {
		File exampleProblemFile = new File(ClassLoader.getSystemResource("pa_ba_prob.txt").toURI());
		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", exampleProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, form);

		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteProblemFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("problem").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	*/
}
