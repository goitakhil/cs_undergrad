public class NodeStmtWrite extends Node {

	private NodeExpr expr;

	public NodeStmtWrite(NodeExpr expr) {
		this.expr = expr;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		System.out.println(expr.eval(env));
		return null;
	}

}
