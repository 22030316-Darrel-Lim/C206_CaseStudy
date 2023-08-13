package Temp_Holding;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import Helper.DBUtil;
import Helper.TableFormatterTest;
import Helper_Package.DBDataTest_Stage_1;
import Helper_Package.DBDataTest_Stage_2;

public class RunAllTest {

	private static Result result;
	private static boolean isSuccessful;
	private static int CountCompletedRun = 0;
	private static int CountTotalRun = 0;

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
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// Stage 2
		result = JUnitCore.runClasses(DBDataTest_Stage_2.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - Stage 2");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// TableFormatter
		result = JUnitCore.runClasses(TableFormatterTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("TableFormatter failed");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		
		// END
		isAllTested = true;
		CountCompletedRun = CountTotalRun - CountCompletedRun;
		System.out.printf("Ran %d / %d test\n",CountCompletedRun,CountTotalRun);
		for (int i = 0; i < 100; i++) {
			DBUtil.close();
		}
		
		return isAllTested;
	}

}
