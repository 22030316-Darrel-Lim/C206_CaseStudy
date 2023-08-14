package Helper_Package;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import Helper.DBUtil;

public class SchoolTest {
	
	private static final String JDBCURL = DBDataTest.getJdbcurl();
	private static final String DBUSERNAME = DBDataTest.getDbusername();
	private static final String DBPASSWORD = DBDataTest.getDbpassword();
	
	private static DBData dbData;

	@Before
	public void Login() {
		dbData = Authentication.Login("admin2@admin2", "admin2");
	}

	@Test
	public void testAddSchool() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		
		String schoolName = "New School 5";
		String schoolAddress = "124 Street, City";

		// Call the addSchool method on the instance
		Boolean isAdded = dbData.addSchool(schoolName, schoolAddress);

		assertTrue("TestAddSchool succeed", isAdded);
		
		String DeleteSQL = "DELETE FROM school WHERE school_name = '%s' AND school_address = '%s';";
		DeleteSQL = String.format(DeleteSQL, schoolName, schoolAddress);
		
		DBUtil.execSQL(DeleteSQL);
		
		DBUtil.close();
	}

	@Test
	public void testAddSchoolNull() {
		String schoolName = null;
		String schoolAddress = null;

		// Call the addSchool method on the instance
		Boolean isAdded = dbData.addSchool(schoolName, schoolAddress);

		assertFalse("TestAddSchoolNull shouldn't succeed", isAdded);
	}

	@Test
	public void testDeleteSchool() {
		String schoolName = "New School 5";

		// Call the deleteSchool method on the instance
		Boolean isDeleted = dbData.deleteSchool(String.valueOf(school_Id));

		assertTrue("TestDeleteSchool succeed", isDeleted);
	}

	@Test
	public void testDeleteSchoolNoneExisting() {
		String schoolId = "999";

		// Call the addSchool method on the instance
		boolean isAdded = dbData.deleteSchool(schoolId);

		assertFalse("TestDeleteSchoolNoneExisting should fail", isAdded);
	}

}
