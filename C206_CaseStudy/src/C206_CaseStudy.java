import Helper_Package.Authentication;
import Helper_Package.DBData;

import java.util.regex.Pattern;

import Helper.Helper;
import Helper.TableFormatter;

public class C206_CaseStudy {

	private static DBData CREDENTIAL;
	private static int CHOICE;

	public static void main(String[] args) {
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
				createFoodMenu();
				break;
			case 3:
				// Delete Menu
				deleteFoodMenu();
				break;
			case 4:
				// Add new Item
				addFoodItem();
				break;
			case 5:
				// Delete Item
				deleteFoodItem();
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
			case 9:
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
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
			print("Welcome "+ CREDENTIAL.getUser_name());
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
			print("Welcome "+ CREDENTIAL.getUser_name());
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
			print("Welcome "+ CREDENTIAL.getUser_name());
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
			System.out.println("\nError - Password contain at least one capital letter and at least 8 characters long\n");
		}
		return matched;
	} // End of isPassword

	// ==========================
	// Methods For USER
	// ==========================

	// TODO (DONE) SQL to view all Menu
	private static void viewAllMenu() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllMenu();

		System.out.println(TableFormatter.tableFormatter(table));

	}

	// ==========================
	// Methods For VENDOR
	// ==========================

	// Method for Vendor to create a new food Menu

	private static void createFoodMenu() {
		line(40, "-");
		print("=== CREATE NEW FOOD MENU ===");
		line(40, "-");
		
		// TODO Create a new Food menu - SQL to Insert new Menu
		addFoodItem();

	}

	// Method for Vendor to delete the Food Menu

	private static void deleteFoodMenu() {
		line(40, "-");
		print("=== DELETE FOOD MENU ===");
		line(40, "-");
		
		// TODO Run SQL Statement to get Menu_id

		int id = readInt("Select Menu_ID to delete: ");
		// TODO Run SQL Statement to delete base on Menu_Id
	}

	// Method for Vendor to add new food

	private static void addFoodItem() {
		line(40, "-");
		print("=== ADD FOOD TO MENU ===");
		line(40, "-");
		
		// Looping for input
		CHOICE = -1;

		while (CHOICE != 9) {
			print("1) Add exisitng food to menu");
			print("2) Create food and add it to menu");
			print("9) Exit");
			CHOICE = readInt("Enter Option: ");

			switch (CHOICE) {
			case 1:
				// TODO Retrieve food from SQL and add it
				String[][] table = CREDENTIAL.viewAllFood();

				print(TableFormatter.tableFormatter(table));

				int countItem = CREDENTIAL.getItemCount();

				if (countItem == 0) {
					print("There are currently no items in the DB");
					return;
				}

				CHOICE = readInt("Enter item_id to add into menu: ") + 1;

				if (CHOICE > countItem || CHOICE <= 0) {
					print("\nWrong item ID entered - Returning back to [ADD FOOD TO MENU]\n");
					return;
				}

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

				char YESNO = readChar("Add item to menu? (Y/N) ");

				if (YESNO != 'y') {
					print("Returning back to [ADD FOOD TO MENU]");
					return;
				}
				
				print("Adding Item to Menu...");
				CREDENTIAL.addItemToMenu(CHOICE);
				
			case 2:
				// Create new food and add it to SQL

				String food = readString("Enter Food Name: ");
				String description = readString("Enter Food Description: ");
				String dietary = readString("Enter Food Dietary: ");
				String ingredients = readString("Enter Food Ingredients: ");
				Double price = readDouble("Enter Food Price: ");
				int qty = readInt("Enter Food Quantity");
				int menu_id = readInt("Enter Menu_id: ");

				// TODO Run insert SQL Statement to create new food and add it to menu

			case 9:
				// Exit from Menu Option
				break;

			default:
				// Error message
				invalidChoice();

			}
		}
	}

	// Method for Vendor to delete Food
	private static void deleteFoodItem() {
		line(40, "-");
		print("== DELETE FOOD ITEM ==");
		// Method to view the Food
		// SQL
		String food = readString("Enter Food Name: ");
		String confirm = readString("Confirm Deletetion? (y/n): ");
		if (confirm.equalsIgnoreCase("y")) {
			// TODO Run Delete SQL
		} else {
			print("Deletation Aborted");
		}
	}

	// ==========================
	// Methods For ADMIN
	// ==========================

	// TODO (DONE) SQL Code to view all Users
	private static void viewAllUser() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllUser();

		System.out.println(TableFormatter.tableFormatter(table));

	}

	// TODO (DONE) SQL Code to view all Schools
	private static void viewAllSchool() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllSchool();

		System.out.println(TableFormatter.tableFormatter(table));
	}

	// TODO (DONE) SQL Code to view all Orders
	private static void viewAllOrder() {
		line(40, "-");

		String[][] table = CREDENTIAL.viewAllOrder();

		System.out.println(TableFormatter.tableFormatter(table));
	}

	private static void createUser() {
		line(40, "-");
		// TODO SQL Code to create any user
		String email = readString("Enter Email: ");
		String name = readString("Enter Name: ");
		String password = readString("Enter Password: ");
		String access = readString("Enter Access Type: ");
	}

	private static void deleteUser() {
		line(40, "-");
		// TODO SQL Code to delete Users + View All Users
		String email = readString("Enter Email: ");
		String confirm = readString("Confirm Deletetion? (y/n): ");
		if (confirm.equalsIgnoreCase("y")) {
			// TODO Run Delete SQL
		} else {
			print("Deletation Aborted");
		}
	}

}
