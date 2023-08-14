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

	
	private static void viewUserChild() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewUserChild();

		if (table.length == 1) {
			print("\nSorry but currently there are no child added");
			return;
		}

		print(TableFormatter.tableFormatter(table));

	}
	// (DONE) SQL to view all Menu
	private static void viewAllMenu() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllMenu();

		if (table.length == 1) {
			print("\nSorry but currently there are no menu offered by the vendors");
			return;
		}

		print(TableFormatter.tableFormatter(table));

	}
	
	public static void main(String[] args) {

		CREDENTIAL = Authentication.Login("normal1@normal1", "normal1");
//		CREDENTIAL = Authentication.Login("normal2@normal2", "normal2");
//		CREDENTIAL = Authentication.Login("normal3@normal3", "normal3");
//		CREDENTIAL = Authentication.Login("admin1@admin1", "admin1");
//		CREDENTIAL = Authentication.Login("vendor1@vendor1", "vendor1");
		print(CREDENTIAL.getUser_name() + " " + CREDENTIAL.getUser_access() + " " + CREDENTIAL.getUser_id());
		
		//print(TableFormatter.tableFormatter(CREDENTIAL.viewVendorSchoolByMenuItem("9")));
		while (true) {
			
			line(40, "-");
			print("== Add Order ==");

			if (CREDENTIAL.getMenuCount() == 0) {
				print("\nSorry but currently there are no menu available");
				//normalMenu();
			} else if (CREDENTIAL.getPaymentCount() == 0) {
				print("\nSorry but currently there are no payment available");
				//normalMenu();
			} else if (CREDENTIAL.getUserChildCount() == 0) {
				print("\nSorry but currently there are no child available");
				//normalMenu();
			} else if (CREDENTIAL.getSchoolCount() == 0) {
				print("\nSorry but currently there are no school available");
				//normalMenu();
			} 
			
			String[][] table = null;
			
			//
			// Add Menu Item to order
			//
			table = CREDENTIAL.viewAllMenu();

			print(TableFormatter.tableFormatter(table));

			ArrayList<String> menu_itemList = new ArrayList<String>();

			for (String[] row : table) {
				String menuItem = row[0] + "," + row[1];
				menu_itemList.add(menuItem);
			}
			menu_itemList.remove(0);

			String menu_id = readString("Enter Menu ID to add into order: ");
			String item_id = readString("Enter Item ID to add into order: ");
			
			String menu_item_id = menu_id + "," + item_id;
			
			if (menu_itemList.contains(menu_item_id) == false) {
				print("\nWrong Item / Menu ID entered - Returning back to [Normal MENU]\n");
				//normalMenu();
			}

			CHOICE = menu_itemList.indexOf(menu_item_id) + 1;

			print("---- Item to Add From Menu ------");
			print(CREDENTIAL.getItemInfo(item_id));
			print("Menu ID Choosen: " + menu_id);
			
			//
			// Choose school by menu item ID - vendor
			//
			
			table = CREDENTIAL.viewVendorSchoolByMenuItem(String.valueOf(CHOICE));
			
			print(TableFormatter.tableFormatter(table));

			ArrayList<String> SHV_IDList = new ArrayList<String>();

			for (String[] row : table) {
				SHV_IDList.add(row[0]);
			}
			SHV_IDList.remove(0);
			
			String SHV_id = readString("Enter School Has Vendor ID option: ");

			if (SHV_IDList.contains(SHV_id) == false) {
				print("\nWrong ID entered - Returning back to [Normal MENU]\n");
				//normalMenu();
			}
			
			//
			// Add Child
			//
			table = CREDENTIAL.viewUserChild();
			print(CREDENTIAL.getUser_name());

			print(TableFormatter.tableFormatter(table));

			ArrayList<String> child_idList = new ArrayList<String>();

			for (String[] row : table)
				child_idList.add(row[0]);
			child_idList.remove(0);

			String child_id = readString("Enter Child ID option: ");

			if (child_idList.contains(child_id) == false) {
				print("\nWrong Child ID entered - Returning back to [Normal MENU]\n");
				//normalMenu();
			}

			//
			// Add Payment
			//
			table = CREDENTIAL.viewAllPayment();

			print(TableFormatter.tableFormatter(table));

			ArrayList<String> payment_idList = new ArrayList<String>();

			for (String[] row : table)
				payment_idList.add(row[0]);
			payment_idList.remove(0);

			String payment_id = readString("Enter Payment ID option: ");

			if (payment_idList.contains(payment_id) == false) {
				print("\nWrong payment ID entered - Returning back to [Normal MENU]\n");
				//normalMenu();
			}
			
			//
			// Confirm Order
			//
			String confirm = readString("\nConfirm Order? (Y/N): ");
			if (confirm.equalsIgnoreCase("y") == false) {
				print("Deletation Aborted");
				//normalMenu();
			}
		}
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
			print("Welcome ");
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
			print("Welcome ");
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
