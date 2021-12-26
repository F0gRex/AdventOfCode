package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PasswordPhilosophy2 {
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
			String indices = sc.next();
			String firstIndex = "";
			String secondIndex = "";
			boolean first = true;
			for (int i = 0; i < indices.length(); i++) {
				if (indices.charAt(i) != '-') {
					if (first)
						firstIndex += indices.charAt(i);
					else
						secondIndex += indices.charAt(i);
				}
				else
					first = false;
			}
			
			char reqChar = sc.next().charAt(0);
			String pwd = sc.next();
			
			if ((pwd.charAt(Integer.parseInt(firstIndex) - 1) == reqChar || pwd.charAt(Integer.parseInt(secondIndex) - 1) == reqChar) 
					&& !(pwd.charAt(Integer.parseInt(firstIndex) - 1) == reqChar && pwd.charAt(Integer.parseInt(secondIndex) - 1) == reqChar))
				nOfCorrectPwds++;
		}
		System.out.println(nOfCorrectPwds);
		
		
		sc.close();
	}
}
