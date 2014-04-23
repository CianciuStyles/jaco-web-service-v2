package it.uniroma1.dis.jacov2.model.json;

import it.uniroma1.dis.jacov2.model.xml.XmlTransition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonTransition {
	@XmlElement
	String behavior;
	@XmlElement
	String action;
	
	public JsonTransition() {}
	
	public JsonTransition(XmlTransition xmlTransition) {
		this.behavior = xmlTransition.behavior;
		this.action = xmlTransition.action;
	}
}
