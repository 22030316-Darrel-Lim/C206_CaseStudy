package Temp_Holding;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Helper.TableFormatter;

public class ItemTest {

	@Test
	
	public void testSuccessAddItem() {
		// Simulate Add Item success
		String name = "Bread";
		String qty = "10";
		String description = "Baked yeast";
		String dietary = "NIL";
		String ingredeints = "yeast, flour, salt";
		String price = "2.6";
		// TODO SQL To add Food

	}

	@Test
	public void testFailAddItem() {
		// Simulate Add Item Failure
		String name = "";
		String qty = "";
		String description = "";
		String dietary = "NIL";
		String ingredeints = "yeast, flour, salt";
		String price = "2.6";
		// TODO SQL To add Food
		assertTrue(true);
	}

}
