package DealerTestCases;

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

public class DeviceLocator extends TestBase{
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void navigateToDeviceLocatorTest() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.cssSelector("a[name=\"207450609Device Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("207450610Device Locator")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

	}

	@Test(groups = { "Regression" })
	public void assertDeviceCountTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		if (!getDriver().findElement(By.id("nonActivenonBiosmart")).isDisplayed()) {
			TestUtils.clickElement("XPATH", "//div[@id='expandTags']/a/b");
		}
		Thread.sleep(1000);
		String totalDevicesValString = getDriver().findElement(By.id("totalDevice")).getText();
		String totalGeoTrackerDevicesValString = getDriver().findElement(By.id("nonBiosmart")).getText();
		String totalBlacklistedValString = getDriver().findElement(By.id("blacklistedDevice")).getText();
		String totalActiveGeoBioDeviceValString = getDriver().findElement(By.id("totalActive")).getText();
		String totalInactiveGeoBioDeviceValString = getDriver().findElement(By.id("inactive")).getText();
		String totalActiveWithNoRegistrationValString = getDriver().findElement(By.id("activeWithNoRegistration")).getText();
		String totalActiveWithOneRegistrationValString = getDriver().findElement(By.id("oneRegistration")).getText();
		String totalActiveWithTwoAboveRegistrationValString = getDriver().findElement(By.id("twoToX")).getText();
		String totalActiveWithOver60RegistrationValString = getDriver().findElement(By.id("xAbove")).getText();
		String totalInactiveGeoDeviceValString = getDriver().findElement(By.id("nonActivenonBiosmart")).getText();
		String totalActiveGeoDeviceValString = getDriver().findElement(By.id("activneNonBiosmart")).getText();

		int actualTotalDevicesVal = TestUtils.convertToInt(totalDevicesValString);
		int actualTotalGeoTrackerDevicesVal = TestUtils.convertToInt(totalGeoTrackerDevicesValString);
		int actualTotalBlacklistedVal = TestUtils.convertToInt(totalBlacklistedValString);
		int actualTotalActiveGeoBioDeviceVal = TestUtils.convertToInt(totalActiveGeoBioDeviceValString);
		int actualTotalInactiveGeoBioDeviceVal = TestUtils.convertToInt(totalInactiveGeoBioDeviceValString);
		int actualTotalActiveWithNoRegistrationVal = TestUtils.convertToInt(totalActiveWithNoRegistrationValString);
		int actualTotalActiveWithOneRegistrationVal = TestUtils.convertToInt(totalActiveWithOneRegistrationValString);
		int actualTotalActiveWithTwoAboveRegistrationVal = TestUtils.convertToInt(totalActiveWithTwoAboveRegistrationValString);
		int actualTotalActiveWithOver60RegistrationVal = TestUtils.convertToInt(totalActiveWithOver60RegistrationValString);
		int actualTotalInactiveGeoDeviceVal = TestUtils.convertToInt(totalInactiveGeoDeviceValString);
		int actualTotalActiveGeoDeviceVal = TestUtils.convertToInt(totalActiveGeoDeviceValString);

		int expectedTotalDevicesVal = actualTotalActiveGeoBioDeviceVal + actualTotalInactiveGeoBioDeviceVal + actualTotalBlacklistedVal;

		try {
			Assert.assertEquals(expectedTotalDevicesVal,actualTotalDevicesVal);
			testInfo.get().log(Status.INFO, "Total devices ("+expectedTotalDevicesVal+") is sum of Total Active (GeoTracker & BioSmart) ("+actualTotalActiveGeoBioDeviceVal+
					") + Total Inactive (GeoTracker & BioSmart) ("+actualTotalInactiveGeoBioDeviceVal+") + Total Blacklisted ("+actualTotalBlacklistedVal+" )");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Total Devices Summation is not equal");
			testInfo.get().error(verificationErrorString);
		}
		
		int expectedTotalGeoTrackerDevicesVal = actualTotalInactiveGeoDeviceVal + actualTotalActiveGeoDeviceVal;

		try {
			Assert.assertEquals(expectedTotalGeoTrackerDevicesVal,actualTotalGeoTrackerDevicesVal);
			testInfo.get().log(Status.INFO, "Total Devices (GeoTracker Only) ("+expectedTotalGeoTrackerDevicesVal+") is sum of Total Inactive (GeoTracker Only) ("+actualTotalInactiveGeoDeviceVal+
					") + Total Active (GeoTracker Only) ("+actualTotalActiveGeoDeviceVal+" )");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Total Devices (GeoTracker Only) Summation is not equal");
			testInfo.get().error(verificationErrorString);
		}
		
		int expectedTotalActiveGeoBioDeviceVal = actualTotalActiveWithNoRegistrationVal + actualTotalActiveWithOneRegistrationVal 
				+ actualTotalActiveWithTwoAboveRegistrationVal + actualTotalActiveWithOver60RegistrationVal;

		try {
			Assert.assertEquals(expectedTotalActiveGeoBioDeviceVal,actualTotalActiveGeoBioDeviceVal);
			testInfo.get().log(Status.INFO, "Total Active (GeoTracker & BioSmart) ("+expectedTotalActiveGeoBioDeviceVal+") is sum of Total Active (0 Registration) ("+actualTotalActiveWithNoRegistrationVal+
					") + Total Active (1 Registration) ("+actualTotalActiveWithOneRegistrationVal+") + Total Active (2 to 59 Registrations) ("+actualTotalActiveWithTwoAboveRegistrationVal+" ) "
							+ "+ Total Active (Over 60 Registrations) ("+actualTotalActiveWithOver60RegistrationVal+" )");
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
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject)config.get("geoTracker");
		String kitTag = (String) envs.get("kitTag");

		String searchBykit = "Filter by kit tag: "+kitTag;
		Markup b = MarkupHelper.createLabel(searchBykit, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
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
		getDriver().findElement(By.cssSelector("a.leaflet-popup-close-button")).click();
		Thread.sleep(500);

	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByMAC(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		navigateToDeviceLocatorTest();
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
		JSONObject envs = (JSONObject)config.get("geoTracker");
		String macAddress = (String) envs.get("macAddress");

		String searchByMAC = "Filter by MAC Address: "+macAddress;
		Markup b = MarkupHelper.createLabel(searchByMAC, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
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

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject)config.get("geoTracker");
		String deviceID = (String) envs.get("deviceID");

		String searchByDeviceID = "Filter by Device ID: "+deviceID;
		Markup b = MarkupHelper.createLabel(searchByDeviceID, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
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
	public void searchByDeviceStatus(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
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
		JSONObject envs = (JSONObject)config.get("geoTracker");
		String totalActive0 = (String) envs.get("totalActive0");
		String totalInactiveGeoBio = (String) envs.get("totalInactiveGeoBio");
		String totalBlacklisted = (String) envs.get("totalBlacklisted");

		String search = "Filter by Device status: "+totalActive0;
		Markup b = MarkupHelper.createLabel(search, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("kitTag")).clear();
		getDriver().findElement(By.id("macAddress")).clear();
		getDriver().findElement(By.id("deviceId")).clear();
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalActive0);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		if (!getDriver().findElement(By.id("nonActivenonBiosmart")).isDisplayed()) {
			TestUtils.clickElement("XPATH", "//div[@id='expandTags']/a/b");
		}
		Thread.sleep(1000);
		assertDeviceCountTest();
		
		String search2 = "Filter by Device status: "+totalInactiveGeoBio;
		Markup s = MarkupHelper.createLabel(search2, ExtentColor.BLUE);
		testInfo.get().info(s);
		Thread.sleep(500);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalInactiveGeoBio);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		
		String search3 = "Filter by Device status: "+totalBlacklisted;
		Markup d = MarkupHelper.createLabel(search3, ExtentColor.BLUE);
		testInfo.get().info(d);
		Thread.sleep(500);
		// Search menu
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ul[@id='simrop-map-search-btn']/li/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(totalBlacklisted);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		getDriver().findElement(By.id("searchMapFilterBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		assertDeviceCountTest();
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div/div/div/div[2]/div/div/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='map']/div/div[4]/img[1]")));
		getDriver().findElement(By.xpath("//div[@id='map']/div/div[4]/img[1]")).click();
		Thread.sleep(500);
		String blacklisted = getDriver().findElement(By.xpath("//p[11]")).getText();
		if (blacklisted.contains("N/A")) {
			testInfo.get().error(blacklisted);
		} else if (blacklisted.endsWith("BLACKLISTED")) {
			testInfo.get().info(blacklisted +" found");
		} else {
			testInfo.get().error("Nothing found");
		}
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//div[@id='map']/div[2]/div/div/a[2]");
		Thread.sleep(500);
		
	}
}
