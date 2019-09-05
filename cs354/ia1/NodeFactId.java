public class NodeFactId extends NodeFact {

	private String id;

	public NodeFactId(int pos, String id) {
		this.pos = pos;
		this.id = id;
	}

	@Override
	/*
	 * This method accounts for any number of unary minus operators.
	 * (non-Javadoc)
	 * @see Node#eval(Environment)
	 */
	public Double eval(Environment env) throws EvalException {
		Integer minusCount = 0;
		int i = 0;
		for (; i < id.length(); i++) {
			if (id.charAt(i) == '-') {
				minusCount++;
			} else {
				break;
			}
		}
		if (minusCount % 2 == 0) {
			return env.get(pos, id.substring(i));
		} else {
			return -(env.get(pos, id.substring(i)));
		}
	}

}
