import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import Helper_Package.Authentication;
import Helper_Package.DBData;

public class MenuTest {

	@Test
	public void testSuccessCreateMenu() {
		// Simulate successful Menu Creation
		// TODO SQL To add Menu

	}

	@Test
	public void testSuccessCreateMenu_Food1() {
		String item_id = "3";

		// Simulate successful Menu Creation with Food
		// TODO SQL To add Menu

	}

	@Test
	public void testSuccessCreateMenu_Food2() {
		String[] item_id = { "3", "4" };

		// Simulate successful Menu Creation with multiple Food
		// TODO SQL To add Menu

	}

	@Test
	public void testFailCreateMenu_Food() {
		String item_id = "99";

		// Simulate Fail Menu Creation with invalid Food
		// TODO SQL To add Menu

	}
}
