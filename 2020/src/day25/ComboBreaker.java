package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class ComboBreaker {
	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(new File("i25.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			return;
		}
		
		int publicKeyCard = sc.nextInt();
		int publicKeyDoor = sc.nextInt();
		
		int cardLoopSize = 1;
		int doorLoopSize = 1;
		
		long result = 1;
		while (result != publicKeyCard)
			result = transformNumber(++cardLoopSize, 7);
		
		while (result != publicKeyDoor)
			result = transformNumber(++doorLoopSize, 7);
		
		long encryptionKeyCard = transformNumber(cardLoopSize, publicKeyDoor);
		long encryptionKeyDoor = transformNumber(doorLoopSize, publicKeyCard);
		System.out.println("Loop-Sizes: " + cardLoopSize + ", " + doorLoopSize);
		System.out.println("Encryption-Key: " + encryptionKeyDoor);
	}
	
	static long transformNumber(int loopSize, int subjectNumber) {
//		for (int i = 0; i < loopSize; i++) {
//			result *= subjectNumber;
//			result %= 20201227;
//		}
		BigInteger b1 = BigInteger.valueOf(subjectNumber);
		BigInteger exp = BigInteger.valueOf(loopSize);
		b1 = b1.modPow(exp, new BigInteger("20201227"));
		return b1.longValue();
	}
}
