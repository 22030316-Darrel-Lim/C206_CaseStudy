import Helper_Package.Authentication;
import Helper_Package.DBData;
import Helper.Helper;

public class C206_CaseStudy {

	private static DBData CREDENTIAL;
	private static String CHOICE;

	public static void main(String[] args) {
		CHOICE = "";

		while (CHOICE != "9") {

			displayMenu("main");
			CHOICE = readString("Enter choice: ");

			switch (CHOICE) {
			case "1":
				login();
				break;
			case "2":
				register();
				break;
			case "9":
				thankYou();
				break;
			default:
				invalidChoice();
			}

		}
	}

	private static void register() {
		String name = readString("Enter name: "); // TODO validation
		String email = readString("Enter email: "); // TODO validation
		String password = readString("Enter password: "); // TODO validation
		String phoneNo = readString("Enter phone number: "); // TODO validation
		String allergies = readString("Enter allergies: ");
		String address = readString("Enter address: ");

		String[] otherInfo = { phoneNo, allergies, address };

		CREDENTIAL = Authentication.RegisterAccountNormal(name, email, password, otherInfo);

		if (CREDENTIAL != null) {
			print("Registration successful!");

		} else {
			print("Registration failed.");
			main(null); // Return back to main if fail
		}
	}

	private static void login() {
		String email = readString("Enter email: ");
		String password = readString("Enter password: ");

		CREDENTIAL = Authentication.Login(email, password);

		if (CREDENTIAL != null) {
			print("Login successful!");

		} else {
			print("Login failed.");
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
			print("Login Failed - Unknown access type.");
			main(null); // Return back to main if fail
		}
	}

	private static void normalMenu() {

		CHOICE = "";

		while (CHOICE != "9") {

			displayMenu("normal");
			CHOICE = readString("Enter Option: ");
			switch (CHOICE) {
			case "1":
				// Call method to see all menu
				viewAllMenu();
				break;
			case "2":
				// TODO Create a new order + payment

				break;
			case "3":
				// TODO View current order
				break;
			case "9":
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
	}

	private static void vendorMenu() {
		CHOICE = "";

		while (CHOICE != "9") {

			displayMenu("vendor");
			CHOICE = readString("Enter Option: ");
			switch (CHOICE) {
			case "1":
				// Call method to see all menu
				viewAllMenu();
				break;
			case "2":
				// New Menu + add Items
				createFoodMenu();
				break;
			case "3":
				// Delete Menu
				deleteFoodMenu();
				break;
			case "4":
				// Add new Item
				addFoodItem();
				break;
			case "5":
				// Delete Item
				deleteFoodItem();
				break;
			case "9":
				thankYou();
				break;
			default:
				invalidChoice();
			}
		}
	}

	private static void adminMenu() {
		CHOICE = "";

		while (CHOICE != "9") {

			displayMenu("admin");
			CHOICE = readString("Enter Option: ");
			switch (CHOICE) {
			case "1":
				// Call method to view all User
				viewAllUser();
				break;
			case "2":
				// View all School
				viewAllSchool();
				break;
			case "3":
				// View all Menu
				viewAllMenu();
				break;
			case "4":
				// View All Orders
				viewAllOrder();
				break;
			case "5":
				// Call Method to Create User
				createUser();
				break;
			case "6":
				// Delete User
				deleteUser();
				break;
			case "9":
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
		print("\nInvalid choice. Please choose again.\n");
	}

	private static void print(String str) {
		print(str);
	}

	private static void displayMenu(String menuType) {
		switch (menuType) {
		case "main":
			line(40, "-");
			print("====== SCHOOL LUNCH BOX MAIN MENU ======");
			line(40, "-");
			print("======= Menu =======");
			print("1. Login");
			print("2. Register");
			print("9. Quit");
			line(20, "-");
			break;
		case "normal":
			line(40, "-");
			print("=== NORMAL MENU ====");
			print("1) View All Menu");
			print("2) Add a New Order");
			print("3) View Order");
			print("9) Exit");
			line(20, "-");
			break;
		case "vendor":
			line(40, "-");
			print("==== VENDOR MENU ===");
			print("1) View All Menu");
			print("2) Add new Menu");
			print("3) Delete Menu");
			print("4) Add new Item");
			print("5) Delete Item");
			print("9) Exit");
			line(20, "-");
			break;
		case "admin":
			line(40, "-");
			print("==== ADMIN MENU ====");
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
			print("=== MENU FAILED ====");
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
		double i = Helper.readInt(str);
		return i;
	}

	private static void line(int count, String str) {
		Helper.line(count, str);
	}

	// NOT REFACTORED
	// TODO
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
		line(40, "-");
		print("== CREATE NEW FOOD MENU ==");
		// TODO Create a new Food menu - SQL to Insert new Menu
		addFoodItem();

	}

	// Method for Vendor to delete the Food Menu

	private static void deleteFoodMenu() {
		line(40, "-");
		print("== DELETE FOOD MENU ==");
		// TODO Run SQL Statement to get Menu_id

		int id = readInt("Select Menu_ID to delete: ");
		// TODO Run SQL Statement to delete base on Menu_Id
	}

	// Method for Vendor to add new food

	private static void addFoodItem() {
		line(40, "-");
		print("== ADD FOOD TO MENU ==");

		// Looping for input
		CHOICE = "";
		while (CHOICE != "9") {
			print("1) Add exisitng food to menu \n2) Create food and add it to menu \n9) Exit");
			CHOICE = readString("Enter Option: ");

			switch (CHOICE) {
			case "1":
				// TODO Retrieve food from SQL and add it

			case "2":
				// Create new food and add it to SQL

				String food = readString("Enter Food Name: ");
				String description = readString("Enter Food Description: ");
				String dietary = readString("Enter Food Dietary: ");
				String ingredients = readString("Enter Food Ingredients: ");
				Double price = readDouble("Enter Food Price: ");
				int qty = readInt("Enter Food Quantity");
				int menu_id = readInt("Enter Menu_id: ");

				// TODO Run insert SQL Statement to create new food and add it to menu

			case "9":
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

	private static void viewAllUser() {
		line(40, "-");
		// TODO SQL Code to view all Users

	}

	private static void viewAllSchool() {
		line(40, "-");
		// TODO SQL Code to view all Schools

	}

	private static void viewAllOrder() {
		line(40, "-");
		// TODO SQL Code to view all Orders

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
