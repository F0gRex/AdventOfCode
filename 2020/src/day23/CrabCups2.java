package day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CrabCups2 {
	static final int MAX_VALUE = 1000000;
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i23.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		CircularLinkedIntList cups = new CircularLinkedIntList();
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			for (int i = 0; i < input.length(); i++) {
				cups.addLast(Integer.parseInt(input.substring(i, i + 1)));
			}
		}
		for (int i = 10; i <= MAX_VALUE; i++)
			cups.addLast(i);
		
		HashMap<Integer, IntNode> nodeLocation = new HashMap<>();
		for (IntNode cur = cups.last.next; cur != cups.last; cur = cur.next)
			nodeLocation.put(cur.value, cur);
		nodeLocation.put(cups.last.value, cups.last);
		
		IntNode curCup = cups.last.next;
		for (int n = 0; n < 10000000; n++) {
			if (n % 100000 == 99999)
				System.out.println("---- move " + (n + 1) + " ----");
			
			//destroys integrity of the list
			IntNode firstRemoved;
			
			firstRemoved = curCup.next;
			curCup.next = curCup.next.next.next.next;
			
			int destCupValue = curCup.value;
			
			destCupValue = (destCupValue != 1) ? destCupValue - 1: MAX_VALUE;
			while (destCupValue == firstRemoved.value || destCupValue == firstRemoved.next.value || destCupValue == firstRemoved.next.next.value)
				destCupValue = (destCupValue != 1) ? destCupValue - 1: MAX_VALUE;
			
			
			// find destination cup
			IntNode desCup = nodeLocation.get(destCupValue);
			
			// reinsert cups and restores integrity of the list 
			// (the last element might not point to the same index of the list but because it's circular it doesn't matter)
			firstRemoved.next.next.next = desCup.next;
			desCup.next = firstRemoved;
			
			curCup = curCup.next;
		}

		IntNode one = nodeLocation.get(1);
		System.out.println(one.next.value + " * " + one.next.next.value + " = " + ((long) one.next.value * (long) one.next.next.value));
	}
}