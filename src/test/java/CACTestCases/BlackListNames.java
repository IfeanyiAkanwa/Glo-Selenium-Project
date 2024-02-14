package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

public class BlackListNames extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("NAME", "searchParam");
	}
	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("XPATH", "//div/p");
	}
	
	@Test(groups = { "Regression" })
	public void navigateToBlackliNameTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Blacklisted names");
		try {
			TestUtils.scrollToElement("XPATH", "//a[@name='1237Blacklisted Names']");
			getDriver().findElement(By.xpath("//a[@name='1237Blacklisted Names']")).click();
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.scrollToElement("XPATH", "//a[@name='11153238579Fraudulent Names Pool']");
			getDriver().findElement(By.xpath("//a[@name='11153238579Fraudulent Names Pool']")).click();
			Thread.sleep(500);
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.col-sm-4 > h4.card-title", "Blacklisted Names");
		
	}
	
	@Test(groups = { "Regression" })
	  public void assertNameCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String totalValString = getDriver().findElement(By.cssSelector("h4.card-title.text-center")).getText();
		String ActiveValString = getDriver().findElement(By.xpath("//div[2]/div/div/h4")).getText();
		String InactiveValString = getDriver().findElement(By.xpath("//div[3]/div/div/h4")).getText();
		
		int totalVal = TestUtils.convertToInt(totalValString);
		int activeVal = TestUtils.convertToInt(ActiveValString);
		int inactiveVal = TestUtils.convertToInt(InactiveValString);
				
		int expectedTotalVal = activeVal + inactiveVal;
		
		TestUtils.testTitle("Test to assert count of names ");

		
		try {
			Assert.assertEquals(expectedTotalVal,totalVal);
		    testInfo.get().log(Status.INFO, "Total BlackList Names ("+expectedTotalVal+") is equal to summation of total active ("+activeVal+") + total inactive ("+inactiveVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    }
		
		// Page size
		new Select(getDriver().findElement(By.name("blacklistedNamesTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");

		int rowCount = getDriver().findElements(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByBlacklistName(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("blacklistName");

		String name = (String) envs.get("name");

		TestUtils.testTitle("Filter by Blacklisted Names: " + name);

		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(name);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='blacklistedNamesTable']/tbody/tr/td[3]", name);
		Thread.sleep(500);

	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByAddedBy(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistName");

		String addedBy = (String) envs.get("addedBy");

		TestUtils.testTitle("Filter by Added By: " + addedBy);
;
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).sendKeys(addedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='blacklistedNamesTable']/tbody/tr/td[5]", addedBy);
		Thread.sleep(500);

	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Test to Search by status");
		scrollDown();
		Thread.sleep(500);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		// Active status
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Active");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "ACTIVE");
		
		// Inactive status
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("InActive");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistName");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		TestUtils.testTitle("Filter by date range: " + startDate+ " and "+endDate);

		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[4]")).getText();
		table_Date = table_Date.substring(0, 10);
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateyYMDBoundary(startDate, endDate, table_Date);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void singleAddName(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistName");

		String name = (String) envs.get("name");
		scrollDown();
		
		TestUtils.testTitle("Test to add name");
		
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("addLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Add Name");
		
		TestUtils.testTitle("Click ADD NAME button without supplying any name to blacklist");
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Please enter a name.')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Please enter a name.')]", "Please enter a name.");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
		
		// valid existing name
		TestUtils.testTitle("Supply existing name " + name + " and Click ADD NAME button");

		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(name);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(500);
		
		// Click No
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this name?");
		getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		Thread.sleep(1000);
		
		// Click Yes
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger > div.container-fluid")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-danger > div.container-fluid")).getText();
		String requiredText = expectedText.substring(20, 39);
		String value = "Name Already Exists";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
		Thread.sleep(1000);
		
		// Valid new name
		String newName = "Test"+TestUtils.generateSerialNumber();
		TestUtils.testTitle("Supply new name "+newName+" and Click ADD NAME button");
		
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("addLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Add Name");
		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(newName);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(500);
		
		// Click Yes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success > div.container-fluid")));
		String expectedText2 = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid")).getText();
		String requiredText2 = expectedText2.substring(12, 30);
		String value2 = "Successfully added";

		try {
			Assert.assertEquals(requiredText2, value2);
			testInfo.get().log(Status.INFO, value2 + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value2 + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
		Thread.sleep(1000);
		
	}
	
	@Parameters({ "server", "downloadPath" })
	@Test(groups = { "Regression" })
	public void bulkAddName(String server, String downloadPath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Test to add names in bulk");
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("bulkAddLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bulkAddNameTitle")));
		TestUtils.assertSearchText("ID", "bulkAddNameTitle", "Add Names");
		
		TestUtils.testTitle("Click ADD NAME button without selecting any file to upload");

		getDriver().findElement(By.id("addBulkNameBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Invalid file Provided. Supported file extension is .xls')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Invalid file Provided. Supported file extension is .xls')]", "Invalid file Provided. Supported file extension is .xls");
		try {
			getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
			Thread.sleep(500);
		} catch (Exception e) {
		
		}
		Thread.sleep(500);
		
		String invalidFile= "image2.jpg";
		
		// Select an invalid file format
		TestUtils.testTitle("Select invalid file format and upload. eg jpg " + invalidFile);
		TestUtils.uploadFile(By.id("addbulknames"), invalidFile);
		getDriver().findElement(By.id("addBulkNameBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Invalid file Provided. Supported file extension is .xls')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Invalid file Provided. Supported file extension is .xls')]", "Invalid file Provided. Supported file extension is .xls");
		try {
			getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
			Thread.sleep(500);
		} catch (Exception e) {
		
		}
		Thread.sleep(500);
		getDriver().findElement(By.id("addBulkNameBtnClose")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void singleActivateDeactivate (String testEnv) throws FileNotFoundException, InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		searchByBlacklistName(testEnv);
		String status = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[6]/span")).getText();
		testInfo.get().info(status);
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		deactivateReactivate(status);
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		searchByBlacklistName(testEnv);
		String status2 = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[6]/span")).getText();
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		testInfo.get().info(status2);
		deactivateReactivate(status2);
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
	}
	
	public void deactivateReactivate(String Status) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		WebElement button;
		@SuppressWarnings("unused")
		String message, action;
		if (Status.equalsIgnoreCase("Active")) {
			button = getDriver().findElement(By.linkText("Deactivate"));
			message = "Active status was changed successfully";
			action = "deactivate";
		} else {
			button = getDriver().findElement(By.linkText("Activate"));
			message = "Active status was changed successfully";
			action = "activate";
		}

		TestUtils.testTitle(action+" blacklisted name");

		button.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to "+action+" check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'"+message+"')]")));
//		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'"+message+"')]", message);
		
	}
	
	@Test(groups = { "Sanity" })
	public void singleActivateUserTest() throws Exception {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
		
		TestUtils.testTitle("Single Activation");

		
		TestUtils.clickElement("ID","advancedBtn");
		Thread.sleep(1000);
		
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("InActive");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		Thread.sleep(1000);
		
	    String name = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[3]")).getText();
		
	    TestUtils.testTitle("Activate WhiteListed Name: " + name);
		TestUtils.scrollToElement("ID", "advancedBtn");

		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//td[7]/div/ul/li/a")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to activate check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Status was changed successfully')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Status was changed successfully')]", "Status was changed successfully");
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Status was changed successfully')]", "Status was changed successfully");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	@Test(groups = { "Sanity" })
	public void singleDeactivateUserTest() throws InterruptedException, IOException {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2500);
		
	    TestUtils.testTitle("Single Deactivation");
		TestUtils.clickElement("ID","advancedBtn");
		Thread.sleep(1000);
		
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("ACTIVE");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "ACTIVE");
		Thread.sleep(1000);
		
	    String name = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[3]")).getText();
	    
	    TestUtils.testTitle("Deactivate WhiteListed Name: " + name);

		TestUtils.scrollToElement("ID", "advancedBtn");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[7]/div/ul/li/a")));
		getDriver().findElement(By.xpath("//td[7]/div/ul/li/a")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to deactivate check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Status was changed successfully')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Status was changed successfully')]", "Status was changed successfully");
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Status was changed successfully')]", "Status was changed successfully");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	public void Assert_BlackListNames_Table() throws Exception {
	    
    	String NA = "N/A";
    	
		String serialNo = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[2]")).getText();
		String blackListedName = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[3]")).getText();
		String dateAdded = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[4]")).getText();
		String addedBy = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[5]")).getText();
		String status = getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td[6]")).getText();
		
		String[] toList = {"Serial No:"+serialNo, "BlackListed Name:"+blackListedName, "Date Added:"+dateAdded,"Added By:"+addedBy, 
				"Status:"+status };
		for (String field : toList) {
			String name= "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name+ " : " +val);
			    } catch (Error e) {
			    testInfo.get().error( name+ " : " + val);
			    }
		}
	}
	
	@Test(groups = { "Sanity" })
	public void bulkDeactivateUserTest() throws InterruptedException, IOException {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
		
	    TestUtils.testTitle("Deactivate multiple Blacklisted Names");
	    
	    TestUtils.clickElement("ID","advancedBtn");

		//getDriver().findElement(By.id("advancedBtn")).click();
		
		Thread.sleep(1000);
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();	
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("ACTIVE");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "ACTIVE");
		Thread.sleep(1000);
		
		// Click the checkboxes
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td/div/label/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr[2]/td/div/label/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr[3]/td/div/label/span/span")).click();
		Thread.sleep(500);
		
		TestUtils.scrollToElement("ID", "add_names_button");
		Thread.sleep(500);
		getDriver().findElement(By.id("add_names_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("removeSelectedLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to deactivate selected name(s)");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success > div.container-fluid")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid")).getText();
		String requiredText = expectedText.substring(12, expectedText.length());
		String value = "Successfully deactivated 3 names";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	@Test(groups = { "Sanity" })
	public void bulkActivateUserTest() throws Exception {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
	
	    TestUtils.testTitle("Activate multiple Blacklisted Names");

		
		TestUtils.clickElement("ID","advancedBtn");
		Thread.sleep(1000);
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("InActive");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		Thread.sleep(1000);
		
		// Click the checkboxes
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr/td/div/label/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr[2]/td/div/label/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='blacklistedNamesTable']/tbody/tr[3]/td/div/label/span/span")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "add_names_button");
		Thread.sleep(500);
		getDriver().findElement(By.id("add_names_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addSelectedLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to activate selected name(s)?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success > div.container-fluid")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid")).getText();
		String requiredText = expectedText.substring(12, expectedText.length());
		String value = "Successfully reactivated 3 names";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
}
