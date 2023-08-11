package Temp_Holding;
import org.junit.Test;

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

	public void testFailAddItem() {
		// Simulate Add Item Failure
		String name = "";
		String qty = "";
		String description = "";
		String dietary = "NIL";
		String ingredeints = "yeast, flour, salt";
		String price = "2.6";
		// TODO SQL To add Food
	}

}
