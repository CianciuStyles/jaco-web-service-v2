package it.uniroma1.dis.jacov2.server;

import it.uniroma1.dis.jacov2.model.json.JsonClientId;
import it.uniroma1.dis.jacov2.model.xml.XmlClientId;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/auth")
public class AuthResource {
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	SessionIdentifierGenerator sig = new SessionIdentifierGenerator();
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	public XmlClientId getXmlClientIdBrowser() {
		XmlClientId xmlClientId = new XmlClientId();
		xmlClientId.setClientId(sig.nextSessionId());
		return xmlClientId;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonClientId getJsonClientId() {
		JsonClientId jsonClientId = new JsonClientId();
		jsonClientId.setClientId(sig.nextSessionId());
		return jsonClientId;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTextPlainClientId() {
		String textPlainId = sig.nextSessionId();
		return textPlainId;
	}
}

final class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();
	
	public String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
}
