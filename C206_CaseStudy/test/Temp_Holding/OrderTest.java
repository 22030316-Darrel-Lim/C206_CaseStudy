package Temp_Holding;
import org.junit.Test;

import Helper_Package.DBData;

public class OrderTest {

	@Test
	public void testSuccessOrder() {
		// Simulate Add Item success
		String normal_id = "5";
		String item_id = "4";
		String sch_ven_id = "2";
		String preference = "Korean";
		String status = "Processing";
		// TODO SQL To add Order

	}

	@Test
	public void testFailOrder() {
		// Simulate Add Item Failure
		String normal_id = "99";
		String item_id = "99";
		String sch_ven_id = "2";
		String preference = "Korean";
		String status = "Processing";
		// TODO SQL To add Order

	}
}
