public class NodeStmt extends Node {

	private NodeAssn assn;

	public NodeStmt(NodeAssn assn) {
		this.assn = assn;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		return assn.eval(env);
	}

}
