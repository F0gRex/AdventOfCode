package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OperationOrder2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i18.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		long sum = 0;
		while (sc.hasNextLine()) {
			long result =  evalTerm(preprocess(sc.nextLine()));
			sum += result;
			System.out.println(result);
			System.out.println();
		}
		System.out.println(sum);
	}
	
	static String preprocess(String input) {
		String result = input;
		System.out.println(input);
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == '+') {
				int first = firstBracket(result, i);
				int last = secondBracket(result, i);
				
				result = result.substring(0, last) + ")" + result.substring(last);
				result = result.substring(0, first) + "(" + result.substring(first);
				i += 2;
				System.out.println(result);
			}
		}
		return result;
	}
	
	static int firstBracket(String input, int index) {
		int bracketDepth = 0;
		for (int i = index - 1; i >= 0; i--) {
			switch (input.charAt(i)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (bracketDepth == 0)
					return i;
				break;
			case '+':
			case '*':
				if (bracketDepth == 0)
					return i + 1;
				break;
			case '(': bracketDepth++; 
				if (bracketDepth == 1)
					return i;
				break;
			case ')': bracketDepth --; break;
			}
		}
		return 0;
	}
	
	static int secondBracket(String input, int index) {
		int bracketDepth = 0;
		for (int i = index + 1; i < input.length(); i++) {
			switch (input.charAt(i)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (bracketDepth == 0)
					return i + 1;
				break;
			case '+':
			case '*':
				if (bracketDepth == 0)
					return i;
				break;
			case '(': bracketDepth++; break;
			case ')': bracketDepth --; 
				if (bracketDepth == -1)
					return i;
				break;
			}
		}
		return input.length();
	}
	
	static long evalTerm(String input) {
		long result = 0;
		int bracketDepth = 0;
		char operator = '#';
		for (int i = 0; i < input.length(); i++) {
			int newNum = -1;
			switch (input.charAt(i)) {
			case '0': newNum = 0; break;
			case '1': newNum = 1; break;
			case '2': newNum = 2; break;
			case '3': newNum = 3; break;
			case '4': newNum = 4; break;
			case '5': newNum = 5; break;
			case '6': newNum = 6; break;
			case '7': newNum = 7; break;
			case '8': newNum = 8; break;
			case '9': newNum = 9; break;
			case '+': operator = '+'; break;
			case '*': operator = '*'; break;
			case '(': bracketDepth++; break;
			case ')': bracketDepth --; break;
			case ' ': break;
			default: System.out.println("Error unknow char" + input.charAt(i)); break;
			}
			
			if (bracketDepth > 0) {
				if (operator != '#')
					result = calcResult(result, evalTerm(input.substring(i + 1)), operator);
				else 
					result = evalTerm(input.substring(i + 1));
				while (bracketDepth > 0) {
					i++;
					if (!(i < input.length())) {
						System.out.println("Bracket Error");
						break;
					}
					switch (input.charAt(i)) {
						case '(': bracketDepth++; break;
						case ')': bracketDepth --; break;
					}
				}
			}
			else if (bracketDepth < 0)
				return result;
			else {
				if (newNum != -1) {
					if (result == 0)
						result = newNum;
					else {
						result = calcResult(result, newNum, operator);
						operator = '#';
					}
				}
			}
		}
		return result;
	}
	static long calcResult(long x, long y, char operator) {
		switch (operator) {
		case '+': return x + y;
		case '*': return x * y;
		default: System.out.println("Unsupported Operator " + operator); return -1;
		}
	}
}
