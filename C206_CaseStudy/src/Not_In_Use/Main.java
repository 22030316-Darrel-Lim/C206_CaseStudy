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

		DBData CREDENTIAL1 = new DBData("admin1@admin1", "admin1");//System.out.println(CREDENTIAL1.getUser_access() + " | " + CREDENTIAL1.getUser_name() + "||");
		//DBData CREDENTIAL2 = Authentication.Login("admin2@admin2", "admin2");//System.out.println(CREDENTIAL2.getUser_access() + " | " + CREDENTIAL2.getUser_name() + "||");
		//DBData CREDENTIAL3 = Authentication.Login("vendor1@vendor1", "vendor1");//System.out.println(CREDENTIAL3.getUser_access() + " | " + CREDENTIAL3.getUser_name() + "||");
		//DBData CREDENTIAL4 = Authentication.Login("vendor2@vendor2", "vendor2");//System.out.println(CREDENTIAL4.getUser_access() + " | " + CREDENTIAL4.getUser_name() + "||");
		//DBData CREDENTIAL5 = Authentication.Login("normal1@normal1", "normal1");//System.out.println(CREDENTIAL5.getUser_access() + " | " + CREDENTIAL5.getUser_name() + "||");
		//DBData CREDENTIAL6 = Authentication.Login("normal2@normal2", "normal2");//System.out.println(CREDENTIAL6.getUser_access() + " | " + CREDENTIAL6.getUser_name() + "||");
		DBData CREDENTIAL7 = new DBData("normal3@normal3", "normal3");//System.out.println(CREDENTIAL7.getUser_access() + " | " + CREDENTIAL7.getUser_name() + "||");
		System.out.println(CREDENTIAL7.getUser_access() + " | " + "|Normal3|");
		System.out.println(CREDENTIAL1.getUser_access() + " | " + "|Admin1|");

//		ArrayList<DBData> lis = new ArrayList<DBData>();
//		lis.add(Authentication.Login("admin1@admin1", "admin1"));//System.out.print(CREDENTIAL1.getUser_access() + " | " + CREDENTIAL1.getUser_name() + "||");
//		lis.add(Authentication.Login("normal3@normal3", "normal3"));//System.out.println(CREDENTIAL1.getUser_access() + " | " + CREDENTIAL1.getUser_name());
//		lis.add(CREDENTIAL3);
//		lis.add(CREDENTIAL4);
//		lis.add(CREDENTIAL5);
//		lis.add(CREDENTIAL6);
//		lis.add(CREDENTIAL7);
//		
//		for (int i = 0; i < lis.size(); i++) {
//			print(i);
//			CREDENTIAL = lis.get(i);
//			System.out.println(lis.get(i).getUser_access() + " " + lis.get(i).getUser_name());
//			//print(TableFormatter.tableFormatter(CREDENTIAL.viewAllMenu()));
//		}
		
		
//		line(40, "-");
//		print("== Add Order ==");
//		// Method to view the Food
//		// SQL
//
//		if (CREDENTIAL.getMenuCount() == 0) {
//			print("\nSorry but currently there are no menu available");
//			return;
//		}
//
//		String[][] table = CREDENTIAL.viewAllMenu();
//
//		print(TableFormatter.tableFormatter(table));
//
//		ArrayList<String> item_idList = new ArrayList<String>();
//		ArrayList<String> menu_idList = new ArrayList<String>();
//
//		ArrayList<String> menu_itemList = new ArrayList<String>();
//		
//		for (String[] row : table) {
//			item_idList.add(row[1]);
//			menu_idList.add(row[0]);
//			String menuItem = row[0] + "," + row[1];
//			menu_itemList.add(menuItem);
//		}
//		menu_itemList.remove(0);
//		menu_idList.remove(0);
//		item_idList.remove(0);
//
//		String item_id = readString("Enter Item ID to add into order: ");
//		String menu_id = readString("Enter Menu ID to add into order: ");
//		
//		if (item_idList.contains(item_id) == false) {
//			print("\nWrong item ID entered - Returning back to [Normal MENU]\n");
//			// normalMenu();
//			return;
//		} else if (menu_idList.contains(menu_id) == false) {
//			print("\nWrong item ID entered - Returning back to [Normal MENU]\n");
//			// normalMenu();
//			return;
//		}
//
//		CHOICE = menu_idList.indexOf(item_id) + 1;
//		
//		print("---- Item to Add From Menu ------");
//		print(CREDENTIAL.getItemInfo(item_id));
//
//		String confirm = readString("Confirm Order? (Y/N): ");
//		if (confirm.equalsIgnoreCase("y") == false) {
//			print("Deletation Aborted");
//			// vendorMenu();
//			return;
//		}

		//
		// Get Payment
		//
//		table = CREDENTIAL.viewAllMenu();
//
//		print(TableFormatter.tableFormatter(table));
//
//		menu_idList = new ArrayList<String>();
//
//		for (String[] row : table)
//			menu_idList.add(row[0]);
//		menu_idList.remove(0);
//
//		menu_id = readString("Enter item_id to add into menu: ");
//
//		if (menu_idList.contains(menu_id) == false) {
//			print("\nWrong item ID entered - Returning back to [Vendor MENU]\n");
//			// vendorMenu();
//			return;
//		}
	
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
			print("Welcome " );
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
			print("Welcome " );
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
			print("Welcome ");
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
