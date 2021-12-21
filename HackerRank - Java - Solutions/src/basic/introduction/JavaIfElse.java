package basic.introduction;

import java.util.Scanner;

public class JavaIfElse {

	public static void main(String[] args) {
		// Solution
		Scanner scanner = new Scanner(System.in);

		int n = scanner.nextInt();

		if (n > 20 && n % 2 == 0) {
			System.out.println("Not Weird");
		}

		else if (n > 6 && n <= 20) {
			System.out.println("Weird");
		}

		else if (n > 2 && n <= 5 && n % 2 == 0) {
			System.out.println("Not Weird");
		} else {
			System.out.println("Weird");
		}
		
		scanner.close();

	}

}
