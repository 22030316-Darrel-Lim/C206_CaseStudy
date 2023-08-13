package Not_In_Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import Helper.Helper;
import Helper.TableFormatter;
import Helper_Package.Authentication;
import Helper_Package.DBData;

public class Main {
	private static DBData CREDENTIAL;
	private static int CHOICE;

	public static void main(String[] args) {

		CREDENTIAL = new DBData("admin1@admin1", "admin1");
		System.out.println(CREDENTIAL.getUser_access() + " " +
		CREDENTIAL.getUser_name());
		
		String t = TableFormatter.tableFormatter(CREDENTIAL.viewAllPayment());
		print(t);
	}

	@SuppressWarnings("unused")
	private static String getVendorMenu() {
		String[] vendorInfo = CREDENTIAL.getVendorInfo();

		String[] menu = vendorInfo[7].split(",");

		print("----- Avaible Menu -----");
		String[][] table = new String[menu.length + 1][1];
		table[0][0] = "Menu_ID";

		for (int i = 0; i < menu.length; i++) {
			table[i + 1][0] = menu[i];
		}

		print(TableFormatter.tableFormatter(table));

		String menuChoice = readString("Enter menu ID to add item in: ");
		boolean contains = Arrays.asList(menu).contains(menuChoice);

		if (contains != true) {
			print("\nWrong Menu ID entered - Returning back to [ADD FOOD TO MENU]\n");
			menuChoice = null;
		}
		return menuChoice;
	}

	@SuppressWarnings("unused")
	private static void displayMenu(String menuType) {
		switch (menuType) {
		case "main":
			print("");
			line(40, "-");
			print("====== SCHOOL LUNCH BOX MAIN MENU ======");
			line(40, "-");
			print("\n======= Menu =======");
			print("1. Login");
			print("2. Register");
			print("9. Quit");
			line(20, "-");
			break;
		case "normal":
			print("");
			line(40, "-");
			print("============== NORMAL MENU =============");
			print("Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("1) View All Menu");
			print("2) Add a New Order");
			print("3) View Order");
			print("9) Exit");
			line(20, "-");
			break;
		case "vendor":
			print("");
			line(40, "-");
			print("============== VENDOR MENU =============");
			print("Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("1) View All Menu");
			print("2) Add new Menu");
			print("3) Delete Menu");
			print("4) Add new Item");
			print("5) Delete Item");
			print("9) Exit");
			line(20, "-");
			break;
		case "admin":
			print("");
			line(40, "-");
			print("============== ADMIN MENU ==============");
			print("Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("1) View All Users");
			print("2) View All Schools");
			print("3) View All Menus");
			print("4) View All Orders");
			print("5) Create User");
			print("6) Delete User");
			print("9) Exit");
			line(20, "-");
			break;
		case "addFoodItem":
			line(40, "-");
			print("=== ADD FOOD TO MENU ===");
			line(40, "-");
			print("1) Add exisitng food to menu");
			print("2) Create food and add it to menu");
			print("9) Exit");
			break;
		default:
			print("\n=== MENU FAILED TO LOAD ====");
			return;
		}

	}

	private static void print(String str) {
		System.out.println(str);
	}

	@SuppressWarnings("unused")
	private static void print(int str) {
		System.out.println(str);
	}

	@SuppressWarnings("unused")
	private static void print(boolean str) {
		System.out.println(str);
	}

	@SuppressWarnings("unused")
	private static void print() {
		System.out.println();
	}

	private static void line(int count, String str) {
		Helper.line(count, str);
	}

	private static int readInt(String str) {
		int i = Helper.readInt(str);
		return i;
	}

	private static String readString(String str) {
		String d = Helper.readString(str);
		return d;
	}

	@SuppressWarnings("unused")
	private static double readDouble(String str) {
		double d = Helper.readDouble(str);
		return d;
	}

	@SuppressWarnings("unused")
	private static char readChar(String str) {
		char c = Helper.readChar(str);
		c = Character.toLowerCase(c);
		return c;
	}
	// NOT IN USE - USE THE DEFAULT PACKAGE
	// THIS SERVERS AS A WORKSTATION TO AVOID CONFLICT WITH THE DEFAULT CLASS

	/**
	 * Method is about validating name by checking if name is all in alphabetical
	 * 
	 * @param name
	 * @return true if name matches
	 */
	private static boolean isName(String name) {

		// Match name using RegEx
		String pattern = "[a-zA-Z]+";

		boolean matched = Pattern.matches(pattern, name);

		if (matched != true) {
			System.out.println("\nError - Name is not all in alphabet\n");
		}
		return matched;
	} // End of isName

	/**
	 * Method is about validating email by checking if email contains @ and .
	 * 
	 * @param email
	 * @return true if email matches
	 */
	private static boolean isEmail(String email) {

		// Match email using RegEx
		String pattern = "[a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]+";

		boolean matched = Pattern.matches(pattern, email);

		if (matched != true) {
			System.out.println("\nError - Email should contains @ and . [Name@email.com]\n");
		}
		return matched;
	} // End of isEmail

	/**
	 * Method is about validating the password strength using regex.
	 * 
	 * @param password
	 * @return true if password is strong (at least one capital letter and at least
	 *         8 characters long)
	 */
	private static boolean isPassword(String password) {

		// Match password using RegEx
		String pattern = "^(?=.*[A-Z]).{8,}$";

		boolean matched = Pattern.matches(pattern, password);

		if (matched != true) {
			System.out
					.println("\nError - Password contain at least one capital letter and at least 8 characters long\n");
		}
		return matched;
	} // End of isPassword

}
