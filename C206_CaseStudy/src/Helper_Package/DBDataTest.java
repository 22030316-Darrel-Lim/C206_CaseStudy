package Helper_Package;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Helper.DBUtil;

public class DBDataTest {
	
	// ===============================================================
	// Test comes in 4 Stages
	// Stage 1: Test SQL Server
	// Stage 2: Test DBData methods
	// Stage 3: Test Getters
	// Stage 4: Test DBData main constructors
	// Extra methods needed for testing
	// ===============================================================

	private static final String JDBCURL = "jdbc:mysql://localhost/c206_ga";
	private static final String DBUSERNAME = "root";
	private static final String DBPASSWORD = "";

	private static Connection conn;
	private static Statement statement;
	private static ResultSet resultSet;

	// ======================================
	// Stage 1: Test SQL Server
	// DBData check string
	// Check link to DB server
	// Check all table name
	// Check all columns name in table
	// ======================================

	private static String[] allTables = new String[] { "admin", "child", "has_order", "item", "menu_item", "normal",
			"payment", "school", "school_has_vendor", "user", "vendor" };

	
	// Test DBData Final String
	@Test
	public  void testDBDataCheckString() {
		assertEquals("JDBC failed", JDBCURL, DBData.JDBCURL);
		assertEquals("DB username failed", DBUSERNAME, DBData.DBUSERNAME);
		assertEquals("DB password failed", DBPASSWORD, DBData.DBPASSWORD);
	}

	// Test Connection to DB server
	@Test
	public void testDBLinkTODB() {
		boolean connected = false;
		try {
			conn = DriverManager.getConnection(JDBCURL, DBUSERNAME, DBPASSWORD);
			close();
			connected = true;
		} catch (SQLException se) {
			fail("Connection to SQL server failed: " + se.getMessage());
		}

		assertTrue("Connection to SQL server failed", connected);
	}

	// Test calling DBUtil
	@Test
	public  void testDBUtilConnection() {
		boolean connected = false;
		try {
			DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
			DBUtil.close();
			connected = true;
		} catch (Exception e) {
			fail("Connection to DBUtil failed" + e.getMessage());
		}
		assertTrue("Connection to DBUtil failed", connected);
	}

	// Test checking all Tables in DB
	@Test
	public  void testAllTables() {
		String SelectTableSQL;

		boolean TablesFound = false;

		for (String table : allTables) {

			SelectTableSQL = "SELECT * FROM %s;";

			// Format SelectTableSQL with the table in allTables
			SelectTableSQL = String.format(SelectTableSQL, table);

			try {
				conn = DriverManager.getConnection(JDBCURL, DBUSERNAME, DBPASSWORD);
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultSet = statement.executeQuery(SelectTableSQL);

				close();

			} catch (SQLException se) {
				fail("Table not found: " + se.getMessage());
			}
		}
		TablesFound = true;
		assertTrue(TablesFound);
	}

	// Test checking all Tables in DB
	@Test
	public  void testAllTablesColumns() {

		// Columns in each table
		String[] admin = { "admin_id", "admin_profile" };
		String[] child = { "child_id", "child_name", "child_allegies", "normal_id" };
		String[] has_order = { "order_id", "order_status", "preference", "child_id", "school_has_vendor_id", "item_id",
				"payment_id", "normal_id" };
		String[] item = { "item_id", "item_name", "item_qty", "item_description", "item_dietary", "item_ingredients",
				"item_price" };
		String[] menu_item = { "menu_item_id", "item_id", "menu_id" };
		String[] normal = { "normal_id", "normal_phoneNumber", "normal_address", "normal_profile", "normal_allegies" };
		String[] payment = { "payment_id", "payment_name" };
		String[] school = { "school_id", "school_name", "school_address" };
		String[] school_has_vendor = { "school_has_vendor_id", "vendor_id", "school_id" };
		String[] user = { "user_id", "user_name", "user_email", "user_password", "ACCESS_TYPE", "LAST_LOGIN" };
		String[] vendor = { "vendor_id", "vendor_phoneNumber", "vendor_companyName", "vendor_profile", "vendor_address",
				"menu_id" };

		// Assign Tables Columns into nested array
		String[][] TablesColumns = { admin, child, has_order, item, menu_item, normal, payment, school,
				school_has_vendor, user, vendor };

		String SelectTableSQL;
		boolean TablesFound = false;
		int CurrentTable = 0;

		for (String table : allTables) {

			SelectTableSQL = "SELECT * FROM %s;";

			// Format SelectTableSQL with the table in allTables
			SelectTableSQL = String.format(SelectTableSQL, table);

			// Get the Current Table number of columns
			String joinArray = String.join(", ", TablesColumns[CurrentTable]);

			// Replace '*' with joinArray
			SelectTableSQL = SelectTableSQL.replace("*", joinArray);

			try {
				conn = DriverManager.getConnection(JDBCURL, DBUSERNAME, DBPASSWORD);
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultSet = statement.executeQuery(SelectTableSQL);

				close();

			} catch (SQLException se) {
				fail("Table with cloumn not found: [" + table + "] " + se.getMessage());
			}
			CurrentTable++;
		}
		TablesFound = true;
		assertTrue(TablesFound);
	}

	// ======================================
	// Stage 2: Test DBData methods
	// SQL Injection (All boundaries)
	// Check Email DB (All boundaries)
	// Delete User (All boundaries)
	// Last Login
	// Login user (All boundaries)
	// ======================================

	private static String SQLString_testSQLInjection = "Testing SQL Injection";

	// SQL Injection
	@Test
	public void testSQLInjection_Normal() {
		String actual = DBData.SQLInjection(SQLString_testSQLInjection);
		String expected = SQLString_testSQLInjection;
		assertEquals("SQL Injection failed - Normal", expected, actual);
	}

	@Test
	public void testSQLInjection_Null() {
		String actual = DBData.SQLInjection(null);
		String expected = null;
		assertEquals("SQL Injection failed - Null", expected, actual);
	}

	@Test
	public void testSQLInjection_Injection() {
		String injection = "'" + SQLString_testSQLInjection + "'";

		String actual = DBData.SQLInjection(injection);
		String expected = "''Testing SQL Injection''";
		assertEquals("SQL Injection failed - Injection", expected, actual);
	}

	@Test
	public void testSQLInjection_OverInjection() {
		String overInjection = "'''" + SQLString_testSQLInjection + "'''";

		String actual = DBData.SQLInjection(overInjection);
		String expected = "''''''Testing SQL Injection''''''";
		assertEquals("SQL Injection failed - Over Injection", expected, actual);
	}

	// Check Email in DB server
	@Test
	public void testCheckEmailDB_Normal() {
		String email_notDB = "johnny@email.com";
		boolean expected = false;
		boolean actual = DBData.CheckEmailDB(email_notDB);

		assertEquals("CheckEmailDB normal failed: Email found in DB", expected, actual);
	}

	@Test
	public void testCheckEmailDB_Inside() {
		String email_DB = "normal1@normal1";
		boolean expected = true;
		boolean actual = DBData.CheckEmailDB(email_DB);

		assertEquals("CheckEmailDB inside failed: Email not found in DB", expected, actual);
	}

	@Test
	public void testCheckEmailDB_Null() {
		String email_null = null;
		Boolean expected = null;
		Boolean actual = DBData.CheckEmailDB(email_null);

		assertEquals("CheckEmailDB null failed", expected, actual);
	}

	@Test
	public void testCheckEmailDB_Empty() {
		String email_null = "        ";
		Boolean expected = null;
		Boolean actual = DBData.CheckEmailDB(email_null);

		assertEquals("CheckEmailDB empty failed", expected, actual);
	}

	// Delete User
	@Test
	public void testDeleteUser_Normal() {

		String user_id = add_User();
		boolean isDeleted = false;

		// Unit Testing
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String DeleteSQL = "DELETE FROM user WHERE user_id ='%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);
		DBUtil.close();

		if (rowsAffected == 1) {
			isDeleted = true;
		}

		// Once completed
		if (isDeleted == false) {
			delete_User();
		}
		assertTrue("DeleteUser normal failed", isDeleted);
	}

	@Test
	public void testDeleteUser_Null() {
		add_User();
		String user_id = null;
		boolean isDeleted = false;

		// Unit Testing
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String DeleteSQL = "DELETE FROM user WHERE user_id ='%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);
		DBUtil.close();

		if (rowsAffected == 1) {
			isDeleted = true;
		}

		// Once completed
		if (isDeleted == false) {
			delete_User();
		}
		assertFalse("DeleteUser null failed", isDeleted);

	}

	@Test
	public void testDeleteUser_Outside() {
		add_User();
		String user_id = "99999999";
		boolean isDeleted = false;

		// Unit Testing
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String DeleteSQL = "DELETE FROM user WHERE user_id ='%s';";
		DeleteSQL = String.format(DeleteSQL, user_id);

		int rowsAffected = DBUtil.execSQL(DeleteSQL);
		DBUtil.close();

		if (rowsAffected == 1) {
			isDeleted = true;
		}

		// Once completed
		if (isDeleted == false) {
			delete_User();
		}
		assertFalse("DeleteUser outside failed", isDeleted);

	}

	// Last Login
	@Test
	public void testLastLogin() {
		String user_id = add_User();
		String ExpectedTime = "2010-10-10 01:10:10";

		// Unit Testing
		boolean isUpdated = false;

		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String UpdateSQL = "UPDATE user SET LAST_LOGIN = '%s' WHERE user_id = '%s';";

		UpdateSQL = String.format(UpdateSQL, ExpectedTime, user_id);

		DBUtil.execSQL(UpdateSQL);

		int rowsAffected = DBUtil.execSQL(UpdateSQL);

		if (rowsAffected == 1) {
			isUpdated = true;
		}

		DBUtil.close();

		// Once completed
		assertTrue("LastLogin update failed", isUpdated);
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String getTime = "SELECT LAST_LOGIN FROM user WHERE user_id = '%s'";
		getTime = String.format(getTime, user_id);
		ResultSet rs = DBUtil.getTable(getTime);
		String ActualTime = null;

		try {
			while (rs.next()) {
				ActualTime = rs.getString("LAST_LOGIN");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();
		delete_User();
		assertEquals("LastLogin normal failed", ExpectedTime, ActualTime);
	}

	// LOGIN User
	@Test
	public void testLoginUser_Parameter_Null() {
		String email = null;
		String password = null;

		boolean Actual = DBData.LOGIN(email, password);

		assertFalse("LoginUser Parameter null failed", Actual);
	}

	@Test
	public void testLoginUser__Normal() {
		add_User();
		String email = "john@email.com";
		String password = "Password123";

		boolean Actual = DBData.LOGIN(email, password);

		delete_User();
		assertTrue("LoginUser Normal failed", Actual);
	}

	@Test
	public void testLoginUser__Outside() {
		String email = "sssssss@email.com";
		String password = "Password12311111111";

		boolean Actual = DBData.LOGIN(email, password);

		assertFalse("LoginUser Outside failed", Actual);
	}

	// ======================================
	// Stage 3: Test Getters
	// Get id
	// Get access
	// Get name
	// ======================================

	@Test
	public void testGetID() {
		String expected = add_User();

		String email = "john@email.com";
		String password = "Password123";
		DBData temp = new DBData(email, password);

		String actual = temp.getUser_id();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetAccess() {
		add_User();

		String email = "john@email.com";
		String password = "Password123";
		DBData temp = new DBData(email, password);

		String expected = "normal";
		String actual = temp.getUser_access();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetName() {
		add_User();

		String email = "john@email.com";
		String password = "Password123";
		DBData temp = new DBData(email, password);

		String expected = "john";
		String actual = temp.getUser_name();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	// ======================================
	// Stage 4: Test DBData main constructors
	// Register Account (Normal, Vendor, Admin)
	// Login User (Normal, Vendor, Admin) 
	// ======================================
	
	private static DBData CREDENTIAL;
	
	// DBData Constructor [Register] (Unit testing included)
	@Test
	public void testConstructorRegister_StartValidation() {
		boolean checkValidation = false;

		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "normal";
		String[] OtherInfo = { "999", "Beef,Flour,Egg", "Street 123" };

		// Unit testing

		// Check all Inputs
		if (name == null || email == null || password == null || access == null || OtherInfo == null) {
			assertTrue("testConstructorRegister StartValidation - parameter null", checkValidation);
		}

		name = DBData.SQLInjection(email);
		email = DBData.SQLInjection(email);
		password = DBData.SQLInjection(password);

		access = access.toLowerCase();

		// Check if email is in DB
		if (DBData.CheckEmailDB(email) == true) {
			assertTrue("testConstructorRegister StartValidation - Email found", checkValidation);
		}

		// Check if access type input is valid
		else if (access != "normal" && access != "vendor" && access != "admin") {
			assertTrue("testConstructorRegister StartValidation - no Access type found", checkValidation);
		}

		// Once completed
		checkValidation = true;
		assertTrue(checkValidation);
	}

	@Test
	public void testConstructorRegister_StartValidation_Null() {
		boolean checkValidation = false;

		String name = null;
		String email = null;
		String password = null;
		String access = null;
		String[] OtherInfo = null;

		// Unit testing

		// Check all Inputs
		if (name == null || email == null || password == null || access == null || OtherInfo == null) {
			assertFalse(checkValidation);
		}

		// Once completed
		checkValidation = true;
		assertTrue("testConstructorRegister StartValidation - all not null", checkValidation);
	}

	@Test
	public void testConstructorRegister_InsertNormal() {
		boolean checkValidation = false;

		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "normal";
		String[] OtherInfo = { "999", "Beef,Flour,Egg", "Street 123" };

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser is created in User table
		checkValidation = DBData.LOGIN(email, password);
		assertTrue("Regisration failed in User Table", checkValidation);

		String id = CREDENTIAL.getUser_id();
		// Check if tempUser is created in Normal table
		checkValidation = false;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String sql = "SELECT * FROM normal WHERE normal_id = '%s';";

		sql = String.format(sql, id);

		ResultSet rs = DBUtil.getTable(sql);
		try {
			if (rs.next()) {
				checkValidation = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.close();
		assertTrue("Registration elete User failed", CREDENTIAL.DELETE_USER());
		assertTrue("Registration failed in Normal Table", checkValidation);
		
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertNormal_Fail() {
		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "normal";
		String[] OtherInfo = { "999" };

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser failed in Normal
		String Actual_id = CREDENTIAL.getUser_id();
		String Expected_id = null;
		assertEquals("Regisration failed - id", Expected_id, Actual_id);

		String Actual_access = CREDENTIAL.getUser_access();
		String Expected_access = null;
		assertEquals("Regisration failed - id", Expected_access, Actual_access);
		
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertVendor() {
		boolean checkValidation = false;

		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "vendor";
		String[] OtherInfo = { "Company 1", "999", "Street 123" };

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser is created in User table
		checkValidation = DBData.LOGIN(email, password);
		assertTrue("Regisration failed in User Table", checkValidation);

		String id = CREDENTIAL.getUser_id();
		// Check if tempUser is created in Normal table
		checkValidation = false;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String sql = "SELECT * FROM vendor WHERE vendor_id = '%s';";

		sql = String.format(sql, id);

		ResultSet rs = DBUtil.getTable(sql);
		try {
			if (rs.next()) {
				checkValidation = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.close();
		assertTrue("Registration elete User failed", CREDENTIAL.DELETE_USER());
		assertTrue("Registration failed in Vendor Table", checkValidation);

		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertVendor_Fail() {
		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "vendor";
		String[] OtherInfo = { "Company 1" };

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser failed in Normal
		String Actual_id = CREDENTIAL.getUser_id();
		String Expected_id = null;
		assertEquals("Regisration failed - id", Expected_id, Actual_id);

		String Actual_access = CREDENTIAL.getUser_access();
		String Expected_access = null;
		assertEquals("Regisration failed - id", Expected_access, Actual_access);
		
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertAdmin() {
		boolean checkValidation = false;

		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "admin";
		String[] OtherInfo = {};

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser is created in User table
		checkValidation = DBData.LOGIN(email, password);
		assertTrue("Regisration failed in User Table", checkValidation);

		String id = CREDENTIAL.getUser_id();
		// Check if tempUser is created in Admin table
		checkValidation = false;
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);

		String sql = "SELECT * FROM admin WHERE admin_id = '%s';";

		sql = String.format(sql, id);

		ResultSet rs = DBUtil.getTable(sql);
		try {
			if (rs.next()) {
				checkValidation = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.close();
		assertTrue("Registration elete User failed", CREDENTIAL.DELETE_USER());
		assertTrue("Registration failed in Vendor Table", checkValidation);
		
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertAdmin_Fail() {
		String name = "John";
		String email = "John@email.com";
		String password = "Password123";
		String access = "admin";
		String[] OtherInfo = { "1" };

		// Unit testing
		CREDENTIAL = new DBData(name, email, password, access, OtherInfo);

		// Check if tempUser failed in Normal
		String Actual_id = CREDENTIAL.getUser_id();
		String Expected_id = null;
		assertEquals("Regisration failed - id", Expected_id, Actual_id);

		String Actual_access = CREDENTIAL.getUser_access();
		String Expected_access = null;
		assertEquals("Regisration failed - id", Expected_access, Actual_access);
		
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	// DBData Constructor [Login]
	@Test
	public void testConstructorLogin() {
		String id = DBDataTest.add_User();
		String email = "john@email.com";
		String password = "Password123";

		CREDENTIAL = new DBData(email, password);

		String tempUser_id = CREDENTIAL.getUser_id();

		DBDataTest.delete_User();
		assertEquals("Login failed - id", id, tempUser_id);
		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}
	
    @Test
    public void testLoginNormalUser() {
        String email = "normal1@normal1";
        String password = "normal1";
        String expectedAccessType = "normal";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginVendor() {
        String email = "vendor1@vendor1";
        String password = "vendor1";
        String expectedAccessType = "vendor";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginAdmin() {
        String email = "admin2@admin2";
        String password = "admin2";
        String expectedAccessType = "admin";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testFailedLogin() {
        String email = "invalid@example.com";
        String password = "invalid";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        assertNull(CREDENTIAL);
    }

	// ======================================
	// Extra Methods needed for testing
	// ======================================

	private static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {

		}
	}

	protected static String add_User() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String name = "john";
		String email = "john@email.com";
		String password = "Password123";
		String access = "normal";
		String login = "2000-01-01 12:00:01.0000";
		int id = 99999;

		// Insert Temp User
		String addSQL = "INSERT INTO user (user_id, user_name, user_email, user_password, ACCESS_TYPE, LAST_LOGIN) VALUES (%d, '%s' , SHA1('%s'), SHA1('%s'), '%s', '%s');";
		addSQL = String.format(addSQL, id, name, email, password, access, login);
		DBUtil.execSQL(addSQL);

		DBUtil.close();

		return String.valueOf(id);
	}

	protected static void delete_User() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		int id = 99999;

		// Delete Temp User
		String deleteSQL = "DELETE FROM user WHERE user_id = %d;";
		deleteSQL = String.format(deleteSQL, id);

		DBUtil.execSQL(deleteSQL);

		DBUtil.close();
	}
}
