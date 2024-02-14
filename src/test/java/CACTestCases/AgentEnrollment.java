package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class AgentEnrollment extends TestBase {

	@Test(groups = { "Regression" })
	public void navigateToAgentEnrollmentTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Navigate to Agent Enrolment");

		Thread.sleep(500);
		try {
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("a[name=\"1235Agent Enrollment\"] > p")).click();
		} catch (Exception e) {
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("a[name=\"7883203238Agent Enrollment\"] > p")).click();
		}
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Kits");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Kit Mapping");

	}

	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("assignmentDetail_length"))).selectByVisibleText("250");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Change page size to: 250");

		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='assignmentDetail']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of user returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	@Test(groups = { "Regression" })
	public void viewKitMappingDetails() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		// Filter by Approved
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		String kitTag = getDriver().findElement(By.xpath("//td[2]")).getText();
		TestUtils.testTitle("View full details of kit tag: " + kitTag);
		Thread.sleep(500);
		
		if (!getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[10]/div/a/i")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//table[@id='assignmentDetail']/tbody/tr[2]/td/ul/li/span[2]/div/a/i");
			
		}
	
		getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr[2]/td/ul/li/span[2]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Mapping Details")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'View Details')]", "View Details");
		TestUtils.scrollToElement("XPATH", "//tr[7]/td[2]");

		// Page assertion
		TestUtils.testTitle("Device and Dealer Information");

		Assertion.assertDeviceDealerDetails();
//		Thread.sleep(1000);
//
//		TestUtils.scrollToElement("XPATH", "//div[5]/div/div/h4");
//		TestUtils.testTitle("Outlet and Agent Information");
//
//		Assertion.assertOutletAgentDetails();
//
//		TestUtils.scrollToElement("XPATH", "//h4");
//		Thread.sleep(500);

		// Return button
		getDriver().findElement(By.xpath("//a/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTag(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("kitMapping");

		String kitTag = (String) envs.get("kitTag");

		TestUtils.testTitle("Filter by kit tag: " + kitTag);

		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//td[2]", kitTag);
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDeviceID(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("kitMapping");

		String deviceID = (String) envs.get("deviceID");

		TestUtils.testTitle("Filter by kit tag: " + deviceID);

		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("View full details of Device ID: ");
		Thread.sleep(500);
		
		if (getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td")).isDisplayed()) {
		
			getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//table[@id='assignmentDetail']/tbody/tr[2]/td/ul/li/span[2]/div/a/i");
			
		}
		
		getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr[2]/td/ul/li/span[2]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Mapping Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

		TestUtils.assertSearchText("XPATH", "//tr[5]/td[2]", deviceID);
		getDriver().findElement(By.xpath("//a/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByAgent(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("kitMapping");

		String assignedAgent = (String) envs.get("assignedAgent");
		/*
		 * try { Thread.sleep(500); getDriver().findElement(By.
		 * cssSelector("a[name=\"173213533Agent Enrollment\"] > p")).click(); } catch
		 * (Exception e) { Thread.sleep(500); getDriver().findElement(By.
		 * cssSelector("a[name=\"7883203238Agent Enrollment\"] > p")).click(); }
		 * Thread.sleep(500);
		 */
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Filter by assigned agent: " + assignedAgent);

		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("usernameParam")).sendKeys(assignedAgent);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[8]", assignedAgent);

	}

	/*
	 * @Parameters({ "testEnv" })
	 * 
	 * @Test(groups = { "Regression" }) public void searchByMacAddress(String
	 * testEnv) throws InterruptedException, FileNotFoundException, IOException,
	 * ParseException { WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	 * File path = null; File classpathRoot = new
	 * File(System.getProperty("user.dir")); if
	 * (testEnv.equalsIgnoreCase("StagingData")) { path = new File(classpathRoot,
	 * "stagingData/data.conf.json"); } else { path = new File(classpathRoot,
	 * "prodData/data.conf.json"); } JSONParser parser = new JSONParser();
	 * JSONObject config = (JSONObject) parser.parse(new FileReader(path));
	 * JSONObject envs = (JSONObject) config.get("kitMapping");
	 * 
	 * String macAddress = (String) envs.get("macAddress");
	 * 
	 * String macFilter = "Filter by mac address: " + macAddress; Markup m =
	 * MarkupHelper.createLabel(macFilter, ExtentColor.BLUE);
	 * testInfo.get().info(m); Thread.sleep(500);
	 * 
	 * getDriver().findElement(By.name("kitTagParam")).clear();
	 * getDriver().findElement(By.name("deviceIdSearchParam")).clear(); if
	 * (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
	 * getDriver().findElement(By.id("advancedBtn")).click(); }
	 * getDriver().findElement(By.name("usernameParam")).clear();
	 * getDriver().findElement(By.name("macAddressSearchParam")).clear();
	 * getDriver().findElement(By.name("macAddressSearchParam")).sendKeys(macAddress
	 * ); getDriver().findElement(By.id("searchBtn")).click(); Thread.sleep(500);
	 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
	 * "dataTables_processing"), "Processing..."));
	 * getDriver().findElement(By.xpath(
	 * "//table[@id='assignmentDetail']/tbody/tr/td[7]/div/a/i")).click();
	 * Thread.sleep(500);
	 * getDriver().findElement(By.linkText("View Mapping Details")).click();
	 * Thread.sleep(500);
	 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
	 * "loader"), "Please wait..."));
	 * 
	 * TestUtils.assertSearchText("XPATH", "//tr[2]/td[2]", macAddress);
	 * getDriver().findElement(By.xpath("//a/button")).click(); Thread.sleep(500);
	 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
	 * "loader"), "Please wait..."));
	 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
	 * "dataTables_processing"), "Processing..."));
	 * 
	 * }
	 */
	@Test(groups = { "Regression" })
	public void searchByDeviceType() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String deviceType = "GLO-HH";
		TestUtils.testTitle("Filter by device type: " + deviceType);

		Thread.sleep(500);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(deviceType);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try{
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td", "No data available in table");
		}catch (Exception e){
			getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[10]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.linkText("View Mapping Details")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			String deviceT = getDriver().findElement(By.xpath("//tr[3]/td[2]")).getText();
			if (deviceT.contains(deviceType)) {
				testInfo.get().log(Status.INFO, deviceType + " found");
			} else {
				testInfo.get().log(Status.INFO, "not found");
			}
			getDriver().findElement(By.xpath("//a/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			Thread.sleep(500);
		}
	}

	@Test(groups = { "Regression" })
	public void searchByDeviceStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		
		TestUtils.testTitle("Filter by Device status");

		Thread.sleep(500);
		
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();

		// Filter by Blacklist
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("BLACKLISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-inactive", "BLACKLISTED");

		// Filter by Whitelist
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "WHITELISTED");
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDealer(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("kitMapping");

		String dealerName2 = (String) envs.get("dealerName2");

		TestUtils.testTitle("Filter by dealer name: " + dealerName2);

		Thread.sleep(500);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName2);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[4]", dealerName2); 

	}

	@Test(groups = { "Regression" })
	public void searchByApprovalStatus() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		
		TestUtils.testTitle("Filter by approval status");

		Thread.sleep(500);
		
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		// Filter by Pending
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try{
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td", "No data available in table");
		}catch (Exception e){
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[9]", "PENDING");
		}


		// Filter by Approved
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try{
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td", "No data available in table");
		}catch (Exception e){
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[9]", "APPROVED");
		}


		// Filter by Disapproved
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("DISAPPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try{
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td", "No data available in table");
		}catch (Exception e){
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[9]", "DISAPPROVED");
		}



	}

//	  @Parameters ({"testEnv"})
//	  
//	  @Test (groups = { "Regression" }) 
	public void approveNewAgentMapping(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");

		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[2]", kitTag);
		String status = getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[6]/span"))
				.getText();
		if (status.equalsIgnoreCase("PENDING")) {
			getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[10]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.linkText("View Mapping Details")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-warning.float-right"))
					.size();
			testInfo.get().info("Number of pending approval: " + pendingApproval);

			// approve
			TestUtils.testTitle("Click APPROVE button without supplying feedback field.");

			Thread.sleep(500);
			getDriver().findElement(By.name("resolution")).click();
			getDriver().findElement(By.name("resolution")).clear();
			getDriver().findElement(By.id("approveRequest")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "required", "Required");

			TestUtils.testTitle("Supply Required field and Click APPROVE button");

			Thread.sleep(500);
			for (int i = 1; i <= pendingApproval; i++) {
				if(i==1) {
				approve();
				}
				else {
					wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
							"Processing..."));
					Thread.sleep(500);
					getDriver().findElement(By.name("kitTagParam")).clear();
					getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
					getDriver().findElement(By.name("deviceIdSearchParam")).clear();
					if (!getDriver().findElement(By.name("usernameParam")).isDisplayed()) {
						getDriver().findElement(By.id("advancedBtn")).click();
					}
					getDriver().findElement(By.name("usernameParam")).clear();
					getDriver().findElement(By.name("macAddressSearchParam")).clear();
					if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
						getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
					}
					getDriver().findElement(By.id("searchBtn")).click();
					Thread.sleep(500);
					wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
							"Processing..."));
					getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[10]/div/a/i")).click();
					Thread.sleep(500);
					getDriver().findElement(By.linkText("View Mapping Details")).click();
					Thread.sleep(500);
					wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
					approve();
				}
			}

			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));

			getDriver().findElement(By.name("kitTagParam")).clear();
			getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			TestUtils.assertSearchText("XPATH", "//table[@id='assignmentDetail']/tbody/tr/td[6]/span", "APPROVED");
			String assignedAgent = getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[8]"))
					.getText();
			String name = "Assigned Agent";
			String NA = "N/A";
			try {
				Assert.assertNotEquals(assignedAgent, NA);
				testInfo.get().log(Status.INFO, name + " : " + assignedAgent);
			} catch (Error e) {
				testInfo.get().error(name + " : " + assignedAgent);
			}
		} else {
			testInfo.get().error("Kit is " + status);
		}

	}

	public static void approve() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-warning.float-right")).size();
		testInfo.get().info("Number of pending approval: " + pendingApproval);
		getDriver().findElement(By.id("resolution")).click();
		getDriver().findElement(By.id("resolution")).clear();
		getDriver().findElement(By.id("resolution")).sendKeys("Seamfix Sanity Test");
		getDriver().findElement(By.id("approveRequest")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to Approve this request?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.swal2-content")));
		Thread.sleep(500);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit Mapping Request was approved successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			Thread.sleep(1000);
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	}

}
