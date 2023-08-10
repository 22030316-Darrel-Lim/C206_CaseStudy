package Helper_Package;

import java.sql.ResultSet;
import java.sql.SQLException;

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

	    // Initialize the access and id after creating the DBData object
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
	public static boolean CheckEmailDB(String email) {
        boolean exists = false;

        // Check if empty
        if (email == null || email.isEmpty()) {
            return exists;
        }

        try {
            DBUtil.init(DBData.JDBCURL, DBData.DBUSERNAME, DBData.DBPASSWORD);

            String SelectSQL = "SELECT user_email FROM user WHERE user_email = SHA1('%s');";
            SelectSQL = String.format(SelectSQL, email);

            ResultSet rs = DBUtil.getTable(SelectSQL);

            // Check if the email exists in the SQL database
            if (rs.next()) {
                exists = true;
            }

            DBUtil.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
	public static String getUserAccessType(String email, String password) {
        DBData credentials = Login(email, password);

        if (credentials != null) {
            return credentials.getUser_access();
        } else {
            return null;
        }
    }
	
} // End of Class
