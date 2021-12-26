package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PassportProcessing2 {
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
			
			else {
				Scanner scs = new Scanner(input);
				while (scs.hasNext()) {
					String token = scs.next();
					String field = token.substring(0, 4);
					token = token.substring(4);
					
					switch (field) {
					case "byr:":
						if (token.length() == 4 && Integer.parseInt(token) >= 1920 && Integer.parseInt(token) <= 2002)
							byr = true;
						break;
					case "iyr:":
						if (token.length() == 4 && Integer.parseInt(token) >= 2010 && Integer.parseInt(token) <= 2020)
							iyr = true;
						break;
					case "eyr:":
						if (token.length() == 4 && Integer.parseInt(token) >= 2020 && Integer.parseInt(token) <= 2030)
							eyr = true;
						break;
					case "hgt:":
						if (token.length() >= 4) {
							String unit = token.substring(token.length() - 2, token.length());
							token = token.substring(0, token.length() - 2);
							if ((unit.equals("in") && Integer.parseInt(token) >= 59 && Integer.parseInt(token) <= 76)
									|| (unit.equals("cm") && Integer.parseInt(token) >= 150 && Integer.parseInt(token) <= 193))
								hgt = true;
						}
						break;
					case "hcl:":
						if (token.length() == 7 && token.charAt(0) == '#') {
							token = token.substring(1);
							
							token = token.replace("0", "");
							token = token.replace("1", "");
							token = token.replace("2", "");
							token = token.replace("3", "");
							token = token.replace("4", "");
							token = token.replace("5", "");
							token = token.replace("6", "");
							token = token.replace("7", "");
							token = token.replace("8", "");
							token = token.replace("9", "");
							token = token.replace("a", "");
							token = token.replace("b", "");
							token = token.replace("c", "");
							token = token.replace("d", "");
							token = token.replace("e", "");
							token = token.replace("f", "");
							if (token.equals(""))
								hcl = true;
						}
						break;
					case "ecl:":
						if (token.equals("amb") || token.equals("blu") || token.equals("brn") || token.equals("gry") || token.equals("grn") 
								|| token.equals("hzl") || token.equals("oth"))
							ecl = true;
						break;
					case "pid:":
						if (token.length() == 9) {
							token = token.replace("0", "");
							token = token.replace("1", "");
							token = token.replace("2", "");
							token = token.replace("3", "");
							token = token.replace("4", "");
							token = token.replace("5", "");
							token = token.replace("6", "");
							token = token.replace("7", "");
							token = token.replace("8", "");
							token = token.replace("9", "");
							
							if (token.equals(""))
								pid = true;
						}

					default:
						break;
					}
				}
				scs.close();
			}
		}
		System.out.println(nOfValid);
	}
}
