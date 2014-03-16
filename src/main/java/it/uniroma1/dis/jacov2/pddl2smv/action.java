package it.uniroma1.dis.jacov2.pddl2smv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * action class. Stores information regarding a specific action
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class action {
	
	/**
	 * Initialize an action object based on its name
	 * 
	 * @param nm Name of the action
	 */
	public action(String nm) {
		name = nm;
		precond = new HashSet<Map<predicate, Boolean>>();
	}
	
	/**
	 * Get the name of the action
	 * 
	 * @return string containing the name of the action
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the action
	 * 
	 * @param nm string defining the name of the action 
	 */
	public void setName(String nm) {//FIXME: This method might be unsafe
		name = nm;
	}
	
	/**
	 * This function stores the action preconditions in a HashMap 
	 *  
	 * @param predPrec predicate appearing in the preconditions
	 * @param status state of the predicate
	 */
	public void addPrecond(predicate predPrec, boolean status) {
		Map<predicate, Boolean> map = new HashMap<predicate, Boolean>();
		map.put(predPrec,status);
		precond.add(map);
	}
	
	/**
	 * Get the action preconditions 
	 * @return a set of HashMaps encoding the action preconditions
	 */
	public  Set<Map<predicate, Boolean>> getPrecond() {
		return precond;
	}
	
	public logicFormula getFormula() {
		return actLogForm;
	}
	
	public void setFormula(logicFormula f) {
		actLogForm = f;
	}
	private String name;
	
	private Set<Map<predicate, Boolean>> precond;
	private logicFormula actLogForm;
}
