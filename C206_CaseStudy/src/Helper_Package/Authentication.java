package Helper_Package;

public class Authentication {

	public static DBData Login(String email, String password) {
		DBData CREDENTIAL = new DBData(email, password);
		
		String CREDENTIAL_access = CREDENTIAL.getUser_access();
		String CREDENTIAL_id = CREDENTIAL.getUser_id();
		
		// Return null if any of the 5 has error
		if (CREDENTIAL == null || CREDENTIAL_id == null || CREDENTIAL_access == null || CREDENTIAL_id.isEmpty() || CREDENTIAL_access.isEmpty()) {
			CREDENTIAL = null;
		}
		
		return CREDENTIAL;
	}

	private static DBData RegisterAccount(String name, String email, String password, String access, String[] otherInfo) {
		DBData CREDENTIAL = new DBData(name, email, password, access, otherInfo);
		
		String CREDENTIAL_access = CREDENTIAL.getUser_access();
		String CREDENTIAL_id = CREDENTIAL.getUser_id();
		
		// Return null if any of the 5 has error
		if (CREDENTIAL == null || CREDENTIAL_id == null || CREDENTIAL_access == null || CREDENTIAL_id.isEmpty() || CREDENTIAL_access.isEmpty()) {
			CREDENTIAL = null;
		}
		
		return CREDENTIAL;
	}
	
	public static DBData RegisterAccountNormal(String name, String email, String password, String[] otherInfo) {
		String access = "normal";
		DBData CREDENTIAL = RegisterAccount(name, email, password, access, otherInfo);
		return CREDENTIAL;
	}
	
	public static DBData RegisterAccountVendor(String name, String email, String password, String[] otherInfo) {
		String access = "vendor";
		DBData CREDENTIAL = RegisterAccount(name, email, password, access, otherInfo);
		return CREDENTIAL;
	}
	
	public static DBData RegisterAccountAdmin(String name, String email, String password, String[] otherInfo) {
		String access = "admin";
		DBData CREDENTIAL = RegisterAccount(name, email, password, access, otherInfo);
		return CREDENTIAL;
	}
} // End of Class
