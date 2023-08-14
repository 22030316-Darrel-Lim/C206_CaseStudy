package Helper_Package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Helper.DBUtil;

public class DBDataTest_Stage_2 {

	private static final String JDBCURL = DBDataTest.getJdbcurl();
	private static final String DBUSERNAME = DBDataTest.getDbusername();
	private static final String DBPASSWORD = DBDataTest.getDbpassword();
	
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
		String inject = null;
		
		String actual = DBData.SQLInjection(inject);
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

		String user_id = DBDataTest.add_User();
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
			DBDataTest.delete_User();
		}
		assertTrue("DeleteUser normal failed", isDeleted);
	}

	@Test
	public void testDeleteUser_Null() {
		DBDataTest.add_User();
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
			DBDataTest.delete_User();
		}
		assertFalse("DeleteUser null failed", isDeleted);

	}

	@Test
	public void testDeleteUser_Outside() {
		DBDataTest.add_User();
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
			DBDataTest.delete_User();
		}
		assertFalse("DeleteUser outside failed", isDeleted);

	}

	// Last Login
	@Test
	public void testLastLogin() {
		String user_id = DBDataTest.add_User();
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
		DBDataTest.delete_User();
		assertEquals("LastLogin normal failed", ExpectedTime, ActualTime);
	}

	// LOGIN User
	@Test
	public void testLoginUser_Parameter_Null() {
		String email = null;
		String password = null;

		DBData temp = Authentication.Login(email, password);

		assertNull("LoginUser Parameter null failed", temp);
	}

	@Test
	public void testLoginUser__Normal() {
		DBDataTest.add_User();
		String email = "john@email.com[ADD_USER]";
		String password = "Password123";

		DBData temp = Authentication.Login(email, password);

		DBDataTest.delete_User();
		assertNotNull("LoginUser Normal failed", temp);
	}

	@Test
	public void testLoginUser__Outside() {
		String email = "sssssss@email.com";
		String password = "Password12311111111";

		DBData temp = Authentication.Login(email, password);

		assertNull("LoginUser Outside failed", temp);
	}
}
