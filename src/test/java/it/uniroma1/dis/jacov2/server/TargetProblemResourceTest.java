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

public class TargetProblemResourceTest extends JerseyTest {

	public TargetProblemResourceTest() {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();

		// Delete the already present on the server
		resource.path("testClientId").path("target").delete();
		
		// Upload target domain file
		File targetDomainFile = new File(ClassLoader.getSystemResource("pa_target_dom.txt").toURI());
		FormDataMultiPart targetDomainForm = new FormDataMultiPart();
		targetDomainForm.bodyPart(new FileDataBodyPart("file", targetDomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("target").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(targetDomainForm);
		
		// Upload target problem file
		File targetProblemFile = new File(ClassLoader.getSystemResource("pa_target_prob.txt").toURI());
		FormDataMultiPart targetProblemForm = new FormDataMultiPart();
		targetProblemForm.bodyPart(new FileDataBodyPart("file", targetProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("target").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(targetProblemForm);
	}
	
	@Test
	public void testGetProblemFile() throws URISyntaxException {
		WebResource resource = resource();				
		ClientResponse response = resource.path("testClientId").path("target").path("problem").get(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(MediaType.valueOf(MediaType.TEXT_PLAIN), response.getType());
	}
	
	@Test
	public void testGetDomainFileFailing() {
		WebResource resource = resource();
		resource.path("testClientId").path("target").path("problem").delete();
		ClientResponse response = resource.path("testClientId").path("target").path("problem").get(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteDomainFile() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("target").path("problem").delete(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteProblemFileFailing() {
		WebResource resource = resource();
		resource.path("testClientId").path("target").path("problem").delete();
		ClientResponse response = resource.path("testClientId").path("target").path("problem").delete(ClientResponse.class);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPostDomainFile() throws URISyntaxException {
		WebResource resource = resource();
		resource.path("testClientId").path("target").path("problem").delete();
		
		File targetProblemFile = new File(ClassLoader.getSystemResource("pa_target_prob.txt").toURI());
		FormDataMultiPart targetProblemForm = new FormDataMultiPart();
		targetProblemForm.bodyPart(new FileDataBodyPart("file", targetProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("target").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, targetProblemForm);
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutProblemFile() throws URISyntaxException {
		WebResource resource = resource();
		
		File targetProblemFile = new File(ClassLoader.getSystemResource("pa_target_prob.txt").toURI());
		FormDataMultiPart targetProblemForm = new FormDataMultiPart();
		targetProblemForm.bodyPart(new FileDataBodyPart("file", targetProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("target").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, targetProblemForm);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testPutProblemFileFailing() throws URISyntaxException {
		WebResource resource = resource();
		resource.path("testClientId").path("target").path("problem").delete();
		
		File targetProblemFile = new File(ClassLoader.getSystemResource("pa_target_prob.txt").toURI());
		FormDataMultiPart targetProblemForm = new FormDataMultiPart();
		targetProblemForm.bodyPart(new FileDataBodyPart("file", targetProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		ClientResponse response = resource.path("testClientId").path("target").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).put(ClientResponse.class, targetProblemForm);
		
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
