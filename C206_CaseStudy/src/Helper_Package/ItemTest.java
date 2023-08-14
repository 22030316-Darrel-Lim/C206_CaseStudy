package Helper_Package;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import Helper.DBUtil;
import Helper.TableFormatter;

public class ItemTest {

	 @Test
	    public void testSuccessAddItem() {
	       
	        String[] item = {
	            "Bread",    
	            "10",       
	            "Baked yeast",   
	            "NIL",      
	            "yeast, flour, salt",
	            "2.6"       
	        };
	        String menu_id = "2";

	      
	        DBData dbData = new DBData("vendor2@vendor2", "vendor2");

	        
	        boolean isAdded = dbData.addItemToMenu(item, menu_id);

	        assertTrue("TestSuccessAddItem succeed", isAdded);
	    }
	    
	    
	    @Test
	    public void testFailAddItem() {
	            String[] item = {
	                "Failure",   
	                "0",      
	                "",   
	                "NIL",      
	                "yeast, flour, salt",   
	                "2.6" 
	            };
	            String menu_id = "2";

	        DBData dbData = new DBData("vendor2@vendor2", "vendor2");
	        
	        boolean isAdded = dbData.addItemToMenu(item, menu_id);

	        assertFalse("TestFailAddItem should fail", isAdded);
	    }
	    
	    
	    @Test
	    public void testDeleteItem() {
	        String itemName = "Bread";
	        
	       
	        DBData dbData = new DBData("vendor2@vendor2", "vendor2");
	        
	        String query = "SELECT item_id FROM item WHERE item_name = '%s'";
	        query = String.format(query, itemName);
	        
	        int item_Id = 0;
	        ResultSet resultSet = DBUtil.getTable(query);
	        
	        try {
	            if (resultSet.next()) {
	                item_Id = resultSet.getInt("item_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	            DBUtil.close();
	        
	        

	        boolean isDeleted = dbData.deleteItem(String.valueOf(item_Id));

	        assertTrue("TestDeleteItem succeed", isDeleted);
	    }
	    
	    
	    @Test
	    public void testDeleteItemNoneExisting() {
	        String itemName = "penpineappleapplepen";
	        
	       
	        DBData dbData = new DBData("vendor2@vendor2", "vendor2");
	        
	        String query = "SELECT item_id FROM item WHERE item_name = '%s'";
	        query = String.format(query, itemName);
	        
	        int item_Id = 0;
	        ResultSet resultSet = DBUtil.getTable(query);
	        
	        try {
	            if (resultSet.next()) {
	                item_Id = resultSet.getInt("item_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	        
	            DBUtil.close();
	        

	        boolean isDeleted = dbData.deleteItem(String.valueOf(item_Id));

	        assertFalse("TestDeleteItem succeed", isDeleted);
	    }

}
