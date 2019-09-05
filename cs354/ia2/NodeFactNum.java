public class NodeFactNum extends NodeFact {

	private String num;

	public NodeFactNum(String num) {
		this.num = num;
	}

	@Override
	public Double eval(Environment env) throws EvalException {
		return Double.parseDouble(num);
	}

}
