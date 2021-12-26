package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class JurassicJigsaw2 {
	static HashMap<Integer, boolean[][]> tiles = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i20.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			if (input.isBlank())
				input = sc.nextLine();
			
			int key = Integer.parseInt(input.substring(5,9));
			boolean[][] tile = new boolean[10][10];
			
			for (int i = 0; i < tile.length; i++) {
				input = sc.nextLine();
				for (int j = 0; j < tile[0].length; j++) {
					tile[i][j] = input.charAt(j) == '#';
				}
			}
			tiles.put(key, tile);
		}
		
		int size = (int) Math.sqrt(tiles.size());
		int[][] puzzle = new int[size][size];
		HashSet<Integer> toBePlaced = new HashSet<>(tiles.keySet());
		// assemble puzzle
		
		// get first tile
		for (int i : toBePlaced) {
			puzzle[0][0] = i;
			break;
		}
		
		toBePlaced.remove(puzzle[0][0]);
		placeFirstLineRight(puzzle, toBePlaced, 0);
		placeFirstRowBottom(puzzle, toBePlaced, 0);
		
		// remove the already puzzled row in order to create the top row
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i][1] != 0) {
				for (int j = 1; j < puzzle[i].length; j++) {
					toBePlaced.add(puzzle[i][j]);
					puzzle[i][j] = 0;
				}
			}
		}
		placeFirstLineRight(puzzle, toBePlaced, 0);
		
		
		for (int i = 1; i < puzzle.length; i++) {
			for (int j = 1; j < puzzle[i].length; j++) {
				for (int x : toBePlaced) {
					boolean[][] result1 = connectBottom(tiles.get(puzzle[i-1][j]), tiles.get(x));
					boolean[][] result2 = connectRight(tiles.get(puzzle[i][j-1]), tiles.get(x));
					if (result1 != null && result2 != null) {
						puzzle[i][j] = x;
						tiles.put(x, result1);
						break;
					}
				}
				if (puzzle[i][j] != 0)
					toBePlaced.remove(puzzle[i][j]);
			}
		}
		
		// create big image
		boolean[][] image = new boolean[size * 8][size * 8];
		for (int i = 0; i < image.length; i+=8) {
			for (int j = 0; j < image[i].length; j+=8) {
				boolean[][] tile = tiles.get(puzzle[i/8][j/8]);
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						image[i+k][j+l] = tile[k+1][l+1];
					}
				}
			}
		}
		image = rotate(image);
//		image = mirror(image);
//		image = rotate(image);
		image = rotate(image);
		image = rotate(image);
		
		
		
		for (int k = 0; k < image.length; k++) {
			for (int k2 = 0; k2 < image.length; k2++) {
				if (image[k][k2])
					System.out.print("#");
				else
					System.out.print(".");
			}
			System.out.println();
		}
		int nOfMonsters = findMonsters(image);
		
		int nOfHashTags = 0;
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image.length; j++) {
				if (image[i][j])
					nOfHashTags++;
			}
		}
		System.out.println("Found " + nOfMonsters + " monsters");
		System.out.println("The roughness of the water is: " + (nOfHashTags - 15 * nOfMonsters));
	}
	
	static int findMonsters(boolean[][] image) {
		int nOfMonsters = 0;
		
		for (int n = 1; n <= 8; n++) {
			for (int i = 0; i < image.length-2; i++) {
				for (int j = 19; j < image.length -1; j++) {
					if (image[i][j] 
						&& image[i+1][j-18] && image[i+1][j-13] && image[i+1][j-12] 
						&& image[i+1][j-7] && image[i+1][j-6] && image[i+1][j-1] && image[i+1][j] && image[i+1][j+1] 
						&& image[i+2][j-17] && image[i+2][j-14] && image[i+2][j-11] && image[i+2][j-8] && image[i+2][j-5] && image[i+2][j-2]) {
						nOfMonsters++;
						System.out.println("Found at: " + i + ", " + j);
					}
						
				}
			}
			
			if (nOfMonsters != 0) 
				return nOfMonsters;
			else {
				image = rotate(image);
				if (n == 4)
					image = mirror(image);
			}
		}
		
		return nOfMonsters;
	}
	
	static void placeFirstRowBottom(int[][] puzzle, HashSet<Integer> toBePlaced, int row) {
		int lastIndex = 0;
		for (int i = 0; i < puzzle.length - 1; i++) {
			for (int x : toBePlaced) {
				boolean[][] result = connectBottom(tiles.get(puzzle[i][row]), tiles.get(x));
				if (result != null) {
					puzzle[i+1][row] = x;
					tiles.put(x, result);
					lastIndex++;
					break;
				}
			}
			if (puzzle[i+1][row] != 0)
				toBePlaced.remove(puzzle[i+1][row]);
			else 
				break;
		}
		if (lastIndex != puzzle.length - 1) {
			//shift puzzle to the bottom, should only be called once
			if (toBePlaced.size() != puzzle.length * (puzzle.length - 1) - lastIndex)
				System.out.println("The found part solution does not make sense");
			for (int i = puzzle.length - 1; i >= 0; i--) {
				for (int j = 0; j < puzzle[i].length; j++) {
					if (i >= puzzle.length - (lastIndex + 1))
						puzzle[i][j] = puzzle[i-(puzzle.length - (lastIndex + 1))][j];
					else
						puzzle[i][j] = 0;
				}
			}
			lastIndex = puzzle.length - (lastIndex + 1);
		}
		
		//fill the remaining pieces to the top
		for (int i = lastIndex; i > 0; i--) {
			for (int x : toBePlaced) {
				boolean[][] result = connectTop(tiles.get(puzzle[i][row]), tiles.get(x));
				if (result != null) {
					puzzle[i-1][row] = x;
					tiles.put(x, result);
					lastIndex++;
					break;
				}
			}
			if (puzzle[i-1][row] != 0)
				toBePlaced.remove(puzzle[i-1][row]);
		}
	}
	
	
	static void placeFirstLineRight(int[][] puzzle, HashSet<Integer> toBePlaced, int line) {
		int lastIndex = 0;
		for (int i = 0; i < puzzle[line].length - 1; i++) {
			for (int x : toBePlaced) {
				boolean[][] result = connectRight(tiles.get(puzzle[line][i]), tiles.get(x));
				if (result != null) {
					puzzle[line][i+1] = x;
					tiles.put(x, result);
					lastIndex++;
					break;
				}
			}
			if (puzzle[line][i+1] != 0)
				toBePlaced.remove(puzzle[line][i+1]);
			else 
				break;
		}
		if (lastIndex != puzzle[0].length - 1) {
			//shift puzzle to the right should only be called once
			if (toBePlaced.size() != puzzle.length * puzzle.length - (lastIndex + 1))
				System.out.println("The found part solution does not make sense");
			for (int i = puzzle[line].length - 1; i >= 0; i--) {
				if (i >= puzzle.length - (lastIndex + 1))
					puzzle[line][i] = puzzle[line][i-(puzzle.length - (lastIndex + 1))];
				else
					puzzle[line][i] = 0;
			}
			lastIndex = puzzle.length - (lastIndex + 1);
		}
		
		//fill the remaining pieces to the left
		for (int i = lastIndex; i > 0; i--) {
			for (int x : toBePlaced) {
				boolean[][] result = connectLeft(tiles.get(puzzle[line][i]), tiles.get(x));
				if (result != null) {
					puzzle[line][i-1] = x;
					tiles.put(x, result);
					lastIndex++;
					break;
				}
			}
			if (puzzle[line][i-1] != 0)
				toBePlaced.remove(puzzle[line][i-1]);
		}
	}
	
	//checks if two tiles can be connected 
	static boolean[][] connectRight(boolean[][] tile1, boolean[][] tile2) {
		for (int n = 1; n <= 8; n++) {
			boolean fits = true;
			for (int i = 0; i < tile1.length; i++) {
				if (tile1[i][tile1.length-1] != tile2[i][0])
					fits = false;
			}
			if (fits) 
				return tile2;
			else {
				tile2 = rotate(tile2);
				if (n == 4)
					tile2 = mirror(tile2);
			}
		}
		
		// does not fit there;
		return null;
	}
	
	//checks if two tiles can be connected 
	static boolean[][] connectLeft(boolean[][] tile1, boolean[][] tile2) {
		for (int n = 1; n <= 8; n++) {
			boolean fits = true;
			for (int i = 0; i < tile1.length; i++) {
				if (tile1[i][0] != tile2[i][tile2.length-1])
					fits = false;
			}
			if (fits) 
				return tile2;
			else {
				tile2 = rotate(tile2);
				if (n == 4)
					tile2 = mirror(tile2);
			}
		}
		
		// does not fit there
		return null;
	}
	
	//checks if two tiles can be connected 
	static boolean[][] connectTop(boolean[][] tile1, boolean[][] tile2) {
		for (int n = 1; n <= 8; n++) {
			boolean fits = true;
			for (int i = 0; i < tile1.length; i++) {
				if (tile1[0][i] != tile2[tile2.length-1][i])
					fits = false;
			}
			if (fits) 
				return tile2;
			else {
				tile2 = rotate(tile2);
				if (n == 4)
					tile2 = mirror(tile2);
			}
		}
		
		// does not fit there
		return null;
	}
	
	//checks if two tiles can be connected 
	static boolean[][] connectBottom(boolean[][] tile1, boolean[][] tile2) {
		for (int n = 1; n <= 8; n++) {
			boolean fits = true;
			for (int i = 0; i < tile1.length; i++) {
				if (tile1[tile1.length-1][i] != tile2[0][i])
					fits = false;
			}
			if (fits) 
				return tile2;
			else {
				tile2 = rotate(tile2);
				if (n == 4)
					tile2 = mirror(tile2);
			}
		}

		// does not fit there
		return null;
	}
	
	//rotates the array 90 degrees clockwise
	static boolean[][] rotate(boolean[][] tile) {
		boolean[][] newTile = new boolean[tile.length][tile[0].length];
		
		for (int i = 0; i < newTile.length; i++) {
			for (int j = 0; j < newTile[0].length; j++) {
				newTile[i][j] = tile[tile.length - (j+1)][i];
			}
		}
		return newTile;
	}
	

	//mirror the array at the vertical axis
	static boolean[][] mirror(boolean[][] tile) {
		boolean[][] newTile = new boolean[tile.length][tile[0].length];
		
		for (int i = 0; i < newTile.length; i++) {
			for (int j = 0; j < newTile[0].length; j++) {
				newTile[i][j] = tile[i][tile.length - (j+1)];
			}
		}
		return newTile;
	}
}