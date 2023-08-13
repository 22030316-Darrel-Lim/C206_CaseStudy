import Helper_Package.Authentication;
import Helper_Package.DBData;
import Temp_Holding.RunAllTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import Helper.Helper;
import Helper.TableFormatter;

public class C206_CaseStudy {

	private static DBData CREDENTIAL;
	private static int CHOICE;

	public static void main(String[] args) {
		if (RunAllTest.runDBDataTest() == false) {
			return;
		} else {
			System.out.println("Loading Completed");
		}

		CHOICE = -1;

		while (CHOICE != 9) {

			displayMenu("main");
			CHOICE = readInt("Enter choice: ");
			line(20, "-");

			switch (CHOICE) {
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 9:
				thankYou();
				break;
			default:
				invalidChoice();
			}

		}
	}

	private static void register() {

		boolean validation = false;

		String name = null;
		String email = null;
		String password = null;
		String phoneNo = null;
		String allergies = null;
		String address = null;

		while (validation != true) {
			name = readString("Enter name: ");
			if (isName(name) == false) {
				continue;
			}

			email = readString("Enter email: ");
			if (isEmail(email) == false) {
				continue;
			}

			password = readString("Enter password: ");
			if (isPassword(password) == false) {
				continue;
			}

			phoneNo = String.valueOf(readInt("Enter phone number: "));
			allergies = readString("Enter allergies: ");
			address = readString("Enter address: ");

			validation = true;
		}

		String[] otherInfo = { phoneNo, allergies, address };

		CREDENTIAL = Authentication.RegisterAccountNormal(name, email, password, otherInfo);

		if (CREDENTIAL != null) {
			print("\nRegistration successful!\n");
			normalMenu(); // Bring user to normal menu
		} else {
			print("\nRegistration failed.\n");
			main(null); // Return back to main if fail
		}
	}

	private static void login() {
		String email = readString("Enter email: ");
		String password = readString("Enter password: ");

		CREDENTIAL = Authentication.Login(email, password);

		if (CREDENTIAL != null && CREDENTIAL.getUser_access() != null) {
			print("\nLogin successful!");

		} else {
			print("\nLogin failed. Email or Password Incorrect!!");
			main(null); // Return back to main if fail
		}

		String userAccessType = CREDENTIAL.getUser_access();

		switch (userAccessType) {
		case "normal":
			normalMenu();
			break;
		case "vendor":
			vendorMenu();
			break;
		case "admin":
			adminMenu();
			break;
		default:
			print("\nLogin Failed - Unknown access type.");
			main(null); // Return back to main if fail
		}
	}

	private static void normalMenu() {

		CHOICE = -1;

		while (CHOICE != 9) {

			displayMenu("normal");
			CHOICE = readInt("Enter Option: ");
			line(20, "-");

			switch (CHOICE) {
			case 1:
				// Call method to see all menu
				viewAllMenu();
				break;
			case 2:
				// TODO Create a new order + payment

				break;
			case 3:
				// TODO View current order
				break;
			case 9:
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
	}

	private static void vendorMenu() {
		CHOICE = -1;

		while (CHOICE != 9) {

			displayMenu("vendor");
			CHOICE = readInt("Enter Option: ");

			switch (CHOICE) {
			case 1:
				// Call method to see all menu
				viewAllMenu();
				break;
			case 2:
				// New Menu + add Items
				addFoodItem();
				break;
			case 3:
				// Delete Menu
				deleteFoodMenu();
				break;
			case 4:
				// Add new menu
				addNewMenu();
				break;
			case 5:
				// Delete Item
				deleteFoodItem();
				break;
			case 6:
				// Link School to Vendor
				addVendorToSchool();
				break;
			case 9:
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
	}

	private static void adminMenu() {
		CHOICE = -1;

		while (CHOICE != 9) {

			displayMenu("admin");
			CHOICE = readInt("Enter Option: ");
			line(20, "-");

			switch (CHOICE) {
			case 1:
				// Call method to view all User
				viewAllUser();
				break;
			case 2:
				// View all School
				viewAllSchool();
				break;
			case 3:
				// View all Menu
				viewAllMenu();
				break;
			case 4:
				// View All Orders
				viewAllOrder();
				break;
			case 5:
				// Call Method to Create User
				createUser();
				break;
			case 6:
				// Delete User
				deleteUser();
				break;
			case 7:
				// Add school
				addSchool();
				break;
			case 9:
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
	}

	// ==========================
	// Methods For USER
	// ==========================

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

	// ==========================
	// Methods For VENDOR
	// ==========================

	// (DONE) Method for Vendor to delete the Menu and Food
	// Test if there is no menu
	private static void deleteFoodMenu() {
		line(40, "-");
		print("=== DELETE FOOD MENU ===");
		line(40, "-");

		// Check if menu is available to add item
		int currentMenu = CREDENTIAL.getMenuCount();

		if (currentMenu == 0) {
			print("\nSorry but currently there are no available menu to delete\n");
			return;
		}

		// Run SQL Statement to get Menu_id
		String[] vendorInfo = CREDENTIAL.getVendorInfo();

		String[] menu = vendorInfo[7].split(",");

		print("----- Avaible Menu -----");
		String[][] table = new String[menu.length + 1][1];
		table[0][0] = "Menu_ID";

		for (int i = 0; i < menu.length; i++) {
			table[i + 1][0] = menu[i];
		}

		print(TableFormatter.tableFormatter(table));

		String menuChoice = readString("Select Menu_ID to delete: ");
		boolean contains = Arrays.asList(menu).contains(menuChoice);

		if (contains != true) {
			print("\nWrong Menu ID entered - Returning back to [Vender Menu]\n");
			vendorMenu();
		}

		// Run SQL Statement to delete base on Menu_Id
		print("Deleting menu....");
		Boolean isDeleted = CREDENTIAL.deleteMenu(menuChoice);

		if (isDeleted == false) {
			print("Delete Menu Failed");
		} else if (isDeleted == null) {
			print("Wrong access type");
		} else {
			print("Delete Menu Successful");
		}
		vendorMenu();
	}

	// (DONE - Testing NEEDED) Method for Vendor to add new food
	// Test if there is no menu
	private static void addFoodItem() {
		// Check if menu is available to add item
		int currentMenu = CREDENTIAL.getMenuCount();

		if (currentMenu == 0) {
			print("\nSorry but currently there are no menu to add items in\n");
			return;
		}

		// Looping for input
		CHOICE = -1;
		String menuChoice;
		char YESNO;
		Boolean isSuccessful;

		while (CHOICE != 9) {

			displayMenu("addFoodItem");

			CHOICE = readInt("Enter Option: ");

			switch (CHOICE) {
			case 1:

				String[] vendorInfo = CREDENTIAL.getVendorInfo();

				if (vendorInfo.length != 8) {
					print("[Add exisitng food to menu] Cant be choosen as currently there is no menu to your account");
					continue;
				}

				String[][] table = CREDENTIAL.viewAllFood();

				print(TableFormatter.tableFormatter(table));

				int countItem = CREDENTIAL.getItemCount();

				if (countItem == 0) {
					print("There are currently no items in the DB");
					continue;
				}

				ArrayList<String> item_idList = new ArrayList<String>();

				for (String[] row : table)
					item_idList.add(row[0]);
				item_idList.remove(0);

				String CHOICE_itemID = readString("Enter item_id to add into menu: ");

				if (item_idList.contains(CHOICE_itemID) == false) {
					print("\nWrong item ID entered - Returning back to [ADD FOOD TO MENU]\n");
					continue;
				}

				CHOICE = item_idList.indexOf(CHOICE_itemID) + 1;

				String item_id = table[CHOICE][0];
				String item_name = table[CHOICE][1];
				String item_qty = table[CHOICE][2];
				String item_description = table[CHOICE][3];
				String item_dietary = table[CHOICE][4];
				String item_ingredients = table[CHOICE][5];
				String item_price = table[CHOICE][6];

				String row = "" + "\n======= Food =======\n" + "Item ID: %s\n" + "Item name: %s\n"
						+ "Item quantity: %s\n" + "Item description: %s\n" + "Item dietary: %s\n"
						+ "Item ingredients: %s\n" + "Item price: %s\n";

				row = String.format(row, item_id, item_name, item_qty, item_description, item_dietary, item_ingredients,
						item_price);
				print(row);

				//
				// Check for vendor available menu
				//
				menuChoice = getVendorMenu();

				YESNO = readChar("Add item to menu? (Y/N) ");

				if (YESNO != 'y') {
					print("Returning back to [ADD FOOD TO MENU]");
					continue;
				}

				print("Adding Item to Menu...");
				isSuccessful = CREDENTIAL.addItemToMenu(Integer.parseInt(CHOICE_itemID), menuChoice);

				if (isSuccessful == null) {
					print("Something went wrong when adding");
				} else if (isSuccessful) {
					print("Added Item to Menu Successful");
				} else {
					print("Item cant be added [Item already inside Menu]");
				}
				continue;

			case 2:
				// Create new food and add it to SQL

				String food = readString("Enter Food Name: ");
				String description = readString("Enter Food Description: ");
				String dietary = readString("Enter Food Dietary: ");
				String ingredients = readString("Enter Food Ingredients: ");
				Double price = readDouble("Enter Food Price: $");
				int qty = readInt("Enter Food Quantity: ");

				menuChoice = getVendorMenu();

				YESNO = readChar("Add item to menu? (Y/N) ");

				if (YESNO != 'y') {
					print("Returning back to [ADD FOOD TO MENU]");
					continue;
				}

				print("Adding new Item to Menu...");
				String[] itemArr = { food, String.valueOf(qty), description, dietary, ingredients,
						String.valueOf(price) };
				isSuccessful = CREDENTIAL.addItemToMenu(itemArr, menuChoice);

				if (isSuccessful == null) {
					print("Something went wrong when adding");
				} else if (isSuccessful) {
					print("Added Item to Menu Successful");
				} else {
					print("Item cant be added [Item already inside Menu]");
				}

				print("Added new Item to Menu Successful");
				continue;

			case 9:
				// Exit from Menu Option
				vendorMenu();
				break;

			default:
				// Error message
				invalidChoice();

			}
		}
	}

	// (Done need testing) Method for Vendor to delete Food
	private static void deleteFoodItem() {
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
			vendorMenu();
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
			vendorMenu();
		}

		// Run Delete SQL
		print("Deleting item....");
		Boolean isDeleted = CREDENTIAL.deleteItem(food_id);

		if (isDeleted == false) {
			print("Delete item Failed");
		} else if (isDeleted == null) {
			print("Wrong access type");
		} else {
			print("Delete item Successful");
		}
		vendorMenu();
	}

	// (Done need testing) add new menu
	private static void addNewMenu() {
		line(40, "-");
		print("== Add New Menu ==");

		char YESNO = readChar("Do you want to create a new Menu? (Y/N) ");

		if (YESNO != 'y') {
			print("Returning back to [Vendor MENU]");
			vendorMenu();
		}

		Boolean isAdded = CREDENTIAL.addNewMenu();

		if (isAdded == null) {
			print("Wrong credential access");
		} else if (isAdded == false) {
			print("Menu Creation failed");
		} else {
			print("Menu Creation Successful");
			print("Bringing user to addFoodItem");
			addFoodItem();
		}
		vendorMenu();
	}

	// (DONE - Testing)
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

	// (Done need testing)
	private static void addVendorToSchool() {

		if (CREDENTIAL.getSchoolCount() == 0) {
			print("There is currently no school available to add to");
			vendorMenu();
		}

		String[][] tableListSchool = CREDENTIAL.viewAllSchool();
		String[][] tableListVendorSchool = CREDENTIAL.viewSchoolHasVendor();

		print("\n===== Available Schools =====");
		print(TableFormatter.tableFormatter(tableListSchool));

		print("\n==== Current school linked to vendor ====");
		print(TableFormatter.tableFormatter(tableListVendorSchool));

		ArrayList<String> SchoolID = new ArrayList<String>();
		for (String[] rowSchoolID : tableListSchool) {
			if (rowSchoolID[0].equalsIgnoreCase("School ID")) {
				continue;
			}
			SchoolID.add(rowSchoolID[0]);
		}

		for (String[] rowSchoolID : tableListVendorSchool) {
			if (rowSchoolID[0].equalsIgnoreCase("School ID")) {
				continue;
			} else if (SchoolID.contains(rowSchoolID[0]) == true) {
				SchoolID.remove(rowSchoolID[0]);
			}
		}

		int linkSchoolID = readInt("Enter school ID to link to vendor: ");

		boolean contains = SchoolID.contains(String.valueOf(linkSchoolID));

		if (contains == false) {
			print("\nWrong / Already Linked school ID");
			vendorMenu();
		}

		print("\nLinking school to vendor...");

		Boolean isAdded = CREDENTIAL.addSchoolHasVendor(String.valueOf(linkSchoolID));
		if (isAdded == false) {
			print("Add School to Vendor Failed");
		} else if (isAdded == null) {
			print("Wrong access type");
		} else {
			print("Add School to Vendor Successful");
		}
		vendorMenu();
	}

	// ==========================
	// Methods For ADMIN
	// ==========================

	// (DONE) SQL Code to view all Users
	private static void viewAllUser() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllUser();

		System.out.println(TableFormatter.tableFormatter(table));

	}

	// (DONE) SQL Code to view all Schools
	private static void viewAllSchool() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllSchool();

		System.out.println(TableFormatter.tableFormatter(table));
	}

	// (DONE) SQL Code to view all Orders
	private static void viewAllOrder() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllOrder();

		System.out.println(TableFormatter.tableFormatter(table));
	}

	// (DONE NEED TESTING) SQL Code to create any user
	private static void createUser() {
		line(40, "-");

		String email = "";
		String name = "";
		String password = "";
		String access = "";
		String[] otherInfo = {};

		while (true) {
			email = readString("Enter Email: ");
			name = readString("Enter Name: ");
			password = readString("Enter Password: ");
			access = readString("Enter Access Type (admin/vendor/normal): ").toLowerCase();
			
			if ((isName(name) && isEmail(email) && isPassword(password)
				&& (access.equals("normal") || access.equals("vendor") || access.equals("admin"))) == true) {
				break;
			}
		}

		DBData createAccount = null;
		switch (access) {
		case "normal":
			otherInfo = new String[3]; // Changed size to 3
			otherInfo[0] = String.valueOf(readInt("Enter Phone Number: "));
			otherInfo[1] = readString("Enter Address: ");
			otherInfo[2] = readString("Enter Allergies: ");

			print("Creeting normal accout....");
			createAccount = Authentication.RegisterAccountNormal(name, email, password, otherInfo);
			print(createAccount == null);
			break;
		case "vendor":
			otherInfo = new String[3]; // Changed size to 3
			otherInfo[0] = readString("Enter Company Name: ");
			otherInfo[1] = String.valueOf(readString("Enter Vendor Phone Number: "));
			otherInfo[2] = readString("Enter Vendor Address: ");

			print("Creeting vender accout....");
			createAccount = Authentication.RegisterAccountVendor(name, email, password, otherInfo);
			break;
		case "admin":
			otherInfo = new String[1];

			print("Creeting admin accout....");
			createAccount = Authentication.RegisterAccountAdmin(name, email, password, otherInfo);
			break;
		default:
			print("\nInvalid access type.\n");
			adminMenu(); // Bring user back to admin menu
		}

		if (createAccount != null) {
			print("\nUser created successfully!\n");
		} else {
			print("\nUser creation failed.\n");
		}

		adminMenu(); // Bring user back to admin menu
	}

	// (DONE NEEDED TEST) SQL Code to delete Users + View All Users
	private static void deleteUser() {
		line(40, "-");

		String email = readString("Enter Email: ");
		String confirm = readString("Confirm Deletetion? (y/n): ");

		if (confirm.equalsIgnoreCase("y") == false) {
			print("Deletation Aborted");
			adminMenu();
		}

		String user_id = CREDENTIAL.getUser_id(email);

		if (user_id == null) {
			print("Delete User Error - user id NULL");
			adminMenu();
		}

		boolean isSuccessful = CREDENTIAL.DELETE_USER(user_id);

		if (isSuccessful == false) {
			print("Delete User Error - Deletion unsuccessful");
		} else {
			print("Delete User Successful");
		}
		adminMenu();
	}

	// DONE NEED TESTING)
	private static void addSchool() {
		line(40, "-");

		String school_name = readString("Enter school name: ");
		String school_address = readString("Enter school address: ");

		Boolean isSuccessful = CREDENTIAL.addSchool(school_name, school_address);

		if (isSuccessful == null) {
			print("Something went wrong when adding");
		} else if (isSuccessful) {
			print("Added Item to Menu Successful");
		} else {
			print("Item cant be added [Item already inside Menu]");
		}
		adminMenu();
	}

	// =============================
	// Refactoring
	// =============================

	private static void thankYou() {
		print("\nThank you for using our system!");
	}

	private static void invalidChoice() {
		print("\nInvalid choice. Please choose again.");
	}

	private static void print(String str) {
		System.out.println(str);
	}

	@SuppressWarnings("unused")
	private static void print(int i) {
		System.out.println(i);
	}

	@SuppressWarnings("unused")
	private static void print(boolean b) {
		System.out.println(b);
	}

	private static void displayMenu(String menuType) {
		switch (menuType) {
		case "main":
			print("");
			line(40, "-");
			print("====== SCHOOL LUNCH BOX MAIN MENU ======");
			line(40, "-");
			print("\n          |======= Menu =======|          ");
			print("          |(1) Login           |");
			print("          |(2) Register        |");
			print("          |(9) Quit            |");
			line(40, "-");
			break;
		case "normal":
			print("");
			line(40, "-");
			print("============== NORMAL MENU =============");
			print("             Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("           (1) View All Menu   ");
			print("           (2) Add a New Order  ");
			print("           (3) View Order       ");
			print("           (9) Exit             ");
			line(40, "-");
			break;
		case "vendor":
			print("");
			line(40, "-");
			print("============== VENDOR MENU =============");
			print("             Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("             (1) View All Menu");
			print("             (2) Add new Menu");
			print("             (3) Delete Menu");
			print("             (4) Add new Item");
			print("             (5) Delete Item");
			print("             (5) Link School to Vendor");
			print("             (9) Exit");
			line(40, "-");
			break;
		case "admin":
			print("");
			line(40, "-");
			print("============== ADMIN MENU ==============");
			print("             Welcome " + CREDENTIAL.getUser_name());
			line(40, "-");
			print("             (1) View All Users");
			print("             (2) View All Schools");
			print("             (3) View All Menus");
			print("             (4) View All Orders");
			print("             (5) Create User");
			print("             (6) Delete User");
			print("             (7) Add School");
			print("             (9) Exit");
			line(40, "-");
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

	private static String readString(String str) {
		str = Helper.readString(str);
		return str;
	}

	private static int readInt(String str) {
		int i = Helper.readInt(str);
		return i;
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

	private static void line(int count, String str) {
		Helper.line(count, str);
	}

	// ==========================
	// Validation
	// ==========================

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
