package CACTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
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

public class SimSwapFraudAlerts extends TestBase {

	private String msisdn;
	private String startDate;
	private String endDate;
	private StringBuffer verificationErrors = new StringBuffer();
	
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
        JSONObject envs = (JSONObject) config.get("SIMSwapFraudAlerts");
        msisdn = (String) envs.get("msisdn");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
        
    }
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToSimSwapFraudAlertsTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		TestUtils.testTitle("Navigate to SIM Swap Fraud Alerts");
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5825471809SIM Swap Fraud Alert\"] > p");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"5825471809SIM Swap Fraud Alert\"] > p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5825471809SIM Swap Fraud Alert\"]");
				Thread.sleep(500);
				getDriver().findElement(By.cssSelector("a[name=\"5825471809SIM Swap Fraud Alert\"]")).click();
				Thread.sleep(500);
			}
		}else {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"854245080SIM Swap Fraud Alerts\"] > p");
				getDriver().findElement(By.cssSelector("a[name=\"854245080SIM Swap Fraud Alerts\"] > p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"854245080SIM Swap Fraud Alerts\"]");
				getDriver().findElement(By.cssSelector("a[name=\"854245080SIM Swap Fraud Alerts\"]")).click();
				Thread.sleep(500);
			}
		}
		 wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/h4")));
         TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div/div/div/div/h4", "SIM Swap Fraud Alerts");
         Thread.sleep(500);

	}
	
	@Test(groups = { "Regression" })
	public void assertSimSwapFraudAlertsCount() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		Thread.sleep(1000);
		TestUtils.testTitle("Total SIM Swap Requests");
        String totalSwapRequests = getDriver().findElement(By.id("totalCount")).getText();
        if (totalSwapRequests.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalSwapRequests + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }
		
		String totalFraudAlertsString = getDriver().findElement(By.cssSelector("#fraudCount")).getText();
		String totalPendingFraudAlertsString = getDriver().findElement(By.cssSelector("#pendingCount")).getText();
		String totalTreatedFraudAlertsString = getDriver().findElement(By.cssSelector("#treatedCount")).getText();

		int actualtotalFraudAlerts = TestUtils.convertToInt(totalFraudAlertsString);
		int actualtotalPendingFraudAlerts = TestUtils.convertToInt(totalPendingFraudAlertsString);
		int actualtotalTreatedFraudAlerts = TestUtils.convertToInt(totalTreatedFraudAlertsString);
		int expectedTotalFraudAlertsVal = actualtotalPendingFraudAlerts + actualtotalTreatedFraudAlerts;
		
		TestUtils.testTitle("Assert SIM Swap Fraud Alerts count");
		try {
			Assert.assertEquals(expectedTotalFraudAlertsVal, actualtotalFraudAlerts);
			testInfo.get().log(Status.INFO,
					"Total Fraud Alerts <b>(" + expectedTotalFraudAlertsVal + ")</b> is equal to Total Pending Fraud Alerts <b>("
							+ actualtotalPendingFraudAlerts + ")</b> and Total Treated Fraud Alerts <b>(" + actualtotalTreatedFraudAlerts + ").</b>");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}
		
		// Page size
		Thread.sleep(500);
		new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select"))).selectByVisibleText("100");
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(1000);
		
		TestUtils.testTitle("Change page size to: 100");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='users']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: <b>" + rowCount + "</b>");
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewDetailsOfMsisdnTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Status");
		Thread.sleep(1000);
		
		// Filter by Msisdn
		TestUtils.testTitle("View details of msisdn: " + msisdn);
		getDriver().findElement(By.name("msisdn")).clear();
		getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[4]", msisdn);
		Thread.sleep(500);
		
		// Click on Action button
		getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		
		// Click on the Lock button
		getDriver().findElement(By.id("lockSwapAction")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Assert details of fraud Alert
		TestUtils.testTitle("Assert details of SIM Swap Fraud Alert");
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "myModalLabel", "SIM Swap Locking Approval");
		Assertion.assertSIMSwapFraudAlertDetailsTest();
		Thread.sleep(500);
		
		// Close the Modal
		getDriver().findElement(By.id("closeBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByMsisdnTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by msisdn: " + msisdn);
		
		getDriver().findElement(By.name("msisdn")).clear();
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Status");
		Thread.sleep(1000);
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[4]", msisdn);
		Thread.sleep(500);
		
		// Assert Fraud Alert Table Test
		TestUtils.testTitle("Assert details of Sim Swap Fraud Alert table");
		Assertion.assertTableDataSimSwapFraudAlertsTest();
		Thread.sleep(500);
	}
	
	public void statusFilter(String status) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.name("msisdn")).clear();
		Thread.sleep(1000);
		
		TestUtils.testTitle("Filter by Status:" + status);
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText(status);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[6]", status);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td", "No data available in table");
		}
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByStatusTest(String testEnv) throws Exception {

		// Locked 
		statusFilter("Locked");
		
		// Unlocked 
		statusFilter("Unlocked");
		
		// Pending 
		statusFilter("Pending");
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByStatusAndDateTest(String testEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.name("msisdn")).clear();
		Thread.sleep(500);
		getDriver().findElement(By.name("endDate")).clear();
		Thread.sleep(500);
		
		TestUtils.testTitle("Filter by Status:" + "Pending and start Date: " + startDate);
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Pending");
		Thread.sleep(500);
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[6]", "Pending");
		String sDate = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		TestUtils.convertDate2(sDate);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.name("msisdn")).clear();
		Thread.sleep(1000);
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Status");
		Thread.sleep(1000);
		
		TestUtils.testTitle("Filter by Start date: " + startDate);
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		String sDate = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		TestUtils.convertDate2(sDate);
		
		TestUtils.testTitle("Filter by End date: " + endDate);
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		String eDate = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		TestUtils.convertDate2(eDate);
		
		TestUtils.testTitle("Filter by date range: " + startDate + " and " + endDate);
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
		String table_Date = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		String newDate = TestUtils.convertDate2(table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, newDate);
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void lockSimSwapFraudAlertTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.name("msisdn")).clear();
		Thread.sleep(1000);
		
		// Filter By Unlocked Status
		TestUtils.testTitle("Filter by Status:" + "Unlocked");
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Unlocked");
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[6]", "Unlocked");
		
		// Click on Action button
		getDriver().findElement(By.cssSelector("a.btn-icon.remove > i.material-icons")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Click on the Lock button
		getDriver().findElement(By.id("lockSwapAction")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Scroll Down
		TestUtils.scrollToElement("ID", "APPROVED");
		
		// Submit without a SIM Swap Lock Reason
		TestUtils.testTitle("Submit form without a SIM Swap Lock Reason");
		getDriver().findElement(By.id("APPROVED")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Please enter a reason in the Feedback section before you can proceed");
		
		// Enter Reason
		TestUtils.testTitle("Submit form with a SIM Swap Lock Reason");
		getDriver().findElement(By.id("feedback")).sendKeys("Seamfix Test");  
		getDriver().findElement(By.id("APPROVED")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		if (getDriver().findElement(By.xpath("//h2")).isDisplayed()) {
			try {
				TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]", "SIM swap Lock request successfully approved. Waiting for the next level of approval");
			} catch (Exception e) {
				 TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
			}
		} else {
			 TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
		}
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void unlockSimSwapFraudAlertTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Filter By Locked Status
		TestUtils.testTitle("Filter by Status:" + "Locked");
		getDriver().findElement(By.name("status")).click();
		Thread.sleep(500);
		new Select(getDriver().findElement(By.name("status"))).selectByVisibleText("Locked");
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[6]", "Locked");
		
		// Click on Action button
		getDriver().findElement(By.cssSelector("a.btn-icon.remove > i.material-icons")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Click on the Lock button
		getDriver().findElement(By.id("unlockSwapAction")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		// Scroll Down
		TestUtils.scrollToElement("ID", "APPROVED");
		
		// Submit without a SIM Swap Lock Reason
		TestUtils.testTitle("Submit form without a SIM Swap Lock Reason");
		getDriver().findElement(By.id("APPROVED")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//h2", "Please enter a reason in the Feedback section before you can proceed");
		
		// Enter Reason
		TestUtils.testTitle("Submit form with a SIM Swap unlock Reason");
		getDriver().findElement(By.id("feedback")).sendKeys("Seamfix Test");  
		getDriver().findElement(By.id("APPROVED")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(500);
		
		if (getDriver().findElement(By.xpath("//h2")).isDisplayed()) {
			try {
				TestUtils.assertSearchText("XPATH", "//*[contains(text(),'unlock request successfully approved.. waiting for the next level of approval')]", "unlock request successfully approved.. waiting for the next level of approval");
			} catch (Exception e) {
				TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap unlocked successfully')]", "Sim Swap unlocked successfully");
			}
		} else {
			 TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap unlocked successfully')]", "Sim Swap unlocked successfully");
		}
	}
	
}
