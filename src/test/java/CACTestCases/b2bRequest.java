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

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class b2bRequest extends TestBase {

	public void scrollDown() throws Exception {

		TestUtils.scrollToElement("ID", "b2bRequestTable");
	}

	public void scrollUp() throws Exception {

		TestUtils.scrollToElement("NAME", "searchData");
	}

	@Test(groups = { "Regression" })
	public void navigateToB2BRequestTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		try {
			Thread.sleep(500);
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"173213537B2B Request\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"173213537B2B Request\"] > p")).click();
		} catch (Exception e) {
			Thread.sleep(500);
			TestUtils.scrollToElement("CSSSELECTOR", "a[name='173213537B2B Request'] > p");
			getDriver().findElement(By.xpath("//li[11]/a/p")).click();
		}
		String navigateToPage = "Navigate to B2B Request";
		Markup n = MarkupHelper.createLabel(navigateToPage, ExtentColor.BLUE);
		testInfo.get().info(n);
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | B2B Requests");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "B2B Requests");

	}

	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("b2bRequestTable_length"))).selectByVisibleText("250");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 250";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='b2bRequestTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	public void viewDetail() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
		// getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "B2B Request Resolution");
		Assertion.assertB2BDetail();
		TestUtils.scrollToElement("XPATH", "//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]");

		// Click close button
		getDriver().findElement(By.xpath("//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]")).click();
		Thread.sleep(1000);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("b2b");

		String kitTag = (String) envs.get("kitTag");

		String kitTagFilter = "Filter by kit tag: " + kitTag;
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='b2bRequestTable']/tbody/tr/td[3]", kitTag);

	}

	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String statusFilter = "Filter by transaction status";
		Markup m = MarkupHelper.createLabel(statusFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchData")).clear();

		// Approved status
		String approvedStatus = "Check For Requests with Approved Status";
		Markup a = MarkupHelper.createLabel(approvedStatus, ExtentColor.BLUE);
		testInfo.get().info(a);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Approved");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");

		// Pending status
		String pendingStatus = "Check For Requests with Kit-tags and Pending Status:" ;
		Markup p = MarkupHelper.createLabel(pendingStatus, ExtentColor.BLUE);
		testInfo.get().info(p);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");

		// Rejected status
		String rejectedStatus = "Check For Requests with Rejected Status";
		Markup r = MarkupHelper.createLabel(rejectedStatus, ExtentColor.BLUE);
		testInfo.get().info(r);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Rejected");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "DISAPPROVED");
		
		// Return to Dealer status
		String returnToDealerStatus = "Check For Requests with Return to Dealer Status";
		Markup g = MarkupHelper.createLabel(returnToDealerStatus, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Return to Dealer");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "RETURNED TO DEALER");

}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void approveRequestTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.name("searchData")).clear();

		// Pending status
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("b2b");
		String kitTagSFX = (String) envs.get("kitTagSFX");

		String pendingKitTag = "Filter Requests with Pending Status and kit tag("+kitTagSFX+")";
		Markup pk = MarkupHelper.createLabel(pendingKitTag, ExtentColor.BLUE);
		testInfo.get().info(pk);

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		getDriver().findElement(By.id("searchParameter")).sendKeys(kitTagSFX);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
		

		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "B2B Request Resolution");
		int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-warning.float-right")).size();
		testInfo.get().info("Number of pending approval: " + pendingApproval);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "B2B Request Resolution");
		TestUtils.scrollToElement("XPATH", "//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]");

		// approve
		String approve = "Click APPROVE button without supplying feedback field.";
		Markup r = MarkupHelper.createLabel(approve, ExtentColor.BLUE);
		testInfo.get().info(r);
		Thread.sleep(500);
		getDriver().findElement(By.name("resolution")).click();
		getDriver().findElement(By.name("resolution")).clear();
		getDriver().findElement(By.id("approveRequest")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "required", "Required");

		// Click close button
		getDriver().findElement(By.xpath("//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]")).click();
		Thread.sleep(1000);

		testInfo.get().skip("Skipped the final approval");

		/*
		 * getDriver().findElement(By.id("resolution")).sendKeys("Test approval");
		 * getDriver().findElement(By.id("approveRequest")).click(); // Click approve
		 * button Thread.sleep(2000); TestUtils.assertSearchText("CSSSELECTOR",
		 * "div.swal2-content", "Are you sure that you want to approve this request?");
		 * getDriver().findElement(By.xpath("(//button[@type='button'])[7]")).click();
		 * Thread.sleep(5000);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
		 * "div.swal2-content")));
		 * 
		 * TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
		 * "This request was approved successfully.");
		 * getDriver().findElement(By.xpath("(//button[@type='button'])[8]")).click();
		 */

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void rejectRequestTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("b2b");

		String kitTagSFX = (String) envs.get("kitTagSFX");
		String rejectRequest = "Reject a Pending Request";
		Markup j = MarkupHelper.createLabel(rejectRequest, ExtentColor.BLUE);
		testInfo.get().info(j);

		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(kitTagSFX);
		// Pending status
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
			String kit = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[3]")).getText();
			String kitTagFilter = "Reject b2b request of : " + kit;
			Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
			testInfo.get().info(m);
			Thread.sleep(500);

			// View detail modal
			getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
			TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "B2B Request Resolution");
			int pendingApproval = getDriver().findElements(By.cssSelector("span.badge.badge-warning.float-right"))
					.size();
			testInfo.get().info("Number of pending approval: " + pendingApproval);

			TestUtils.scrollToElement("XPATH", "//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]");
			// reject
			String reject = "Click REJECT button without supplying feedback field.";
			Markup r = MarkupHelper.createLabel(reject, ExtentColor.BLUE);
			testInfo.get().info(r);
			Thread.sleep(500);
			getDriver().findElement(By.name("resolution")).click();
			getDriver().findElement(By.name("resolution")).clear();
			getDriver().findElement(By.id("rejectRequest")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "required", "Required");

			String reject2 = "Supply feedback field and Click REJECT button";
			Markup v = MarkupHelper.createLabel(reject2, ExtentColor.BLUE);
			testInfo.get().info(v);
			Thread.sleep(500);
			getDriver().findElement(By.name("resolution")).click();
			getDriver().findElement(By.name("resolution")).clear();
			getDriver().findElement(By.name("resolution")).sendKeys("Sanity Test");
			getDriver().findElement(By.id("rejectRequest")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
					"Are you sure that you want to reject this request?");
			getDriver().findElement(By.cssSelector("button.swal2-cancel.btn.btn-simple.btn-blue")).click();
			Thread.sleep(1000);

			// Click confirm/ok
			getDriver().findElement(By.id("rejectRequest")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
					"This request was disapproved successfully.");
			getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();

			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));

			String view = "Check the status of newly rejected kit " + kit;
			Markup k = MarkupHelper.createLabel(view, ExtentColor.BLUE);
			testInfo.get().info(k);
			Thread.sleep(500);
			getDriver().findElement(By.name("searchData")).clear();
			getDriver().findElement(By.name("searchData")).sendKeys(kit);
			getDriver().findElement(By.id("searchButton")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "DISAPPROVED");
			viewDetail();
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='b2bRequestTable']/tbody/tr/td",
					"No data available in table");
		}

	}

}