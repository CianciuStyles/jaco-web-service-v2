package it.uniroma1.dis.jacov2.pddl2smv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * smvWriter class. This is the class which actually writes the SMV modules.
 * It handles I/O and outputs the strings for each part of the module in the correct order. 
 * 
 * @author Valsamis Ntouskos
 * @version 0.1
 */
public class smvWriter {
	
	/**
	 * Default constructor. Outputs to console instead of a file
	 */
	public smvWriter() {
		outwr = new OutputStreamWriter(System.out);
		
	}
	
	/**
	 * When a name of a file is given, output is written in this file
	 * @param outfile
	 * @throws IOException
	 */
	public smvWriter(String outfile) throws IOException {
		outwr = new FileWriter(outfile);
		fwr = new PrintWriter(outwr,false);
	}
	public void close() {
		fwr.close();
	}
	public boolean writeSMVStart(pddl2smvListener mylist, int n_agents) throws IOException {
		fwr = new PrintWriter(outwr,false);
		StringBuilder txt = new StringBuilder();
		txt.append("MODULE main\n");
		txt.append("VAR\n");
		txt.append("  env: Env(sys.index);\n  sys: Sys;\n");
		txt.append("DEFINE\n");
		txt.append("  good := (sys.initial & env.initial) |\n\t!(env.failure);\n\n");
		
		txt.append("MODULE Sys\n");
		txt.append("VAR\n");
		txt.append("  index : 0.."+(n_agents-2)+";\n");
		txt.append("INIT\n");
		txt.append("  index = 0\n");
		txt.append("TRANS\n  case\n");
		txt.append("    index=0 : next(index)!=0;\n");
		txt.append("    index!=0 : next(index)!=0;\n  esac\n");
		txt.append("DEFINE\n");
		txt.append("  initial := (index=0);\n\n");
		
		txt.append("MODULE Env(index)\n");
		txt.append("VAR\n");
		txt.append("  operation : {" + startOp);
		
		Iterator<action> actit = mylist.getActIt();
		while (actit.hasNext()) {
			action actTrans = actit.next();
			txt.append(',' + actTrans.getName());
		}
		txt.append("};\n");
		txt.append("  senv : ServEnv(operation);\n");
		txt.append("  target : Target(operation);\n");
		for (int kk=1;kk<=n_agents-2;kk=kk+1) {
			txt.append("  s" + kk + " : Service" + kk + "(index,operation);\n");
		}
		
		txt.append("DEFINE\n");
		txt.append("  initial := (");
		txt.append("senv.initial & target.initial");
		for (int kk=1;kk<=n_agents-2;kk=kk+1) {
			txt.append(" & s" + kk + ".initial");
		}
		txt.append(" & operation=" + startOp + ");\n");
		
		txt.append("  failure := (");
		txt.append("senv.failure");
		for (int kk=1;kk<=n_agents-2;kk=kk+1) {
			txt.append(" | s" + kk + ".failure");
		}
		txt.append(") |\n\t(target.final & !(senv.final ");
		for (int kk=1;kk<=n_agents-2;kk=kk+1) {
			txt.append(" & s" + kk + ".final");
		}	
		txt.append("));\n");
		
		writeToFile(txt.toString());

		return true;
	}
	/**
	 * Method for writing the module. It takes the reference of a 
	 * pddl2smvListener object and calls its methods to retrieve 
	 * the strings for each part of the module.
	 * 
	 * @param mylist
	 * @return
	 * @throws IOException
	 */
	public boolean writeSMV(pddl2smvListener mylist, String modname) throws IOException {

		StringBuilder outtxtb = new StringBuilder();
		
		//Write the module header
		mylist.getHeaderText(outtxtb,modname,false);
				
		mylist.getPredicatesText(outtxtb);
		writeToFile(outtxtb.toString());
		
		outtxtb.delete(0, outtxtb.length());
		mylist.getTransitionsText(outtxtb,startOp,0);
		writeToFile(outtxtb.toString());

		outtxtb.delete(0, outtxtb.length());
		mylist.getDefinesText(outtxtb,startOp,0);
		writeToFile(outtxtb.toString());
		

		return true;
	}
	
	/**
	 * Method for writing the module. It takes the reference of a 
	 * pddl2smvListener object and calls its methods to retrieve 
	 * the strings for each part of the module.
	 * 
	 * @param mylist
	 * @return
	 * @throws IOException
	 */
	public boolean writeSMVTarget(pddl2smvListener mylist, String modname) throws IOException {

		StringBuilder outtxtb = new StringBuilder();
		
		//Write the module header
		mylist.getHeaderText(outtxtb,modname,false);
				
		mylist.getPredicatesText(outtxtb);
		writeToFile(outtxtb.toString());

		outtxtb.delete(0, outtxtb.length());
		outtxtb.append("  & operation="+startOp+"\n");
		mylist.getTransitionsText(outtxtb,startOp,-1);
		writeToFile(outtxtb.toString());

		outtxtb.delete(0, outtxtb.length());
		mylist.getDefinesText(outtxtb,startOp,0);
		writeToFile(outtxtb.toString());
		

		return true;
	}
	
	
	public boolean writeSMVServ(pddl2smvListener mylist, int modind) throws IOException {

		StringBuilder outtxtb = new StringBuilder();
		
		String modname = "Service" + modind;
		mylist.getHeaderText(outtxtb,modname,true);
		
		mylist.getPredicatesText(outtxtb);
		writeToFile(outtxtb.toString());
		
		outtxtb.delete(0, outtxtb.length());
		mylist.getTransitionsText(outtxtb,startOp,modind);
		writeToFile(outtxtb.toString());

		outtxtb.delete(0, outtxtb.length());
		mylist.getDefinesText(outtxtb,startOp,modind);
		writeToFile(outtxtb.toString());
		

		return true;
	}
	
	/**
	 * Method to write a file in the output stream
	 * 
	 * @param txt string to write
	 */
	private void writeToFile(String txt){
		fwr.println(txt);
	}
	
	private PrintWriter fwr;
	static private String startOp = "start_op";
	private OutputStreamWriter outwr;

}
