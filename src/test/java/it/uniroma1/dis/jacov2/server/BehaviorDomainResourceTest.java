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

public class BehaviorDomainResourceTest extends JerseyTest {

	public BehaviorDomainResourceTest() {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();

		// Delete all behavior already present on the server
		resource.path("testClientId").path("behaviors").delete();
		
		// Create structure for behavior1
		resource.path("testClientId").path("behaviors").queryParam("name", "behavior1").post();
		
		File behavior1DomainFile = new File(ClassLoader.getSystemResource("pa_ba_dom.txt").toURI());
		FormDataMultiPart behavior1DomainForm = new FormDataMultiPart();
		behavior1DomainForm.bodyPart(new FileDataBodyPart("file", behavior1DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior1").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior1DomainForm);
		
		File behavior1ProblemFile = new File(ClassLoader.getSystemResource("pa_ba_prob.txt").toURI());
		FormDataMultiPart behavior1ProblemForm = new FormDataMultiPart();
		behavior1ProblemForm.bodyPart(new FileDataBodyPart("file", behavior1ProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior1").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior1ProblemForm);
		
		// Create structure for behavior2
		resource.path("testClientId").path("behaviors").queryParam("name", "behavior2").post();
		
		File behavior2ProblemFile = new File(ClassLoader.getSystemResource("pa_bb_prob.txt").toURI());
		FormDataMultiPart behavior2ProblemForm = new FormDataMultiPart();
		behavior2ProblemForm.bodyPart(new FileDataBodyPart("file", behavior2ProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior1").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior2ProblemForm);
	}
	
	@Test
	public void testGetDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").path("domain").get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.valueOf(MediaType.TEXT_PLAIN), response.getType());
	}
	
	@Test
	public void testGetDomainFileFailing() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior2").path("domain").get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").path("domain").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteProblemFileFailing() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior2").path("domain").delete(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPostDomainFile() throws URISyntaxException {
		WebResource resource = resource();
		
		File behavior1DomainFile = new File(ClassLoader.getSystemResource("pa_ba_dom.txt").toURI());
		FormDataMultiPart behavior1DomainForm = new FormDataMultiPart();
		behavior1DomainForm.bodyPart(new FileDataBodyPart("file", behavior1DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior2").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, behavior1DomainForm);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutDomainFile() throws URISyntaxException {
		WebResource resource = resource();
		
		File behavior1DomainFile = new File(ClassLoader.getSystemResource("pa_ba_prob.txt").toURI());
		FormDataMultiPart behavior1DomainForm = new FormDataMultiPart();
		behavior1DomainForm.bodyPart(new FileDataBodyPart("file", behavior1DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior1").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, behavior1DomainForm);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutDomainFileFailing() throws URISyntaxException {
		WebResource resource = resource();
		
		File behavior2DomainFile = new File(ClassLoader.getSystemResource("pa_bb_prob.txt").toURI());
		FormDataMultiPart behavior2DomainForm = new FormDataMultiPart();
		behavior2DomainForm.bodyPart(new FileDataBodyPart("file", behavior2DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("behaviors").path("behavior2").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, behavior2DomainForm);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
