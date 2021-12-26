package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MonsterMessages {
	static HashMap<Integer, ArrayList<Integer>> rules = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i19.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashSet<String> validForZero = new HashSet<>();
		int nOfValidZeros = 0;
		
		boolean getRules = true;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			if (input.isBlank()) {
				getRules = false;
				// compute valid patterns for 0;
				validForZero = computeValidPatterns(rules.get(0));
			}
			
			if (getRules) {
				Scanner scs = new Scanner(input);
				ArrayList<Integer> rule = new ArrayList<>();
				int key = Integer.parseInt(scs.next().replace(":", ""));
				while (scs.hasNext()) {
					String tk = scs.next();
					if (tk.equals("|"))
						rule.add(-1);
					else if (tk.equals("\"a\""))
						rule.add(-2);
					else if (tk.equals("\"b\""))
						rule.add(-3);
					else
						rule.add(Integer.parseInt(tk));
				}
				rules.put(key, rule);
				scs.close();
			}
			
			else {
				if (validForZero.contains(input))
					nOfValidZeros++;
			}
		}
		System.out.println(nOfValidZeros);
	}
	
	static HashSet<String> computeValidPatterns(ArrayList<Integer> subrules) {
		HashSet<String> valids1 = new HashSet<>();
		HashSet<String> valids2 = new HashSet<>();
		boolean first = true;
		
		for (int i = 0; i < subrules.size(); i++) {
			if (subrules.get(i) == -1) {
				first = false;
			}
			else if (subrules.get(i) == -2)
				valids1.add("a");
			else if (subrules.get(i) == -3)
				valids1.add("b");
			else {
				HashSet<String> returned = computeValidPatterns(rules.get(subrules.get(i)));
				if (first) {
					if (valids1.isEmpty())
						valids1.addAll(returned);
					else {
						HashSet<String> temp = new HashSet<>();
						for (String s : valids1) {
							for (String r : returned)
								temp.add(s + r);
						}
						valids1.clear();
						valids1.addAll(temp);
					}
				}

				else {
					if (valids2.isEmpty())
						valids2.addAll(returned);
					else {
						HashSet<String> temp = new HashSet<>();
						for (String s : valids2) {
							for (String r : returned)
								temp.add(s + r);
						}
						valids2.clear();
						valids2.addAll(temp);
					}
				}
			}
		}
		
		valids1.addAll(valids2);
		return valids1;
	}
}
