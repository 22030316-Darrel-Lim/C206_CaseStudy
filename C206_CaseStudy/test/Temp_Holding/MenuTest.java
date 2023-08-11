package Temp_Holding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import Helper.DBUtil;

public class MenuTest {

	private static final String JDBCURL = "jdbc:mysql://localhost/c206_ga";
	private static final String DBUSERNAME = "root";
	private static final String DBPASSWORD = "";

	private static Connection conn;
	private static Statement statement;
	private static ResultSet resultSet;

	@Test
	public void testSuccessCreateMenu() {
		// Simulate Menu Creation Success
		// TODO SQL To add Menu

	}

	@Test
	public void testSuccessCreateMenu_Food1() {
		String item_id = "3";

		// Simulate Menu Creation Success with Food
		// TODO SQL To add Menu

	}

	@Test
	public void testSuccessCreateMenu_Food2() {
		String[] item_id = { "3", "4" };

		// Simulate Menu Creation Success with multiple Food
		// TODO SQL To add Menu

	}

	@Test
	public void testFailCreateMenu_Food() {
		String item_id = "99";

		// Simulate Menu Creation Failure with invalid Food
		// TODO SQL To add Menu

	}

}
