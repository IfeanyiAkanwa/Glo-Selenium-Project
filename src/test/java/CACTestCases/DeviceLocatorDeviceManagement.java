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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class DeviceLocatorDeviceManagement extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	private String kitTag;
	private String macAddress;
	private String deviceID;
	private String division;
	private String dealerName;
	private String state;
	private String region;
	private String totalActive0;
	private String totalInactiveGeoBio;
	private String totalBlacklisted;
	
	@Parameters({"testEnv"})
    @BeforeMethod
    public void parseJson(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject)config.get("geoTracker");
		kitTag = (String) envs.get("kitTag");
		macAddress = (String) envs.get("macAddress");
		deviceID = (String) envs.get("deviceID");
		division = (String) envs.get("devision");
		dealerName = (String) envs.get("dealerName");
		state = (String) envs.get("state");
		region = (String) envs.get("region");
		division = (String) envs.get("division");
		totalActive0 = (String) envs.get("totalActive0");
		totalInactiveGeoBio = (String) envs.get("totalInactiveGeoBio");
		totalBlacklisted = (String) envs.get("totalBlacklisted");

    }

	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToDeviceLocatorTest(String testEnv) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Navigate to Device Locator");
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
			Thread.sleep(500);
			getDriver().findElement(By.linkText("Device Locator")).click();
			Thread.sleep(500);
		} else {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"854255307Device Locator\"]");
			getDriver().findElement(By.cssSelector("a[name=\"854255307Device Locator\"]")).click();
			Thread.sleep(500);
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
		
	}

	@Test(groups = { "Regression" })
	public void assertDeviceCountTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		if (!getDriver().findElement(By.id("activeWithNoRegistration")).isDisplayed()) {
			TestUtils.clickElement("XPATH", "//div[@id='expandTags']/a/b");
		}
		Thread.sleep(1000);
		String totalDevicesValString = getDriver().findElement(By.id("totalDevice")).getText();
		String totalBlacklistedValString = getDriver().findElement(By.id("blacklistedDevice")).getText();
		String totalActiveGeoBioDeviceValString = getDriver().findElement(By.id("totalActive")).getText();
		String totalInactiveGeoBioDeviceValString = getDriver().findElement(By.id("inactive")).getText();
		String totalActiveWithNoRegistrationValString = getDriver().findElement(By.id("activeWithNoRegistration")).getText();
		String totalActiveWithOver40RegistrationValString = getDriver().findElement(By.id("xAbove")).getText();

		int actualTotalDevicesVal = TestUtils.convertToInt(totalDevicesValString);
		int actualTotalBlacklistedVal = TestUtils.convertToInt(totalBlacklistedValString);
		int actualTotalActiveGeoBioDeviceVal = TestUtils.convertToInt(totalActiveGeoBioDeviceValString);
		int actualTotalInactiveGeoBioDeviceVal = TestUtils.convertToInt(totalInactiveGeoBioDeviceValString);
		int actualTotalActiveWithNoRegistrationVal = TestUtils.convertToInt(totalActiveWithNoRegistrationValString);
		int actualTotalActiveWithOver40RegistrationVal = TestUtils.convertToInt(totalActiveWithOver40RegistrationValString);

		int expectedTotalDevicesVal = actualTotalActiveGeoBioDeviceVal + actualTotalInactiveGeoBioDeviceVal + actualTotalBlacklistedVal;

		try {
			Assert.assertEquals(expectedTotalDevicesVal,actualTotalDevicesVal);
			testInfo.get().log(Status.INFO, "Total devices <b>("+expectedTotalDevicesVal+")</b> is sum of Total Active (GeoTracker & BioSmart) <b>("+actualTotalActiveGeoBioDeviceVal+
					")</b> + Total Inactive (GeoTracker & BioSmart) <b>("+actualTotalInactiveGeoBioDeviceVal+")</b> + Total Blacklisted <b>("+actualTotalBlacklistedVal+" )</b>");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Total Devices Summation is not equal");
			testInfo.get().error(verificationErrorString);
		}
		
		int expectedTotalActiveGeoBioDeviceVal = actualTotalActiveWithNoRegistrationVal + actualTotalActiveWithOver40RegistrationVal;

		try {
			Assert.assertEquals(expectedTotalActiveGeoBioDeviceVal,actualTotalActiveGeoBioDeviceVal);
			testInfo.get().log(Status.INFO, "Total Active (GeoTracker & BioSmart) <b>("+expectedTotalActiveGeoBioDeviceVal+")</b> is sum of Total Active (0 Registration) <b>("+actualTotalActiveWithNoRegistrationVal+")</b>"
							+ "+ Total Active (Over 40 Registrations) <b>("+actualTotalActiveWithOver40RegistrationVal+" )</b>");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Total Active (GeoTracker & BioSmart) summation is not equal");
			testInfo.get().error(verificationErrorString);
		}
		
	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByKitTag(String testEnv) throws Exception, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		
		TestUtils.testTitle("Filter by kit tag: " + kitTag);
		// Search menu
		Thread.sleep(3000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("kitTag")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		assertDeviceCountTest();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("CSSSELECTOR", "h3", kitTag);
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'View kit details')]");
		TestUtils.clickElement("XPATH", "//a[contains(text(),'View kit details')]");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		viewKitDetails(testEnv);
		Thread.sleep(500);
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByMAC(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		navigateToDeviceLocatorTest(testEnv);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by MAC Address: " + macAddress);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("macAddress")).sendKeys(macAddress);
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		assertDeviceCountTest();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img")).click();
		Thread.sleep(500);
		String mac = getDriver().findElement(By.xpath("//div[6]/div/div/div/p")).getText();
		if (mac.contains("N/A")) {
			testInfo.get().error(mac);
		} else if (mac.endsWith(macAddress)) {
			testInfo.get().info(mac+" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		getDriver().findElement(By.cssSelector("a.leaflet-popup-close-button")).click();
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDeviceID(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Device ID: "+deviceID);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		getDriver().findElement(By.id("deviceId")).sendKeys(deviceID);
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		assertDeviceCountTest();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img")).click();
		Thread.sleep(500);
		String device = getDriver().findElement(By.xpath("//p[3]")).getText();
		if (device.contains("N/A")) {
			testInfo.get().error(device);
		} else if (device.endsWith(deviceID)) {
			testInfo.get().info(device+" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		getDriver().findElement(By.cssSelector("a.leaflet-popup-close-button")).click();
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDivision(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Devision: "+division);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(division);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img")).click();
		Thread.sleep(500);
		String devisionType = getDriver().findElement(By.xpath("//p[10]")).getText();
		if (devisionType.contains("N/A")) {
			testInfo.get().error(devisionType);
		} else if (devisionType.endsWith(division)) {
			testInfo.get().info(devisionType+" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		getDriver().findElement(By.cssSelector("a.leaflet-popup-close-button")).click();
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDealer(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Dealer name: "+dealerName);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.xpath("//div[8]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		Thread.sleep(500);
		try {
			TestUtils.clickElement("XPATH", "//*[@id='map']/div[1]/div[4]/div[2]/div");
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.clickElement("XPATH", "//*[@id='map']/div[1]/div[4]/div/div");
			Thread.sleep(500);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img")).click();
		Thread.sleep(500);
		String dealer = getDriver().findElement(By.xpath("//p[5]")).getText();
		if (dealer.contains("N/A")) {
			testInfo.get().error(dealer);
		} else if (dealer.endsWith(dealerName)) {
			testInfo.get().info(dealer +" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByState(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Region: " + region + " and State: " + state);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("deviceId")).click();
		// Region 
		getDriver().findElement(By.xpath("//div[5]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(region);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		
		// State
		getDriver().findElement(By.xpath("//div[6]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(state);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img[2]")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img[2]")).click();
		Thread.sleep(500);
		String stateView = getDriver().findElement(By.xpath("//p[14]")).getText();
		String regionView = getDriver().findElement(By.xpath("//p[15]")).getText();
		if (stateView.contains("N/A")) {
			testInfo.get().error(stateView);
		} else if (stateView.endsWith(state)) {
			testInfo.get().info(stateView+" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		
		if (regionView.contains("N/A")) {
			testInfo.get().error(regionView);
		} else if (regionView.endsWith(region)) {
			testInfo.get().info(regionView+" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDeviceStatus(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Device status: " + totalActive0);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("deviceId")).click();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("deviceId")).click();
		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalActive0);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		if (!getDriver().findElement(By.id("activeWithNoRegistration")).isDisplayed()) {
			TestUtils.clickElement("XPATH", "//div[@id='expandTags']/a/b");
		}
		Thread.sleep(1000);
		assertDeviceCountTest();
		
		TestUtils.testTitle("Filter by Device status: " + totalInactiveGeoBio);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalInactiveGeoBio);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		
		TestUtils.testTitle("Filter by Device status: " + totalBlacklisted);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[9]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalBlacklisted);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		Thread.sleep(500);
		
	}

	@Parameters({ "testEnv" })
	public void viewKitDetails(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Assert Mobile Device Information");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Mobile Device Information");
		TestUtils.scrollToElement("ID", "headingTwo");
		Assertion.assertMobileInfo();

		getDriver().findElement(By.id("headingTwo")).click();

		TestUtils.testTitle("Assert Device Location Information");
		TestUtils.assertSearchText("CSSSELECTOR", "#headingThree > div.card-header.collapsed > h4.card-title",
				"Device Location Information");
		TestUtils.scrollToElement("CSSSELECTOR", "#headingThree > div.card-header.collapsed > h4.card-title");
		Assertion.assertLocationInfo();

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Client Activity Summary')]",
				"CLIENT ACTIVITY SUMMARY");
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "vMore");
		getDriver().findElement(By.id("vMore")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title", "Client Activity Log");
		TestUtils.scrollToElement("XPATH", "//div[2]/div/div/div[3]/button");
		getDriver().findElement(By.xpath("//div[2]/div/div/div[3]/button")).click();
		Thread.sleep(500);

		TestUtils.scrollToElement("ID", "outletName");
		getDriver().findElement(By.id("locationHistoryTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");

		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistButton")));
		BlacklistDeviceManagement.blacklistWhitelist(testEnv);
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		Thread.sleep(500);
		getDriver().findElement(By.id("blacklistTab")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blacklistButton")));
		BlacklistDeviceManagement.blacklistWhitelist(testEnv);

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Client Activity Summary')]");
		getDriver().findElement(By.xpath("//a[contains(text(),'Kit Agents')]")).click();
		Thread.sleep(1000);
		testInfo.get().log(Status.INFO, "Kit Agents view");
		getDriver().findElement(By.xpath("//a[contains(text(),'Registration Summary')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		getDriver().findElement(By.id("startDateData")).clear();
		getDriver().findElement(By.id("startDateData")).sendKeys("2020/01/25");
		getDriver().findElement(By.id("endDateData")).clear();
		getDriver().findElement(By.id("endDateData")).sendKeys("2020/11/16");
		getDriver().findElement(By.xpath("(//button[@type='button'])[3]")).click(); // Search button
		Thread.sleep(1000);
		testInfo.get().log(Status.INFO, "Registration summary view page");

		// Scroll up to click back button
		TestUtils.scrollToElement("XPATH", "//a/button");
		getDriver().findElement(By.xpath("//a/button")).click();

	}

}
