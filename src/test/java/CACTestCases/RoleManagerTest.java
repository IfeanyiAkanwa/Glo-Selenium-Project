package CACTestCases;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import util.TestBase;
import util.TestUtils;

public class RoleManagerTest extends TestBase {
	private static String newRole = "SEAMFIX SANITY TEST ROLE "+ System.currentTimeMillis();
	
	public void scrollDown() throws Exception {
		TestUtils.scrollToElement("ID", "roleManagementTable");
	}

	public void scrollUp() throws Exception {
		TestUtils.scrollToElement("NAME", "searchParam");
	}

	@Test(groups = { "Regression" })
	  public void navigateToRoleManagerTest() throws Exception{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Role Management");
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1010Role Management\"] > p");
	    getDriver().findElement(By.cssSelector("a[name=\"1010Role Management\"] > p")).click();
	   
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "User Roles");
	}
	
	@Test (groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.xpath("//select[@name='roleManagementTable_length']"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='roleManagementTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	  public void searchByRoleTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("roleManager");

		String role1 = (String) envs.get("role1");
		String role2 = (String) envs.get("role2");
		
		TestUtils.testTitle("Filter by roles: " + role1+ " and "+role2);
		getDriver().findElement(By.name("searchParam")).clear();
	    getDriver().findElement(By.name("searchParam")).sendKeys(role1);
	    TestUtils.clickElement("ID", "tableSearchBtn");
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    String role = getDriver().findElement(By.xpath("//table[@id='roleManagementTable']/tbody/tr/td[2]")).getText();
		if (role.contains(role1)) {
            testInfo.get().log(Status.INFO, role + " found");
		} else {
			 testInfo.get().log(Status.INFO, "not found");
		}
	    
	    getDriver().findElement(By.name("searchParam")).clear();
	    getDriver().findElement(By.name("searchParam")).sendKeys(role2);
	    TestUtils.clickElement("ID", "tableSearchBtn");
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='roleManagementTable']/tbody/tr/td[2]", role2);
	    
	  }
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	  public void addNewRoleFormvalidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("roleManager");
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		TestUtils.testTitle("Role Management Form Validation");
		String role1 = (String) envs.get("role1");
		Thread.sleep(500);
	    getDriver().findElement(By.xpath("//form[@id='addRoleForm']/button")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.category", "Create Role and Assign Privileges");
	    
	    TestUtils.testTitle("Leave mandatory fields empty and Submit with existing role: " + role1);
	  
	    // Role name
	    getDriver().findElement(By.id("roleName")).clear();
	    TestUtils.scrollToElement("ID", "submit");
	    getDriver().findElement(By.id("submit")).click();
	    Thread.sleep(500);
	    WebElement roleName = getDriver().findElement(By.name("roleName"));
	    TestUtils.assertValidationMessage(validationMessages, roleName, "Role Name");
	    getDriver().findElement(By.id("roleName")).sendKeys(role1);
	    
	    // Description
	    getDriver().findElement(By.id("description")).clear();
	    TestUtils.scrollToElement("ID", "submit");
	    getDriver().findElement(By.id("submit")).click();
	    Thread.sleep(500);
	    WebElement description = getDriver().findElement(By.name("description"));
	    TestUtils.assertValidationMessage(validationMessages, description, "Description Name");
	    getDriver().findElement(By.id("description")).sendKeys("Support the activities for Testing");
	    
	    Thread.sleep(500);
	    getDriver().findElement(By.id("cacheable")).click();
	    
	    // save button
 		TestUtils.scrollToElement("ID", "submit");
 	    getDriver().findElement(By.id("submit")).click();
 	    Thread.sleep(500);
 	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
 	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Role With name already exists");
 	    getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
 	    Thread.sleep(1000);
		
	    // Cancel button
		TestUtils.scrollToElement("ID", "submit");
	    getDriver().findElement(By.cssSelector("button.btn.btn-secondary.pull-left")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    
	}
	 
	@Test(groups = { "Regression" })
	  public void addNewRoleTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		navigateToRoleManagerTest();

		//Download PDF
		getDriver().findElement(By.xpath("//div[@id='roleManagementTable_wrapper']/div/a")).click();
		Thread.sleep(500);

		//Download EXCEL
		getDriver().findElement(By.xpath("//div[@id='roleManagementTable_wrapper']/div/a[2]")).click();
		Thread.sleep(500);

	    getDriver().findElement(By.xpath("//form[@id='addRoleForm']/button")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.category", "Create Role and Assign Privileges");
	    
	    TestUtils.testTitle(" Create new role: " + newRole);
	    getDriver().findElement(By.id("roleName")).clear();
	    getDriver().findElement(By.id("roleName")).sendKeys(newRole);
	    
	    getDriver().findElement(By.id("description")).clear();
	    getDriver().findElement(By.id("description")).sendKeys("Support the activities for Testing");
	    Thread.sleep(500);
	    getDriver().findElement(By.id("cacheable")).click();
	    
	    getDriver().findElement(By.xpath("//span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//li/input")).clear();
	    getDriver().findElement(By.xpath("//li/input")).sendKeys("SIMROP DEALER DASHBOARD");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//li/input")).clear();
	    getDriver().findElement(By.xpath("//li/input")).sendKeys("SIMROP VTU DEALER TRANSACTION HISTORY");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
	    
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).sendKeys("GLO ADMIN");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//div[5]/span/span/span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).sendKeys("DEALER");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		testInfo.get().log(Status.INFO, "Form completed.");
		
	    // save button
		TestUtils.scrollToElement("ID", "submit");
	    getDriver().findElement(By.id("submit")).click();
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Role has been successfully created");
	    Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.swal2-confirm.swal2-styled")));
	    getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "User Roles");
	  }
	 
	@Test(groups = { "Regression" })
	  public void viewDetailsAndUpdateRoleTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToRoleManagerTest();
		TestUtils.testTitle("View full details and update newly created role: " + newRole);
		getDriver().findElement(By.name("searchParam")).clear();
	    getDriver().findElement(By.name("searchParam")).sendKeys(newRole);
	    TestUtils.clickElement("ID", "tableSearchBtn");
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='roleManagementTable']/tbody/tr/td[2]", newRole);
	    	
	    getDriver().findElement(By.xpath("//table[@id='roleManagementTable']/tbody/tr/td[6]/div/a/i")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Role Details");
	    
	    TestUtils.testTitle("Role Information");
	    String role = getDriver().findElement(By.xpath("//div/div[2]/div/div[2]/div")).getText();
	    String description = getDriver().findElement(By.xpath("//div[2]/div/div[2]/div[2]")).getText();
	    String admin = getDriver().findElement(By.xpath("//div[2]/div/div[2]/div[3]")).getText();
	    
	    testInfo.get().info(role);
	    testInfo.get().info(description);
	    testInfo.get().info(admin);
	    
	    TestUtils.testTitle("Privilege Information");
	    int privilegeInfo = getDriver().findElements(By.xpath("//div[2]/div[2]/div/table/tbody/tr")).size();
	    if(privilegeInfo>0) {
	    for (int i=1; i<=privilegeInfo; i++) {
	    	String privilegName = getDriver().findElement(By.xpath("//div[2]/div[2]/div/table/tbody["+i+"]/tr/td")).getText();
	    	testInfo.get().info(privilegName);
	    	}
	    }else {
	    	testInfo.get().info("No privilege found");
	    }
	    
	    TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Edit')]");
	    
	    TestUtils.testTitle("Community Information");
	    int communityInfo = getDriver().findElements(By.xpath("//div[3]/div[2]/div/table/tbody/tr")).size();
	    if(communityInfo>0) {
	    for (int i=1; i<=communityInfo; i++) {
	    	String communityName = getDriver().findElement(By.xpath("//div[3]/div[2]/div/table/tbody["+i+"]/tr/td")).getText();
	    	testInfo.get().info(communityName);
	    	}
	    }else {
	    	testInfo.get().info("No community found");
	    }
	    
	    // Edit
	    String editRole1 = "UPDATE SEAMFIX SANITY TEST ROLE "+ System.currentTimeMillis();
	    TestUtils.testTitle("Update newly created role to : " + editRole1);
	    getDriver().findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.category", "Edit Role and Assigned Privileges");
	    
	    getDriver().findElement(By.id("roleName")).clear();
	    getDriver().findElement(By.id("updateButton")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.id("roleName")).sendKeys(editRole1);
	    
	    getDriver().findElement(By.id("description")).clear();
	    getDriver().findElement(By.id("updateButton")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.id("description")).sendKeys("Edit the activities for Testing");
	    
	    getDriver().findElement(By.id("cacheable")).click();
	    
	    getDriver().findElement(By.xpath("//span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//li/input")).clear();
	    getDriver().findElement(By.xpath("//li/input")).sendKeys("SIMROP_DEALER_DASHBOARD");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//li/input")).clear();
	    getDriver().findElement(By.xpath("//li/input")).sendKeys("SIMROP_SETTINGS_VIEW");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(1000);
	    
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).clear();
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).sendKeys("Dealer");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//div[5]/span/span/span/ul")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).clear();
	    getDriver().findElement(By.xpath("//div[5]/span/span/span/ul/li/input")).sendKeys("Guest");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);
		testInfo.get().log(Status.INFO, "Update Form completed.");
		
		// save button
		TestUtils.scrollToElement("ID", "updateButton");
	    getDriver().findElement(By.id("updateButton")).click();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Successfully updated Role");
	    TestUtils.clickElement("CSSSELECTOR", "button.swal2-confirm.swal2-styled");
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Role Details");
	    TestUtils.assertSearchText("XPATH", "//td[2]", editRole1);
	    
	    //click back button
	    TestUtils.testTitle("Navigate back and search for newly updated role: "+editRole1);
	    TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Edit')]");
	    getDriver().findElement(By.cssSelector("button.btn.btn-secondary.pull-left")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "User Roles");
	    getDriver().findElement(By.name("searchParam")).clear();
	    getDriver().findElement(By.name("searchParam")).sendKeys(editRole1);
	    TestUtils.clickElement("ID", "tableSearchBtn");
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='roleManagementTable']/tbody/tr/td[2]", editRole1);
	    
	  }


}


