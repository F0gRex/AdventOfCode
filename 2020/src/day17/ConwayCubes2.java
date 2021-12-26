package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConwayCubes2 {
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i17.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		boolean[][][][] active = new boolean[1][1][8][8];

		int height = 0;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			for (int i = 0; i < input.length(); i++) {
				switch (input.charAt(i)) {
				case '.':
					active[0][0][height][i] = false;
					break;
				case '#':
					active[0][0][height][i] = true;
					break;
				default:
					System.out.println("Error");
					break;
				}
			}
			height++;
		}
		
		System.out.println("___________" + 0 + "___________");
		printActive(active);
		
		//boot up cycle
		for (int n = 0; n < 6; n++) {
			active = increaseSize(active);
			boolean[][][][] oldActive = copyArray(active);
			
			for (int i = 0; i < active.length; i++) {
				for (int j = 0; j < active[i].length; j++) {
					for (int k = 0; k < active[i][j].length; k++) {
						for (int h = 0; h < active[i][j][k].length; h++)
							active[i][j][k][h] = updateStatus(oldActive, i , j ,k, h);
					}
				}
			}
			System.out.println("___________" + (n + 1) + "___________");
//			printActive(active);
		}
		
		//count active
		int nOfActive = 0;
		for (boolean[][][] i : active) {
			for (boolean[][] j : i) {
				for (boolean[] k : j) {
					for (boolean h : k) {
						if (h)
							nOfActive++;
					}
				}
			}
		}
		System.out.println(nOfActive);
		System.out.println("Took: " + (System.currentTimeMillis() - t) / 1000.0 + " seconds");
		
	}
	
	static boolean updateStatus(boolean[][][][] active, int i ,int j ,int k, int h) {
		int nOfActiveNeighbours = 0;
		for (int ii = (i-1 > 0) ? i -1 : 0; ii <= i + 1 && ii < active.length; ii++) {
			for (int jj = (j-1 > 0) ? j -1 : 0; jj <= j + 1 && jj < active[ii].length; jj++) {
				for (int kk = (k-1 > 0) ? k -1 : 0; kk <= k + 1 && kk < active[ii][jj].length; kk++) {
					for (int hh = (h-1 > 0) ? h -1 : 0; hh <= h + 1 && hh < active[ii][jj][kk].length; hh++) {
						if (!(ii == i && jj == j && kk == k && hh == h) && active[ii][jj][kk][hh])
							nOfActiveNeighbours++;
					}
				}
			}
		}
		if (nOfActiveNeighbours == 3)
			return true;
		else if (nOfActiveNeighbours == 2 && active[i][j][k][h])
			return true;
		else
			return false;
	}
	
	
	static boolean[][][][] copyArray(boolean[][][][] active) {
		boolean[][][][] copy = new boolean[active.length][active[0].length][active[0][0].length][active[0][0][0].length];
		for (int i = 0; i < active.length; i++) {
			for (int j = 0; j < active[i].length; j++) {
				for (int k = 0; k < active[i][j].length; k++) {
					for (int h = 0; h < active[i][j][k].length; h++)
						copy[i][j][k][h] = active[i][j][k][h];
				}
			}
		}
		return copy;
	}
	
	static boolean[][][][] increaseSize(boolean[][][][] active) {
		boolean[][][][] copy = new boolean[active.length + 2][active[0].length + 2][active[0][0].length + 2][active[0][0][0].length + 2];
		for (int i = 0; i < active.length; i++) {
			for (int j = 0; j < active[i].length; j++) {
				for (int k = 0; k < active[i][j].length; k++) {
					for (int h = 0; h < active[i][j][k].length; h++)
						copy[i+1][j+1][k+1][h+1] = active[i][j][k][h];
				}
			}
		}
		return copy;
	}
	
	static void printActive(boolean[][][][] active) {
		int w = -1 * active.length / 2;
		for (boolean[][][] n : active) {
			int z = -1 * active[0].length / 2;
			for (boolean[][] i : n) {
				System.out.println("z = " + z + " and w = " + w);
				for (boolean[] j : i) {
					for (boolean k : j) {
						if (k)
							System.out.print('#');
						else 
							System.out.print('.');
					}
					System.out.println();
				}
				System.out.println();
				z++;
			}
			System.out.println();
			w++;
		}
	}
}
