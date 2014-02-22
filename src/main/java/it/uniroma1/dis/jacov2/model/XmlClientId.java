package it.uniroma1.dis.jacov2.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "client_id")
public class XmlClientId {
	@XmlValue
	public String clientId;
	
	public XmlClientId(){}
	
	public XmlClientId(String clientId) {
		this.clientId = clientId;
	}
}
