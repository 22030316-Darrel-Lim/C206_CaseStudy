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
				break;
			case "2":
				// Create a new order + payment
				break;
			case "3":
				// View current order
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
				break;
			case "2":
				// New Menu + add Items
				break;
			case "3":
				// Delete Menu
				break;
			case "4":
				// Add new Item to the menu
				break;
			case "5":
				// Delete Item
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
				break;
			case "2":
				// View all School
				break;
			case "3":
				// View all Menu
				break;
			case "4":
				// View All Orders
				break;
			case "5":
				// Call Method to Create User
				break;
			case "6":
				// Delete User
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
		System.out.println(str);
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

	private static void line(int count, String str) {
		Helper.line(count, str);
	}
}
