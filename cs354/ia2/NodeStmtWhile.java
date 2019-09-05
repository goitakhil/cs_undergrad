public class NodeStmtWhile extends Node {

	private NodeBoolExpr cond;
	private Node stmt;

	public NodeStmtWhile(NodeBoolExpr cond, Node stmt) {
		this.cond = cond;
		this.stmt = stmt;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		while (cond.eval(env) == 1.0) {
			stmt.eval(env);
		}
		return null;
	}

}
