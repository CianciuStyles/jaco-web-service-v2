package it.uniroma1.dis.jacov2.pddl2smv;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * PDDL2SMV main class. Takes two pddl files (domain & problem) describing
 * a composition environment and generates the corresponding SMV module.
 * Requires two arguments, the first is the domain file and the second the 
 * problem file. An optional third argument specifies the file where the SMV
 * module will be written.
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class pddl2smv {
	public static void main(String outFilePath, String[] args, String[] behaviorNames) throws Exception {		
		if (args.length < 2) {
			System.out.println("Too few arguments, use: pddl2smv <env domain file>" +
					" <env problem file> <target domain file> <target problem file> " +
					"<s1 domain file> <s1 problem file>...");
			return;
		}
		
		outFile = outFilePath;
		
		int n_agents = (args.length)/2;
		System.out.println("Number of agents " + n_agents);
		
		smvWriter mysmv = new smvWriter(outFile);
		
		//ArrayList<pddl2smvListener> listarr = new ArrayList<pddl2smvListener>();
		
		for (int kk=0; kk < n_agents; kk=kk+1) {
			String currentAgent = null;
			switch (kk) {
				case 0:
					currentAgent = "Environment";
					break;
				case 1:
					currentAgent = "Target";
					break;
				default:
					currentAgent = behaviorNames[kk-2];
					break;
			}
			
			System.out.println("Started processing agent " + currentAgent);
			int ag_ind = kk*2;
			/**
			 * Prepare domain file for input
			 */
			ANTLRInputStream inputDom = new ANTLRFileStream(args[ag_ind]);

			/**
			 * Parse domain file
			 */
			ParseTree treeDom = getTreeFromFile(inputDom);
			
			ParseTreeWalker walker = new ParseTreeWalker();
			pddl2smvListener mylist = new pddl2smvListener();
			
			/**
			 * Create a walker to navigate the tree and collect the information
			 * to write the SMV module 
			 */
			walker.walk(mylist, treeDom);

			/**
			 * After walking through the parse tree of the domain file, repeat for the
			 * problem file
			 */
			ANTLRInputStream inputProb = new ANTLRFileStream(args[ag_ind+1]);
			ParseTree treeProb = getTreeFromFile(inputProb);
			
			walker.walk(mylist, treeProb);
			
			//listarr.add(mylist);
			System.out.println("Finished processing agent " + currentAgent);
			
			/**
			 * At this point all information to write the SMV module must be available.
			 * The output file is generated.
			 */
			if (kk==0) {
				mysmv.writeSMVStart(mylist, n_agents, behaviorNames);
				mysmv.writeSMV(mylist,"ServEnv");
			} else {
				if (kk==1) {
					mysmv.writeSMVTarget(mylist,"Target");
				} else {
					mysmv.writeSMVServ(mylist, kk-1, currentAgent);
				}
			}
		}

		//System.out.println(tree.toStringTree(parser)); // print LISP-style tree
		mysmv.close();
		System.out.println("File \'" + outFile + "\' succesfully generated."); // print LISP-style tree
	}

	/**
	 * 
	 * @param input The ANTLR input stream to parse
	 * @return the corresponding parse tree
	 */
	private static ParseTree getTreeFromFile(ANTLRInputStream input) {
		// create a lexer that feeds off of input CharStream
		pddlLexer lexer = new pddlLexer(input);
		// create a buffer of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// create a parser that feeds off the tokens buffer
		pddlParser parser = new pddlParser(tokens);
		return parser.pddlDoc(); // begin parsing at pddlDoc rule and return tree
	}
	
	private static String outFile;
}
