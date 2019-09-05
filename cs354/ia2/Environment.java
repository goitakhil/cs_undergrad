
import java.util.Hashtable;

/**
 * Keeps track of all ids declared in programs.
 * 
 * @author tylerbevan
 *
 */
public class Environment {
	private static Hashtable<String, Double> vars;

	/**
	 * Stores or updates a variable. Instantiates the table if needed.
	 * 
	 * @param var
	 * @param val
	 * @return
	 */
	public double put(String var, double val) {
		if (vars == null) {
			vars = new Hashtable<String, Double>();
		}
		vars.put(var, val);
		return val;
	}

	/**
	 * Gets the value of a variable. Instantiates the table if needed.
	 * 
	 * @param pos
	 * @param var
	 * @return
	 * @throws EvalException
	 */
	public double get(int pos, String var) throws EvalException {
		if (vars == null) {
			vars = new Hashtable<String, Double>();
		}
		if (!vars.containsKey(var)) {
			throw new EvalException(pos, "Variable not defined: " + var);
		} else {
			return vars.get(var);
		}
	}

}
