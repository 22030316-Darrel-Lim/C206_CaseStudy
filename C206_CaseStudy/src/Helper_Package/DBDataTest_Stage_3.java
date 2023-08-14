package Helper_Package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import Helper.DBUtil;

public class DBDataTest_Stage_3 {

	private static final String JDBCURL = DBDataTest.getJdbcurl();
	private static final String DBUSERNAME = DBDataTest.getDbusername();
	private static final String DBPASSWORD = DBDataTest.getDbpassword();
	
	private static DBData CREDENTIAL;

	@Test
	public void testGetID() {
		String expected = DBDataTest.add_User();

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String actual = CREDENTIAL.getUser_id();

		DBDataTest.delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetAccess() {
		DBDataTest.add_User();

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String expected = "normal";
		String actual = CREDENTIAL.getUser_access();

		DBDataTest.delete_User();
		assertEquals("TestGetID failed", expected, actual);
	}

	@Test
	public void testGetName() {
		DBDataTest.add_User();

		String email = "john@email.com[ADD_USER]";
		String password = "Password123";
		CREDENTIAL = new DBData(email, password);

		String expected = "john[ADD_USER]";
		String actual = CREDENTIAL.getUser_name();

		DBDataTest.delete_User();
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
}
