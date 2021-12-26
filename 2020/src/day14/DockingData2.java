package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DockingData2 {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i14.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashMap<Long, Integer> nums = new HashMap<>();
		String mask = "";
		int nOfMaskX = 0;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			Scanner scs = new Scanner(input);
			if (input.contains("mask")) {
				scs.next();
				scs.next();
				mask = scs.next();
				nOfMaskX = 0;
				for (int i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) == 'X')
						nOfMaskX++;
				}
			}
			else {
				String key = scs.next();
				key = key.replace("mem[", "");
				key = key.replace("]", "");
				scs.next();
				
				//create binary representation and add leading zeros up to 36 bit
				key = Integer.toBinaryString(Integer.parseInt(key));
				String addLeadingZeros = "";
				for (int i = key.length(); i < 36; i++)
					addLeadingZeros += "0";
				key = addLeadingZeros + key;
				int num = Integer.parseInt(scs.next());
				
				//apply mask to key
				for (int i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) == '0')
						continue;
					key = key.substring(0, i) + mask.charAt(i) + key.substring(i+1);
				}
				
				//replace the floating values ('X') with all possible representations 
				//creates all permutations by converting the iterator variable into binary
				for (int i = 0; i < Math.pow(2, nOfMaskX); i++) {
					String floatingBit = Integer.toBinaryString(i);
					addLeadingZeros = "";
					for (int j = floatingBit.length(); j < nOfMaskX; j++)
						addLeadingZeros += "0";
					floatingBit = addLeadingZeros + floatingBit;
					
					String tKey = key;
					for (int j = 0; j < nOfMaskX; j++)
						tKey = tKey.replaceFirst("X", "" + floatingBit.charAt(j));
					
					nums.put(Long.parseLong(tKey, 2), num);
				}
			}
			scs.close();
		}
		
		long sum = 0;
		for (int i: nums.values())
			sum += i;
		System.out.println(sum);
	}
}
