package Helper_Package;

public class Authentication {
	
	private static DBData checkCREDENTIAL(DBData CREDENTIAL) {
		// Initialize the access and id after creating the DBData object
		String CREDENTIAL_access = CREDENTIAL.getUser_access();
		String CREDENTIAL_id = CREDENTIAL.getUser_id();
		
		// Return null if any of the 5 has error
		if (CREDENTIAL == null || CREDENTIAL_id == null || CREDENTIAL_access == null || CREDENTIAL_id.isEmpty() || CREDENTIAL_access.isEmpty()) {
			CREDENTIAL = null;
		}

		return CREDENTIAL;
	}
	
	public static DBData Login(String email, String password) {
		DBData CREDENTIAL = new DBData(email, password);
		
		CREDENTIAL = checkCREDENTIAL(CREDENTIAL);
		
		return CREDENTIAL;
	}

	private static DBData RegisterAccount(String name, String email, String password, String access, String[] otherInfo) {
		DBData CREDENTIAL = new DBData(name, email, password, access, otherInfo);
		
		CREDENTIAL = checkCREDENTIAL(CREDENTIAL);

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
	
	public static Boolean CreateUser(String name, String email, String password, String access, String[] otherInfo) {
		Boolean isCreated = null;
		DBData newUser = null;
		
		switch (access) {
		case "normal":
			newUser = RegisterAccountNormal(name, email, password, otherInfo);
			break;
		case "vendor":
			newUser = RegisterAccountVendor(name, email, password, otherInfo);
			break;
		case "admin":
			newUser = RegisterAccountAdmin(name, email, password, otherInfo);
			break;
		}
		
		if (newUser != null) {
			isCreated = true;
		} else {
			isCreated = false;
		}
		return isCreated;
	}
} // End of Class
