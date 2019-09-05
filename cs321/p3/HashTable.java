
import java.math.BigInteger;

public class HashTable<T> {
	@SuppressWarnings("rawtypes")
	private HashObject[] table;
	private ProbeType useProbeType;
	private int objectsStored;

	public HashTable(ProbeType useProbeType, int minSize){
		if (minSize <= 0) throw new IllegalArgumentException("Size must be positive!");
		this.useProbeType = useProbeType;
		BigInteger size = new BigInteger("" + minSize);
		BigInteger prevSize = new BigInteger("" + minSize);
		while (!size.subtract(prevSize).equals(new BigInteger("2"))) {
			prevSize = size;
			size = size.nextProbablePrime();
		}
		table = new HashObject[Integer.parseInt(size.toString())];
		objectsStored = 0;
	}

	@SuppressWarnings("unchecked")
	public int insert(HashObject<T> newObject) {
		int stepsToInsert = 0;

		if (useProbeType == ProbeType.LINEAR) {
			int hash = HashOne(newObject.getKey());
			HashObject<T> nextField = table[hash];
			do {
				if (nextField == null) {
					table[hash] = newObject;
					stepsToInsert++;
					objectsStored++;
					return stepsToInsert;
				}  else if (nextField.equals(newObject)) {
					nextField.repeat();
					return 0;
				} else {
					stepsToInsert++;
					hash++;
					if (hash > table.length - 1) {
						hash = hash - table.length;
					}
					nextField = table[hash];
				}
			} while (stepsToInsert < table.length);
		} else {
			int hash = HashOne(newObject.getKey());
			int hashTwo = HashTwo(newObject.getKey());
			HashObject<T> nextField = table[hash];
			do {
				if (nextField == null) {
					table[hash] = newObject;
					stepsToInsert++;
					objectsStored++;
					return stepsToInsert;
				}  else if (nextField.equals(newObject)) {
					nextField.repeat();
					return 0;
				} else {
					stepsToInsert++;
					hash += hashTwo;
					if (hash > table.length - 1) {
						hash = hash - table.length;
					}
					nextField = table[hash];
				}
			} while (stepsToInsert <= table.length);
		}
		return stepsToInsert;
	}

	private int HashOne(long key) {
		return (int) (key % table.length);
	}

	private int HashTwo(long key){
		return (int) (1 + (key % (table.length - 2)));
	}

	public int length(){
		return table.length;
	}

	public double getLoadFactor(){
		return (double) objectsStored / (double) table.length;
	}

	public int getStoredObjects(){
		return objectsStored;
	}

	public void print(){

		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.println("table[" + i + "]: " + table[i].getValue() + " " + table[i].getRecurrence());
			}
		}

	}


	public enum ProbeType {
		LINEAR,
		DOUBLE_HASH
	}

}
