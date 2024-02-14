package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class kitIssueLog extends TestBase {

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "kitIssue");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("NAME", "searchData");
	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void navigateToKitIssueLogTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to kit issue log");
		try {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1232Issue Resolution\"] > p");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("a[name=\"1232Issue Resolution\"] > p")));
			getDriver().findElement(By.cssSelector("a[name=\"1232Issue Resolution\"] > p")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.name("2079Kit Issue Log")).click();
		} catch (Exception e) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"7883203242Issue Resolution\"] > p");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("a[name=\"7883203242Issue Resolution\"] > p")));
			getDriver().findElement(By.cssSelector("a[name=\"7883203242Issue Resolution\"] > p")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.name("7883203244Kit Issue Log")).click();
		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Kits Issues");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Kit Related Issues");

	}

	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("kitIssue_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Change page size to: 50");

		Thread.sleep(1000);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='kitIssue']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void viewDetail() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//table[@id='kitIssue']/tbody/tr/td[11]/div/a/i")));
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("arguments[0].click()",
				getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[11]/div/a/i")));
//		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[11]/div/a/i")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View details')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'View details')]")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("closeModal")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "Resolve Kit Issue");
		Assertion.assertKitIssueViewDetailCAC();
		TestUtils.scrollToElement("ID", "closeModal");

		// Click close button
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(1000);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByIssueID(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");
		String issueID = (String) envs.get("issueID");

		TestUtils.testTitle("Test to search by issueId: " + issueID);

		Thread.sleep(1000);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(issueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[2]", issueID);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");
		String kitTag = (String) envs.get("kitTag");

		TestUtils.testTitle("Filter by kit tag: " + kitTag);
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[4]", kitTag);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");
		String deviceID = (String) envs.get("deviceID");
		
		TestUtils.testTitle("Filter by Device ID: " + deviceID);
		try{
			getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		}catch (Exception ee){

		}
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("deviceIdParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary",
				"Resolve Kit Issue");
		TestUtils.assertSearchText("ID", "device_id", deviceID);
		TestUtils.scrollToElement("ID", "closeModal");

		// Click close button
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(1000);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByIssueSummary(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");
		String summaryWhitelist = (String) envs.get("summaryWhitelist");
		String summaryBlacklist = (String) envs.get("summaryBlacklist");
		String summaryUnableToLogin = (String) envs.get("summaryUnableToLogin");
		String summaryDeviceLicensing = (String) envs.get("summaryDeviceLicensing");
		String summaryOtp = (String) envs.get("summaryOtp");
		String summaryFingerprint = (String) envs.get("summaryFingerprint");

		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("deviceIdParam")).clear();

		// White list Kit
		TestUtils.testTitle("Test to Search By Status " + summaryWhitelist);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryWhitelist);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryWhitelist);

		// Blacklist Kit
		TestUtils.testTitle("Search By Status " + summaryBlacklist);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryBlacklist);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryBlacklist);

		// Unable to Login
		TestUtils.testTitle("Search By Status " + summaryUnableToLogin);
		
		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryUnableToLogin);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryUnableToLogin);

		// Device Licensing
		TestUtils.testTitle("Search By Status " + summaryDeviceLicensing);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryDeviceLicensing);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryDeviceLicensing);

		// OTP
		TestUtils.testTitle("Search By Status " + summaryOtp);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryOtp);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryOtp);

		// Others
		TestUtils.testTitle("Search By Status " + summaryFingerprint);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(summaryFingerprint);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[5]", summaryFingerprint);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByResolutionStatus(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		// Resolved status
		TestUtils.testTitle("Test to Search By Status Resolved");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-active", "RESOLVED");

		// Pending status
		TestUtils.testTitle("Test to Search By Status pending");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");

		// Returned B2B Status
		TestUtils.testTitle("Test to Search By Status Returned B2B");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Returned Back to Business");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "RETURNB2B");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td", "No data available in table");

			
		}

		// Rejected Status
		TestUtils.testTitle("Test to Search By Status Rejected");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Rejected");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[7]/span", "REJECTED");

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByDate(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");
		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		TestUtils.testTitle("Test to search by date; start date: " + startDate + "  end date : " + endDate);

		Thread.sleep(1000);

		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate); // Start date DD/MM/YYYY format
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate); // End date DD/MM/YYYY format
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[11]")).getText();
		testInfo.get().info("Date returned " + table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date);
		Thread.sleep(1000);


		TestUtils.testTitle("Test to search by date; start date: " + startDate + "  end date : " + endDate +" and Status: RESOLVED");

		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate); // Start date DD/MM/YYYY format
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate); // End date DD/MM/YYYY format

		// Pending status
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String table_Date2 = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[11]")).getText();
		testInfo.get().info("Date returned " + table_Date2);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date2);
		TestUtils.assertSearchText("XPATH", "//td[7]", "RESOLVED");

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void resolveKitIssuePendingRequestTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("kitIssueLog");

		String kitTag = (String) envs.get("kitTag");

		try{
			getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		}catch (Exception ee){

		}

		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		TestUtils.testTitle("Test to Filter by kit tag: " + kitTag + " and resolve kit issue with status pending");
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		Thread.sleep(2000);

		// Pending status
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary",
				"Resolve Kit Issue");
		TestUtils.scrollToElement("ID", "closeModal");

		getDriver().findElement(By.id("resolveIssue")).click(); // Click resolve button
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "required", "Required");

		getDriver().findElement(By.id("resolution")).sendKeys("Seamfix test");
		getDriver().findElement(By.id("resolveIssue")).click(); // Click resolve button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.swal2-confirm.swal2-styled")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Issue has been successfully resolved.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchBySLAStatus(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		Thread.sleep(1000);
		try{
			getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		}catch (Exception ee){

		}
		getDriver().findElement(By.id("searchData")).clear();
		getDriver().findElement(By.id("deviceIdParam")).clear();
		Thread.sleep(1000);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(1000);
		if (!getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
			// Verify SLA status is visible on Kit Related Issues table
		TestUtils.testTitle("Verify that the SLA status is displayed on the Kit Related Issues table");

		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "th._sla_status.sorting_disabled", "SLA Status");

		// Verify SLA status is displayed on Kit issues View details
		TestUtils.testTitle("Verify that the SLA status is displayed on Kit issues view details");
		if (!getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");

		}
		getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr[2]/td/ul/li/span[2]/div/ul/li/a")).click();
	//	getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("XPATH",
				"//div[@id='resolveKitIssue']/div/form/div/div[2]/div/div/table/tbody/tr[10]/td/b", "SLA Status");
		TestUtils.scrollToElement("ID", "closeModal");
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(1000);

		// Verify that user is able to search by SLA status (On Target)
		TestUtils.testTitle("Verify that user is able to search for a kit by SLA Status (On Target)");

		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[8]", "On Target");
			verifySlaStatus();

		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td", "No data available in table");

		}
		// Verify that user is able to search by SLA status (Warning)
		TestUtils.testTitle("Verify that user is able to search for a kit by SLA Status (Warning)");

		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li[2]")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[8]", "Warning");
			verifySlaStatus();

		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td", "No data available in table");

		}

		// Verify that user is able to search by SLA status (Breached)
		TestUtils.testTitle("Verify that user is able to search for a kit by SLA Status (Breached)");

		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li[3]")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[8]", "Breached");
			verifySlaStatus();

		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td", "No data available in table");

		}

		Thread.sleep(1000);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		// Clear SLA status field

		String resolutionStatus;
		TestUtils.testTitle("Verify that View details modal is correctly populated when the Resolution status is Resolved");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-active", "RESOLVED");
		resolutionStatus = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[7]/span")).getText();
		verifyViewDetailsModal(resolutionStatus);

		TestUtils.testTitle("Verify that View details modal is correctly populated when the Resolution status is Pending");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
		resolutionStatus = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[7]/span")).getText();
		verifyViewDetailsModal(resolutionStatus);

		/*String viewDetailsModalForReturnedB2BResolutionStatus = "Verify that View details modal is correctly populated when the Resolution status is Returned B2B";
		Markup vb = MarkupHelper.createLabel(viewDetailsModalForReturnedB2BResolutionStatus, ExtentColor.BLUE);
		testInfo.get().info(vb);
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Returned Back to Business");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "RETURNB2B");
		resolutionStatus = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[7]/span")).getText();
		verifyViewDetailsModal(resolutionStatus);*/

		TestUtils.testTitle("Verify that View details modal is correctly populated when the Resolution status is Rejected");

		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Rejected");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[7]/span", "REJECTED");
		resolutionStatus = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[7]/span")).getText();
		verifyViewDetailsModal(resolutionStatus);

	}

	public void verifyViewDetailsModal(String resolutionStatus) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		if (!getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}
		getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr[2]/td/ul/li/span[2]/div/ul/li/a")).click();
		//TestUtils.clickElement("XPATH", "//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i");
	
	//	getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='resolveKitIssue']/div/form/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='resolveKitIssue']/div/form/div/div/h4", "Resolve Kit Issue");
		Thread.sleep(1000);
		Assertion.assertViewDetailsModalIssueLogCACAdmin(resolutionStatus);
		TestUtils.scrollToElement("ID", "closeModal");
		if (!resolutionStatus.equalsIgnoreCase("Pending")) {
			TestUtils.testTitle("Verify that View details modal does not show the Resolve button or Return B2B button when the resolution status is "
					+ resolutionStatus);

			if (getDriver().findElement(By.id("returnB2B")).isDisplayed()
					|| getDriver().findElement(By.id("resolveIssue")).isDisplayed()
					|| getDriver().findElement(By.id("rejectIssue")).isDisplayed()) {
				testInfo.get()
						.fail("ReturnB2B/Resolve/Reject button is displayed on view details when resolution status is: "
								+ resolutionStatus);
			}

		} else {
			TestUtils.testTitle("Verify that View details modal shows the Resolve button and Return B2B button when the resolution status is "
					+ resolutionStatus);

			try {
				TestUtils.assertSearchText("ID", "resolveIssue", "RESOLVE");
				TestUtils.assertSearchText("ID", "closeModal", "CLOSE");
				//TestUtils.assertSearchText("ID", "returnB2B", "RETURN B2B");
				TestUtils.assertSearchText("ID", "rejectIssue", "REJECT");
			} catch (Exception e) {
				testInfo.get().fail(
						"The Resolve/ReturnB2B button is not present in the view details when the Resolution status is Pending");
			}
		}
		// Click close button
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(1000);

	}

	public void verifySlaStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		String slaStatus = getDriver().findElement(By.xpath("//td[8]")).getText();
		TestUtils.testTitle("Test to view details to verify the SLA status on the 'View logged issue' modal");

		TestUtils.clickElement("XPATH", "//tbody/tr[1]/td[13]/div[1]/a[1]/i[1]");
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='resolveKitIssue']/div/form/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='resolveKitIssue']/div/form/div/div/h4", "Resolve Kit Issue");
		Thread.sleep(1000);
		TestUtils.scrollToElement("ID", "slaStatus");
		if (slaStatus.equals("On Target")) {
			TestUtils.assertSearchText("ID", "slaStatus", "ON_TARGET");
		} else if (slaStatus.equals("Warning")) {
			TestUtils.assertSearchText("ID", "slaStatus", "WARNING");
		} else if (slaStatus.equals("Breached")) {
			TestUtils.assertSearchText("ID", "slaStatus", "BREACHED");
		}

		// Click close button
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(1000);
	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByStateOfDeployment(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		// Search by State of Deployment
		TestUtils.testTitle("Test to Verify that user can search by State of Deployment");

		String[] states = { "OYO", "LAGOS", "ABIA"};
		for (int i = 0; i < states.length; i++) {
			// Search By ABIA
			testInfo.get().log(Status.INFO, "Search By: " + states[i]);
			getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
			getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(states[i]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
			getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(3000);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));

			try {
				String state = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[9]")).getText();
				TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[9]", state);
			} catch (Exception e) {
				TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td",
						"No data available in table");
			}
		}

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByDealer(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		// Search by Dealer
		TestUtils.testTitle("Tes to Verify that user can search by Dealer");

		String[] dealers = { "SEAMFIXQA SUPPORT", "1000 MAKS ENTERPRISE", "ABC TELECOM" };
		for (int i = 0; i < dealers.length; i++) {
			// Search By Dealers
			testInfo.get().log(Status.INFO, "<b> Search By: " + dealers[i] + "</b>");
			getDriver().findElement(By.xpath("//div[3]/div/div[3]/div/span/span/span")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
			getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealers[i]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
			getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(3000);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));

			try {
				String dealer = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[3]")).getText();
				TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[3]", dealer);
			} catch (Exception e) {
				TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td",
						"No data available in table");
			}
		}

	}

	/*@Test(groups = { "Regression" })
	@Parameters({ "downloadPath", "server", "testEnv" })
	public void verifyReturnB2B(String testEnv, String server, String downloadPath)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		String kitTag1 = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[4]")).getText();
		// View B2B
		String viewB2B = "Verify that user with the right privilege can view and Return B2B on Kit Related Issues table";
		Markup v = MarkupHelper.createLabel(viewB2B, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'Return B2B')]")).click();
		Thread.sleep(1000);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='requestForB2bForm']/div/div/div")));
		TestUtils.assertSearchText("XPATH", "//form[@id='requestForB2bForm']/div/div/div", "Kit Tag");
		String kitTag = getDriver().findElement(By.id("kitTag")).getAttribute("value");
		try {
			Assert.assertEquals(kitTag, kitTag1);
		} catch (Exception e) {
			testInfo.get().error(
					"The Kit Tag displayed on the Kit Related Issues table is not equal to what is displayed on the B2B modal");
		}
		// TestUtils.assertSearchText("ID", "kitTag", kitTag);
		getDriver().findElement(By.xpath("//form[@id='requestForB2bForm']/div/div/div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li[4]")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("reason")).click();
		getDriver().findElement(By.id("reason")).sendKeys("Tested");
		Thread.sleep(1000);

		String pic;
		String invalidFile = null;

		if (server.equals("remote-browserStack")) {
			pic = downloadPath + "image2.jpg";
			invalidFile = downloadPath + "Invalidfile.txt";
		} else if (server.equals(remoteJenkins)) {
			pic = downloadPath + "image2.jpg";
			invalidFile = downloadPath + "Invalidfile.txt";

		} else {
			pic = System.getProperty("user.dir") + "\\files\\image2.jpg";
			invalidFile = System.getProperty("user.dir") + "\\files\\Invalidfile.txt";
		}

		// Invalid Picture
		String invalidPic = "Verify error message when user uploads invalid file format";
		Markup ip = MarkupHelper.createLabel(invalidPic, ExtentColor.BLUE);
		testInfo.get().info(ip);
		WebElement input = getDriver().findElement(By.id("kitPicture"));
		input.sendKeys(invalidFile);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kv-avatar-errors-1")));
		String errorMessage = getDriver().findElement(By.cssSelector("#kv-avatar-errors-1")).getText();
		errorMessage = errorMessage.substring(1, errorMessage.length());
		testInfo.get().info(errorMessage + " found");
		getDriver().findElement(By.cssSelector("button.close.kv-error-close > span")).click();
		Thread.sleep(1000);

		// Upload Valid Pic
		String validPic = "Verify that user can proceed with valid picture format";
		Markup vp = MarkupHelper.createLabel(validPic, ExtentColor.BLUE);
		testInfo.get().info(vp);
		input = getDriver().findElement(By.id("kitPicture"));
		input.sendKeys(pic);
		TestUtils.scrollToElement("ID", "submitB2bRequest");
		getDriver().findElement(By.id("submitB2bRequest")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-modal.swal2-show")));
		TestUtils.assertSearchText("XPATH", "//h2", "Return Back to Business");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
		Thread.sleep(1000);

	}*/

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void rejectKitIssuePendingRequestTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		 getDriver().navigate().refresh();
		String kitTag;
		try{
			getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		}catch (Exception ee){

		}
		Thread.sleep(1000);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		try {
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		} catch (Exception e) {

		}
		// Filter by Pending
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		kitTag = getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[4]")).getText();
		TestUtils.testTitle("Test to Reject kit tag (" + kitTag + ") with pending status");
		TestUtils.assertSearchText("XPATH", "//table[@id='kitIssue']/tbody/tr/td[7]/span", "PENDING");

		Thread.sleep(1000);
		if (!getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td[13]/div/a/i")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}

		getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
		
		Thread.sleep(1000);

		getDriver().findElement(By.xpath("//table[@id='kitIssue']/tbody/tr[2]/td/ul/li/span[2]/div/ul/li/a")).click();
	//	getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary",
				"Resolve Kit Issue");
		TestUtils.scrollToElement("ID", "closeModal");

		getDriver().findElement(By.id("rejectIssue")).click(); // Click reject button
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "required", "Required");

		getDriver().findElement(By.id("resolution")).sendKeys("Seamfix test Rejected");
		getDriver().findElement(By.id("rejectIssue")).click(); // Click reject button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.swal2-confirm.swal2-styled")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Issue has been successfully rejected.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Test (groups = { "Regression" })
	public void selectVisibleColumns() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Select Visible Columns')]")));
		getDriver().findElement(By.linkText("Select Visible Columns")).click();
		Thread.sleep(1000);

		// Issue ID Column
		TestUtils.testTitle("Remove Issue ID Column");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[2]/span")));
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Issue ID")) {
			TestUtils.assertSearchText("XPATH", "//th[2]", "Issue ID");
			testInfo.get().error("Issue ID column not removed");

		}else {
			testInfo.get().info("Issue ID column removed");

		}
		TestUtils.testTitle("Add Issue ID Column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[2]", "Issue ID");
		Thread.sleep(1000);

		//Dealer Column
		TestUtils.testTitle("Remove Dealer Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Dealer")) {
			TestUtils.assertSearchText("XPATH", "//th[3]", "Dealer");
			testInfo.get().error("Dealer column not removed");

		}else {
			testInfo.get().info("Dealer column removed");

		}
		TestUtils.testTitle("Add Dealer Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[3]", "Dealer");
		Thread.sleep(1000);

		// Kit Tag Column
		TestUtils.testTitle("Remove Kit Tag Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		if (getDriver().findElement(By.xpath("//th[4]")).getText().contains("Kit Tag")) {
			TestUtils.assertSearchText("XPATH", "//th[4]", "Kit Tag");
			testInfo.get().error("Kit Tag column not removed");

		}else {
			testInfo.get().info("Kit Tag column removed");

		}
		TestUtils.testTitle("Add Kit Tag Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[4]", "Kit Tag");
		Thread.sleep(1000);

		// Issue Summary Column
		TestUtils.testTitle("Remove Issue Summary Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		if (getDriver().findElement(By.xpath("//th[5]")).getText().contains("Issue Summary")) {
			TestUtils.assertSearchText("XPATH", "//th[5]", "Issue Summary");
			testInfo.get().error("Issue Summary column not removed");

		}else {
			testInfo.get().info("Issue Summary column removed");

		}
		TestUtils.testTitle("Add Issue Summary Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[5]", "Issue Summary");
		Thread.sleep(1000);

		// Issue Category Column
		TestUtils.testTitle("Remove Issue Category Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		if (getDriver().findElement(By.xpath("//th[6]")).getText().contains("Issue Category")) {
			TestUtils.assertSearchText("XPATH", "//th[6]", "Issue Category");
			testInfo.get().error("Issue Category column not removed");

		}else {
			testInfo.get().info("Issue Category column removed");

		}
		TestUtils.testTitle("Add Issue Category Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[6]", "Issue Category");
		Thread.sleep(1000);

		// Resolution Status Column
		TestUtils.testTitle("Remove Resolution Status Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();

		if (getDriver().findElement(By.xpath("//th[7]")).getText().contains("Resolution Status")) {
			TestUtils.assertSearchText("XPATH", "//th[7]", "Resolution Status");
			testInfo.get().error("Resolution Status column not removed");

		} else {
			testInfo.get().info("Resolution Status column removed");
		}
		TestUtils.testTitle("Add Resolution Status Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[7]", "Resolution Status");

		// SLA Status Column
		TestUtils.testTitle("Remove SLA Status Column");
		getDriver().findElement(By.xpath("//a[8]/span")).click();
		if (getDriver().findElement(By.xpath("//th[8]")).getText().contains("SLA Status")) {
			TestUtils.assertSearchText("XPATH", "//th[8]", "SLA Status");
			testInfo.get().error("SLA Status column not removed");

		} else {
			testInfo.get().info("SLA Status column removed");
		}
		TestUtils.testTitle("Add SLA Status Column");
		getDriver().findElement(By.xpath("//a[8]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[8]", "SLA Status");

		// State of Deployment Column
		TestUtils.testTitle("Remove State of Deployment Column");
		getDriver().findElement(By.xpath("//a[9]/span")).click();
		if (getDriver().findElement(By.xpath("//th[9]")).getText().contains("State of Deployment")) {
			TestUtils.assertSearchText("XPATH", "//th[9]", "State of Deployment");
			testInfo.get().error("Resolved By column not removed");

		} else {
			testInfo.get().info("State of Deployment column removed");
		}
		TestUtils.testTitle("Add State of Deployment Column");
		getDriver().findElement(By.xpath("//a[9]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[9]", "State of Deployment");

		// Resolved By Column
		TestUtils.testTitle("Remove Resolved By Column");
		getDriver().findElement(By.xpath("//a[10]/span")).click();
		if (getDriver().findElement(By.xpath("//th[10]")).getText().contains("Resolved By")) {
			TestUtils.assertSearchText("XPATH", "//th[10]", "Resolved By");
			testInfo.get().error("Resolved By column not removed");

		} else {
			testInfo.get().info("Resolved By column removed");
		}
		TestUtils.testTitle("Add Resolved By Column");
		getDriver().findElement(By.xpath("//a[10]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[10]", "Resolved By");

		// Date Logged Column
		TestUtils.testTitle("Remove Date Logged Column");
		getDriver().findElement(By.xpath("//a[11]/span")).click();
		if (getDriver().findElement(By.xpath("//th[11]")).getText().contains("Date Logged")) {
			TestUtils.assertSearchText("XPATH", "//th[11]", "Date Logged");
			testInfo.get().error("Date Logged column not removed");

		} else {
			testInfo.get().info("Date Logged column removed");
		}
		TestUtils.testTitle("Add Date Logged Column");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[11]/span")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//th[11]", "Date Logged");

		// Resolution Date Column
		TestUtils.testTitle("Remove Resolution Date Column");
		getDriver().findElement(By.xpath("//a[12]/span")).click();
		if (getDriver().findElement(By.xpath("//th[12]")).getText().contains("Resolution Date")) {
			TestUtils.assertSearchText("XPATH", "//th[12]", "Resolution Date");
			testInfo.get().error("Resolution Date column not removed");

		} else {
			testInfo.get().info("Resolution Date column removed");
		}
		TestUtils.testTitle("Add Resolution Date Column");
		getDriver().findElement(By.xpath("//a[12]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[12]", "Resolution Date");

		// Actions Column
		TestUtils.testTitle("Remove Actions Column");
		getDriver().findElement(By.xpath("//a[13]/span")).click();
		try {
			TestUtils.assertSearchText("XPATH", "//th[13]", "Actions");

		} catch (Exception e) {
			testInfo.get().info("Actions column removed");
		}
		TestUtils.testTitle("Add Actions Column");
		getDriver().findElement(By.xpath("//a[13]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[13]", "Actions");
		getDriver().navigate().refresh();
	}

}
