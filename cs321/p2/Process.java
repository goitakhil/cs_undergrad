package p2;


public class Process implements Comparable<Process> {
	private int myTime;
	private int priority;
	private int timeRequired;
	private int cyclesWaited;
	
	public Process(int priority, int timeRequired, int currentTime){
		this.priority = priority;
		myTime = currentTime;
		this.timeRequired = timeRequired;
		resetTimeNotProcessed();
	}

	public int getTimeRemaining() {
		return timeRequired;
	}

	public void reduceTimeRemaining() {
		timeRequired--;
		
	}

	public boolean finish() {
		return (timeRequired == 0);
	}

	public int getArrivalTime() {
		return myTime;
	}

	public int getPriority() {
		return priority;
	}

	public void resetTimeNotProcessed() {
		cyclesWaited = 0;
	}

	@Override
	public int compareTo(Process o) {
		if (priority > o.getPriority()){
			return 1;
		} else if (priority < o.getPriority()){
			return -1;
		} else if (myTime < o.getArrivalTime()){
			return 1;
		} else if (o.getArrivalTime() > o.getArrivalTime()){
			return -1;
		}
		return 0;
	}
	
	public void waitCycle(){
		cyclesWaited++;
	}
	
	public int getCyclesWaited(){
		return cyclesWaited;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}

}
