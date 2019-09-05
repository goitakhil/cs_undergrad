package p2;

import java.util.Random;

public class ProcessGenerator {
	private double probability;
	private Random rng;
	public ProcessGenerator(double probability) {
		this.probability = probability;
		rng = new Random();
	}

	public boolean query() {
		if (probability > rng.nextDouble()){
			return true;
		} else {
			return false;
		}
	}

	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		Process newProc = new Process(rng.nextInt(maxLevel - 1) + 1, rng.nextInt(maxProcessTime - 1) + 1, currentTime);
		return newProc;
	}

}
