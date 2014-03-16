package it.uniroma1.dis.jacov2.pddl2smv;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * logicFormula class. Stores information regarding a specific logical formula
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class logicFormula {
	
	public enum formulaType {
		OUTER, ATOMIC, CONJUCTIVE, DISJUNCTIVE, NEGATED
	}
	
	/**
	 * Initialize a logicFormula object
	 * 
	 * @param nm Name of the action
	 */
	public logicFormula(formulaType type) {
		m_type = type;
		m_literal = null;
		m_formulaArray = new ArrayList<logicFormula>();
	}
	
	public formulaType getType() {
		return m_type;
	}
	
	public void setLiteral(String lit) throws Exception {
		if (m_type != formulaType.ATOMIC) {
			String ex_msg = "Cannot set literal in a non atomic formula";
			throw new Exception(ex_msg);
		}
		m_literal = lit;
	}
	
	public String getLiteral() throws Exception {
		if (m_type != formulaType.ATOMIC) {
			String ex_msg = "Cannot return literal of a non atomic formula";
			throw new Exception(ex_msg);
		}
		return m_literal;
	}
	
	public void addSubFormula(logicFormula a) throws Exception{
		if (m_type == formulaType.ATOMIC) {
			String ex_msg = "Cannot add formula at an atomic formula";
			throw new Exception(ex_msg);
		}
		m_formulaArray.add(a);
	}
	
	public Iterator<logicFormula> getFormulaIterator() {
		return m_formulaArray.iterator();
	}
	
	public boolean contains(String nm) {
		if (m_type == formulaType.ATOMIC) {
			return m_literal.equals(nm);
		} else {
			boolean rec = false;
			Iterator<logicFormula> it = m_formulaArray.iterator();
			while (it.hasNext()) {
				rec |= it.next().contains(nm);
			}
			return rec;
		}
	}

	private formulaType m_type;
	private ArrayList<logicFormula> m_formulaArray;
	private String m_literal; 
}
