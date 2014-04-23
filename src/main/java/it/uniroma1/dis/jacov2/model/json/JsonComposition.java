package it.uniroma1.dis.jacov2.model.json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.uniroma1.dis.jacov2.model.xml.XmlBehaviorState;
import it.uniroma1.dis.jacov2.model.xml.XmlComposition;
import it.uniroma1.dis.jacov2.model.xml.XmlCompositionState;
import it.uniroma1.dis.jacov2.model.xml.XmlTransition;

@XmlRootElement(name = "composition")
public class JsonComposition {
	@XmlElement
	boolean isRealizable;
	@XmlElement
	public List<JsonCompositionState> compositionStates;

	public JsonComposition() {}
	
	public JsonComposition(XmlComposition xmlComposition) {
		this.isRealizable = xmlComposition.isRealizable;
		this.compositionStates = new LinkedList<JsonCompositionState>();
		
		for (XmlCompositionState compositionState : xmlComposition.compositionStates) {
			JsonCompositionState jsonState = new JsonCompositionState();
			
			JsonTargetState jsonTargetState = new JsonTargetState(compositionState.targetState);
			JsonEnvironmentState jsonEnvState = new JsonEnvironmentState(compositionState.environmentState);
			List<JsonBehaviorState> jsonBehaviorStates = populateBehaviorStates(compositionState.behaviorStates);
			List<JsonTransition> jsonTransitions = populateTransitions(compositionState.transitions);
			
			jsonState.setTargetState(jsonTargetState);
			jsonState.setEnvironmentState(jsonEnvState);
			jsonState.setBehaviorStates(jsonBehaviorStates);
			jsonState.setTransitions(jsonTransitions);
			
			compositionStates.add(jsonState);
		}
	}
	
	private List<JsonBehaviorState> populateBehaviorStates(List<XmlBehaviorState> xmlBehaviorStates) {
		List<JsonBehaviorState> jsonBehaviorStates = new ArrayList<JsonBehaviorState>();
		
		for (XmlBehaviorState xmlState : xmlBehaviorStates) {
			JsonBehaviorState jsonState = new JsonBehaviorState(xmlState);
			jsonBehaviorStates.add(jsonState);
		}
		
		return jsonBehaviorStates;
	}
	
	private List<JsonTransition> populateTransitions(List<XmlTransition> xmlTransitions) {
		List<JsonTransition> jsonTransitions = new ArrayList<JsonTransition>();
		
		for (XmlTransition xmlState : xmlTransitions) {
			JsonTransition jsonState = new JsonTransition(xmlState);
			jsonTransitions.add(jsonState);
		}
		
		return jsonTransitions;
	}
}
