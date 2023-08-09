package Helper_Package;

public class Authentication {

	public static DBData Login(String email, String password) {
		DBData CREDENTIAL = new DBData(email, password);
		
		String access = CREDENTIAL.getUser_access();
		String id = CREDENTIAL.getUser_id();
		
		// Return null if any of the 3 has error
		if (id.isEmpty() || access.isEmpty() || CREDENTIAL == null) {
			CREDENTIAL = null;
		}
		
		return CREDENTIAL;
	}
	
	public static DBData RegisterAccount() {
		return null;
	}
} // End of Class
