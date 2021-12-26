package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LobbyLayout {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i24.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		// a tile is identified by a string where (e.g. 25_-10) the first number indicates the line in the pattern
		// relative to the center tile (first number is the line upwards (negative) or downwards and the 
		// secondNumber is the horizontal position relative to the center tile (where north east is always 0)
		
		HashMap<String, Boolean> tiles = new HashMap<>();
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			
			int line = 0;
			int col = 0;
			while (input.length() > 0) {
				String direction = input.substring(0,1);
				input = input.substring(1);
				
				if (direction.equals("n") || direction.equals("s")) {
					direction += input.substring(0,1);
					input = input.substring(1);
				}
				
				switch (direction) {
				case "e": col++; break;
				case "w": col--; break;
				case "nw": col-= Math.abs(line-- % 2); break;
				case "ne": col+= Math.abs(--line % 2); break;
				case "sw": col-= Math.abs(line++ % 2); break;
				case "se": col+= Math.abs(++line % 2); break;
				default:
					System.out.println("Error");
					break;
				}
			}
			String key = line + "_" + col;
			if (!tiles.containsKey(key))
				tiles.put(key, true);
			else {
				tiles.replace(key, tiles.get(key) == false);
			}
		}
		
		
		// count black tiles
		int nOfBlackTiles = 0;
		for (boolean i : tiles.values()) {
			if (i)
				nOfBlackTiles++;
		}
		System.out.println(nOfBlackTiles);
	}

}
