package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;	

public class CrabCombat2 {
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
		
		//determine winner
		LinkedList<Integer> winner;
		if (recGame(p1, p2))
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
	
	static boolean recGame(LinkedList<Integer> p1, LinkedList<Integer> p2) {
		HashSet<LinkedList<Integer>> prevDecksP1 = new HashSet<>();
		
		while (p1.size() != 0 && p2.size() != 0) {
			for (LinkedList<Integer> l : prevDecksP1) {
				if (p1.equals(l))
					return true;
			}
			prevDecksP1.add(new LinkedList<>(p1));
			int val1 = p1.removeFirst();
			int val2 = p2.removeFirst();
			boolean p1IsWinner;
			
			if (val1 <= p1.size() && val2 <= p2.size()) {
				LinkedList<Integer> recP1 = new LinkedList<>();
				LinkedList<Integer> recP2 = new LinkedList<>();
				int i = val1;
				for (Iterator<Integer> iterator = p1.iterator(); i > 0; i--)
					recP1.addLast(iterator.next());
				i = val2;
				for (Iterator<Integer> iterator = p2.iterator(); i > 0; i--)
					recP2.addLast(iterator.next());
				
				p1IsWinner = recGame(recP1, recP2);
			}
			else {
				if (val1 > val2)
					p1IsWinner = true;
				else
					p1IsWinner = false;
			}
			
			if (p1IsWinner) {
				p1.addLast(val1);
				p1.addLast(val2);
			}
			else {
				p2.addLast(val2);
				p2.addLast(val1);
			}
		}
		if (p2.size() == 0)
			return true;
		else
			return false;
	}
}