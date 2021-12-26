package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RainRisk {
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i12.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		ArrayList<Character> dir = new ArrayList<>();
		ArrayList<Integer> dis = new ArrayList<>();
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			dir.add(input.charAt(0));
			dis.add(Integer.parseInt(input.substring(1)));
		}
			
		int curDir = 90;
		int dEast = 0;
		int dNorth = 0;
		for (int i = 0; i < dir.size(); i++) {
			switch (dir.get(i)) {
			case 'F':
				switch (curDir % 360) {
				case 0:
					dNorth += dis.get(i);
					break;
				case 90:
					dEast += dis.get(i);
					break;
				case 180:
					dNorth -= dis.get(i);
					break;
				case 270:
					dEast -= dis.get(i);
					break;
				default:
					System.out.println("Error " + curDir);
					break;
				}
				break;
				
			case 'L':
				curDir -= dis.get(i);
				curDir += (curDir < 0) ? 360: 0;
				break;
			case 'R':
				curDir += dis.get(i);
				break;
			case 'N':
				dNorth += dis.get(i);
				break;
			case 'E':
				dEast += dis.get(i);
				break;
			case 'S':
				dNorth -= dis.get(i);
				break;
			case 'W':
				dEast -= dis.get(i);
				break;
			default:
				System.out.println("Error case 1");
				break;
			}
		}
		System.out.println(Math.abs(dEast) + Math.abs(dNorth));
	}
}
