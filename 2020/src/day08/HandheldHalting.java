package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandheldHalting {	
	
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i08.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		ArrayList<String> inst = new ArrayList<>();
		ArrayList<Integer> num = new ArrayList<>();
	
		while (sc.hasNextLine()) {
			inst.add(sc.next());
			num.add(sc.nextInt());
		}
		
		boolean[] visited = new boolean[inst.size()];
		int accu = 0;
		int i = 0;
		while (!visited[i]) {
			visited[i] = true;
			switch (inst.get(i)) {
			case "nop":
				i++;
				break;
			case "acc":
				accu += num.get(i);
				i++;
				break;
			case "jmp":
				i += num.get(i);
				break;
			default:
				System.out.println("Error");
			}
		}
			
			
		System.out.println(accu);
	}
}
