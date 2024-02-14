package DealerTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class kitIssueLog extends TestBase {
	
	private String issueID;
	private String kitTag;
	private String deviceID;
	
 
 @Parameters({ "testEnv" })
 @BeforeMethod
 public void parseJson(String testEnv) throws IOException, ParseException {
     File path = null;
     File classpathRoot = new File(System.getProperty("user.dir"));
     if (testEnv.equalsIgnoreCase("StagingData")) {
         path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
     } else {
         path = new File(classpathRoot, "prodData/dealerdata.conf.json");
     }
     JSONParser parser = new JSONParser();
     JSONObject config = (JSONObject) parser.parse(new FileReader(path));
     JSONObject envs = (JSONObject) config.get("kit_Issue_log");

     issueID = (String) envs.get("issueID");
     kitTag = (String) envs.get("kitTag");
     deviceID = (String) envs.get("deviceID");
 }
 
 public void scrollDown() throws InterruptedException {
		
		TestUtils.scrollToElement("ID", "dealerKitIssue");
	}

	public void scrollUp() throws InterruptedException {

		TestUtils.scrollToElement("NAME", "searchParam");
	}

	@Test (groups = { "Regression" })
	public void navigateToKitIssueLogTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		TestUtils.testTitle("Navigate to Kit Issue Log");
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			}
		
		try {
			getDriver().findElement(By.name("1040Log Issues")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("1042Kit Issue Log")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("7883203274Log Issues")).click();
		    Thread.sleep(500);
		    getDriver().findElement(By.name("7883203276Kit Issue Log")).click();
		}
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div[4]/div/h4", "Kits Issues");
		
	}
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void downloadReport(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Download kit issue logs");
		// Download pdf
		TestUtils.testTitle("Download Pdf");
		getDriver().findElement(By.xpath("//div[3]/div[2]/a")).click();
		Thread.sleep(500);
		// Download excel
		TestUtils.testTitle("Download Excel");
		getDriver().findElement(By.xpath("//div[2]/a[2]")).click();
		Thread.sleep(500);
	}
	
	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		TestUtils.testTitle("Change page size to: 50");

		new Select(getDriver().findElement(By.name("dealerKitIssue_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='dealerKitIssue']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Test(groups = { "Regression" })
	public void selectVisibleColumns() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		TestUtils.testTitle("Select Visible Columns");
		TestUtils.assertSearchText("XPATH", "//div[2]/a[3]", "Select Visible Columns");
		getDriver().findElement(By.xpath("//div[2]/a[3]")).click();
		Thread.sleep(500);

		// Issue Id Column
		TestUtils.testTitle("Remove Issue Id column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Issue Id")) {
			TestUtils.assertSearchText("XPATH", "//th[2]", "Issue ID");

		}else {
			testInfo.get().info("Issue Id column removed");

		}
		TestUtils.testTitle("Add Issue Id column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[2]", "Issue ID");
		Thread.sleep(500);

		// Kit Tag Column
		TestUtils.testTitle("Remove Kit Tag column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Kit Tag")) {
			TestUtils.assertSearchText("XPATH", "//th[3]", "Kit Tag");

		}else {
			testInfo.get().info("Kit Tag column removed");

		}
		TestUtils.testTitle("Add Kit Tag column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[3]", "Kit Tag");
		Thread.sleep(500);

		
		// Mac Address Column
		TestUtils.testTitle("Remove Mac Address Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		if (getDriver().findElement(By.xpath("//th[4]")).getText().contains("Mac Address")) {
			TestUtils.assertSearchText("XPATH", "//th[4]", "MAC Address");

		}else {
			testInfo.get().info("Mac Address column removed");

		}
		TestUtils.testTitle("Add Mac Address Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[4]", "MAC Address");
		Thread.sleep(500);

		// Issue Summary Column
		TestUtils.testTitle("Remove Issue Summary");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		if (getDriver().findElement(By.xpath("//th[5]")).getText().contains("Issue Summary")) {
			TestUtils.assertSearchText("XPATH", "//th[5]", "Issue Summary");

		}else {
			testInfo.get().info("Issue Summary column removed");

		}
		TestUtils.testTitle("Add Issue Summary Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[5]", "Issue Summary");
		Thread.sleep(500);

		// SLA Status Column
		TestUtils.testTitle("Remove SLA Status Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		if (getDriver().findElement(By.xpath("//th[6]")).getText().contains("SLA Status")) {
			TestUtils.assertSearchText("XPATH", "//th[6]", "SLA Status");

		}else {
			testInfo.get().info("SLA Status column removed");

		}
		TestUtils.testTitle("Add SLA Status Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[6]", "SLA Status");
		Thread.sleep(500);

		// Resolved By Column
		TestUtils.testTitle("Remove Resolved By Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		if (getDriver().findElement(By.xpath("//th[7]")).getText().contains("Resolved By")) {
			TestUtils.assertSearchText("XPATH", "//th[7]", "Resolved By");

		}else {
			testInfo.get().info("Resolved By column removed");

		}
		TestUtils.testTitle("Add Resolved By Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[7]", "Resolved By");
		Thread.sleep(500);
		
		// Date Logged Column
		TestUtils.testTitle("Remove Date Logged Column ");
		getDriver().findElement(By.xpath("//a[8]/span")).click();
		if (getDriver().findElement(By.xpath("//th[8]")).getText().contains("Date Logged")) {
			TestUtils.assertSearchText("XPATH", "//th[8]", "Date Logged");

		}else {
			testInfo.get().info("Date Logged column removed");

		}
		TestUtils.testTitle("Add Date logged Column");
		getDriver().findElement(By.xpath("//a[8]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[8]", "Date Logged");
		Thread.sleep(500);
		
		// Resolution Date Column
		TestUtils.testTitle("Remove Resolution Date Column");
		getDriver().findElement(By.xpath("//a[9]/span")).click();
		if (getDriver().findElement(By.xpath("//th[9]")).getText().contains("Resolution Date")) {
			TestUtils.assertSearchText("XPATH", "//th[9]", "Resolution Date");

		}else {
			testInfo.get().info("Resolution Date column removed");

		}
		TestUtils.testTitle("Add Resolution Date Column");
		getDriver().findElement(By.xpath("//a[9]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[9]", "Resolution Date");
		Thread.sleep(500);
		
		// Issue Status Column
		TestUtils.testTitle("Remove Issue Status Column");
		getDriver().findElement(By.xpath("//a[10]/span")).click();
		if (getDriver().findElement(By.xpath("//th[10]")).getText().contains("Issue Status")) {
			TestUtils.assertSearchText("XPATH", "//th[10]", "Issue Status");

		}else {
			testInfo.get().info("Issue Status column removed");

		}
		TestUtils.testTitle("Add Issue Status Column");
		getDriver().findElement(By.xpath("//a[10]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[10]", "Issue Status");
		Thread.sleep(500);
		
		// Actions Column
		TestUtils.testTitle("Remove Actions Column");
		getDriver().findElement(By.xpath("//a[11]/span")).click();		
		try {
			TestUtils.assertSearchText("XPATH", "//th[11]", "Actions");

		} catch (Exception e) {
			testInfo.get().info("Actions column removed");
		}
		TestUtils.testTitle("Add Actions Column");
		getDriver().findElement(By.xpath("//a[11]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[11]", "Actions");
		Thread.sleep(500);
		
		WebElement figure = getDriver().findElement(By.xpath("//body"));
			
		Actions actions = new Actions(getDriver());
			actions.moveToElement(figure).perform(); // hover action
			figure.click();
		
			}

	@Test (groups = { "Regression" })
	@Parameters({"testEnv"})
	public void viewDetailsTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		String kitTag = getDriver().findElement(By.xpath("//table[@id='dealerKitIssue']/tbody/tr/td[3]")).getText();
		TestUtils.testTitle("View full details of kit tag: " + kitTag);
		scrollDown();
		
		// View detail modal
		TestUtils.clickElement("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[11]/div/a/i");
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='viewKitIssue']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='viewKitIssue']/div/div/div/h4", "View logged issue");
		Thread.sleep(500);
	    Assertion.assertViewDetailsModalKitIssueLogDealer();
	    Thread.sleep(1000);
	    Assertion.imageDisplayKitIssueLog(testEnv);

		// Click close button
		getDriver().findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(500);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByIssueIDTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		TestUtils.testTitle("Filter by Issue ID: " + issueID);
        getDriver().navigate().refresh();
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(issueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[2]", issueID);
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByKitTagTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		TestUtils.testTitle("Filter by Device ID: " + kitTag);
		
        getDriver().navigate().refresh();
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[3]", kitTag);
		
	}
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDeviceIdTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		TestUtils.testTitle("Filter by Device ID: " + deviceID);
		
        getDriver().navigate().refresh();
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.name("deviceIdParam")).clear();
		getDriver().findElement(By.name("deviceIdParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		
		// View detail modal
		TestUtils.clickElement("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[11]/div/a/i");
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='viewKitIssue']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='viewKitIssue']/div/div/div/h4", "View logged issue");
		TestUtils.assertSearchText("ID", "vDeviceId", deviceID);
		Thread.sleep(500);
		
		// Close Modal
		TestUtils.scrollToElement("XPATH", "//div[3]/button");
		getDriver().findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(500);
	}
	
	@Test (groups = { "Regression" })
	public void searchByIssueStatusTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		scrollUp();
		getDriver().findElement(By.id("searchParam")).clear();
		getDriver().findElement(By.id("deviceIdParam")).clear();
		Thread.sleep(500);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}  
		Thread.sleep(500);
		if (!getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		
		// Resolved status
		TestUtils.testTitle("Filter by Issue Status: Resolved");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[10]/span", "RESOLVED");
		
		// Pending status
		TestUtils.testTitle("Filter by Issue Status: Pending");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[10]/span", "PENDING");
		
		// Returned Back to Business
		TestUtils.testTitle("Filter by Issue Status: Returned Back to Business");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Returned Back to Business");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[10]/span", "RETURNB2B");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Rejected
		TestUtils.testTitle("Filter by Issue Status: Rejected");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Rejected");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[10]/span", "REJECTED");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Test (groups = { "Regression" })
	public void searchByIssueSummaryTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);

		scrollUp();
		getDriver().findElement(By.id("searchParam")).clear();
		getDriver().findElement(By.id("deviceIdParam")).clear();
		Thread.sleep(500);
		
		// Whitelist Kit
		TestUtils.testTitle("Filter by Issue Summary: Whitelist Kit");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Whitelist Kit");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Whitelist Kit");
		Thread.sleep(500);
		
		// Blacklist Kit
		TestUtils.testTitle("Filter by Issue Summary: Blacklist Kit");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Blacklist Kit");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Blacklist Kit");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Unable to Login
		TestUtils.testTitle("Filter by Issue Summary: Unable to Login");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unable to Login");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Unable to Login");
		
		// Can't Capture Fingerprint
		TestUtils.testTitle("Filter by Issue Summary: Can't Capture Fingerprint");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Can't Capture Fingerprint");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Can't Capture Fingerprint");

		// Can't Capture Portriat
		TestUtils.testTitle("Filter by Issue Summary: Can't Capture Portriat");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Can't Capture Portriat");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Can't Capture Portriat");
		
		// Can't Receive OTP
		TestUtils.testTitle("Filter by Issue Summary: Can't Receive OTP");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Can't Receive OTP");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Can't Receive OTP");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// 	Java Exception Error
		TestUtils.testTitle("Filter by Issue Summary: Java Exception Error");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Java Exception Error");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Java Exception Error");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// Lines not Activated after Registration
		TestUtils.testTitle("Filter by Issue Summary: Lines not Activated after Registration");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Lines not Activated after Registration");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Lines not Activated after Registration");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// Network Connection Error
		TestUtils.testTitle("Filter by Issue Summary: Network Connection Error");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Network Connection Error");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Network Connection Error");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Registrations Failed
		TestUtils.testTitle("Filter by Issue Summary: Registrations not Synchronizing");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Registrations Failed");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]",	"Registrations Failed");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// Registrations not Synchronizing
		TestUtils.testTitle("Filter by Issue Summary: Registrations not Synchronizing");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Registrations not Synchronizing");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Registrations not Synchronizing");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Device Licensing 
		TestUtils.testTitle("Filter by Issue Summary: Device Licensing");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Device Licensing");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[5]", "Device Licensing");

	}
	
	@Parameters({"testEnv" })
	@Test (groups = { "Regression" })
	public void logKitIssueFormValidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		
		TestUtils.testTitle("Filter by device type: " + kitTag);
		scrollUp();
		getDriver().findElement(By.xpath("//div/div/div[2]/div/button")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "myModalLabel", "Log kit related issue");
		
		TestUtils.testTitle("Check for empty fields");
		TestUtils.scrollToElement("ID", "kitIssuesFormSubmitId");
		getDriver().findElement(By.id("kitIssuesFormSubmitId")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "kitPk-error");
		TestUtils.assertSearchText("ID", "kitPk-error", "Please select a kit Tag.");
		TestUtils.assertSearchText("ID", "issueSummary2-error", "Please select issue summary.");
		TestUtils.assertSearchText("ID", "description-error", "Description should not be greater than 255 characters.");
		Thread.sleep(1000);
		
		TestUtils.testTitle("Check for picture and document upload");
		// select kit tag
		getDriver().findElement(By.xpath("//form[@id='kitIssuesFormId']/div/div/div/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(kitTag);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Select issue summary
		getDriver().findElement(By.xpath("//form[@id='kitIssuesFormId']/div/div/div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Can't Capture Fingerprint");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Input Description
		getDriver().findElement(By.id("description")).clear();
		getDriver().findElement(By.id("description")).sendKeys("testing");

	    // Upload picture or document
		
		//Multiple Photo upload Test
	    //Confirm error when user uploads photos above allowable number of files(2) 
	    TestUtils.testTitle("Confirm error when user uploads photo above allowable number of files(2)");
	    TestUtils.uploadFile(By.id("devicePhoto"), "image2.jpg");
	    TestUtils.uploadFile(By.id("devicePhoto"), "image4.jpg");
	    TestUtils.uploadFile(By.id("devicePhoto"), "image3.jpg");
	    Thread.sleep(500);
	    TestUtils.assertSearchText("XPATH", "//div[4]/div/ul/li", "Number of files selected for upload (3) exceeds maximum allowed limit of 2.");
		getDriver().findElement(By.xpath("//div[2]/button/span")).click();
		
	    // Picture more than 512kb or Invalid picture size
		TestUtils.testTitle("Upload invalid picture size of more than 512kb");
		TestUtils.uploadFile(By.id("devicePhoto"), "Bigpic.jpg");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kv-avatar-errors-1")));
		String inv = getDriver().findElement(By.cssSelector("#kv-avatar-errors-1")).getText();
		inv = inv.substring(1, inv.length());
		testInfo.get().info(inv + " found");
		getDriver().findElement(By.cssSelector("button.close.kv-error-close > span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.hidden-xs")).click();
		Thread.sleep(500);
		
		//Multiple Document upload Test
	    //Confirm error when user uploads documents above allowable number of files(2) 
	    TestUtils.testTitle("Confirm error when user uploads documents above allowable number of files(2)");
	    TestUtils.uploadFile(By.id("document"), "SIMROP  Agent Enrollment.pdf");
	    TestUtils.uploadFile(By.id("document"), "SIMROP  Biodata.pdf");
	    TestUtils.uploadFile(By.id("document"), "SIMROP  Kits Issues.pdf");
	    Thread.sleep(500);
	    TestUtils.assertSearchText("XPATH", "//div[5]/div/ul/li", "Number of files selected for upload (3) exceeds maximum allowed limit of 2.");
		getDriver().findElement(By.xpath("//div[5]/div[2]/div[4]/div[2]/button/span")).click();
		
		
	    // Document more than 512kb or Invalid document size
		TestUtils.testTitle("Upload invalid document size of more than 512kb");
		TestUtils.uploadFile(By.id("document"), "BigDoc.pdf");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kv-avatar-errors-2")));
		String inv1 = getDriver().findElement(By.cssSelector("#kv-avatar-errors-2")).getText();
		inv1 = inv1.substring(1, inv1.length());
		testInfo.get().info(inv1 + " found");
		getDriver().findElement(By.cssSelector("button.close.kv-error-close > span")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[5]/div[2]/div[4]/div[2]/button/span");
		getDriver().findElement(By.xpath("//div[5]/div[2]/div[4]/div[2]/button/span")).click();
		Thread.sleep(500);
		
		// Invalid Picture
		TestUtils.testTitle("Upload invalid picture");
		TestUtils.uploadFile(By.id("devicePhoto"), "Invalidfile.txt");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kv-avatar-errors-1")));
		String inv2 = getDriver().findElement(By.cssSelector("#kv-avatar-errors-1")).getText();
		inv2 = inv2.substring(1, inv2.length());
		testInfo.get().info(inv2 + " found");
		getDriver().findElement(By.cssSelector("button.close.kv-error-close > span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.hidden-xs")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("closeModalBtn")).click();
		Thread.sleep(500);
	}
	
	@Parameters({"server", "downloadPath",  "testEnv" })
	@Test (groups = { "Regression" })
	public void logKitIssueTest(String server, String downloadPath, String testEnv) throws Exception {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		
		/*TestUtils.testTitle("Filter by device type: " + kitTag);
		scrollUp();*/
		TestUtils.testTitle("Log Kit Issue Test: " + kitTag);
		getDriver().findElement(By.xpath("//div/div/div[2]/div/button")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "myModalLabel", "Log kit related issue");
		
		// select kit tag
	    getDriver().findElement(By.xpath("//form[@id='kitIssuesFormId']/div/div/div/div/span/span/span")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(kitTag);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
	    
	    // Select issue summary
	    getDriver().findElement(By.xpath("//form[@id='kitIssuesFormId']/div/div/div[2]/div/span/span/span")).click();
	    Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Can't Capture Fingerprint");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// Input Description
	    getDriver().findElement(By.id("description")).clear();
	    getDriver().findElement(By.id("description")).sendKeys("testing");
	   

		// Valid picture
	    TestUtils.testTitle("Check that the user can upload multiple photos");
	    TestUtils.uploadFile(By.id("devicePhoto"), "image4.jpg");
	    Thread.sleep(2000);
		//TestUtils.uploadFile(By.id("devicePhoto"), "image1.jpeg");
		testInfo.get().info("Photo Upload was successful");


	   
		// Valid Document
	    TestUtils.testTitle("Check that the user can upload multiple documents");

		TestUtils.uploadFile(By.id("document"), "SIMROP  Kits Issues.pdf");
		//TestUtils.uploadFile(By.id("document"), "SIMROP  Biodata.pdf");
		testInfo.get().info("Document Upload was successful");



		TestUtils.scrollToElement("ID", "kitIssuesFormSubmitId");
		getDriver().findElement(By.id("kitIssuesFormSubmitId")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(2000);
		TestUtils.assertSearchText("CSSSELECTOR", "h2", "Issue Log creation");
		String issID = getDriver().findElement(By.cssSelector("div.swal2-content")).getText();
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", issID);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
	}
	
	@Test (groups = { "Regression" })
	public void searchBySlaStatusTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		scrollUp();
		getDriver().findElement(By.id("searchParam")).clear();
		getDriver().findElement(By.id("deviceIdParam")).clear();
		Thread.sleep(500);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}  
		Thread.sleep(500);
		if (!getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}

		// On Target
		TestUtils.testTitle("Filter by SLA status: On Target");
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("On Target");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[6]", "On Target");
			verifySlaStatus();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Breached
		TestUtils.testTitle("Filter by SLA status: Breached");
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Breached");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[6]", "Breached");
			verifySlaStatus();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Warning 
		TestUtils.testTitle("Filter by SLA status: Warning");
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Warning");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[6]", "Warning");
			verifySlaStatus();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	
	}
	
	public void verifySlaStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		
		String slaStatus = getDriver().findElement(By.xpath("//td[6]")).getText();
		TestUtils.testTitle("Click on view details to verify the SLA status on the 'View logged issue' modal");
		TestUtils.clickElement("XPATH", "//table[@id='dealerKitIssue']/tbody/tr/td[11]/div/a/i");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='viewKitIssue']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='viewKitIssue']/div/div/div/h4", "View logged issue");
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "vSlaStatus");
		String sla = getDriver().findElement(By.id("vSlaStatus")).getText();
		if (slaStatus.equals(sla)) {
			testInfo.get().info("SLA Status Verified: " + slaStatus);
		} else {
			testInfo.get().error("SLA status on the 'View logged issue' modal is different from the one on Issue table.");
		}

		// Click close button
		getDriver().findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(500);
	}

	public void uploadFile(By by, String fileName) throws InterruptedException {
 		Thread.sleep(20000);
		WebElement element = getDriver().findElement(by);
		LocalFileDetector detector = new LocalFileDetector();
		String path = new File(System.getProperty("user.dir") + "/files").getAbsolutePath() + "/" + fileName;
		File file = detector.getLocalFile(path);
		((RemoteWebElement) element).setFileDetector(detector);
		element.sendKeys(file.getAbsolutePath());
	}
	
}
