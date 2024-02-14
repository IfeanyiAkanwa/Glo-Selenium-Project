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

public class vtuOnboardingApproval extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "approvalRequest");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "approved-vtu");
	}

	@Test(groups = { "Regression" })
	public void navigateToVtuOnboardingApprovalTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("a[name=\"999999981VTU Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("3445345345VTU Onboarding Approval")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Vtu Onboarding Approval");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "VTU Onboarding Approval");
	}
	
	
		/*// View detail modal
		getDriver().findElement(By.xpath("//*[@id=\"approvalRequest\"]/tbody/tr[1]/td[9]/div/a/i")).click();
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View details')]")));
		Thread.sleep(3000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View details')]")).click();
		Thread.sleep(3000);
		TestUtils.assertSearchText("ID", "myModalLabel", "View Onboarding Request Details");
		Thread.sleep(3000);

		// Click close button
		getDriver().findElement(By.xpath("//form/div[2]/button")).click();
*/
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByEmailTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentEmail = (String) envs.get("agentEmail");

		String emailFilter = "Filter by agent email: " + agentEmail;
		Markup m = MarkupHelper.createLabel(emailFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(agentEmail);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='approvalRequest']/tbody/tr/td[4]", agentEmail);

	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByAgentNameTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentName = (String) envs.get("agentName");

		String nameFilter = "Filter by agent name: " + agentName;
		Markup m = MarkupHelper.createLabel(nameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(agentName);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='approvalRequest']/tbody/tr/td[3]", agentName);

	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByAgentVtuNumberTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String vtuNumber = (String) envs.get("vtuNumber");

		String nameFilter = "Filter by agent vtu number: " + vtuNumber;
		Markup m = MarkupHelper.createLabel(nameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(vtuNumber);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='approvalRequest']/tbody/tr/td[5]", vtuNumber);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDealerTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String dealerName = (String) envs.get("dealerName");

		String dealerFilter = "Filter by dealer name: " + dealerName;
		Markup m = MarkupHelper.createLabel(dealerFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		getDriver().findElement(By.xpath("//span/span/span")).click();
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='approvalRequest']/tbody/tr/td[6]", dealerName);

	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String statusFilter = "Filter by onboarding approval status";
		Markup m = MarkupHelper.createLabel(statusFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		// Approved status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");

		// Pending status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");

		// Rejected status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("REJECTED");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "REJECTED");

	}

	@Test(groups = { "Sanity" })
	public void searchByDateTest() throws InterruptedException {
		scrollUp();
		getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys("2017-01-25"); // Start date YYYY/MM/DD format
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys("2018-01-25"); // Start date YYYY/MM/DD format
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(4000);

	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void approveRejectMultiplePendingRequestTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		searchByDealerTest(testEnv);
		scrollUp();
		Thread.sleep(500);

		// Pending status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");

		// click approve and reject button without selecting a request
		String click = "Click APPROVE SELECTED and REJECT SELECTED button without selecting a request";
		Markup m = MarkupHelper.createLabel(click, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("approveSelectedBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'No Onboarding request was selected')]")));
		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'No Onboarding request was selected')]",
				"No Onboarding request was selected");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("rejectSelectedBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'No Onboarding request was selected')]")));
		Thread.sleep(800);
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'No Onboarding request was selected')]",
				"No Onboarding request was selected");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		
		// Select a record and leave reason empty and try submit
		String selectAll = "select all recored pendind and Click APPROVE SELECTED and REJECT SELECTED button without supplying feedback reason";
		Markup s = MarkupHelper.createLabel(selectAll, ExtentColor.BLUE);
		testInfo.get().info(s);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.check")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("approveSelectedBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='multipleApprovalFeedbackModal']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='multipleApprovalFeedbackModal']/div/div/div/h4",
				"Feedback for bulk Approval");
		getDriver().findElement(By.id("feedbackApprovalBtn")).click();
		Thread.sleep(500);
		String title = getDriver().findElement(By.name("aFeedback")).getAttribute("title");
		Assert.assertEquals(title, "Please provide a feedback");
		getDriver().findElement(By.xpath("//div[@id='multipleApprovalFeedbackModal']/div/div/div[3]/button")).click(); // close
		// button
		Thread.sleep(500);
		testInfo.get().log(Status.SKIP, "Skip approving this test case, but select multiple request");

		// For multiple select rejection
		wait.until(ExpectedConditions.elementToBeClickable(By.id("rejectSelectedBtn")));
		getDriver().findElement(By.id("rejectSelectedBtn")).click(); // Rejection
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='multipleRejectionFeedbackModal']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='multipleRejectionFeedbackModal']/div/div/div/h4",
				"Feedback for bulk rejection");
		getDriver().findElement(By.id("feedbackRejectBtn")).click();
		Thread.sleep(500);
		String title2 = getDriver().findElement(By.name("rFeedback")).getAttribute("title");
		Assert.assertEquals(title2, "Please provide a feedback");
		getDriver().findElement(By.xpath("//div[@id='multipleRejectionFeedbackModal']/div/div/div[3]/button")).click(); // close
		// button
		Thread.sleep(500);
		testInfo.get().log(Status.SKIP, "Skip rejecting this test case, but select multiple request");
		
		getDriver().findElement(By.cssSelector("span.check")).click();
		Thread.sleep(500);

	}

	@Test(groups = { "Regression" })
	public void ApproveVTUOnboardingApprovalTest() throws Exception {

		getDriver().navigate().refresh();
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		scrollUp();
		Thread.sleep(1000);
		TestUtils.clickElement("ID", "toggle");

		// Pending status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		if(TestUtils.isLoaderPresents()) {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		}

		// Select single record

		getDriver().findElement(By.xpath("//td/div/label/span/span")).click();
		getDriver().findElement(By.id("approveSelectedBtn")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("aFeedback")).sendKeys("test");
		getDriver().findElement(By.id("feedbackApprovalBtn")).click();
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		Thread.sleep(500);
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
		String requiredText = expectedText.substring(12, 96);
		String value = "Processed 1 Request(s) - 1 Approved Successfully | 0 Failed | Failure Reason(s): N/A";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}

		/*
		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='approvalRequest']/tbody/tr/td[9]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(2000);
		TestUtils.scrollToElement("ID", "feedback");
		getDriver().findElement(By.id("feedback")).sendKeys("test");
		getDriver().findElement(By.id("approvalBtn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "h2",
				"Are you sure you want to approve this Agent Onboarding request ?");
		Thread.sleep(2000);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click(); // cancel button
		Thread.sleep(2000);
		String screenshotPath2 = TestUtils.addScreenshot();
		testInfo.get().addScreenCaptureFromPath(screenshotPath2);
		String message2 = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid")).getText();
		testInfo.get().log(Status.INFO, " " + message2);
		 */
	}

	@Test(groups = { "Regression" })
	public void RejectVTUOnboardingApprovalTest() throws Exception {

		getDriver().navigate().refresh();
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		Thread.sleep(1000);
		if(TestUtils.isLoaderPresents()) {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		}
		TestUtils.scrollToElement("ID", "approved-vtu");
		getDriver().findElement(By.id("toggle")).click();

		// Pending status
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
		if(TestUtils.isLoaderPresents()) {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		}

		// Select single record
		TestUtils.scrollToElement("XPATH", "//div[2]/div/span/span/span");
		getDriver().findElement(By.xpath("//td/div/label/span/span")).click();
		getDriver().findElement(By.id("rejectSelectedBtn")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.name("rFeedback")).sendKeys("test");
		getDriver().findElement(By.id("feedbackRejectBtn")).click();
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		Thread.sleep(500);
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
		String requiredText = expectedText.substring(12, 96);
		String value = "Processed 1 Request(s) - 1 Rejected Successfully | 0 Failed | Failure Reason(s): N/A";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}

		/*
		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='approvalRequest']/tbody/tr/td[9]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(2000);

		getDriver().findElement(By.id("rejectBtn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "h2",
				"Are you sure you want to reject this Agent Onboarding request ?");
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("(//button[@type='button'])[18]")).click(); // cancel button
		testInfo.get().log(Status.SKIP, " Skipped rejecting this test case");
		Thread.sleep(2000);

		getDriver().findElement(By.xpath("(//button[@type='button'])[6]")).click();
		 */
	}
}
