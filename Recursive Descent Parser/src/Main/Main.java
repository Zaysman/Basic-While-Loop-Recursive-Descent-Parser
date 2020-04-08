package Main;
//imports


//libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

	static File file = new File("Sample While Loop");

	static LinkedList<String> LList = new LinkedList<String>();
	static int LListIndex = 0;

	static String token = new String();

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Take input

		Scanner input = new Scanner(file);
		String line = new String();

		while(input.hasNextLine() == true) {
			line = input.nextLine();

			Scanner scan = new Scanner(line);

			while(scan.hasNext() == true) {
				String str = scan.next();
				handleString(str);
			}
			scan.close();
		}
		input.close();

		clearEmptySpaces(LList);
		printList(LList);
		System.out.print("\n");

		rdp();
	}

	public static void clearEmptySpaces(List<String> list) {
		for(int i = 0; i < list.size(); i++) {
			String str = (String) list.get(i);
			if(str.isEmpty() == true) {
				list.remove(i);
				i--;
			}
		}
	}

	public static void printList(List<String> list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.println("list(" + i + ")" + list.get(i));
		}
	}

	public static void handleString(String str) {
		if(hasSpecialCharacter(str) == false) {
			LList.add(str);
		} else {
			spliceString(str);
		}
	}

	public static void spliceString(String str) {
		int special = specialCharacterAt(str);
		String substr = str.substring(0, special);
		LList.add(substr);

		LList.add(Character.toString(str.charAt(special)));
		substr = str.substring(special + 1, str.length());
		handleString(substr);
	}

	/*
	 * The purpose of this method is to determine if a special character exists within a string. If so, returns true. else returns false
	 * 
	 * str - String parameter
	 */

	public static boolean hasSpecialCharacter(String str) {

		for(int i = 0; i < str.length(); i++) {
			if(checkSpecialCharacter(str.charAt(i)) == true) {
				return true;
			}
		}

		return false;
	}

	public static int specialCharacterAt(String str) {

		for(int i = 0; i < str.length(); i++) {
			if(checkSpecialCharacter(str.charAt(i)) == true) {
				return i;
			}
		}

		return -1;
	}

	public static boolean checkSpecialCharacter(char ch) {

		switch(ch) {
		case ';' :
		case '!' :
		case '#' :
		case '"' :
		case '$' :
		case '%' :
		case '&' :
		case '(' :
		case ')' :
		case '*' :
		case '+' :
		case ',' :
		case '-' :
		case '.' :
		case '/' :
		case ':' :
		case '<' :
		case '=' :
		case '>' :
		case '?' :
		case '@' :
		case '[' :
		case '\\' :
		case ']' :
		case '^' :
		case '_' :
		case '`' :
		case '{' :
		case '}' :
		case '|' :
		case '~' :	
			return true;
		default:
			return false;
		}
	}



	public static void getToken() {
		if(LListIndex >= LList.size()) {
			return;
		} else {
			token = LList.get(LListIndex++);
		}
	}

	public static void error() {
		System.out.println("Error!");
		System.exit(0);
	}

	/*
	 * Recursive Descent Parser
	 * parser for while loop
	 * <While expression> = while(expression) { statementlist }
	 */

	public static void rdp() {
		System.out.println("Entering While Expression");
		getToken();

		/*Looks for the while keyword for the while keyword to start the statement  */
		whileKey();

		/*Looks for the left parenthesis to start the expression */
		leftParenthesis();

		/*Start looking for the beginning of the expression */
		expr();

		/*Looks for the right parenthesis to end the expression */
		rightParenthesis();

		/*Looks for the left curly brace*/
		statementList();
	}

	public static void whileKey() {
		if(token.compareTo("while") == 0) {
			System.out.println("Next token is: " + token);
			getToken();
		} else {
			error();
		}

	}

	public static void leftParenthesis() {
		System.out.println("Entering Parenthesis");
		if(token.compareTo("(") == 0) {
			System.out.println("Next token is: " + token);
			getToken();

		} else {
			error();
		}
	}

	//Expression
	public static void expr() {
		System.out.println("Entering expression");

		/*Looks for the id of the expression */
		id();

		/*Looks for the comparison operator */
		comparisonOP();

		/*Looks for the integer constant */
		integerConstant();
	}

	public static void id() {
		if(Character.isLetter(token.charAt(0)) == true) {
			System.out.println("Next token is: " + token);
			getToken();
		} else {
			error();
		}
	}

	public static void comparisonOP() {
		switch(token) {
		case "==":
		case "!=":
		case "<":
		case "<=":
		case ">":
		case ">=":
			System.out.println("Next token is: " + token);
			getToken();
			return;
		default:
			error();

		}
	}

	public static void integerConstant() {
		if(Integer.parseInt(token) ==  Math.floor(Double.parseDouble(token))) {
			System.out.println("Next token is: " + token);
			getToken();
			System.out.println("Exiting Expression");
		} else {
			error();
		}


	}


	public static void rightParenthesis() {
		if(token.compareTo(")") == 0) {
			System.out.println("Next token is: " + token);
			getToken();
			System.out.println("Exiting Parenthesis");
		} else {
			error();
		}
	}

	public static void statementList() {
		if(token.compareTo("{") == 0) {
			System.out.println("Entering Statement List");
			System.out.println("Next token is: " + token);
			getToken();
			/*Look for statements */
			statement();


		} else {
			error();
		}
	}

	public static void statement() {
		if(token.compareTo("}") == 0) {
			System.out.println("Next Token is: " + token);
			System.out.println("Exiting Statement List");
		}
		
		String statement = new String();
		String str = new String();

		while(str.compareTo(";") != 0) {
			str = token;
			statement += str;
			getToken();
		}

		System.out.println("Next Token is: " + statement);
		statement();
	}
}
