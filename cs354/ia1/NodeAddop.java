public class NodeAddop extends Node {

	private String addop;

	public NodeAddop(int pos, String addop) {
		this.pos = pos;
		this.addop = addop;
	}

	/**
	 * Evaluates the two numbers using the correct operator.
	 * @param o1
	 * @param o2
	 * @return
	 * @throws EvalException
	 */
	public Double op(Double o1, Double o2) throws EvalException {
		if (addop.equals("+"))
			return o1 + o2;
		if (addop.equals("-"))
			return o1 - o2;
		throw new EvalException(pos, "bogus addop: " + addop);
	}

}
