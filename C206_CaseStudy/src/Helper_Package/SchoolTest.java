package Helper_Package;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Helper.DBUtil;

public class SchoolTest {

	@Test
	public void testAddSchool() {
	    String schoolName = "New School 5";
	    String schoolAddress = "124 Street, City";

	    // Create an instance of the DBData class
	    DBData dbData = new DBData("admin2@admin2","admin2");

	    // Call the addSchool method on the instance
	    boolean isAdded = dbData.addSchool(schoolName, schoolAddress);

	    assertTrue("TestAddSchool succeed", isAdded);
	}
	@Test
	public void testAddSchoolNull() {
	    String schoolName = null;
	    String schoolAddress = null;

	    // Create an instance of the DBData class
	    DBData dbData = new DBData("admin2@admin2","admin2");

	    // Call the addSchool method on the instance
	    boolean isAdded = dbData.addSchool(schoolName, schoolAddress);

	    assertFalse("TestAddSchoolNull shouldn't succeed", isAdded);
	}
	@Test
	public void testDeleteSchool() {
	    String schoolName = "New School 5";
	    // Create an instance of the DBData class
	    DBData dbData = new DBData("admin2@admin2", "admin2");
	    String query = "SELECT school_id FROM school WHERE school_name = '%s'";
	    query = String.format(query, schoolName);
	    
	    int school_Id = 0;
	    ResultSet resultSet = DBUtil.getTable(query);
	    
	    try {
	        if (resultSet.next()) {
	            school_Id = resultSet.getInt("school_id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    DBUtil.close();

	    // Call the deleteSchool method on the instance
	    boolean isDeleted = dbData.deleteSchool(String.valueOf(school_Id));

	    assertTrue("TestDeleteSchool succeed", isDeleted);
	}


	

	@Test
	public void testDeleteSchoolNoneExisting() {
	    String schoolId = "999";

	    // Create an instance of the DBData class
	    DBData dbData = new DBData("admin2@admin2","admin2");

	    // Call the addSchool method on the instance
	    boolean isAdded = dbData.deleteSchool(schoolId);

	    assertFalse("TestDeleteSchoolNoneExisting should fail", isAdded);
	}

}
