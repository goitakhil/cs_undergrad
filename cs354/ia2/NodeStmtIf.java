public class NodeStmtIf extends Node {

	private NodeBoolExpr boolExpr;
	private Node stmt1;
	private Node stmt2;

	public NodeStmtIf(NodeBoolExpr boolExpr, Node stmt1, Node stmt2) {
		this.boolExpr = boolExpr;
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		if (boolExpr.eval(env) == 1) {
			return stmt1.eval(env);
		} else if (stmt2 != null) {
			return stmt2.eval(env);
		} else {
			return null;
		}
	}

}
