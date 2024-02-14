package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class DealerAccountCreation extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	private String newPhoneNumber = TestUtils.generatePhoneNumber();
	private String newPhoneNumber1 = TestUtils.generatePhoneNumber();

	private String deletedDealer;
	private String dealerTyper;
	private String dealerPhone;
	private String dealerEmail;
	private String dealerName;
	private String email;
	private String dealerPhone2;
	private String Region;
	private String de_activateDealer;
	private String approvedBy;
	private String approvalComment;
	private String dealerHistory;
	private String performedBy;
	private String disapprovalComment;

	@Parameters({ "testEnv" })
	@BeforeMethod
	public void parseJson(String testEnv) throws IOException, ParseException {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));


		JSONObject envs = (JSONObject) config.get("dealermanagement");

		deletedDealer = (String) envs.get("deletedDealer");
		dealerTyper = (String) envs.get("dealerTyper");
		dealerPhone = (String) envs.get("dealerPhone");
		dealerEmail = (String) envs.get("dealerEmail");
		dealerName = (String) envs.get("dealerName");

		JSONObject envs2 = (JSONObject) config.get("valid_Admin_Login");

		email = (String) envs2.get("email");
		dealerPhone2 = (String) envs.get("dealerPhone2");
		dealerTyper = (String) envs.get("dealerTyper");
		Region = (String) envs.get("Region");
		de_activateDealer = (String) envs.get("de_activateDealer");
		approvedBy = (String) envs.get("approvedBy");
		approvalComment = (String) envs.get("approvalComment");
		dealerHistory = (String) envs.get("dealerHistory");
		performedBy = (String) envs.get("performedBy");
		disapprovalComment = (String) envs.get("disapprovalComment");

	}

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("XPATH", "//table[@id='dealerTable']/tbody");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("NAME", "name");
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToDealerAccountCreation(String testEnv) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate dealer account creation");
		Thread.sleep(1000);
		if (testEnv.equalsIgnoreCase("stagingData")) {
			try {
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.linkText("Account Creation")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.linkText("Account Creation")).click();
				Thread.sleep(500);
			}
		} else {
			try {
				getDriver().findElement(By.cssSelector("a[name=\"1012User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.linkText("Account Creation")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				getDriver().findElement(By.cssSelector("a[name=\"8954091967User Management\"] > p")).click();
				Thread.sleep(500);
				getDriver().findElement(By.name("17321399899Account Creation")).click();
				Thread.sleep(500);
			}
		}
	
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/div")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a/div")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div[3]/div/h4", "Dealers");

	}

	@Test(groups = { "Regression" })
	public void assertDealerAccountCount() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total_dealers")));
		String totalDealersValString = getDriver().findElement(By.id("total_dealers")).getText();
		String activeDealersValString = getDriver().findElement(By.id("active_dealers")).getText();
		String inactiveDealersValString = getDriver().findElement(By.id("inactive_dealers")).getText();

		int actualTotalDealerslVal = TestUtils.convertToInt(totalDealersValString);
		int actualActiveDealersVal = TestUtils.convertToInt(activeDealersValString);
		int actualInactiveDealersVal = TestUtils.convertToInt(inactiveDealersValString);

		int expectedTotalDealersVal = actualActiveDealersVal + actualInactiveDealersVal;
		
		TestUtils.testTitle("Test to assert count of dealer acounts");


		try {
			Assert.assertEquals(expectedTotalDealersVal, actualTotalDealerslVal);
			testInfo.get().log(Status.INFO,
					"Total dealers (" + expectedTotalDealersVal + ") is equal to number of Active dealers ("
							+ actualActiveDealersVal + ") + Inactive dealers (" + actualInactiveDealersVal + ").");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		// Change page size
		new Select(getDriver().findElement(By.name("dealerTable_length"))).selectByVisibleText("300");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 300");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='dealerTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of user returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}

	}

	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.scrollToElement("ID", "total_dealers");

		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		if (!getDriver().findElement(By.id("includeDeletedDealers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}

		getDriver().navigate().refresh();
		// Inactive status
		TestUtils.testTitle("Filter with status: Inactive");
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Inactive");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-inactive", "INACTIVE");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		getDriver().navigate().refresh();

        if (!getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).isDisplayed()) {
            getDriver().findElement(By.id("advancedBtn")).click();
        }
		// Active status
		TestUtils.testTitle("Filter with status: Active");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Active");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-active", "ACTIVE");
		
		// Blacklist status
		TestUtils.testTitle("Filter with status: Blacklist");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Blacklist");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[7]/span", "BLACKLIST");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByDeletedDealerEmail(String testEnv) throws InterruptedException, IOException, ParseException {

		TestUtils.testTitle("Filter by deleted dealer email address:" + deletedDealer);
		getDriver().navigate().refresh();
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(deletedDealer);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		if (!getDriver().findElement(By.id("includeDeletedDealers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[4]", deletedDealer);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByDealerTypeTest(String testEnv)
			throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by dealer type: " + dealerTyper);

		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.id("phone")).clear();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerTyper);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[5]", dealerTyper);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByPhoneResetPasswordTest(String testEnv)
			throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		TestUtils.testTitle("Filter by phone: " + dealerPhone);
		Thread.sleep(1000);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.cssSelector("#phone")).clear();
		getDriver().findElement(By.cssSelector("#phone")).sendKeys(dealerPhone);
		getDriver().findElement(By.id("searchdealers")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//td")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//tbody/tr/td[6]", dealerPhone);

		// Reset Password
		String email = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[4]")).getText();
		TestUtils.testTitle("reset password for dealer with email address " + email);
		getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("Reset password")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"A new password has been sent to " + dealerPhone);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void searchByEmailNameTest(String testEnv)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		// Email search
		TestUtils.testTitle("Filter by email address: " + dealerEmail);
		scrollUp();
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(dealerEmail);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[4]", dealerEmail);

		getDriver().navigate().refresh();
		// DealerName search
		TestUtils.testTitle("Filter by dealer name: " + dealerName);
		Thread.sleep(500);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(dealerName);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[2]", dealerName);

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void addNewDealerFormValidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);



		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		validationMessages.add("Please match the requested format.");
		validationMessages.add("Please match the format requested.");
		
		TestUtils.testTitle("Submit form without supplying required fields");
		navigateToDealerAccountCreation(testEnv);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/div/div/div/div/div/form/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Create Dealer");

		// Empty dealer First name
		TestUtils.testTitle("Submit form without dealer First name");
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(1000);
		WebElement dealerFirstName = getDriver().findElement(By.name("dealerName"));
		TestUtils.assertValidationMessage(validationMessages, dealerFirstName, "First Name");
		getDriver().findElement(By.id("firstName")).sendKeys("Seamfix Dealer AutoTest" + newPhoneNumber);

		// Empty dealer Surname
		TestUtils.testTitle("Submit form without dealer Surname");
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(1000);
		WebElement dealerSurName = getDriver().findElement(By.name("dealerName"));
		TestUtils.assertValidationMessage(validationMessages, dealerSurName, "SurName");
		getDriver().findElement(By.id("surname")).sendKeys("Seamfix Dealer AutoTest" + newPhoneNumber);
		
		// Empty dealer name
		TestUtils.testTitle("Submit form without dealer name");
		TestUtils.scrollToElement("ID", "dealerName");
		getDriver().findElement(By.id("dealerName")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(1000);
		WebElement dealerName = getDriver().findElement(By.name("dealerName"));
		TestUtils.assertValidationMessage(validationMessages, dealerName, "Dealer Name");
		getDriver().findElement(By.id("dealerName")).sendKeys("Seamfix Dealer AutoTest" + newPhoneNumber);

		// Empty dealer code
		TestUtils.testTitle("Submit form without dealer code");
		getDriver().findElement(By.id("dealerCode")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(1000);
		WebElement dealerCode = getDriver().findElement(By.name("dealerCode"));
		TestUtils.assertValidationMessage(validationMessages, dealerCode, "Dealer Code");
		getDriver().findElement(By.id("dealerCode")).sendKeys("SFX" + TestUtils.generateSerialNumber());

		// Submit empty phone number
		TestUtils.testTitle("Submit form without phone number");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement phoneField = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneField, "Phone Number");

		// Submit less than 11 digit phone number
		TestUtils.testTitle("Submit form with phone number less than 11 digits");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys("08012");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(1000);
		WebElement phoneLength = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneLength, "Phone Number Length");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(dealerPhone2);

		// Empty email address
		TestUtils.testTitle("Submit form without email address");
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement emailField = getDriver().findElement(By.name("email"));
		TestUtils.assertValidationMessage(validationMessages, emailField, "Email Address");

		// Submit wrong email format
		TestUtils.testTitle("Submit form with wrong email format");
		getDriver().findElement(By.id("email")).sendKeys("as@e");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement emailFormat = getDriver().findElement(By.name("email"));
		TestUtils.assertValidationMessage(validationMessages, emailFormat, "Email format");

		// Enter already exist user email
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);

		// Empty dealer type
		TestUtils.testTitle("Submit form without dealer type");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerType = getDriver().findElement(By.name("dealerType"));
		String validationMessage7 = dealerType.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage7, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer Type field: </b>" + validationMessage7);
		getDriver().findElement(By.xpath("//span/span/span")).click(); // dealer Type
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(dealerTyper);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("XPATH", "//div[8]/span/span/span");

		// Empty dealer division
		TestUtils.testTitle("Submit form without dealer division");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerDivision = getDriver().findElement(By.name("dealerDivision"));
		String validationMessage8 = dealerDivision.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage8, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer Division field: </b>" + validationMessage8);
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click(); // Dealer Division
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Information Systems");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Empty dealer state
		TestUtils.testTitle("Submit form without dealer state");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerState = getDriver().findElement(By.name("dealerState"));
		String validationMessage9 = dealerState.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage9, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer State field: </b>" + validationMessage9);
		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click(); // Dealer State
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("LAGOS");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		// Empty dealer region
		TestUtils.testTitle("Submit form without dealer region");
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerZone = getDriver().findElement(By.name("dealerZone"));
		String validationMessage10 = dealerZone.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage10, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer region field: </b>" + validationMessage10);
		getDriver().findElement(By.xpath("//div[10]/span/span/span")).click(); // Dealer Zone
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(Region);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// User Id
		String userId = getDriver().findElement(By.name("userId")).getAttribute("value");
		Thread.sleep(500);
		TestUtils.testTitle("Assert User ID: " + userId);
		
		// Empty address field
		TestUtils.testTitle("Submit form without address field");
		getDriver().findElement(By.id("address")).clear();
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		WebElement address = getDriver().findElement(By.name("address"));
		TestUtils.assertValidationMessage(validationMessages, address, "Address");
		getDriver().findElement(By.id("address"))
				.sendKeys(newPhoneNumber + " C & I  Off Admiralty Way Lekki Phase 1 | Lagos, Nigeria");
		Thread.sleep(500);

		getDriver().findElement(By.cssSelector("span.check")).click();
		Thread.sleep(500);

		// Submit form for existing dealer's email address
		TestUtils.testTitle("Submit form with already existing dealer email address" + email);
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "User with this email address already exists");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);

		/*
		 * // Submit form for existing dealer's phone String existingPhone =
		 * "Try to submit form with already existing email address: " + email; Markup p
		 * = MarkupHelper.createLabel(existingPhone, ExtentColor.BLUE);
		 * testInfo.get().info(p); getDriver().findElement(By.id("email")).clear();
		 * getDriver().findElement(By.id("email")).sendKeys(newPhoneNumber+
		 * "@seamfix.com");
		 * 
		 * getDriver().findElement(By.id("createDealerBtn")).click(); Thread.sleep(500);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
		 * "div.swal2-content"))); TestUtils.assertSearchText("CSSSELECTOR",
		 * "div.swal2-content", "A user with this phone number already exists.");
		 * Thread.sleep(500);
		 * getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).
		 * click(); Thread.sleep(1000);
		 */

		// Reset button
		getDriver().findElement(By.id("reset")).click();

		// Back button
		getDriver().findElement(By.cssSelector("a.btn.btn-primary.btn-simple")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Dealers");

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void addNewDealerTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		TestUtils.testTitle("Test to add new dealer: Seamfix Dealer Auto Test" + newPhoneNumber1);
		navigateToDealerAccountCreation(testEnv);
		getDriver().findElement(By.xpath("//div[3]/div/div/div/div/div/form/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Create Dealer");
		

		// fill form and submit
		TestUtils.testTitle("To confirm that First Name and Last Name input fields doesn't accept numbers and special characters");
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("Seamfix Dealer Auto Test$$" + newPhoneNumber1);
		testInfo.get().info("<b>Value Entered in First Name input Field: </b>"+"Seamfix Dealer Auto Test$$" + newPhoneNumber1);
		testInfo.get().info("<b>Value displayed in First Name input Field: </b>"+getDriver().findElement(By.name("firstName")).getAttribute("value"));
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys("Seamfix Dealer Auto Tes$$t" + newPhoneNumber1);
		testInfo.get().info("<b>Value Entered in Last Name input Field: </b>"+"Seamfix Dealer Auto Test$$" + newPhoneNumber1);
		testInfo.get().info("<b>Value displayed in Last Name input Field: </b>"+getDriver().findElement(By.name("surname")).getAttribute("value"));
		TestUtils.scrollToElement("ID", "dealerName");
		getDriver().findElement(By.id("dealerName")).clear();
		getDriver().findElement(By.id("dealerName")).sendKeys("Seamfix Dealer Auto Test" + newPhoneNumber1);
		getDriver().findElement(By.id("dealerCode")).clear();
		getDriver().findElement(By.id("dealerCode")).sendKeys("SFX" + newPhoneNumber1);
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(newPhoneNumber1);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("sfxtest" + newPhoneNumber1 + "@yopmail.com");

		getDriver().findElement(By.xpath("//span/span/span")).click(); // dealer Type
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("AGENCY");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("XPATH", "//div[8]/span/span/span");
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click(); // Dealer Division
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Information Systems");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click(); // Dealer State
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("LAGOS");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();
		Thread.sleep(500);

		getDriver().findElement(By.xpath("//div[10]/span/span/span")).click(); // Dealer Zone
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("ABA");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		// User Id
		String userId = getDriver().findElement(By.name("userId")).getAttribute("value");
		Thread.sleep(500);
		TestUtils.testTitle("Assert User ID: " + userId);
		
		getDriver().findElement(By.id("address")).clear(); // Dealer Address
		getDriver().findElement(By.id("address"))
				.sendKeys(newPhoneNumber1 + " C & I  Off Admiralty Way Lekki Phase 1 | Lagos, Nigeria");
		Thread.sleep(1000);

		getDriver().findElement(By.cssSelector("span.check")).click();
		Thread.sleep(500);

		// Save button
		getDriver().findElement(By.id("createDealerBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "The dealer was created successfully.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Dealers");

	}

	@Test(groups = { "Regression" })
	public void searchNewCreatedDealerByPhoneTest() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		TestUtils.scrollToElement("ID", "total_dealers");
		TestUtils.testTitle("Test to search for newly created dealer by phone number:"+ newPhoneNumber1);
		getDriver().navigate().refresh();

		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.id("phone")).clear();
		getDriver().findElement(By.id("phone")).sendKeys(newPhoneNumber1);
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//td")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr[2]/td/ul/li/span[2]", newPhoneNumber1);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewDetailsUpdateDetailsTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToDealerAccountCreation(testEnv);
		scrollUp();
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		validationMessages.add("Please match the requested format.");
		validationMessages.add("Please match the format requested.");
		
		TestUtils.testTitle("Filter and edit dealer with Email: " + de_activateDealer);

		if (!getDriver().findElement(By.id("name")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("includeDeletedDealers")).isSelected()) {
			getDriver().findElement(By.id("advancedBtn")).click();
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(de_activateDealer);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		String dealerFullName = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[2]"))
				.getText();
		String dealerFullCode = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[3]"))
				.getText();
		getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Edit Dealer");
		TestUtils.scrollToElement("CSSSELECTOR", "h4.card-title");
		
		TestUtils.testTitle("Dealer details");
		Assertion.dealerDetailsFormAssertion();

		// Edit dealer Information
		TestUtils.testTitle("Edit Dealer Details and check required field, also supply existing dealer's mobile "
				+ dealerPhone2);

		// Dealer name
		TestUtils.testTitle("Edit Dealer name");
		getDriver().findElement(By.id("dealerName")).clear();
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerName = getDriver().findElement(By.name("dealerName"));
		TestUtils.assertValidationMessage(validationMessages, dealerName, "Dealer Name");
		getDriver().findElement(By.id("dealerName")).sendKeys(dealerFullName);

		// Dealer code
		TestUtils.testTitle("Edit Dealer Code");
		getDriver().findElement(By.id("dealerCode")).clear();
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerCode = getDriver().findElement(By.name("dealerCode"));
		TestUtils.assertValidationMessage(validationMessages, dealerCode, "Dealer Code");
		getDriver().findElement(By.id("dealerCode")).sendKeys(dealerFullCode);

		// Mobile number
		TestUtils.testTitle("Edit mobile number");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement phoneField = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneField, "Phone Number");

		// Submit less than 11 digit phone number
		TestUtils.testTitle("Edit phone number and submit less than 11 digits");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys("08012");
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement phoneLength = getDriver().findElement(By.name("mobile"));
		TestUtils.assertValidationMessage(validationMessages, phoneLength, "Phone Number Length");
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(dealerPhone2); // already existing dealer's phone
		do {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
			getDriver().findElement(By.id("mobile")).click();
			Thread.sleep(500);
		} while (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear"));

		// Dealer type
		TestUtils.testTitle("Edit Dealer type");
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerType = getDriver().findElement(By.name("dealerType"));
		String validationMessage7 = dealerType.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage7, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer Type field: </b>" + validationMessage7);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click(); // dealer Type
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(dealerTyper);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Dealer Division
		TestUtils.testTitle("Edit Dealer division");
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerDivision = getDriver().findElement(By.name("dealerDivision"));
		String validationMessage8 = dealerDivision.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage8, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer Division field: </b>" + validationMessage8);
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click(); // Dealer Division
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Information Systems");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// Dealer State
		TestUtils.testTitle("Edit Dealer State");
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerState = getDriver().findElement(By.name("dealerState"));
		String validationMessage9 = dealerState.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage9, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer State field: </b>" + validationMessage9);
		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click(); // Dealer State
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("LAGOS");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//body/span/span/span/ul/li")).click();

		// Dealer Region
		TestUtils.testTitle("Edit Dealer region");
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(500);
		WebElement dealerZone = getDriver().findElement(By.name("dealerZone"));
		String validationMessage10 = dealerZone.getAttribute("validationMessage");
		Assert.assertEquals(validationMessage10, "Please select an item in the list.");
		testInfo.get().info("<b>Empty Dealer region field: </b>" + validationMessage10);
		getDriver().findElement(By.xpath("//div[10]/span/span/span")).click(); // Dealer Region
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(Region);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();

		// Address
		TestUtils.testTitle("Edit address");
		getDriver().findElement(By.id("address")).clear();
		getDriver().findElement(By.id("address"))
				.sendKeys(" 8 C & I  Off Admiralty Way Lekki Phase 1 | Lagos, Nigeria");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.check")).click(); // check box
		Thread.sleep(1000);

		/*
		 * // Submit for already existing phone
		 * getDriver().findElement(By.id("saveDealerBtn")).click(); Thread.sleep(500);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
		 * "div.swal2-content"))); TestUtils.assertSearchText("CSSSELECTOR",
		 * "div.swal2-content", "A user with this phone number already exists.");
		 * getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).
		 * click(); Thread.sleep(500);
		 */

		// Click Save button
		getDriver().findElement(By.id("mobile")).clear();
		getDriver().findElement(By.id("mobile")).sendKeys(dealerPhone);
		getDriver().findElement(By.id("saveDealerBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "The dealer was updated successfully.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Dealers");

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void activateDealer(String testEnv)
			throws InterruptedException, IOException, ParseException {
		// Active Dealer Test
		TestUtils.testTitle("Test to Activate a deactivated dealer");
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		scrollUp();

		// Email search
		TestUtils.testTitle("Filter by deactivated dealer: " + de_activateDealer);
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(de_activateDealer);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeletedDealers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String deactiveEmail = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[4]")).getText();
		String userStatus = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[6]")).getText();
		Thread.sleep(1000);
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-inactive", "INACTIVE");
		} catch (Exception e) {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "BLACKLIST");
		}
		
		TestUtils.testTitle("Activate user with email address " + deactiveEmail + " And status: " + userStatus);
		getDriver().findElement(By.xpath("//*[@id=\"dealerTable\"]/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("activate")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		
		TestUtils.testTitle("Test to check required fields");

		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Account has been activated successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void deactivateDealer(String testEnv)
			throws InterruptedException, IOException, ParseException {
		// Deactive Dealer Test
		TestUtils.testTitle("Test to Deactivate activated dealer");

		WebDriverWait wait = new WebDriverWait(getDriver(), 100);
		scrollUp();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// Email search
		scrollUp();
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(de_activateDealer);
		if (!getDriver().findElement(By.id("phone")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (!getDriver().findElement(By.id("includeDeletedDealers")).isSelected()) {
			getDriver().findElement(By.cssSelector("span.slider.round")).click();
		}
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String InactiveEmail = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[4]")).getText();
		String userStatus = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[6]")).getText();
		Thread.sleep(500);
		TestUtils.testTitle("Deactivate user with email address " + InactiveEmail + " And status: " + userStatus);

		getDriver().findElement(By.xpath("//*[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("Deactivate")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");

		getDriver().findElement(By.id("approvalEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Account has been deactivated successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void viewDealerActivationHistory(String testEnv)
			throws InterruptedException, IOException, ParseException {
		// Active User Test
		TestUtils.testTitle("Test to check Dealer activation history");

		navigateToDealerAccountCreation(testEnv);
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);

		TestUtils.testTitle("Filter user with email address " + dealerHistory);

		// Email search
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(dealerHistory);
		getDriver().findElement(By.id("searchdealers")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[4]", dealerHistory);
		String dealerName = getDriver().findElement(By.xpath("//*[@id=\"dealerTable\"]/tbody/tr/td[2]")).getText();
		String dealerStatus = getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[7]/span"))
				.getText();
		Thread.sleep(500);
		TestUtils.testTitle("View history of dealer with email address " + dealerHistory + " And status: "
				+ dealerStatus);
		TestUtils.assertSearchText("XPATH", "//*[@id='dealerTable']/tbody/tr/td[2]", dealerName);

		getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		// Change page size
		new Select(getDriver().findElement(By.name("userHistory_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='userHistory']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number count returned: " + rowCount);
		} else {
			testInfo.get().error("Table is empty.");
		}

		// Filter by performed By
		TestUtils.testTitle("Filter by performed By " + performedBy);
		getDriver().findElement(By.id("performedByEmail")).clear();
		getDriver().findElement(By.id("performedByEmail")).sendKeys(performedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("XPATH", "//td[4]", performedBy);
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// Filter by approved By
		if (!getDriver().findElement(By.id("approverEmail")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		TestUtils.testTitle("Filter by approved By " + approvedBy);
		getDriver().findElement(By.id("approverEmail")).clear();
		getDriver().findElement(By.id("approverEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("XPATH", "//td[5]", approvedBy);
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// Filter by Actions
		if (!getDriver().findElement(By.id("approverEmail")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		TestUtils.testTitle("Filter by action ACTIVATION and DEACTIVATION ");
		getDriver().findElement(By.id("approverEmail")).clear();
		getDriver().findElement(By.id("performedByEmail")).clear();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("activation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-warning", "ACTIVATION");
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("Deactivation");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-danger", "DEACTIVATION");
		} else {
			testInfo.get().error("Table is empty.");
		}
		Thread.sleep(500);

		// DeactivateReactivate Dealer
		if (dealerStatus.equalsIgnoreCase("Blacklist") || dealerStatus.equalsIgnoreCase("Inactive")) {
			activateHistory(testEnv);
			getDriver().findElement(By.id("name")).clear();
			getDriver().findElement(By.id("name")).sendKeys(dealerHistory);
			getDriver().findElement(By.id("searchdealers")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			scrollUp();
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[4]", dealerHistory);
			TestUtils.assertSearchText("XPATH", "//tbody[1]/tr[1]/td[7]/span[1]", "ACTIVE");
			getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			deactivateHistory(testEnv);

		} else {

			deactivateHistory(testEnv);
			getDriver().findElement(By.id("name")).clear();
			getDriver().findElement(By.id("name")).sendKeys(dealerHistory);
			getDriver().findElement(By.id("searchdealers")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			scrollUp();
			TestUtils.assertSearchText("XPATH", "//table[@id='dealerTable']/tbody/tr/td[4]", dealerHistory);
			getDriver().findElement(By.xpath("//table[@id='dealerTable']/tbody/tr/td[8]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'Activation History')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			activateHistory(testEnv);

		}

	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void deactivateHistory(String testEnv)
			throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);

		Thread.sleep(500);
		String buttonText = getDriver().findElement(By.id("activate")).getText();
		Assert.assertEquals(buttonText, "DEACTIVATE USER");

		TestUtils.testTitle(buttonText);
		getDriver().findElement(By.id("activate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		
		// missing field
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Account has been deactivated successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	}

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void activateHistory(String testEnv)
			throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);

		String buttonText2 = getDriver().findElement(By.id("activate")).getText();
		Assert.assertEquals(buttonText2, "ACTIVATE USER");

		TestUtils.testTitle(buttonText2);
		getDriver().findElement(By.id("activate")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		getDriver().findElement(By.id("approvalEmail")).clear();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "approvalEmail-error", "This field is required.");
		getDriver().findElement(By.id("approvalEmail")).sendKeys(approvedBy);
		getDriver().findElement(By.xpath("//div[2]/span/span/span")).click();
		Thread.sleep(1000);
		
		// missing field
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("proceed")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Account has been activated successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

	}

	@Test(groups = { "Regression" })
	public void searchByDate() throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));
		getDriver().findElement(By.id("sStartDate")).clear();
		getDriver().findElement(By.id("sEndDate")).clear();

		getDriver().findElement(By.id("sStartDate")).sendKeys("2018-10-26");
		getDriver().findElement(By.id("sEndDate")).sendKeys("2018-04-19");
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();

	}

}
