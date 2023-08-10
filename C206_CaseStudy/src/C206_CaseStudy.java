import Helper_Package.Authentication;
import Helper_Package.DBData;

public class C206_CaseStudy {

	public static void main(String[] args) {
		DBData CREDENTIAL = Authentication.Login("normal1@normal1", "normal1");
		String access = CREDENTIAL.getUser_access();
		String id = CREDENTIAL.getUser_id();
		System.out.println(access);
		System.out.println(id);
		

	}

	private static String Login() {
		String credential = "";
		return credential;
		
	}
}
