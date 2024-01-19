package com.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerHelper {

	public static int getValidInt(Scanner scanner, String wrong_message) {
		try {						
			int opcion = scanner.nextInt();
			scanner.nextLine();
			return opcion;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.err.print(wrong_message);	
			scanner.nextLine();
			return getValidInt(scanner, wrong_message);
		}
	}
	
	public static float getValidFloat(Scanner scanner, String wrong_message) {
		try {						
			float opcion = scanner.nextFloat();
			scanner.nextLine();
			return opcion;
		} catch (InputMismatchException e) {
			// TODO: handle exception
			System.err.print(wrong_message);	
			scanner.nextLine();
			return getValidFloat(scanner, wrong_message);
		}
	}
}
