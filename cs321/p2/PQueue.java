package p2;

public class PQueue {
	private MaxHeap<Process> heap;
	
	public PQueue(){
		heap = new MaxHeap<Process>();
	}
	public void enPQueue(Process p) {
		heap.addObject(p);
	}

	public boolean isEmpty() {
		if (heap.getSize() == 0){
			return true;
		} else {
			return false;
		}
	}

	public Process dePQueue() {
		return (Process) heap.removeObject(0);
	}

	public void update(int timeToIncrementPriority, int maxLevel) {
		for (int i = 0; i < heap.getSize(); i++){
			Process currentProc = (Process) heap.getObject(i);
			if (currentProc.getCyclesWaited() > timeToIncrementPriority && currentProc.getPriority() < maxLevel){
				currentProc.setPriority(currentProc.getPriority() + 1);
				currentProc.resetTimeNotProcessed();
				heap.maxHeapifyUp(i);
			}
		}
		
	}

}
