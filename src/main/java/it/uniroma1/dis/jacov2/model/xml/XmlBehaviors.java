package it.uniroma1.dis.jacov2.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "behaviors")
public class XmlBehaviors {
	@XmlElement(name = "behavior")
	List<XmlBehavior> behaviors;
	
	public XmlBehaviors() {
		this.behaviors = new ArrayList<XmlBehavior>();
	}
	
	public void add(XmlBehavior behavior) {
		this.behaviors.add(behavior);
	}
}
