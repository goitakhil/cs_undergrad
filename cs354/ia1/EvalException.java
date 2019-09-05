@SuppressWarnings("serial")
public class EvalException extends Exception {

	private int pos;
	private String msg;

	/**
	 * Contructor for EvalException
	 * 
	 * @param pos
	 * @param msg
	 */
	public EvalException(int pos, String msg) {
		this.pos = pos;
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "eval error" + ", pos=" + pos + ", " + msg;
	}

}
