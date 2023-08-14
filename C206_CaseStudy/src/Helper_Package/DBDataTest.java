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
	public void runDBDataTest_Stage_3() {
		Result result = JUnitCore.runClasses(DBDataTest_Stage_3.class);

		boolean isSuccessful = result.wasSuccessful();

		assertTrue("Stage 3 failed", isSuccessful);
	}
	
	// ======================================
	// Stage 4: Test DBData main constructors
	// Register Account (Normal, Vendor, Admin)
	// Login User (Normal, Vendor, Admin)
	// ======================================

	@Test
	public void runDBDataTest_Stage_4() {
		Result result = JUnitCore.runClasses(DBDataTest_Stage_4.class);

		boolean isSuccessful = result.wasSuccessful();

		assertTrue("Stage 4 failed", isSuccessful);
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
