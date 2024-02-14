package DealerTestCases;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class AgentEnrollment extends TestBase {

	private static String NubanPhoneNumber = TestUtils.generatePhoneNumber();
	private static String VTUNumber = TestUtils.generatePhoneNumber();
	private static String phoneNumber = TestUtils.generatePhoneNumber();

	@Test(groups = { "Regression" })
	public void navigateToAgentEnrollmentTest() throws InterruptedException {
		TestUtils.testTitle("Navigate to Agent Enrollment");
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		try {
			getDriver().findElement(By.name("1036Agent Enrollment")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("1036Agent Enrollment")).click();
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void exportfileTest() throws Exception {
		TestUtils.testTitle("Export File");

		// PDF download
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div[2]/a");

		// EXCEL download
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div[2]/a[2]");

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewKitMappingDetailsTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		/*getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();*/
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		/*getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}*/

		// Filter by Approved
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		String kitTag = getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[2]")).getText();
		TestUtils.testTitle("View full details of kit tag: " + kitTag);
		
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/h4")));

		// Page assertion
		TestUtils.assertSearchText("XPATH", "//div[2]/div/div/h4", "View Details");
		Assertion.imageDisplayAgentEnrolment();
		Thread.sleep(1000);
		Assertion.assertDeviceDealerInformation("testEnv");
		TestUtils.scrollUntilElementIsVisible("XPATH", "//div[2]/div/div/h4");
		Thread.sleep(1000);

		// return button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")));
		getDriver().findElement(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void editExistingAgentDetailsTest(String testEnv) throws Exception {

		navigateToAgentEnrollmentTest();
		String outletOwnerNumber = TestUtils.generatePhoneNumber();
		String outletOwnerVtu = TestUtils.generatePhoneNumber();
		String firstName = "Buchi";
		String surName = "Jennifer";

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

		String outletType = (String) envs.get("outletType");
		String outletOwnerName = (String) envs.get("outletOwnerName");

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		// Filter by Approved
		TestUtils.testTitle("Filter and select an APPROVED request");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "APPROVED");

		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/h4")));
		TestUtils.testTitle("View and Edit the Approved request");
		TestUtils.assertSearchText("XPATH", "//h6", "ID CARD");
		//TestUtils.clickElement("XPATH", "//h4/a/i");
		TestUtils.clickElement("CSSSELECTOR", "i.material-icons.pull-right");
		Thread.sleep(500);
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(surName);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentNubanNumber")).clear();
		getDriver().findElement(By.id("agentNubanNumber")).sendKeys(NubanPhoneNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentVtuNumber")).clear();
		getDriver().findElement(By.id("agentVtuNumber")).sendKeys(VTUNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
		testInfo.get().info("<b>New Phone Number: </b>" +phoneNumber);
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "agentUpdateButton");

		getDriver().findElement(By.id("select2-outletTypeSelect-container")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(outletType);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("outletOwnerName")).clear();
		getDriver().findElement(By.id("outletOwnerName")).sendKeys(outletOwnerName);
		Thread.sleep(500);
		getDriver().findElement(By.id("outletOwnerNumber")).clear();
		getDriver().findElement(By.id("outletOwnerNumber")).sendKeys(outletOwnerNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("outletOwnerVtu")).clear();
		getDriver().findElement(By.id("outletOwnerVtu")).sendKeys(outletOwnerVtu);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[@id='agentUpdateButton']/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		WebElement div_alert = getDriver().findElement(By.cssSelector("div.alert.alert-success > div.container-fluid"));
		String expected_message = "Agent's Information was successfully updated";
		TestUtils.assertDivAlert(div_alert, expected_message);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span > i.material-icons")).click();

		// return button
		getDriver().findElement(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");

	}

	/*@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void retrieveKitTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);

		// With Approval status of APPROVED
		String kitFilter = "Retrieve kit with approval status as APPROVED";
		Markup m = MarkupHelper.createLabel(kitFilter, ExtentColor.ORANGE);
		testInfo.get().info(m);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "APPROVED");
		getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr[2]/td[7]/div/a/i")).click();
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr[2]/td[7]/div/ul/li/a",
				"View Details");

		TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'Return to B2B')])[2]", "Return to B2B");
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr[2]/td[7]/div/ul/li[3]/a",
					"Retrieve Kit");

		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'Retrieve Kit')])[2]", "Retrieve Kit");
		}
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//a[contains(text(),'Retrieve Kit')]");
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "myModalLabel", "Kit Retrieval");
		Assertion.assertKitRetieval();

		// Check for empty retrieval reason
		getDriver().findElement(By.xpath("//button[2]")).click();
		Thread.sleep(500);
		WebElement reason = getDriver().findElement(By.name("retrievalReason"));
		String validationMessage = reason.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage, "Please select an item in the list.");
		testInfo.get().info("Empty Retrieval reason field: " + validationMessage);
		Thread.sleep(500);

		// Check for retrieval reason
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Non-Compliant Outlet");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Zero Performance");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Others");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys("Testing");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.btn.btn-link.text-secondary")).click();
		Thread.sleep(500);

		// With Approval status of DISAPPROVED
		String kit1Filter = "Retrieve kit with approval status as DISAPPROVED";
		Markup o = MarkupHelper.createLabel(kit1Filter, ExtentColor.ORANGE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("DISAPPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "DISAPPROVED");
		getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i")).click();
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Details')]", "View Details");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Return to B2B')]", "Return to B2B");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Retrieve Kit')]", "Retrieve Kit");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//a[contains(text(),'Retrieve Kit')]");
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "myModalLabel", "Kit Retrieval");
		Assertion.assertKitRetieval();
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("No-ID Card");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys("Testing");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.btn.btn-link.text-secondary")).click();
		Thread.sleep(500);

		// With Approval status of REASSIGNED
		String kit2Filter = "Retrieve kit with approval status as CANCELLED";
		Markup r = MarkupHelper.createLabel(kit2Filter, ExtentColor.ORANGE);
		testInfo.get().info(r);
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("REASSIGNED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "REASSIGNED");
			getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i")).click();
			TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[7]/div/ul/li/a",
					"View Details");
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Return to B2B')]", "Return to B2B");
			TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Retrieve Kit')]", "Retrieve Kit");
			Thread.sleep(500);
			TestUtils.clickElement("XPATH", "//a[contains(text(),'Retrieve Kit')]");
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "myModalLabel", "Kit Retrieval");
			Assertion.assertKitRetieval();
			getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("No-ID Card");
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("feedback")).clear();
			getDriver().findElement(By.id("feedback")).sendKeys("Testing");
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("button.btn.btn-link.text-secondary")).click();
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			Thread.sleep(500);
		}

		// With Approval status of UNASSIGNED
		String kit5Filter = "Retrieve kit with approval status as UNASSIGNED";
		Markup b = MarkupHelper.createLabel(kit5Filter, ExtentColor.ORANGE);
		testInfo.get().info(b);
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("UNASSIGNED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "UNASSIGNED");
		getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i")).click();
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Assign to an Agent')]", "Assign to an Agent");
		Thread.sleep(500);

		// With Approval status of PENDING
		String kit4Filter = "Retrieve kit with approval status as PENDING";
		Markup c = MarkupHelper.createLabel(kit4Filter, ExtentColor.ORANGE);
		testInfo.get().info(c);
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "PENDING");
		getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i")).click();
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'View Details')]", "View Details");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Return to B2B')]", "Return to B2B");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Retrieve Kit')]", "Retrieve Kit");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Retrieve Kit')]")).click();
		Thread.sleep(500);
		Assertion.assertKitRetieval();
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unavailable Agent");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys("Testing");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.btn.btn-link.text-secondary")).click();
		Thread.sleep(500);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);
	}

	@Parameters({ "downloadPath", "server", "testEnv" })
	@Test(groups = { "Regression" })
	public void returnToB2BTest(String server, String downloadPath, String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String kitTag1 = (String) envs.get("kitTag1");
		String loc = "IK2 - Ikoyi Service Centre";
		String pic;
		if (server.equals("remote-browserStack")) {

			pic = downloadPath + "image2.jpg";

		} else if (server.equals(remoteJenkins)) {
			pic = downloadPath + "image2.jpg";
		} else {
			pic = System.getProperty("user.dir") + "\\files\\image2.jpg";
		}

		String b2bFilter = "Return B2B for kit tag: " + kitTag1;
		Markup m = MarkupHelper.createLabel(b2bFilter, ExtentColor.BLUE);
		testInfo.get().info(m);

		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag1);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Return to B2B')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("requestForB2bForm")));
		TestUtils.assertSearchText("XPATH", "//div[4]/div/div/div/h4", "Return to B2B");

		String kit = getDriver().findElement(By.id("kitTag")).getAttribute("value");
		if (kitTag1.equals(kit)) {
			testInfo.get().info(kitTag1 + " found");
		} else {
			testInfo.get().error(kitTag1 + " not found");
		}

		// Check for empty fields
		String emptyFilter = "Assert empty fields";
		Markup g = MarkupHelper.createLabel(emptyFilter, ExtentColor.ORANGE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("submitB2bRequest")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//label[@id='b2bLocationPk-error']/p", "B2B Location is required");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//label[@id='reason-error']/p",
				"Please Indicate reason for this B2B request");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//label[@id='kitPicture-error']/p", "Please Upload Kit Picture");

		// Fill return to B2B form
		String fillFilter = "Fill return to B2B form";
		Markup t = MarkupHelper.createLabel(fillFilter, ExtentColor.BLUE);
		testInfo.get().info(t);
		new Select(getDriver().findElement(By.id("b2bLocationPk"))).selectByVisibleText(loc);
		Thread.sleep(500);
		getDriver().findElement(By.id("reason")).clear();
		getDriver().findElement(By.id("reason")).sendKeys("Testing");
		Thread.sleep(500);

		WebElement input1 = getDriver().findElement(By.id("kitPicture"));
		input1.sendKeys(pic);
		testInfo.get().info("Picture successfully updated");
		Thread.sleep(500);

		getDriver().findElement(By.id("submitB2bRequest")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		if (getDriver().findElement(By.xpath("//h2")).getText().equals("Success")) {
			TestUtils.assertSearchText("XPATH", "//h2", "Success");
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "B2B Request was successful");
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		} else if (getDriver().findElement(By.xpath("//h2")).getText().equals("Notification")) {
			TestUtils.assertSearchText("XPATH", "//h2", "Notification");
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
					"B2b request has already been raised for this Kit");
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(500);
			TestUtils.clickElement("XPATH", "//div[4]/div/div/form/div[2]/button");
			Thread.sleep(500);
		}
		TestUtils.assertSearchText("XPATH", "//h4", "Assigned Kits");

	}*/

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String kitTag = (String) envs.get("kitTag");
		TestUtils.testTitle("Filter by kit tag: " + kitTag);
		getDriver().findElement(By.id("kitTagParam")).clear();
		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[2]", kitTag);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDeviceIDTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String deviceID = (String) envs.get("deviceID");
		TestUtils.testTitle("Filter by device ID: " + deviceID);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			getDriver().findElement(By.xpath("//td[7]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.linkText("View Details")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			String devID = getDriver().findElement(By.id("deviceId")).getAttribute("value");
			Assert.assertEquals(devID, deviceID);
			testInfo.get().info(devID + " Device ID found");
			getDriver().findElement(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
			TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");

		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByAgentTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String assignedAgent = (String) envs.get("assignedAgent");
		
		TestUtils.testTitle("Filter by assigned agent: " + assignedAgent);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("usernameSearchParam")).sendKeys(assignedAgent);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//td[5]", assignedAgent);
	}

	/*@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByMacAddressTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String macAddress = (String) envs.get("macAddress");

		String macFilter = "Filter by mac address: " + macAddress;
		Markup m = MarkupHelper.createLabel(macFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);

		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).sendKeys(macAddress);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

		String macAdres = getDriver().findElement(By.id("mac")).getAttribute("value");
		Assert.assertEquals(macAdres, macAddress);
		testInfo.get().info(macAdres + " Mac Address found");
		getDriver().findElement(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//h4", "Assigned Kits");

	}*/

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDeviceTypeTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("kitMapping_Dealer");

		String deviceType = (String) envs.get("deviceType");
		
		TestUtils.testTitle("Filter by device type: " + deviceType);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			TestUtils.clickElement("CSSSELECTOR", "span.select2-selection__clear");
		}

		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(deviceType);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		getDriver().findElement(By.xpath("//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("View Details")).click();
		Thread.sleep(300);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

		String devType = getDriver().findElement(By.id("deviceType")).getAttribute("value");
		if (devType.equalsIgnoreCase(deviceType)) {
			testInfo.get().info(devType + "Device type found");
		} else {
			testInfo.get().error("Device type not found");
		}

		getDriver().findElement(By.xpath("//a[contains(@href, '/simrop/dealers/manage-kits')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");
	}

	@Test(groups = { "Regression" })
	public void searchByDeviceStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();

		// Filter by Blacklist
		TestUtils.testTitle("Filter by Device Status: Blacklist");
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("BLACKLISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "BLACKLISTED");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// Filter by Whitelist
		TestUtils.testTitle("Filter by Device Status: whitelist");
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("WHITELISTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "WHITELISTED");
	}

	@Test(groups = { "Regression" })
	public void searchByApprovalStatusTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("deviceIdSearchParam")).clear();
		if (!getDriver().findElement(By.name("usernameSearchParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("usernameSearchParam")).clear();
		getDriver().findElement(By.name("macAddressSearchParam")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}

		// Filter by Pending
		TestUtils.testTitle("Filter by Approval Status: Pending");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "PENDING");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Filter by Approved
		TestUtils.testTitle("Filter by Approval Status: Approved");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("APPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "APPROVED");

		// Filter by Disapproved
		TestUtils.testTitle("Filter by Approval Status: Disapproved");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("DISAPPROVED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "DISAPPROVED");

		// Filter by Unassigned
		TestUtils.testTitle("Filter by Approval Status: Unassigned");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("UNASSIGNED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "UNASSIGNED");

		// Filter by Reassigned
		TestUtils.testTitle("Filter by Approval Status: Reassigned");
		getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("REASSIGNED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "REASSIGNED");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	}

	@Parameters({ "testEnv", "kitTag" })
	public static void fillForm(String testEnv, String kitTag) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

		String region = (String) envs.get("region");
		String subRegion = (String) envs.get("subRegion");
		String state = (String) envs.get("state");
		String territory = (String) envs.get("territory");
		String LGA = (String) envs.get("LGA");
		String area = (String) envs.get("area");
		String devicetype = (String) envs.get("devicetype");
		String deviceOwner = (String) envs.get("deviceOwner");
		
		TestUtils.testTitle("KitTag used for new kit assignment: " + kitTag);
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("XPATH", "//h3", "New Kit Assignment");
		Thread.sleep(1000);

		// For Device
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).clear();
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(kitTag);
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Assert autopopulated details of kit");
		Assertion.assertNewKitAssignment();
		Thread.sleep(500);

		// For Device type
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).clear();
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(devicetype);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// For Device Owner
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(deviceOwner);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// For Dealer
		TestUtils.testTitle("Assert autopopulated details of Dealer");
		TestUtils.assertSearchText("XPATH", "//div[@id='dealer']/h4", "Dealer Information");
		Assertion.dealerAutopopulatedDetails();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[2]/div/div[4]/div/span/span/span");

		// Select region
		getDriver().findElement(By.xpath("//div[2]/div/div[4]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(region);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);

		// Select sub region
		getDriver().findElement(By.xpath("//span[2]/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(subRegion);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);

		// Select State
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);

		// Select Territory
		getDriver().findElement(By.xpath("//div[7]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(territory);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);

		// Select LGA
		getDriver().findElement(By.xpath("//div[8]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(LGA);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);

		// Select Area
		getDriver().findElement(By.xpath("//div[9]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(1000);
	}

	@Parameters({ "testEnv", "server", "downloadPath" })
	@Test(groups = { "Regression" })
	public void newKitAssignmentOfNewUserTest(String testEnv, String server, String downloadPath) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String outletOwnerNumber = TestUtils.generatePhoneNumber();
		String outletOwnerVtu = TestUtils.generatePhoneNumber();
		String phonNum = TestUtils.generatePhoneNumber2();
		String firstName = "Sanity" + phonNum;
		String surName = "Sfxtest";

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

		String kitTag = (String) envs.get("kitTag");
		String outlet = (String) envs.get("outlet");
		String gender = (String) envs.get("gender");
		String outletType = (String) envs.get("outletType");
		String location = (String) envs.get("location");
		String outletName = (String) envs.get("outletName");
		String outletOwnerName = (String) envs.get("outletOwnerName");
		String pic = "image2.jpg";

		fillForm(testEnv, kitTag);

		// For Agent
		TestUtils.testTitle("Assign new kit to new user");
		TestUtils.assertSearchText("XPATH", "//div[@id='agent']/div/div/h4", "Agent Information");
		
		// Select new user
		TestUtils.clickElement("XPATH", "//label/span/span");
		Thread.sleep(500);

		// New user details
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(surName);
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("phoneNumber")).sendKeys(phonNum);
		Thread.sleep(1000);
		String userName = getDriver().findElement(By.id("emailAddress")).getAttribute("value");
		TestUtils.testTitle("New user email: " + userName);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentNubanNumber")).clear();
		getDriver().findElement(By.id("agentNubanNumber")).sendKeys(NubanPhoneNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentVtuNumber")).clear();
		getDriver().findElement(By.id("agentVtuNumber")).sendKeys(VTUNumber);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[12]/div/div/span/span/span")).click();
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(gender);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "finish");
		Thread.sleep(500);
		TestUtils.uploadFile(By.id("editIdCard"), pic);
		testInfo.get().info("Picture successfully updated");
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "agentVtuNumber");

		// Select Outlet
		new Select(getDriver().findElement(By.id("outlet"))).selectByVisibleText(outlet);
		new Select(getDriver().findElement(By.id("outletType"))).selectByVisibleText(outletType);
		Thread.sleep(500);
		getDriver().findElement(By.id("formatted_address")).sendKeys(location);
		getDriver().findElement(By.id("outletName")).sendKeys(outletName);
		Thread.sleep(500);
		getDriver().findElement(By.id("outletOwnerName")).sendKeys(outletOwnerName);
		getDriver().findElement(By.id("outletOwnerNumber")).sendKeys(outletOwnerNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("outletOwnerVtu")).sendKeys(outletOwnerVtu);

		// Upload new outlet image
		TestUtils.uploadFile(By.id("newOutletPicture"), pic);
		testInfo.get().info("Picture successfully updated");
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "finish");
		getDriver().findElement(By.id("finish")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"a pending request already exists for this kit, do you want to proceed?");
		TestUtils.assertSearchText("XPATH", "//h2", "Confirm");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Success!");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit was successfully assigned to agent");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");
		Thread.sleep(500);

		// Check for kitTag
		TestUtils.testTitle("Verifying Approval status of kitTag");
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTagParam")).clear();
		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "PENDING");
		Thread.sleep(500);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void newKitAssignmentOfExistingUserTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

		String existingUser = (String) envs.get("existingUser");
		String kitTag1 = (String) envs.get("kitTag1");
		String outlet1 = (String) envs.get("outlet1");

		navigateToAgentEnrollmentTest();
		fillForm(testEnv, kitTag1);
		TestUtils.testTitle("Assign new kit to existing email: " + existingUser);

		// For Agent
		TestUtils.assertSearchText("XPATH", "//div[@id='agent']/div/div/h4", "Agent Information");
		// Select existing user
		if (getDriver().findElement(By.cssSelector("span.select2-selection__clear")).isDisplayed()) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.xpath("//div[3]/div/div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(existingUser);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Assert autopopulated details of existing Agent");
		Assertion.assertExistingAgentInformation();
		Thread.sleep(500);

		getDriver().findElement(By.xpath("//div[13]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(outlet1);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "finish");
		getDriver().findElement(By.id("finish")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirm");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"a pending request already exists for this kit, do you want to proceed?");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Success!");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit was successfully assigned to agent");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");
		Thread.sleep(500);

		// Check for approved kitTag
		TestUtils.testTitle("Verifying Approval status of kitTag");
		getDriver().findElement(By.id("kitTagParam")).clear();
		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag1);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "APPROVED");
		Thread.sleep(500);
	}

	@Parameters({ "testEnv", "server", "downloadPath" })
	@Test(groups = { "Regression" })
	public void existingDetailsValidationTest(String testEnv, String downloadPath, String server) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String phonNum = TestUtils.generatePhoneNumber2();
//		String firstName = "Gera" + phonNum;
//		String surName = "Som";

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

//		String existingPhoNum = (String) envs.get("existingPhoNum");
		String outlet2 = (String) envs.get("outlet2");
		String gender = (String) envs.get("gender");
		String kitTag = (String) envs.get("kitTag");
		String existingFirstName = (String) envs.get("existingFirstName");
		String existingSurname = (String) envs.get("existingSurname");
		String pic = "image2.jpg";

		navigateToAgentEnrollmentTest();
		fillForm(testEnv, kitTag);

		// For Agent
		TestUtils.assertSearchText("XPATH", "//div[@id='agent']/div/div/h4", "Agent Information");

		// Check for empty fields
		TestUtils.testTitle("Assert empty fields");
		TestUtils.clickElement("XPATH", "//label/span/span");
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName")));
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("seamfix");
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys("sanity");
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "finish");
		getDriver().findElement(By.id("finish")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Please upload Agent ID Card to proceed.')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Please upload Agent ID Card to proceed.')]",
				"Please upload Agent ID Card to proceed.");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		TestUtils.scrollToElement("XPATH", "//label/span/span");
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "phoneNumber-error", "Please Indicate Agent Phone Number");
		TestUtils.assertSearchText("XPATH", "//label[@id='agentNubanNumber-error']/p",
				"Please Indicate Agent Nuban Number");
		TestUtils.assertSearchText("XPATH", "//label[@id='agentVtuNumber-error']/p",
				"Please Indicate Agent Phone Number");
		TestUtils.assertSearchText("XPATH", "//label[@id='agentGender-error']/p",
				"You need to specify Agent Gender for New Agents");
		Thread.sleep(500);

		/*
		 * // Check for existing phone number String pho =
		 * "Assign new kit to existing phone Number: " + existingPhoNum; Markup m =
		 * MarkupHelper.createLabel(pho, ExtentColor.BLUE); testInfo.get().info(m);
		 * getDriver().findElement(By.id("firstName")).clear();
		 * getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		 * Thread.sleep(500); getDriver().findElement(By.id("surname")).clear();
		 * getDriver().findElement(By.id("surname")).sendKeys(surName);
		 * Thread.sleep(500); getDriver().findElement(By.id("phoneNumber")).clear();
		 * getDriver().findElement (By.id("phoneNumber")).sendKeys(existingPhoNum);
		 * Thread.sleep(500);
		 * getDriver().findElement(By.id("agentNubanNumber")).clear(); getDriver(
		 * ).findElement(By.id("agentNubanNumber")).sendKeys(NubanPhoneNumber);
		 * Thread.sleep(500); getDriver().findElement(By.id("agentVtuNumber")).clear();
		 * getDriver().findElement(By.id("agentVtuNumber")).sendKeys(VTUNumber);
		 * Thread.sleep(500);
		 * getDriver().findElement(By.xpath("//div[12]/div/div/span/span/span"
		 * )).click(); Thread.sleep(500); getDriver().findElement(By.cssSelector(
		 * "input.select2-search__field")).sendKeys(gender); Thread.sleep(500);
		 * getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		 * Thread.sleep(500); TestUtils.scrollToElement("ID", "finish"); WebElement
		 * input = getDriver().findElement(By.id("editIdCard")); input.sendKeys(pic);
		 * testInfo.get().info("Picture successfully updated"); Thread.sleep(500);
		 * 
		 * getDriver().findElement(By.xpath("//div[13]/div/span/span/span")).click ();
		 * Thread.sleep(500);
		 * getDriver().findElement(By.cssSelector("input.select2-search__field"
		 * )).sendKeys(outlet1); Thread.sleep(500);
		 * getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		 * Thread.sleep(500); getDriver().findElement(By.id("finish")).click();
		 * Thread.sleep(1000); TestUtils.assertSearchText("XPATH", "//h2", "Confirm");
		 * TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
		 * "a pending request already exists for this kit, do you want to proceed?" );
		 * getDriver().findElement(By.cssSelector(
		 * "button.swal2-confirm.success.swal2-styled")).click(); Thread.sleep(500);
		 * 
		 * TestUtils.assertSearchText("XPATH", "//h2", "Notification");
		 * TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
		 * "Agent with this phone number already exists"); getDriver().findElement
		 * (By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		 * Thread.sleep(500);
		 */

		// Check for existing email
		TestUtils.testTitle("Assign new kit to existing email");
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(existingFirstName);
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(existingSurname);
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("phoneNumber")).sendKeys(phonNum);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentNubanNumber")).clear();
		getDriver().findElement(By.id("agentNubanNumber")).sendKeys(NubanPhoneNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentVtuNumber")).clear();
		getDriver().findElement(By.id("agentVtuNumber")).sendKeys(VTUNumber);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[12]/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(gender);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		String username = getDriver().findElement(By.id("emailAddress")).getAttribute("value");
		int combinations = 0;
		if (Pattern.compile("[0-9]").matcher(username).find()) {
			combinations = combinations + 10;
			testInfo.get().info("<b>" + username + "</b> : New Email Address found");
		} else if (Pattern.compile("[a-z]").matcher(username).find()) {
			combinations = combinations + 26;
			testInfo.get().error("<b>" + username + "</b> : Existing Email Address found");
		} else if (Pattern.compile("[A-Z]").matcher(username).find()) {
			combinations = combinations + 26;
			testInfo.get().error("<b>" + username + "</b> : Existing Email Address found");
		}
		
		Thread.sleep(500);

		
	//	getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		
		Thread.sleep(500);
//		TestUtils.scrollToElement("ID", "outletName");
//		getDriver().findElement(By.id("outletName")).clear();
//		getDriver().findElement(By.id("outletName")).sendKeys(outlet);
//		Thread.sleep(500);
	//	getDriver().findElement(By.xpath("//input[@id='editIdCard']")).click();
	//	TestUtils.scrollToElement("ID", "previewAgentIdCard");
//		TestUtils.uploadFile(By.id("previewAgentIdCard"), pic);
//		testInfo.get().info("Picture successfully updated");
//		Thread.sleep(500);
		
		TestUtils.scrollToElement("ID", "editIdCard");
		TestUtils.uploadFile(By.id("editIdCard"), pic);
		testInfo.get().info("Picture successfully updated");
		
		Thread.sleep(500);
		
		getDriver().findElement(By.xpath("//div[13]/div/span/span/span")).click();
		Thread.sleep(500);
//		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(outlet); 
		new Select(getDriver().findElement(By.id("outlet"))).selectByVisibleText(outlet2);
//		getDriver().findElement(By.xpath("//li[contains(text(),'Glo HQ')]")).click();
		Thread.sleep(1000);
		
		getDriver().findElement(By.id("finish")).click();
		
		Thread.sleep(500);
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.swal2-content")));
		
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirm");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"a pending request already exists for this kit, do you want to proceed?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		if (getDriver().findElement(By.xpath("//h2")).getText().equals("Success")) {
			TestUtils.assertSearchText("XPATH", "//h2", "Success");
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit was successfully assigned to agent");
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			Thread.sleep(500);

		} else if (getDriver().findElement(By.xpath("//h2")).getText().equals("Notification")) {
			TestUtils.assertSearchText("XPATH", "//h2", "Notification");
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
					"Agent with this email address already exists");
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(1000);
		}
	}

}
