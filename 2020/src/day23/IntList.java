package day23;

public interface IntList {

	/** Return the integer value at position 'index'. */
	public int get(int index);

	/** Set the integer value at position 'index' to 'value'. */
	public void set(int index, int value);

	/** Returns whether the list is empty (has no values). */
	public boolean isEmpty();

	/** Returns the size of the list. */
	public int getSize();

	/** Inserts 'value' at position 0 in the list. */
	public void addFirst(int value);

	/** Appends 'value' at the end of the list. */
	public void addLast(int value);

	/** 
	 * Removes and returns the first value of the list. 
	 * Throws a NoSuchElementException if the List is empty.
	 * */
	public int removeFirst();

	/** 
	 * Removes and returns the last value of the list. 
	 * Throws a NoSuchElementException if the List is empty.
	 * */
	public int removeLast();

	/** Removes all values from the list, making the list empty. */
	public void clear();

	/** Returns a new int-array with the same contents as the list. */
	public int[] toArray();
}
