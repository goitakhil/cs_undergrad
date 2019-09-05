
public class NodeUnary extends Node {

	private NodeUnary unary;

	public NodeUnary(NodeUnary unary) {
		this.unary = unary;
	}

	public Double op(Double termVal) {
		if (unary == null) {
			return -(termVal);
		} else {
			return -(unary.op(termVal));
		}
	}

}
