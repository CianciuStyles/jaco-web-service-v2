package it.uniroma1.dis.jacov2.pddl2smv;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * pddl2smvListener class. This is the class which runs the parse tree and
 * collect all the necessary information to write an SMV module for the 
 * environment based on the pddl files.
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class pddl2smvListener extends pddlBaseListener {

	private HashMap<Integer, logicFormula> formMap;
	private int fLevel;
	
	private HashMap<String, predicate> predMap;
	private HashMap<String, action> actMap;
	
	private action currAct;
	private boolean currState;
	
	private boolean inPredDef = false;
	private boolean inActDefBody = false;
	private boolean inGoal = false;
	
	private boolean domReady = false;
	private boolean probReady = false;

	private StringBuilder goalBldr;
	
	static private String opName = "operation";
	
	public Iterator<action> getActIt(){
		return actMap.values().iterator();
	}
	/**
	 * Default constructor, initializes the necessary data structures
	 */
	public pddl2smvListener(){
		predMap = new HashMap<String,predicate>();
		actMap = new HashMap<String,action>();
		goalBldr = new StringBuilder();
	}
	
	/**
	 * Overrides default enter rule for 'PredicatesDef'. 
	 * It turns on the flag inPredDef.
	 */
	@Override
	public void enterPredicatesDef(pddlParser.PredicatesDefContext ctx) {
		inPredDef = true;
	}
	
	/**
	 * Overrides default enter rule for 'Predicates'. 
	 * While in a predicate definition stores each predicate 
	 * into a Map data structure with key the name of the predicate
	 * and value a predicate object. Otherwise does nothing.
	 */
	@Override
	public void enterPredicate(pddlParser.PredicateContext ctx) {
		if(inPredDef) {
			predMap.put(ctx.getText(),new predicate(ctx.getText()));
		}
	}
	
	/**
	 * While exiting 'PredicatesDef' turns of respective flag. At this point
	 * all predicate objects must have been initialized
	 */
	@Override
	public void exitPredicatesDef(pddlParser.PredicatesDefContext ctx) {
		inPredDef = false;
	}
	
	/**
	 * While entering an 'ActionSymbol' rule create a new action object.
	 * Store a reference of this object as the current object been processed.
	 */
	@Override
	public void enterActionSymbol(pddlParser.ActionSymbolContext ctx) {
		action act = new action(ctx.getChild(0).getText());
		actMap.put(act.getName(), act);
		currAct = act;
		fLevel = 0;
		formMap = new HashMap<Integer, logicFormula>();
		formMap.put(fLevel, new logicFormula(logicFormula.formulaType.OUTER));
	}
	
	/**
	 * While entering an 'ActionDefBody' rule, turn the respective flag on (true).
	 * Restore value of currState to true. currState is used to keep track of negations
	 * in preconditions for nested atoms.
	 */
	@Override
	public void enterActionDefBody(pddlParser.ActionDefBodyContext ctx) {
		inActDefBody = true;
		currState = true;
	}
	
	/**
	 * Rule for processing a 'GoalDesc'. According to different parts of the file different
	 * procedures are followed.
	 */
	@Override
	public void enterGoalDesc(pddlParser.GoalDescContext ctx) {
		/**
		 * In 'ActionDefBody' the preconditions and effects of an action are stored in 
		 * the respective action objects
		 */
		if (inActDefBody) {
			/**
			 * The number of children indicates if the formula is negated or not.
			 * NOTE: This is based on the assumption that only conjunctive preconditions
			 * are allowed!
			 */
			if (ctx.getChildCount()>1) {
				if (ctx.getChild(1).getText().equals("not")) {
					currState = !currState; //When entering a negated formula update the current state
				}
			} else {
				/**
				 * When only one child exist, and the predicate exists, add a precondition term
				 * accompanied with its state
				 */
				ParseTree predtree = ctx.getChild(0).getChild(1);
				if (predMap.containsKey(predtree.getText())) {
					currAct.addPrecond(predMap.get(predtree.getText()), currState);
				} else {
					String ex_msg = "ERROR: Action definition references nondefined predicate \'" + predtree.getText()+'\'';
					throw new RuntimeException(ex_msg); //FIXME: Write Exception Class
				}
			}
			/**
			 *  In 'Goal', the goal formula is being read. The formula is rewritten in a string
			 *  according to SMV format, thus replacing 'not' with '!', 'and' with intermediate '&'s
			 *  and 'or' with intermediate '|'s. 
			 */			
		} else	if (inGoal) {
			if (ctx.getChildCount()>1) {
				String opname = ctx.getChild(1).getText();
				if (opname.equals("not")) {
					goalBldr.append('!');
				} else if (opname.equals("or")) {
					goalBldr.append('(');
				} else if (opname.equals("and")) {
					goalBldr.append('(');
				} else { //Currently only not, or and and goal operators are supported
					String ex_msg = "ERROR: Operator " + '\'' + ctx.getChild(1).getText() + '\'' + " in goal, not yet implemented";
					throw new RuntimeException(ex_msg); //FIXME: Write Exception Class
				}
			} else {
				ParseTree predtree = ctx.getChild(0).getChild(1);
				if(predtree.getText().equals("TRUE")) {
					return;
				}
				if (predMap.containsKey(predtree.getText())) { //check if predicates exist, otherwise throw exception
					goalBldr.append('(' + predMap.get(predtree.getText()).getName() + ')');
					//goalMap.put(predMap.get(predtree.getText()), currState);
				} else {
					String ex_msg = "ERROR: Action definition references nondefined predicate \'" + predtree.getText()+'\'';
					throw new RuntimeException(ex_msg); //FIXME: Write Exception Class
				}
			}
		}
	}
	
	/**
	 * Rule for exiting 'GoalDesc'.  According to different parts of the file different
	 * procedures are followed.
	 */
	@Override
	public void exitGoalDesc(pddlParser.GoalDescContext ctx) {
		/**
		 * In 'ActionDefBody' the preconditions and effects of an action are stored in 
		 * the respective action objects
		 */
		if (inActDefBody) {
			if (ctx.getChildCount()>1) {
				if (ctx.getChild(1).getText().equals("not")) {
					currState = !currState; //Keep track of negations
				}
			}
			/**
			 *  In 'Goal', the goal formula is being read. The formula is rewritten in a string
			 *  according to SMV format.
			 *  @see #enterGoalDesc
			 */		
		} else if (inGoal) {
			int numChild = ctx.getParent().getChildCount();
			if (numChild>1) {
				String opname = ctx.getParent().getChild(1).getText();
				if (opname.equals("or")) {
					if (ctx.equals(ctx.getParent().getChild(numChild-2))) {
						goalBldr.append(')');
					} else {
						goalBldr.append('|');
					}
				} else if (opname.equals("and")) {
					if (ctx.equals(ctx.getParent().getChild(numChild-2))) {
						goalBldr.append(')');
					} else {
						goalBldr.append('&');
					}
				} /*else {
					String ex_msg = "ERROR: Operator " + '\'' + ctx.getParent().getChild(1).getText() + '\'' + " in goal, not yet implemented";
					throw new RuntimeException(ex_msg); //FIXME: Write Exception Class
				}*/
			}
		}
	}

	public void allEffectsExit() {
		fLevel--;
		try {
			formMap.get(fLevel).addSubFormula(formMap.get((fLevel+1)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void enterEffect(pddlParser.EffectContext ctx) {
		if (ctx.getChildCount()>1) {
			fLevel++;
			if (ctx.getChild(1).getText().equals("and")) {
				formMap.put(fLevel, new logicFormula(logicFormula.formulaType.CONJUCTIVE));
			}
		}
	}
	
	@Override
	public void exitEffect(pddlParser.EffectContext ctx) {
		if (ctx.getChildCount()>1) {
			allEffectsExit();
		}
	}
	
	@Override
	public void enterCEffect(pddlParser.CEffectContext ctx) {
		if (ctx.getChildCount()>1) {
			fLevel++;
			//System.out.println(ctx.getChild(1).getText());
			if (ctx.getChild(1).getText().equals("oneof")) {
				formMap.put(fLevel, new logicFormula(logicFormula.formulaType.DISJUNCTIVE));
			}
		}
	}
	
	@Override
	public void exitCEffect(pddlParser.CEffectContext ctx) {
		if (ctx.getChildCount()>1) {
			allEffectsExit();
		}
	}
	
	/**
	 * Rule for entering 'PEffect'. It stores the predicate affected by action effects
	 * with the corresponding state. Based on the grammar, negations are allowed only immediately 
	 * outside atoms
	 */
	@Override
	public void enterPEffect(pddlParser.PEffectContext ctx) {
		if (ctx.getChildCount()>1) {
			fLevel++;
			/**
			 * If the atom is negated add it to the list of predicates the action disables 
			 */
			if (ctx.getChild(1).getText().equals("not")) {
				formMap.put(fLevel, new logicFormula(logicFormula.formulaType.NEGATED));
				fLevel++;
				formMap.put(fLevel, new logicFormula(logicFormula.formulaType.ATOMIC));
				try {
					formMap.get(fLevel).setLiteral(ctx.getChild(2).getChild(1).getText());
					//System.out.println(formMap.get(fLevel).getLiteral());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				allEffectsExit();
			}
			/**
			 * Otherwise add it to the list of predicates the action enable
			 */
		} else {
			fLevel++;
			formMap.put(fLevel, new logicFormula(logicFormula.formulaType.ATOMIC));
			try {
				formMap.get(fLevel).setLiteral(ctx.getChild(0).getChild(1).getText());
				//System.out.println(formMap.get(fLevel).getLiteral());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override 
	public void exitPEffect(pddlParser.PEffectContext ctx) {
		allEffectsExit();
	}
	
	/**
	 * While entering an 'ActionDefBody' rule, turn the respective flag off (false).
	 */
	@Override
	public void exitActionDefBody(pddlParser.ActionDefBodyContext ctx) {
		inActDefBody = false;
		currAct.setFormula(formMap.get(0));
		
		/*try {
			System.out.println(formulaPrinter(currAct.getFormula()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	
	/**
	 * While exiting a 'Domain' rule, we have finished walking the parse tree of the
	 * domain file.
	 */
	@Override
	public void exitDomain(pddlParser.DomainContext ctx) {
		domReady = true;
	}
	
	/**
	 * Rule for entering 'Goal'. Turns on respective flag
	 */
	@Override 
	public void enterGoal(pddlParser.GoalContext ctx) {
		inGoal = true;
	}
	
	/**
	 * Rule for exiting 'Goal'. Turns off respective flag
	 */
	@Override 
	public void exitGoal(pddlParser.GoalContext ctx) {
		inGoal = false;
	}
	
	/**
	 * Rule for entering 'InitEl'. Reads and sets the initial values of all the predicates
	 */
	@Override
	public void enterInitEl(pddlParser.InitElContext ctx) {
		String predNm;
		
		/**
		 * Find if predicate is negated or not at initialization
		 */
		boolean initState = true;
		if (ctx.getChild(0).getChildCount()>1) {
			predNm = ctx.getChild(0).getChild(2).getChild(1).getChild(0).getText();
			initState=false;
		} else {
			predNm = ctx.getChild(0).getChild(0).getChild(1).getChild(0).getText();
		}
		
		/**
		 * Store the state to the respective predicate object
		 */
		if (predMap.containsKey(predNm)) {
			predicate predInit = (predMap.get(predNm));
			predInit.setInitState(initState);
		} else {
			String ex_msg = "ERROR: Action definition references nondefined predicate \'" + predNm +'\'';
			throw new RuntimeException(ex_msg); //FIXME: Write Exception Class
		}
	}
	
	/**
	 * While exiting a 'problemDomain' rule, we have finished walking the parse tree of the
	 * problem file.
	 */
	@Override
	public void exitProblemDomain(pddlParser.ProblemDomainContext ctx) {
		probReady = true;
	}
	
	/**
	 * 
	 * 
	 * @param txt a StringBuilder object for building the output string
	 * @return a boolean indicating if the operation can be done
	 */
	public boolean getHeaderText(StringBuilder txt, String modname, boolean hasind) {
		if (!isReady()) {
			return false;
		}
		
		if (hasind) {
			txt.append("MODULE " + modname + "(index," + opName + ")\n");
		} else {
			txt.append("MODULE " + modname + '(' + opName + ")\n");
		}
		return true;
	}
	
	/**
	 * This method builds a string for the declaration of all the predicates as boolean
	 * variables in the SMV module
	 * 
	 * @param txt a StringBuilder object for building the output string
	 * @return a boolean indicating if the operation can be done
	 */
	public boolean getPredicatesText(StringBuilder txt) {
		if (!isReady()) {
			return false;
		}
		
		Collection<predicate> predCol = predMap.values();
		Iterator<predicate> it=predCol.iterator();
		txt.append("VAR\n");
		while(it.hasNext()) {
			txt.append("  " +   it.next().getName() + " : boolean;\n"); 
		}
		
		//Declare each predicate
		boolean firstPred = true;
		it=predCol.iterator();
		txt.append("INIT\n");
		while(it.hasNext()) {
			if (firstPred) {
				txt.append("  !"+it.next().getName());
				firstPred = false;
			} else {
				txt.append(" &\n  !"+it.next().getName());
			}
		}
		return true;
	}
	
	/**
	 * This method builds a string for defining all possible transitions  in the SMV module
	 * 
	 * @param txt a StringBuilder object for building the output string
	 * @return a boolean indicating if the operation can be done
	 */
	public boolean getTransitionsText(StringBuilder txt, String startOp, int modind) {
		if (!isReady()) {
			return false;
		}
		
		txt.append("TRANS\n");
		if (modind < 0) {
			txt.append("\tnext("+opName+")!="+startOp+"\n&\n\tnext(failure)=FALSE\n&\n");
		}
		txt.append("  case\n");	//Single 'case' 'esac' block. This is required for compatibility with JTLV
		
		//Define the initial values as the effects of the 'start' operation, initializing all SMV modules
		boolean firstPred = true;
		Iterator<predicate> itpred = predMap.values().iterator();
		if (modind > 0) {
			txt.append("\tindex=0 & " + opName + " = " + startOp + " : ");
		} else {
			txt.append('\t' + opName + " = " + startOp + " : ");
		}
		while (itpred.hasNext()) {
			if (!firstPred) {
				txt.append(" & ");
			}
			predicate predTrans = itpred.next();  
			txt.append("next(" + predTrans.getName() + ")=");
			if  (predTrans.getInitState()==true) {
				txt.append("TRUE");
			} else {
				txt.append("FALSE");
			}
			firstPred=false;
		}
		txt.append(";\n");
		
		if (modind > 0) {
			firstPred = true;
			itpred = predMap.values().iterator();
			txt.append("\tindex != " + modind + " : ");
			while (itpred.hasNext()) {
				if (!firstPred) {
					txt.append(" & ");
				}
				predicate predTrans = itpred.next();  
				txt.append("next(" + predTrans.getName() + ") = " + predTrans.getName());
				firstPred=false;
			}
			txt.append(";\n");
		}
		// TODO FIX COMMENT
		/**
		 * Then iterate on each action and write the status of each predicate in the following state.
		 * Unchanged predicates keep current state (explicitly), the rest are becoming equal to TRUE or FALSE
		 * according to action effects. 
		 * For non-deterministic effects some predicates may take any of these values
		 */
		Iterator<action> itact = actMap.values().iterator();
		while (itact.hasNext()) {
			action actTrans = itact.next();  
			txt.append('\t' + opName + " = " + actTrans.getName() +" : ");

			try {
				String part = formulaPrinter(actTrans.getFormula());
				if (part.isEmpty()) {
					firstPred = true;
				} else {
					firstPred = false;
				}
				txt.append(part);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 

			for (predicate predTrans : predMap.values()) {
				if (!actTrans.getFormula().contains(predTrans.getName())) {
					if (!firstPred) {
						txt.append(" & ");
					}
					txt.append("next(" + predTrans.getName() + ")="+predTrans.getName());
					firstPred = false;
				}
			}
			txt.append(";\n");
		}
		
		/*if (modind <=0 ) {
			firstPred = true;
			itpred = predMap.values().iterator();
			txt.append("\tTRUE : ");
			while (itpred.hasNext()) {
				if (!firstPred) {
					txt.append(" & ");
				}
				predicate predTrans = itpred.next();  
				txt.append("next(" + predTrans.getName() + ") = " + predTrans.getName());
				firstPred=false;
			}
			txt.append(";\n");
		}*/
		txt.append("  esac");
		return true;
	}
	
	
	/**
	 * Builds the string for the DEFINE part of SMV module. Calls functions
	 * to build strings for initialization, failure and final states conditions 
	 * 
	 * @param txt a StringBuilder object for building the output string
	 * @return a boolean indicating if the operation can be done
	 */
	public boolean getDefinesText(StringBuilder txt, String startOp, int modind) {
		if (!isReady()) {
			return false;
		}
		txt.append("DEFINE\n");
		buildInitText(txt,startOp);
		buildFailureText(txt,modind);
		buildFinalText(txt);
		return true;
	}
	
	/**
	 * Build the string for the failure states. This in practice encodes action preconditions.
	 * Applying an operation at a state where the preconditions are not met, leads to failure
	 * 
	 * @param txt a StringBuilder object for building the output string
	 */
	private void buildFailureText(StringBuilder txt, int modind) {
		Iterator<action> itact = actMap.values().iterator();
		
		txt.append("  failure := \n  ");
		if (modind > 0) {
			txt.append("  index=" + modind + " & ( !(operation in {");
			boolean firstactloc = true;
			while (itact.hasNext()) {
				action actTrans = itact.next(); 
				if (!firstactloc) {			
					txt.append(",");
				}
				txt.append(actTrans.getName());
				firstactloc = false;
			}
			txt.append("}) | \n");
			itact = actMap.values().iterator();
		}
		boolean firstact = true;
		while (itact.hasNext()) {
			action act = itact.next();
			if (act.getPrecond().size()==0) {
				continue;
			}
			txt.append('\t');
			if (!firstact) {			
				txt.append(" | ");
			}
			txt.append('(' + opName  + '=' + act.getName() + " & !(");
			boolean firstPred = true;
			for (Map<predicate, Boolean> pair : act.getPrecond()) {
				if (!firstPred) {			
					txt.append(" & ");
				}
				Iterator<Map.Entry<predicate, Boolean>> itpair = pair.entrySet().iterator();
				while (itpair.hasNext()) {
					Map.Entry<predicate, Boolean> pairEnt = itpair.next();
					if (pairEnt.getValue()==false) {
						txt.append('!');
					}
					txt.append(pairEnt.getKey().getName());
				}
				firstPred = false;
			}
			
			txt.append(") )\n");
			firstact = false;
		}
		if (modind > 0) {
			txt.append("\t);\n");
		} else {
			txt.append("\t;\n");
		}
	}
	
	/**
	 * Build the string for the initial state. This just specifies the name of the start operation.
	 * The actual predicates are initialized by the transition rules.
	 * @see #getTransitionsText
	 * 
	 * @param txt a StringBuilder object for building the output string
	 */
	private void buildInitText(StringBuilder txt, String startOp) {
		txt.append("  initial := ");
		txt.append(opName + '='+ startOp + ";\n");
	}
	
	/**
	 * Build the final states string. This method just prints the goal string parsed while walking
	 * the parse tree of the problem pddl file.
	 * @see #enterGoalDesc
	 * 
	 * @param txt a StringBuilder object for building the output string
	 */
	private void buildFinalText(StringBuilder txt) {
		txt.append("  final := ");
		if (goalBldr.length()==0) {
			goalBldr.append("TRUE");
		}
		txt.append(goalBldr.toString());
		txt.append(";\n");
	}
	
	/**
	 * Query whether both files have been parsed and walked
	 * 
	 * @return true if SMV module can be generated, false otherwise
	 */
	private boolean isReady() {
		return domReady & probReady;
	}
	
	public static String formulaPrinter(logicFormula f) throws Exception {
		StringBuilder txt = new StringBuilder();
		logicFormula.formulaType ftype = f.getType();
		Iterator<logicFormula> it = f.getFormulaIterator();
		switch (ftype) {
		case OUTER:
			while(it.hasNext()) {
				txt.append(formulaPrinter(it.next()));
			}
			break;
		case CONJUCTIVE:
			txt.append("(");
			while(it.hasNext()) {
				txt.append(formulaPrinter(it.next()));
				if (it.hasNext()) {
					txt.append(" & ");
				}
			}
			txt.append(")");
			break;
		case DISJUNCTIVE:
			txt.append("(");
			while(it.hasNext()) {
				txt.append(formulaPrinter(it.next()));
				if (it.hasNext()) {
					txt.append(" | ");
				}
			}
			txt.append(")");
			break;
		case NEGATED:
			txt.append("next("+it.next().getLiteral()+")=FALSE");
			break;
		case ATOMIC:
			txt.append("next("+f.getLiteral()+")=TRUE");
			break;

		default:
			break;
		}
		return txt.toString();
	}
	
}
