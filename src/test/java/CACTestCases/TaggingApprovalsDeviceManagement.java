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
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class TaggingApprovalsDeviceManagement extends TestBase {

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "deviceTaggingTable");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "global_filter");
	}
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToTaggingApprovalsDeviceManagement(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Tagging Approval");
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("1595Tagging Approval")).click();
			Thread.sleep(500);
		} else {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"38683138Device Management\"]");
			getDriver().findElement(By.cssSelector("a[name=\"38683138Device Management\"]")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("1595Tagging Approval")).click();
			Thread.sleep(500);
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Device Tagging Approval");

	}

	@Test(groups = { "Regression" })
	public void viewTageHistoryTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// View history modal
		TestUtils.testTitle("View Tag History");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Tag History')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div[@id='viewTagHistoriesModal']/div/div/div/h4",
				"View Tag History");

		// Change page size
		new Select(getDriver().findElement(By.name("tagHistoryTable_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='tagHistoryTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);

			// Filter by kit tag
			int number = rowCount / 2;
			Math.ceil(number);
			String kitTag = getDriver().findElement(By.xpath("//table[@id='tagHistoryTable']/tbody/tr[" + number + "]/td[4]")).getText();
			String createDate = getDriver().findElement(By.xpath("//table[@id='tagHistoryTable']/tbody/tr[" + number + "]/td[2]")).getText();
			TestUtils.testTitle("Filter by kit tag: " + kitTag);
			getDriver().findElement(By.id("mSearchKitTag")).clear();
			getDriver().findElement(By.id("mSearchKitTag")).sendKeys(kitTag);
			getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
			Thread.sleep(1000);
			TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr[1]/td[4]", kitTag);
			getDriver().findElement(By.id("mSearchKitTag")).clear();
			getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));

			// Filter by create start date
			TestUtils.testTitle("Filter by Create date: " + createDate);
			getDriver().findElement(By.id("mStartDate")).clear();
			getDriver().findElement(By.id("mStartDate")).sendKeys(createDate);
			getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
			Thread.sleep(1000);
			TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr[1]/td[2]", createDate);
		} else {
			TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr/td", "No data available in table");
			testInfo.get().info("Table is empty.");
		}

		TestUtils.scrollToElement("XPATH", "//div[@id='viewTagHistoriesModal']/div/div/div[3]/button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[3]/button")));
		
		// Click close button
		getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[3]/button")).click();
		Thread.sleep(1000);
	}

	public void viewDetailsTest(String dealerName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		// View details modal
		
		String tag = getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[3]")).getText();
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		TestUtils.testTitle("View details of kit tag: " + tag);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "Device Tagging Approval");
		TestUtils.assertSearchText("XPATH", "//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]", dealerName);
		Assertion.viewTaggingApprovalDetails();
		TestUtils.scrollToElement("XPATH", "//form[@id='approvalForm']/div[3]/button[2]");
		Assertion.viewTaggingApprovalDetailsDown();
		
		// Click close button
		getDriver().findElement(By.id("closeBtn")).click();
		Thread.sleep(1000);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDeviceIDTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String deviceID = (String) envs.get("deviceID");

		TestUtils.testTitle("Filter by device ID: " + deviceID);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[2]", deviceID);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String tag = (String) envs.get("tag");

		TestUtils.testTitle("Filter by kit tag: " + tag);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(tag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[3]", tag);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td","No data available in table");
		}

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDealerViewFullDetailsTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String dealerName = (String) envs.get("dealerName");

		TestUtils.testTitle("Filter by dealer name: " + dealerName);
		getDriver().findElement(By.id("global_filter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		viewDetailsTest(dealerName);

	}

	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.id("global_filter")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);

		// Approved status
		TestUtils.testTitle("Filter by Status: Approved and list of action button options ");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Approved");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[6]/span", "APPROVED");
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Tag History')]", "View Tag History");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Details')]", "View Details");

		// Pending Status
		TestUtils.testTitle("Filter by Status: Pending and list of action button options ");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[6]/span", "PENDING");
			getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Tag History')]", "View Tag History");
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Details')]", "View Details");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// Rejected status
		TestUtils.testTitle("Filter by Status: Rejected and list of action button options ");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("DISAPPROVED");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[6]/span", "DISAPPROVED");
			getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Tag History')]", "View Tag History");
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Details')]", "View Details");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	}

	@Parameters({"testEnv"})
	@Test(groups = {"Regression"})
	public void searchByDateTest(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i")).click();
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
			Thread.sleep(500);
			TestUtils.testTitle("Filter by Start date: " + startDate);
			getDriver().findElement(By.id("startDate")).clear();
			getDriver().findElement(By.id("startDate")).sendKeys(startDate);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")));
			Thread.sleep(1000);
			String sDate = getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")).getText();
			TestUtils.convertToNormalDate(sDate);

			TestUtils.testTitle("Filter by End date: " + endDate);
			getDriver().findElement(By.id("startDate")).clear();
			getDriver().findElement(By.id("endDate")).clear();
			getDriver().findElement(By.id("endDate")).sendKeys(endDate);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")));
			Thread.sleep(1000);
			String eDate = getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")).getText();
			TestUtils.convertToNormalDate(eDate);

			TestUtils.testTitle("Filter by date range: " + startDate + " and " + endDate);
			getDriver().findElement(By.id("startDate")).clear();
			getDriver().findElement(By.id("startDate")).sendKeys(startDate);
			getDriver().findElement(By.id("endDate")).clear();
			getDriver().findElement(By.id("endDate")).sendKeys(endDate);
			getDriver().findElement(By.id("searchBtn")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")));
			Thread.sleep(1000);
			String table_Date = getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[5]")).getText();
			String newDate = TestUtils.convertToNormalDate(table_Date);
			TestUtils.checkDateBoundary(startDate, endDate, newDate);
			getDriver().findElement(By.xpath("//button[@id='minimizeSidebar']/i[2]")).click();
			Thread.sleep(500);
	}

	@Test(groups = { "Regression" })
	public void ApproveTaggingApprovalsTest() throws Exception {

		scrollUp();
		getDriver().findElement(By.id("global_filter")).clear();
		// getDriver().findElement(By.id("advancedBtn")).click();
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		Thread.sleep(1000);

		// String Device = TestUtils.setDeviceID();

		scrollUp();
		getDriver().findElement(By.id("global_filter")).sendKeys("BIORUGGED");
		// Pending Status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		if (TestUtils.isLoaderPresents()) {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		}
		// Pending Status
		/*
		 * getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		 * Thread.sleep(1000);
		 * getDriver().findElement(By.cssSelector("input.select2-search__field")).
		 * sendKeys("PENDING"); Thread.sleep(1000);
		 * getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		 * Thread.sleep(1000); getDriver().findElement(By.id("searchBtn")).click();
		 * Thread.sleep(1000); if(TestUtils.isLoaderPresents()) { WebDriverWait wait =
		 * new WebDriverWait(getDriver(), waitTime);
		 * wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className(
		 * "dataTables_processing"), "Processing...")); }
		 */

		// View details modal
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(3000);
		String deviceID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		// TestUtils.scrollToElement("XPATH",
		// "//form[@id='approvalForm']/div[3]/button[2]");

		// approve
		TestUtils.scrollToElement("ID", "APPROVED");
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("feedback")));
		getDriver().findElement(By.name("feedback")).click();
		getDriver().findElement(By.name("feedback")).clear();
		getDriver().findElement(By.name("feedback")).sendKeys("approve test");
		getDriver().findElement(By.id("APPROVED")).click();
		Thread.sleep(2000);

		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("APPROVED")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		String screenshotPath1 = TestUtils.addScreenshot();
		testInfo.get().addScreenCaptureFromPath(screenshotPath1);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("closeBtn")));
		getDriver().findElement(By.id("closeBtn")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));

		scrollUp();
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(deviceID);
		TestUtils.clickElement("ID", "searchBtn");
		Thread.sleep(1000);
		if (TestUtils.isLoaderPresents()) {
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		}
		TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[2]", deviceID);
//		getDriver().navigate().refresh();
//		Thread.sleep(1000);
//		if (TestUtils.isAlertPresents()) {
//			getDriver().switchTo().alert().accept();
//		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(3000);
		Assert.assertEquals(deviceID,
				getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]")).getText());
		String screenshotPath2 = TestUtils.addScreenshot();
		testInfo.get().addScreenCaptureFromPath(screenshotPath2);
		getDriver().findElement(By.id("closeBtn")).click();

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void rejectTaggingApprovalsTest(String testEnv)
			throws Exception {

		// Create a tagging request for approving and rejection 
		TaggingRequestDeviceManagement TaggingRequest = new TaggingRequestDeviceManagement();
		TaggingRequest.navigateToTaggingRequestDeviceManagement(testEnv);
		TaggingRequest.reassignApprovedRequestTest(testEnv);
		TaggingRequest.reassignDisapprovedRequestTest(testEnv);

		navigateToTaggingApprovalsDeviceManagement(testEnv);
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

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String deviceID = (String) envs.get("reassignDisapprovedDeviceID");

		TestUtils.testTitle("Filter and disapprove request for device ID: " + deviceID);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(deviceID);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// View details modal
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "Device Tagging Approval");

		int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-pending.pull-right")).size();
		testInfo.get().info("<b> Number of pending approval: </b>" + pendingApproval);

		// reject
		TestUtils.testTitle("Click REJECT button without supplying feedback field.");
		TestUtils.scrollToElement("ID", "APPROVED");
		getDriver().findElement(By.name("feedback")).click();
		getDriver().findElement(By.name("feedback")).clear();
		getDriver().findElement(By.id("DISAPPROVED")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "feedback-error", "Please enter a feedback");

		TestUtils.testTitle("Supply feedback field and Click REJECT button");
		getDriver().findElement(By.name("feedback")).clear();
		getDriver().findElement(By.name("feedback")).sendKeys("Testing");
		getDriver().findElement(By.id("DISAPPROVED")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("DISAPPROVED")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//*[contains(text(),'Device Tagging request has been successfully disapproved.')]")));
		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH",
				"//*[contains(text(),'Device Tagging request has been successfully disapproved.')]",
				"Device Tagging request has been successfully disapproved.");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void approveTaggingApprovalsTest(String testEnv)
			throws Exception {

		navigateToTaggingApprovalsDeviceManagement(testEnv);

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

		JSONObject envs = (JSONObject) config.get("taggingApproval");

		String deviceID = (String) envs.get("reassignApprovedDeviceID");

		TestUtils.testTitle("Filter and approve request for device ID: " + deviceID);
		getDriver().findElement(By.id("global_filter")).clear();
		getDriver().findElement(By.id("global_filter")).sendKeys(deviceID);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// View details modal
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "Device Tagging Approval");

		int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-pending.pull-right")).size();
		testInfo.get().info("<b> Number of pending approval: </b>" + pendingApproval);

		// approve
		TestUtils.testTitle("Click APPROVE button without supplying feedback field.");
		TestUtils.scrollToElement("ID", "APPROVED");
		getDriver().findElement(By.name("feedback")).click();
		getDriver().findElement(By.name("feedback")).clear();
		getDriver().findElement(By.id("APPROVED")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "feedback-error", "Please enter a feedback");
		getDriver().findElement(By.id("closeBtn")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Supply feedback field and Click APPROVE button");
		for (int i = 1; i <= pendingApproval; i++) {
			approve();
		}

		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Device has been successfully re-tagged')]",
				"Device has been successfully re-tagged");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='deviceTaggingTable']/tbody/tr/td[6]/span", "APPROVED");

	}

	public void approve() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")));
		getDriver().findElement(By.xpath("//table[@id='deviceTaggingTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));

		int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-pending.pull-right")).size();
		testInfo.get().info("Number of pending approval: " + pendingApproval);
		TestUtils.scrollToElement("ID", "APPROVED");
		getDriver().findElement(By.id("feedback")).click();
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys("testing");
		getDriver().findElement(By.id("APPROVED")).click();

		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button.close > i.material-icons")));
		Thread.sleep(800);
	}

}
