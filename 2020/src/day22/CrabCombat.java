package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;	

public class CrabCombat {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i22.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		LinkedList<Integer> p1 = new LinkedList<>();
		LinkedList<Integer> p2 = new LinkedList<>();
		
		boolean firstPlayer = true;
		sc.nextLine();
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			if (input.contains("Player 2"))
				firstPlayer = false;
			else if (input.isBlank())
				continue;
			else if (firstPlayer)
				p1.addLast(Integer.parseInt(input));
			else
				p2.addLast(Integer.parseInt(input));
		}
		
		// play game
		while (p1.size() != 0 && p2.size() != 0) {
			int val1 = p1.removeFirst();
			int val2 = p2.removeFirst();
			
			if (val1 > val2) {
				p1.addLast(val1);
				p1.addLast(val2);
			}
			else {
				p2.addLast(val2);
				p2.addLast(val1);
			}
		}
		
		//determine winner
		LinkedList<Integer> winner;
		if (p2.size() == 0)
			winner = p1;
		else
			winner = p2;
		// calculate result score
		int score = 0;
		int i = winner.size();
		for (Iterator<Integer> iterator = winner.iterator(); iterator.hasNext(); i--) {
			int value = iterator.next();
			score += i * value;
		}
		System.out.println(score);
	}
}
