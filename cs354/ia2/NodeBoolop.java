public class NodeBoolop extends Node {

	private String boolop;

	public NodeBoolop(int pos, String boolop) {
		this.pos = pos;
		this.boolop = boolop;
	}

	/**
	 * Applies the proper operator the the numbers given.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 * @throws EvalException
	 */
	public Double op(Double o1, Double o2) throws EvalException {
		if (boolop.equals(">"))
			return o1 > o2 ? 1.0 : 0.0;
		if (boolop.equals("<"))
			return o1 < o2 ? 1.0 : 0.0;
		if (boolop.equals(">="))
			return o1 >= o2 ? 1.0 : 0.0;
		if (boolop.equals("<="))
			return o1 <= o2 ? 1.0 : 0.0;
		if (boolop.equals("<>"))
			return o1 != o2 ? 1.0 : 0.0;
		if (boolop.equals("=="))
			return o1 == o2 ? 1.0 : 0.0;

		throw new EvalException(pos, "bogus boolop: " + boolop);
	}

}