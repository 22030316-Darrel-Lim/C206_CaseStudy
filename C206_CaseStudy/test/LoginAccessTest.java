import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import Helper_Package.Authentication;
import Helper_Package.DBData;

public class LoginAccessTest {
	
	private static DBData CREDENTIAL;

	// TODO DONT USE EXSITING EMAIL - CREATE YOUR OWN ACCOUNT THEN TEST IT
    @Test
    public void testLoginNormalUser() {
        String email = "normal1@normal1";
        String password = "normal1";
        String expectedAccessType = "normal";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginVendor() {
        String email = "vendor1@vendor1";
        String password = "vendor1";
        String expectedAccessType = "vendor";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginAdmin() {
        String email = "admin2@admin2";
        String password = "admin2";
        String expectedAccessType = "admin";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        String actualAccessType = CREDENTIAL.getUser_access();
        
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testFailedLogin() {
        String email = "invalid@example.com";
        String password = "invalid";
        
        CREDENTIAL = Authentication.Login(email, password);
        
        assertNull(CREDENTIAL);
    }
} 
