package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class HandyHaversacks {
	public static HashMap<String, HashMap<String, Integer>> bags = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i07.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			Scanner scs = new Scanner(input);
			while (scs.hasNext()) {
				String color = scs.next() + scs.next();
				
				//skip bags contain and 
				scs.next(); scs.next();
				
				if (scs.hasNextInt()) {
					HashMap<String, Integer> temp = new HashMap<>();
					temp = addSubBags(scs, temp);
					bags.put(color, temp);
				}
				else
					break;
			}
		}
		
		//count number of possible shiny gold bags
		Set<String> search = bags.keySet();
		HashSet<String> containsGold = new HashSet<>();
		containsGold.add("shinygold");
		
		int found;
		do {
			HashSet<String> remove = new HashSet<>();
			found = 0;
			for (String s: search) {
				Set<String> containBags = bags.get(s).keySet();
				for (String c: containsGold) {
					if (containBags.contains(c)) {
						containsGold.add(s);
						remove.add(s);
						found++;
						break;
					}
				}
				
			}
			search.removeAll(remove);
		} while (found > 0);
		System.out.println(containsGold.size() - 1);
	}	
	
	
	public static HashMap<String, Integer> addSubBags(Scanner scs, HashMap<String, Integer> containBags) {
		while (scs.hasNext()) {
			int count = scs.nextInt();
			String color = scs.next() + scs.next();
			containBags.put(color, count);
			scs.next();
		}
		return containBags;
	}
}
