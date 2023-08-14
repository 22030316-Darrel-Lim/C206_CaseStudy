package Helper_Package;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Helper.DBUtil;

public class OrderTest {
	@Test
	public void testAddOrder() {
		// Create an instance of the DBData class
		DBData dbData = new DBData("normal1@normal1", "normal1");

		String orderStatus = "Preparing";
		String preference = "Test Preference";
		String childId = "1";
		String schoolHasVendorId = "2";
		String menuItemId = "3";
		String paymentId = "1";

		// Call the addOrder method on the instance
		boolean isAdded = dbData.addOrder(orderStatus, preference, childId, schoolHasVendorId, menuItemId, paymentId);
		assertTrue("TestAddOrder succeed", isAdded);
	}

	@Test
	public void testDeleteOrder() {
		String preference = "Test Preference";

		// Create an instance of the DBData class
		DBData dbData = new DBData("normal1@normal1", "normal1");

		String query = "SELECT order_id FROM has_order WHERE preference = '%s'";
		query = String.format(query, preference);

		int order_Id = 5;
		ResultSet resultSet = DBUtil.getTable(query);

		try {
			if (resultSet.next()) {
				order_Id = resultSet.getInt("order_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close();

		// Call the deleteOrder method on the instance
		boolean isDeleted = dbData.deleteOrder(String.valueOf(order_Id));

		assertTrue("TestDeleteOrder succeed", isDeleted);
	}

}
