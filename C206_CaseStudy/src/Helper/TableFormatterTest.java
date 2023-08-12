package Helper;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import Helper.TableFormatter;

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

		String tableMenu = "" 
				+ "+----------+----------+-----------+-----------+----------+-------------+\n"
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
}
