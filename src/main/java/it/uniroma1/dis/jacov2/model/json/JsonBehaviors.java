package it.uniroma1.dis.jacov2.model.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "behaviors")
public class JsonBehaviors {
	@XmlElement(name = "behaviors")
	List<JsonBehavior> behaviors;
	
	public JsonBehaviors() {
		this.behaviors = new ArrayList<JsonBehavior>();
	}
	
	public void add(JsonBehavior behavior) {
		this.behaviors.add(behavior);
	}
}
