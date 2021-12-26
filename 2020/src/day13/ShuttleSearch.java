package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShuttleSearch {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i13.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		int depTime = sc.nextInt();
		ArrayList<Integer> bus = new ArrayList<>();
		String input = sc.nextLine();
		input = sc.nextLine();
		String num = "";
		
		while (input.length() > 0) {
			if (input.charAt(0) != 'x' && input.charAt(0) != ',') {
				num += input.charAt(0);
				input = input.substring(1);
			}
				
			else {
				if (!num.isBlank())
					bus.add(Integer.parseInt(num));
				num = "";
				input = input.substring(1);
			}
		}
		int bestLine = -1;
		int bestWaitingTime = Integer.MAX_VALUE;
		for (int i = 0; i < bus.size(); i++) {
			int busDepTime = bus.get(i);
			while (busDepTime <= depTime)
				busDepTime += bus.get(i);
			
			if (busDepTime - depTime < bestWaitingTime) {
				bestLine = i;
				bestWaitingTime = busDepTime - depTime;
			}
		}
		System.out.println(bus.get(bestLine) * bestWaitingTime);
		
	}
}
