import javax.security.auth.login.CredentialException;

import Helper_Package.Authentication;
import Helper_Package.DBData;
import Helper_Package.Helper;
	
public class C206_CaseStudy {
	private static DBData credentials;
	public static void main(String[] args) {
		Helper.line(40, "-");
		System.out.println("== SCHOOL LUNCH BOX MAIN MENU==");
		while (true) {
            int choice = displayMenu();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Goodbye! Have a nice day.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static int displayMenu() {
        System.out.println("===== Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        return Helper.readInt("");
    }

    private static void register() {
        String name = Helper.readString("Enter your name: ");
        String email = Helper.readString("Enter your email: ");
        String password = Helper.readString("Enter your password: ");
        String phoneNo = Helper.readString("Enter your phone number: ");
        String allergies = Helper.readString("Enter your allergies: ");
        String address = Helper.readString("Enter your address: ");

        String[] otherInfo = {phoneNo, allergies,address};

        credentials = Authentication.RegisterAccountNormal(name, email, password, otherInfo);

        if (credentials != null) {
            System.out.println("Registration successful!");
            System.out.println("Access: " + credentials.getUser_access());
            System.out.println("User ID: " + credentials.getUser_id());
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void login() {
        String email = Helper.readString("Enter your email: ");
        String password = Helper.readString("Enter your password: ");

        String userAccessType = Authentication.getUserAccessType(email, password);

        if (userAccessType != null) {
            System.out.println("Login successful!");

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
                    System.out.println("Unknown access type.");
                    break;
            }
        } else {
            System.out.println("Login failed.");
        }
    }
 
    private static void normalMenu() {
        int choice = 0;
        while (choice != 9) {
            Helper.line(40, "-");
            System.out.println("== NORMAL MENU ==");
            System.out.println("1) View All Menu\n2) Add a New Order\n3) View Order\n9) Exit");
            choice = Helper.readInt("Enter Option: ");
            switch (choice) {
                case 1:
                    // Call method to see all menu
                    break;
                case 2:
                    // Create a new order + payment
                    break;
                case 3:
                    // View current order
                    break;
                case 9:
                    System.out.println("Thank you for using our system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
    private static void vendorMenu() {
        int choice = 0;
        while (choice != 9) {
            Helper.line(40, "-");
            System.out.println("== VENDOR MENU ==");
            System.out.println("1) View All Menu\n2) Add new Menu\n3) Delete Menu\n4) Add new Item\n5) Delete Item\n9) Exit");
            choice = Helper.readInt("Enter Option: ");
            switch (choice) {
                case 1:
                    // Call method to see all menu
                    break;
                case 2:
                    // New Menu + add Items
                    break;
                case 3:
                    // Delete Menu
                    break;
                case 4:
                    // Add new Item to the menu
                    break;
                case 5:
                    // Delete Item
                    break;
                case 9:
                    System.out.println("Thank you for using our system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static void adminMenu() {
        int choice = 0;
        while (choice != 9) {
            Helper.line(40, "-");
            System.out.println("== ADMIN MENU ==");
            System.out.println("1) View All Users\n2) View All Schools\n3) View All Menus\n4) View All Orders\n5) Create User\n6) Delete User\n9) Exit");
            choice = Helper.readInt("Enter Option: ");
            switch (choice) {
                case 1:
                    // Call method to view all User
                    break;
                case 2:
                    // View all School
                    break;
                case 3:
                    // View all Menu
                    break;
                case 4:
                    // View All Orders
                    break;
                case 5:
                    // Call Method to Create User
                    break;
                case 6:
                    // Delete User
                    break;
                case 9:
                    System.out.println("Thank you for using our system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }}
