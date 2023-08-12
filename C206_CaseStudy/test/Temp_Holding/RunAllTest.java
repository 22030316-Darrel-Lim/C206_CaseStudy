package Temp_Holding;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import Helper.TableFormatterTest;
import Helper_Package.DBDataTest_Stage_1;
import Helper_Package.DBDataTest_Stage_2;

public class RunAllTest {

	private static Result result;
	private static boolean isSuccessful;

	public static void main(String[] args) {
		System.out.println(runDBDataTest());
	}

	public static boolean runDBDataTest() {
		boolean isAllTested = false;

		// Stage 1
		result = JUnitCore.runClasses(DBDataTest_Stage_1.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - Stage 1");
			return isAllTested;
		}
		
		// Stage 2
		result = JUnitCore.runClasses(DBDataTest_Stage_2.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - Stage 2");
			return isAllTested;
		}

		// TableFormatter
		result = JUnitCore.runClasses(TableFormatterTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("TableFormatter failed");
			return isAllTested;
		}

		isAllTested = true;
		return isAllTested;
	}

}
