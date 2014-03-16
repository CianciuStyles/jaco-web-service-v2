package it.uniroma1.dis.jacov2.composition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.Vector;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDD.BDDIterator;
import edu.wis.jtlv.env.Env;
import edu.wis.jtlv.env.module.SMVModule;
import edu.wis.jtlv.lib.FixPoint;

public class Composition {

	private String baseFilePath;

	public Composition(String baseFilePath) {
		this.baseFilePath = baseFilePath;
	}

	public void doComposition() {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(baseFilePath + File.separator + "Composition.txt")));
			System.out.println("Calculating composition...");

			Env.resetEnv();
			Env.loadModule(baseFilePath + File.separator + "Composition.smv");
			SMVModule main = (SMVModule) Env.getModule("main");
			SMVModule env = (SMVModule) Env.getModule("main.env");
			SMVModule sys = (SMVModule) Env.getModule("main.sys");

			BDD good = main.getDefine("good", false).getBDDVal();

			BDD initial = (env.initial()).and(sys.initial());
			// System.out.println(initial.toStringWithDomains(Env.stringer));

			// calcolo winning states
			BDD win = Env.TRUE();
			FixPoint<BDD> fp = new FixPoint<BDD>();
			while (fp.advance(win)) {
				BDD cox = env.yieldStates(sys, win);
				win = good.and(cox);
			}

			BDD counter_example = initial.and(win.not());
			if (!counter_example.isZero()) {
				pw.println("non realizzabile");
			} else {
				pw.println("realizzabile");

				// System.out.println("stampa strategia");

				Stack<BDD> stackinit = new Stack<BDD>();
				Vector<RawState> auto_states = new Vector<RawState>();

				BDDIterator initial_states = initial.iterator(env
						.moduleUnprimeVars().union(sys.moduleUnprimeVars()));

				BDD trans12 = env.trans().and(sys.trans());
				BDD trans = win.and(trans12).and(Env.prime(win));

				while (initial_states.hasNext()) {
					BDD s = (BDD) initial_states.next();
					stackinit.push(s);
				}
				while (!stackinit.isEmpty()) {
					BDD s = stackinit.pop();
					// recupera lo stato corrente oppure lo crea se non esiste
					RawState new_state = new RawState(s);
					int index = auto_states.indexOf(new_state);
					if (index == -1) {
						auto_states.add(new_state);
					} else {
						new_state = auto_states.elementAt(index);
					}
					// calcola i successori dello stato corrente
					BDD succ = Env.unprime(s.and(trans).exist(
							Env.globalUnprimeVars()));
					BDDIterator it = succ.iterator(Env.globalUnprimeVars());
					while (it.hasNext()) {
						// per ogni successore recupera lo stato successore
						// oppure se non esiste lo crea e lo aggiunge allo stack
						BDD i = (BDD) it.next();
						// System.out.println(i.toStringWithDomains(Env.stringer));
						RawState succ_state = new RawState(i);
						int index_succ = auto_states.indexOf(succ_state);
						if (index_succ == -1) {
							auto_states.add(succ_state);
							stackinit.push(i);
						} else {
							succ_state = auto_states.elementAt(index_succ);
						}
						// aggiunge allo stato corrente l'indice dello stato
						// successore
						index_succ = auto_states.indexOf(succ_state);
						new_state.add_successor_index(index_succ);
					}
				}

				// Stampa
				// System.out.println("Automaton states");
				// pw.println("Automation states");

				// stampa gli stati
				for (int v = 0; v < auto_states.size(); v++) {
					// System.out.println("State "+v+" : "+
					// auto_states.elementAt(v).stateToString());
					pw.println("State " + v + " : "
							+ auto_states.elementAt(v).stateToString());
				}
				int transitions = 0;
				// stampa le transizioni
				for (int v = 0; v < auto_states.size(); v++) {
					RawState s = auto_states.elementAt(v);
					transitions = transitions + s.get_transition_number();
					// System.out.println("From "+v+" to "+
					// s.successorToString());
					pw.println("From " + v + " to " + s.successorToString());
				}

				// System.out.println("Automaton has "+ auto_states.size()+
				// " states and " + transitions+ " transitions");
				// pw.println("Automaton has "+ auto_states.size()+
				// " states and " + transitions+ " transitions");
			}

			pw.flush();
			pw.close();

			System.out.println("Calculating composition...DONE!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class RawState {
	private BDD state;
	private Vector<Integer> succ;

	public RawState(BDD state) {
		this.state = state;
		succ = new Vector<Integer>();
	}

	public void add_successor_index(int to_add) {
		succ.add(to_add);
	}

	public int get_transition_number() {
		return succ.size();
	}

	public boolean equals(Object other) {
		if (!(other instanceof RawState))
			return false;
		RawState other_raw = (RawState) other;
		return this.state.equals(other_raw.state);
	}

	public String stateToString() {
		String res = "State " + " -> "
				+ state.toStringWithDomains(Env.stringer) + "\n";
		return res;
	}

	public String successorToString() {
		String res = "";
		for (int i = 0; i < succ.size(); i++)
			res = res + succ.elementAt(i) + " ";
		return res;
	}
}
