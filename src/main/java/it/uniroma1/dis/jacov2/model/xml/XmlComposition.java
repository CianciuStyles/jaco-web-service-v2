package it.uniroma1.dis.jacov2.model.xml;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "composition")
public class XmlComposition {
	@XmlElement
	boolean isRealizable;
	@XmlElement(name = "compositionState")
	public List<XmlCompositionState> compositionStates;

	public XmlComposition() {}

	public XmlComposition(boolean isRealizable) {
		this.isRealizable = isRealizable;
		this.compositionStates = new LinkedList<XmlCompositionState>();
	}

	public void addCompositionState(XmlCompositionState compositionState) {
		this.compositionStates.add(compositionState);
	}
}
