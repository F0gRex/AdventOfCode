package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LobbyLayout2 {
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
		
		HashMap<String, Boolean> tileMap = new HashMap<>();
		int minLine = 0;
		int maxLine = 0;
		int minCol = 0;
		int maxCol = 0;
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
			if (!tileMap.containsKey(key))
				tileMap.put(key, true);
			else {
				tileMap.replace(key, tileMap.get(key) == false);
			}
			
			minLine = Math.min(minLine, line);
			maxLine = Math.max(maxLine, line);
			minCol = Math.min(minCol, col);
			maxCol = Math.max(maxCol, col);
		}
		// make sure that that the origin is in the even line
		if (Math.abs(minLine % 2) == 0)
			minLine--;
		boolean[][] tiles = new boolean[maxLine-minLine + 3][maxCol-minCol + 3];
		
		// transfer tiles (black is true)
		for (String s: tileMap.keySet()) {
			if (tileMap.get(s)) {
				int divPoint = s.indexOf("_");
				int line = Integer.parseInt(s.substring(0,divPoint));
				int col = Integer.parseInt(s.substring(1+ divPoint));
				tiles[line - minLine + 1][col - minCol + 1] = true;
			}
		}
		
		// calculate the steps
		for (int n = 0; n < 100; n++) {
			tiles = increaseSize(tiles);
			boolean[][] oldTiles = copyArray(tiles);
//			printTiles(tiles);
			for (int i = 1; i < tiles.length - 1; i++) {
				for (int j = 1; j < tiles[i].length - 1; j++)
						tiles[i][j] = updateStatus(oldTiles, i , j);
			}
//			System.out.println();
//			printTiles(tiles);
		}
		
		// count black tiles
		int nOfBlackTiles = 0;
		for (boolean[] line : tiles) {
			for (boolean tile : line) {
			if (tile)
				nOfBlackTiles++;
			}
		}
		System.out.println(nOfBlackTiles);
	}
	
	static boolean updateStatus(boolean[][] tiles, int i ,int j) {
		int nOfBlackTiles = 0;
		if (tiles[i][j-1])
			nOfBlackTiles++;
		if (tiles[i][j+1])
			nOfBlackTiles++;
		if (tiles[i-1][j])
			nOfBlackTiles++;
		if (tiles[i+1][j])
			nOfBlackTiles++;
		int hexagonIndex = (i % 2 == 1) ? j - 1: j + 1;
		if (tiles[i-1][hexagonIndex])
			nOfBlackTiles++;
		if (tiles[i+1][hexagonIndex])
			nOfBlackTiles++;
		
		if (tiles[i][j] && (nOfBlackTiles == 0 || nOfBlackTiles > 2))
			return false;
		else if (!tiles[i][j] && nOfBlackTiles == 2)
			return true;
		else
			return tiles[i][j];
	}
	
	
	static boolean[][] copyArray(boolean[][] tiles) {
		boolean[][] copy = new boolean[tiles.length][tiles[0].length];
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++)
					copy[i][j] = tiles[i][j];
		return copy;
	}
	
	static boolean[][] increaseSize(boolean[][] tiles) {
		// added 3 to vertical dim in order to preserve even indexed rows
		boolean[][] copy = new boolean[tiles.length + 3][tiles[0].length + 2];
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++)
					copy[i+2][j+1] = tiles[i][j];
		return copy;
	}
	
	// print Pattern
	static void printTiles(boolean[][] tiles) {
		for (boolean[] line : tiles) {
			for (boolean tile : line) {
			if (tile) 
				System.out.print(" ");
			else
				System.out.print("#");
			}
			System.out.println();
		}
	}
}
