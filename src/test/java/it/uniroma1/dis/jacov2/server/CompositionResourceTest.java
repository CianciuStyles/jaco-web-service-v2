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
		
		// Upload behavior2
		// Create behavior2 via the POST request
		resource.path("testClientId").path("behaviors").queryParam("name", "behavior2").post();
		
		// Upload behavior2 domain file
		File behavior2DomainFile = new File(ClassLoader.getSystemResource("pa_bb_dom.txt").toURI());
		FormDataMultiPart behavior2DomainForm = new FormDataMultiPart();
		behavior2DomainForm.bodyPart(new FileDataBodyPart("file", behavior2DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior2").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior2DomainForm);
		
		// Upload behavior2 problem file
		File behavior2ProblemFile = new File(ClassLoader.getSystemResource("pa_bb_prob.txt").toURI());
		FormDataMultiPart behavior2ProblemForm = new FormDataMultiPart();
		behavior2ProblemForm.bodyPart(new FileDataBodyPart("file", behavior2ProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior2").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior2ProblemForm);
		
		// Upload behavior3
		// Create behavior3 via the POST request
		resource.path("testClientId").path("behaviors").queryParam("name", "behavior3").post();
		
		// Upload behavior3 domain file
		File behavior3DomainFile = new File(ClassLoader.getSystemResource("pa_bc_dom.txt").toURI());
		FormDataMultiPart behavior3DomainForm = new FormDataMultiPart();
		behavior3DomainForm.bodyPart(new FileDataBodyPart("file", behavior3DomainFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior3").path("domain").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior3DomainForm);
		
		// Upload behavior3 problem file
		File behavior3ProblemFile = new File(ClassLoader.getSystemResource("pa_bc_prob.txt").toURI());
		FormDataMultiPart behavior3ProblemForm = new FormDataMultiPart();
		behavior3ProblemForm.bodyPart(new FileDataBodyPart("file", behavior3ProblemFile, MediaType.valueOf(MediaType.TEXT_PLAIN)));
		resource.path("testClientId").path("behaviors").path("behavior3").path("problem").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(behavior3ProblemForm);
				
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
