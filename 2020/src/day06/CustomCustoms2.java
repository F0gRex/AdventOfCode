package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CustomCustoms2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i06.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		
		int total = 0;
		int nOfPersons = 0;
		int[] answers = new int[26];
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			if (input.isEmpty()) {
				for (int i = 0; i < answers.length; i++) {
					if (answers[i] == nOfPersons)
						total++;
				}
				answers = new int[26];
				nOfPersons = 0;
			}
			
			else {
				nOfPersons++;
				Scanner scs = new Scanner(input);
				while (scs.hasNext()) {
					String token = scs.next();
					for (int i = 0; i < token.length(); i++) {
						answers[(int) token.charAt(i) - 97]++;
					}
					
				}
				scs.close();
			}
		}
		System.out.println(total);
	}
}
