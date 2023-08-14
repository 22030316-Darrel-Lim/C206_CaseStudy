package Helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TableFormatterTest {

	@Test
	public void testTable_Normal() {

		String tableMenu = "" 
				+ "+----------+----------+-----------+-----------+----------+-------------+\n"
				+ "|   Item   |   info   |   price   |   apple   |   Milk   |   Bangbus   |\n"
				+ "+----------+----------+-----------+-----------+----------+-------------+\n"
				+ "|     1    |     3    |     5     |     7     |     9    |      11     |\n"
				+ "|     2    |     4    |     6     |     8     |    10    |      12     |\n"
				+ "+----------+----------+-----------+-----------+----------+-------------+";

		String[][] tablemenuArray = { { "Item", "info", "price", "apple", "Milk", "Bangbus" },
				{ "1", "3", "5", "7", "9", "11" }, { "2", "4", "6", "8", "10", "12" } };
		String tableActual = TableFormatter.tableFormatter(tablemenuArray);
		assertEquals("TestTable failed", tableMenu, tableActual);
	}

	@Test
	public void testTable_Null() {

		String tableMenu = "" + "+----------+----------+-----------+-----------+----------+-------------+\n"
				+ "|   Item   |   info   |   price   |   apple   |   Milk   |   Bangbus   |\n"
				+ "+----------+----------+-----------+-----------+----------+-------------+\n"
				+ "|     1    |     3    |     5     |     7     |     9    |             |\n"
				+ "|     2    |     4    |     6     |     8     |          |      12     |\n"
				+ "+----------+----------+-----------+-----------+----------+-------------+";

		String[][] tablemenuArray = { { "Item", "info", "price", "apple", "Milk", "Bangbus" },
				{ "1", "3", "5", "7", "9", null }, { "2", "4", "6", "8", null, "12" } };
		String tableActual = TableFormatter.tableFormatter(tablemenuArray);
		assertEquals("TestTable failed", tableMenu, tableActual);
	}

	@Test
	public void testCenterString_Normal() {
		String expected = "      Item      |";
		String center = "Item";
		String truecol = TableFormatter.centerStr(16, center);
		assertEquals(expected, truecol);
	}

	@Test
	public void testCenterString_OddStr() {
		String expected = "      Items     |";
		String center = "Items";
		String truecol = TableFormatter.centerStr(16, center);
		assertEquals(expected, truecol);
	}

	@Test
	public void testCenterString_OddWidth() {
		String expected = "       Item      |";
		String center = "Item";
		String truecol = TableFormatter.centerStr(17, center);
		assertEquals(expected, truecol);
	}

	@Test
	public void testCenterString_LongBus() {
		String expected = "      Bangbus      |";
		String center = "Bangbus";
		String truecol = TableFormatter.centerStr(19, center);
		assertEquals(expected, truecol);
	}

	@Test
	public void testSeparator() {
		String expectedseparator = "+----------+----------+-----------+-----------+----------+-------------+";
		int[] truecolumnwidth = { 10, 10, 11, 11, 10, 13 };
		String truecol = TableFormatter.createSeparator(truecolumnwidth);
		assertEquals(expectedseparator, truecol);
	}

	@Test
	public void testSeparator_Small() {
		String expectedseparator = "+-+";
		int[] truecolumnwidth = { 1 };
		String truecol = TableFormatter.createSeparator(truecolumnwidth);
		assertEquals(expectedseparator, truecol);
	}

	@Test
	public void testSeparator_Small1() {
		String expectedseparator = "+-+-+-+-+-+-+";
		int[] truecolumnwidth = { 1, 1, 1, 1, 1, 1 };
		String truecol = TableFormatter.createSeparator(truecolumnwidth);
		assertEquals(expectedseparator, truecol);
	}

	@SuppressWarnings("unused")
	@Test
	public void testSeparator_Zero() {
		int[] truecolumnwidth = { 0 };
		
		try {
			String truecol = TableFormatter.createSeparator(truecolumnwidth);
		} catch (Exception e) {
			assertTrue(true);
			return;
		} 
		fail("Separactor should fail");
	}
}
