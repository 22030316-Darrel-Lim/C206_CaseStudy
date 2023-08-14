
public class CaseStudy_Check {

	//
	// Main Menu
	// ----------------------
	// SUCCESSFUL
	// - Login and Register
	// -----------------------

	//
	// Normal Menu
	// ----------------------
	// SUCCESSFUL
	// - View Menu
	// -----------------------
	// Error
	// - Add a new order : After choosing school_ID - UserId and UserName is printed (FIX)
	// + String.Format Error 
	// - Unable to view order (FIX)
	// ------------------------

	//
	// Vendor Menu
	// ----------------------
	// SUCCESSFUL
	// - View Menu
	// -----------------------
	// ERROR
	// - Create a new menu > return to main screen. - No prompt to add item till	(FIX)
	// another input (Only happens to vendor 1)										(FIX)
	// - New menu is not updated in the View Menu - Updated when it is rebooted		(FIX)
	// - Exit is not working after selecting an option								(FIX)
	// - Add new food: "Price and qty must be more than 0" - has error. inputed 2 & (FIX)
	// 100 and got rejected. Inputed -2 & 100 than works							(FIX)
	// after exiting, DBTest Fails - Resolved by importing a new SQL				(FIX)
	// - Delete food - typo in the Helper.Read "Enter item_id to add into menu"		(FIX)
	// ------------------------

	//
	// ADMIN Menu
	// ----------------------
	// SUCCESSFUL
	// 1 View All Users
	// 2 View Schools
	// 3 View All Menus
	// 9 Delete Schools
	// 10 Add Payment
	// -----------------------
	// ERROR
	// - View All order - Array Out Of Bounds index 1								(FIX)
	// - Creating useraccount, delete it than view all user account will make this.useraccess null
	// - 8 Add school: wrong statement: "Added Item to Menu Successful"				(FIX)
	// - 11 Payment is not deleted - Create, delete and view						(FIX)
	// - Exiting is a long process													(FIX)
	// 
	// ------------------------
}
