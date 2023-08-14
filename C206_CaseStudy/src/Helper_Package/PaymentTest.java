package Helper_Package;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Helper.DBUtil;

public class PaymentTest {

    @Test
    public void PaymentAddTest() {

        String paymentName = "Success";

        DBData dbData = new DBData("admin2@admin2", "admin2");

        boolean isAdded = dbData.addPayment(paymentName);

        assertTrue("PaymentAddTest succeed", isAdded);
    }
    @Test
    public void PaymentAddTestNull() {

        String paymentName = null;

        DBData dbData = new DBData("admin2@admin2", "admin2");

        boolean isAdded = dbData.addPayment(paymentName);

        assertTrue("PaymentAddTestNull should pass", isAdded);
    }
    @Test
    public void PaymentDeleteTest() {
        String paymentNameToDelete = "Success";

        DBData dbData = new DBData("admin2@admin2", "admin2");

        boolean isAdded = dbData.deletePayment(paymentNameToDelete);

        assertTrue("PaymentDeleteTest deleted", isAdded);
    }
    @Test
    public void PaymentDeleteTestNoneExisting() {
        String paymentNameToDelete = "potatopotatatata";

        DBData dbData = new DBData("admin2@admin2", "admin2");

        boolean isAdded = dbData.deletePayment(paymentNameToDelete);

        assertFalse("PaymentDeleteTest deleted", isAdded);
    }
    

    
    

}
