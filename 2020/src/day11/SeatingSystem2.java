package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SeatingSystem2 {
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i11.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		int height = 0;
		int width = 0;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			height++;
			width = input.length();
		}
		int[][] seats = new int[height][width];
		
		//transfer input to array
		try {
			sc = new Scanner(new File("i11.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		height = 0;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			for (int i = 0; i < width; i++) {
				char s = input.charAt(i);
				switch (s) {
				case '.':
					seats[height][i] = -1;
					break;
				case '#':
					seats[height][i] = 1;
					break;
				case 'L':
					seats[height][i] = 0;
					break;
				default:
					System.out.println("Error");
					break;
				}
			}
			height++;
		}
		int[][] newSeats = new int[height][width];
		for (int i = 0; i < newSeats.length; i++) {
			for (int j = 0; j < newSeats[0].length; j++) {
				newSeats[i][j] = seats[i][j];
			}
		}
		
		do {
			for (int i = 0; i < seats.length; i++) {
				for (int j = 0; j < seats[0].length; j++) {
					seats[i][j] = newSeats[i][j];
				}
			}
			for (int i = 0; i < newSeats.length; i++) {
				for (int j = 0; j < newSeats[0].length; j++) {
					int res = checkSeats(seats, i, j);
					if (newSeats[i][j] == 1 && res == -1) {
						newSeats[i][j] = 0;
					}
					else if (newSeats[i][j] == 0 && res == 1)
						newSeats[i][j] = 1;
				}
			}
//			for (int i = 0; i < newSeats.length; i++) {
//				System.out.println(Arrays.toString(newSeats[i]));
//			}
//			System.out.println();
		} while (!checkEquality(seats, newSeats));
		
		
		//count number of occupied seats;
		int occupied = 0;
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[0].length; j++) {
				if (seats[i][j] == 1)
					occupied++;
			}
		}
		System.out.println(occupied);
	}
	
	static int checkSeats(int[][] seats, int i, int j) {
		int adjSeats = 0;
		for (int k = i-1; k <= i+1; k++) {
			for (int h = j-1; h <= j+1; h++) {
				if (k == i && h == j || h < 0 || k < 0 || k >= seats.length || h >= seats[i].length)
					continue;
				else if (seats[k][h] == 1)
					adjSeats++;
			}
		}
		if (adjSeats >= 4)
			return -1;
		if (adjSeats == 0)
			return 1;
		else
			return 0;
	}
	
	static boolean checkEquality(int[][] arr1, int[][] arr2) {
		boolean equal = true;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				if (arr1[i][j] != arr2[i][j])
					equal = false;
			}
		}
		return equal;
	}
}
