package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandheldHalting2 {	
	
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
		int lastI = 0;
		int lastVal = 0;
		int newVal = 0;
		int accu = 0;
		
		while (lastI != num.size() - 1) {
			//change last instruction back
			if (lastVal != newVal) {
				if (inst.get(lastVal).equals("jmp"))
					inst.set(lastVal, "nop");
				else 
					inst.set(lastVal, "jmp");
			}
			
			//change new instruction
			while (inst.get(newVal).equals("acc"))
				newVal++;
			if (inst.get(newVal).equals("jmp"))
				inst.set(newVal, "nop");
			else 
				inst.set(newVal, "jmp");
			lastVal = newVal;
			newVal++;
			
			
			boolean[] visited = new boolean[inst.size()];
			accu = 0;
			int i = 0;
			while (i < inst.size() && !visited[i]) {
				visited[i] = true;
				lastI = i;
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
		} 
		
		System.out.println(accu);
	}
}
