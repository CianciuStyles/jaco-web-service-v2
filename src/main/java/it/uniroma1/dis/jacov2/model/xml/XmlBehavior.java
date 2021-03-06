package it.uniroma1.dis.jacov2.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "behavior")
@XmlType(propOrder = {"name", "domainFilePath", "problemFilePath"})
public class XmlBehavior {
	String name;
	String domainFilePath;
	String problemFilePath;
	
	public XmlBehavior() {}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return this.name;
	}
	
	public void setDomainFilePath(String domainFilePath) {
		this.domainFilePath = domainFilePath;
	}
	
	@XmlElement(name = "domainFile")
	public String getDomainFilePath() {
		return this.domainFilePath;
	}
	
	public void setProblemFilePath(String problemFilePath) {
		this.problemFilePath = problemFilePath;
	}
	
	@XmlElement(name = "problemFile")
	public String getProblemFilePath() {
		return this.problemFilePath;
	}
}
