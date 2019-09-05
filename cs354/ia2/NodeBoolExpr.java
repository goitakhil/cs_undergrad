public class NodeBoolExpr extends Node {

	private NodeExpr expr1;
	private NodeBoolop boolop;
	private NodeExpr expr2;

	public NodeBoolExpr(NodeExpr expr1, NodeBoolop boolop, NodeExpr expr2) {
		this.expr1 = expr1;
		this.boolop = boolop;
		this.expr2 = expr2;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		return boolop.op(expr1.eval(env), expr2.eval(env));
	}

}