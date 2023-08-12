package Temp_Holding;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Helper_Package.DBDataTest;
import Helper_Package.DBDataTest_Stage_1;

public class RunAllTest {

	private static Result result;
	
	public static void main(String[] args) {
		runDBDataTest();
	}
	
	public static boolean runDBDataTest() {
		boolean isAllTested = false;
		
	      result = JUnitCore.runClasses(DBDataTest_Stage_1.class);
	      
	      boolean isSuccessful = result.wasSuccessful();
	     
	      if (isSuccessful == false) {
	    	  System.out.println("DBData Initialization failed - Stage 1");
	    	  return isAllTested;
	      }
	      
	      isAllTested = true;
	      return isAllTested;
	}

}
