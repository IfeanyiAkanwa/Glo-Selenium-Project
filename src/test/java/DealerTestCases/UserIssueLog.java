package DealerTestCases;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class UserIssueLog extends TestBase {
	
	
	public void scrollDown() throws InterruptedException {
		
		TestUtils.scrollToElement("ID", "issueRequest");
	}

	public void scrollUp() throws InterruptedException {
		
		TestUtils.scrollToElement("NAME", "searchParam");
	}

	@Test (groups = { "Regression" })
	public void navigateToUserIssueLogTest() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to User Issue Log");
		
		try {
			getDriver().findElement(By.name("1040Log Issues")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("7883203274Log Issues")).click();
		}
	    Thread.sleep(500);
	    TestUtils.scrollToElement("XPATH", "//a[contains(text(),'User Issue Log')]");
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//a[contains(text(),'User Issue Log')]")).click();
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "div.col-md-4.col-sm-12.font-weight-bold.text-secondary", "Users Issues");
	}
	
	@Test (groups = { "Regression" })
	public void viewDetailsTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Filter by Issue Status: Resolved
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		Thread.sleep(500);
		String issueId = getDriver().findElement(By.xpath("//table[@id='issueRequest']/tbody/tr/td[2]")).getText();
		TestUtils.testTitle("View full details of Issue ID: " + issueId);

		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='issueRequest']/tbody/tr/td[9]/div/a")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='viewUserIssue']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='viewUserIssue']/div/div/div/h4", "View logged issue");
		Thread.sleep(500);
		Assertion.assertViewDetailsModalUserIssueLogDealer();
		TestUtils.scrollToElement("XPATH", "//div[3]/button");
	    
		// Click close button
		getDriver().findElement(By.xpath("//div[3]/button")).click();
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByIssueIDTest(String testEnv) throws Exception {

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
		JSONObject envs = (JSONObject) config.get("User_Issue_log");
		
		String issueID = (String) envs.get("issueID");
		
		TestUtils.testTitle("Filter by Issue ID: " + issueID);
		Thread.sleep(500);

		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(issueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[2]", issueID);
		Thread.sleep(500);
	
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
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
		JSONObject envs = (JSONObject) config.get("User_Issue_log");
		
		String emailAddress = (String) envs.get("emailAddress");
		
		TestUtils.testTitle("Filter by Email Address: " + emailAddress);
		Thread.sleep(500);

		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(emailAddress);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[3]", emailAddress);
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByIssueStatusTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		scrollUp();
		getDriver().findElement(By.id("searchParam")).clear();
		
		// Resolved 
		TestUtils.testTitle("Filter by Issue Status: Resolved");
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[8]/span", "RESOLVED");
		
		// Pending status
		TestUtils.testTitle("Filter by Issue Status: Pending");
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[8]/span", "PENDING");
		
	}

	@Test (groups = { "Regression" })
	public void searchByIssueSummary() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		scrollUp();
		getDriver().findElement(By.id("searchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);
		
		// Account Activation
		TestUtils.testTitle("Filter by Issue Summary: Account Activation");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Account Activation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Account Activation");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Account Deactivation
		TestUtils.testTitle("Filter by Issue Summary: Account Deactivation");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Account Deactivation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Account Deactivation");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Edit Details
		TestUtils.testTitle("Filter by Issue Summary: Edit Details");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Edit Details");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Edit Details");
		
		// Invalid Username /password during login
		TestUtils.testTitle("Filter by Issue Summary: Invalid Username /password during login");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Invalid Username /password during login");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Invalid Username /password during login");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Others
		TestUtils.testTitle("Filter by Issue Summary: Others");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Others");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Others");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Password Reset
		TestUtils.testTitle("Filter by Issue Summary: Password Reset");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Password Reset");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Password Reset");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Unlock Account
		TestUtils.testTitle("Filter by Issue Summary: Unlock Account");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unlock Account");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='issueRequest']/tbody/tr/td[4]", "Unlock Account");
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = {"Regression"})
	public void logUserIssueTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Log User Issues");
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("User_Issue_log");

		String userName = (String) envs.get("userName");

		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");

		scrollUp();
		getDriver().findElement(By.xpath("//div[3]/div/button")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "myModalLabel", "Log User related issue");

		// Check for empty Username field
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='logUserIssue']/div/div/form/div[2]/button")));
		getDriver().findElement(By.xpath("//div[@id='logUserIssue']/div/div/form/div[2]/button")).click();
		WebElement user = getDriver().findElement(By.name("username"));
		String validationMessage = user.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Username field:</b> " + validationMessage);
		Thread.sleep(500);

		// select user
		getDriver().findElement(By.xpath("//div/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(userName);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Check for empty Issue Summary field
		getDriver().findElement(By.xpath("//div[@id='logUserIssue']/div/div/form/div[2]/button")).click();
		WebElement issueSummary = getDriver().findElement(By.name("issueSummary"));
		String validationMessage1 = issueSummary.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage1, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Issue Summary field:</b> " + validationMessage1);
		Thread.sleep(500);

	    // Select issue summary
		getDriver().findElement(By.xpath("//div/div/div[2]/div/span/span/span")).click();
	    Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Account Activation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Check for empty description field
		getDriver().findElement(By.xpath("//div[@id='logUserIssue']/div/div/form/div[2]/button")).click();
		WebElement description = getDriver().findElement(By.name("description"));
		TestUtils.assertValidationMessage(validationMessages, description, "Description");
		Thread.sleep(500);

	    getDriver().findElement(By.id("description")).clear();
	    getDriver().findElement(By.id("description")).sendKeys("testing");

	    getDriver().findElement(By.xpath("//div[@id='logUserIssue']/div/div/form/div[2]/button")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		WebElement div_alert = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid"));
		String expected_message = "Issue log for user " + userName + " was successfully created with issue id";
		TestUtils.assertDivAlert(div_alert, expected_message);

	}

}
