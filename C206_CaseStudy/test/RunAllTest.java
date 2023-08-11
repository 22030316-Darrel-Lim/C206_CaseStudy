import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Helper_Package.DBDataTest;

public class RunAllTest {

	private static Result result;
	
	public static void main(String[] args) {
		System.out.println(runDBDataTest());
	}
	
	private static boolean runDBDataTest() {
	      result = JUnitCore.runClasses(DBDataTest.class);
	      
	      // Print where went wrong
	      for (Failure failure : result.getFailures()) {
	          System.out.println(failure.toString());
	       }
	      
	      // Print number of test ran
	      System.out.println(result.getRunCount());
	      System.out.println(result.getFailureCount());
	      
	      boolean isSuccessful = result.wasSuccessful();
	      return isSuccessful;
	}

}
