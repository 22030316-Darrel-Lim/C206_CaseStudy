package Helper_Package_Test_NOT_IN_USED;

import java.sql.ResultSet;
import java.sql.SQLException;

import Helper_Package.DBUtil;

public class DBData {

	// NOTE: URL may be different depending on the name of the database
	protected static final String JDBCURL = "jdbc:mysql://localhost/c206_ga";
	protected static final String DBUSERNAME = "root";
	protected static final String DBPASSWORD = "";

	private static String user_access;
	private static String user_id;

	// NOTE: IF any error were to occur, user_access, user_id is to return null
	// Register Account (DONE - TESTING)
	public DBData(String name, String email, String password, String access, String[] OtherInfo) {

		// Set Default FIELD to null
		user_access = null;
		user_id = null;

		// Check all Inputs
		if (name == null || email == null || password == null || access == null || OtherInfo == null) {
			return;
		}

		name = SQLInjection(email);
		email = SQLInjection(email);
		password = SQLInjection(password);

		access = access.toLowerCase();

		// Check if email is in DB
		if (CheckEmailDB(email) == true) {
			return;
		}

		// Check if access type input is valid
		else if (access != "normal" && access != "vendor" && access != "admin") {
			return;
		}

		//
		// Start of Account Creation
		//
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		// Create and format SQL insert Statement
		String InsertSQL = "INSERT INTO user (user_name, user_email, user_password, ACCESS_TYPE, LAST_LOGIN) VALUES ('%s' , SHA1('%s'), SHA1('%s'), '%s', NOW());";
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

		String InsertBYAccessSQL;
		int RowAffectByAccess;
		String phoneNo;
		String address;
		String picture;
		String company;
		String allegies;
		String menu_id = "1";

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
				} else {
					OtherInfo[i] = SQLInjection(OtherInfo[i]);
				}
			}

			phoneNo = OtherInfo[0]; // TODO Validate phone number using regex
			allegies = OtherInfo[1];
			address = OtherInfo[2];
			picture = "user.png";

			InsertBYAccessSQL = "INSERT INTO normal (normal_id, normal_phoneNumber, normal_address, normal_profile, normal_allegies) VALUES ('%s', %s, '%s', '%s', '%s');";
			InsertBYAccessSQL = String.format(InsertBYAccessSQL, user_id, phoneNo, address, picture, allegies);

			RowAffectByAccess = DBUtil.execSQL(InsertBYAccessSQL);

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
				} else {
					OtherInfo[i] = SQLInjection(OtherInfo[i]);
				}
			}

			company = OtherInfo[0];
			phoneNo = OtherInfo[1];
			address = OtherInfo[2];
			picture = "vendor.png";

			InsertBYAccessSQL = "INSERT INTO vendor (vendor_id, vendor_phoneNumber, vendor_companyName, vendor_profile, vendor_address, menu_id) VALUES ('%s' , '%s', '%s', '%s','%s', '%s');";
			InsertBYAccessSQL = String.format(InsertBYAccessSQL, user_id, phoneNo, company, picture, address, menu_id); // TODO
																														// Check
																														// menu
																														// ID

			RowAffectByAccess = DBUtil.execSQL(InsertBYAccessSQL);
			
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

			InsertBYAccessSQL = "INSERT INTO admin (admin_id, admin_profile) VALUES ('%s','%s');";
			InsertBYAccessSQL = String.format(InsertBYAccessSQL, user_id, picture);

			RowAffectByAccess = DBUtil.execSQL(InsertBYAccessSQL);

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

	// (DONE - TESTING)
	public DBData(String email, String password) {
		if (LOGIN(email, password) == false) {
			user_access = null;
			user_id = null;
		}
	}

	// Delete user - Error in creating will delete user (DONE - TESTING)
	protected boolean DELETE_USER() {
		boolean isDeleted = false;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String DeleteSQL = "DELETE FROM user WHERE user_id ='%s';";
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

	// Login (DONE - TESTING)
	protected static boolean LOGIN(String email, String password) {
		boolean isLogged = false;

		// Check all Inputs
		if (email == null || password == null) {
			return isLogged;
		}

		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

			email = SQLInjection(email);
			password = SQLInjection(password);

			String sql = "SELECT ACCESS_TYPE, user_id FROM user WHERE user_email = SHA1('%s') AND user_password = SHA1('%s');";

			sql = String.format(sql, email, password);

			ResultSet rs = DBUtil.getTable(sql);
			if (rs.next()) {
				user_access = rs.getString("ACCESS_TYPE");
				user_id = String.valueOf(rs.getInt("user_id"));
			}

			DBUtil.close();

			// Update Last Login
			if (LAST_LOGIN() == true) {
				isLogged = true;
			}

		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
		return isLogged;
	}

	// (DONE - TESTING)
	protected static Boolean CheckEmailDB(String email) {
		Boolean check = false;

		email = SQLInjection(email);

		// Check if empty
		if (email == null || email.isEmpty()) {
			return null;
		}

		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

			String SelectSQL = "SELECT user_email FROM user WHERE user_email = SHA1('%s');";
			SelectSQL = String.format(SelectSQL, email);

			ResultSet rs = DBUtil.getTable(SelectSQL);

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

	// (DONE - TESTING)
	protected static boolean LAST_LOGIN() {
		boolean isUpdated = false;

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String UpdateSQL = "UPDATE user SET LAST_LOGIN = NOW() WHERE user_id = '%s';";

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

	// (DONE - TESTING)
	public String getUser_name() {
		String name = "";
		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

			String sql = "SELECT user_name FROM user WHERE user_id = %s;";

			sql = String.format(sql, user_id);

			ResultSet rs = DBUtil.getTable(sql);
			if (rs.next()) {
				name = rs.getString("user_name");
			}

			DBUtil.close();

		} catch (SQLException e) {
			name = null;
			System.out.println("SQL Error: " + e.getMessage());
		}

		return name;
	}

	// ======================================
	// Extra methods
	// ======================================

	// (DONE - TESTING)
	protected static String SQLInjection(String str) {
		if (str == null) {
			return null;
		}
		str = str.strip();
		str = str.replaceAll("'", "''");
		return str;
	} // End of SQLInjection
}
