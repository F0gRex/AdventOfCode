package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MonsterMessages2 {
	static HashMap<Integer, ArrayList<Integer>> rules = new HashMap<>();
	static HashMap<Integer, HashSet<String>> valPatterns = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i19.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		int nOfValidZeros = 0;
		
		boolean getRules = true;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			if (input.isBlank()) {
				getRules = false;
				// update rules as requested in the puzzle
				ArrayList<Integer> up8 = new ArrayList<>();
				up8.add(42);
				up8.add(-1);
				up8.add(42);
				up8.add(8);
				rules.put(8, up8);
				ArrayList<Integer> up11 = new ArrayList<>();
				up11.add(42);
				up11.add(31);
				up11.add(-1);
				up11.add(42);
				up11.add(11);
				up11.add(31);
				rules.put(11, up11);
				// compute valid patterns for all non cyclic rules (all rules that are contained in 8 and 11)
				// => the rule 0, 8 and 11 are not computed yet
				computeValidPatterns(rules.get(8));
				computeValidPatterns(rules.get(11));
			}
			
			// put the rules in a map
			else if (getRules) {
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
				//check every pattern if it is valid
				HashSet<String> posRemainders = new HashSet<>();
				posRemainders.add(input);
				//check if a patterns might work considering rule 8 (rule 0: 8 11)
				posRemainders = remaindersFirst(posRemainders, 8);
				//check if a valid substring of the pattern works with rule 11 too
				posRemainders = remaindersSecond(posRemainders, 11);
				//if the pattern is valid the function return null
				if (posRemainders == null)
					nOfValidZeros++;
				
			}
		}
//		System.out.println(valPatterns.toString());
		System.out.println(nOfValidZeros);
	}
	
	// creates all substring applying the first rule (here 8 multiple times)
	static HashSet<String> remaindersFirst(HashSet<String> posRemainders, int ruleNumber){
		//exits the recursion if no possible substring remain
		if (posRemainders.isEmpty())
			return posRemainders;
		
		HashSet<String> posRemainders1 = new HashSet<>();
		HashSet<String> posRemainders2 = new HashSet<>();
		posRemainders1.addAll(posRemainders);
		posRemainders2.addAll(posRemainders);
		
		boolean first = true;
		ArrayList<Integer> rule = rules.get(ruleNumber);
		for (int i = 0; i < rule.size(); i++) {
			// calls the recursion
			if (rule.get(i) == ruleNumber) {
				HashSet<String> temp = new HashSet<>();
				temp.addAll(posRemainders2);
				HashSet<String> loop = remaindersFirst(temp, ruleNumber);
				posRemainders2.addAll(loop);
			}
			// create OR if the rule has one
			else if (rule.get(i) == -1)
				first = false;
			else {
				// creates all possible substring after applying a part of the rule
				if (first) {
					HashSet<String> temp = new HashSet<>();
					for (String s: posRemainders1)
						temp.addAll(calcPosRemainders(s, rule, i));
					posRemainders1.clear();
					posRemainders1.addAll(temp);
				}
				else {
					HashSet<String> temp = new HashSet<>();
					for (String s: posRemainders2)
						temp.addAll(calcPosRemainders(s, rule, i));
					posRemainders2.clear();
					posRemainders2.addAll(temp);
				}
			}
		}
		
		posRemainders1.addAll(posRemainders2);
		return posRemainders1;
	}
	
	static HashSet<String> remaindersSecond(HashSet<String> posRemainders, int ruleNumber){
		//exits the recursion if no possible substring remain
		if (posRemainders.isEmpty())
			return posRemainders;
		
		HashSet<String> posRemainders1 = new HashSet<>();
		HashSet<String> posRemainders2 = new HashSet<>();
		posRemainders1.addAll(posRemainders);
		posRemainders2.addAll(posRemainders);
		
		boolean first = true;
		ArrayList<Integer> rule = rules.get(ruleNumber);
		for (int i = 0; i < rule.size(); i++) {
			// if the last part of the rule can be applied and the whole input is consumed,
			// the pattern is valid and null is returned 
			// (should only be reached in the most outer call of recursion)
			if (i == rule.size() - 1 &&  first) {
				if (isValid(posRemainders1, valPatterns.get(rule.get(i)))) {
					return null;
				}
			}
			else if (i == rule.size() - 1 && !first) {
				if (isValid(posRemainders2, valPatterns.get(rule.get(i)))) {
					return null;
				}
			}
			// calls the recursion
			if (rule.get(i) == ruleNumber) {
				HashSet<String> temp = new HashSet<>();
				temp.addAll(posRemainders2);
				HashSet<String> loop = remaindersFirst(temp, ruleNumber);
				posRemainders2.addAll(loop);
			}
			// create OR if the rule has one
			else if (rule.get(i) == -1)
				first = false;
			// creates all possible substring after applying a part of the rule
			else {
				if (first) {
					HashSet<String> temp = new HashSet<>();
					for (String s: posRemainders1)
						temp.addAll(calcPosRemainders(s, rule, i));
					posRemainders1.clear();
					posRemainders1.addAll(temp);
				}
				else {
					HashSet<String> temp = new HashSet<>();
					for (String s: posRemainders2)
						temp.addAll(calcPosRemainders(s, rule, i));
					posRemainders2.clear();
					posRemainders2.addAll(temp);
				}
			}
		}
		posRemainders1.addAll(posRemainders2);
		return posRemainders1;
	}
	
	// checks if one of the remainders is equal to a valid pattern for the given rule
	static boolean isValid(HashSet<String> posRemainders, HashSet<String> valPattern) {
		for (String p : posRemainders) {
			for (String s: valPattern) {
				if (p.equals(s))
					return true;
			}
		}
		return false;
	}
	
	// calculates all the remainders if a part of rule is applied
	static HashSet<String> calcPosRemainders(String input, ArrayList<Integer> rule, int index) {
		HashSet<String> possibleRemainders = new HashSet<>();
		for (String s: valPatterns.get(rule.get(index))) {
			if (input.startsWith(s))
				possibleRemainders.add(input.replaceFirst(s, ""));
		}
		return possibleRemainders;
	}
	
	// computes all the valid patterns for the non cyclic rules
	static HashSet<String> computeValidPatterns(ArrayList<Integer> subrules) {
		HashSet<String> valids1 = new HashSet<>();
		HashSet<String> valids2 = new HashSet<>();
		boolean first = true;
		
		for (int i = 0; i < subrules.size(); i++) {
			// prevent that any loop is entered
			if (subrules.get(i) == 8 || subrules.get(i) == 11)
				continue;
			// create OR if the rule has one
			if (subrules.get(i) == -1) {
				first = false;
			}
			// base case
			else if (subrules.get(i) == -2)
				valids1.add("a");
			else if (subrules.get(i) == -3)
				valids1.add("b");
			else {
				HashSet<String> returned;
				// if the needed pattern for the sub rule has already been computed the algorithm uses it
				// else the pattern is computed and stored in map
				if (valPatterns.containsKey(subrules.get(i)))
					returned = valPatterns.get(subrules.get(i));
				else {
					returned = computeValidPatterns(rules.get(subrules.get(i)));
					valPatterns.put(subrules.get(i), returned);
				}
				// computes the valid patterns for the part before |
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
				
				// computes the valid patterns for the part after | (if needed)
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