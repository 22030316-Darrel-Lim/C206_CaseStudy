package Helper_Package;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

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

	private static DBData CREDENTIAL;

	public static String getJdbcurl() {
		return JDBCURL;
	}

	public static String getDbusername() {
		return DBUSERNAME;
	}

	public static String getDbpassword() {
		return DBPASSWORD;
	}
	
	// ======================================
	// Stage 1: Test SQL Server
	// DBData check string
	// Check link to DB server
	// Check all table name
	// Check all columns name in table
	// ======================================

	@Test
	public void runDBDataTest_Stage_1() {
		Result result = JUnitCore.runClasses(DBDataTest_Stage_1.class);

		boolean isSuccessful = result.wasSuccessful();

		assertTrue("Stage 1 failed", isSuccessful);
	}

	// ======================================
	// Stage 2: Test DBData methods
	// SQL Injection (All boundaries)
	// Check Email DB (All boundaries)
	// Delete User (All boundaries)
	// Last Login
	// Login user (All boundaries)
	// ======================================

	@Test
	public void runDBDataTest_Stage_2() {
		Result result = JUnitCore.runClasses(DBDataTest_Stage_2.class);

		boolean isSuccessful = result.wasSuccessful();

		assertTrue("Stage 2 failed", isSuccessful);
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

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String actual = CREDENTIAL.getUser_id();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetAccess() {
		add_User();

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String expected = "normal";
		String actual = CREDENTIAL.getUser_access();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetName() {
		add_User();

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String expected = "john[ADD_USER]";
		String actual = CREDENTIAL.getUser_name();

		delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetName_All() {
		String[][] loginCredential = { { "admin2@admin2", "admin2" }, { "normal1@normal1", "normal1" },
				{ "vendor1@vendor1", "vendor1" } };

		String[] expectedName = { "admin2", "normal1", "vendor1" };

		String password;
		String email;

		for (int i = 0; i < loginCredential.length; i++) {
			password = loginCredential[i][1];
			email = loginCredential[i][0];

			CREDENTIAL = new DBData(email, password);

			String Actualname = CREDENTIAL.getUser_name();

			assertEquals("GetName_All failed", expectedName[i], Actualname);
		}
	}

	@Test
	public void testGetUserInfo() {
		String email = "admin2@admin2";
		String password = "admin2";
		CREDENTIAL = new DBData(email, password);

		String today = String.valueOf(LocalDate.now());

		String[] expectedInfo = { "admin2", "admin2@admin2", today };
		String[] actualInfo = CREDENTIAL.getUserInfo();

		assertEquals("GetUserInfo failed both not same length", expectedInfo.length, actualInfo.length);

		for (int i = 0; i < expectedInfo.length; i++) {
			if (i == 2) {
				actualInfo[i] = actualInfo[i].split(" ")[0];
			}
			assertEquals("GetUserInfo failed: ", expectedInfo[i], actualInfo[i]);
		}
	}

	@Test
	public void testGetUserInfo_All() {
		String[][] loginCredential = { { "admin2@admin2", "admin2" }, { "normal1@normal1", "normal1" },
				{ "vendor1@vendor1", "vendor1" } };

		String today = String.valueOf(LocalDate.now());

		String[][] expectedInfo = { { "admin2", "admin2@admin2", today }, { "normal1", "normal1@normal1", today },
				{ "vendor1", "vendor1@vendor1", today } };

		String password;
		String email;

		for (int i = 0; i < loginCredential.length; i++) {
			password = loginCredential[i][1];
			email = loginCredential[i][0];

			CREDENTIAL = new DBData(email, password);
			String[] actualInfo = CREDENTIAL.getUserInfo();

			for (int x = 0; x < expectedInfo.length; x++) {
				if (x == 2) {
					actualInfo[x] = actualInfo[x].split(" ")[0];
				}
				assertEquals("GetUserInfo failed: ", expectedInfo[i][x], actualInfo[x]);
			}
		}
	}

	@Test
	public void testGetVendorInfo_2() {
		String email = "vendor2@vendor2";
		String password = "vendor2";
		CREDENTIAL = new DBData(email, password);

		String today = String.valueOf(LocalDate.now());
		String[] expectedInfo = { "vendor2", email, today, "999", "Company 2", "vendor.png", "Street 2", "3" };
		String[] actualInfo = CREDENTIAL.getVendorInfo();

		assertEquals("GetVendorInfo failed both not same length", expectedInfo.length, actualInfo.length);
		
		for (int i = 0; i < expectedInfo.length; i++) {
			if (i == 2) {
				actualInfo[i] = actualInfo[i].split(" ")[0];
			}
			assertEquals("GetVendorInfo_2 failed: ", expectedInfo[i], actualInfo[i]);
		}
	}
	
	@Test
	public void testGetVendorInfo_1() {
		String email = "vendor1@vendor1";
		String password = "vendor1";
		CREDENTIAL = new DBData(email, password);

		String today = String.valueOf(LocalDate.now());
		String[] expectedInfo = { "vendor1", email, today, "999", "Company 1", "vendor.png", "Street 1", "1,2" };
		String[] actualInfo = CREDENTIAL.getVendorInfo();

		assertEquals("GetVendorInfo failed both not same length", expectedInfo.length, actualInfo.length);
		
		for (int i = 0; i < expectedInfo.length; i++) {
			if (i == 2) {
				actualInfo[i] = actualInfo[i].split(" ")[0];
			}
			assertEquals("GetVendorInfo_1 failed: ", expectedInfo[i], actualInfo[i]);
		}
	}
	
	// ======================================
	// Stage 4: Test DBData main constructors
	// Register Account (Normal, Vendor, Admin)
	// Login User (Normal, Vendor, Admin)
	// ======================================

	// DBData Constructor [Register] (Unit testing included)
	@Test
	public void testConstructorRegister_StartValidation() {
		boolean checkValidation = false;

		String name = "John[StartValidation]";
		String email = "John@email.com[StartValidation]";
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

		String name = "John[InsertNormal]";
		String email = "John@email.com[InsertNormal]";
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
		String name = "John[InsertNormal_Fail]";
		String email = "John@email.com[InsertNormal_Fail]";
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

		String name = "John[InsertVendor]";
		String email = "John@email.com[InsertVendor]";
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
			fail("testConstructorRegister_InsertVendor failed" + e.getMessage());
		}

		DBUtil.close();
		assertTrue("Registration delete User failed", CREDENTIAL.DELETE_USER());
		assertTrue("Registration failed in Vendor Table", checkValidation);

		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertVendor_Fail() {
		String name = "John[InsertVendor_Fail]";
		String email = "John@email.com[InsertVendor_Fail]";
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

		String name = "John[InsertAdmin]";
		String email = "John@email.com[InsertAdmin]";
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
			fail("testConstructorRegister_InsertAdmin failed" + e.getMessage());
		}

		DBUtil.close();
		assertTrue("Registration elete User failed", CREDENTIAL.DELETE_USER());
		assertTrue("Registration failed in Vendor Table", checkValidation);

		CREDENTIAL.DELETE_USER();
		CREDENTIAL = null;
	}

	@Test
	public void testConstructorRegister_InsertAdmin_Fail() {
		String name = "John[InsertAdmin_Fail]";
		String email = "John@email.com[InsertAdmin_Fail]";
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
		String id = add_User();
		String email = "john@email.com[ADD_USER]";
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

	protected static String add_User() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		String name = "john[ADD_USER]";
		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		String access = "normal";
		String login = "2000-01-01 12:00:01.0000";
		int id = 99999;

		// Insert Temp User
		String addSQL = "INSERT INTO user (user_id, user_name, user_email, user_password, ACCESS_TYPE, LAST_LOGIN) VALUES (%d, '%s' , '%s', SHA1('%s'), '%s', '%s');";
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
	
	@After
	public void closeAll() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		for (int i = 0; i < 100; i++) {
			DBUtil.close();
		}
	}
}
