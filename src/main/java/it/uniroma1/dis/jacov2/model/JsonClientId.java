package it.uniroma1.dis.jacov2.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonClientId {
	@XmlElement(name = "client_id")
	public String clientId;
	
	public JsonClientId() {}
	
	public JsonClientId(String clientId) {
		this.clientId = clientId;
	}
}
