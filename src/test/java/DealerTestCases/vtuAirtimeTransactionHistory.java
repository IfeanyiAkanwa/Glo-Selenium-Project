package DealerTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class vtuAirtimeTransactionHistory extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void scrollDown() throws InterruptedException{
		TestUtils.scrollToElement("ID", "airtimeTransactionHistoryTable_wrapper");
	}
	
	public void scrollUp() throws InterruptedException{
		TestUtils.scrollToElement("ID", "toggle");
	}

	@Test(groups = { "Regression" })
	public void navigateToVtuAirtimeHistoryTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		getDriver().findElement(By.name("999999991VTU Management")).click();
		Thread.sleep(500);
	    getDriver().findElement(By.name("99999999Transaction History")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Airtime')]", "AIRTIME");
		
	}
	
	@Test(groups = { "Regression" })
	public void assertTransactionCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Airtime')]");
		
		String totalTransactionsValString = getDriver().findElement(By.id("total_agents")).getText();
		String totalSuccessfulValString = getDriver().findElement(By.id("new-registration_today")).getText();
		String totalFailedValString = getDriver().findElement(By.id("re-registration_today")).getText();
		
		int actualTotalTransactionsVal = TestUtils.convertToInt(totalTransactionsValString);
		int actualTotalSuccessfulVal = TestUtils.convertToInt(totalSuccessfulValString);
		int actualTotalFailedVal = TestUtils.convertToInt(totalFailedValString);
				
		int expectedTotalTransactionsVal = actualTotalSuccessfulVal + actualTotalFailedVal;

		try {
			Assert.assertEquals(expectedTotalTransactionsVal, actualTotalTransactionsVal);
			testInfo.get().log(Status.INFO, "Total transactions (" + expectedTotalTransactionsVal + ") is equal to summation of total successful (" + actualTotalSuccessfulVal + ") + total failed (" + actualTotalFailedVal + ").");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}
		
		// Page size
		new Select(getDriver().findElement(By.name("airtimeTransactionHistoryTable_length"))).selectByVisibleText("250");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 250";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='airtimeTransactionHistoryTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Test(groups = { "Regression" })
	public void viewDetailsTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		try {
			TestUtils.scrollToElement("XPATH", "//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[10]/div/a/i");
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[10]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
			String view = "View airtime transaction details ";
			Markup m = MarkupHelper.createLabel(view, ExtentColor.BLUE);
			testInfo.get().info(m);
			TestUtils.assertSearchText("ID", "myModalLabel", "VTU Transaction History");
			Assertion.assertAirtimeTransInfoDealer();
			TestUtils.scrollToElement("XPATH", "//div[3]/button"); 
			getDriver().findElement(By.xpath("//div[3]/button")).click();
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByUsernameTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuAirtime");

		String userName = (String) envs.get("userName");

		String userNameFilter = "Filter by User name: " + userName;
		Markup m = MarkupHelper.createLabel(userNameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "total_agents");
		Thread.sleep(500);
		getDriver().findElement(By.id("asearch")).clear();
		getDriver().findElement(By.id("asearch")).sendKeys(userName);
		TestUtils.clickElement("ID", "airtimeSearchBtn");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[5]", userName);
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchBySubPhoneTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuAirtime");

		String subPhone = (String) envs.get("subPhone");

		String sPhoneFilter = "Filter by Subscriber Phone No: " + subPhone;
		Markup m = MarkupHelper.createLabel(sPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("asearch")).clear();
		getDriver().findElement(By.id("asearch")).sendKeys(subPhone);
		getDriver().findElement(By.id("airtimeSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[8]", subPhone);
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByVtuPhoneTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuAirtime");

		String vtuNumber = (String) envs.get("vtuNumber");

		String vPhoneFilter = "Filter by Agent VTU Number: " + vtuNumber;
		Markup m = MarkupHelper.createLabel(vPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("asearch")).clear();
		getDriver().findElement(By.id("asearch")).sendKeys(vtuNumber);
		getDriver().findElement(By.id("airtimeSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[6]", vtuNumber);
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuAirtime");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate+ " and "+endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("asearch")).clear();
		if (!getDriver().findElement(By.id("airtimeStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		Thread.sleep(500);
	    getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate); 
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate); 
		getDriver().findElement(By.id("airtimeSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			String table_Date = getDriver().findElement(By.xpath("//table[@id='airtimeTransactionHistoryTable']/tbody/tr/td[3]")).getText();
			table_Date = table_Date.substring(0, 10);
			testInfo.get().info("Date returned "+table_Date);
			TestUtils.checkDateBoundary(startDate, endDate, table_Date);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		 getDriver().findElement(By.id("asearch")).clear();
		if (!getDriver().findElement(By.id("airtimeStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("endDate")).clear();
		String statusFilter = "Filter by transaction status";
		Markup m = MarkupHelper.createLabel(statusFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollUp();
		
		// Success status
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("SUCCESSFUL");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("airtimeSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "SUCCESSFUL");
			viewDetailsTest();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
		
		// Failed status
		scrollUp();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("FAILED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("airtimeSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "FAILED");
			viewDetailsTest();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}
	}
}
