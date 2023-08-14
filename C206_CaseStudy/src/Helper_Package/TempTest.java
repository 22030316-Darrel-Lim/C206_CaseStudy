package Helper_Package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Helper.DBUtil;

public class TempTest {

	private static final String JDBCURL = "jdbc:mysql://localhost/c206_ga";
	private static final String DBUSERNAME = "root";
	private static final String DBPASSWORD = "";

	@Before
	public void initDB() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
	}

	@After
	public void closeAll() {
		DBUtil.init(JDBCURL, DBUSERNAME, DBPASSWORD);
		for (int i = 0; i < 100; i++) {
			DBUtil.close();
		}
	}
	
	@Test
	public void testAllLogin_NotNull() {

		Result result = JUnitCore.runClasses(testAllLogin_NotNull.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_NotNull");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_NotNull",result.wasSuccessful());
	}
	
	@Test
	public void testAllLogin_ID() {

		Result result = JUnitCore.runClasses(testAllLogin_ID.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_ID");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_ID",result.wasSuccessful());
	}
	
	@Test
	public void testAllLogin_ID_FromEmail() {

		Result result = JUnitCore.runClasses(testAllLogin_ID_FromEmail.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_ID_FromEmail");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_ID_FromEmail",result.wasSuccessful());
	}

	@Test
	public void testAllLogin_Access() {

		Result result = JUnitCore.runClasses(testAllLogin_ID_FromEmail.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_Access");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_Access",result.wasSuccessful());
	}

	@Test
	public void testAllLogin_Name() {

		Result result = JUnitCore.runClasses(testAllLogin_ID_FromEmail.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_Name");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_Name",result.wasSuccessful());
	}
	
	@Test
	public void testAllLogin_Fail_Password() {

		Result result = JUnitCore.runClasses(testAllLogin_ID_FromEmail.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_Fail_Password");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_Fail_Password",result.wasSuccessful());
	}
	
	@Test
	public void testAllLogin_Fail_Pass_Half() {

		Result result = JUnitCore.runClasses(testAllLogin_ID_FromEmail.class);
		System.out.printf("\nRan %d runs with failures %d in %s:\n",result.getRunCount(), result.getFailureCount(), "testAllLogin_Fail_Pass_Half");
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		assertTrue("testAllLogin_Fail_Pass_Half",result.wasSuccessful());
	}
	
	public static class testAllLogin_NotNull {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_2() {
			String email = "normal2@normal2";
			String password = "normal2";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_3() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_1() {
			String email = "vendor1@vendor1";
			String password = "vendor1";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_2() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_1() {
			String email = "admin1@admin1";
			String password = "admin1";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_2() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

	}

	public static class testAllLogin_ID {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "4";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_2() {
			String email = "normal2@normal2";
			String password = "normal2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "5";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_3() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "6";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_1() {
			String email = "vendor1@vendor1";
			String password = "vendor1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "2";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_2() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "3";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_1() {
			String email = "admin1@admin1";
			String password = "admin1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "8";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_2() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "1";
			String idActual = CREDENTIAL.getUser_id();
			assertEquals(idExpected, idActual);
		}

	}

	public static class testAllLogin_ID_FromEmail {
		private DBData CREDENTIAL;
		private String getIDFromEmail = "admin1@admin1";

		@Test
		public void testLoginNormal_1_Fail() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertNull(idActual);
		}

		@Test
		public void testLoginNormal_2_Fail() {
			String email = "normal2@normal2";
			String password = "normal2";

			CREDENTIAL = Authentication.Login(email, password);

			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertNull(idActual);
		}

		@Test
		public void testLoginNormal_3_Fail() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertNull(idActual);
		}

		@Test
		public void testLoginVendor_1_Fail() {
			String email = "vendor1@vendor1";
			String password = "vendor1";

			CREDENTIAL = Authentication.Login(email, password);

			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertNull(idActual);
		}

		@Test
		public void testLoginVendor_2_Fail() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertNull(idActual);
		}

		@Test
		public void testLoginAdmin_1_Pass() {
			String email = "admin1@admin1";
			String password = "admin1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin1@admin1";
			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_2_Pass() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin1@admin1";
			String idActual = CREDENTIAL.getUser_id(getIDFromEmail);
			assertEquals(idExpected, idActual);
		}

	}

	public static class testAllLogin_Access {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_2() {
			String email = "normal2@normal2";
			String password = "normal2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_3() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_1() {
			String email = "vendor1@vendor1";
			String password = "vendor1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "vendor";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_2() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "vendor";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_1() {
			String email = "admin1@admin1";
			String password = "admin1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_2() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin";
			String idActual = CREDENTIAL.getUser_access();
			assertEquals(idExpected, idActual);
		}

	}

	public static class testAllLogin_Name {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal1";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_2() {
			String email = "normal2@normal2";
			String password = "normal2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal2";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginNormal_3() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "normal3";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_1() {
			String email = "vendor1@vendor1";
			String password = "vendor1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "vendor1";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginVendor_2() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "vendor2";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_1() {
			String email = "admin1@admin1";
			String password = "admin1";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin1";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

		@Test
		public void testLoginAdmin_2() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			String idExpected = "admin2";
			String idActual = CREDENTIAL.getUser_name();
			assertEquals(idExpected, idActual);
		}

	}

	public static class testAllLogin_Fail_Password {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1() {
			String email = "normal1@normal1";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_2() {
			String email = "normal2@normal2";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_3() {
			String email = "normal3@normal3";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_1() {
			String email = "vendor1@vendor1";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_2() {
			String email = "vendor2@vendor2";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_1() {
			String email = "admin1@admin1";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_2() {
			String email = "admin2@admin2";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}
	}

	public static class testAllLogin_Fail_Pass_Half {
		private DBData CREDENTIAL;

		@Test
		public void testLoginNormal_1_Pass() {
			String email = "normal1@normal1";
			String password = "normal1";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_2_Fail() {
			String email = "normal2@normal2";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginNormal_3_Pass() {
			String email = "normal3@normal3";
			String password = "normal3";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_1_Fail() {
			String email = "vendor1@vendor1";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginVendor_2_Pass() {
			String email = "vendor2@vendor2";
			String password = "vendor2";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_1_Fail() {
			String email = "admin1@admin1";
			String password = "failingPassword";

			CREDENTIAL = Authentication.Login(email, password);

			assertNull(CREDENTIAL);
		}

		@Test
		public void testLoginAdmin_2_Pass() {
			String email = "admin2@admin2";
			String password = "admin2";

			CREDENTIAL = Authentication.Login(email, password);

			assertNotNull(CREDENTIAL);
		}

	}

}
