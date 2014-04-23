package it.uniroma1.dis.jacov2.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transition")
public class XmlTransition {
	@XmlAttribute
	public String behavior;
	@XmlAttribute
	public String action;
	
	public XmlTransition() {}
	
	public XmlTransition(String behavior, String action) {
		this.behavior = behavior;
		this.action = action;
	}
}
