package Not_In_Use;

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

		DBData CREDENTIAL = new DBData("vendor1@vendor1", "vendor1");
		System.out.println(CREDENTIAL.getUser_access() + " " + CREDENTIAL.getUser_name());
print(CREDENTIAL.getUser_name());
		//String[][] table = CREDENTIAL.viewAllFood();

		//print(TableFormatter.tableFormatter(table));

//		int countItem = CREDENTIAL.getItemCount();
//
//		if (countItem == 0) {
//			print("There are currently no items in the DB");
//			return;
//		}
//
//		CHOICE = readInt("Enter item_id to add into menu: ") + 1;
//
//		if (CHOICE > countItem || CHOICE <= 0) {
//			print("\nWrong item ID entered - Returning back to [ADD FOOD TO MENU]\n");
//			return;
//		}
//
//		String item_id = table[CHOICE][0];
//		String item_name = table[CHOICE][1];
//		String item_qty = table[CHOICE][2];
//		String item_description = table[CHOICE][3];
//		String item_dietary = table[CHOICE][4];
//		String item_ingredients = table[CHOICE][5];
//		String item_price = table[CHOICE][6];
//
//		String row = "" + "\n======= Food =======\n" + "Item ID: %s\n" + "Item name: %s\n" + "Item quantity: %s\n"
//				+ "Item description: %s\n" + "Item dietary: %s\n" + "Item ingredients: %s\n" + "Item price: %s\n";
//
//		row = String.format(row, item_id, item_name, item_qty, item_description, item_dietary, item_ingredients,
//				item_price);
//		print(row);
//
//		char YESNO = readChar("Add item to menu? (Y/N) ");
//
//		if (YESNO != 'y') {
//			print("Returning back to [ADD FOOD TO MENU]");
//			return;
//		}
//
//		print("Adding Item to Menu...");
//		CREDENTIAL.addItemToMenu(CHOICE);

	}

	private static void print(String str) {
		System.out.println(str);
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
	// NOT IN USE - USE THE DEFAULT PACKAGE
	// THIS SERVERS AS A WORKSTATION TO AVOID CONFLICT WITH THE DEFAULT CLASS

	// Main Menu is displayed when application is open
	public static void mainMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== SCHOOL LUNCH BOX MAIN MENU ==");
			System.out.println("1) Login\n2) Register\n9)Exit");
			i = Helper.readInt("Enter Option: ");

			// Option 1 to Login
			switch (i) {
			case 1:
				String email = Helper.readString("Enter email: ");
				String pass = Helper.readString("Enter Password: ");
				CREDENTIAL = Authentication.Login(email, pass);
				if (CREDENTIAL == null) {
					System.out.println("Invalid Email or Password");
				} else {
					String access = CREDENTIAL.getUser_access();
					switch (access) {
					case "normal":
						userMenu();

					case "vendor":
						vendorMenu();

					case "admin":
						adminMenu();

					default:
						System.out.println("Error - Access Type not found");
					}
				}

				// Option 2 to Register
			case 2:
				email = Helper.readString("Enter email: ");
				pass = Helper.readString("Enter Password: ");
				CREDENTIAL = Authentication.Login(email, pass);
				if (CREDENTIAL == null) {
					System.out.println("Invalid Email or Password");
				} else {

				}
			}
		}
	}

	private static void userMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== USER MENU ==");
			System.out.println("1)View All Menu \n2)Add a New Order\n3)View Order \n9)Exit");
			i = Helper.readInt("Enter Option: ");
			switch (i) {
			case 1:
				// Call method to see all menu

			case 2:
				// Create a new order + payment

			case 3:
				// View current oder

			case 9:
				System.out.println("Thank you for using our system!");
				break;

			default:
				System.out.println("Invlaid Option");

			}
		}
	}

	private static void vendorMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== VENDOR MENU ==");
			System.out.println(
					"1)View All Menu \n2)Add new Menu \n3)Delete Menu \n4)Add new Item \n5)Delete Items \n9)Exit");
			i = Helper.readInt("Enter Option: ");
			switch (i) {
			case 1:
				// Call method to see all menu
				viewAllMenu();

			case 2:
				// New Menu + add Items
				createFoodMenu();

			case 3:
				// Delete Menu
				deleteFoodMenu();

			case 4:
				// Add new Item to the menu
				addFoodItem();

			case 5:
				// Delete Item from Menu
				deleteFoodItem();
			case 9:
				System.out.println("Thank you for using our system!");
				break;

			default:
				System.out.println("Invlaid Option");
			}
		}
	}

	private static void adminMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== ADMIN MENU ==");
			System.out.println("1)View All Users \n2)View All Schools \n3)View All Menus"
					+ "\n4)View All Orders \n5)Create User 6)Delete User 9)Exit");

			i = Helper.readInt("Enter Option: ");
			switch (i) {
			case 1:
				// Call method to view all User
				viewAllUser();

			case 2:
				// View all School
				viewAllSchool();

			case 3:
				// View all Menu
				viewAllMenu();

			case 4:
				// View All Orders
				viewAllOrder();

			case 5:
				// Call Method to Create User
				createUser();

			case 6:
				// Delete User
				deleteUser();

			case 9:
				System.out.println("Thank you for using our system!");
				break;

			default:
				System.out.println("Invlaid Option");
			}
		}
	}

	// ==========================
	// Methods For USER
	// ==========================

	private static void viewAllMenu() {
		// TODO SQL to view all Menu
	}

	// ==========================
	// Methods For VENDOR
	// ==========================

	// Method for Vendor to create a new food Menu

	private static void createFoodMenu() {
		System.out.println("== CREATE NEW FOOD MENU ==");
		// TODO Create a new Food menu - SQL to Insert new Menu
		addFoodItem();

	}

	// Method for Vendor to delete the Food Menu

	private static void deleteFoodMenu() {
		System.out.println("== DELETE FOOD MENU ==");
		// TODO Run SQL Statement to get Menu_id

		int id = Helper.readInt("Select Menu_ID to delete: ");
		// TODO Run SQL Statement to delete base on Menu_Id
	}

	// Method for Vendor to add new food

	private static void addFoodItem() {
		System.out.println("== ADD FOOD TO MENU ==");

		// Looping for input
		int i = 0;
		while (i != 9) {
			System.out.println("1) Add exisitng food to menu \n2) Create food and add it to menu \n9) Exit");
			i = Helper.readInt("Enter Option: ");

			switch (i) {
			case 1:
				// TODO Retrieve food from SQL and add it

			case 2:
				// Create new food and add it to SQL

				String food = Helper.readString("Enter Food Name: ");
				String description = Helper.readString("Enter Food Description: ");
				String dietary = Helper.readString("Enter Food Dietary: ");
				String ingredients = Helper.readString("Enter Food Ingredients: ");
				Double price = Helper.readDouble("Enter Food Price: ");
				int qty = Helper.readInt("Enter Food Quantity");
				int menu_id = Helper.readInt("Enter Menu_id: ");

				// TODO Run insert SQL Statement to create new food and add it to menu

			case 9:
				// Exit from Menu Option
				break;

			default:
				// Error message
				System.out.println("Invalid choice. Please choose again.");
			}
		}
	}

	// Method for Vendor to delete Food
	private static void deleteFoodItem() {
		System.out.println("== DELETE FOOD ITEM ==");
		// Method to view the Food
		// SQL
		String food = Helper.readString("Enter Food Name: ");
		String confirm = Helper.readString("Confirm Deletetion? (y/n): ");
		if (confirm.equalsIgnoreCase("y")) {
			// TODO Run Delete SQL
		} else {
			System.out.println("Deletation Aborted");
		}
	}

	// ==========================
	// Methods For ADMIN
	// ==========================

	private static void viewAllUser() {
		// TODO SQL Code to view all Users

	}

	private static void viewAllSchool() {
		// TODO SQL Code to view all Schools

	}

	private static void viewAllOrder() {
		// TODO SQL Code to view all Orders

	}

	private static void createUser() {
		// TODO SQL Code to create any user
		String email = Helper.readString("Enter Email: ");
		String name = Helper.readString("Enter Name: ");
		String password = Helper.readString("Enter Password: ");
		String access = Helper.readString("Enter Access Type: ");
	}

	private static void deleteUser() {
		// TODO SQL Code to delete Users + View All Users
		String email = Helper.readString("Enter Email: ");
		String confirm = Helper.readString("Confirm Deletetion? (y/n): ");
		if (confirm.equalsIgnoreCase("y")) {
			// TODO Run Delete SQL
		} else {
			System.out.println("Deletation Aborted");
		}
	}
}
