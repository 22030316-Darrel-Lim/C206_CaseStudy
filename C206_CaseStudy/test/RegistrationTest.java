import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import Helper_Package.Authentication;
import Helper_Package.DBData;

public class RegistrationTest {

	@Test
	public void testSuccessfulRegistration() {
	    String name = "Dayweed2";
	    String email = "Dayweed2@g.com";
	    String password = "Dayweed123";
	    String phoneNo = "12345678";
	    String allergies = "None";
	    String address = "123 Main St";

	    String[] otherInfo = {phoneNo, allergies, address};

	    // Simulate successful registration
	    DBData credentials = Authentication.RegisterAccountNormal(name, email, password, otherInfo);
	    assertNotNull(credentials); // The registration should succeed
	}



    @Test
    public void testFailedRegistration() {
        // Attempt to register with existing email
        String name = "Jane Smith";
        String email = "normal1@normal1";
        String password = "password123";
        String phoneNo = "987654321";
        String allergies = "None";
        String address = "456 Elm St";

        String[] otherInfo = {phoneNo, allergies, address};

        DBData credentials = Authentication.RegisterAccountNormal(name, email, password, otherInfo);
        assertNull(credentials);
    }
 
}
