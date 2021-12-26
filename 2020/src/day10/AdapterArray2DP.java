package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class AdapterArray2DP {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner myS = new Scanner(new File("i10.txt"));
		LinkedList<Integer> l = new LinkedList<Integer>();
		while (myS.hasNextInt()) {
			l.add(myS.nextInt());
		}
		l.sort(null);
		// I start with making an array for every value up to the last adapter, so I
		// have indexes for which I have no adapter in the array
		// l is the array of my input
		long[] arr = new long[Collections.max(l) + 1];
		// I change the numbers to 1 for each index where I have an adapter, rest is 0
		for (int a : l)
			arr[a] = 1;

		// in my dp array, the value of index[i] is the numbers of possible combinations
		// up to that adapter
		// then for every index I check if I have an adapter for it. if I do, I look at
		// the values of the previous three indexes, and
		// for any where I had a valid adapter, I sum them up. last value is the result
		arr[0] = 1;
		for (int i = 2; i < arr.length; i++) {
			if (i == 2 && arr[i] == 1)
				arr[i] = arr[0] + arr[1];
			else if (i > 2 && arr[i] == 1)
				arr[i] = arr[i - 3] + arr[i - 2] + arr[i - 1];
		}
		System.out.println(arr[arr.length - 1]);

	}

}