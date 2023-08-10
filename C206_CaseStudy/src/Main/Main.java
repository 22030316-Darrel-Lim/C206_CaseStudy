package Main;

import java.util.ArrayList;
import java.util.regex.Pattern;

import Helper.Helper;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainMenu();
	}

	// Main Menu is displayed when application is open
	public static void mainMenu() {
		int i = 0;
		while (i != 9) {
			Helper.line(40, "-");
			System.out.println("== SCHOOL LUNCH BOX MAIN MENU");
			System.out.println("1) Login\n2) Register\n9)Exit");
			i = Helper_Package.Helper.readInt("Enter Option: ");

			switch (i) {
			case 1:
			}

		}
	}
}
