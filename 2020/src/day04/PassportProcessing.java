package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PassportProcessing {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i04.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		boolean byr, iyr, eyr, hgt, hcl, ecl, pid;
		byr = iyr = eyr = hgt = hcl = ecl = pid = false;
		int nOfValid = 0;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			if (input.isEmpty()) {
				if (byr && iyr && eyr && hgt && hcl && ecl && pid)
					nOfValid++;
				byr = iyr = eyr = hgt = hcl = ecl = pid = false;
			}
			
			if (input.contains("byr:")) byr = true;
			if (input.contains("iyr:")) iyr = true;
			if (input.contains("eyr:")) eyr = true;
			if (input.contains("hgt:")) hgt = true;
			if (input.contains("hcl:")) hcl = true;
			if (input.contains("ecl:")) ecl = true;
			if (input.contains("pid:")) pid = true;
		}
		System.out.println(nOfValid);
	}
}
