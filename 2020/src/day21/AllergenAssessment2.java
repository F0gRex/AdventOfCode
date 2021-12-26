package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

public class AllergenAssessment2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i21.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		TreeMap<String, HashSet<String>> allergens = new TreeMap<>();
		HashSet<HashSet<String>> allRecipies = new HashSet<>();
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			Scanner scs = new Scanner(input);
			HashSet<String> ingredients = new HashSet<>();
			boolean firstPart = true;
			while (scs.hasNext()) {
				String tk = scs.next();
				if (tk.contains("contains")) {
					firstPart = false;
					continue;
				}
				
				if (firstPart)
					ingredients.add(tk);
				else {
					String key = tk.substring(0, tk.length()-1);
					allRecipies.add(ingredients);
					if (!allergens.containsKey(key)) {
						HashSet<String> value = new HashSet<>(ingredients);
						allergens.put(key, value);
					}
					else {
						HashSet<String> oldIngredients = allergens.get(key);
						oldIngredients.retainAll(ingredients);
					}
				}
			}
			scs.close();
		}
		
		// get the exact allergics
		boolean updated = true;
		while (updated) {
			updated = false;
			for (HashSet<String> set : allergens.values()) {
				if (set.size() == 1) {
					for (String s : set) { //is only used to get the one element contained in set
						for (HashSet<String> set2 : allergens.values()) {
							if (set != set2 && set2.contains(s)) {
								updated = true;
								set2.remove(s);
							}
						}
					}
				}
			}
		}
		
		String result = "";
		for (HashSet<String> set : allergens.values()) {
			for (String s : set) {
				result += s + ",";
			}
		}
		result = result.substring(0, result.length()-1);
		System.out.println(allergens.toString());
		System.out.println(result);
	}
}
