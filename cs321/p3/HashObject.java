
public class HashObject<T> {
	private long key;
	private T value;
	private int recurrence;
	private boolean deleted;

	public HashObject(long key, T value){
		this.key = key;
		this.value = value;
		recurrence = 0;
		deleted = false;
	}

	public boolean equals(HashObject<T> compTo){
		return value.equals(compTo.getValue());
	}

	public long getKey(){
		return key;
	}

	public T getValue(){
		return value;
	}

	public void repeat(){
		recurrence++;
	}

	public boolean isDeleted(){
		return deleted;
	}

	public T delete(){
		deleted = true;
		return value;
	}

	public int getRecurrence(){
		return recurrence;
	}

	public String toString(){
		return "" + value + " " + recurrence;
	}

}
