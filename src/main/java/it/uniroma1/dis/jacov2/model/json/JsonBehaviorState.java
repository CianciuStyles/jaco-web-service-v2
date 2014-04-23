package it.uniroma1.dis.jacov2.model.json;

import java.util.ArrayList;
import java.util.List;

import it.uniroma1.dis.jacov2.model.xml.XmlBehaviorState;
import it.uniroma1.dis.jacov2.model.xml.XmlTerm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonBehaviorState {
	@XmlElement
	String behaviorName;
	@XmlElement
	List<JsonTerm> terms;
	
	public JsonBehaviorState() {}
	
	public JsonBehaviorState(XmlBehaviorState xmlState) {
		this.behaviorName = xmlState.behaviorName;
		this.terms = new ArrayList<JsonTerm>();
		
		for (XmlTerm xmlTerm : xmlState.terms) {
			JsonTerm term = new JsonTerm(xmlTerm);
			terms.add(term);
		}
	}
}
