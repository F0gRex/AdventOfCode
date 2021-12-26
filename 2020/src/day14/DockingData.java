package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DockingData {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i14.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		HashMap<Integer, String> nums = new HashMap<>();
		String mask = "";
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			Scanner scs = new Scanner(input);
			if (input.contains("mask")) {
				scs.next();
				scs.next();
				mask = scs.next();
			}
			else {
				String key = scs.next();
				key = key.replace("mem[", "");
				key = key.replace("]", "");
				scs.next();
				
				//create binary representation and add leading zeros up to 36 bit
				String binNum = Integer.toBinaryString(Integer.parseInt(scs.next()));
				String addLeadingZeros = "";
				for (int i = binNum.length(); i < 36; i++)
					addLeadingZeros += "0";
				binNum = addLeadingZeros + binNum;
				
				//apply the mask
				for (int i = 0; i < mask.length(); i++) {
					switch (mask.charAt(i)) {
					case 'X':
						break;
					case '0':
						binNum = binNum.substring(0, i) + "0" + binNum.substring(i+1);
						break;
					case '1':
						binNum = binNum.substring(0, i) + "1" + binNum.substring(i+1);
						break;
					default:
						System.out.println("Error");
						break;
					}
				}
				nums.put(Integer.parseInt(key), binNum);
			}
			scs.close();
		}
		long sum = 0;
		for (String s: nums.values())
			sum += Long.parseLong(s, 2);
		System.out.println(sum);
	}
}
