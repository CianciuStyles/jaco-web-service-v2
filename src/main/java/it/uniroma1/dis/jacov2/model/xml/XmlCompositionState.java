package it.uniroma1.dis.jacov2.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlCompositionState {
	@XmlElement
	public XmlTargetState targetState;
	@XmlElement
	public XmlEnvironmentState environmentState;
	@XmlElementWrapper(name = "behaviorStates")
	@XmlElement(name = "behaviorState")
	public List<XmlBehaviorState> behaviorStates;
	@XmlElementWrapper(name = "transitions")
	@XmlElement(name = "transition")
	public List<XmlTransition> transitions;
	
	public XmlCompositionState() {
		this.targetState = null;
		this.environmentState = null;
		this.behaviorStates = new ArrayList<XmlBehaviorState>();
		this.transitions = new ArrayList<XmlTransition>();
	}
	
	public void setTargetState(XmlTargetState targetState) {
		this.targetState = targetState;
	}
	
	public void setEnvironmentState(XmlEnvironmentState environmentState) {
		this.environmentState = environmentState;
	}
	
	public void setBehaviorStates(List<XmlBehaviorState> behaviorStates) {
		this.behaviorStates = behaviorStates;
	}
	
	public void addTransition(XmlTransition transition) {
		this.transitions.add(transition);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof XmlCompositionState))
			return false;
		
		XmlCompositionState state = (XmlCompositionState) obj;
		return this.targetState.equals(state.targetState) && this.environmentState.equals(state.environmentState) && this.behaviorStates.equals(state.behaviorStates);
	}
	
	@Override
	public int hashCode() {
		return targetState.hashCode() + environmentState.hashCode() + behaviorStates.hashCode();
	}
}
