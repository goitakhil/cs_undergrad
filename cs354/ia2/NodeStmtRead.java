import java.util.Scanner;

public class NodeStmtRead extends Node {

	private String id;

	public NodeStmtRead(String id) {
		this.id = id;
	}

	@Override
	public Double eval(Environment env) {
		Scanner kbd = new Scanner(System.in);
		System.out.printf("Enter value for %s: ", id);
		double val = kbd.nextDouble();
		env.put(id, val);
		kbd.close();
		return val;

	}

}
