package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ShuttleSearch2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i13.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashMap<Integer, Integer> bus = new HashMap<>();
		String input = sc.nextLine();
		input = sc.nextLine();
		int offSet = 0;
		String number = "";
		
		while (input.length() > 0) {
			if (input.charAt(0) != 'x' && input.charAt(0) != ',') {
				number += input.charAt(0);
				input = input.substring(1);
			}
			else if (input.charAt(0) == 'x') {
				offSet++;
				input = input.substring(1);
			}
			
			else {
				if (number.length() > 0) {
					bus.put(Integer.parseInt(number), offSet);
					number = "";
					offSet++;
				}
				input = input.substring(1);
				
			}
		}
		if (number.length() > 0) {
			bus.put(Integer.parseInt(number), offSet);
			number = "";
			offSet++;
		}
		
		int[] rem = new int[bus.size()];
		int[] num = new int[bus.size()];
		
		int index = 0;
		for (int i : bus.keySet()) {
			num[index] = i;
			rem[index] = (i - bus.get(i));
			while (rem[index] < 0)
				rem[index] += i;
			rem[index] %= i;
			index++;
		}
		
		System.out.println(crt(rem,num));
	}
	
	public static long crt(int[] rem, int[] num) {
		// calculate the product of all numbers M
		long productM = 1;
		for (int i = 0; i < num.length; i++) {
			productM *= num[i];
		}
		
		//could be omitted but makes the code neater
		long productMi[] = new long[num.length];
		long inverse[] = new long[num.length];
		
		long sum = 0;
		for (int i = 0; i < num.length; i++) {
			//calculate Mi = M/mi
			productMi[i] = productM / num[i];
			//computes the x of (Mi * x) mod_mi (1)
			inverse[i] = computeInverse(productMi[i], num[i]);
			sum += productMi[i] * inverse[i] * rem[i];
		}
		
		// returns the result (for bigger numbers the modulo operation has to be done earlier in order to prevent overflow)
		return sum % productM;
	}

	
	public static long computeInverse(long a, long b) {
		long oldB = b;
		long q;
		long x = 0, y = 1;
		long temp;
		
		if (b == 1)
			return 0;

		// Apply extended Euclid Algorithm
		while (a > 1) {
			// q is quotient
			q = a / b;

			// Euclid algorithm
			temp = b;
			b = a % b;
			a = temp;

			// Stores the factors of the multiplication (extended part)
			temp = x;
			x = y - q * x;
			y = temp;
		}
		// Make y positive
		if (y < 0)
			y += oldB;
		return y;
	}
}
