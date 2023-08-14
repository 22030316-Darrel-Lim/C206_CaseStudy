package RunTesty;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import Helper.DBUtil;
import Helper.TableFormatterTest;
import Helper_Package.DBDataTest;
import Helper_Package.DBDataTest_Stage_1;
import Helper_Package.DBDataTest_Stage_2;
import Helper_Package.DBDataTest_Stage_3;
import Helper_Package.DBDataTest_Stage_4;
import Helper_Package.ItemTest;
import Helper_Package.OrderTest;
import Helper_Package.PaymentTest;
import Helper_Package.TempTest;

public class RunAllTest {

	private static Result result;
	private static boolean isSuccessful;

	public static void main(String[] args) {
		System.out.println(runDBDataTest());
	}

	public static boolean runDBDataTest() {
		Result result;
		boolean isAllTested = false;
		int CountCompletedRun = 0;
		int CountTotalRun = 0;
		
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
		
		// Stage 3
		result = JUnitCore.runClasses(DBDataTest_Stage_3.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - Stage 3");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// Stage 4
		result = JUnitCore.runClasses(DBDataTest_Stage_4.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - Stage 4");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// DBData test
		result = JUnitCore.runClasses(DBDataTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("DBData Initialization failed - All");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// TempTest test
		result = JUnitCore.runClasses(TempTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("TempTest failed - All");
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
		
		// Item Test
		result = JUnitCore.runClasses(ItemTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("ItemTest failed");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// Order Test
		result = JUnitCore.runClasses(OrderTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("OrderTest failed");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// Payment Test
		result = JUnitCore.runClasses(PaymentTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("PaymentTest failed");
			return isAllTested;
		}
		
		CountCompletedRun += result.getFailureCount();
		CountTotalRun += result.getRunCount();
		
		// School Test
		result = JUnitCore.runClasses(PaymentTest.class);

		isSuccessful = result.wasSuccessful();

		if (isSuccessful == false) {
			System.out.println("SchoolTest failed");
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
