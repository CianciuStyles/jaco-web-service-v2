package it.uniroma1.dis.jacov2.model.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonClientId {
	@XmlElement(name = "client_id")
	public String clientId;
	
	public JsonClientId() {}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
