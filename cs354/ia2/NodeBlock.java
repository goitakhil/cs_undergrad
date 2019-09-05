/**
 * @author tylerbevan
 *
 */
public class NodeBlock extends Node {

	private Node stmt;
	private NodeBlock block;

	/**
	 * Constructor: Takes a statement and the following block as parameters.
	 * Block may be null.
	 * 
	 * @param stmt
	 * @param block
	 */
	public NodeBlock(Node stmt, NodeBlock block) {
		this.stmt = stmt;
		this.block = block;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		if (block != null) {
			stmt.eval(env);
			return block.eval(env);
		} else {
			return stmt.eval(env);
		}
	}

	/**
	 * Get next block, returns null if there isn't one.
	 * 
	 * @return block
	 */
	public NodeBlock nextBlock() {
		return block;
	}

}
