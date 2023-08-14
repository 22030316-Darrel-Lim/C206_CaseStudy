package Application_MAIN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class InputValidation_Test {

	@Test
	public void nameTest_Normal() {
		String name = "name";
		boolean isCorrect = C206_CaseStudy.isName(name);
		
		assertTrue(isCorrect);
	}
	
	@Test
	public void nameTest_Wrong() {
		String name = "1111";
		boolean isCorrect = C206_CaseStudy.isName(name);
		
		assertFalse(isCorrect);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void nameTest_Null() {
		String name = null;
		try {
			boolean isCorrect = C206_CaseStudy.isName(name);
		} catch (NullPointerException np) {
			assertTrue("Working", true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void emailTest_Normal() {
		String email = "Email@email.com";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertTrue(isCorrect);
	}
	
	@Test
	public void emailTest_Normal1() {
		String email = "Email123@email.com";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertTrue(isCorrect);
	}
	
	@Test
	public void emailTest_Normal_Boundary() {
		String email = "e@e.c";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertTrue(isCorrect);
	}
	
	@Test
	public void emailTest_Wrong1() {
		String email = "@.";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertFalse(isCorrect);
	}
	
	@Test
	public void emailTest_Wrong2() {
		String email = "email@1.com";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertFalse(isCorrect);
	}
	
	@Test
	public void emailTest_Wrong3() {
		String email = "email@email.1";
		
		boolean isCorrect = C206_CaseStudy.isEmail(email);
		
		assertFalse(isCorrect);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void emailTest_Null() {
		String email = null;
		
		try {
			boolean isCorrect = C206_CaseStudy.isEmail(email);
		} catch (NullPointerException np) {
			assertTrue("Working", true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void passwordTest_Length_8() {
		String password = "F2345678";
		
		boolean isCorrect = C206_CaseStudy.isPassword(password);
		
		assertTrue(isCorrect);
	}
	
	@Test
	public void passwordTest_Length_7() {
		String password = "F234567";
		
		boolean isCorrect = C206_CaseStudy.isPassword(password);
		
		assertFalse(isCorrect);
	}
	
	@Test
	public void passwordTest_Length_9() {
		String password = "F23456789";
		
		boolean isCorrect = C206_CaseStudy.isPassword(password);
		
		assertTrue(isCorrect);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void passwordTest_Null() {
		String password = null;
		
		try {
			boolean isCorrect = C206_CaseStudy.isPassword(password);
		} catch (NullPointerException np) {
			assertTrue("Working", true);
		} catch (Exception e) {
			fail();
		}
	}
}

