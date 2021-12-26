package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class RambunctiousRecitation2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i15.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		String input = sc.nextLine();
		
		String num = "";
		HashMap<Integer, Integer> nums = new HashMap<>();
		int turn = 1;
		
		//initialize Map
		while (input.length() > 0) {
			if (input.charAt(0) != ',') {
				num += input.charAt(0);
				input = input.substring(1);
			}
			else {
				nums.put(Integer.parseInt(num), turn);
				turn++;
				num = "";
				input = input.substring(1);
			}
		}
		nums.put(Integer.parseInt(num), turn);
		turn++;
		
		int lastSpoken = 0;
		int curSpoken = 0;
		while (turn != 30000000) {
			lastSpoken = curSpoken;
			if (nums.keySet().contains(lastSpoken)) {
				curSpoken = turn - nums.get(lastSpoken);
			}
			else {
				curSpoken = 0;
			}
			nums.put(lastSpoken, turn);
			turn++;
//			System.out.println("Turn " + turn + ": " + curSpoken);
		}
		System.out.println(curSpoken);
	}
		
}
