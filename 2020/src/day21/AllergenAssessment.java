package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class AllergenAssessment {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i21.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashMap<String, HashSet<String>> allergens = new HashMap<>();
		HashSet<HashSet<String>> allRecipies = new HashSet<>();
		HashSet<String> nonAllergic = new HashSet<>();
		
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
				
				if (firstPart) {
					ingredients.add(tk);
					nonAllergic.add(tk);
				}
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
		
		// get all the nonAllergic ingredients
		for (HashSet<String> set : allergens.values()) {
			for (String s : set)
				nonAllergic.remove(s);
		}
		
		int nOfNonAllergicIngredients = 0;
		for (HashSet<String> set : allRecipies) {
			for (String s : set) {
				if (nonAllergic.contains(s))
					nOfNonAllergicIngredients++;
			}
		}
		System.out.println(allergens.toString());
		System.out.println(nOfNonAllergicIngredients);
	}
}
