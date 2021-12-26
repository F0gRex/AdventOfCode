package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TobogganTrajectory2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i03.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		ArrayList<String> trees = new ArrayList<String>();
		while (sc.hasNextLine())
			trees.add(sc.nextLine());
		
		long nOfTrees1 = 0;
		int pos = 0;
		for (String s : trees) {
			if (s.charAt(pos) == '#')
				nOfTrees1++;
			pos += 1;
			if (pos >= s.length())
				pos -= s.length();
		}
		
		long nOfTrees2 = 0;
		pos = 0;
		for (String s : trees) {
			if (s.charAt(pos) == '#')
				nOfTrees2++;
			pos += 3;
			if (pos >= s.length())
				pos -= s.length();
		}
		
		long nOfTrees3 = 0;
		pos = 0;
		for (String s : trees) {
			if (s.charAt(pos) == '#')
				nOfTrees3++;
			pos += 5;
			if (pos >= s.length())
				pos -= s.length();
		}
		
		long nOfTrees4 = 0;
		pos = 0;
		for (String s : trees) {
			if (s.charAt(pos) == '#')
				nOfTrees4++;
			pos += 7;
			if (pos >= s.length())
				pos -= s.length();
		}
		
		long nOfTrees5 = 0;
		pos = 0;
		int index = 0;
		for (String s : trees) {
			if (index % 2 == 0) {
				if (s.charAt(pos) == '#')
					nOfTrees5++;
				pos += 1;
				if (pos >= s.length())
					pos -= s.length();
			}
			index++;
		}
			
		System.out.println(nOfTrees1 * nOfTrees2 * nOfTrees3 * nOfTrees4 * nOfTrees5);
	}
}
