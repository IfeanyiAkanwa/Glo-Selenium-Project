package CACTestCases;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

import org.openqa.selenium.support.ui.Select;

import javax.swing.text.Element;

public class QuarantinedEyeballing extends TestBase {
	
	private String kitTag;
	private String msisdn;
	private String msisdn1;
	private String startDate;
	private String endDate;
	private String releaseReason;
	private String rejectReason;
	private String agentEmail;
	private String kitTag2;
	private StringBuffer verificationErrors = new StringBuffer();
	private String savedphoneNumber ="";
	private String rejectMsisdn;
	private String searchKit;
	private String quarantinedMsisdn;
	private String startTime;
	private String endTime;
	
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
        JSONObject envs = (JSONObject) config.get("quarantinedEyeballing");
        kitTag = (String) envs.get("kitTag");
        msisdn = (String) envs.get("msisdn");
		msisdn1 = (String) envs.get("msisdn1");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
        releaseReason = (String) envs.get("releaseReason");
		agentEmail = (String) envs.get("agentEmail");
		kitTag2 = (String) envs.get("kitTag2");
		rejectMsisdn = (String) envs.get("rejectMsisdn");
		searchKit = (String) envs.get("searchKit");
		quarantinedMsisdn = (String) envs.get("quarantinedMsisdn");
		startTime = (String) envs.get("startTime");
		endTime = (String) envs.get("endTime");
    }
	
	@Parameters("testEnv")
	@Test(groups = { "Regression" })
	public void navigateToQuarantinedEyeballingTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Navigate to Quarantined Eyeballing");
		
		
		if (testEnv.equalsIgnoreCase("stagingData")) {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"712090EyeBalling Pool\"] > p");
				getDriver().findElement(By.cssSelector("a[name=\"712090EyeBalling Pool\"] > p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				Thread.sleep(500);
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"712090EyeBalling Pool\"]");
				getDriver().findElement(By.cssSelector("a[name=\"712090EyeBalling Pool\"]")).click();
				Thread.sleep(500);
			}
			getDriver().findElement(By.cssSelector("a[name=\"5817767753Quarantined \"]")).click();
			//getDriver().findElement(By.xpath("//a[contains(text(),'Quarantined Ng')]")).click();
			Thread.sleep(500);
		}else {
			try {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"324177975Eyeballing Pool\"] > p");
				getDriver().findElement(By.cssSelector("a[name=\"324177975Eyeballing Pool\"] > p")).click();
				Thread.sleep(500);
			} catch (Exception e) {
				TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"324177975Eyeballing Pool\"]");
				getDriver().findElement(By.cssSelector("a[name=\"324177975Eyeballing Pool\"]")).click();
				Thread.sleep(500);
			}
			//getDriver().findElement(By.linkText("Quarantined")).click();
			getDriver().findElement(By.xpath("//a[contains(text(),'Quarantined Ng')]")).click();
			Thread.sleep(500);
		}
	
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Quarantined Eyeballing");

	}
	
	@Test(groups = { "Regression" })
	public void assertQuarantineStatusCount() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		String totalQuarantineString = getDriver().findElement(By.id("pendingCount")).getText();
		String totalReleasedValString = getDriver().findElement(By.id("invalidCount")).getText();
		String totalRejectedValString = getDriver().findElement(By.id("rejectedCount")).getText();
		String totalPendingValString = getDriver().findElement(By.xpath("//div[4]/div/div/h5")).getText();

		int actualtotalQuarantine = TestUtils.convertToInt(totalQuarantineString);
		int actualtotalReleasedVal = TestUtils.convertToInt(totalReleasedValString);
		int actualtotalPendingVal = TestUtils.convertToInt(totalPendingValString);
		int actualRejectedValString = TestUtils.convertToInt(totalRejectedValString);
		int expectedTotalQuarantineVal = actualtotalReleasedVal + actualtotalPendingVal + actualRejectedValString;
		
		TestUtils.testTitle("Assert Quarantine status count");
		try {
			Assert.assertEquals(expectedTotalQuarantineVal, actualtotalQuarantine);
			testInfo.get().log(Status.INFO,
					"Total Quarantine records <b>(" + expectedTotalQuarantineVal + ")</b> is equal to Total released records <b>("
							+ actualtotalReleasedVal + ")</b> and Total pending records <b>(" + actualtotalPendingVal + ").</b>");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		/*// Page size
		new Select(getDriver().findElement(By.name("eyeBallingDetailsTableA_length"))).selectByVisibleText("250");
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		
		TestUtils.testTitle("Change page size to: 250");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='eyeBallingDetailsTableA']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: <b>" + rowCount + "</b>");
		} else {
			testInfo.get().info("Table is empty.");
		}*/
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void viewDetailsOfRegistrationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		searchByKitTagTest(testEnv);
		TestUtils.testTitle("View Details of Registration");
		
		// Assert Basic Information
		getDriver().findElement(By.linkText("Basic Information")).click();
		TestUtils.testTitle("Assert Basic Information of Registration");
		Thread.sleep(2000);
		Assertion.assertBasicInfoEyeballingTest();
		Assertion.imageDisplayEyeballing();
		Thread.sleep(500);
		
		// Assert Biometric Details
		getDriver().findElement(By.xpath("//a[contains(text(),'Biometric Details')]")).click();
		TestUtils.testTitle("Assert Biometric Details of Registration");
		Assertion.assertBiometricDetailsEyeballingTest();
	
		// Assert Enrolment Signature
		getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
		TestUtils.testTitle("Assert Enrolment Signature of Registration");
		Assertion.assertEnrolmentSignatureEyeballingTest();
		Thread.sleep(500);
		
		// Assert Company Details
		getDriver().findElement(By.xpath("//a[contains(text(),'Company Details')]")).click();
		TestUtils.testTitle("Assert Company Details of Registration");
		Assertion.assertCompanyDetailsEyeballingTest();
		Thread.sleep(500);

		// Assert Passport Details
		getDriver().findElement(By.linkText("Passport Details")).click();
		TestUtils.testTitle("Assert Passport Details of Registration");
		Assertion.assertPassportDetailsEyeballingTest();
		Thread.sleep(500);

		// Assert Documents
		getDriver().findElement(By.linkText("Documents")).click();
		TestUtils.testTitle("Assert Documents of Registration");
		Assertion.assertDocumentsEyeballingTest();

		// Assert NIN Details
		TestUtils.testTitle("Filter by MSISDN:"+msisdn1);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(msisdn1);
		getDriver().findElement(By.cssSelector("span.slider.round")).click();
		getDriver().findElement(By.id("searchBtn"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("NIN Details")));
		getDriver().findElement(By.linkText("NIN Details")).click();
		TestUtils.testTitle("Assert NIN Details");
		Assertion.assertNINEyeballingTest();
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='kitTag']")));
		TestUtils.testTitle("Filter by kit tag: " + searchKit);
		getDriver().findElement(By.xpath("//input[@name='kitTag']")).clear();
		getDriver().findElement(By.xpath("//input[@name='kitTag']")).sendKeys(searchKit);
		Thread.sleep(2000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(2000);
		getDriver().findElement(By.cssSelector("span.slider.round")).click();
		Thread.sleep(1000);

		getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Quarantine Information')]");
		Thread.sleep(2000);
		TestUtils.assertSearchText("XPATH", "//div[4]/div/div/div/table/tbody/tr[8]/td[2]/div", searchKit); 
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByMsisdnTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		TestUtils.testTitle("Filter by msisdn: " + quarantinedMsisdn);

		//getDriver().findElement(By.id("kitTag")).clear();
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='kitTag']")));
		getDriver().findElement(By.xpath("//input[@name='kitTag']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(quarantinedMsisdn);
		Thread.sleep(2000);
		getDriver().findElement(By.id("searchBtn")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.assertSearchText("XPATH", "//div[2]/div/span", quarantinedMsisdn);
		Thread.sleep(500);
	}
	
	public void quarantineReasonFilter(String reason) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Quarantine Reason:" + reason);
		Thread.sleep(500);
		//getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(reason);

		Select drpReason = new Select(getDriver().findElement(By.xpath("//select")));
		drpReason.selectByVisibleText(reason);

		Thread.sleep(500);
		//getDriver().findElement(By.xpath("select.form-control.custom-select.ng-pristine.ng-valid.ng-touched")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		try {
			//TestUtils.assertSearchText("ID", "fastestCaptureTimeDiv", reason);
			//Get the rejection reason
			TestUtils.scrollToElement("XPATH", "//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[6]/span");
			String rejectReasons=getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[6]/span")).getText();
			if (rejectReasons.contains(reason)){
				testInfo.get().log(Status.INFO, reason+" found");
			}else{
				testInfo.get().log(Status.INFO, reason+" not found");
			}
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//div[contains(text(),'No data available in table')]", "No data available in table");
		}
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByQuarantineReasonTest(String testEnv) throws Exception {

		getDriver().navigate().refresh();
		/*getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='kitTag']")).clear();
		if (!getDriver().findElement(By.xpath("//div[3]/label/input")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		
		Thread.sleep(2000);*/

		//getDriver().findElement(By.cssSelector("span.slider.round")).click();
		
		// ILLEGAL SYNC FILE 
		quarantineReasonFilter("ILLEGAL SYNC FILE");
		
		// VALID
		quarantineReasonFilter("VALID");

		// BFPHOBIA SYNC FILES
		quarantineReasonFilter("BFPHOBIA SYNC FILES");

		// INVALID MSISDN
		quarantineReasonFilter("INVALID MSISDN");

		// BLACKLISTED AGENT
		quarantineReasonFilter("BLACKLISTED AGENT");

		// BLACKLISTED APP VERSION
		quarantineReasonFilter("BLACKLISTED APP VERSION");

		/*// NULL META DATA
		quarantineReasonFilter("NULL META DATA");*/

		// NULL ENROLMENT LOG
		quarantineReasonFilter("NULL ENROLMENT LOG");

		// INVALID MSISDN DETAIL
		quarantineReasonFilter("INVALID MSISDN DETAIL");
		
		// DUPLICATE SIM SERIAL
		quarantineReasonFilter("DUPLICATE SIM SERIAL");

		// INVALID SIM SERIAL
		quarantineReasonFilter("INVALID SIM SERIAL");

		/*// CLIENT MAC ADDRESS MISMATCH
		quarantineReasonFilter("CLIENT MAC ADDRESS MISMATCH");

		// CLIENT DEVICE ID MISMATCH
		quarantineReasonFilter("CLIENT DEVICE ID MISMATCH");
		
		// BLACKLISTED MAC ADDRESS OR KIT
		quarantineReasonFilter("BLACKLISTED MAC ADDRESS OR KIT");*/
		
		// BLACKLISTED DEVICE ID
		quarantineReasonFilter("BLACKLISTED DEVICE ID");

		/*// KIT NOT TAGGED
		quarantineReasonFilter("KIT NOT TAGGED");

		// UNREGISTERED MAC ADDRESS
		quarantineReasonFilter("UNREGISTERED MAC ADDRESS");*/

		// UNREGISTERED DEVICE ID
		quarantineReasonFilter("UNREGISTERED DEVICE ID");
		
		/*// NULL MAC ADDRESS
		quarantineReasonFilter("NULL MAC ADDRESS");

		// NULL DEVICE ID
		quarantineReasonFilter("NULL DEVICE ID");*/

		// MAC ADDRESS ASSOCIATED WITH MULTIPLE KITS
		quarantineReasonFilter("MAC ADDRESS ASSOCIATED WITH MULTIPLE KITS");

		/*// MAC_ADDRESS KIT TAG MISMATCH
		quarantineReasonFilter("MAC_ADDRESS KIT TAG MISMATCH");*/

		// INVALID REGISTRATION TIME
		quarantineReasonFilter("INVALID REGISTRATION TIME");
	}
	
	
	public void releaseStatusFilter(String status) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Release Status: " + status);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(status);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		getDriver().findElement(By.id("blacklistTab")).click();
		TestUtils.assertSearchText("XPATH", "//div[@id='enrollmentSignature']/div/div/div/table/tbody/tr[2]/td[2]/div",	status);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByReleaseStatusTest(String testEnv) throws Exception {
		
		if (!getDriver().findElement(By.id("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);
		
		// RELEASED
		releaseStatusFilter("RELEASED");
	
		// QUARANTINED
		releaseStatusFilter("QUARANTINED");
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advancedBtn")));
		if (!getDriver().findElement(By.xpath("//div[5]/label/input")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);
		
		TestUtils.testTitle("Filter by Start date: " + startDate); 
		
//		getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).clear();
//		getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).sendKeys(startDate);
		
		getDriver().findElement(By.xpath("//div[5]/label/input")).clear();
		getDriver().findElement(By.xpath("//div[5]/label/input")).sendKeys(startDate);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		//getDriver().findElement(By.id("blacklistTab")).click();
		//TestUtils.scrollUntilElementIsVisible("ID", "fastestCaptureTimeDiv");
		//GET START DATE
		Thread.sleep(1000);
		String sDate = getDriver().findElement(By.xpath("//div[3]/span")).getText();
		TestUtils.convertDate(sDate);
		
		TestUtils.testTitle("Filter by End date: " + endDate);
		getDriver().findElement(By.xpath("//div[6]/label/input")).clear();
		getDriver().findElement(By.xpath("//div[6]/label/input")).sendKeys(endDate);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		//getDriver().findElement(By.id("blacklistTab")).click();
		//TestUtils.scrollUntilElementIsVisible("ID", "fastestCaptureTimeDiv");
		//GET DATE
		String eDate = getDriver().findElement(By.xpath("//div[3]/span")).getText();
		TestUtils.convertDate(eDate);
		
		TestUtils.testTitle("Filter by date range: " + startDate + " and " + endDate);
		getDriver().findElement(By.xpath("//div[5]/label/input")).clear();
		getDriver().findElement(By.xpath("//div[5]/label/input")).sendKeys(startDate);
		getDriver().findElement(By.xpath("//div[6]/label/input")).clear();
		getDriver().findElement(By.xpath("//div[6]/label/input")).sendKeys(endDate);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		/*getDriver().findElement(By.id("blacklistTab")).click();
		TestUtils.scrollUntilElementIsVisible("ID", "fastestCaptureTimeDiv");*/
		String table_Date = getDriver().findElement(By.xpath("//div[3]/span")).getText();
		String newDate = TestUtils.convertDate(table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, newDate);
		
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByAgentEmailTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advancedBtn")));
		if (!getDriver().findElement(By.xpath("//div[5]/label/input")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		Thread.sleep(500);

		TestUtils.testTitle("Filter by Agent Email: " + agentEmail);
		getDriver().findElement(By.xpath("//ng-select[@id='agents']/div/div/div[2]/input")).clear();
		getDriver().findElement(By.xpath("//ng-select[@id='agents']/div/div/div[2]/input")).sendKeys(agentEmail);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/span")));
		String name=getDriver().findElement(By.xpath("//div[2]/span")).getText();
		testInfo.get().info("Detail:"+name+" found");

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void releaseQuarantinedRecordTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.scrollToElement("ID", "searchBtn");
		if (testEnv.equalsIgnoreCase("prodData")) {
			// Filter by KitTag
			TestUtils.testTitle("Filter by kit tag: " + kitTag);
			getDriver().findElement(By.id("kitTag")).clear();
			getDriver().findElement(By.id("kitTag")).sendKeys(kitTag);
		}
		Thread.sleep(500);

		//Check start date
		if (!getDriver().findElement(By.xpath("//div[3]/label/input")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		// Filter by Quarantined Status
		TestUtils.testTitle("Filter by Release Status: QUARANTINED");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.scrollToElement("XPATH", "//div[3]/div/div[2]/span");
		TestUtils.assertSearchText("XPATH", "//div[3]/div/div[2]/span", "QUARANTINED");
		Thread.sleep(2000);

		//Get Phone number
		String phoneNumber = getDriver().findElement(By.cssSelector("div.col-md-2.my-auto > span")).getText();
		System.out.println("Phone: "+phoneNumber);

		TestUtils.testTitle("Release Quarantine record");
		//Confirm button
		getDriver().findElement(By.cssSelector("button.float-right.btn.btn-yellow.btn-sm.mr-4")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Release Confirmation')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Release Confirmation')]",	"Release Confirmation");
		
		// Submit form without supplying required field
		TestUtils.testTitle("Submit form without supplying release reason");
		getDriver().findElement(By.id("doFastEyeballingBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[contains(text(),'Feedback Required')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Feedback Required')]", "Feedback Required");
		//getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);
		
		// Release a Quarantine Record
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys(releaseReason);
		getDriver().findElement(By.id("doFastEyeballingBtn")).click();
		Thread.sleep(500);
		
		// Test the Cancel button
		//No button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[2]")));
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("cancelEyeballing")).click();
		Thread.sleep(500);
		
		// Submit a record for release
		TestUtils.testTitle("Submit form after supplying release reason");
		//Confirm button
		getDriver().findElement(By.cssSelector("button.float-right.btn.btn-yellow.btn-sm.mr-4")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys(releaseReason);
		getDriver().findElement(By.id("doFastEyeballingBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Confirm Release ?')]")));
		TestUtils.assertSearchText("XPATH", "//h2[contains(text(),'Confirm Release ?')]", "Confirm Release ?");
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[1]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));

		try{
			Thread.sleep(1200);
			TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Success!')]", "Success!");
			Thread.sleep(500);
		}catch (Exception e){
			try{
				TestUtils.assertSearchText("XPATH", "//*[contains(text(),'NIN_VERIFICATION_FAILURE')]", "NIN_VERIFICATION_FAILURE");
				testInfo.get().error("NIN_VERIFICATION_FAILURE RETURNED");
			}catch (Exception e1){
				try{
					TestUtils.assertSearchText("XPATH", "//*[contains(text(),'BLACK_LISTED_APP_VERSION')]", "BLACK_LISTED_APP_VERSION");
				}catch (Exception e2){
					String screenshotPath = TestUtils.addScreenshot();
					testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
					Thread.sleep(1000);
					testInfo.get().error("No data entry for release, multiple re-registration needs to be generated over time");
				}
			}
		}

		
		// To confirm the release status is updated after a successful release of record
		Thread.sleep(1000);
		TestUtils.testTitle("To confirm the release status is updated after a successful release of record: " + phoneNumber);
		getDriver().navigate().refresh();
		//Search for msisdn
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='msisdn']")));
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(phoneNumber);
		
		// Filter by Released Status
		/*Select drpRelease2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
		drpRelease2.selectByVisibleText("RELEASED");*/
		//Switch to Released page
		getDriver().findElement(By.xpath("//a[contains(text(),'Released')]")).click();

		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.scrollToElement("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]/span");
		TestUtils.assertSearchText("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]/span", "RELEASED");
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void rejectQuarantinedRecord(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advancedBtn")));
		Thread.sleep(1000);

		// Test reject quarantined
		TestUtils.testTitle("Test reject quarantined Record Msisdn:"+rejectMsisdn);

		// Filter by Quarantined Status
		TestUtils.testTitle("Filter by Release Status: QUARANTINED");
		Thread.sleep(2000);
		getDriver().findElement(By.xpath("//a[contains(text(),'Quarantined')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("kitTag")));

		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(rejectMsisdn);

		TestUtils.scrollToElement("ID", "searchBtn");

		TestUtils.scrollToElement("XPATH", "//div[3]/div/div[2]/span");
		TestUtils.assertSearchText("XPATH", "//div[3]/div/div[2]/span", "QUARANTINED");
		Thread.sleep(500);
		//Get Phone number
		String phoneNumber = getDriver().findElement(By.xpath("//div[2]/div/span")).getText();
		savedphoneNumber=phoneNumber;
		TestUtils.testTitle("Reject Quarantine record");
		String tagId=getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
		//Confirm button
		getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[3]/div/div[3]/button[3]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Reject Record')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Reject Record')]",	"Reject Record");

		// Submit form without supplying required field
		TestUtils.testTitle("Submit form without supplying rejection reason");
		getDriver().findElement(By.id("doRejectionBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[contains(text(),'Rejection reason Required')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Rejection reason Required')]", "Rejection reason Required");
		//getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);

		// Reject a Quarantine Record
		Select rejReason = new Select(getDriver().findElement(By.id("rejectionReason")));
		rejReason.selectByVisibleText("Test");

		getDriver().findElement(By.id("doRejectionBtn")).click();
		Thread.sleep(500);

		//Test the Cancel button
		//No button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[2]")));
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("cancelRejection")).click();
		Thread.sleep(500);

		// Reject a record
		TestUtils.testTitle("Reject a record ");

		//Confirm button
		getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[3]/div/div[3]/button[3]")).click();
		Thread.sleep(2000);
		Select drp2Reason = new Select(getDriver().findElement(By.id("rejectionReason")));
		drp2Reason.selectByVisibleText("Test");
		getDriver().findElement(By.id("doRejectionBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Confirm Rejection')]")));
		TestUtils.assertSearchText("XPATH", "//h2[contains(text(),'Confirm Rejection')]", "Confirm Rejection");

		//Confirm Rejection
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[1]")).click();

		Thread.sleep(1000);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Record with unique ID of "+tagId+" was successfully rejected')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Record with unique ID of "+tagId+" was successfully rejected')]", "Record with unique ID of "+tagId+" was successfully rejected");
		Thread.sleep(500);

		// To confirm the reject status is updated after a successful rejection of a record
		Thread.sleep(1000);
		TestUtils.testTitle("To confirm the rejection status is updated after a successful rejection of record: " + phoneNumber);
		getDriver().findElement(By.xpath("//a[contains(text(),'Rejected')]")).click();
		Thread.sleep(1000);
		//Search for msisdn
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(rejectMsisdn);
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.scrollToElement("XPATH", "//div[3]/div/div[2]/span");
		TestUtils.assertSearchText("XPATH", "//div[3]/div/div[2]/span", "REJECTED");
	}


	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void UndoRejectedQuarantinedRecord(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Rejected')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'Rejected')]")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//h4[contains(text(),'Rejected Records')]");
		TestUtils.testTitle("Undo rejected Quarantine record. Msisdn:"+rejectMsisdn);

		//Search for rejected MSISDN
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(rejectMsisdn);
		Thread.sleep(1000);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(1000);
	
		
		
 		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-3:nth-child(3) > p:nth-child(5) > span")));
		//Confirm button
		getDriver().findElement(By.xpath("//button[contains(text(),'Undo Rejection')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Undo Rejection')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Undo Rejection')]",	"Undo Rejection");

		// Submit form without supplying required field
		TestUtils.testTitle("Submit form without supplying rejection reason");
		getDriver().findElement(By.id("doFastEyeballingBtn")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[contains(text(),'Feedback Required')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Feedback Required')]", "Feedback Required");
		//getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);

		//Test the Cancel button
		//No button
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys(releaseReason);
		getDriver().findElement(By.id("cancelEyeballing")).click();
		Thread.sleep(500);


		
		String tagId = getDriver().findElement(By.cssSelector(".col-md-3:nth-child(3) > p:nth-child(5) > span")).getText();
		
		System.out.println("The uniqueID is "+ tagId);

		// Undo rejection a record
		TestUtils.testTitle("Undo rejection a record ");
		//Confirm button   
		getDriver().findElement(By.xpath("//button[contains(text(),'Undo Rejection')]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("feedback")).clear();
		getDriver().findElement(By.id("feedback")).sendKeys(releaseReason);
		getDriver().findElement(By.id("doFastEyeballingBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Confirm Undo Rejection')]")));
		TestUtils.assertSearchText("XPATH", "//h2[contains(text(),'Confirm Undo Rejection')]", "Confirm Undo Rejection");

		//Confirm Rejection
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[1]")).click();
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Rejection on record with unique ID of "+tagId+" was successfully undone')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Rejection on record')]", "Rejection on record with unique ID of "+tagId+" was successfully undone");
//	
		Thread.sleep(500);

		// To confirm the undo rejection status is updated after a successful undo of a record
		Thread.sleep(1000);
		TestUtils.testTitle("To confirm the undo rejection status is updated after a successful undo of a record: " + rejectMsisdn);
		getDriver().findElement(By.xpath("//a[contains(text(),'Quarantined')]")).click();

		//Search for msisdn
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='msisdn']")));
		getDriver().findElement(By.name("kitTag")).clear();
//		getDriver().findElement(By.name("kitTag")).sendKeys(kitTag2);
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).clear();
		getDriver().findElement(By.xpath("//input[@name='msisdn']")).sendKeys(rejectMsisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2")));
		TestUtils.scrollToElement("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]/span");
		TestUtils.assertSearchText("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]/span", "QUARANTINED");

		
		TestUtils.testTitle("Return record back to rejected : " + rejectMsisdn); 
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//button[contains(text(),'Reject Record')]")).click();
		Thread.sleep(500);
		Select rejReason = new Select(getDriver().findElement(By.id("rejectionReason")));
		rejReason.selectByVisibleText("Test");

		getDriver().findElement(By.id("doRejectionBtn")).click();
		Thread.sleep(500);

		//Confirm Rejection
		getDriver().findElement(By.xpath("//body/modal-container[2]/div[1]/div[1]/div[2]/button[1]")).click();
	}

	@Test
	public void viewWorkAssignment() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

		//To confirm that user with privilege "EYEBALLING_AGENT_WORK_ASSIGNMENT" can view eyeballing assignment
		TestUtils.testTitle("To confirm that user can view eyeballing assignment");
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow.float-right.ml-5.btn-sm")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		String sn = getDriver().findElement(By.xpath("//td")).getText();
		String assignmentCat = getDriver().findElement(By.xpath("//td[2]/div")).getText();
		String assignment = getDriver().findElement(By.xpath("//td[3]/div")).getText();
		String agent = getDriver().findElement(By.xpath("//td[4]/div")).getText();
		TestUtils.assertSearchText("XPATH", "//b[contains(text(),'SN')]", "SN");
		TestUtils.assertSearchText("XPATH", "//th[2]/b", "Assignment Category");
		TestUtils.assertSearchText("XPATH", "//th[3]/b", "Assignment");
		TestUtils.assertSearchText("XPATH", "//th[4]/b", "Agent");
		TestUtils.assertSearchText("XPATH", "//th[5]", "Actions");
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Assignment type", assignmentCat);
		fields.put("Assignment", assignment);
		fields.put("SN", sn);
		fields.put("Agent", agent);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
	}

	@Test
	public void searchAgentAndCategories() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		//To confirm that user can filter by Agent and Assignment Categories
		TestUtils.testTitle("To confirm that user can filter by Agent("+agentEmail+") and Assignment Categories");
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow.float-right.ml-5.btn-sm")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		getDriver().findElement(By.xpath("//div[3]/input")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/input")).sendKeys(agentEmail);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[4]/div")));
		TestUtils.assertSearchText("XPATH", "//td[4]/div", agentEmail);

		//Assert details for TIME
		Assertion.assertAssignmenDetails("TIME");

		//Assert details for ZONE
		Assertion.assertAssignmenDetails("ZONE");

		//Assert details for REGION
		Assertion.assertAssignmenDetails("REGION");

		//Assert details for STATE
		Assertion.assertAssignmenDetails("STATE");

		//Close Modal
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
	}

	@Test
	public void assignEyeballerTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		//To confirm that user can perform "Assign Eyeballer"
		TestUtils.testTitle("To confirm that user can perform \"Assign Eyeballer\" ("+agentEmail+")");
		//Work Assignment Button
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow.float-right.ml-5.btn-sm")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//app-work-assignment-modal/button")).click();
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/form/div/ng-select/div/div/div[2]/input")));
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).click();
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).sendKeys(agentEmail);
		TestUtils.assertSearchText("XPATH", "//ng-dropdown-panel/div/div[2]/div[4]/span", agentEmail);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[4]/span")).click();
		Thread.sleep(500);

		//test all buttons for Select Assignment Category :
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//li[2]/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//li[3]/span")).click();
		//Check all Zones present
		checkAllZonePresent();

		Thread.sleep(500);
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//li[4]/span")).click();
		WebElement element=getDriver().findElement(By.id("state"));
		//Check all states
		TestUtils.checkAllStatesPresent(element);

		Thread.sleep(500);
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//li[2]/span")).click();
		Thread.sleep(500);

		//Check all Regions
		checkAllRegionsPresent();
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH WEST 1");
		getDriver().findElement(By.xpath("//div[5]/div/button")).click();
		//Close Modal
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();
		Thread.sleep(2000);

		//To confirm that user can't Assign "Eyeballer" without inputting agent and assignment categories or time
		TestUtils.testTitle("To confirm that user can't Assign \"Eyeballer\" without inputting agent and assignment categories or time");
		Thread.sleep(1000);
		//Work Assignment Button
		getDriver().findElement(By.xpath("//app-view-work-assignment-modal/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		getDriver().findElement(By.cssSelector("app-work-assignment-modal > button.btn.btn-yellow.float-right")).click();
		//Submit Button
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//small[contains(text(),'Please select Agent(s)')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Please select Agent(s)')]", "Please select Agent(s)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//small[contains(text(),'Please select either Time or category or a combination of both')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Please select either Time or category or a combination of both')]", "Please select either Time or category or a combination of both");


		//To confirm that user can Assign "Eyeballer" without time after selecting assignment categories
		TestUtils.testTitle("To confirm that user can Assign \"Eyeballer\" without time after selecting assignment categories");
		Thread.sleep(1000);
		//Input agent
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).click();
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).sendKeys(agentEmail);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[4]")).click();
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//ul[@id='dropdown-basic']/li[2]/span")).click();
		getDriver().findElement(By.id("region")).click();
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH WEST 1");
		getDriver().findElement(By.id("region")).click();
		//Submit Button
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'Work Assigned Successfully')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Work Assigned Successfully')]", "Work Assigned Successfully.");
		//Close Modal
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();

		//To confirm that user can assign "Eyeballer" without categories after selecting time
		TestUtils.testTitle("To confirm that user can assign \"Eyeballer\" without categories after selecting time");
		//Work Assignment Button
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow.float-right.ml-5.btn-sm")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		getDriver().findElement(By.cssSelector("app-work-assignment-modal > button.btn.btn-yellow.float-right")).click();
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).click();
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).sendKeys(agentEmail);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[4]")).click();
		//Input time
		getDriver().findElement(By.id("startTime")).sendKeys(startTime);
		getDriver().findElement(By.id("endTime")).sendKeys(endDate);
		//Submit
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'Work Assigned Successfully')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Work Assigned Successfully')]", "Work Assigned Successfully.");
		//Close Modal
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();

		//Assign work assignment with start time only
		TestUtils.testTitle("Assign work assignment with start time only("+startTime+")");
		//Work Assignment Button
		getDriver().findElement(By.xpath("//app-view-work-assignment-modal/button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("app-work-assignment-modal > button.btn.btn-yellow.float-right")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).click();
		getDriver().findElement(By.xpath("//form/div/ng-select/div/div/div[2]/input")).sendKeys(agentEmail);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[4]")).click();
		//Select category
		getDriver().findElement(By.id("button-basic")).click();
		getDriver().findElement(By.xpath("//ul[@id='dropdown-basic']/li[2]/span")).click();

		getDriver().findElement(By.id("region")).click();
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH WEST 1");
		getDriver().findElement(By.id("region")).click();
		getDriver().findElement(By.id("startTime")).sendKeys(startTime);
		//Submit
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//small[contains(text(),'Please select an end time')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Please select an end time')]", "Please select an end time");


		//Assign work assignment with end time only
		TestUtils.testTitle("Assign work assignment with end time only("+endDate+")");
		getDriver().findElement(By.id("startTime")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		getDriver().findElement(By.id("endTime")).sendKeys(endDate);
		Thread.sleep(1000);
		//Submit
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//small[contains(text(),'Please select a start time')]")));
		TestUtils.assertSearchText("XPATH", "//small[contains(text(),'Please select a start time')]", "Please select a start time");

		//Assign work assignment with start and end time
		TestUtils.testTitle("Assign work assignment with end time only("+endDate+")");
		getDriver().findElement(By.id("startTime")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		getDriver().findElement(By.id("endTime")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		getDriver().findElement(By.id("startTime")).sendKeys(startTime);
		getDriver().findElement(By.id("endTime")).sendKeys(endDate);
		Thread.sleep(1000);
		//Submit
		getDriver().findElement(By.cssSelector("div.create-btn > button.btn.btn-yellow")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'Work Assigned Successfully')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Work Assigned Successfully')]", "Work Assigned Successfully.");
		//Close Modal
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("button.close.pull-right > span")).click();

	}

	public void checkAllRegionsPresent(){
		//Check all regions
		TestUtils.testTitle("Check all regions");
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH WEST 1");
		String region=getDriver().findElement(By.id("region")).getText();
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("NORTH EAST 1");
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH EAST 1");
		new Select(getDriver().findElement(By.id("region"))).selectByVisibleText("SOUTH SOUTH 1");
		testInfo.get().info(region+" found");
	}

	public void checkAllZonePresent(){
		String[] zones = new String[] {"NCC", "APAPA", "IKOYI", "MATORI", "FCT", "JOS", "KANO", "ASABA", "ABA", "CALABAR", "BENIN", "OWERRI", "KADUNA", "PORT HARCOURT", "IBADAN", "SOKOTO", "ENUGU", "YOLA", "ABEOKUTA"};

		int count=zones.length;
		//Check all zones present
		TestUtils.testTitle("Check all zone items present");
		for (int i=0; i<count; i++){
			new Select(getDriver().findElement(By.id("zone"))).selectByVisibleText(zones[i]);
		}
		String region=getDriver().findElement(By.id("zone")).getText();
		testInfo.get().info(region+" found");
	}


	@Test
	public void unAssignAgentTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		//To confirm that user can unAssign Agent(" + agentEmail + ") "
		TestUtils.testTitle("To confirm that user can unAssign Agent(" + agentEmail + ") ");
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow.float-right.ml-5.btn-sm")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Eyeballing Assignment')]")));
		TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Assignment')]", "Eyeballing Assignment");
		getDriver().findElement(By.xpath("//div[3]/input")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//div[3]/input")).sendKeys(agentEmail);
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[4]/div")));
		TestUtils.assertSearchText("XPATH", "//td[4]/div", agentEmail);

		getDriver().findElement(By.xpath("//div/a/i")).click();
		getDriver().findElement(By.id("vd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'Operation was successful')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Operation was successful')]", "Operation was successful");
	}

}