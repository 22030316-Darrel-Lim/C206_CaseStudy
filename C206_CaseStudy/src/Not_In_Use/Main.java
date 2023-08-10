package Not_In_Use;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Helper.Helper;
import Helper_Package.Authentication;
import Helper_Package.DBData;

public class Main {
	private static DBData CREDENTIAL;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainMenu();
	}

	// NOT IN USE - USE THE DEFAULT PACKAGE

	// Main Menu is displayed when application is open
	public static void mainMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== SCHOOL LUNCH BOX MAIN MENU");
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
			}
		}
	}

	private static void vendorMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== VENDOR MENU ==");
			System.out.println("1)View All Menu \n2)Add new Menu \n3)Delete Menu \n4)Add new Item \n9)Exit");
			i = Helper.readInt("Enter Option: ");
			switch (i) {
			case 1:
				// Call method to see all menu

			case 2:
				// New Menu + add Items
				createFoodMenu();

			case 3:
				// Delete Menu
				deleteFoodMenu();

			case 4:
				// Add new Item to the menu
				addFoodItem();

			case 9:
				System.out.println("Thank you for using our system!");
				break;
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

			case 2:
				// View all School

			case 3:
				// View all Menu

			case 4:
				// View All Orders

			case 5:
				// Call Method to Create User

			case 6:
				// Delete User

			case 9:
				System.out.println("Thank you for using our system!");
				break;
			}
		}
	}

	// Method for Vendor to create a new food Menu
	private static void createFoodMenu() {
		System.out.println("== Create New Food Menu ==");
	}

	// Method for Vendor to delete the Food Menu
	private static void deleteFoodMenu() {
		System.out.println("");
	}

	// Method for Vendor to delete the Food Menu
	private static void addFoodItem() {
		System.out.println("");
	}

}
