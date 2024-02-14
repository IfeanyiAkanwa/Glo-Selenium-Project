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
import org.openqa.selenium.interactions.Actions;
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

public class SubscribersInfo extends TestBase {
	
	private String fullUniqueID; 
	private String partUniqueID;
	private String phoneNumber;
	private String fullSimSerial;
	private String partSimSerial;
	private String surname;
	private String firstName;
	private String otherName;
	
	 @Parameters({ "testEnv" })
	    @BeforeMethod
	    public void parseJson(String testEnv) throws IOException, ParseException {
	        File path = null;
	        File classpathRoot = new File(System.getProperty("user.dir"));
	        if (testEnv.equalsIgnoreCase("StagingData")) {
	            path = new File(classpathRoot, "stagingData/data.conf.json");
	        } else {
	            path = new File(classpathRoot, "prodData/data.conf.json");
	        }
	        JSONParser parser = new JSONParser();
	        JSONObject config = (JSONObject) parser.parse(new FileReader(path));
	        JSONObject envs = (JSONObject) config.get("subscriber");

	        fullUniqueID = (String) envs.get("fullUniqueID");
	        partUniqueID = (String) envs.get("partUniqueID");
	        phoneNumber = (String) envs.get("phoneNumber");
	        fullSimSerial = (String) envs.get("fullSimSerial");
	        partSimSerial = (String) envs.get("partSimSerial");
	        surname = (String) envs.get("surname");
	        firstName = (String) envs.get("firstName");
	        otherName = (String) envs.get("otherName");
	    }

	@Test(groups = { "Regression" })
	public void navigateToSubscriberInfoTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Subcriber's Information");
		try {
			TestUtils.scrollToElement("NAME", "427727Subscriber Info");
			getDriver().findElement(By.name("427727Subscriber Info")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("2096Basic Info")).click();
		} catch (Exception e) {
			TestUtils.scrollToElement("NAME", "38588944Subscriber Info");
			getDriver().findElement(By.name("38588944Subscriber Info")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("2096Basic Info")).click();
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary",
				"Subscriber Information");
	}

	@Test(groups = { "Regression" })
	public void selectVisibleColumns() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		TestUtils.testTitle("Select Visible Columns");
		TestUtils.assertSearchText("XPATH", "//div[2]/a[3]", "Select Visible Columns");
		getDriver().findElement(By.xpath("//div[2]/a[3]")).click();
		Thread.sleep(500);

		
		// Name Column
		TestUtils.testTitle("Remove Name Column");
		getDriver().findElement(By.linkText("Name")).click();
		if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Name")) {
			TestUtils.assertSearchText("XPATH", "//th[2]", "Name");

		}else {
			testInfo.get().info("Name column removed");

		}
		TestUtils.testTitle("Add Name Column");
		getDriver().findElement(By.linkText("Name")).click();
		TestUtils.assertSearchText("XPATH", "//th[2]", "Name");
		Thread.sleep(500);

		// Phone Number Column
		TestUtils.testTitle("Remove Phone Number Column");
		getDriver().findElement(By.linkText("Phone Number")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Phone Number")) {
			TestUtils.assertSearchText("XPATH", "//th[3]", "Phone Number");

		}else {
			testInfo.get().info("Phone Number column removed");

		}
		TestUtils.testTitle("Add Phone Number Column");
		getDriver().findElement(By.linkText("Phone Number")).click();
		TestUtils.assertSearchText("XPATH", "//th[3]", "Phone Number");
		Thread.sleep(500);

		// Unique ID Column
		TestUtils.testTitle("Remove Unique ID Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		if (getDriver().findElement(By.xpath("//th[4]")).getText().contains("Unique ID")) {
			TestUtils.assertSearchText("XPATH", "//th[4]", "Unique ID");

		}else {
			testInfo.get().info("Unique ID column removed");

		}
		TestUtils.testTitle("Add Unique ID Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[4]", "Unique ID");
		Thread.sleep(500);

		// Registration Date Column
		TestUtils.testTitle("Remove Registration Date Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		if (getDriver().findElement(By.xpath("//th[5]")).getText().contains("Registration Date")) {
			TestUtils.assertSearchText("XPATH", "//th[5]", "Registration Date");

		}else {
			testInfo.get().info("Registration Date column removed");

		}
		TestUtils.testTitle("Add Registration Date Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[5]", "Registration Date");
		Thread.sleep(500);

		// Serial Number Column
		TestUtils.testTitle("Remove Serial Number Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		if (getDriver().findElement(By.xpath("//th[6]")).getText().contains("Serial Number")) {
			TestUtils.assertSearchText("XPATH", "//th[6]", "Serial Number");

		}else {
			testInfo.get().info("Serial Number column removed");

		}
		TestUtils.testTitle("Add Serial Number Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[6]", "Serial Number");
		Thread.sleep(500);
	
		// Actions Column
		TestUtils.testTitle("Remove Actions Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		try {
			TestUtils.assertSearchText("XPATH", "//th[7]", "Actions");

		} catch (Exception e) {
			testInfo.get().info("Actions column removed");
		}
		TestUtils.testTitle("Add Actions Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[7]", "Actions");
		Thread.sleep(500);
		
		WebElement figure = getDriver().findElement(By.xpath("//body"));
			
		Actions actions = new Actions(getDriver());
			actions.moveToElement(figure).perform(); // hover action
			figure.click();
		
			

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByUniqueID(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);

		// Test with Part unique id
		TestUtils.testTitle("Filter by Part Unique ID: " + partUniqueID);
		getDriver().findElement(By.name("uniqueId")).clear();
		getDriver().findElement(By.name("uniqueId")).sendKeys(partUniqueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[4]", fullUniqueID);
		Thread.sleep(500);

		// Test with full unique id
		TestUtils.testTitle("Filter by Full Unique ID: " + fullUniqueID);
		getDriver().findElement(By.name("uniqueId")).clear();
		getDriver().findElement(By.name("uniqueId")).sendKeys(fullUniqueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[4]", fullUniqueID);
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByPhoneNumber(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);

		TestUtils.testTitle("Filter by Phone number: " + phoneNumber);
		getDriver().findElement(By.name("uniqueId")).clear();
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.name("phoneNumber")).sendKeys(phoneNumber);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[3]", phoneNumber);
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchBySimSerial(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);

		// Test with part sim serial
		TestUtils.testTitle("Filter by Part Sim Serial: " + partSimSerial);
		getDriver().findElement(By.id("uniqueId")).clear();
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("simSerial")).clear();
		getDriver().findElement(By.id("simSerial")).sendKeys(partSimSerial);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[6]", fullSimSerial);
		Thread.sleep(500);

		// Test with full sim serial
		TestUtils.testTitle("Filter by Full Sim Serial: " + fullSimSerial);
		getDriver().findElement(By.id("simSerial")).clear();
		getDriver().findElement(By.id("simSerial")).sendKeys(fullSimSerial);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[6]", fullSimSerial);
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchBySurname(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);
		
		TestUtils.testTitle("Filter by Surname: " + surname);
		getDriver().findElement(By.id("uniqueId")).clear();
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("simSerial")).clear();
		if (!getDriver().findElement(By.name("surname")).isDisplayed()) {
			getDriver().findElement(By.id("more")).click();
		}
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(surname);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String fullName = getDriver().findElement(By.xpath("//table[@id='subscribers']/tbody/tr/td[2]")).getText();
		fullName.contains(surname);
		testInfo.get().info("Name: <b>" + fullName + "</b> contains Surname: <b>" + surname + "</b>");
		Thread.sleep(500);

		// Page size
		new Select(getDriver().findElement(By.name("subscribers_length"))).selectByVisibleText("250");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 250");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='subscribers']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByfirstName(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);
		

		TestUtils.testTitle("Filter by First Name: " + firstName);
		getDriver().findElement(By.id("uniqueId")).clear();
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("simSerial")).clear();
		if (!getDriver().findElement(By.name("surname")).isDisplayed()) {
			getDriver().findElement(By.id("more")).click();
		}
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String fullName = getDriver().findElement(By.xpath("//table[@id='subscribers']/tbody/tr/td[2]")).getText();
		fullName.contains(firstName);
		testInfo.get().info("Name: <b>" + fullName + "</b> contains first name: <b>" + firstName + "</b>");
		Thread.sleep(500);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByOtherName(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);
		

		getDriver().navigate().refresh();
		TestUtils.testTitle("Filter by Other Name: " + otherName);
		getDriver().findElement(By.id("uniqueId")).clear();
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("simSerial")).clear();
		if (!getDriver().findElement(By.name("surname")).isDisplayed()) {
			getDriver().findElement(By.id("more")).click();
		}
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("otherName")).clear();
		getDriver().findElement(By.id("otherName")).sendKeys(otherName);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    getDriver().findElement(By.xpath("//table[@id='subscribers']/tbody/tr/td[7]/div/a/i")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title.mb-3.font-weight-bold.text-secondary")));
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    TestUtils.scrollToElement("CSSSELECTOR", "h4.header-color.font-weight-bold.text-secondary");
	    TestUtils.assertSearchText("XPATH", "//tr[3]/td[2]", otherName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a/button")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void downloadReport(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		// Retrieve subscriber info by surname search
//		getDriver().findElement(By.id("uniqueId")).clear();
//		getDriver().findElement(By.id("phoneNumber")).clear();
//		getDriver().findElement(By.id("simSerial")).clear();
//		if (!getDriver().findElement(By.name("surname")).isDisplayed()) {
//			getDriver().findElement(By.id("more")).click();
//		}
//		getDriver().findElement(By.id("surname")).clear();
//		getDriver().findElement(By.id("surname")).sendKeys(surname);
//		getDriver().findElement(By.id("searchBtn")).click();
//		Thread.sleep(3000);

		TestUtils.testTitle("Download Subscribers Reports");
		// Download pdf
		TestUtils.testTitle("Download Pdf");
		getDriver().findElement(By.xpath("//div/div/div/div[2]/div/div[2]/a")).click();
		Thread.sleep(500);
		// Download excel
		TestUtils.testTitle("Download Excel");
		getDriver().findElement(By.xpath("//div[2]/a[2]")).click();
		Thread.sleep(500);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewsubscriberDetailsTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);

		TestUtils.testTitle("Filter by Full Unique ID: " + fullUniqueID);
		if (!getDriver().findElement(By.name("surname")).isDisplayed()) {
			getDriver().findElement(By.id("more")).click();
		}
		getDriver().findElement(By.name("uniqueId")).clear();
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("otherName")).clear();
		getDriver().findElement(By.name("uniqueId")).sendKeys(fullUniqueID);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='subscribers']/tbody/tr/td[4]", fullUniqueID);
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.xpath("//table[@id='subscribers']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("h4.card-title.mb-3.font-weight-bold.text-secondary")));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		
		Assertion.assertSubscriberInfo();
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "btnPrint");
		getDriver().findElement(By.id("btnPrint")).click();
		Thread.sleep(500);

	}

}
