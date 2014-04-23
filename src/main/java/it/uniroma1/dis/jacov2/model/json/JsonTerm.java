package it.uniroma1.dis.jacov2.model.json;

import it.uniroma1.dis.jacov2.model.xml.XmlTerm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonTerm {
	@XmlElement
	String name;
	@XmlElement
	String value;
	
	public JsonTerm() {}
	
	public JsonTerm(XmlTerm xmlTerm) {
		this.name = xmlTerm.name;
		this.value = xmlTerm.value;
	}
}
