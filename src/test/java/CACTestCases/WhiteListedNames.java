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

import util.TestBase;
import util.TestUtils;

public class WhiteListedNames extends TestBase{
	private StringBuffer verificationErrors = new StringBuffer();
	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("NAME", "searchParam");
	}
	@Test(groups = { "Regression" })
	public void navigateToWhiteListedNamesTest() throws InterruptedException {
		String names = "Navigate to WhiteListed Names";
		Markup b = MarkupHelper.createLabel(names, ExtentColor.BLUE);
		testInfo.get().info(b);
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.scrollToElement("XPATH", "//a[@name='5824781324Whitelisted Name']");
		getDriver().findElement(By.xpath("//a[@name='5824781324Whitelisted Name']")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Whitelisted Name Pool");
	}
	
	@Test(groups = { "Regression" })
	  public void assertNameCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		String names = "Assert Name Count on dashboard";
		Markup d = MarkupHelper.createLabel(names, ExtentColor.BLUE);
		testInfo.get().info(d);
		
		String totalValString = getDriver().findElement(By.cssSelector("h4.card-title.text-center")).getText();
		String ActiveValString = getDriver().findElement(By.xpath("//div[2]/div/div/h4")).getText();
		String InactiveValString = getDriver().findElement(By.xpath("//div[3]/div/div/h4")).getText();
		
		int totalVal = TestUtils.convertToInt(totalValString);
		int activeVal = TestUtils.convertToInt(ActiveValString);
		int inactiveVal = TestUtils.convertToInt(InactiveValString);
				
		int expectedTotalVal = activeVal + inactiveVal;
		
		try {
			Assert.assertEquals(expectedTotalVal,totalVal);
		    testInfo.get().log(Status.INFO, "Total WhiteListed Names ("+expectedTotalVal+") is equal to summation of total active ("+activeVal+") + total inactive ("+inactiveVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    }
		
		// Page size
		new Select(getDriver().findElement(By.name("whitelistedNamesTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(1000);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
		
		// To verify the columns on whitelisted names data table
		String verify = "To verify the columns on whitelisted names data table";
		Markup vs = MarkupHelper.createLabel(verify, ExtentColor.BLUE);
		testInfo.get().info(vs);
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//th[2]", "SN");
		TestUtils.assertSearchText("XPATH", "//th[3]", "Whitelisted Names");
		TestUtils.assertSearchText("XPATH", "//th[4]", "Date Added");
		TestUtils.assertSearchText("XPATH", "//th[5]", "Added By");
		TestUtils.assertSearchText("XPATH", "//th[6]", "Category");
		TestUtils.assertSearchText("XPATH", "//th[7]", "Status");
		TestUtils.assertSearchText("XPATH", "//th[8]", "Actions");
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void exportfileTest() throws Exception {
		// Check for export pdf and excel functionality
		String verify = "Check for export pdf and excel functionality";
		Markup vs = MarkupHelper.createLabel(verify, ExtentColor.BLUE);
		testInfo.get().info(vs);
		
		// PDF download
		Thread.sleep(1000);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div[2]/a");

		// EXCEL download
		Thread.sleep(1000);
		TestUtils.clickElement("XPATH", "//div[2]/a[2]");

	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByWhiteListedNameTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("whiteListedNames");

		String name = (String) envs.get("name2");

		String kitTagFilter = "Filter by Whitelisted Names: " + name;
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(name);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='whitelistedNamesTable']/tbody/tr/td[3]", name);
		Thread.sleep(1000);
	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		String whiteName1 = "Filter by Status: ACTIVE and INACTIVE";
		Markup w = MarkupHelper.createLabel(whiteName1, ExtentColor.BLUE);
		testInfo.get().info(w);
		
		scrollDown();
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		// Active status
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("span.select2-selection.select2-selection--single")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("ACTIVE");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "ACTIVE");
		
		// Inactive status
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("span.select2-selection.select2-selection--single")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Inactive");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(1000);
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		} catch (Exception e) {
			Thread.sleep(1000);
			TestUtils.assertSearchText("XPATH", "//td[contains(text(),'No data available in table')]", "No data available in table");
		}
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

		JSONObject envs = (JSONObject) config.get("whiteListedNames");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by start date only: " + startDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String start_Date = getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[4]")).getText();
		start_Date = start_Date.substring(0, 10);
		testInfo.get().info("Date returned "+start_Date);
		Thread.sleep(1000);
		
		String dateFilter2 = "Filter by end date only: " +endDate;
		Markup f = MarkupHelper.createLabel(dateFilter2, ExtentColor.BLUE);
		testInfo.get().info(f);
		Thread.sleep(1000);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String end_Date = getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[4]")).getText();
		end_Date = end_Date.substring(0, 10);
		testInfo.get().info("Date returned "+end_Date);
		Thread.sleep(1000);
		
		String dateFilter1 = "Filter by date range: " + startDate+ " and "+endDate;
		Markup d = MarkupHelper.createLabel(dateFilter1, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[4]")).getText();
		table_Date = table_Date.substring(0, 10);
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateyYMDBoundary(startDate, endDate, table_Date);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByAddedByTest(String testEnv)
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

		JSONObject envs = (JSONObject) config.get("whiteListedNames");

		String addedBy = (String) envs.get("added_By");

		String kitTagFilter = "Filter by Added By: " + addedBy;
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).clear();
		getDriver().findElement(By.name("addedBySearchParameter")).sendKeys(addedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='whitelistedNamesTable']/tbody/tr/td[5]", addedBy);
		Thread.sleep(1000);

	}
	
	@Test(groups = { "Sanity" })
	public void singleDeactivateUserTest() throws InterruptedException, IOException {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
		
		String whiteName1 = "Single Deactivation";
		Markup w = MarkupHelper.createLabel(whiteName1, ExtentColor.BLUE);
		testInfo.get().info(w);
		
		getDriver().findElement(By.id("advancedBtn")).click();
		
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
		
		String name = getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[3]")).getText();
		String whiteName = "Deactivate WhiteListed Name: " + name;
		Markup m = MarkupHelper.createLabel(whiteName, ExtentColor.BLUE);
		testInfo.get().info(m);
		
		TestUtils.scrollToElement("ID", "advancedBtn");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[8]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(text(),'Deactivate')])[3]")));
		getDriver().findElement(By.xpath("(//a[contains(text(),'Deactivate')])[3]")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to deactivate check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/strong")));
		TestUtils.assertSearchText("XPATH", "//span/strong", "Success!");
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]", "Status was changed successfully");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	@Test(groups = { "Sanity" })
	public void singleActivateUserTest() throws Exception {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
		
		String whiteName1 = "Single Activation";
		Markup w = MarkupHelper.createLabel(whiteName1, ExtentColor.BLUE);
		testInfo.get().info(w);
		
		TestUtils.clickElement("ID","advancedBtn");
		Thread.sleep(1000);
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Inactive");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		Thread.sleep(1000);
		
	    String name = getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[3]")).getText();
		String whiteName = "Activate WhiteListed Name: " + name;
		Markup m = MarkupHelper.createLabel(whiteName, ExtentColor.BLUE);
		testInfo.get().info(m);
			
		TestUtils.scrollToElement("ID", "advancedBtn");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[8]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td[8]/div/ul/li/a")));
		getDriver().findElement(By.xpath("//tr/td[8]/div/ul/li/a")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to activate check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/strong")));
		TestUtils.assertSearchText("XPATH", "//span/strong", "Success!");
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]", "Status was changed successfully");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void singleAddNameTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		String whiteName1 = "Add a name to whilisted names pool";
		Markup w = MarkupHelper.createLabel(whiteName1, ExtentColor.BLUE);
		testInfo.get().info(w);
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("whiteListedNames");

		String name = (String) envs.get("name");
		String invalidName = (String) envs.get("invalidName");
		scrollDown();
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Add Names");
		
		String empty = "Click ADD NAME button without supplying any name to WhiteList";
		Markup m = MarkupHelper.createLabel(empty, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(1000);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[6]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[6]/span[2]", "Please enter a name.");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
		
		String empty1 = "Click ADD NAME button without choosing a category";
		Markup d = MarkupHelper.createLabel(empty1, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(name);
		getDriver().findElement(By.id("addNameBtn")).click();
//		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[6]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[6]/span[2]", "Please enter a validation category.");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
		
		// valid existing name
		String valid = "Supply existing name: '"+name+"' and Click ADD NAME button";
		Markup v = MarkupHelper.createLabel(valid, ExtentColor.BLUE);
		testInfo.get().info(v);
		Thread.sleep(1000);
		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(name);
		
		// Select Category
		getDriver().findElement(By.xpath(".//span[@class='select2-selection select2-selection--multiple']")).click();
		getDriver().findElement(By.xpath(".//span[@class='select2-results']//ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(1000);
		
		// Click No
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		Thread.sleep(1000);
		
		// Click Yes
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
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
		
		// Check for names with numbers and special characters
		String valid1 = "Check for names with numbers and special characters: " + invalidName;
		Markup c = MarkupHelper.createLabel(valid1, ExtentColor.BLUE);
		testInfo.get().info(c);
		Thread.sleep(1000);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Add Names");
		Thread.sleep(1000);
		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(invalidName);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath(".//span[@class='select2-selection select2-selection--multiple']")).click();
		getDriver().findElement(By.xpath(".//span[@class='select2-results']//ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger > div.container-fluid")));
		String expectedText1 = getDriver().findElement(By.cssSelector("div.alert.alert-danger > div.container-fluid")).getText();
		String requiredText1 = expectedText1.substring(0, 100);
		String value1 = "Name should not contain numbers and special characters";

		try {
			Assert.assertEquals(requiredText1, value1);
			testInfo.get().log(Status.INFO, value1 + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value1 + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
		Thread.sleep(1000);
	
		// Valid new name
		String newName = TestUtils.uniqueString(8);
		String validNew = "Supply new name: '"+newName+"' and Click ADD NAME button";
		Markup n = MarkupHelper.createLabel(validNew, ExtentColor.BLUE);
		testInfo.get().info(n);
		Thread.sleep(1000);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Add Name");
		getDriver().findElement(By.id("addname")).clear();
		getDriver().findElement(By.id("addname")).sendKeys(newName);
		Thread.sleep(1000);
		
		// Select Category
		getDriver().findElement(By.xpath(".//span[@class='select2-selection select2-selection--multiple']")).click();
		getDriver().findElement(By.xpath(".//span[@class='select2-results']//ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addNameBtn")).click();
		Thread.sleep(1000);
		
		// Click Yes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
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
	public void bulkAddNamesTest(String server, String downloadPath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		String whiteName1 = "Bulk addition of names";
		Markup w = MarkupHelper.createLabel(whiteName1, ExtentColor.BLUE);
		testInfo.get().info(w);
		
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("bulkAddLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bulkAddNameTitle")));
		TestUtils.assertSearchText("ID", "bulkAddNameTitle", "Add Names");
		
		String empty = "Click ADD NAME button without selecting any file to upload";
		Markup m = MarkupHelper.createLabel(empty, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(1000);
		getDriver().findElement(By.id("addBulkNameBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[5]/span[2]")));
		TestUtils.assertSearchText("XPATH", "//div[5]/span[2]", "Invalid file Provided. Supported file extension is .xls");
		try {
			getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
		
		}
		Thread.sleep(1000);
		
		String validFile;
		if (server.equals("remote-browserStack")) {
			validFile = downloadPath + "Whitelisted_names_template.xls";
		} else if (server.equals(remoteJenkins)) {
			validFile = downloadPath + "Whitelisted_names_template.xls";
		} else {
			validFile = System.getProperty("user.dir") + "/files/Whitelisted_names_template.xls";
		}

		// Select a valid file format
		String file = "Select valid file format and upload. eg xls " + validFile;
		Markup i = MarkupHelper.createLabel(file, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("addbulknames")).sendKeys(validFile);
		getDriver().findElement(By.id("addBulkNameBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to upload these names?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		Thread.sleep(1000);
		getDriver().findElement(By.id("bulkAddModalClose")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
	}
	
	@Test(groups = { "Sanity" })
	public void bulkDeactivateUserTest() throws InterruptedException, IOException {
		Thread.sleep(1000);
		getDriver().navigate().refresh();
		Thread.sleep(2000);
	
		String whiteName = "Deactivate multiple WhiteListed Names";
		Markup m = MarkupHelper.createLabel(whiteName, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("advancedBtn")).click();
		
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
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td/div/label/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr[2]/td/div/label/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr[3]/td/div/label/span/span")).click();
		Thread.sleep(1000);
		
		TestUtils.scrollToElement("ID", "add_names_button");
		Thread.sleep(1000);
		getDriver().findElement(By.id("add_names_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("removeSelectedLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to deactivate check for this name?");
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
		
		String whiteName = "Activate multiple WhiteListed Names";
		Markup m = MarkupHelper.createLabel(whiteName, ExtentColor.BLUE);
		testInfo.get().info(m);
		
		TestUtils.clickElement("ID","advancedBtn");
		Thread.sleep(1000);
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span/span")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys("Inactive");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "INACTIVE");
		Thread.sleep(1000);
		
		// Click the checkboxes
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr/td/div/label/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr[2]/td/div/label/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='whitelistedNamesTable']/tbody/tr[3]/td/div/label/span/span")).click();
		Thread.sleep(1000);

		TestUtils.scrollToElement("ID", "add_names_button");
		Thread.sleep(1000);
		getDriver().findElement(By.id("add_names_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("addSelectedLink")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Confirmation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to activate check for this name?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/div/div/div/div/div")));
		String expectedText = getDriver().findElement(By.xpath("//div[4]/div/div/div/div/div")).getText();
		String requiredText = expectedText.substring(12, expectedText.length());
		String value = "clear Reactivated 3 name(s) out of 3";

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
