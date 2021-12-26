package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class HandyHaversacks2 {
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
		
		//count number of bags in a shiny gold bag		
		HashMap<String, Integer> goldBag = bags.get("shinygold");
		System.out.println(countBags(goldBag) - 1);
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
	
	public static int countBags(HashMap<String, Integer> bag) {
		int count = 0;
		if (bag == null)
			return 1;
		Set<String> keys = bag.keySet();
		for (String s: keys) {
			count += bag.get(s) * countBags(bags.get(s));
		}
		
		//because the bag itself does count too (except for the last one (off by one error: 1 too high))
		return count + 1;
	}
}
