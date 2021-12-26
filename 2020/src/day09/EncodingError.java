package day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EncodingError {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i09.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		ArrayList<Long> values = new ArrayList<>();
		while (sc.hasNext()) {
			values.add(sc.nextLong());
		}
		int index = 25;
		boolean notFound = false;
		outer:while (!notFound) {
			for (int i = index - 25; i < index && i < values.size(); i++) {
				for (int j = i + 1; j < index && j < values.size(); j++) {
					if (values.get(i) + values.get(j) == values.get(index)) {
						index++;
						continue outer;
					}
				}
			}
			notFound = true;
		}
		System.out.println(values.get(index));
	}
}
