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

public class CompositionResourceTest extends JerseyTest {

	public CompositionResourceTest() throws Exception {
		super("it.uniroma1.dis.jacov2.server");
	}
	
	@Before
	public void prepareServerForTests() throws URISyntaxException {
		WebResource resource = resource();
		
		// Clear any file already present on server
		resource.path("testClientId").path("behaviors").delete();
		resource.path("testClientId").path("env").delete();
		resource.path("testClientId").path("target").delete();
		
		// Upload all the behaviors
		// Upload behavior1
		// Upload behavior2
		// Upload behavior3
		
		// Upload environment files
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
		
		// Upload target files
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
	public void testRequestComposition() {
		WebResource resource = resource();
		ClientResponse response = resource.path("testClientId").path("composition").post(ClientResponse.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	/*
	@Test
	public void testGetComposition() {
		fail("Not yet implemented");
	}
	*/
}
