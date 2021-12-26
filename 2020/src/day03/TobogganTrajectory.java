package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TobogganTrajectory {
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
		
		int nOfTrees = 0;
		int pos = 0;
		for (String s : trees) {
			if (s.charAt(pos) == '#')
				nOfTrees++;
			pos += 3;
			if (pos >= s.length())
				pos -= s.length();
		}
			
		System.out.println(nOfTrees);
	}
}
