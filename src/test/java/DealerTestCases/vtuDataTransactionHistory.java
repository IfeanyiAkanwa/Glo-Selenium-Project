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

public class vtuDataTransactionHistory extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void scrollDown() throws InterruptedException{
		TestUtils.scrollToElement("ID", "dsearch");
	}
	
	public void scrollUp() throws InterruptedException{
		TestUtils.scrollToElement("ID", "total_agents");
	}

	@Test(groups = { "Regression" })
	public void navigateTovtuDataTransactionHistoryTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    Thread.sleep(500);
		getDriver().findElement(By.cssSelector("a[name=\"999999991VTU Management\"] > p")).click();
	    Thread.sleep(500);
	    TestUtils.scrollToElement("NAME", "99999999Transaction History");
	    Thread.sleep(500);
	    getDriver().findElement(By.name("99999999Transaction History")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.clickElement("XPATH", "//a[contains(text(),'Data')]");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Data')]", "DATA");
	}
	
	@Test(groups = { "Regression" })
	public void assertTransactionCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String totalTransactionsValString = getDriver().findElement(By.xpath("(//h4[@id='total_agents'])[2]")).getText();
		String totalSuccessfulValString = getDriver().findElement(By.xpath("(//h4[@id='new-registration_today'])[2]")).getText();
		String totalFailedValString = getDriver().findElement(By.xpath("(//h4[@id='re-registration_today'])[2]")).getText();

		int actualTotalTransactionsVal = TestUtils.convertToInt(totalTransactionsValString);
		int actualTotalSuccessfulVal = TestUtils.convertToInt(totalSuccessfulValString);
		int actualTotalFailedVal = TestUtils.convertToInt(totalFailedValString);

		int expectedTotalTransactionsVal = actualTotalSuccessfulVal + actualTotalFailedVal;

		try {
			Assert.assertEquals(expectedTotalTransactionsVal, actualTotalTransactionsVal);
			testInfo.get().log(Status.INFO,
					"Total transactions (" + expectedTotalTransactionsVal+ ") is equal to summation of total successful (" + actualTotalSuccessfulVal+ ") + total failed (" + actualTotalFailedVal + ").");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		// Page size
		new Select(getDriver().findElement(By.name("dataTransactionHistoryTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='dataTransactionHistoryTable']/tbody/tr")).size();
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
			scrollDown();
			getDriver().findElement(By.xpath("//table[@id='dataTransactionHistoryTable']/tbody/tr/td[11]/div/a/i")).click();
			Thread.sleep(500);
			TestUtils.clickElement("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[11]/div/ul/li/a");
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
			String view = "View data transaction details ";
			Markup m = MarkupHelper.createLabel(view, ExtentColor.BLUE);
			testInfo.get().info(m);
			TestUtils.assertSearchText("ID", "myModalLabel", "VTU Transaction History");
			Assertion.assertDataTransInfoDealer();
			TestUtils.scrollToElement("XPATH", "//div[3]/button"); 
			getDriver().findElement(By.xpath("//div[3]/button")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String userName = (String) envs.get("userName");

		String sUserNameFilter = "Filter by Username: " + userName;
		Markup m = MarkupHelper.createLabel(sUserNameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollDown();
		getDriver().findElement(By.id("dsearch")).clear();
		getDriver().findElement(By.id("dsearch")).sendKeys(userName);
		Thread.sleep(500);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[5]", userName);
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchBySubPhoneTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollDown();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuData");

		String subPhone = (String) envs.get("subPhone");

		String sPhoneFilter = "Filter by Subscriber Phone No: " + subPhone;
		Markup m = MarkupHelper.createLabel(sPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("dsearch")).clear();
		getDriver().findElement(By.id("dsearch")).sendKeys(subPhone);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr[3]/td[9]", subPhone);
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String vtuNumber = (String) envs.get("vtuNumber");

		String vPhoneFilter = "Filter by Agent VTU Number: " + vtuNumber;
		Markup m = MarkupHelper.createLabel(vPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("dsearch")).clear();
		getDriver().findElement(By.id("dsearch")).sendKeys(vtuNumber);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			scrollDown();
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[6]", vtuNumber);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.id("dsearch")).clear();
		if (!getDriver().findElement(By.id("dataStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggleData")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		String statusFilter = "Filter by transaction status";
		Markup m = MarkupHelper.createLabel(statusFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollUp();
		// Success status
		getDriver().findElement(By.xpath("//div[2]/form/div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("SUCCESSFUL");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(500);
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[10]/span", "SUCCESSFUL");
			Thread.sleep(500);
			viewDetailsTest();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	
		// Failed status
		scrollDown();
		getDriver().findElement(By.xpath("//div[2]/form/div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("FAILED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(500);
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[10]/span", "FAILED");
			Thread.sleep(500);
			scrollDown();
			viewDetailsTest();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate+ " and "+endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollDown();
		if (!getDriver().findElement(By.id("dataStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggleData")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("dataStartDate")).clear();
		getDriver().findElement(By.id("dataStartDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("dataEndDate")).clear();
		getDriver().findElement(By.id("dataEndDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(500);
		try {
			String table_Date = getDriver().findElement(By.xpath("//table[@id='dataTransactionHistoryTable']/tbody/tr/td[3]")).getText();
			table_Date = table_Date.substring(0, 10);
			testInfo.get().info("Date returned "+table_Date);
			TestUtils.checkDateBoundary(startDate, endDate, table_Date);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByBundleTypeTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String data = (String) envs.get("data");

		String dataFilter = "Filter by bundle type: " + data;
		Markup m = MarkupHelper.createLabel(dataFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollDown();
		if (!getDriver().findElement(By.id("dataStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggleData")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(data);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("dataSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(500);
		try {
			 TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td[7]", data);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionHistoryTable']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	}

}
