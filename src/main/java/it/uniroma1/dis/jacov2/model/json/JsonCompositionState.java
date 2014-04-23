package it.uniroma1.dis.jacov2.model.json;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonCompositionState {
	@XmlElement
	public JsonTargetState targetState;
	@XmlElement
	public JsonEnvironmentState environmentState;
	@XmlElement
	public List<JsonBehaviorState> behaviorStates;
	@XmlElement
	public List<JsonTransition> transitions;
	
	public JsonCompositionState() {}
	
	public void setTargetState(JsonTargetState targetState) {
		this.targetState = targetState;
	}
	
	public void setEnvironmentState(JsonEnvironmentState envState) {
		this.environmentState = envState;
	}
	
	public void setBehaviorStates(List<JsonBehaviorState> behaviorStates) {
		this.behaviorStates = behaviorStates;
	}
	
	public void setTransitions(List<JsonTransition> transitions) {
		this.transitions = transitions;
	}
}
