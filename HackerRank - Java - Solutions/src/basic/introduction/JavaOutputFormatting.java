package basic.introduction;

import java.util.Scanner;

public class JavaOutputFormatting {

	public static void main(String[] args) {
		// Solution
		Scanner scanner = new Scanner(System.in);

		System.out.println("================================");
		for (int i = 0; i < 3; i++) {
			String s = scanner.next();
			int n = scanner.nextInt();
			System.out.printf("%-15s%03d%n", s, n);
		}
		System.out.println("================================");

		scanner.close();
	}

}
