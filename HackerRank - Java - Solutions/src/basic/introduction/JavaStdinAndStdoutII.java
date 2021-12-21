package basic.introduction;

import java.util.Scanner;

public class JavaStdinAndStdoutII {

	public static void main(String[] args) {
		// Solution
		Scanner scanner = new Scanner(System.in);

		int i = scanner.nextInt();
		double d = scanner.nextDouble();
		scanner.nextLine();
		String s = scanner.nextLine();

		System.out.println("String: " + s);
		System.out.println("Double: " + d);
		System.out.println("Int: " + i);

		scanner.close();

	}

}
