package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class BinaryBoarding2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i05.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashSet<Integer> id = new HashSet<>();
		int min = Integer.MAX_VALUE;
		int max = 0;
		while (sc.hasNext()) {
			String in = sc.next();
			int row = 0;
			for (int i = 0; i < 8; i++) {
				if (in.charAt(i) == 'B')
					row += Math.pow(2, 6 - i);
			}
			
			int collumn = 0;
			for (int i = 7; i < in.length(); i++) {
				if (in.charAt(i) == 'R')
					collumn += Math.pow(2, 2 - (i-7));
			}
			if (row * 8 + collumn > max)
				max = row * 8 + collumn;
			if (row * 8 + collumn < min)
				min = row * 8 + collumn;
			
			id.add(row * 8 + collumn);
			
		}
		for (int i = min; i <= max; i++) {
			if (!id.contains(i))
				System.out.println(i);
		}
	}
}
