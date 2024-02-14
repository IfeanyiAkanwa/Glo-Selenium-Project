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

public class UserIssueLog extends TestBase {

	public void scrollDown() throws InterruptedException {

		TestUtils.scrollToElement("ID", "userIssuesTable");
	}

	public void scrollUp() throws InterruptedException {

		TestUtils.scrollToElement("NAME", "searchData");
	}

	@Test(groups = { "Regression" })
	public void navigateToUserIssueLogTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to User Issue Logs");
		try {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1232Issue Resolution\"] > p");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("a[name=\"1232Issue Resolution\"] > p")));
			getDriver().findElement(By.cssSelector("a[name=\"1232Issue Resolution\"] > p")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.name("2077User Issue Log")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"7883203242Issue Resolution\"] > p");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("a[name=\"7883203242Issue Resolution\"] > p")));
			getDriver().findElement(By.cssSelector("a[name=\"7883203242Issue Resolution\"] > p")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.name("7883203246User Issue Log")).click();
			Thread.sleep(1000);
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Users Issues");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "User-Related Issues");

	}

	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("userIssuesTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='userIssuesTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	@Test(groups = { "Regression" })
	public void viewDetailsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Filter by Issue Status: Resolved
		if (!getDriver().findElement(By.id("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		Thread.sleep(1000);
		String issueId = getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[2]")).getText();
		TestUtils.testTitle("View full details of Issue ID: " + issueId);
		
		// View detail modal		
		getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[9]/div/a/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View Details')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Resolve User Issue");
		Assertion.assertViewDetailsModalUserIssueLogAdmin();
		TestUtils.scrollToElement("ID", "closeModal");

		// Click close button
		getDriver().findElement(By.id("closeModal")).click();
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
		JSONObject envs = (JSONObject) config.get("userIssueLog");
		String issueID = (String) envs.get("issueID");
		
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		TestUtils.testTitle("Filter by Issue ID: " + issueID);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(issueID);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[2]", issueID);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByEmail(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("userIssueLog");
		String email = (String) envs.get("email");

		TestUtils.testTitle("Filter by User Email: " + email);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(email);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[4]", email);

	}

	@Test(groups = { "Regression" })
	public void searchByIssueSummary() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.name("searchData")).clear();

		// Password Reset
		TestUtils.testTitle("Filter by Issue Summary: Password Reset");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Password Reset");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Password Reset");

		// Unlock Account
		TestUtils.testTitle("Filter by Issue Summary: Unlock Account");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unlock Account");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Unlock Account");

		// Edit Details
		TestUtils.testTitle("Filter by Issue Summary: Edit Details");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Edit Details");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Edit Details");
		
		// Invalid Username /password during login
		TestUtils.testTitle("Filter by Issue Summary: Edit Details");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Invalid Username /password during login");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Invalid Username /password during login");

		// Account Activation
		TestUtils.testTitle("Filter by Issue Summary: Account Activation");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Account Activation");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Account Activation");

		// Account De-activation
		TestUtils.testTitle("Filter by Issue Summary: Account Deactivation");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Account Deactivation");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Account Deactivation");

		// Others
		TestUtils.testTitle("Filter by Issue Summary: Others");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Others");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Others");
		
		// Periperals not working ( e.g Scanners , cameras )
		TestUtils.testTitle("Filter by Issue Summary: Periperals not working ( e.g Scanners , cameras )");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Periperals not working ( e.g Scanners , cameras )");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[5]", "Periperals not working ( e.g Scanners , cameras )");
		
	}

	@Test(groups = { "Regression" })
	public void searchByResolutionStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("searchData")).clear();

		// Resolved status
		TestUtils.testTitle("Filter by Issue Status: Resolved");
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Resolved");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-active", "RESOLVED");

		// Pending status
		TestUtils.testTitle("Filter by Issue Status: Pending");
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
		
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
		JSONObject envs = (JSONObject) config.get("userIssueLog");
		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		TestUtils.testTitle("Filter by Date Range: Start date is: " + startDate + " and End date is: " + endDate);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate);
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[7]"))
				.getText();
		testInfo.get().info("Date returned " + table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date);

	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void resolveUserIssuePendingRequestTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("userIssueLog");

		String emailResolve = (String) envs.get("emailResolve");
		
		TestUtils.testTitle("Filter by users ending with: " + emailResolve + " and resolve user issue with status pending");
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(emailResolve);
		Thread.sleep(1000);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		Thread.sleep(1000);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();

		// Pending status
		getDriver().findElement(By.xpath("//div[3]/div/div/div/span/span/span")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		
		// View Details
		String issueID = getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[2]")).getText();
		getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[9]/div/a/i")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("View Details")));
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary",	"Resolve User Issue");
		TestUtils.scrollToElement("ID", "closeModal");

		TestUtils.testTitle("Click save without filling mandatory field");
		getDriver().findElement(By.id("resolveIssue")).click(); // Click resolve button
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "required", "Required");
		
		TestUtils.testTitle("Fill form and Submit");
		getDriver().findElement(By.id("resolution")).sendKeys("Seamfix test");
		getDriver().findElement(By.id("resolveIssue")).click(); // Click resolve button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.swal2-confirm.swal2-styled")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Issue has been successfully resolved.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(issueID);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[6]/span", "RESOLVED");
	}

	@Test(groups = { "Regression" })
	@Parameters({ "testEnv" })
	public void searchByDealer(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.id("searchData")).clear();
		
		// Search by Dealer
		TestUtils.testTitle("Filter by Dealers");
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		Thread.sleep(1000);

		String[] dealers = { "SEAMFIXQA SUPPORT", "1000 MAKS ENTERPRISE", "ABC TELECOM" };
		for (int i = 0; i < dealers.length; i++) {
			// Search By Dealers
			testInfo.get().log(Status.INFO, "<b> Search By: " + dealers[i] + "</b>");
			getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.select2-search__field")));
			getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealers[i]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[2]/ul/li")));
			getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
			getDriver().findElement(By.id("searchButton")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));

			try {
				String dealer = getDriver().findElement(By.xpath("//table[@id='userIssuesTable']/tbody/tr/td[3]"))
						.getText();
				TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td[3]", dealer);
			} catch (Exception e) {
				TestUtils.assertSearchText("XPATH", "//table[@id='userIssuesTable']/tbody/tr/td",
						"No data available in table");
			}
		}

	}

}
