package day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class CrabCups {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i23.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		LinkedList<Integer> cups = new LinkedList<>();
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			for (int i = 0; i < input.length(); i++) {
				cups.addLast(Integer.parseInt(input.substring(i, i + 1)));
			}
		}
		
		int curIndex = 0;
		int curCup = 0;
		for (int n = 0; n < 100; n++) {
			int val1, val2, val3;
			System.out.println("---- move " + (n + 1) + " ----");
			System.out.println("Active: " + cups.get(curCup));
			System.out.println(cups.toString());
			curIndex = curCup;
			curIndex = (curIndex + 1 < cups.size()) ? curIndex + 1: 0;
			
			curCup -= (curIndex < curCup)? 1 : 0;
			val1 = cups.remove(curIndex);
			curIndex = (curIndex < cups.size()) ? curIndex: 0;
			curCup -= (curIndex < curCup)? 1 : 0;
			val2 = cups.remove(curIndex);
			curIndex = (curIndex < cups.size()) ? curIndex: 0;
			curCup -= (curIndex < curCup)? 1 : 0;
			val3 = cups.remove(curIndex);
			
			int curCupValue = cups.get(curCup);
			// find destination cup
			while (curCupValue > 0) {
				curCupValue = (curCupValue != 1) ? curCupValue - 1: 9;
				for (int i = 0; i < cups.size(); i++) {
					if (cups.get(i) == curCupValue) {
						curIndex = i;
						curCupValue = 0;
						break;
					}
				}
			}
			
			System.out.println("Picked up: " + val1  + ", " + val2 + ", " + val3);
			System.out.println("Destination: " + cups.get(curIndex));
			System.out.println();
			// reinsert cups
			curCup += (curIndex < curCup)? 1 : 0;
			cups.add(++curIndex, val1);
			curCup += (curIndex < curCup)? 1 : 0;
			cups.add(++curIndex, val2);
			curCup += (curIndex < curCup)? 1 : 0;
			cups.add(++curIndex, val3);
			curCup = (curCup + 1 < cups.size()) ? curCup + 1: 0;
		}
		System.out.println(cups.toString());
		
		int index = -1;
		for (int n = 0; n < 8; n++) {
			// get Index of number 1
			if (index == -1) {
				for (int i = 0; i < cups.size(); i++) {
					if (cups.get(i) == 1) {
						index = i;
						break;
					}
				}
			}
			index = (index != 8) ? index + 1: 0;
			System.out.print(cups.get(index));
		}
	}

}
