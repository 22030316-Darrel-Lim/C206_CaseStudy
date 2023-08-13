package Not_In_Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import Helper.Helper;
import Helper.TableFormatter;
import Helper_Package.Authentication;
import Helper_Package.DBData;

public class Main {
	private static DBData CREDENTIAL;
	private static int CHOICE;

	public static void main(String[] args) {

		CREDENTIAL = new DBData("vendor1@vendor1", "vendor1");
		System.out.println(CREDENTIAL.getUser_access() + " " +
		CREDENTIAL.getUser_name());

		line(40, "-");
		print("== DELETE FOOD ITEM ==");
		// Method to view the Food
		// SQL

		String[][] table = CREDENTIAL.viewAllFood();

		print(TableFormatter.tableFormatter(table));

		ArrayList<String> item_idList = new ArrayList<String>();

		for (String[] row : table)
			item_idList.add(row[0]);
		item_idList.remove(0);

		String food_id = readString("Enter item_id to add into menu: ");

		if (item_idList.contains(food_id) == false) {
			print("\nWrong item ID entered - Returning back to [Vendor MENU]\n");
			// vendorMenu();
			return;
		}

		CHOICE = item_idList.indexOf(food_id) + 1;

		String item_id = table[CHOICE][0];
		String item_name = table[CHOICE][1];
		String item_qty = table[CHOICE][2];
		String item_description = table[CHOICE][3];
		String item_dietary = table[CHOICE][4];
		String item_ingredients = table[CHOICE][5];
		String item_price = table[CHOICE][6];

		String row = "" + "\n======= Food =======\n" + "Item ID: %s\n" + "Item name: %s\n" + "Item quantity: %s\n"
				+ "Item description: %s\n" + "Item dietary: %s\n" + "Item ingredients: %s\n" + "Item price: %s\n";

		row = String.format(row, item_id, item_name, item_qty, item_description, item_dietary, item_ingredients,
				item_price);
		print(row);

		String confirm = readString("Confirm Deletetion? (Y/N): ");
		if (confirm.equalsIgnoreCase("y") == false) {
			print("Deletation Aborted");
			// vendorMenu();
			return;
		}
		
		// TODO Run Delete SQL
		print("Deleting item....");
		Boolean isDeleted = CREDENTIAL.deleteItem(food_id);
		
		if (isDeleted == false) {
			print("Delete item Failed");
		} else if (isDeleted == null) {
			print("Wrong access type");
		} else {
			print("Delete item Successful");
		}

	}

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

	private static double readDouble(String str) {
		double d = Helper.readDouble(str);
		return d;
	}

	private static char readChar(String str) {
		char c = Helper.readChar(str);
		c = Character.toLowerCase(c);
		return c;
	}
	// NOT IN USE - USE THE DEFAULT PACKAGE
	// THIS SERVERS AS A WORKSTATION TO AVOID CONFLICT WITH THE DEFAULT CLASS

}
