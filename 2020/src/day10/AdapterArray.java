package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class AdapterArray {

	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i10.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		TreeSet<Integer> adapters = new TreeSet<>();
		while (sc.hasNext()) {
			adapters.add(sc.nextInt());	
		}
		
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		
		int first = 0;
		while (adapters.size() != 0) {
			int last = first;
			first = adapters.first();
			adapters.remove(first);
			
			switch (first - last) {
			case 3:
				sum3++;
				break;
			case 2:
				sum2++;
				break;
			case 1:
				sum1++;
				break;
			default:
				System.out.println("Error");
				break;
			}
		}
		System.out.println(sum1 + sum2 + sum3);
		System.out.println(sum1 * (sum3 + 1));
	}
}
