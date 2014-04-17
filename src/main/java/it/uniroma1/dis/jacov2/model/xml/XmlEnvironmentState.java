package it.uniroma1.dis.jacov2.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlEnvironmentState {
	@XmlElement(name = "term")
	List<XmlTerm> terms;
	
	public XmlEnvironmentState() {
		terms = new ArrayList<XmlTerm>();
	}
	
	public void addTerm(String name, String value) {
		XmlTerm newTerm = new XmlTerm(name, value);
		terms.add(newTerm);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof XmlEnvironmentState))
			return false;
		
		XmlEnvironmentState state = (XmlEnvironmentState) obj;
		if (state.terms.size() != this.terms.size())
			return false;
		int size = this.terms.size();
		for (int i = 0; i < size; i++) {
			XmlTerm thisTerm = this.terms.get(i);
			XmlTerm stateTerm = state.terms.get(i);
			
			if (thisTerm.equals(stateTerm) == false)
				return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.terms.hashCode();
	}
}
