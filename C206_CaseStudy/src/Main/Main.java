package Main;

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

	// Main Menu is displayed when application is open
	public static void mainMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("====== SCHOOL LUNCH BOX MAIN MENU ====== ");
			Helper.line(40, "-");
			System.out.println("1) Login\n2) Register\n9) Exit");
			Helper.line(40, "-");
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
					case "admin":
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
}
