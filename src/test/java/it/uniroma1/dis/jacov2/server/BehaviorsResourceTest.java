package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class BehaviorsResourceTest extends JerseyTest {

	public BehaviorsResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	/*
	@Test
	public void testCreateNewBehavior() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").queryParam("name", "behavior1").post(ClientResponse.class);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	*/
	@Test
	public void testGetXmlBehaviors() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void testGetJsonBehaviors() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
		
		System.out.println(response.getEntity(String.class));
	}
	
	/*
	@Test
	public void testClearBehaviors() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	*/
	@Test
	public void testGetCount() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("count").get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN, response.getType().toString());
	}
}
