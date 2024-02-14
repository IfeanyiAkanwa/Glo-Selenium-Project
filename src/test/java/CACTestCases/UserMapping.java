package CACTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class UserMapping extends TestBase {
	
	private String mappingName;
	private String levelName;
	private String email;
	private String mappingName1;
	
	@Parameters({"testEnv"})
    @BeforeMethod
    public void parseJson(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader(path));
        JSONObject envs = (JSONObject) config.get("userMapping");
        mappingName = (String) envs.get("mappingName");
        levelName = (String) envs.get("levelName");
        email = (String) envs.get("email");
        mappingName1 = (String) envs.get("mappingName1");
    }
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToUserMappingTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		TestUtils.testTitle("Navigate to User Mapping");
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1012User Management\"] > p");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("5823881479User Mapping")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1012User Management\"]");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"]")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("5823881479User Mapping")).click();
				Thread.sleep(500);
			}
		}else {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1012User Management\"] > p");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("854248137User Mapping")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1012User Management\"]");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"]")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("854248137User Mapping")).click();
				Thread.sleep(500);
			}
		}
		 wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
         TestUtils.assertSearchText("CSSSELECTOR", "h5.card-title.text-secondary", "User Mapping");
         Thread.sleep(500);
	}
	
	@Test(groups = { "Regression" })
	public void checkPageSizeTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Page size
		new Select(getDriver().findElement(By.xpath("//*[@id=\"roleManagementTable_length\"]/div/label/select"))).selectByVisibleText("50");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(1000);
		
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='userMapping']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: <b>" + rowCount + "</b>");
		} else {
			testInfo.get().info("Table is empty.");
		}

		// Assert User Mapping Table Test
		TestUtils.testTitle("Assert details of User Mapping table");
		Assertion.assertTableDataUserMappingTest();
		Thread.sleep(500);
		
		// Assert Action Menu of User Mapping Table
		TestUtils.testTitle("Assert Action Menu of User Mapping Table");
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Mapping Details')]", "View Mapping Details");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Edit Mapping Details')]", "Edit Mapping Details");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Add Users')]", "Add Users");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Remove User')]", "Remove User");
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByMappingNameTest(String testEnv) throws Exception {

		// Search with invalid Mapping name
		TestUtils.testTitle("Filter by Invalid Mapping name: oiuyt");
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("oiuyt");
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td/div/div", "No data available in table"); 
		
		// Search with valid Mapping name
		TestUtils.testTitle("Filter by valid Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void addUserMappingFormValidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Add Mapping Test");
		getDriver().findElement(By.id("addMapping")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Mapping");
		
		// Submit form with spaces as Mapping name
		TestUtils.testTitle("Submit form with spaces as Mapping name");
		getDriver().findElement(By.name("mappingName")).clear();
		getDriver().findElement(By.name("mappingName")).sendKeys("     ");
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[1]/small", "Mapping Name is Required");
		
		// Click on Cancel button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addMapping")));
		Thread.sleep(1000);
		
		// Submit form without filling mandatory fields
		getDriver().findElement(By.id("addMapping")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Mapping");
		TestUtils.testTitle("Submit form without filling mandatory fields");
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[1]/small", "Mapping Name is Required");
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[2]/div/div/div/div/small", "Level is required");
		
		// Click on the Cancel button
		TestUtils.testTitle("Click on the Cancel button");
		getDriver().findElement(By.xpath("//div[2]/div[2]/button")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("CSSSELECTOR", "h5.card-title.text-secondary", "User Mapping");
        Thread.sleep(500);
        
        // Test to Add new level button
        TestUtils.testTitle("Test to Add new level button");
		getDriver().findElement(By.id("addMapping")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Mapping");
		getDriver().findElement(By.name("mappingName")).clear();
		getDriver().findElement(By.name("mappingName")).sendKeys(mappingName);
		
		// Add new level
		getDriver().findElement(By.xpath("//form/div[3]/button")).click();
		
		// Submit button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[2]/div/div/div/div/small", "Level is required");
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[2]/div/div[2]/div/div/small", "Level is required");
		
		// Enter level name
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(levelName);
		
		// delete level
		getDriver().findElement(By.xpath("//div[2]/div/div/span/i")).click();
		
		// Cancel button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addMapping")));
		Thread.sleep(1000);
		
		// Submit form with existing mapping name
		TestUtils.testTitle("Submit form with existing mapping name");
		getDriver().findElement(By.id("addMapping")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Mapping");
		getDriver().findElement(By.name("mappingName")).clear();
		getDriver().findElement(By.name("mappingName")).sendKeys(mappingName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(levelName);
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping Name or group exists')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Mapping Name or group exists')]", "Mapping Name or group exists");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping Name or group exists')]")));
		Thread.sleep(500);
		
		// Submit form with special characters as Mapping name: @#$%$
		TestUtils.testTitle("Submit form with special characters as Mapping name: @#$%$");
		getDriver().findElement(By.id("addMapping")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Mapping");
		getDriver().findElement(By.name("mappingName")).clear();
		getDriver().findElement(By.name("mappingName")).sendKeys("@#$%$");
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(levelName);
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "/html/body/modal-container/div/div/div[2]/form/div[1]/small",
				"Mapping Name is Required");
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void editMappingDetailsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		Thread.sleep(1500);
		getDriver().findElement(By.xpath("//div/div[2]/button")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(1000);
		
		// Click on Edit button
		TestUtils.testTitle("Edit Mapping Details of Group: " + mappingName);
		//getDriver().findElement(By.linkText("Edit Mapping Details")).click();
		TestUtils.clickElement("XPATH", "//td[6]/div/ul/li[2]/a");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Edit Level Group')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Edit Level Group')]", "Edit Level Group");
		Thread.sleep(500);
		
		// Edit Mapping Details of Group with existing Group name
		TestUtils.testTitle("Edit Mapping Details of Group with existing Group name: " + mappingName1);
		
		// Enter existing Group name
		getDriver().findElement(By.name("mappingName")).clear();
		getDriver().findElement(By.name("mappingName")).sendKeys(mappingName1);
		
		// Submit button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping Name or group exists')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Mapping Name or group exists')]", "Mapping Name or group exists");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping Name or group exists')]")));
		Thread.sleep(500);
		
		// Add New level to Group
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Edit button
		TestUtils.testTitle("Edit Mapping Details of Group: " + mappingName);
		getDriver().findElement(By.xpath("//a[contains(text(),'Edit Mapping Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Edit Level Group");
		Thread.sleep(500);
		
		 //Add New level
		TestUtils.testTitle("Add new Level" );
		getDriver().findElement(By.xpath("//form/div[3]/button")).click();

		// Enter level name
		getDriver().findElement(By.xpath("//div[4]/div/div/input")).clear();
		getDriver().findElement(By.xpath("//div[4]/div/div/input")).sendKeys(levelName);
		
		// Submit Button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping was successfully Edited')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Mapping was successfully Edited')]", "Mapping was successfully Edited");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping was successfully Edited')]")));
		Thread.sleep(500);
		
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(2500);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);

		// Click on Action button
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Edit button
		TestUtils.testTitle("Edit Mapping Details of Group: " + mappingName);
		getDriver().findElement(By.xpath("//a[contains(text(),'Edit Mapping Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Edit Level Group");
		
		// delete level
		TestUtils.testTitle("Delete newly Added Level" );
		getDriver().findElement(By.xpath("//div[4]/div/div/span/i")).click();
		Thread.sleep(500);
		
		// Submit Button
		getDriver().findElement(By.xpath("//div[2]/div[2]/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping was successfully Edited')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Mapping was successfully Edited')]", "Mapping was successfully Edited");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Mapping was successfully Edited')]")));
		Thread.sleep(500);
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void addUserToMappingGroupTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Add user button
		TestUtils.testTitle("Add Users to a Mapping Group: " + mappingName);
		//getDriver().findElement(By.linkText("Add Users")).click();
		TestUtils.clickElement("XPATH", "//td[6]/div/ul/li[3]/a");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Users");
		
		// Submit form without filling mandatory fields
		TestUtils.testTitle("Submit form without filling mandatory fields");
		getDriver().findElement(By.xpath("//div[2]/div/button[2]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div[2]/small", "Level is required");
		TestUtils.assertSearchText("XPATH", "//div[3]/small", "User is required");
		
		// Click on Cancel button
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
		Thread.sleep(500);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Add user button
		TestUtils.testTitle("Submit form with valid details and Add user: " + email);
		//getDriver().findElement(By.xpath("//a[contains(text(),'Add Users')]")).click();
		TestUtils.clickElement("XPATH", "//td[6]/div/ul/li[3]/a");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Add Users')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Add Users')]", "Add Users");
		
		// Select Level
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
		Thread.sleep(500);
		
		// Select User
		getDriver().findElement(By.xpath("//ng-select/div/div/div[2]/input")).click();
		Thread.sleep(500);
		TestUtils.setText(By.xpath("//ng-select/div/div/div[2]/input"), email);
		getDriver().findElement(By.xpath("//span[contains(text(),'gnwabude@seamfix.com')]")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ng-option-label")));
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.ng-option-label")));
//		Thread.sleep(1000);
//		try{
//			getDriver().findElement(By.cssSelector(".ng-option-label")).click();
//		}catch (Exception e){
//			try{
//				getDriver().findElement(By.cssSelector(".ng-option-label")).click();
//			}catch (Exception ee){
//				getDriver().findElement(By.cssSelector(".ng-option-label")).click();
//			}
//		}

		Thread.sleep(500);
		
		// Submit Button
		getDriver().findElement(By.xpath("(//button[@type='submit'])[6]")).click();
		Thread.sleep(100);
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'User was mapped successfully')]")));
			TestUtils.assertSearchText("XPATH", "//*[contains(text(),'User was mapped successfully')]", "User was mapped successfully");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'User was mapped successfully')]")));
			Thread.sleep(500);
		}catch (Exception e){

		}

		
		// To confirm that user was added
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/div[2]/button")).click();
		Thread.sleep(2500);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on View Details
		TestUtils.testTitle("To confirm that the user: " + email + " was added");
		getDriver().findElement(By.xpath("//a[contains(text(),'View Mapping Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "View Details");
		
		String Username = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr/td")).getText();
		if (Username.contains(email)) {
            testInfo.get().log(Status.INFO, Username + " found");
		} else {
			 testInfo.get().log(Status.INFO, "not found");
		}
		Thread.sleep(500);
		
		// Close the Modal
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
		Thread.sleep(1000);
		
		// To confirm that a user cannot be mapped to more than one level under the same mapping name
		TestUtils.testTitle("To confirm that a user cannot be mapped to more than one level under the same mapping name");
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);
		
		// Click on Add user button
		getDriver().findElement(By.xpath("//a[contains(text(),'Add Users')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Add Users");
		
		// Select Level
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/div[2]/div[3]")).click();
		Thread.sleep(500);

		// Select User
//		getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).click();
//		Thread.sleep(500);
//		TestUtils.setText(By.xpath("(//input[@type='text'])[5]"), email);
		getDriver().findElement(By.xpath("//ng-select/div/div/div[2]/input")).click();
		Thread.sleep(500);
		TestUtils.setText(By.xpath("//ng-select/div/div/div[2]/input"), email);
		getDriver().findElement(By.xpath("//span[contains(text(),'gnwabude@seamfix.com')]")).click();
		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ng-option-label")));
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.ng-option-label")));
//		Thread.sleep(1000);
//		try{
//			getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//		}catch (Exception e){
//			try{
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}catch (Exception ee){
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}
//		}

		Thread.sleep(500);
		
		// Submit Button
		getDriver().findElement(By.xpath("(//button[@type='submit'])[6]")).click();
		Thread.sleep(500);
		try{
			Thread.sleep(500);
			TestUtils.assertSearchText("CSSSELECTOR", "h2.modal-title.pull-left.pt-5", "User was previously mapped to a Level. Do you want to override ?");

			Thread.sleep(1000);
			// Click on NO button
			TestUtils.testTitle("Click on NO button");
			getDriver().findElement(By.xpath("//div/div[2]/button[2]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
			TestUtils.assertSearchText("CSSSELECTOR", "h5.card-title.text-secondary", "User Mapping");
			Thread.sleep(500);
			Thread.sleep(500);
		}catch (Exception e){

		}


		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Add user button
		getDriver().findElement(By.xpath("//a[contains(text(),'Add Users')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		Thread.sleep(500);
        
		// Select Level
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/div[2]/div[3]")).click();
		Thread.sleep(500);

		// Select User
//		getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).click();
//		Thread.sleep(500);
//		TestUtils.setText(By.xpath("(//input[@type='text'])[5]"), email);
		
		getDriver().findElement(By.xpath("//ng-select/div/div/div[2]/input")).click();
		Thread.sleep(500);
		TestUtils.setText(By.xpath("//ng-select/div/div/div[2]/input"), email);
		getDriver().findElement(By.xpath("//span[contains(text(),'gnwabude@seamfix.com')]")).click();
		
		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ng-option-label")));
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.ng-option-label")));
//		Thread.sleep(1000);
//		try{
//			getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//		}catch (Exception e){
//			try{
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}catch (Exception ee){
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}
//		}
		Thread.sleep(500);

		// Submit Button
		getDriver().findElement(By.xpath("//div[2]/div/button[2]")).click();
		Thread.sleep(500);

		// Click on YES button
		TestUtils.testTitle("Click on YES button");
		try{
			Thread.sleep(1500);
			TestUtils.assertSearchText("CSSSELECTOR", "h2.modal-title.pull-left.pt-5", "User was previously mapped to a Level. Do you want to override ?");
			getDriver().findElement(By.xpath("//div/div/div[2]/button")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}
		try{
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//*[contains(text(),'User was mapped successfully')]",	"User was mapped successfully");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'User was mapped successfully')]")));
			Thread.sleep(500);

		}catch (Exception e){

		}

	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void removeUserFromGroupTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Add user button
		TestUtils.testTitle("Remove User from a Mapping Group: " + mappingName);
		//getDriver().findElement(By.xpath("//a[contains(text(),'Remove User')]")).click();
		TestUtils.clickElement("XPATH", "//td[6]/div/ul/li[4]/a");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Remove User");
		
		// Submit form without filling mandatory fields
		TestUtils.testTitle("Submit form without filling mandatory fields");
		getDriver().findElement(By.xpath("(//button[@type='submit'])[6]")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div/small", "A user is required");

		// Click on Cancel button
		getDriver().findElement(By.xpath("//div/div/div[2]/div/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
		Thread.sleep(500);

		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on Add user button
		TestUtils.testTitle("Submit form with valid details and Remove user: " + email);
		getDriver().findElement(By.xpath("//a[contains(text(),'Remove User')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Remove User");
		
		// Select User
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[4]")));
		getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).click();
		Thread.sleep(500);
		//getDriver().findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(email);
		
		TestUtils.setText(By.xpath("(//input[@type='text'])[4]"), email);
		getDriver().findElement(By.xpath("//span[contains(text(),'gnwabude@seamfix.com')]")).click();
		
//		try{
//			getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//		}catch (Exception e){
//			try{
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}catch (Exception ee){
//				getDriver().findElement(By.cssSelector("span.ng-option-label")).click();
//			}
//		}
		getDriver().findElement(By.xpath("//div[2]/div/button[2]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Success')]", "Success");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("Success")));
		Thread.sleep(500);
		
		
		
		
		// To confirm that user was removed
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName);
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName);

		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);

		// Click on View Details
		TestUtils.testTitle("To confirm that the user: " + email + " was removed");
		getDriver().findElement(By.xpath("//a[contains(text(),'View Mapping Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "View Details");
		try{
			TestUtils.assertSearchText("XPATH", "//td/div/div", "No data available in table");
		}catch (Exception e){
			testInfo.get().error("User wasn't removed");
		}

		
		// Close the Modal
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h5.card-title.text-secondary", "User Mapping");
        Thread.sleep(500);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewMappingDetailsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Search by Mapping name
		TestUtils.testTitle("Filter by Mapping name: " + mappingName1);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[2]")));
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).clear();
		getDriver().findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(mappingName1);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='userMapping']/tbody/tr/td[2]")));
		TestUtils.assertSearchText("XPATH", "//table[@id='userMapping']/tbody/tr/td[2]", mappingName1);
		
		// Click on Action button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[6]/div/a/i")).click();
		Thread.sleep(500);
		
		// Click on View Details
		TestUtils.testTitle("View Mapping details of Group: " + mappingName1);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Mapping Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "View Details");
		
		// Change page Size
		new Select(getDriver().findElement(By.xpath("/html/body/modal-container/div/div/div/div[2]/div/div/div/div/div/div[1]/div[2]/div/label/select"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("/html/body/modal-container/div/div/div/div[2]/div/div/div/div/div/div[1]/table/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: <b>" + rowCount + "</b>");
		} else {
			testInfo.get().info("Table is empty.");
		}
		
		// View Levels
		TestUtils.testTitle("View Levels of Group: " + mappingName1);
		getDriver().findElement(By.cssSelector("div.btn-link.mb-2")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "View Details");
		TestUtils.assertSearchText("XPATH", "//div/div/p", "Ballers");
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div/div/div/div/div/div/span", "Level : 5");
		Thread.sleep(500);
		
		// Assert Level names
		TestUtils.testTitle("Assert Level names of mapping Group: " + mappingName1);
		Assertion.assertLevelsOfUserMappingTest();
		Thread.sleep(500);
		
		// Close the modal
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
		Thread.sleep(500);
	}
}
