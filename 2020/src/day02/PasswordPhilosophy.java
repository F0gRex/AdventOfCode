package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PasswordPhilosophy {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i02.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		int nOfCorrectPwds = 0;
		while (sc.hasNextLine()) {
			String nOfAllowed = sc.next();
			String firstNumber = "";
			String secondNumber = "";
			boolean first = true;
			for (int i = 0; i < nOfAllowed.length(); i++) {
				if (nOfAllowed.charAt(i) != '-') {
					if (first)
						firstNumber += nOfAllowed.charAt(i);
					else
						secondNumber += nOfAllowed.charAt(i);
				}
				else
					first = false;
			}
			
			char reqChar = sc.next().charAt(0);
			String pwd = sc.next();
			//count the number of times reqChar occures in pwd
			int nOccured = 0;
			for (int i = 0; i < pwd.length(); i++) {
				if (reqChar == pwd.charAt(i))
					nOccured++;
			}
			
			if (nOccured >= Integer.parseInt(firstNumber) && nOccured <= Integer.parseInt(secondNumber))
				nOfCorrectPwds++;
		}
		System.out.println(nOfCorrectPwds);
		
		
		sc.close();
	}
}
