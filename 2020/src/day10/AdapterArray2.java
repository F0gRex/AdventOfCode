package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AdapterArray2 {

	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i10.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		ArrayList<Integer> adapters = new ArrayList<>();
		while (sc.hasNext()) {
			adapters.add(sc.nextInt());	
		}
		adapters.add(0);

		Collections.sort(adapters);
		//add last element
		adapters.add(adapters.get(adapters.size() - 1) + 3);
		
		int[] adDiff = new int[adapters.size() - 1];
		int nOfConsecutiveOnes = 0;
		long result = 1;
		for (int i = 0; i < adapters.size() - 1; i++) {
			adDiff[i] = adapters.get(i+1) - adapters.get(i);
			if (adDiff[i] == 1)
				nOfConsecutiveOnes++;
			else {
				if (nOfConsecutiveOnes == 0)
					continue;
				// two consecutive ones result in two possibilities and three in 4 possibilities (one does not add any more
				// Possibilities because the adapter can't be removed
				else if (nOfConsecutiveOnes <= 3)
					result *= Math.pow(2, nOfConsecutiveOnes - 1);
				// 4 Consecutive adapters result in 7 more possibilities because at most two can be omitted
				else if (nOfConsecutiveOnes == 4)
					result *= 7;
				else
					System.out.println("Error not made for samples like this");
				nOfConsecutiveOnes = 0;
			}
		}
		System.out.println("Result: " + result);		
	}
}
