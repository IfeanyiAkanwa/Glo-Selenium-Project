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
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class vtuDataTransactionHistory extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void scrollDown() throws InterruptedException{
		TestUtils.scrollToElement("ID", "dataTransactionLogTable");
	}
	
	public void scrollUp() throws InterruptedException{
		TestUtils.scrollToElement("ID", "total-transactions");
	}

	@Test(groups = { "Regression" })
	public void navigateTovtuDataTransactionHistoryTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    Thread.sleep(500);
		getDriver().findElement(By.cssSelector("a[name=\"999999981VTU Management\"] > p")).click();
	    Thread.sleep(500);
	    TestUtils.scrollToElement("NAME", "9999999Transaction History");
	    Thread.sleep(500);
	    getDriver().findElement(By.name("9999999Transaction History")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
        TestUtils.clickElement("ID", "dataId");
        Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("ID", "dataId", "DATA");
		
	}
	
	@Test(groups = { "Regression" })
	public void assertTransactionCountTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String totalTransactionsValString = getDriver().findElement(By.id("total-transactions")).getText();
		String totalSuccessfulValString = getDriver().findElement(By.id("total-successful")).getText();
		String totalFailedValString = getDriver().findElement(By.id("total-failed")).getText();
		
		int actualTotalTransactionsVal = TestUtils.convertToInt(totalTransactionsValString);
		int actualTotalSuccessfulVal = TestUtils.convertToInt(totalSuccessfulValString);
		int actualTotalFailedVal = TestUtils.convertToInt(totalFailedValString);
				
		int expectedTotalTransactionsVal = actualTotalSuccessfulVal + actualTotalFailedVal;
		
		try {
			Assert.assertEquals(expectedTotalTransactionsVal,actualTotalTransactionsVal);
		    testInfo.get().log(Status.INFO, "Total transactions ("+expectedTotalTransactionsVal+") is equal to summation of total successful ("+actualTotalSuccessfulVal+") + total failed ("+actualTotalFailedVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
		// Page size
		new Select(getDriver().findElement(By.name("dataTransactionLogTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='dataTransactionLogTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	public void viewDetailsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.xpath("//table[@id='dataTransactionLogTable']/tbody/tr/td[10]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		String view = "View data transaction details ";
		Markup m = MarkupHelper.createLabel(view, ExtentColor.BLUE);
		testInfo.get().info(m);
		TestUtils.assertSearchText("ID", "myModalLabel", "VTU Transaction History");
		Assertion.assertDataTransInfoCAC();
		TestUtils.scrollToElement("CSSSELECTOR", "button.btn.btn-default.btn-simple.untrackForm.all");
		getDriver().findElement(By.cssSelector("button.btn.btn-default.btn-simple.untrackForm.all")).click();
		Thread.sleep(1000);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchBySubPhoneTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String subPhone = (String) envs.get("subPhone");

		String sPhoneFilter = "Filter by Subscriber Phone No: " + subPhone;
		Markup m = MarkupHelper.createLabel(sPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(subPhone);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionLogTable']/tbody/tr/td[7]", subPhone);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByVtuPhoneTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String vtuNumber = (String) envs.get("vtuNumber");

		String vPhoneFilter = "Filter by Agent VTU Number: " + vtuNumber;
		Markup m = MarkupHelper.createLabel(vPhoneFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(vtuNumber);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionLogTable']/tbody/tr/td[4]", vtuNumber);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByBundleTypeTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String data = (String) envs.get("data");

		String dataFilter = "Filter by bundle type: " + data;
		Markup m = MarkupHelper.createLabel(dataFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("sStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		getDriver().findElement(By.id("global_filter")).clear();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(data);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionLogTable']/tbody/tr/td[5]", data);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDealerTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String dealerName = (String) envs.get("dealerName");

		String dealerFilter = "Filter by dealer name: " + dealerName;
		Markup m = MarkupHelper.createLabel(dealerFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("sStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("global_filter")).clear();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='dataTransactionLogTable']/tbody/tr/td[8]", dealerName);
		
	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		if (!getDriver().findElement(By.name("sStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		getDriver().findElement(By.id("global_filter")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		String statusFilter = "Filter by transaction status";
		Markup m = MarkupHelper.createLabel(statusFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollUp();
		// Success status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("SUCCESSFUL");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "SUCCESSFUL");
		viewDetailsTest();
		
		// Failed status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("FAILED");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "FAILED");
		viewDetailsTest();
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("vtuData");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate+ " and "+endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		
		if (!getDriver().findElement(By.name("sStartDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		getDriver().findElement(By.id("global_filter")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
	    
		getDriver().findElement(By.name("sStartDate")).clear();
		getDriver().findElement(By.name("sStartDate")).sendKeys(startDate);  
		getDriver().findElement(By.name("sEndDate")).clear();
		getDriver().findElement(By.name("sEndDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='dataTransactionLogTable']/tbody/tr/td[3]")).getText();
		table_Date = table_Date.substring(0, 10);
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date);


	}
}
