package it.uniroma1.dis.jacov2.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "term")
public class XmlTerm {
	@XmlAttribute
	String name;
	@XmlAttribute
	String value;
	
	public XmlTerm() {}
	
	public XmlTerm(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof XmlTerm))
			return false;
		
		XmlTerm term = (XmlTerm) obj;
		return this.name.equals(term.name) && this.value.equals(term.value);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.value.hashCode();
	}
}
