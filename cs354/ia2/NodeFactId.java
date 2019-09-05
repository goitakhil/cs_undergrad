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
	 * 
	 * @see Node#eval(Environment)
	 */
	public Double eval(Environment env) throws EvalException {
		return env.get(pos, id);
	}

}
