package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import DealerTestCases.signIn;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class ReturnToDealer extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();

	@Parameters({ "dealerTestEnv", "dealerUser" })
	@Test(groups = { "Regression" })
	public void loginAsDealer(String dealerTestEnv, String dealerUser)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		signIn.login(dealerTestEnv, dealerUser);
	}

	@Parameters({ "downloadPath", "server", "dealerTestEnv" })
	@Test(groups = { "Regression" })
	public void returnToB2BRequest(String server, String downloadPath, String dealerTestEnv)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (dealerTestEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");
		Thread.sleep(500);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		try {
			getDriver().findElement(By.name("173213561Agent Enrollment")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("7883203270Agent Enrollment")).click();
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//h4", "Assigned Kits");

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

		String loc = "IK2 - Ikoyi Service Centre";
		String pic;
		if (server.equals("remote-browserStack")) {

			pic = downloadPath + "image2.jpg";

		} else if (server.equals(remoteJenkins)) {
			pic = downloadPath + "image2.jpg";
		} else {
			pic = System.getProperty("user.dir") + "\\files\\image2.jpg";
		}

		String b2bFilter = "Return B2B request for kit tag: " + kitTag;
		Markup m = MarkupHelper.createLabel(b2bFilter, ExtentColor.BLUE);
		testInfo.get().info(m);

		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag);
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
		if (kitTag.equals(kit)) {
			testInfo.get().info(kitTag + " found");
		} else {
			testInfo.get().error(kitTag + " not found");
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
		logOut();

	}

	@Parameters({ "testEnv", "user" })
	@Test(groups = { "Regression" })
	public void loginAsCACAdmin(String testEnv, String user)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		SignIn.login(testEnv, user);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void approveB2BRequest(String testEnv)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		// Navigate to B2B Request
		String navigateToB2B = "Navigate to B2B Request module";
		Markup n = MarkupHelper.createLabel(navigateToB2B, ExtentColor.BLUE);
		testInfo.get().info(n);
		b2bRequest b2b = new b2bRequest();
		b2b.navigateToB2BRequestTest();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");

		String kitTagFilter = "Filter by kit tag: " + kitTag + " and approve B2B Request";
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		approveRequestTest(testEnv);

	}

	public void approveRequestTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");

		// Filter by Kit Tag
		Thread.sleep(500);
		getDriver().findElement(By.name("searchData")).clear();
		getDriver().findElement(By.name("searchData")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

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
		String approve = "Supply the required field and Click APPROVE button.";
		Markup r = MarkupHelper.createLabel(approve, ExtentColor.BLUE);
		testInfo.get().info(r);
		Thread.sleep(500);
		for (int i = 0; i < pendingApproval; i++) {
			if (i == 0) {
				// approve
				getDriver().findElement(By.name("resolution")).click();
				getDriver().findElement(By.name("resolution")).clear();
				getDriver().findElement(By.name("resolution")).sendKeys("Tested");
				getDriver().findElement(By.id("approveRequest")).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
				TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
						"Are you sure that you want to approve this request?");
				getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
				TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
						"This request was approved successfully.");
				getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
				wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
				wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
						"Processing..."));
			} else {
				Thread.sleep(1000);
				try {
					getDriver().findElement(By.name("searchData")).clear();
					getDriver().findElement(By.name("searchData")).sendKeys(kitTag);
					getDriver().findElement(By.id("searchButton")).click();
					Thread.sleep(500);
					wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
							"Processing..."));
				} catch (Exception e) {
					getDriver().findElement(By.name("searchData")).clear();
					getDriver().findElement(By.name("searchData")).sendKeys(kitTag);
					getDriver().findElement(By.id("searchButton")).click();
					Thread.sleep(500);
					wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
							"Processing..."));
				}

				// View detail modal
				getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
				Thread.sleep(200);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title")));
				TestUtils.scrollToElement("XPATH", "//div[@id='resolveB2bRequest']/div/div/div[3]/button[3]");

				// approve
				getDriver().findElement(By.name("resolution")).click();
				getDriver().findElement(By.name("resolution")).clear();
				getDriver().findElement(By.name("resolution")).sendKeys("Tested");
				getDriver().findElement(By.id("approveRequest")).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
				TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content","Are you sure that you want to approve this request?");
				getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
				TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
						"This request was approved successfully.");
				getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
				wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
				wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
						"Processing..."));
			}
		}
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void returnToDealer(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		try {
			getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		} catch (Exception e) {

		}
		String navigateToB2B = "Navigate to B2B Request module";
		Markup n = MarkupHelper.createLabel(navigateToB2B, ExtentColor.BLUE);
		testInfo.get().info(n);
		b2bRequest b2b = new b2bRequest();
		b2b.navigateToB2BRequestTest();
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");
		Thread.sleep(500);

		String returnKitToDealer = "Return " + kitTag + " back to dealer when it is still blacklisted";
		Markup m = MarkupHelper.createLabel(returnKitToDealer, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchParameter")).clear();
		getDriver().findElement(By.id("searchParameter")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='b2bRequestTable']/tbody/tr/td[3]", kitTag);
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");

		// Navigate to Return to dealer
		getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Return To Dealer')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2",
				"Are you sure you want to return this kit back to its previous dealer?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(2000);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"This kit is blacklisted. Please whitelist the kit before reassigning back to Dealer");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		try {
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		} catch (Exception e) {

		}
		whiteListKitTag(testEnv);
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchParameter")).clear();
		getDriver().findElement(By.id("searchParameter")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='b2bRequestTable']/tbody/tr/td[3]", kitTag);
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");

		// Navigate to Return to dealer
		String returnKit = "Return kit " + kitTag + " back to Dealer after it has been Whitelisted ";
		Markup r = MarkupHelper.createLabel(returnKit, ExtentColor.BLUE);
		testInfo.get().info(r);
		getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Return To Dealer')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2",
				"Are you sure you want to return this kit back to its previous dealer?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Return to Dealer was successful");
		TestUtils.clickElement("CSSSELECTOR", "button.swal2-confirm.swal2-styled");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(2000);
	}

	@Parameters({ "dealerTestEnv", "dealerUser" })
	@Test(groups = { "Regression" })
	public void validateKitReturnedToDealer(String dealerTestEnv, String dealerUser)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		logOut();
		// Login as Dealer and verify that the device is returned back to the dealer but
		// not assigned
		loginAsDealer(dealerTestEnv, dealerUser);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (dealerTestEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("returnToDealer");

		String kitTag = (String) envs.get("kitTag");
		String navigateToAgentEnrollment = "Navigate to Agent Enrollment Module on Dealer";
		Markup ne = MarkupHelper.createLabel(navigateToAgentEnrollment, ExtentColor.BLUE);
		testInfo.get().info(ne);
		
		Thread.sleep(500);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		try {
			getDriver().findElement(By.name("173213561Agent Enrollment")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("7883203270Agent Enrollment")).click();
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//h4", "Assigned Kits");

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

		String verifyKit = "Verify that the kit Tag " + kitTag
				+ " is returned back to the Dealer but not assigned to an agent";
		Markup m = MarkupHelper.createLabel(verifyKit, ExtentColor.BLUE);
		testInfo.get().info(m);

		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[2]", kitTag);
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "UNASSIGNED");
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[3]/span", "WHITELISTED");
		TestUtils.clickElement("XPATH", "//table[@id='kitManagement']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Assign to an Agent')]", "Assign to an Agent");
		getDriver().findElement(By.xpath("//a[contains(text(),'Assign to an Agent')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
	}

	public void whiteListKitTag(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		// Navigate to Blacklist/Whitelist Device Management
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String navigateToBlacklistWhitelistModule = "Navigate to Blacklist/Whitelist module under Device Management";
		Markup n = MarkupHelper.createLabel(navigateToBlacklistWhitelistModule, ExtentColor.BLUE);
		testInfo.get().info(n);
		BlacklistDeviceManagement bd = new BlacklistDeviceManagement();
		bd.navigateToBlacklistDeviceManagementTest(testEnv);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("returnToDealer");
		String userEmail = (String) envs.get("userEmail");
		// WhiteList Kit
		String kitTag = (String) envs.get("kitTag");
		Thread.sleep(500);
		String kitTagFilter = "Filter by kit tag: " + kitTag + " and Whitelist Kit";
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='blackList']/tbody/tr/td[3]", kitTag);
		Thread.sleep(500);

		getDriver().findElement(By.name("deviceIdParam")).clear();
		if (!getDriver().findElement(By.name("macAddressParam")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeleted")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		getDriver().findElement(By.xpath("//table[@id='blackList']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("(//a[contains(text(),'Whitelist')])[2]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistHeader")));

		// Valid approval email address and reason
		String valid = "Submit valid approval email address: " + userEmail + " and correctly fill form";
		Markup r = MarkupHelper.createLabel(valid, ExtentColor.BLUE);
		testInfo.get().info(r);
		Thread.sleep(1000);
		getDriver().findElement(By.id("approvedByEmailAddress")).sendKeys(userEmail);
		getDriver().findElement(By.xpath("//div[@id='whitelistReasonDiv']/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("approvalReasonWhitelist")).sendKeys("Testing");
		getDriver().findElement(By.id("saveBlacklistBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
		String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
		String requiredText = expectedText.substring(12, 47);
		String value = "Request was processed successfully.";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
			getDriver().findElement(By.xpath("//span/i")).click();
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
		// Navigate Back to B2B Request
		String navigateToB2B = "Navigate back to B2B Request module and search for kit: "+kitTag;
		Markup nb = MarkupHelper.createLabel(navigateToB2B, ExtentColor.BLUE);
		testInfo.get().info(nb);
		b2bRequest b2b = new b2bRequest();
		b2b.navigateToB2BRequestTest();
	}

	@Parameters({ "dealerTestEnv" })
	public void fillForm(String dealerTestEnv) throws Exception {

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (dealerTestEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("newKitAssignment");
		JSONObject envs2 = (JSONObject) config.get("returnToDealer");
		String kitTag = (String) envs2.get("kitTag");
		String region = (String) envs.get("region");
		String subRegion = (String) envs.get("subRegion");
		String state = (String) envs.get("state");
		String territory = (String) envs.get("territory");
		String LGA = (String) envs.get("LGA");
		String area = (String) envs.get("area");
		String devicetype = (String) envs.get("devicetype");
		String deviceOwner = (String) envs.get("deviceOwner");
		
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h3", "New Kit Assignment");
		Thread.sleep(1000);

		// For Device
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		String TagUsed = "Assert autopopulated details of kit: " + kitTag;
		Markup d = MarkupHelper.createLabel(TagUsed, ExtentColor.ORANGE);
		testInfo.get().info(d);
		Thread.sleep(500);
		Assertion.assertNewKitAssignment();
		Thread.sleep(1000);

		// For Device type
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
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
		String dealerUsed = "Assert autopopulated details of Dealer";
		Markup f = MarkupHelper.createLabel(dealerUsed, ExtentColor.ORANGE);
		testInfo.get().info(f);
		TestUtils.assertSearchText("XPATH", "//h4", "Dealer Information");
		Assertion.dealerAutopopulatedDetails();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[2]/div/div[4]/div/span/span/span");

		// Select region
		getDriver().findElement(By.xpath("//div[2]/div/div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(region);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select sub region
		getDriver().findElement(By.xpath("//span[2]/span/span")).click();
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(subRegion);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select State
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select Territory
		getDriver().findElement(By.xpath("//div[7]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(territory);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select LGA
		getDriver().findElement(By.xpath("//div[8]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(LGA);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select Area
		getDriver().findElement(By.xpath("//div[9]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);
	}

	@Parameters({ "dealerTestEnv", "server", "downloadPath" })
	@Test(groups = { "Regression" })
	public void assignKitToAnAgent(String dealerTestEnv, String server, String downloadPath) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (dealerTestEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("returnToDealer");
		// WhiteList Kit
		String kitTag = (String) envs.get("kitTag");
		String phoneNumber = TestUtils.generatePhoneNumber2();
		String outlet = (String) envs.get("outlet");
		String gender = (String) envs.get("gender");
		String firstName = (String) envs.get("firstName");
		String surName = (String) envs.get("surName");
		String nubanPhoneNumber = TestUtils.generatePhoneNumber();
		String vtuNumber = TestUtils.generatePhoneNumber();

		String pic;
		if (server.equals("remote-browserStack")) {

			pic = downloadPath + "image2.jpg";

		} else if (server.equals(remoteJenkins)) {
			pic = downloadPath + "image2.jpg";
		} else {
			pic = System.getProperty("user.dir") + "\\files\\image2.jpg";
		}

		fillForm(dealerTestEnv);
		Thread.sleep(500);
		// For Agent
		TestUtils.assertSearchText("XPATH", "//div[@id='agent']/div/div/h4", "Agent Information");
		Thread.sleep(500);
		String assignKit = "Assign kit to agent";
		Markup v = MarkupHelper.createLabel(assignKit, ExtentColor.BLUE);
		testInfo.get().info(v);

		getDriver().findElement(By.cssSelector("span.check")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(surName);
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
		Thread.sleep(500);

		getDriver().findElement(By.id("agentNubanNumber")).clear();
		getDriver().findElement(By.id("agentNubanNumber")).sendKeys(nubanPhoneNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentVtuNumber")).clear();
		getDriver().findElement(By.id("agentVtuNumber")).sendKeys(vtuNumber);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[12]/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(gender);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "editIdCard");
		WebElement input1 = getDriver().findElement(By.id("editIdCard"));
		input1.sendKeys(pic);
		testInfo.get().info("Picture successfully updated");
		Thread.sleep(500);

		getDriver().findElement(By.xpath("//div[13]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(outlet);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("finish")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//h2", "Confirm");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"a pending request already exists for this kit, do you want to proceed?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//h2", "Success");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit was successfully assigned to agent");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h4", "Assigned Kits");
		Thread.sleep(500);
		
		// Check for kitTag
		String kitTagUsed = "Verifying Approval status of kitTag";
		Markup a = MarkupHelper.createLabel(kitTagUsed, ExtentColor.BLUE);
		testInfo.get().info(a);
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTagParam")).clear();
		getDriver().findElement(By.id("kitTagParam")).sendKeys(kitTag);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='kitManagement']/tbody/tr/td[6]/span", "PENDING");
		Thread.sleep(500);
	}

	public void logOut() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("#navbarDropdownMenuLink > i.material-icons.text_align-center.visible-on-sidebar-regular")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("Logout")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
	}

	@Parameters({ "testEnv", "user" })
	@Test(groups = { "Regression" })
	public void approveKitAssignedToAgent(String testEnv, String user)
			throws FileNotFoundException, InterruptedException, IOException, ParseException {
		logOut();
		loginAsCACAdmin(testEnv, user);
		String navigateToAgentEnrollment = "Navigate to Agent Enrollment on CAC Admin and Approve Kit Mapping request";
		Markup n = MarkupHelper.createLabel(navigateToAgentEnrollment, ExtentColor.BLUE);
		testInfo.get().info(n);
		AgentEnrollment ag = new AgentEnrollment();
		ag.navigateToAgentEnrollmentTest();
		ag.approveNewAgentMapping(testEnv);
	}

}
