package day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TropicalIsland {
	
	public static void main(String[] args) {
		Scanner sc = null;
		Scanner tmp = null;
		try {
			sc = new Scanner(new File("i01.txt"));
			tmp = new Scanner(new File("i01.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		//get array with values
		int size = 0;
		while (tmp.hasNextInt()) {
			tmp.nextInt();
			size++;
		}
		
		int[] values = new int[size];
		for (int i = 0; i < values.length; i++)
			values[i] = sc.nextInt();
		sc.close();
	
		//find two compatible values
		for (int i = 0; i < values.length; i++) {
			for (int j = i + 1; j < values.length; j++) {
				if (values[i] + values[j] == 2020)
					System.out.println(values[i] * values[j]);
			}
		}
	}
}
