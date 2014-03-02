package it.uniroma1.dis.jacov2.server;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class BehaviorDomainResourceTest extends JerseyTest {

	public BehaviorDomainResourceTest () {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Test
	public void testGetDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").path("domain").get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	/*
	@Test
	public void testGetJsonDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("domain").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testPostDomainFile() throws URISyntaxException {
		File exampleDomainFile = new File(ClassLoader.getSystemResource("pa_ba_dom.txt").toURI());
		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", exampleDomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, form);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behavior").path("behavior1").path("domain").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	*/
}
