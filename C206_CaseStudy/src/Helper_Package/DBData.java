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

	private String InsertSQL;
	private String DeleteSQL;
	private String UpdateSQL;
	private String SelectSQL;
	private Boolean isAllChecked;
	private boolean isChecked;

	private int count;

	private ResultSet rs;

	private String user_access;
	private String user_id;

	// NOTE: IF any error were to occur, user_access, user_id is to return null
	// Register Account (DONE - TESTED)
	protected DBData(String name, String email, String password, String access, String[] OtherInfo) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

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
		isAllChecked = CheckEmailDB(email);
		if (isAllChecked == null || isAllChecked == true) {
			return;
		}

		// Check if access type input is valid
		else if ((access.equals("normal") || access.equals("vendor") || access.equals("admin")) == false) {
			return;
		}

		//
		// Start of Account Creation
		//

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
	protected DBData(String email, String password) {
		if (LOGIN(email, password) == false) {
			user_access = null;
			user_id = null;
		}
	}

	// Delete user - Error in creating will delete user (DONE - TESTED)
	protected boolean DELETE_USER() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isChecked = false;

		DeleteSQL = "DELETE FROM user WHERE user_id = '%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);

		if (rowsAffected == 1) {
			isChecked = true;
		}

		user_access = null;
		user_id = null;

		DBUtil.close();
		return isChecked;
	}

	// Delete user - Error in creating will delete user (DONE - TESTED)
	public boolean DELETE_USER(String user_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isChecked = false;

		if (user_access.equals("admin") == false) {
			return isChecked;
		}

		user_id = SQLInjection(user_id);

		DeleteSQL = "DELETE FROM user WHERE user_id = '%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);

		if (rowsAffected == 1) {
			isChecked = true;
		}

		user_access = null;
		user_id = null;

		DBUtil.close();
		return isChecked;
	}

	// Login (DONE - TESTED)
	protected boolean LOGIN(String email, String password) {

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isChecked = false;

		// Check all Inputs
		if (email == null || password == null) {
			return isChecked;
		}

		email = SQLInjection(email);
		password = SQLInjection(password);

		SelectSQL = "SELECT ACCESS_TYPE, user_id FROM user WHERE user_email = '%s' AND user_password = SHA1('%s');";

		SelectSQL = String.format(SelectSQL, email, password);

		try {

			rs = DBUtil.getTable(SelectSQL);
			if (rs.next()) {
				setUser_access(rs.getString("ACCESS_TYPE"));
				// user_access = rs.getString("ACCESS_TYPE");
				user_id = String.valueOf(rs.getInt("user_id"));
			}

			// Update Last Login
			if (LAST_LOGIN() == true) {
				isChecked = true;
			}

		} catch (SQLException e) {
			System.out.println("SQL Error (LOGIN): " + e.getMessage());
		}

		DBUtil.close();
		return isChecked;
	}

	// Check email in DB (DONE - TESTED)
	protected static Boolean CheckEmailDB(String email) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		Boolean isAllChecked = false;

		email = SQLInjection(email);

		// Check if empty
		if (email == null || email.isEmpty()) {
			return null;
		}

		try {

			String SelectSQL = "SELECT user_email FROM user WHERE user_email = '%s';";
			SelectSQL = String.format(SelectSQL, email);

			ResultSet rs = DBUtil.getTable(SelectSQL);

			// Getting all the email from the SQL database and comparing it to the input
			// if rs = null - no result
			while (rs.next()) {
				isAllChecked = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (CheckEmailDB): " + e.getMessage());
		}

		DBUtil.close();
		return isAllChecked;
	} // End of CheckEmailDB

	// Updated last login (DONE - TESTED)
	protected boolean LAST_LOGIN() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isChecked = false;

		UpdateSQL = "UPDATE user SET LAST_LOGIN = NOW() WHERE user_id = '%s';";

		UpdateSQL = String.format(UpdateSQL, user_id);

		DBUtil.execSQL(UpdateSQL);

		int rowsAffected = DBUtil.execSQL(UpdateSQL);

		if (rowsAffected == 1) {
			isChecked = true;
		}

		DBUtil.close();
		return isChecked;
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

	public void setUser_access(String user_access) {
		this.user_access = user_access;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	// (Done need tesing)
	public String getUser_id(String email) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String user_id = null;

		if (user_access.equals("admin") == false) {
			return user_id;
		}

		else if (CheckEmailDB(email) != true) {
			return user_id;
		}

		SelectSQL = "SELECT user_id FROM user WHERE user_email = '%s';";
		SelectSQL = String.format(SelectSQL, email);

		try {
			rs = DBUtil.getTable(SelectSQL);

			while (rs.next()) {
				user_id = rs.getString("user_id");
			}

		} catch (SQLException e) {
			System.out.println("SQL Error (getUser_id): " + e.getMessage());
		}

		DBUtil.close();
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
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String[] userInfo = new String[3];

		SelectSQL = "SELECT `user_name`, `user_email`, `LAST_LOGIN` FROM `user` WHERE user_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

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
			System.out.println("SQL Error (getUserInfo): " + e.getMessage());
		}

		DBUtil.close();
		return userInfo;
	}

	public String[][] viewAllOrder() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("admin") == false) {
			return null;
		}

		int column = getOrderCount() + 1;
		String[] header = { "Order ID", "order Status", "Child ID", "School Has Vendor ID", "Payment Type",
				"Normal ID" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select order_id,order_status,child_id,school_has_vendor_id,payment_name,normal_id FROM has_order INNER JOIN payment ON payment.payment_id = has_order.payment_id;";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String order_id = rs.getString("order_id");
				String status = rs.getString("order_status");
				String child_id = rs.getString("child_id");
				String school_has_vendor_id = rs.getString("school_has_vendor_id");
				String payment_type = rs.getString("payment_name");
				String normal_id = rs.getString("normal_id");

				table[count + 1][0] = order_id;
				table[count + 1][1] = status;
				table[count + 1][2] = child_id;
				table[count + 1][3] = school_has_vendor_id;
				table[count + 1][4] = payment_type;
				table[count + 1][5] = normal_id;
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (viewAllOrder): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	// (DOne need testing)
	public Boolean deleteOrder(String order_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		order_id = SQLInjection(order_id);

		DeleteSQL = "DELETE FROM has_order WHERE `order_id` = '%s';";
		DeleteSQL = String.format(DeleteSQL, order_id);

		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (Done need testing)
	public String getItemInfo(String item_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String itemInfo = "" + "\n======= Food =======\n" + "Item ID: %s\n" + "Item name: %s\n" + "Item quantity: %s\n"
				+ "Item description: %s\n" + "Item dietary: %s\n" + "Item ingredients: %s\n" + "Item price: $%.2f\n";

		item_id = SQLInjection(item_id);

		SelectSQL = "SELECT item_name, item_qty, item_description, item_dietary, item_ingredients, item_price FROM item WHERE item_id = '%s';";
		SelectSQL = String.format(SelectSQL, item_id);

		try {
			rs = DBUtil.getTable(SelectSQL);

			while (rs.next()) {

				String item_name = rs.getString("item_name");
				String item_qty = rs.getString("item_qty");
				String item_description = rs.getString("item_description");
				String item_dietary = rs.getString("item_dietary");
				String item_ingredients = rs.getString("item_ingredients");
				double item_price = rs.getDouble("item_price");

				itemInfo = String.format(itemInfo, item_id, item_name, item_qty, item_description, item_dietary,
						item_ingredients, item_price);
			}

		} catch (SQLException e) {
			System.out.println("SQL Error (getUser_id): " + e.getMessage());
		}

		DBUtil.close();
		return itemInfo;
	}

	// ------------ Admin ONLY
	public String[][] viewAllUser() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("admin") == false) {
			return null;
		}

		int column = getUserCount() + 1;
		String[] header = { "User ID", "User Name", "User Email", "Access Type", "Last Login" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select user_id,user_name,user_email,ACCESS_TYPE,LAST_LOGIN FROM user;";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String id = rs.getString("user_id");
				String name = rs.getString("user_name");
				String email = rs.getString("user_email");
				String access = rs.getString("ACCESS_TYPE");
				String login = rs.getString("LAST_LOGIN");

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
			System.out.println("SQL Error (viewAllUser): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	public String[][] viewAllSchool() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if ((user_access.equals("admin") || user_access.equals("vendor")) == false) {
			return null;
		}

		int column = getSchoolCount() + 1;
		String[] header = { "School ID", "School Name", "School Address" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select school_id,school_name,school_address FROM school;";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String id = rs.getString("school_id");
				String name = rs.getString("school_name");
				String address = rs.getString("school_address");

				table[count + 1][0] = id;
				table[count + 1][1] = name;
				table[count + 1][2] = address;
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (viewAllSchool): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	public String[][] viewAllPayment() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		int column = getPaymentCount() + 1;
		String[] header = { "Payment ID", "Payment Name" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select payment_id,payment_name FROM payment;";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String id = rs.getString("payment_id");
				String name = rs.getString("payment_name");

				table[count + 1][0] = id;
				table[count + 1][1] = name;
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (viewAllPayment): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	public String[][] viewUserOrder() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("normal") == false) {
			return null;
		}

		int column = getOrderCount() + 1;
		String[] header = { "Order ID", "order Status", "Child ID", "School Has Vendor ID", "Payment Type",
				"Normal ID" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select order_id, order_status, child_id, school_has_vendor_id, payment_name, normal_id FROM has_order INNER JOIN payment ON payment.payment_id = has_order.payment_id WHERE normal_id = '%s';";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String order_id = rs.getString("order_id");
				String status = rs.getString("order_status");
				String child_id = rs.getString("child_id");
				String school_has_vendor_id = rs.getString("school_has_vendor_id");
				String payment_type = rs.getString("payment_name");
				String normal_id = rs.getString("normal_id");

				table[count + 1][0] = order_id;
				table[count + 1][1] = status;
				table[count + 1][2] = child_id;
				table[count + 1][3] = school_has_vendor_id;
				table[count + 1][4] = payment_type;
				table[count + 1][5] = normal_id;
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (viewUserOrder): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	public String[][] viewAllMenu() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		int column = getMenuCount() + 1;
		String[] header = { "Menu ID", "Item ID", "Item Name", "Quantity", "Description", "Price" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select menu_id,menu_item.item_id,item_name,item_qty,item_description,item_price FROM menu_item INNER JOIN item ON item.item_id = menu_item.item_id;";

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String menu_id = rs.getString("menu_id");
				String item_id = rs.getString("menu_item.item_id");
				String item_name = rs.getString("item_name");
				String item_qty = rs.getString("item_qty");
				String item_description = rs.getString("item_description");
				double item_price = rs.getDouble("item_price");

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
			System.out.println("SQL Error (viewAllMenu): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	// (DONE need testing)
	public Boolean addSchool(String school_name, String school_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("admin") == false) {
			return null;
		}

		isAllChecked = false;

		InsertSQL = "INSERT INTO `school` (`school_name`, `school_address`) VALUES ('%s','%s');";
		InsertSQL = String.format(InsertSQL, school_name, school_id);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (DOne need testing)
	public Boolean deleteSchool(String school_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("admin") == false) {
			return null;
		}

		isAllChecked = false;

		school_id = SQLInjection(school_id);

		DeleteSQL = "DELETE FROM school WHERE `school`.`school_id` = '%s';";
		DeleteSQL = String.format(DeleteSQL, school_id);

		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (DOne need testing)
	public Boolean addPayment(String payment_name) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("admin") == false) {
			return null;
		}

		payment_name = SQLInjection(payment_name);

		InsertSQL = "INSERT INTO `payment`( `payment_name`) VALUES ('%s');";
		InsertSQL = String.format(InsertSQL, payment_name);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (DOne need testing)
	public Boolean deletePayment(String payment_name) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("admin") == false) {
			return null;
		}

		payment_name = SQLInjection(payment_name);

		DeleteSQL = "DELETE FROM `payment` WHERE `payment_name` = '%s';";
		DeleteSQL = String.format(DeleteSQL, payment_name);

		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// ---------- Vendor ONLY
	// (DONE - Tested)
	public String[] getVendorInfo() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String[] vendorInfo = new String[5];

		String[] userInfo = getUserInfo();

		//
		// Get Vendor Menu Info
		//
		ArrayList<String> menufoundList = new ArrayList<String>();

		SelectSQL = "SELECT `menu_id` FROM `menu` INNER JOIN vendor ON vendor.vendor_id = menu.vendor_id WHERE vendor.vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

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
			System.out.println("SQL Error (getVendorInfo) Get Vendor Menu Info Failed: " + e.getMessage());
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
			System.out.println("SQL Error (getVendorInfo) Get Vender Table info Failed: " + e.getMessage());
		}

		DBUtil.close();
		return vendorInfo;
	}

	public String[][] viewAllFood() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("vendor") == false) {
			return null;
		}

		int column = getItemCount() + 1;

		String[] header = { "Item ID", "Name", "Quantity", "Description", "Dietary", "Ingredients", "Price" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "Select item_id,item_name,item_qty,item_description,item_dietary,item_ingredients,item_price FROM item WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String item_id = rs.getString("item_id");
				String item_name = rs.getString("item_name");
				String item_qty = rs.getString("item_qty");
				String item_description = rs.getString("item_description");
				String item_dietary = rs.getString("item_dietary");
				String item_ingredients = rs.getString("item_ingredients");
				double item_price = rs.getDouble("item_price");

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
			System.out.println("SQL Error (viewAllFood): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	// (DONE - testing)
	public Boolean addItemToMenu(int item_id, String menu_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("vendor") == false) {
			return null;
		}
		menu_id = SQLInjection(menu_id);

		// Check if item_id inside menu_item
		SelectSQL = "SELECT item_id FROM menu_item WHERE item_id = '%s'";
		SelectSQL = String.format(SelectSQL, item_id);

		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (count != 0) {
			return isAllChecked;
		}

		InsertSQL = "INSERT INTO `menu_item` (`item_id`, `menu_id`) VALUES ('%d', '%s');";
		InsertSQL = String.format(InsertSQL, item_id, menu_id);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		} else {
			isAllChecked = null;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (DONE - testing)
	public Boolean addItemToMenu(String[] item, String menu_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = null;

		if (user_access.equals("vendor") == false || item.length != 6) {
			return null;
		}
		menu_id = SQLInjection(menu_id);
		item = SQLInjection(item);

		String item_name = item[0];
		String item_qty = item[1];
		String item_description = item[2];
		String item_dietary = item[3];
		String item_ingredients = item[4];
		String item_price = item[5];

		InsertSQL = "INSERT INTO `item`(`vendor_id`, `item_name`, `item_qty`, `item_description`, `item_dietary`, `item_ingredients`, `item_price`) "
				+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";
		InsertSQL = String.format(InsertSQL, user_id, item_name, item_qty, item_description, item_dietary,
				item_ingredients, item_price);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		} else {
			return isAllChecked;
		}

		//
		// Find Newly Created Item ID
		//
		int item_id = 0;

		SelectSQL = "SELECT item_id FROM `item` WHERE vendor_id = '%s' AND item_name = '%s' AND item_qty = '%s' AND item_description = '%s' AND item_dietary = '%s' AND item_ingredients = '%s' AND item_price = '%s';";
		SelectSQL = String.format(SelectSQL, user_id, item_name, item_qty, item_description, item_dietary,
				item_ingredients, item_price);

		rs = DBUtil.getTable(SelectSQL);

		try {
			while (rs.next()) {
				item_id = rs.getInt("item_id");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (addItemToMenu): " + e.getMessage());
			return isAllChecked;
		}

		isAllChecked = addItemToMenu(item_id, menu_id);

		DBUtil.close();
		return isAllChecked;
	}

	// (DONE - testing)
	public Boolean deleteMenu(String menu_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("vendor") == false) {
			return null;
		}

		menu_id = SQLInjection(menu_id);

		DeleteSQL = "DELETE FROM menu WHERE `menu`.`menu_id` = '%s';";
		DeleteSQL = String.format(DeleteSQL, menu_id);

		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (DONE need testing)
	public Boolean addNewMenu() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("vendor") == false) {
			return null;
		}

		InsertSQL = "INSERT INTO `menu` (`vendor_id`) VALUES ('%s');";
		InsertSQL = String.format(InsertSQL, user_id);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (Done need testing)
	public Boolean deleteItem(String item_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("vendor") == false) {
			return null;
		}

		item_id = SQLInjection(item_id);

		DeleteSQL = "DELETE FROM item WHERE `item`.`item_id` = '%s';";
		DeleteSQL = String.format(DeleteSQL, item_id);

		int rowsDeleted = DBUtil.execSQL(DeleteSQL);

		if (rowsDeleted == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// (Done need tesing)
	public String[][] viewSchoolHasVendor() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		if (user_access.equals("vendor") == false) {
			return null;
		}

		int column = getVenderHasSchoolCount() + 1;

		String[] header = { "School ID", "School Name", "School Address" };
		int row = header.length;

		String table[][] = new String[column][row];

		SelectSQL = "SELECT school.school_id,school_name,school_address FROM `school` INNER JOIN school_has_vendor ON school.school_id = school_has_vendor.school_id WHERE school_has_vendor.vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		// Set first index of data to header
		table[0] = header;

		// Assigning values into data
		rs = DBUtil.getTable(SelectSQL);
		count = 0;
		try {
			while (rs.next()) {
				// Assign values based on header info
				String school_id = rs.getString("school.school_id");
				String school_name = rs.getString("school_name");
				String school_address = rs.getString("school_address");

				table[count + 1][0] = school_id;
				table[count + 1][1] = school_name;
				table[count + 1][2] = school_address;
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (viewSchoolHasVendor): " + e.getMessage());
		}

		DBUtil.close();
		return table;
	}

	// (Done need tesing)
	public Boolean addSchoolHasVendor(String school_id) {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		isAllChecked = false;

		if (user_access.equals("vendor") == false) {
			return null;
		}

		school_id = SQLInjection(school_id);

		InsertSQL = "INSERT INTO `school_has_vendor` (`vendor_id`, `school_id`) VALUES ('%s', '%s')";
		InsertSQL = String.format(InsertSQL, user_id, school_id);

		count = DBUtil.execSQL(InsertSQL);

		if (count == 1) {
			isAllChecked = true;
		}

		DBUtil.close();
		return isAllChecked;
	}

	// ======================================
	// Extra methods
	// ======================================

	public int getMenuCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		SelectSQL = "SELECT menu_id FROM menu_item;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getMenuCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getPaymentCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		SelectSQL = "SELECT payment_id FROM payment;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getPaymentCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getSchoolCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		SelectSQL = "SELECT school_id FROM school;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getSchoolCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getVenderHasSchoolCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;

		if (user_access.equals("vendor") == false) {
			return count;
		}

		SelectSQL = "SELECT school_id FROM school_has_vendor WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getVenderHasSchoolCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getNormalCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		SelectSQL = "SELECT normal_id FROM normal;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getNormalCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getUserCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;

		SelectSQL = "SELECT user_id FROM user;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getUserCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getVendorCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		SelectSQL = "SELECT vendor_id FROM vendor;";

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getVendorCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getItemCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		if (user_access.equals("vendor") == false) {
			return count;
		}

		SelectSQL = "SELECT item_id FROM item WHERE vendor_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getItemCount): " + e.getMessage());
		}

		DBUtil.close();
		return count;
	}

	public int getOrderCount() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		count = 0;
		if (user_access.equals("normal") == false) {
			return count;
		}
		SelectSQL = "SELECT order_id FROM has_order WHERE normal_id = '%s';";
		SelectSQL = String.format(SelectSQL, user_id);

		rs = DBUtil.getTable(SelectSQL);
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error (getOrderCount): " + e.getMessage());
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
