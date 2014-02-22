package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

import org.junit.Test;

public class AuthResourceTest extends JerseyTest {
	
	public AuthResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Test
	public void testTextPlainClientId() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("auth").accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN, response.getType().toString());
	}

	@Test
	public void testTextXmlCliendId() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("auth").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.TEXT_XML, response.getType().toString());
	}
	
	@Test
	public void testApplicationXmlCliendId() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("auth").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_XML, response.getType().toString());
	}
	
	@Test
	public void testApplicationJsonClientId() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("auth").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, response.getType().toString());
	}
	
	@Test
	public void testInvalidCliendId() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("auth").accept(MediaType.APPLICATION_OCTET_STREAM).get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
}
