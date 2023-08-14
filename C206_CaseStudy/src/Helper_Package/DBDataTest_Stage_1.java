package Helper_Package;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;
import Helper.DBUtil;

public class DBDataTest_Stage_1 {

	private static final String JDBCURL = DBDataTest.getJdbcurl();
	private static final String DBUSERNAME = DBDataTest.getDbusername();
	private static final String DBPASSWORD = DBDataTest.getDbpassword();

	private static Connection conn;
	private static Statement statement;
	private static ResultSet resultSet;

	private static String[] allTables = new String[] { "admin", "child", "has_order", "item", "menu", "menu_item",
			"normal", "payment", "school", "school_has_vendor", "user", "vendor" };

	// Test DBData Final String
	@Test
	public void testDBDataCheckString() {
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
	public void testDBUtilConnection() {
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
	public void testAllTables() {
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
	public void testAllTablesColumns() {

		// Columns in each table
		String[] admin = { "admin_id", "admin_profile" };
		String[] child = { "child_id", "child_name", "child_allegies", "normal_id" };
		String[] has_order = { "order_id", "order_status", "preference", "child_id", "school_has_vendor_id", "menu_item_id",
				"payment_id", "normal_id" };
		String[] item = { "item_id", "vendor_id", "item_name", "item_qty", "item_description", "item_dietary", "item_ingredients",
				"item_price" };
		String[] menu = { "menu_id", "vendor_id" };
		String[] menu_item = { "menu_item_id", "item_id", "menu_id" };
		String[] normal = { "normal_id", "normal_phoneNumber", "normal_address", "normal_profile", "normal_allegies" };
		String[] payment = { "payment_id", "payment_name" };
		String[] school = { "school_id", "school_name", "school_address" };
		String[] school_has_vendor = { "school_has_vendor_id", "vendor_id", "school_id" };
		String[] user = { "user_id", "user_name", "user_email", "user_password", "ACCESS_TYPE", "LAST_LOGIN" };
		String[] vendor = { "vendor_id", "vendor_phoneNumber", "vendor_companyName", "vendor_profile",
				"vendor_address" };

		// Assign Tables Columns into nested array
		String[][] TablesColumns = { admin, child, has_order, item, menu, menu_item, normal, payment, school,
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
}
