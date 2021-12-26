package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RainRisk2 {
	
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
			
		int dEast = 0;
		int dNorth = 0;
		int wayPointEast = 10;
		int wayPointNorth = 1;
		for (int i = 0; i < dir.size(); i++) {
			switch (dir.get(i)) {
			case 'F':
				dEast += dis.get(i) * wayPointEast;
				dNorth += dis.get(i) * wayPointNorth;
				break;
				
			case 'L':
				int oldEast = wayPointEast;
				int oldNorth = wayPointNorth;
				switch (dis.get(i)) {
				case 90:
					wayPointEast = -1 * oldNorth;
					wayPointNorth = oldEast;
					break;
				case 180:
					wayPointEast *= -1;
					wayPointNorth *= -1;
					break;
				case 270:
					wayPointEast = oldNorth;
					wayPointNorth = -1 * oldEast;
					break;
				default:
					System.out.println("Error");
					break;
				}
				break;
			case 'R':
				oldEast = wayPointEast;
				oldNorth = wayPointNorth;
				switch (dis.get(i)) {
				case 90:
					wayPointEast =  oldNorth;
					wayPointNorth = -1 * oldEast;
					break;
				case 180:
					wayPointEast *= -1;
					wayPointNorth *= -1;
					break;
				case 270:
					wayPointEast = -1 * oldNorth;
					wayPointNorth = oldEast;
					break;
				default:
					System.out.println("Error");
					break;
				}
				break;
			case 'N':
				wayPointNorth += dis.get(i);
				break;
			case 'E':
				wayPointEast += dis.get(i);
				break;
			case 'S':
				wayPointNorth -= dis.get(i);
				break;
			case 'W':
				wayPointEast -= dis.get(i);
				break;
			default:
				System.out.println("Error case 1");
				break;
			}
			System.out.println("i: " + i + " " + dEast + " " + dNorth + " " + wayPointEast + " " + wayPointNorth);
		}
		System.out.println(Math.abs(dEast) + Math.abs(dNorth));
	}
}
