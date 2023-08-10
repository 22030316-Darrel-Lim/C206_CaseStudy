import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import Helper_Package.Authentication;

public class LoginAccessTest {

    @Test
    public void testLoginNormalUser() {
        String email = "normal1@normal1";
        String password = "normal1";
        String expectedAccessType = "normal";
        
        String actualAccessType = Authentication.getUserAccessType(email, password);
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginVendor() {
        String email = "vendor1@vendor1";
        String password = "vendor1";
        String expectedAccessType = "vendor";
        
        String actualAccessType = Authentication.getUserAccessType(email, password);
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testLoginAdmin() {
        String email = "admin2@admin2";
        String password = "admin2";
        String expectedAccessType = "admin";
        
        String actualAccessType = Authentication.getUserAccessType(email, password);
        assertEquals(expectedAccessType, actualAccessType);
    }

    @Test
    public void testFailedLogin() {
        String email = "invalid@example.com";
        String password = "invalid";
        
        String actualAccessType = Authentication.getUserAccessType(email, password);
        assertNull(actualAccessType);
    }
} 
