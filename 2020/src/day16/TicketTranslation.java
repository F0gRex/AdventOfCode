package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TicketTranslation {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i16.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		HashMap<String, ArrayList<Integer>> rules = new HashMap<>();
		ArrayList<Integer> myTicket = new ArrayList<>();
		ArrayList<ArrayList<Integer>> tickets = new ArrayList<>();
		
		int scanningPart = 0; 	//0: rules, 1: myTicket, 2: other tickets
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			Scanner scs = new Scanner(input);
			if (input.equals("")) {
				scanningPart++;
				continue;
			}
				
			if (scanningPart == 0) {
				ArrayList<Integer> ruleNumbers = new ArrayList<>();
				String key = scs.next();
				if (!key.contains(":"))
					key += scs.next();
				
				String rawNum = scs.next();
				String num = "";
				while (rawNum.length() > 0) {
					if (rawNum.charAt(0) != '-') {
						num += rawNum.charAt(0);
						rawNum = rawNum.substring(1);
					}
					else {
						ruleNumbers.add(Integer.parseInt(num));
						num = "";
						rawNum = rawNum.substring(1);
					}
				}
				ruleNumbers.add(Integer.parseInt(num));
				
				scs.next(); 
				
				rawNum = scs.next();
				num = "";
				while (rawNum.length() > 0) {
					if (rawNum.charAt(0) != '-') {
						num += rawNum.charAt(0);
						rawNum = rawNum.substring(1);
					}
					else {
						ruleNumbers.add(Integer.parseInt(num));
						num = "";
						rawNum = rawNum.substring(1);
					}
				}
				ruleNumbers.add(Integer.parseInt(num));
				
				rules.put(key, ruleNumbers);
			}
			
			else if (scanningPart == 1) {
				if (input.charAt(0) == 'y')
					continue;
				
				String num = "";
				while (input.length() > 0) {
					if (input.charAt(0) != ',') {
						num += input.charAt(0);
						input = input.substring(1);
					}
					else {
						myTicket.add(Integer.parseInt(num));
						num = "";
						input = input.substring(1);
					}
				}
				myTicket.add(Integer.parseInt(num));
			}
			
			else if (scanningPart == 2) {
				if (input.charAt(0) == 'n')
					continue;
				ArrayList<Integer> curTicket = new ArrayList<>();
				
				String num = "";
				while (input.length() > 0) {
					if (input.charAt(0) != ',') {
						num += input.charAt(0);
						input = input.substring(1);
					}
					else {
						curTicket.add(Integer.parseInt(num));
						num = "";
						input = input.substring(1);
					}
				}
				curTicket.add(Integer.parseInt(num));
				
				tickets.add(curTicket);				
			}
			
			else 
				System.out.println("Error");
			scs.close();
		}
		//find maxValue
		int max = 0;
		for (ArrayList<Integer> i: rules.values()) {
			for (int j: i) {
				max = Math.max(max, j);
			}
		}
		
		//mark allowed values
		boolean[] validValues = new boolean[max + 1];
		for (ArrayList<Integer> i: rules.values()) {
			for (int j = i.get(0); j <= i.get(1); j++)
				validValues[j] = true;
			for (int j = i.get(2); j <= i.get(3); j++)
				validValues[j] = true;
		}
		
		//calculate sum of all invalid numbers
		long sum = 0;
		for (ArrayList<Integer> i: tickets) {
			for (int j: i) {
				if (j > max || !validValues[j])
					sum += j;
			}
		}
		
		System.out.println(sum);
	}
}
