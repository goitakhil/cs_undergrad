public class NodeStmtBlock extends Node {

	private NodeBlock blk;

	public NodeStmtBlock(NodeBlock blk) {
		this.blk = blk;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		return blk.eval(env);
	}

}
