package Helper_Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import Helper.DBUtil;

public class DBData {

	// NOTE: URL may be different depending on the name of the database
	protected static final String JDBCURL = "jdbc:mysql://localhost/c206_ga";
	protected static final String DBUSERNAME = "root";
	protected static final String DBPASSWORD = "";

	protected static String InsertSQL;
	protected static String DeleteSQL;
	protected static String UpdateSQL;
	protected static String SelectSQL;

	protected static ResultSet rs;

	private static String user_access;
	private static String user_id;

	// NOTE: IF any error were to occur, user_access, user_id is to return null
	// Register Account (DONE - TESTED)
	public DBData(String name, String email, String password, String access, String[] OtherInfo) {

		// Check all Inputs
		if (name == null || email == null || password == null || access == null || OtherInfo == null) {
			return;
		}

		name = SQLInjection(name);
		email = SQLInjection(email);
		password = SQLInjection(password);
		OtherInfo = SQLInjection(OtherInfo);
		
		access = access.toLowerCase();

		// Check if email is in DB
		Boolean isEmailDB = CheckEmailDB(email);
		if (isEmailDB == null || isEmailDB == true) {
			return;
		}

		// Check if access type input is valid
		else if ((access.equals("normal") || access.equals("vendor") || access.equals("admin")) == false) {
			return;
		}

		//
		// Start of Account Creation
		//
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Create and format SQL insert Statement
		InsertSQL = "INSERT INTO user (user_name, user_email, user_password, ACCESS_TYPE, LAST_LOGIN) VALUES ('%s' , '%s', SHA1('%s'), '%s', NOW());";
		InsertSQL = String.format(InsertSQL, name, email, password, access);

		int rowsAffectedUser = DBUtil.execSQL(InsertSQL);

		// Validate if insert is not 1
		if (rowsAffectedUser != 1) {
			DBUtil.close();
			return;
		}

		// Get user_id by Logging in
		else if (LOGIN(email, password) == false) {
			DBUtil.close();
			return;
		}

		int RowAffectByAccess;
		String phoneNo;
		String address;
		String picture;
		String company;
		String allegies;

		main: switch (access) {
		case "normal":
			if (OtherInfo.length != 3) {
				DELETE_USER();
				break;
			}

			// Validate inside info
			for (int i = 0; i < OtherInfo.length; i++) {
				if (OtherInfo[i] == null) {
					if (DELETE_USER() == false) {
						user_access = null;
						user_id = null;
						break main;
					}
				}
			}

			phoneNo = OtherInfo[0];
			allegies = OtherInfo[1];
			address = OtherInfo[2];
			picture = "normal.png";

			InsertSQL = "INSERT INTO normal (normal_id, normal_phoneNumber, normal_address, normal_profile, normal_allegies) VALUES ('%s', %s, '%s', '%s', '%s');";
			InsertSQL = String.format(InsertSQL, user_id, phoneNo, address, picture, allegies);

			RowAffectByAccess = DBUtil.execSQL(InsertSQL);

			// Delete user if Inserts Fails
			if (RowAffectByAccess != 1) {
				if (DELETE_USER() == false) {
					user_access = null;
					user_id = null;
				}
			}

			break;
		case "vendor":
			if (OtherInfo.length != 3) {
				DELETE_USER();
				break;
			}

			// Validate inside info
			for (int i = 0; i < OtherInfo.length; i++) {
				if (OtherInfo[i] == null) {
					if (DELETE_USER() == false) {
						user_access = null;
						user_id = null;
						break main;
					}
				}
			}

			company = OtherInfo[0];
			phoneNo = OtherInfo[1];
			address = OtherInfo[2];
			picture = "vendor.png";

			InsertSQL = "INSERT INTO vendor (vendor_id, vendor_phoneNumber, vendor_companyName, vendor_profile, vendor_address) VALUES ('%s' , '%s', '%s', '%s', '%s');";
			InsertSQL = String.format(InsertSQL, user_id, phoneNo, company, picture, address);

			RowAffectByAccess = DBUtil.execSQL(InsertSQL);

			// Delete user if Inserts Fails
			if (RowAffectByAccess != 1) {
				if (DELETE_USER() == false) {
					user_access = null;
					user_id = null;
				}
			}

			break;
		case "admin":
			if (OtherInfo.length != 0) {
				DELETE_USER();
				break;
			}
			picture = "admin.png";

			InsertSQL = "INSERT INTO admin (admin_id, admin_profile) VALUES ('%s','%s');";
			InsertSQL = String.format(InsertSQL, user_id, picture);

			RowAffectByAccess = DBUtil.execSQL(InsertSQL);

			// Delete user if Inserts Fails
			if (RowAffectByAccess != 1) {
				if (DELETE_USER() == false) {
					user_access = null;
					user_id = null;
				}
			}

			break;
		default:
			if (DELETE_USER() == false) {
				user_access = null;
				user_id = null;
			}
		}
		DBUtil.close();
	}

	// Login to account (DONE - TESTED)
	public DBData(String email, String password) {
		if (LOGIN(email, password) == false) {
			user_access = null;
			user_id = null;
		}
	}

	// Delete user - Error in creating will delete user (DONE - TESTED)
	protected boolean DELETE_USER() {
		boolean isDeleted = false;

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		DeleteSQL = "DELETE FROM user WHERE user_id = '%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);

		DBUtil.close();

		if (rowsAffected == 1) {
			isDeleted = true;
		}

		user_access = null;
		user_id = null;

		return isDeleted;
	}

	// Delete user - Error in creating will delete user (DONE - TESTED)
	public boolean DELETE_USER(String user_id) {
		boolean isDeleted = false;

		if (user_access.equals("admin") == false) {
			return isDeleted;
		}
		
		user_id = SQLInjection(user_id);
		
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		DeleteSQL = "DELETE FROM user WHERE user_id = '%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);

		DBUtil.close();

		if (rowsAffected == 1) {
			isDeleted = true;
		}

		user_access = null;
		user_id = null;

		return isDeleted;
	}

	// Login (DONE - TESTED)
	protected static boolean LOGIN(String email, String password) {
		boolean isLogged = false;

		// Check all Inputs
		if (email == null || password == null) {
			return isLogged;
		}
		
		email = SQLInjection(email);
		password = SQLInjection(password);
		
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		SelectSQL = "SELECT ACCESS_TYPE, user_id FROM user WHERE user_email = '%s' AND user_password = SHA1('%s');";

		SelectSQL = String.format(SelectSQL, email, password);
		
		try {

			rs = DBUtil.getTable(SelectSQL);
			if (rs.next()) {
				user_access = rs.getString("ACCESS_TYPE");
				user_id = String.valueOf(rs.getInt("user_id"));
			}

			// Update Last Login
			if (LAST_LOGIN() == true) {
				isLogged = true;
			}

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		
		DBUtil.close();
		return isLogged;
	}

	// Check email in DB (DONE - TESTED)
	public static Boolean CheckEmailDB(String email) {
		Boolean check = false;

		email = SQLInjection(email);

		// Check if empty
		if (email == null || email.isEmpty()) {
			return null;
		}

		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

			SelectSQL = "SELECT user_email FROM user WHERE user_email = '%s';";
			SelectSQL = String.format(SelectSQL, email);

			rs = DBUtil.getTable(SelectSQL);

			// Getting all the email from the SQL database and comparing it to the input
			// if rs = null - no result
			while (rs.next()) {
				check = true;
			}

			DBUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	} // End of CheckEmailDB

	// Updated last login (DONE - TESTED)
	protected static boolean LAST_LOGIN() {
		boolean isUpdated = false;

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		UpdateSQL = "UPDATE user SET LAST_LOGIN = NOW() WHERE user_id = '%s';";

		UpdateSQL = String.format(UpdateSQL, user_id);

		DBUtil.execSQL(UpdateSQL);

		int rowsAffected = DBUtil.execSQL(UpdateSQL);

		if (rowsAffected == 1) {
			isUpdated = true;
		}

		DBUtil.close();

		return isUpdated;
	}

	// ======================================
	// Getter & Setters
	// ======================================

	public String getUser_access() {
		return user_access;
	}

	public String getUser_id() {
		return user_id;
	}

	// TODO TEST needy
	public String getUser_id(String email) {
		String user_id = null;

		if (user_access.equals("admin") == false) {
			return user_id;
		}

		else if (CheckEmailDB(email) != true) {
			return user_id;
		}

		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

			SelectSQL = "SELECT user_id FROM user WHERE user_email = '%s';";
			SelectSQL = String.format(SelectSQL, email);

			rs = DBUtil.getTable(SelectSQL);

			while (rs.next()) {
				user_id = rs.getString("user_id");
			}

			DBUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user_id;
	}

	// (DONE - Tested)
	public String getUser_name() {
		String name = "";

		name = getUserInfo()[0];

		return name;
	}

	// (DONE - Tested)
	protected String[] getUserInfo() {
		String[] userInfo = new String[3];

		SelectSQL = "SELECT `user_name`, `user_email`, `LAST_LOGIN` FROM `user` WHERE user_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		rs = DBUtil.getTable(SelectSQL);

		try {
			while (rs.next()) {

				String user_name = rs.getString("user_name");
				String user_email = rs.getString("user_email");
				String last_login = rs.getString("LAST_LOGIN");

				userInfo[0] = user_name;
				userInfo[1] = user_email;
				userInfo[2] = last_login;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.close();
		return userInfo;
	}

	// Admin ONLY
	public String[][] viewAllUser() {

		if (user_access.equals("admin") == false) {
			return null;
		}

		int column = getUserCount() + 1;
		String[] header = { "user_id", "user_name", "user_email", "ACCESS_TYPE", "LAST_LOGIN" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select %s FROM user;";
		SelectSQL = String.format(SelectSQL, String.join(", ", header));

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String id = rs.getString(header[0]);
				String name = rs.getString(header[1]);
				String email = rs.getString(header[2]);
				String access = rs.getString(header[3]);
				String login = rs.getString(header[4]);

				// Add (You) based on user id
				if (id.equals(user_id))
					name += " [YOU]";

				table[count + 1][0] = id;
				table[count + 1][1] = name;
				table[count + 1][2] = email;
				table[count + 1][3] = access;
				table[count + 1][4] = login;
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return table;
	}

	public String[][] viewAllSchool() {

		if (user_access.equals("admin") == false) {
			return null;
		}

		int column = getSchoolCount() + 1;
		String[] header = { "school_id", "school_name", "school_address" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select %s FROM school;";
		SelectSQL = String.format(SelectSQL, String.join(", ", header));

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String id = rs.getString(header[0]);
				String name = rs.getString(header[1]);
				String address = rs.getString(header[2]);

				table[count + 1][0] = id;
				table[count + 1][1] = name;
				table[count + 1][2] = address;
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return table;
	}

	public String[][] viewAllOrder() {

		if (user_access.equals("admin") == false) {
			return null;
		}

		int column = getOrderCount() + 1;
		String[] header = { "order_id", "order_status", "child_id", "school_has_vendor_id", "payment_id", "normal_id" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select %s FROM has_order;";
		SelectSQL = String.format(SelectSQL, String.join(", ", header));

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String order_id = rs.getString(header[0]);
				String status = rs.getString(header[1]);
				String child_id = rs.getString(header[2]);
				String school_has_vendor_id = rs.getString(header[3]);
				String payment_id = rs.getString(header[4]);
				String normal_id = rs.getString(header[5]);

				table[count + 1][0] = order_id;
				table[count + 1][1] = status;
				table[count + 1][2] = child_id;
				table[count + 1][3] = school_has_vendor_id;
				table[count + 1][4] = payment_id;
				table[count + 1][5] = normal_id;
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return table;
	}

	public String[][] viewAllMenu() {

		int column = getMenuCount() + 1;
		String[] header = { "menu_id", "menu_item.item_id", "item_name", "item_qty", "item_description", "item_price" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select %s FROM menu_item INNER JOIN item ON item.item_id = menu_item.item_id;";
		SelectSQL = String.format(SelectSQL, String.join(", ", header));

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String menu_id = rs.getString(header[0]);
				String item_id = rs.getString(header[1]);
				String item_name = rs.getString(header[2]);
				String item_qty = rs.getString(header[3]);
				String item_description = rs.getString(header[4]);
				double item_price = rs.getDouble(header[5]);

				String price = String.format("$%.2f", item_price);

				table[count + 1][0] = menu_id;
				table[count + 1][1] = item_id;
				table[count + 1][2] = item_name;
				table[count + 1][3] = item_qty;
				table[count + 1][4] = item_description;
				table[count + 1][5] = price;
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return table;
	}

	// Vendor ONLY
	// (DONE - Tested)
	public String[] getVendorInfo() {
		String[] vendorInfo = new String[5];

		String[] userInfo = getUserInfo();

		//
		// Get Vendor Menu Info
		//
		ArrayList<String> menufoundList = new ArrayList<String>();

		SelectSQL = "SELECT `menu_id` FROM `menu` INNER JOIN vendor ON vendor.vendor_id = menu.vendor_id WHERE vendor.vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		rs = DBUtil.getTable(SelectSQL);

		try {
			while (rs.next()) {

				String menu_id = rs.getString("menu_id");

				menufoundList.add(menu_id);
			}

			// Convert ArrayList to Array

			String menufound = String.join(",", menufoundList);

			vendorInfo[4] = menufound;

		} catch (SQLException e) {
			vendorInfo = null;
			e.printStackTrace();
		}

		// Set menu to blank
		if (menufoundList.size() == 0) {
			vendorInfo[4] = "";
		}

		//
		// Get Vender Table info
		//
		SelectSQL = "SELECT `vendor_phoneNumber`, `vendor_companyName`, `vendor_profile`, `vendor_address` FROM `vendor` WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		rs = DBUtil.getTable(SelectSQL);

		try {
			while (rs.next()) {

				String vendor_phoneNumber = rs.getString("vendor_phoneNumber");
				String vendor_companyName = rs.getString("vendor_companyName");
				String vendor_profile = rs.getString("vendor_profile");
				String vendor_address = rs.getString("vendor_address");

				vendorInfo[0] = vendor_phoneNumber;
				vendorInfo[1] = vendor_companyName;
				vendorInfo[2] = vendor_profile;
				vendorInfo[3] = vendor_address;
			}

			// Join arrays together
			vendorInfo = Stream.concat(Arrays.stream(userInfo), Arrays.stream(vendorInfo)).toArray(String[]::new);

		} catch (SQLException e) {
			vendorInfo = null;
			e.printStackTrace();
		}

		DBUtil.close();
		return vendorInfo;
	}

	public String[][] viewAllFood() {

		if (user_access.equals("vendor") == false) {
			return null;
		}

		int column = getItemCount() + 1;

		String[] header = { "item_id", "item_name", "item_qty", "item_description", "item_dietary", "item_ingredients",
				"item_price" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select %s FROM item WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, String.join(", ", header), user_id);

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String item_id = rs.getString(header[0]);
				String item_name = rs.getString(header[1]);
				String item_qty = rs.getString(header[2]);
				String item_description = rs.getString(header[3]);
				String item_dietary = rs.getString(header[4]);
				String item_ingredients = rs.getString(header[5]);
				double item_price = rs.getDouble(header[6]);

				String price = String.format("$%.2f", item_price);

				table[count + 1][0] = item_id;
				table[count + 1][1] = item_name;
				table[count + 1][2] = item_qty;
				table[count + 1][3] = item_description;
				table[count + 1][4] = item_dietary;
				table[count + 1][5] = item_ingredients;
				table[count + 1][6] = price;
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return table;
	}

	// (DONE - testing)
	public Boolean addItemToMenu(int item_id, String menu_id) {

		if (user_access.equals("vendor") == false) {
			return null;
		}
		menu_id = SQLInjection(menu_id);
		
		Boolean isAdded = false;
		
		// Check if item_id inside menu_item
		SelectSQL = "SELECT item_id FROM menu_item WHERE item_id = '%s'";
		SelectSQL = String.format(SelectSQL, item_id);
		
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		
		rs = DBUtil.getTable(SelectSQL);
		int count = 0;
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (count != 0) {
			return isAdded;
		}

		InsertSQL = "INSERT INTO `menu_item` (`item_id`, `menu_id`) VALUES ('%d', '%s');";
		InsertSQL = String.format(InsertSQL, item_id, menu_id);

		int rowsAdded = DBUtil.execSQL(InsertSQL);

		if (rowsAdded == 1) {
			isAdded = true;
		} else {
			isAdded = null;
		}

		DBUtil.close();
		return isAdded;
	}

	// (DONE - testing)
	public Boolean addItemToMenu(String[] item, String menu_id) {

		if (user_access.equals("vendor") == false || item.length != 6) {
			return null;
		}
		menu_id = SQLInjection(menu_id);
		item = SQLInjection(item);

		Boolean isAdded = null;

		String item_name = item[0];
		String item_qty = item[1];
		String item_description = item[2];
		String item_dietary = item[3];
		String item_ingredients = item[4];
		String item_price = item[5];

		InsertSQL = "INSERT INTO `item`(`vendor_id`, `item_name`, `item_qty`, `item_description`, `item_dietary`, `item_ingredients`, `item_price`) "
				+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";
		InsertSQL = String.format(InsertSQL, user_id, item_name, item_qty, item_description, item_dietary, item_ingredients,
				item_price);

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		int rowsAdded = DBUtil.execSQL(InsertSQL);

		if (rowsAdded == 1) {
			isAdded = true;
		} else {
			return isAdded;
		}

		//
		// Find Newly Created Item ID
		//
		int item_id = 0;

		SelectSQL = "SELECT item_id FROM `item` WHERE vendor_id = '%s' AND item_name = '%s' AND item_qty = '%s' AND item_description = '%s' AND item_dietary = '%s' AND item_ingredients = '%s' AND item_price = '%s';";
		SelectSQL = String.format(SelectSQL, user_id, item_name, item_qty, item_description, item_dietary, item_ingredients,
				item_price);

		rs = DBUtil.getTable(SelectSQL);

		try {
			while (rs.next()) {
				item_id = rs.getInt("item_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return isAdded;
		}

		DBUtil.close();

		isAdded = addItemToMenu(item_id, menu_id);
		return isAdded;
	}

	// (DONE - testing)
	public Boolean deleteMenu(String menu_id) {
		
		if (user_access.equals("vendor") == false) {
			return null;
		}
		
		Boolean isDeleted = false;
		
		menu_id = SQLInjection(menu_id);
		
		DeleteSQL = "DELETE FROM menu WHERE `menu`.`menu_id` = '%s';";
		DeleteSQL = String.format(DeleteSQL, menu_id);
		
		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isDeleted = true;
		}
		
		return isDeleted;
	}
	
	// (DONE need testing)
	public Boolean addNewMenu() {
		
		if (user_access.equals("vendor") == false) {
			return null;
		}
		
		Boolean isAdded = false;
		
		InsertSQL = "INSERT INTO `menu` (`vendor_id`) VALUES ('%s')";
		InsertSQL = String.format(InsertSQL, user_id);
		
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		int rowsAdded = DBUtil.execSQL(InsertSQL);

		if (rowsAdded == 1) {
			isAdded = true;
		}
		
		DBUtil.close();
		return isAdded;
	}
	// ======================================
	// Extra methods
	// ======================================

	public int getMenuCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT menu_id FROM menu_item;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	public int getSchoolCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT school_id FROM school;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	public int getNormalCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT normal_id FROM normal;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	public int getUserCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT user_id FROM user;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		return count;
	}

	public int getVendorCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT vendor_id FROM vendor;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	public int getItemCount() {
		int count = 0;

		if (user_access.equals("vendor") == false) {
			return count;
		}
		
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT item_id FROM item WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);
		
		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	public int getOrderCount() {
		int count = 0;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		SelectSQL = "SELECT order_id FROM has_order;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		return count;
	}

	// (DONE - Tested)
	protected static String SQLInjection(String str) {
		if (str == null) {
			return null;
		}
		str = str.strip();
		str = str.replaceAll("'", "''");
		return str;
	} // End of SQLInjection

	// (DONE - Tested)
	protected static String[] SQLInjection(String[] strArr) {
		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = SQLInjection(strArr[i]);
		}
		return strArr;
	} // End of SQLInjection
}
