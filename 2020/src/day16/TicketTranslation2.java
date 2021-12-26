package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class TicketTranslation2 {
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
		// end of getting input
		
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
		
		//remove invalid tickets
		for (int i = 0; i < tickets.size(); i++) {
			boolean valid = true;
			for (int j: tickets.get(i)) {
				if (j > max || !validValues[j])
					valid = false;
			}
			if (!valid) {
				tickets.remove(i);
				i--;
			}
		}
		
		// initialize map with all possible fields per "class"
		HashMap<String, HashSet<Integer>> fields = new HashMap<>();
		HashSet<Integer> allFields = new HashSet<>();
		for (int i = 0; i < myTicket.size(); i++)
			allFields.add(i);
		for (String key: rules.keySet())
			fields.put(key, allFields);
		
		for (String key: fields.keySet()) {
			HashSet<Integer> allowedFields = fields.get(key);
			HashSet<Integer> updAFields = new HashSet<>(allowedFields);
			for (int i: allowedFields) {
				if (!isValid(myTicket.get(i), rules.get(key)))
					updAFields.remove(i);
			}
			fields.replace(key, updAFields);
		}
		String fieldAsStringOld = "";
		String fieldAsStringNew = fields.toString();
		
		
		//eliminate possibilities as long as possible
		while (!fieldAsStringNew.equals(fieldAsStringOld)) {
			fieldAsStringOld = fieldAsStringNew;
			for (String key: fields.keySet()) {
				HashSet<Integer> allowedFields = fields.get(key);
				HashSet<Integer> updAFields = new HashSet<>(allowedFields);
				for (int i: allowedFields) {
					for (int j = 0; j < tickets.size(); j++) {
						if (!isValid(tickets.get(j).get(i), rules.get(key)))
							updAFields.remove(i);
					}
				}
				fields.replace(key, updAFields);
				if (updAFields.size() == 1) {
					for (String key1: fields.keySet()) {
						if (fields.get(key1).size() > 1) {
							HashSet<Integer> upSet = fields.get(key1);
							upSet.removeAll(updAFields);
							fields.replace(key1, upSet);
						}
					}
				}
			}
			fieldAsStringNew = fields.toString();
		}
		System.out.println("Result: " + fieldAsStringNew);
		
		
		//calculate the result for the task
		long result = 1;
		for (String key: fields.keySet()) {
			if (key.startsWith("departure")) {
				Iterator<Integer> i = fields.get(key).iterator();
				result *= myTicket.get(i.next());
			}
		}
		System.out.println(result);
	}
	
	static boolean isValid(int value, ArrayList<Integer> rule) {
		if ((value >= rule.get(0) && value <= rule.get(1)) || (value >= rule.get(2) && value <= rule.get(3)))
			return true;
		else
			return false;
	}
}
