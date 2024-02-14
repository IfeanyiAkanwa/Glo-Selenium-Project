package CACTestCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

import static javax.swing.text.html.CSS.getAttribute;
import static util.TestUtils.yyyymmddToDate;

public class UserAccountCreation extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	private String PhoneNumber = TestUtils.generatePhoneNumber();

    private String role;
    private String userPhone;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String deletedUser;
    private String Dealer;
    private String Zone;
    private String authenticationMode;
    private String email;
    private String adUser;
    private String existingStaffId;
    private String invalidStaffId;
    private String de_activateUser;
    private String approvalComment;
    private String disapprovalComment;
    private String userHistory;
    private String performedBy;
    private String approvedBy;
    private String dealerApprovedBy;
    private String bulkUser;
    
    
    
    //added by ifeanyi test data
    private String deactivatedUser;
    private String user_firstname;
    private String user_lastname;
    private String user_phone;
    private String newcreateduserNo;
    private String userEmail2;
    
    @Parameters({ "testEnv" })
    @BeforeMethod
    public void parseJson(String testEnv) throws IOException, ParseException {
        File path = null;
        File classpathRoot = new File(System.getProperty("user.dir"));
        if(testEnv.equalsIgnoreCase("StagingData")) {
            path = new File(classpathRoot, "stagingData/data.conf.json");
        }else {
            path = new File(classpathRoot, "prodData/data.conf.json");
        }
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader(path));

        JSONObject envs = (JSONObject) config.get("usermanagement");
        JSONObject envs2 = (JSONObject) config.get("valid_Admin_Login");

        email = (String) envs2.get("email");
        adUser = (String) envs.get("adUser");
        existingStaffId = (String) envs.get("existingStaffId");
        invalidStaffId = (String) envs.get("invalidStaffId");

        role = (String) envs.get("role");
        userPhone = (String) envs.get("userPhone");
        userEmail = (String) envs.get("userEmail");
        userEmail2 = (String) envs.get("userEmail2");
        userFirstName = (String) envs.get("userFirstName");
        userLastName = (String) envs.get("userLastName");
        deletedUser = (String) envs.get("deletedUser");
        Dealer = (String) envs.get("Dealer");
        Zone = (String) envs.get("Zone");
        authenticationMode = (String) envs.get("authenticationMode");
        de_activateUser = (String) envs.get("de_activateUser");
        approvalComment = (String) envs.get("approvalComment");
        disapprovalComment = (String) envs.get("disapprovalComment");
        userHistory = (String) envs.get("userHistory");
        performedBy = (String) envs.get("performedBy");
        approvedBy = (String) envs.get("approvedBy");
		bulkUser = (String) envs.get("bulkUser");
        JSONObject envs3 = (JSONObject) config.get("dealermanagement");

        dealerApprovedBy = (String) envs3.get("approvedBy");
        
        //added by ifeanyi test data
        deactivatedUser = (String) envs.get("deactivatedUser");
        user_firstname  = (String) envs.get("user_firstname");
        user_lastname   = (String) envs.get("user_lastname");
        user_phone      = (String) envs.get("user_phone");
        newcreateduserNo =(String) envs.get("newcreateduserNo");
        
    }


    public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "users");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "advancedBtn");
	}
	
	public void scrollToSaveBtn() throws InterruptedException {
		TestUtils.scrollToElement("ID", "createUserBtn");
	}
	
//	public void scrolltotableData() throws InterruptedException{
//		TestUtils.scrollToElement("XPATH", "//td");
//	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToUserAccountCreationTest(String testEnv) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to User Account Creation");
		Thread.sleep(1000);
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			try {
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("1014Account Creation")).click();
			} catch (Exception e) {
				getDriver().findElement(By.cssSelector("a[name=\"8954091967User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("17321399899Account Creation")).click();
				Thread.sleep(500);
			}
		} else {
			try {
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("1014Account Creation")).click();
			} catch (Exception e) {
				getDriver().findElement(By.cssSelector("a[name=\"8954091967User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("17321399899Account Creation")).click();
				Thread.sleep(500);
			}
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div[4]/div/div/button", "USER");
	}

	@Test(groups = { "Regression" })
	public void assertUserAccountCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Assert Card Count");
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total_users"))); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Total Users')]")));
		String totalAgentValString = getDriver().findElement(By.xpath("//div[contains(text(),'Total number of users')]")).getText();
		
		//String activeUsersValString = getDriver().findElement(By.id("active_users")).getText();
		String activeUsersValString = getDriver().findElement(By.xpath("//div[contains(text(),'Total number of users')]")).getText();
		//String inactiveUsersValString = getDriver().findElement(By.id("inactive_users")).getText();
		String inactiveUsersValString = getDriver().findElement(By.xpath("//div[contains(text(),'Total number of users')]")).getText();
		int actualTotalAgentlVal = TestUtils.convertToInt(totalAgentValString);
		int actualActiveUsersVal = TestUtils.convertToInt(activeUsersValString);
		int actualInactiveUsersVal = TestUtils.convertToInt(inactiveUsersValString);

		int expectedTotalAgentVal = actualActiveUsersVal + actualInactiveUsersVal;

		try {
			Assert.assertEquals(expectedTotalAgentVal, actualTotalAgentlVal);
			testInfo.get().log(Status.INFO,
					"Total agents (" + expectedTotalAgentVal + ") is equal to number of Active Users ("
							+ actualActiveUsersVal + ") + Inactive users (" + actualInactiveUsersVal + ").");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		// Change page size
		new Select(getDriver().findElement(By.name("users_length"))).selectByVisibleText("250");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 250");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='users']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of user returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}

		/*
		 * // Select visible columns String visibleOutput ="Select visible columns";
		 * Markup c = MarkupHelper.createLabel(visibleOutput, ExtentColor.BLUE);
		 * testInfo.get().info(c); String columnHeaders =
		 * getDriver().findElement(By.xpath("//table[@id='users']/thead/tr")).getText();
		 * testInfo.get().info("default visible headers: "+columnHeaders);
		 * 
		 * String unSelectOutput ="unSelect Name, Phone, and Role columns"; Markup u =
		 * MarkupHelper.createLabel(unSelectOutput, ExtentColor.BLUE);
		 * testInfo.get().info(u); getDriver().findElement(By.
		 * xpath("//a[contains(text(),'Select Visible Columns')]")).click();
		 * Thread.sleep(1000); getDriver().findElement(By.xpath("//a[2]/span")).click();
		 * Thread.sleep(500); getDriver().findElement(By.xpath("//a[4]/span")).click();
		 * Thread.sleep(500); getDriver().findElement(By.xpath("//a[5]/span")).click();
		 * Thread.sleep(500); getDriver().findElement(By.
		 * xpath("//a[contains(text(),'Select Visible Columns')]")).click(); int
		 * selectOutputCount
		 * =getDriver().findElements(By.xpath("//table[@id='users']/thead/tr/th")).size(
		 * ); String newColumnHeaders =
		 * getDriver().findElement(By.xpath("//table[@id='users']/thead/tr")).getText();
		 * for(int i = 1; i <= selectOutputCount ; i++) { String selectedOutput1 =
		 * getDriver().findElement(By.xpath(
		 * "//table[@id='datatables_users']/thead/tr/th["+i+"]")).getText(); if
		 * (selectedOutput1.contains("Name") || (selectedOutput1.contains("Phone")) ||
		 * (selectedOutput1.contains("Role"))) {
		 * testInfo.get().error("Name or Phone or Role column is still present."); } }
		 * testInfo.get().info("available visible headers: "+newColumnHeaders);
		 * 
		 * // select all back String SelectOutput ="Select All columns"; Markup s =
		 * MarkupHelper.createLabel(SelectOutput, ExtentColor.BLUE);
		 * testInfo.get().info(s); getDriver().findElement(By.
		 * xpath("//a[contains(text(),'Select Visible Columns')]")).click();
		 * Thread.sleep(1000); getDriver().findElement(By.xpath("//a[2]/span")).click();
		 * Thread.sleep(500); getDriver().findElement(By.
		 * xpath("//a[contains(text(),'Select Visible Columns')]")).click();
		 * testInfo.get().info("Visible headers: "+columnHeaders);
		 */
	}

	@Test(groups = { "Regression" })
	public void downloadReport() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Download Reports");
		// Download pdf
		getDriver().findElement(By.xpath("//div/div/div/div[2]/div/div[2]/a")).click();
		Thread.sleep(500);
		// Download excel
		getDriver().findElement(By.xpath("//div[2]/a[2]")).click();
		Thread.sleep(500);
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByRoleTest(String testEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToUserAccountCreationTest(testEnv);
		scrollUp();
		Thread.sleep(500);
		
		TestUtils.testTitle("Filter by Role: "+role);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.id("phone")).clear();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(role);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		String role1 = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		role1.contains(role);
		testInfo.get().info(role + " found");
		Thread.sleep(500);
	}

	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollUp();

		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		// Active status
		TestUtils.testTitle("Filter by Status: Active");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Active");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-active", "ACTIVE");

		// Inactive status
		TestUtils.testTitle("Filter by Status: Inactive");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Inactive");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-inactive", "INACTIVE");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Blacklist status
		TestUtils.testTitle("Filter by Status: Blacklist");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Blacklist");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "BLACKLIST");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByPhoneTest(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by phone: " + userPhone);
		scrollUp();
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		Thread.sleep(500);
		getDriver().findElement(By.id("phone")).clear();
		getDriver().findElement(By.id("phone")).sendKeys(userPhone);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[4]", userPhone);
			// Reset Password
			String email = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[3]")).getText();
			TestUtils.testTitle("Reset password for user with email address: " + email);
			getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.linkText("Reset password")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
					"A new password has been sent to " + userPhone);
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No matching records found");
		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByEmailNameTest(String testEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		// Email search
		TestUtils.testTitle("Filter by email address: " + userEmail);
		scrollUp();
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("phone")).clear();
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userEmail);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", userEmail);

		// First Name search
		TestUtils.testTitle("Filter by first name: " + user_firstname);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("firstname")).clear();
		getDriver().findElement(By.id("firstname")).sendKeys(user_firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String result = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[2]")).getText();
		result.contains(user_firstname);
		testInfo.get().info(user_firstname + " Found");

		// Last Name search
		TestUtils.testTitle("Filter by Last name: " + user_lastname);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("lastname")).clear();
		getDriver().findElement(By.id("lastname")).sendKeys(user_lastname);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[2]", user_lastname + " " + user_firstname);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByDeletedUserEmail(String testEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by deleted user email address: " + deletedUser);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("firstname")).clear();
		getDriver().findElement(By.id("lastname")).clear();
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(deletedUser);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", deletedUser);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void viewDetailsUpdateDetailsTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		scrollUp();

		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		validationMessages.add("Please match the requested format.");
		validationMessages.add("Please match the format requested.");
		
		String newFirstName = "SeamfixTest";	
		String newLastName = "Auto";
		
		TestUtils.testTitle("Filter and edit user with email: " + userEmail2);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userEmail2);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("vd")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "User Details");
		TestUtils.testTitle("User Details before editing");
		Assertion.userDetailsFormAssertion();

		// Click to edit User Information
		TestUtils.testTitle("Edit User Details to Admin User and check required fields for " + user_phone);
		getDriver().findElement(By.cssSelector("i.material-icons.float-right")).click();
		Thread.sleep(500);

		// First name
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement firstName = getDriver().findElement(By.name("firstName"));
		TestUtils.assertValidationMessage(validationMessages, firstName, "First Name");
		getDriver().findElement(By.id("firstName")).sendKeys(newFirstName);

		// Last name
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement lastName = getDriver().findElement(By.name("lastName"));
		TestUtils.assertValidationMessage(validationMessages, lastName, "Last Name");
		getDriver().findElement(By.id("lastName")).sendKeys(newLastName);

		// Other name
		getDriver().findElement(By.id("otherName")).clear();
		getDriver().findElement(By.id("otherName")).sendKeys("ABDULHAMID");

		// Telephone
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement phoneField = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneField, "phone number");

		// Submit less than 11 digit phone number
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys("08015");
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement phoneLength = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneLength, "Phone number length");
		/*getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(userPhone2); */// already existing user's phone
		String newPhone = TestUtils.generatePhoneNumber();
		TestUtils.testTitle("Supply a new phone number: " + newPhone);
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(newPhone);
		
		if (getDriver().findElement(By.cssSelector("span.select2-selection__clear")).isDisplayed()) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
			getDriver().findElement(By.xpath("//span/span/span")).click();
		}

		// Gender
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("MALE");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// User type
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement userType = getDriver().findElement(By.name("userType"));
		String validationMessage7 = userType.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage7, "Please select an item in the list.");
		testInfo.get().info("<b>Empty User Type field: </b>" + validationMessage7);
		getDriver().findElement(By.xpath("//div[8]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Admin User");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Role
		getDriver().findElement(By.xpath("//form[@id='viewUserForm']/f/div[9]/div/span/span/span/ul/li/span")).click();
		TestUtils.scrollToElement("ID", "updateUserBtn");
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		WebElement role = getDriver().findElement(By.name("role"));
		String validationMessage8 = role.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage8, "Please select an item in the list.");
		testInfo.get().info("<b> Empty Role Type field: </b>" + validationMessage8);
		getDriver().findElement(By.xpath("//div[9]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//li/input")).sendKeys("GLO ADMIN");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		/*
		 * // Submit for already existing phone
		 * getDriver().findElement(By.id("updateUserBtn")).click(); Thread.sleep(500);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
		 * "div.swal2-content"))); TestUtils.assertSearchText("CSSSELECTOR",
		 * "div.swal2-content", "A user with this phone number already exists.");
		 * getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).
		 * click(); Thread.sleep(500);
		 */

		// Select all
		getDriver().findElement(By.cssSelector("label.custom-control-label.text-dark")).click();
		Thread.sleep(1000);

		// Authentication Mode
		getDriver().findElement(By.xpath("//div[14]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(authenticationMode);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Staff ID
		String id= TestUtils.generateSixSerialNumber();
		TestUtils.setText(By.id("staffId"), id);
		Thread.sleep(1000);
		getDriver().findElement(By.name("adUsername")).click();

		// User Id
		String userId = getDriver().findElement(By.name("userIdtag")).getAttribute("value");
		Thread.sleep(500);
		String initials = userId.substring(0,2);
		String firstInitial = newFirstName.substring(0,1);
		String secondInitial = newLastName.substring(0,1);
		try {
			Assert.assertEquals(firstInitial+secondInitial,initials);
			testInfo.get().info("Generated userID: "+userId+" have user's first name: <b>"+newFirstName+"</b> and last name: <b> "+ newLastName+"</b> initials");
		} catch (Error e) {
			testInfo.get().error("Generated userID: "+userId+" does not have user's first name: <b>"+newFirstName+"</b> and last name: <b> "+ newLastName+"</b> initials");
		}
		
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Details successfully edited");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);

		scrollUp();
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userEmail2);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//td[contains(text(),'"+newPhone+"')]", newPhone);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("vd")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.testTitle("User Details after first editing");
		Assertion.userDetailsFormAssertion();

		try {
			TestUtils.assertSearchText("ID", "select2-userType-container", "Admin User");
		} catch (Exception e) {
			testInfo.get().error("user type (Admin User) not present.");
		}

		// Edit and change user type to agent user
		TestUtils.testTitle("Edit and change user type to agent user and user's old number: " + user_phone);
		getDriver().findElement(By.cssSelector("i.material-icons.float-right")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(user_phone);
		if (getDriver().findElement(By.cssSelector("span.select2-selection__clear")).isDisplayed()) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
			getDriver().findElement(By.xpath("//span/span/span")).click();
		}
		
		// Gender
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("MALE");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// User type
		getDriver().findElement(By.xpath("//div[8]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Agent User");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);

		// Role
		getDriver().findElement(By.xpath("//form[@id='viewUserForm']/f/div[9]/div/span/span/span/ul/li/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/ul")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//li/input")).sendKeys("AGENT");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		// Select zone
		
		  getDriver().findElement(By.id("updateUserBtn")).click(); Thread.sleep(500);
		  WebElement zone = getDriver().findElement(By.name("zone")); 
		  String validationMessage9 = zone.getAttribute("validationMessage");
		  Assert.assertEquals(validationMessage9,
		  "Please select an item in the list.");
		  testInfo.get().info("Empty Zone field: " + validationMessage9);
		 

		getDriver().findElement(By.xpath("//div[10]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(Zone);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);

		// Select Dealer
		
		  getDriver().findElement(By.id("updateUserBtn")).click(); Thread.sleep(500);
		  WebElement dealer = getDriver().findElement(By.name("dealer")); 
		  String validationMessage10 = dealer.getAttribute("validationMessage");
		  Assert.assertEquals(validationMessage10,
		  "Please select an item in the list.");
		  testInfo.get().info("Empty Dealer field: " + validationMessage10);
		 
		getDriver().findElement(By.xpath("//div[11]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(Dealer);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Authentication Mode
		getDriver().findElement(By.xpath("//div[14]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(authenticationMode);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Staff ID
		TestUtils.setText(By.id("staffId"), id);
		Thread.sleep(1000);
		getDriver().findElement(By.name("adUsername")).click();
				
		// User Id
		String userId1 = getDriver().findElement(By.name("userIdtag")).getAttribute("value");
		String initials2 = userId1.substring(0,2);
		try {
			Assert.assertNotEquals(firstInitial+secondInitial,initials2);
			testInfo.get().info("Generated userID: "+userId1+" is not agent's initials <b>"+newFirstName+" "+ newLastName+"</b>");
		} catch (Error e) {
			testInfo.get().error("Generated userID: "+userId1+" is agent's initials <b>"+newFirstName+"  "+ newLastName+"</b>");
		}

		// Click Update button
		TestUtils.scrollToElement("ID", "updateUserBtn");
		getDriver().findElement(By.id("updateUserBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Details successfully edited");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void addNewUserFormValidationTest(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        String PhoneNumber1 = TestUtils.generatePhoneNumber();

		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		validationMessages.add("Please match the requested format.");
		validationMessages.add("Please match the format requested.");
		
		navigateToUserAccountCreationTest(testEnv);
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(500);
		TestUtils.testTitle("Submit form without supplying required fields");
		getDriver().findElement(By.xpath("//a[contains(text(),'User creation')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.w300.font-weight-bold.text-secondary", "Create user");

		// Submit empty first name
		TestUtils.testTitle("Submit form with empty First name");
		getDriver().findElement(By.id("firstName")).clear();
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement firstName = getDriver().findElement(By.name("firstName"));
		TestUtils.assertValidationMessage(validationMessages, firstName, "First Name");
		getDriver().findElement(By.id("firstName")).sendKeys("SeamfixTest" + System.currentTimeMillis());

		// Submit empty last name
		TestUtils.testTitle("Submit form with empty last name");
		getDriver().findElement(By.id("lastName")).clear();
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement lastName = getDriver().findElement(By.name("lastName"));
		TestUtils.assertValidationMessage(validationMessages, lastName, "Last Name");
		getDriver().findElement(By.id("lastName")).sendKeys("Auto");

		// Submit empty gender
		TestUtils.testTitle("Submit form with empty gender field");
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement gender = getDriver().findElement(By.name("gender"));
		String validationMessage2 = gender.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage2, "Please select an item in the list.");
		testInfo.get().info("<b>Empty gender field:</b> " + validationMessage2);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("MALE");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);

		// Submit empty email
		TestUtils.testTitle("Submit form with empty email address");
		getDriver().findElement(By.id("email")).clear();
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement emailField = getDriver().findElement(By.name("email"));
		TestUtils.assertValidationMessage(validationMessages, emailField, "Email Address");

		// Submit wrong email format
		TestUtils.testTitle("Submit form with wrong email format");
		getDriver().findElement(By.id("email")).sendKeys("as@e");
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement emailFormat = getDriver().findElement(By.name("email"));
		TestUtils.assertValidationMessage(validationMessages, emailFormat, "Email Format");

		// Enter already exist user email
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);

		TestUtils.scrollToElement("ID", "mobile");

		// Submit empty phone number
		TestUtils.testTitle("Submit form with empty phone number");
		getDriver().findElement(By.id("mobile")).clear();
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement phoneField = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneField, "Phone Number");
		
		// Submit less than 11 digit phone number
		TestUtils.testTitle("Submit form with phone number less than 11 digits");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys("08012");
		scrollToSaveBtn();
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement phoneLength = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneLength, "Phone Number Length");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(PhoneNumber1);

		// Submit empty user type field
		TestUtils.testTitle("Submit form with empty user type");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement userType = getDriver().findElement(By.name("userType"));
		String validationMessage7 = userType.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage7, "Please select an item in the list.");
		testInfo.get().info("<b>Empty User Type field:</b> " + validationMessage7);
		getDriver().findElement(By.xpath("//div[7]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Admin User");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Submit empty role
		TestUtils.testTitle("Submit form with empty role");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement role = getDriver().findElement(By.name("role"));
		String validationMessage8 = role.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage8, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Role Type field:</b> " + validationMessage8);
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("AGENT");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		
		// Submit empty Authentication Mode
		TestUtils.testTitle("Submit form with empty authentication mode");
		TestUtils.scrollToElement("NAME", "authMode");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement authMode = getDriver().findElement(By.name("authMode"));
		String validationMessage9 = authMode.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage9, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Authentication Mode field:</b> " + validationMessage9);
		getDriver().findElement(By.xpath("//div[11]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Active Directory");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		// Submit empty Staff ID
		TestUtils.testTitle("Submit form with empty Staff ID");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement staffID = getDriver().findElement(By.name("staffId"));
		TestUtils.assertValidationMessage(validationMessages, staffID, "Staff ID");
		getDriver().findElement(By.id("staffId")).sendKeys(TestUtils.generateSixSerialNumber());
		Thread.sleep(1000);
		
		// Submit empty AD Username
		TestUtils.testTitle("Submit form with empty AD Username");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement adUserName = getDriver().findElement(By.name("adUsername"));
		TestUtils.assertValidationMessage(validationMessages, adUserName, "AD Username");
		getDriver().findElement(By.id("adUsername")).clear();
		getDriver().findElement(By.id("adUsername")).sendKeys(TestUtils.uniqueString(6));
		Thread.sleep(500);
				
		/*// Empty dealer field
		TestUtils.testTitle("Submit form with empty dealer field");
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(500);
		WebElement dealers = getDriver().findElement(By.name("dealers"));
		String validationMessage11 = dealers.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage11, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer field: </b>" + validationMessage11);*/

		// Select all dealers
		getDriver().findElement(By.cssSelector("label.custom-control-label.text-dark")).click();
		Thread.sleep(1000);
		
		// User Id
		String userId = getDriver().findElement(By.name("userId")).getAttribute("value");
		Thread.sleep(500);
		TestUtils.testTitle("Assert User ID: " + userId);
		
		// Submit form for existing user's email address
		TestUtils.testTitle("Submit form with already existing email address: " + email);
		getDriver().findElement(By.id("createUserBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User with this email address already exists");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);

		// AD Username
		TestUtils.testTitle("Submit form after supplying existing Active Directory Username: " + adUser);
		getDriver().findElement(By.id("adUsername")).clear();
		getDriver().findElement(By.id("adUsername")).sendKeys(adUser);
		Thread.sleep(500);
		getDriver().findElement(By.id("createUserBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "This AD Username already exists");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("adUsername")).clear();
		getDriver().findElement(By.id("adUsername")).sendKeys(TestUtils.uniqueString(6));
		Thread.sleep(500);
		
		// Submit form with staff ID less than 6 digits
		TestUtils.testTitle("Submit form with staff ID less than 6 digits: " + invalidStaffId);
		getDriver().findElement(By.id("staffId")).clear();
		getDriver().findElement(By.id("staffId")).sendKeys(invalidStaffId);
		Thread.sleep(1000);
		getDriver().findElement(By.id("createUserBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "This Staff ID is not up to the required length (6)");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);

		// Submit form with existing staff ID
		TestUtils.testTitle("Submit form with existing staff ID: " + existingStaffId);
		getDriver().findElement(By.id("staffId")).clear();
		getDriver().findElement(By.id("staffId")).sendKeys(existingStaffId);
		Thread.sleep(1000);
		getDriver().findElement(By.id("createUserBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "This Staff ID already exist");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		
		// Click reset button
		getDriver().findElement(By.id("reset")).click();
		
		// Back button
		getDriver().findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void addNewUserTest(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		String PhoneNumber1 = TestUtils.generatePhoneNumber();
		
		navigateToUserAccountCreationTest(testEnv);
		TestUtils.testTitle("Create New User with Biosmart Auth mode and Agent user type");
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'User creation')]")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.w300", "Create user");

		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("Test" + System.currentTimeMillis());
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("Auto");
		getDriver().findElement(By.id("otherName")).clear();
		getDriver().findElement(By.id("otherName")).sendKeys("Web" + System.currentTimeMillis());

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("MALE");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("sfxtest" + PhoneNumber1 + "@yopmail.com");

		TestUtils.scrollToElement("ID", "mobile");
		getDriver().findElement(By.id("mobile")).clear();
		TestUtils.testTitle("Newly created Phone number: " + PhoneNumber);
		getDriver().findElement(By.id("mobile")).sendKeys(PhoneNumber);

		// User Type
		getDriver().findElement(By.xpath("//div[7]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Agent User");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Role
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("AGENT");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		// Dealer
		getDriver().findElement(By.xpath("//div[10]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(Dealer);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.loading-results"),"Searching…"));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Zone
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(Zone);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Authentication Mode
		getDriver().findElement(By.xpath("//div[11]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(authenticationMode);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// User Id
		String userId = getDriver().findElement(By.id("userId")).getAttribute("value");
		Thread.sleep(500);
		TestUtils.testTitle("Assert User ID: " + userId);
				
		// Staff ID
		getDriver().findElement(By.id("staffId")).sendKeys(TestUtils.generateSixSerialNumber());
		Thread.sleep(500);
		
		// Click save button
		getDriver().findElement(By.id("createUserBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User account has been created successfully.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a/div")).click();
		Thread.sleep(1000);

	}
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void addNewUserWithStaffIdTest(String testEnv) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String PhoneNumber1 = TestUtils.generatePhoneNumber();
		
		navigateToUserAccountCreationTest(testEnv);
		TestUtils.testTitle("Create New User with Active Directory Auth mode and Admin user type");
		testInfo.get().info("<b>Staff ID is compulsory for Admin User Type </b>");
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'User creation')]")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.w300", "Create user");

		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("Test" + System.currentTimeMillis());
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("Auto");
		getDriver().findElement(By.id("otherName")).clear();
		getDriver().findElement(By.id("otherName")).sendKeys("Web" + System.currentTimeMillis());

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("MALE");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("sfxtest" + PhoneNumber1 + "@yopmail.com");

		TestUtils.scrollToElement("ID", "mobile");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(PhoneNumber);

		// User Type
		getDriver().findElement(By.xpath("//div[7]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Admin User");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Role
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("AGENT");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		// Authentication Mode
		getDriver().findElement(By.xpath("//div[11]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Active Directory");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Staff ID
		getDriver().findElement(By.id("staffId")).sendKeys(TestUtils.generateSixSerialNumber());
		Thread.sleep(500);
		
		// User Id
		String userId = getDriver().findElement(By.id("userId")).getAttribute("value");
		Thread.sleep(500);
		TestUtils.testTitle("Assert User ID: " + userId);
		
		// AD Username
		getDriver().findElement(By.id("adUsername")).clear();
		getDriver().findElement(By.id("adUsername")).sendKeys(TestUtils.uniqueString(6));
		Thread.sleep(500);
		
		// Select all dealers
		getDriver().findElement(By.cssSelector("label.custom-control-label.text-dark")).click();
		Thread.sleep(1000);
		
		// Click reset button
		getDriver().findElement(By.id("reset")).click();

		// Back button
		getDriver().findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchNewCreatedUserByPhoneTest(String testEnv) throws InterruptedException, java.text.ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToUserAccountCreationTest(testEnv);
		scrollUp();
		Thread.sleep(500);
		TestUtils.testTitle("Search Newly created user with Phone number: " + newcreateduserNo);
		getDriver().findElement(By.id("advancedBtn")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("phone")).clear();
		getDriver().findElement(By.id("phone")).sendKeys(newcreateduserNo);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[4]", newcreateduserNo);


        //Verify that the Activation reason for newly created users  should be ï¿½Newly Created Userï¿½
        TestUtils.testTitle("Verify that the Activation reason for newly created users  should be ï¿½Newly Created Userï¿½ Test");
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.linkText("Activation History")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/h4")));
        TestUtils.assertSearchText("XPATH", "//div[2]/div/h4", "Activation/Deactivation History");
        String toolTip = getDriver().findElement(By.xpath("//td[3]/button")).getAttribute("data-original-title");
        if(toolTip.equalsIgnoreCase("Newly Created User")){
            testInfo.get().info(toolTip +" found");
        }else {
            testInfo.get().error(toolTip);
        }

        //Verify that When a user is created, the Activation Date should be the date the user was created
        TestUtils.testTitle("Verify that When a user is created, the Activation Date should be the date the user was created Test");
        String dateCreated = getDriver().findElement(By.xpath("//td[2]")).getText();
        testInfo.get().info("Activation Date: "+dateCreated);
        testInfo.get().info("Date Created: "+new Date().toLocaleString());
        String curDate = TestUtils.convertDate(new Date().toLocaleString());
        if(dateCreated.split(" ")[0].equalsIgnoreCase(curDate)){
            testInfo.get().info("Activation Date equals the date the user was created");
        }else {
            testInfo.get().error("Activation Date should be the date the user was created");
        }
    }

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void activateUser(String testEnv)
			throws InterruptedException {
		// Active User Test
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToUserAccountCreationTest(testEnv);
		scrollUp();

		// Email search
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.id("firstname")).clear();
		getDriver().findElement(By.id("lastname")).clear();
		getDriver().findElement(By.id("email")).clear();
		
		//added by ifeanyi
		getDriver().findElement(By.id("email")).sendKeys(deactivatedUser);
		
		//getDriver().findElement(By.id("email")).sendKeys(de_activateUser);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String activeEmail = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[3]")).getText();
		String userStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[6]")).getText();
		Thread.sleep(500);
		TestUtils.testTitle("Activate user with email address: " + activeEmail + " And status: " + userStatus);
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		if(userStatus.equalsIgnoreCase("BLACKLIST")) {
			getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			activateHistory(testEnv);
			
		}else {
		
			try{
				getDriver().findElement(By.linkText("activate")).click();
			}catch (Exception e){
				getDriver().findElement(By.linkText("Activate")).click();
			}
		

		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys("test@seamfix.com");

		//Check that if User selects 'OTHERS' as Activation there is a Mandatory input field for text
		TestUtils.testTitle("Check that if User selects 'OTHERS' as Activation there is a Mandatory input field for text Test");
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		
		//commented by ifeanyi
//		if(getDriver().findElement(By.xpath("//td[6]")).getText().equalsIgnoreCase("BLACKLIST")){
//			getDriver().findElement(By.xpath("//span[2]/ul/li[5]")).click();
//		}else{
//			getDriver().findElement(By.xpath("//span[2]/ul/li[10]")).click();
//		}
		
		//written by ifeanyi
		
		if(!getDriver().findElement(By.xpath("//td[6]")).getText().equalsIgnoreCase("BLACKLIST")){
			getDriver().findElement(By.xpath("//span[2]/ul/li[5]")).click();
		}else{
			getDriver().findElement(By.xpath("//span[2]/ul/li[10]")).click();
		}
		

		Thread.sleep(500);
		if(getDriver().findElement(By.name("otherReason")).isDisplayed()){
			testInfo.get().info("Other reason input field is displayed");
		}else{
			testInfo.get().error("Other reason input field is not displayed");
		}
		getDriver().findElement(By.id("comment")).sendKeys(disapprovalComment);
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "otherReason-error", "This field is required.");

		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li[2]")).click();
		getDriver().findElement(By.id("comment")).sendKeys(approvalComment);
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User account was successfully Activated");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void deactivateUser(String testEnv)
			throws InterruptedException {
		// Deactive User Test
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollUp();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// Email search
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(deactivatedUser); //deactivated user edited 
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String InactiveEmail = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[3]")).getText();
		String userStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[6]")).getText();
		Thread.sleep(500);
		TestUtils.testTitle("Deactivate user with email address: " + InactiveEmail + " And status: " + userStatus);
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("Deactivate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys("test@seamfix.com");

		//Check that if User selects 'OTHERS' as Deactivation there is a Mandatory input field for text
		TestUtils.testTitle("Check that if User selects 'OTHERS' as Deactivation there is a Mandatory input field for text Test");
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li[10]")).click();
		Thread.sleep(500);
		if(getDriver().findElement(By.name("otherReason")).isDisplayed()){
			testInfo.get().info("Other reason input field is displayed");
		}else{
			testInfo.get().error("Other reason input field is not displayed");
		}
		getDriver().findElement(By.id("comment")).sendKeys(disapprovalComment);
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "otherReason-error", "This field is required.");

		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("comment")).sendKeys(disapprovalComment);
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User account was successfully Deactivated");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void viewUserActivationHistory(String testEnv)
			throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToUserAccountCreationTest(testEnv);

		// Active User Test
		TestUtils.testTitle("Filter user with email address " + userHistory);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// Email search
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userHistory);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", userHistory);
		String userFullName = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[2]")).getText();
		String userStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[6]/span")).getText();
		Thread.sleep(500);
		TestUtils.testTitle("View history of user with email address: " + userHistory + " And status: " + userStatus);
		getDriver().navigate().refresh();
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedUsers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userHistory);
		getDriver().findElement(By.id("searchBtn")).click();
	
		scrollDown();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click(); 
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		//TestUtils.assertSearchText("XPATH", "//h4", userFullName); 
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'"+userFullName+"')]", userFullName); 
		
		

		// Change page size
		new Select(getDriver().findElement(By.name("userHistory_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='userHistory']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number count returned: " + rowCount);
		} else {
			testInfo.get().error("Table is empty.");
		}

		// Filter by performed By
		TestUtils.testTitle("Filter by performed By: " + performedBy);
		getDriver().findElement(By.id("performedByEmail")).clear();
		getDriver().findElement(By.id("performedByEmail")).sendKeys(performedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("XPATH", "//td[4]", performedBy);
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// Filter by approved By
		if (!getDriver().findElement(By.id("approverEmail")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		TestUtils.testTitle("Filter by approved By: " + approvedBy);
		getDriver().findElement(By.id("performedByEmail")).clear();
		getDriver().findElement(By.id("approverEmail")).clear();
		getDriver().findElement(By.id("approverEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		//scrolltotableData();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing...")); 
		
		
		
		//TestUtils.scrollToElement("XPATH", "//td[5]");
		
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("XPATH", "//td[5]", approvedBy);
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// Filter by Actions
		if (!getDriver().findElement(By.id("approverEmail")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		TestUtils.testTitle("Filter by action: ACTIVATION and DEACTIVATION");
		getDriver().findElement(By.id("approverEmail")).clear();
		getDriver().findElement(By.id("performedByEmail")).clear();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("activation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-warning", "ACTIVATION");
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("deactivation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
	
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	
		
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-danger", "DEACTIVATION"); 
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// DeactivateReactivate Dealer
		if (userStatus.equalsIgnoreCase("Blacklist") || userStatus.equalsIgnoreCase("Inactive")) {
			activateHistory(testEnv);
			getDriver().findElement(By.id("email")).clear();
			getDriver().findElement(By.id("email")).sendKeys(userHistory);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			scrollUp();
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", userHistory);
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[6]/span", "ACTIVE");
			getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			deactivateHistory(testEnv);

		} else {

			deactivateHistory(testEnv);
			getDriver().findElement(By.id("email")).clear();
			getDriver().findElement(By.id("email")).sendKeys(userHistory);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			scrollUp();
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", userHistory);
			getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			activateHistory(testEnv);

		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void deactivateHistory(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		Thread.sleep(500);
		String buttonText = getDriver().findElement(By.id("activate")).getText();
		Assert.assertEquals(buttonText, "DEACTIVATE USER");

		TestUtils.testTitle(buttonText);
		getDriver().findElement(By.id("activate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys(dealerApprovedBy);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		// missing field
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();

		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User account was successfully Deactivated");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void activateHistory(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		String buttonText2 = getDriver().findElement(By.id("activate")).getText();
		Assert.assertEquals(buttonText2, "ACTIVATE USER");

		TestUtils.testTitle(buttonText2);
		getDriver().findElement(By.id("activate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys(dealerApprovedBy);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		// missing field
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();

		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User account was successfully Activated");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void bulkActivationAndDeactivation(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		String bulkActivateTemplate = "Bulk_Account_Activation_List.xls";
		TestUtils.testTitle("Bulk Activate User Test for User: "+bulkUser+" and File: "+bulkActivateTemplate);
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("bad")).click();

		//Confirm that the modal is displayed
		TestUtils.testTitle("Bulk Activate/Deactivate Modal View Test");

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bulkAd']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='bulkAd']/div/div/div/h4", "Activate / Deactivate Users");
		if(getDriver().findElement(By.xpath("//div[8]/div/div/div[3]/a")).getText().contains("DOWNLOAD ACTIVATION TEMPLATE")){
			testInfo.get().info("DOWNLOAD ACTIVATION TEMPLATE found");
		}else{
			testInfo.get().info("DOWNLOAD ACTIVATION TEMPLATE not found");
		}
		if(getDriver().findElement(By.xpath("//div[3]/a[2]")).getText().contains("DOWNLOAD DEACTIVATION TEMPLATE")){
			testInfo.get().info("DOWNLOAD DEACTIVATION TEMPLATE found");
		}else{
			testInfo.get().info("DOWNLOAD DEACTIVATION TEMPLATE not found");
		}
		if(getDriver().findElement(By.id("badBtn")).getText().contains("UPLOAD")){
			testInfo.get().info("UPLOAD found");
		}else{
			testInfo.get().info("UPLOAD not found");
		}
		Thread.sleep(500);

		//Click upload without selecting a file
		TestUtils.testTitle("Upload without selecting a file Test");
		getDriver().findElement(By.id("badSpan")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "No file selected");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();

		String invalidFile = "image1.jpeg";
		//Click upload without selecting a file
		TestUtils.testTitle("Upload invalid file format Test: "+invalidFile);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("closeAD")));
		getDriver().findElement(By.id("closeAD")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("addUserMenuButton")));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.id("addUserMenuButton")));
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("bad")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bulkAd']/div/div/div/h4")));
		getDriver().findElement(By.id("uploadAd")).clear();
		TestUtils.uploadFile(By.id("uploadAd"), invalidFile);
		Thread.sleep(500);
		getDriver().findElement(By.id("badSpan")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Invalid file Provided. Supported file extension is .xls");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("closeAD")));

		//Confirm that the cancel button works
		TestUtils.testTitle("Cancel/Close Modal Test");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("closeAD")));
		getDriver().findElement(By.id("closeAD")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("addUserMenuButton")));
		TestUtils.assertSearchText("ID", "addUserMenuButton", "ADD USER");

		//Bulk Activate Test
		TestUtils.testTitle("Bulk Activate Test");
		wait.until(ExpectedConditions.elementToBeClickable(
				By.id("addUserMenuButton")));
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("bad")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bulkAd']/div/div/div/h4")));
		getDriver().findElement(By.id("uploadAd")).clear();
		TestUtils.uploadFile(By.id("uploadAd"), bulkActivateTemplate);
		Thread.sleep(500);
		getDriver().findElement(By.id("badSpan")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.id("closeAD")));
		getDriver().findElement(By.id("closeAD")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(bulkUser);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", bulkUser);
		TestUtils.assertSearchText("XPATH", "//td[6]/span", "ACTIVE");


		String deactivateTemplate = "Bulk_Account_deactivation_List.xls";
		TestUtils.testTitle("Bulk Deactivate User Test for User: "+bulkUser+" and File: "+deactivateTemplate);
		getDriver().findElement(By.id("addUserMenuButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("bad")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bulkAd']/div/div/div/h4")));
		getDriver().findElement(By.id("uploadAd")).clear();
		TestUtils.uploadFile(By.id("uploadAd"), deactivateTemplate);
		getDriver().findElement(By.id("badSpan")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("closeAD")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));

		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(bulkUser);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[3]", bulkUser);
		TestUtils.assertSearchText("XPATH", "//td[6]/span", "BLACKLIST");
    }

	@Test(groups = { "Sanity" })
	public void searchByDate() throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));
		getDriver().findElement(By.id("sStartDate")).clear();
		getDriver().findElement(By.id("sEndDate")).clear();
		TestUtils.testTitle("");
		getDriver().findElement(By.id("sStartDate")).sendKeys("2018-10-26");
		getDriver().findElement(By.id("sEndDate")).sendKeys("2018-04-19");
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();

	}

}
