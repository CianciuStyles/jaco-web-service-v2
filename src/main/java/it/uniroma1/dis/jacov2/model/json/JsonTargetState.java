package it.uniroma1.dis.jacov2.model.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.uniroma1.dis.jacov2.model.xml.XmlTargetState;
import it.uniroma1.dis.jacov2.model.xml.XmlTerm;

@XmlRootElement
public class JsonTargetState {
	@XmlElement
	List<JsonTerm> terms;
	
	public JsonTargetState() {}
	
	public JsonTargetState(XmlTargetState xmlState) {
		terms = new ArrayList<JsonTerm>();
		
		for (XmlTerm xmlTerm : xmlState.terms) {
			JsonTerm term = new JsonTerm(xmlTerm);
			terms.add(term);
		}
	}
}
