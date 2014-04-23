package it.uniroma1.dis.jacov2.model.json;

import it.uniroma1.dis.jacov2.model.xml.XmlEnvironmentState;
import it.uniroma1.dis.jacov2.model.xml.XmlTerm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonEnvironmentState {
	@XmlElement
	List<JsonTerm> terms;
	
	public JsonEnvironmentState() {}
	
	public JsonEnvironmentState(XmlEnvironmentState xmlState) {
		terms = new ArrayList<JsonTerm>();
		
		for (XmlTerm xmlTerm : xmlState.terms) {
			JsonTerm term = new JsonTerm(xmlTerm);
			terms.add(term);
		}
	}
}
