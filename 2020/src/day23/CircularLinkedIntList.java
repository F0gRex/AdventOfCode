package day23;
import java.util.NoSuchElementException;

/* Prog 252-0027, HS 2020
 * Author Joel Voegtlin
 * 2020-11-27
 * Contains the method for a CircularLinkedList
 */

public class CircularLinkedIntList implements IntList {

	IntNode last;
	int size;
	
	// Return the integer value at position 'index'.
	public int get(int index) {
		if (index > size - 1 || index < 0)
			throw new NoSuchElementException();
		
		IntNode cur = last.next;
		for (int i = 0; i < index; i++)
			cur = cur.next;
		return cur.value;
	}

	// Set the integer value at position 'index' to 'value'.
	public void set(int index, int value) {
		if (index > size - 1 || index < 0)
			throw new NoSuchElementException();
		
		IntNode cur = last.next;
		for (int i = 0; i < index; i++)
			cur = cur.next;
		cur.value = value;
	}

	// Returns whether the list is empty (has no values).
	public boolean isEmpty() {
		return size == 0;
	}

	//Returns the size of the list.
	public int getSize() {
		return size;
	}

	//Inserts 'value' at position 0 in the list.
	public void addFirst(int value) {
		if (isEmpty())
			addLast(value);
		else {
			IntNode cur = last.next;
			last.next = new IntNode(value);
			last.next.next = cur;
			size++;
		}
	}

	///Appends 'value' at the end of the list.
	public void addLast(int value) {
		if (isEmpty()) {
			last = new IntNode(value);
			last.next = last;
		}
		else {
			IntNode first = last.next;
			last.next = new IntNode(value);
			last = last.next;
			last.next = first;
		}
		size ++;
	}
 
	// Removes and returns the first value of the list. (Throws a NoSuchElementException if the List is empty.)
	public int removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		int value = last.next.value;
		
		if (size == 1)
			clear();
		else {
			last.next = last.next.next; 
			size--;
		}
		
		return value;
	}

	// Removes and returns the last value of the list.  (Throws a NoSuchElementException if the List is empty.)
	public int removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		int value = last.value;
		
		if (size == 1)
			clear();
		
		else {
			//gets the second last element
			IntNode cur = last;
			for (int i = 0; i < size - 1; i++)
				cur = cur.next;
			cur.next = last.next;
			last = cur;
			size--;
		}
		
		return value;
	}

	// Removes all values from the list, making the list empty.
	public void clear() {
		last = null;
		size = 0;
	}

	// Returns a new int-array with the same contents as the list.
	public int[] toArray() {
		int[] array = new int[size];
		
		IntNode cur = last;
		for (int i = 0; i < array.length; i++) {
			cur = cur.next;
			array[i] = cur.value;
		}
		
		return array;
	}
	
	public int remove(int index) {
		if (index == size - 1)
			return removeLast();
		IntNode cur = last;
		for (int i = 0; i < index; i++)
			cur = cur.next;
		
		int value = cur.next.value;
		cur.next = cur.next.next;
		size--;
		return value;
	}
	
	public void add(int index, int value) {
		if (index == size)
			addLast(value);
		else {
			IntNode cur = last;
			for (int i = 0; i < index; i++)
				cur = cur.next;
			cur.next = new IntNode(value, cur.next);
			size++;
		}
	}
	
	public int size() {
		return getSize();
	}
	
	@Override
	public String toString() {
		String result = "[";
		
		for (IntNode cur = last.next; cur != last; cur = cur.next)
			result += cur.value + ", ";
		
		return result + last.value + "]";
	}
}
