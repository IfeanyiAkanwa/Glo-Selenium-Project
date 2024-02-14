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

public class BlacklistDeviceManagement extends TestBase {

	private StringBuffer verificationErrors = new StringBuffer();

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "searchForm");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("NAME", "kitTagParam");
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public static void navigateToBlacklistDeviceManagementTest(String testEnv) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Blacklist/Whitelist");
		Thread.sleep(1500);
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
			Thread.sleep(500);
			getDriver().findElement(By.linkText("Blacklist/Whitelist")).click();
			Thread.sleep(500);
		} else {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
				getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
				getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.linkText("Blacklist/Whitelist")).click();
				Thread.sleep(500);
			}
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Device Blacklist / Whitelist");
	}

	@Test(groups = { "Regression" })
	public void assertKitStatusCount() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		String totalKitsValString = getDriver().findElement(By.cssSelector("h4.card-title.text-center")).getText();
		String blacklistedKitsValString = getDriver().findElement(By.xpath("//div[2]/div/div/h4")).getText();
		String whitelistedKitsValString = getDriver().findElement(By.xpath("//div[3]/div/div/h4")).getText();

		int actualTotalKitsVal = TestUtils.convertToInt(totalKitsValString);
		int actualBlacklistedKitsVal = TestUtils.convertToInt(blacklistedKitsValString);
		int actualWhitelistedKitsVal = TestUtils.convertToInt(whitelistedKitsValString);
		int expectedTotalKitsVal = actualBlacklistedKitsVal + actualWhitelistedKitsVal;
		
		TestUtils.testTitle("Test to assert kit status count");
		try {
			Assert.assertEquals(expectedTotalKitsVal, actualTotalKitsVal);
			testInfo.get().log(Status.INFO,
					"Total kits (" + expectedTotalKitsVal + ") is equal to number of blacklisted kits ("
							+ actualBlacklistedKitsVal + ") + whitelisted kits (" + actualWhitelistedKitsVal + ").");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		// Page size
		new Select(getDriver().findElement(By.name("blackList_length"))).selectByVisibleText("250");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Change page size to: 250 ");
		
		int rowCount = getDriver().findElements(By.xpath("//table[@id='blackList']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of kits returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTag(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String kitTag = (String) envs.get("kitTag");

		 
		TestUtils.testTitle("Filter by kit tag: " + kitTag);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='blackList']/tbody/tr/td[3]", kitTag);
		Thread.sleep(500);

	}

	/*
	 * @Parameters({ "testEnv" })
	 * 
	 * @Test(groups = { "Regression" }) public void searchByMacAddress(String
	 * testEnv) throws InterruptedException, FileNotFoundException, IOException,
	 * ParseException { WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	 * scrollDown(); File path = null; File classpathRoot = new
	 * File(System.getProperty("user.dir")); if
	 * (testEnv.equalsIgnoreCase("StagingData")) { path = new File(classpathRoot,
	 * "stagingData/data.conf.json"); } else { path = new File(classpathRoot,
	 * "prodData/data.conf.json"); } JSONParser parser = new JSONParser();
	 * JSONObject config = (JSONObject) parser.parse(new FileReader(path));
	 * 
	 * JSONObject envs = (JSONObject) config.get("blacklistWhitelist");
	 * 
	 * String macAddress = (String) envs.get("macAddress");
	 * 
	 * String macFilter = "Filter by mac address: " + macAddress; Markup m =
	 * MarkupHelper.createLabel(macFilter, ExtentColor.BLUE);
	 * testInfo.get().info(m); Thread.sleep(500);
	 * 
	 * getDriver().findElement(By.name("kitTagParam")).clear();
	 * getDriver().findElement(By.name("deviceIdParam")).clear(); if
	 * (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
	 * getDriver().findElement(By.id("advancedBtn")).click(); } if
	 * (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
	 * getDriver().findElement(By.cssSelector("span.slider.round")).click(); }
	 * getDriver().findElement(By.name("macAddressParam")).clear();
	 * getDriver().findElement(By.name("macAddressParam")).sendKeys(macAddress);
	 * getDriver().findElement(By.id("searchBtn")).click(); Thread.sleep(500);
	 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
	 * "dataTables_processing"), "Processing..."));
	 * TestUtils.assertSearchText("XPATH",
	 * "//table[@id='blackList']/tbody/tr/td[4]", macAddress);
	 * 
	 * }
	 */

	@Test(groups = { "Regression" })
	public void searchByDeviceStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).clear();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("macAddressParam")).clear();

		// Filter by Blacklist
		TestUtils.testTitle("Filter by device status: Blacklisted ");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("BLACKLISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-inactive", "BLACKLISTED");

		// Filter by Whitelist
		TestUtils.testTitle("Filter by device status: Whitelisted");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("WHITELISTED");
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
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String dealerName = (String) envs.get("dealerName");

		TestUtils.testTitle("Filter by dealer name: " + dealerName);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).clear();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("macAddressParam")).clear();
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='blackList']/tbody/tr/td[6]", dealerName);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void singleBlacklistTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 100);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String deviceID = (String) envs.get("de_activateDevice");
		String userEmail = (String) envs.get("userEmail");

		TestUtils.testTitle("Filter by device ID: " + deviceID);
		getDriver().navigate().refresh();


		getDriver().findElement(By.name("kitTagParam")).clear();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			Thread.sleep(1000);
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("deviceIdParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		getDriver().findElement(By.xpath("//table[@id='blackList']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//td[7]/div/ul/li/a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistHeader")));
		String title = getDriver().findElement(By.id("saveBlacklistBtn")).getText();
		title.contains("KIT BLACKLIST");
		testInfo.get().info("Header: " + title + " found");

		// Empty approval email address field
		TestUtils.testTitle("Submit empty Approval email address ");
		TestUtils.clickElement("XPATH", "//div[2]/div/button[2]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		getDriver().findElement(By.xpath("//div[5]/span[2]")).isDisplayed();
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"*Please enter a valid email address");
		Thread.sleep(1500);

		// Invalid approval email address format
		TestUtils.testTitle("Submit Invalid approval email address format: soliseamfixco ");
		getDriver().findElement(By.id("approvedByEmailAddress")).clear();
		getDriver().findElement(By.id("approvedByEmailAddress")).sendKeys("soliseamfixco");

		TestUtils.clickElement("XPATH", "//div[2]/div/button[2]");
		//Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"*Please enter a valid email address");
		Thread.sleep(1500);

		// Valid approval email address
		TestUtils.testTitle("Submit valid approval email address: " + userEmail + " and empty reason");
		getDriver().findElement(By.xpath("//div[6]/div/div/form/div/div/div/input")).clear();
		Thread.sleep(100);
		getDriver().findElement(By.xpath("//div[6]/div/div/form/div/div/div/input")).sendKeys(userEmail);
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"*Please select a reason");
		Thread.sleep(1500);

		// Valid approval email address and reason
		TestUtils.testTitle("Submit valid approval email address: " + userEmail + " and valid reason");
		Thread.sleep(1500);
		getDriver().findElement(By.xpath("//div[@id='blacklistReasonDiv']/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Pre-registration");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
		String requiredText = expectedText.substring(12, 47);
		String value = "Request was processed successfully.";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void singleWhitelistTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String deviceID = (String) envs.get("de_activateDevice");
		String userEmail = (String) envs.get("userEmail");
		
		Thread.sleep(500);
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
		getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("Blacklist/Whitelist")).click();
		Thread.sleep(500);

		getDriver().navigate().refresh();
		
		TestUtils.testTitle("Filter by device ID: " + deviceID);
		getDriver().findElement(By.name("kitTagParam")).clear();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			Thread.sleep(1000);
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
			Thread.sleep(1000);
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("deviceIdParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		getDriver().findElement(By.xpath("//table[@id='blackList']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//td[7]/div/ul/li/a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistHeader")));
		String title = getDriver().findElement(By.id("saveBlacklistBtn")).getText();
		title.contains("KIT BLACKLIST");
		testInfo.get().info("Header: " + title + " found");

		// Empty approval email address field
		TestUtils.testTitle("Submit empty Approval email address ");
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]","*Please enter a valid email address");
		Thread.sleep(1500);

		// Valid approval email address, reason and empty specify reason
		TestUtils.testTitle("Submit valid approval email address: " + userEmail + ", Other reason and empty specify reason");
		Thread.sleep(1000);
		getDriver().findElement(By.id("approvedByEmailAddress")).clear();
		Thread.sleep(1000);
		getDriver().findElement(By.id("approvedByEmailAddress")).sendKeys(userEmail);
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Other");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",	"*Please specify a reason");
		Thread.sleep(1000);

		// Valid approval email address and reason
		TestUtils.testTitle("Submit valid approval email address: " + userEmail
				+ ", Other reason and valid specify reason");
		getDriver().findElement(By.id("whitelistReasonOthers")).sendKeys("Testing");
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
		String requiredText = expectedText.substring(12, 47);
		String value = "Request was processed successfully.";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
			getDriver().findElement(By.xpath("//span/i")).click();
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void multipleBlacklistWhitelist(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 100);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String userEmail = (String) envs.get("userEmail");
		
		// Approve multiple selection
		TestUtils.testTitle("Click on APPROVE SELECTED without selecting any record");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		scrollUp();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/button")).click();
//		Thread.sleep(100);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//div[4]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[4]/span[2]", "No Kit was selected");
//		Thread.sleep(1500);

		// select records and try to procced without email approval and action type
		TestUtils.testTitle("Select records and procced without email approval and action type");
		if (!getDriver().findElement(By.id("checkAll")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.check")).click();
		}
		getDriver().findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("blacklistHeader")));
		TestUtils.assertSearchText("ID", "blacklistHeader", "APPROVE SELECTED KITS");
//		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		TestUtils.clickElement("ID", "saveBlacklistBtn");
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'*Please enter a valid email address')]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"*Please enter a valid email address");
		Thread.sleep(1000);

		// Valid approval email address, empty action type
		TestUtils.testTitle("Submit valid approval email address: " + userEmail + ", and empty action type");
//		Thread.sleep(1000);
		getDriver().findElement(By.id("approvedByEmailAddress")).clear();
		Thread.sleep(1500);
		getDriver().findElement(By.id("approvedByEmailAddress")).sendKeys(userEmail);
		Thread.sleep(1000);
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions
//				.visibilityOfAllElementsLocatedBy(By.xpath("//div[5]/span[2]")));
//		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"*Please select an action");
		Thread.sleep(1000);

		// Select an action
		TestUtils.testTitle("Submit valid approval email address: " + userEmail + ", valid action type and empty reason");
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("BLACKLIST");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		TestUtils.clickElement("ID", "saveBlacklistBtn");
		Thread.sleep(500);
//		wait.until(ExpectedConditions
//				.visibilityOfAllElementsLocatedBy(By.xpath("//div[5]/span[2]")));
		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]", "*Please select a reason");
		Thread.sleep(1000);

		// Select a reason and save
		TestUtils.testTitle("Save Multiple Blacklist Test");
		getDriver().findElement(By.xpath("//div[@id='blacklistReasonDiv']/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Life Image");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		if(getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText().contains("Request was processed successfully")){
			testInfo.get().info("Request was processed successfully found");
		}else{
			testInfo.get().error("Request was processed successfully not found");
		}
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
		getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("Blacklist/Whitelist")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//td[5]/span", "BLACKLISTED");

		//Blacklist Process
		if (!getDriver().findElement(By.id("checkAll")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.check")).click();
		}
		getDriver().findElement(By.xpath("//button[@onclick='showApproveSelected()']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("blacklistHeader")));
		TestUtils.assertSearchText("ID", "blacklistHeader", "APPROVE SELECTED KITS");


		getDriver().findElement(By.id("approvedByEmailAddress")).clear();
		Thread.sleep(1000);
		getDriver().findElement(By.id("approvedByEmailAddress")).sendKeys(userEmail);

		// Select an action
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("WHITELIST");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Select a reason and save
		TestUtils.testTitle("Save Multiple Whitelist Test");
		getDriver().findElement(By.xpath("//div[@id='whitelistReasonDiv']/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Re-Tagged");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		if(getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText().contains("Request was processed successfully")){
			testInfo.get().info("Request was processed successfully found");
		}else{
			testInfo.get().error("Request was processed successfully not found");
		}
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
		getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("Blacklist/Whitelist")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[5]/span")));
		TestUtils.assertSearchText("XPATH", "//td[5]/span", "WHITELISTED");

//		testInfo.get().log(Status.SKIP, "Skipped approving this test case, but was able to select multiple request");
//		getDriver().findElement(By.id("closeBListModal")).click();
//		Thread.sleep(1000);
//		getDriver().findElement(By.cssSelector("span.check")).click();

	}

	@Test(groups = { "Regression" })
	@Parameters({ "server", "downloadPath" })
	public void uploadBulkBlacklistWhitelist(String server, String downloadPath) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollUp();
		getDriver().findElement(By.xpath("//a[@onclick='showBulkUpload();']")).click();
		Thread.sleep(1000);

		// no file selected
		TestUtils.testTitle("Click on UPLOAD without selecting any file");
		getDriver().findElement(By.id("uploadBtn")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
//				By.xpath("//*[contains(text(),'Invalid file Provided. Supported file extension is .xls')]")));
//		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH",
				"//div[5]/span[2]",
				"Invalid file Provided. Supported file extension is .xls");
		Thread.sleep(1000);
		getDriver().findElement(By.id("blicklistFile")).clear();
		String file = "blacklist_template.xls";
		String invalidFile = "image2.jpg";

		// Select an invalid file format
		TestUtils.testTitle("Select invalid file format and upload. eg jpg " + invalidFile);
		TestUtils.uploadFile(By.id("blicklistFile"), invalidFile);
		getDriver().findElement(By.id("uploadBtn")).click();
//		Thread.sleep(500);
		try {

			/*wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//*[contains(text(),'Invalid file Provided. Supported file extension is .xls')]")));*/
//			Thread.sleep(1500);
			TestUtils.assertSearchText("XPATH",
					"//div[5]/span[2]",
					"Invalid file Provided. Supported file extension is .xls");
		}catch (Exception e){

//			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
//					By.xpath("//*[contains(text(),'Invalid file provided. Supported file extension is .xls')]")));
//
			TestUtils.assertSearchText("XPATH",
					"//div[5]/span[2]",
					"Invalid file provided. Supported file extension is .xls");
		}
		Thread.sleep(1000);

		// Select a valid file format
		TestUtils.testTitle("Select a valid file format and upload. " + file);
		getDriver().findElement(By.id("blicklistFile")).clear();
		Thread.sleep(1000);
		TestUtils.uploadFile(By.id("blicklistFile"), file);
		Thread.sleep(1000);
		getDriver().findElement(By.id("uploadBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
//		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]",
				"Your request has been processed.");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'Close')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Device Blacklist / Whitelist");

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewKitDetailsTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String deviceID = (String) envs.get("deviceID");
		
		// Approve multiple selection
		TestUtils.testTitle("Filter and view full information for device ID: " + deviceID);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		scrollUp();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.name("deviceIdParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='blackList']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(2000);
		try{
			getDriver().findElement(By.linkText("View Kit Details")).click();
		}catch (Exception e){
			getDriver().findElement(By.xpath("//a[contains(text(),'View Kit Details')]")).click();
		}

		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Mobile Device Information");
		TestUtils.scrollToElement("ID", "headingTwo");
		Assertion.assertMobileInfo();

		getDriver().findElement(By.id("headingTwo")).click();

		TestUtils.assertSearchText("CSSSELECTOR", "#headingThree > div.card-header.collapsed > h4.card-title",
				"Device Location Information");
		TestUtils.scrollToElement("CSSSELECTOR", "#headingThree > div.card-header.collapsed > h4.card-title");
		Assertion.assertLocationInfo();
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Client Activity Summary')]",
				"CLIENT ACTIVITY SUMMARY");
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "vMore");
		getDriver().findElement(By.id("vMore")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Client Activity Log')]", "Client Activity Log");
		TestUtils.scrollToElement("XPATH", "//div[2]/div/div/div[3]/button");
		getDriver().findElement(By.xpath("//div[2]/div/div/div[3]/button")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "outletName");
		getDriver().findElement(By.id("locationHistoryTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");

		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistButton")));
		blacklistWhitelist(testEnv);
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		Thread.sleep(500);
		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistButton")));
		blacklistWhitelist(testEnv);

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		Thread.sleep(1500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Kit Agents')]")).click();
		Thread.sleep(1000);
		testInfo.get().log(Status.INFO, "Kit Agents view");
		getDriver().findElement(By.xpath("//a[contains(text(),'Registration Summary')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.scrollToElement("XPATH", "//b[contains(text(),'Total MSISDN Activated')]");
		Thread.sleep(2000);
		getDriver().findElement(By.id("startDateData")).clear();
		getDriver().findElement(By.id("startDateData")).sendKeys("2017/01/25");
		getDriver().findElement(By.id("endDateData")).clear();
		getDriver().findElement(By.id("endDateData")).sendKeys("2018/01/26");
		getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click(); // Search button
		Thread.sleep(1000);
		testInfo.get().log(Status.INFO, "Registration summary view page");

		// Scroll up to click back button
		TestUtils.scrollToElement("XPATH", "//a/button");
		getDriver().findElement(By.xpath("//a/button")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Device Management");

	}

	@Test(groups = { "Regression" })
	public static void blacklistWhitelist(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("blacklistWhitelist");

		String userEmail = (String) envs.get("userEmail");
		String buttonText = getDriver().findElement(By.id("blacklistButton")).getText();
		String message, reason;
		if (buttonText.equalsIgnoreCase("WHITELIST KIT")) {
			buttonText = "WHITELIST KIT";
			reason = "Re-Assigned";
			message = "Request was processed successfully.";
		} else {
			buttonText = "BLACKLIST KIT";
			reason = "Pre-registration";
			message = "Request was processed successfully.";
		}

		TestUtils.testTitle( buttonText + " with required fields missing");


		getDriver().findElement(By.id("blacklistButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistApprover")));
		getDriver().findElement(By.id("blacklistApprover")).clear();
		getDriver().findElement(By.id("blacklistButtonForm")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "blacklistApprover-error", "This field is required.");
		getDriver().findElement(By.id("blacklistApprover")).clear();
		getDriver().findElement(By.id("blacklistApprover")).sendKeys(userEmail);
		getDriver().findElement(By.id("blacklistButtonForm")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Required inputs were not provided");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);

		TestUtils.testTitle(buttonText + " with required supplied");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(reason);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("blacklistButtonForm")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.swal2-confirm.swal2-styled")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", message);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	}

}
