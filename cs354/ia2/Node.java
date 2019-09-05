// This class, and its subclasses,
// collectively model parse-tree nodes.
// Each kind of node can be eval()-uated.

public abstract class Node {

	protected int pos = 0;

	/**
	 * Evaluates the node mathematically depending on the type.
	 * 
	 * @param env
	 * @return
	 * @throws EvalException
	 */
	public Double eval(Environment env) throws EvalException {
		throw new EvalException(pos, "cannot eval() node!");
	}

}
