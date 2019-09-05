public class NodeMulop extends Node {

	private String mulop;

	public NodeMulop(int pos, String mulop) {
		this.pos = pos;
		this.mulop = mulop;
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
		if (mulop.equals("*"))
			return o1 * o2;
		if (mulop.equals("/"))
			return o1 / o2;
		throw new EvalException(pos, "bogus mulop: " + mulop);
	}

}
