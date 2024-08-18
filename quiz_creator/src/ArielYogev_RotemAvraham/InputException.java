package ArielYogev_RotemAvraham;

import java.util.InputMismatchException;
import java.util.Scanner;

import ArielYogev_RotemAvraham.Question.eDifficulty;

public class InputException {
	public static Scanner sc = new Scanner(System.in);

	public static int inputMisMatchExpc(String msg) throws Exception {
		boolean isNumOk = false;
		int number = 0;
		while (!isNumOk) {
			try {
				System.out.println(msg);
				number = sc.nextInt();
				if (number < 0) {
					throw new Exception("you can't insert negative number,try again");

				}
				isNumOk = true;
			} catch (InputMismatchException e) {
				System.out.println("invalid input, try again");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		return number;
	}


	public static int arrayBoundsExcp(Object[] arr,String msg) {
		boolean isNumOk = false;
		int index =0;
		while (!isNumOk) {
			try {
				System.out.println(msg);
				index = sc.nextInt();
				if (index-1< 0) {
					throw new Exception("you can't insert negative number,try again");
				}
				if (index > arr.length)
					throw new Exception("index out of bounds, try again");
				isNumOk = true;
			} catch (InputMismatchException e) {
				System.out.println("invalid input, try again");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return index;
	}

	public static boolean booleanExcp(String msg) {
		boolean isNumOk = false;
		String IsCorrect = "";
		boolean tOf = false;
		while (!isNumOk) {
			try {
				System.out.println(msg);
				IsCorrect=sc.next();
				if(IsCorrect.equals("true")||IsCorrect.equals("True")||IsCorrect.equals("T")||IsCorrect.equals("t")||IsCorrect.equals("yes")) {
					tOf = true;
					isNumOk=true;
				}
				else if(IsCorrect.equals("false")||IsCorrect.equals("False")||IsCorrect.equals("F")||IsCorrect.equals("f")||IsCorrect.equals("no")) {
					tOf = false;
					isNumOk=true;
				}
				else
					throw new Exception("invalid input,try again (true/false)");

			} catch (InputMismatchException e) {
				System.out.println("invalid input, try again");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		return tOf;
	}
}







