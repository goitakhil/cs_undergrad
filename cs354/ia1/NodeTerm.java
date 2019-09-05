public class NodeTerm extends Node {

	private NodeUnary unary;
	private NodeFact fact;
	private NodeMulop mulop;
	private NodeTerm term;

	public NodeTerm(NodeUnary unary, NodeFact fact, NodeMulop mulop, NodeTerm term) {
		this.unary = unary;
		this.fact = fact;
		this.mulop = mulop;
		this.term = term;
	}

	
	/**
	 * Add an additional term.
	 * @param term
	 */
	public void append(NodeTerm term) {
		if (this.term == null) {
			this.mulop = term.mulop;
			this.term = term;
			term.mulop = null;
		} else
			this.term.append(term);
	}

	public Double eval(Environment env) throws EvalException {
		if (unary == null){
			return term == null ? fact.eval(env) : mulop.op(term.eval(env), fact.eval(env));
		} else {
			return term == null ? unary.op(fact.eval(env)) : unary.op(mulop.op(term.eval(env), fact.eval(env)));
		}
	}

}
