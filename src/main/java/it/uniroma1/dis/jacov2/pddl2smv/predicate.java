package it.uniroma1.dis.jacov2.pddl2smv;

/**
 * predicate class. Stores information regarding a single predicate
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class predicate {
		
	/**
	 * Initialize a predicate object based on its name
	 * 
	 * @param nm Name of the predicate
	 */
	public predicate(String nm) {
		name = nm;
	}
	
	/**
	 * Get the name of the predicate
	 * 
	 * @return string containing the name of the predicate
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the predicate
	 * 
	 * @param nm string defining the name of the predicate
	 */
	public void setName(String nm) { //FIXME: This method might be unsafe
		name = nm;
	}
	
	/**
	 * Set the initial state of the predicate
	 * 
	 * @param state boolean indicating the initial state of the predicate
	 */
	public void setInitState(boolean state) {
		initState = state;
	}
	
	/**
	 * Get the initial state of the predicate
	 * 
	 * @return boolean indicating the initial state of the predicate
	 */
	public boolean getInitState() {
		return initState;
	}
	
	private String name;
	
	private boolean initState;
}
