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

import util.TestBase;
import util.TestUtils;

public class mobileProvisioning extends TestBase {
	String email, password, MSISDN, Serial, iMSISDN, iSerial;
	@Parameters({ "testEnv" })
	@Test
	public void login(String testEnvn) throws Exception{
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnvn.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/simropAdmin.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/simropAdmin.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("test_Data");
		email = (String) envs.get("email");
		password = (String) envs.get("password");
		MSISDN = (String) envs.get("MSISDN");
		iMSISDN = (String) envs.get("iMSISDN");
		iSerial = (String) envs.get("iSerial");
		Serial = (String) envs.get("Serial");

		getDriver().get("https://glosimroptest.seamfix.com:8182/simrop/");
		// Select Login type
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);
		Thread.sleep(1000);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(password);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
	}

	@Test(groups = { "Regression" })
	public void navigateToMobileProvisioning() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Mobile/MSISDN Provisioning");
		Thread.sleep(1500);
			try {
				TestUtils.scrollToElement("XPATH", "//li[15]/a/p");
				getDriver().findElement(By.xpath("//li[15]/a/p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "div.wrapper:nth-child(1) div.sidebar div.sidebar-wrapper ul.nav:nth-child(2) li.nav-item.active.simrop-:nth-child(29) a.nav-link > p:nth-child(2)");
				getDriver().findElement(By.cssSelector("div.wrapper:nth-child(1) div.sidebar div.sidebar-wrapper ul.nav:nth-child(2) li.nav-item.active.simrop-:nth-child(29) a.nav-link > p:nth-child(2)")).click();
				Thread.sleep(500);
			}
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[1]/label/input")));
			Thread.sleep(500);
			TestUtils.testTitle("Page Load Confirmation");
			TestUtils.assertSearchText("XPATH", "//div[1]/div[1]/div[1]/div[1]/div[1]/h4[1]", "MSISDN Provisioning");
		}
	
	@Test(groups = { "Regression" })
	public void assertProvisioningcount() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("activeMsisdn")));
		String activeMSISDN = getDriver().findElement(By.id("activeMsisdn")).getText();
		String availableMSISDN = getDriver().findElement(By.id("availableMsisdn")).getText();
		String blockedMSISDN = getDriver().findElement(By.id("blockedMsisdn")).getText();
	}

	@Test(groups = { "Regression" })
  public void changeSize() throws Exception {
	  WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	  TestUtils.testTitle("Change Tbale value size to 100");
	  new Select(getDriver().findElement(By.xpath("//*[@id='roleManagementTable_length']/div/label/select"))).selectByVisibleText("100");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Confriming page size is 100");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='users']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of user returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
		
	}
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
  public void searchMSISDNTable() throws Exception {
	  WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	  TestUtils.testTitle("Search with MSISDN");
	  getDriver().findElement(By.name("msisdn")).clear();
	  Thread.sleep(1000);
	  getDriver().findElement(By.name("msisdn")).sendKeys(MSISDN);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(2000);
	  TestUtils.assertSearchText("XPATH", "//*[@id=\"users\"]/tbody/tr/td[2]", MSISDN);
	  getDriver().findElement(By.name("msisdn")).clear();
	  
	  TestUtils.testTitle("Search with Serial");
	  getDriver().findElement(By.name("simSerial")).clear();
	  getDriver().findElement(By.name("simSerial")).sendKeys(Serial);
	  Thread.sleep(1000);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(1000);
	  TestUtils.assertSearchText("XPATH", "//*[@id=\"users\"]/tbody/tr/td[3]", Serial);
	  getDriver().findElement(By.name("simSerial")).clear();
	  
	  TestUtils.testTitle("Search with Invalid MSISDN");
	  getDriver().findElement(By.name("msisdn")).clear();
	  getDriver().findElement(By.name("msisdn")).sendKeys(iMSISDN);
	  Thread.sleep(1000);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(1000);
	  TestUtils.assertSearchText("XPATH", "//tbody[1]/tr[1]/td[1]/div[1]/div[1]", "No data available in table");
	  getDriver().findElement(By.name("msisdn")).clear();
	  
	  TestUtils.testTitle("Search with Invalid Serial");
	  getDriver().findElement(By.name("simSerial")).clear();
	  getDriver().findElement(By.name("simSerial")).sendKeys(iSerial);
	  Thread.sleep(1000);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(1000);
	  TestUtils.assertSearchText("XPATH", "//tbody[1]/tr[1]/td[1]/div[1]/div[1]", "No data available in table");
	
	  TestUtils.testTitle("Search with Invalid Serial and Invalid MSISDN");
	  getDriver().findElement(By.name("msisdn")).clear();
	  getDriver().findElement(By.name("msisdn")).sendKeys(iMSISDN);
	  Thread.sleep(1000);
	  getDriver().findElement(By.name("simSerial")).clear();
	  getDriver().findElement(By.name("simSerial")).sendKeys(iSerial);
	  Thread.sleep(1000);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(1000);
	  TestUtils.assertSearchText("XPATH", "//tbody[1]/tr[1]/td[1]/div[1]/div[1]", "No data available in table");
	  
	  TestUtils.testTitle("Search with both MSISDN & Serial");
	  getDriver().findElement(By.name("msisdn")).clear();
	  getDriver().findElement(By.name("msisdn")).sendKeys(MSISDN);
	  Thread.sleep(1000);
	  getDriver().findElement(By.name("simSerial")).clear();
	  getDriver().findElement(By.name("simSerial")).sendKeys(Serial);
	  Thread.sleep(1000);
	  getDriver().findElement(By.xpath("//form/div/div/div/div[3]/button")).click();
	  Thread.sleep(1000);
	  TestUtils.assertSearchText("XPATH", "//*[@id=\"users\"]/tbody/tr/td[2]", MSISDN);
	  TestUtils.assertSearchText("XPATH", "//*[@id=\"users\"]/tbody/tr/td[3]", Serial);
  }
  
}