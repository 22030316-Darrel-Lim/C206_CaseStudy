package Helper_Package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import Helper.DBUtil;

public class DBDataTest_Stage_4 {

	private static final String JDBCURL = DBDataTest.getJdbcurl();
	private static final String DBUSERNAME = DBDataTest.getDbusername();
	private static final String DBPASSWORD = DBDataTest.getDbpassword();
	
	private static DBData CREDENTIAL;


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
		checkValidation = CREDENTIAL.LOGIN(email, password);
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
		checkValidation = CREDENTIAL.LOGIN(email, password);

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
		checkValidation = CREDENTIAL.LOGIN(email, password);
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
		String id = DBDataTest.add_User();
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
}
