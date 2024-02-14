package CACTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.Assertion;
import util.TestBase;
import util.TestUtils;


public class FMLicenceApprovalLicenceManagement extends TestBase {
	
	private StringBuffer verificationErrors = new StringBuffer();

	@Test(groups = { "Regression" })
	  public void navigateToFMLicenceApprovalPage() throws Exception{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Licence management");

		try {
			Thread.sleep(500);
			getDriver().findElement(By.name("1295License Management")).click();
		} catch (Exception e) {
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("a[name=\"1295License Management\"] > p")).click();
			Thread.sleep(500);
			getDriver().findElement(By.name("195580048FM License Approval")).click();
		}
	    Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "License Request Management");
	    Assert.assertEquals(getDriver().getTitle(), "SIMROP | License Request");
	   
	}
	
	@Test(groups = { "Regression" })
	  public void assertCardCountTest() throws Exception {
		Thread.sleep(2000);
		String PurchasedLicenseValString = getDriver().findElement(By.id("purchasedLicense")).getText();
		String LicenseInUseValString = getDriver().findElement(By.id("licenseInUse")).getText();
		String RemainingLicenseValString = getDriver().findElement(By.id("remainingLicense")).getText();
		
		TestUtils.testTitle("Test to assert the card count");

		
		int actualPurchasedLicenselVal = TestUtils.convertToInt(PurchasedLicenseValString);
		int actualLicenseInUseVal = TestUtils.convertToInt(LicenseInUseValString);
		int actualRemainingLicenseVal = TestUtils.convertToInt(RemainingLicenseValString);
				
		int expectedTotalPurchasedLicense = actualLicenseInUseVal + actualRemainingLicenseVal;
		
		try {
			Assert.assertEquals(expectedTotalPurchasedLicense,actualPurchasedLicenselVal);
		    testInfo.get().log(Status.INFO, "Purchased License ("+expectedTotalPurchasedLicense+") is equal to number of Licenses in Use ("+actualLicenseInUseVal+") + Available License ("+actualRemainingLicenseVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	}
	
	@Parameters("testEnv")
	@Test (groups = { "Regression" })
	  public void searchByTag(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("license");
		String tag = (String) envs.get("tag");

		TestUtils.testTitle("Filter by tag: " + tag);
		getDriver().findElement(By.id("search")).clear();
	    getDriver().findElement(By.id("search")).sendKeys(tag);
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    try {
	    	TestUtils.assertSearchText("XPATH", "//table[@id='licenseRequestTable']/tbody/tr/td[3]", tag);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	  }
	
	@Parameters("testEnv")
	@Test (groups = { "Regression" })
	  public void searchByDeviceID(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("license");
		String deviceID = (String) envs.get("deviceID");

		TestUtils.testTitle("Filter by device ID: " + deviceID);
;
		
		getDriver().findElement(By.id("search")).clear();
	    getDriver().findElement(By.id("search")).sendKeys(deviceID);
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    try {
	    	TestUtils.assertSearchText("XPATH", "//table[@id='licenseRequestTable']/tbody/tr/td[5]", deviceID);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	  }
	
	@Parameters("testEnv")
	@Test (groups = { "Regression" })
	  public void searchByEmail(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("license");
		String email = (String) envs.get("email");

		TestUtils.testTitle("Filter by email: " + email);

		
		TestUtils.scrollToElement("CSSSELECTOR", "h4.card-title");
		getDriver().findElement(By.id("search")).clear();
	    getDriver().findElement(By.id("search")).sendKeys(email);
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='licenseRequestTable']/tbody/tr/td[6]", email);
	    
	  }
	
	@Parameters("testEnv")
	@Test (groups = { "Regression" })
	  public void searchByMacAddress(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("license");
		String macAddress = (String) envs.get("macAddress");

		TestUtils.testTitle("Filter by Mac Address: " + macAddress);

		
		TestUtils.scrollToElement("CSSSELECTOR", "h4.card-title");
		getDriver().findElement(By.id("search")).clear();
	    getDriver().findElement(By.id("search")).sendKeys(macAddress);
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("XPATH", "//table[@id='licenseRequestTable']/tbody/tr/td[4]", macAddress);
	    
	  }
	
	@Test (groups = { "Regression" })
	  public void searchByStatusViewDetail() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Filter by Status: Approved");

		
		// Approved
		getDriver().findElement(By.id("search")).clear();
		if (!getDriver().findElement(By.id("status")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
	    getDriver().findElement(By.xpath("//span/span/input")).sendKeys("approved");
	    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");
	    
		TestUtils.testTitle("View approved detail");
		try {
			getDriver().findElement(By.xpath("//table[@id='licenseRequestTable']/tbody/tr/td[9]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
			TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "License Request Information");
			Assertion.viewDetailApprovedLicense();
			getDriver().findElement(By.id("statusBtn")).click();
			Thread.sleep(1000);
			} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

	    
	    // Rejected
		TestUtils.testTitle("Filter by Status: Rejected"); 
	    try {
	    	getDriver().findElement(By.id("search")).clear();
		    Thread.sleep(500);
			getDriver().findElement(By.xpath("//span/span/span")).click();
			Thread.sleep(500);
		    getDriver().findElement(By.xpath("//span/span/input")).sendKeys("rejected");
		    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		    getDriver().findElement(By.id("searchBtn")).click();
		    Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		    TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "REJECTED");
		    } catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

	    // Pending
		TestUtils.testTitle("Filter by Status: Pending");	   
	    try {
	    	 getDriver().findElement(By.id("search")).clear();
	 	    Thread.sleep(500);
	 		getDriver().findElement(By.xpath("//span/span/span")).click();
	 		Thread.sleep(500);
	 	    getDriver().findElement(By.xpath("//span/span/input")).sendKeys("pending");
	 	    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
	 	    getDriver().findElement(By.id("searchBtn")).click();
	 	    Thread.sleep(500);
	 		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	 	    TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
	 	    } catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

	}
	
	@Test 
	  public void approveRejectRequest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Approve
		TestUtils.testTitle("Filter by Status: Pending");
		if (!getDriver().findElement(By.id("status")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
	    getDriver().findElement(By.id("search")).clear();
	    Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
	    getDriver().findElement(By.xpath("//span/span/input")).sendKeys("pending");
	    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    
	    try {
	    	TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
	    	TestUtils.testTitle("Approve pending request");
			

			getDriver().findElement(By.xpath("//table[@id='licenseRequestTable']/tbody/tr/td[9]/div/a/i")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//a[contains(text(),'Approve')]")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='approvalDialog']/div/div/div/h4")));
			
			TestUtils.assertSearchText("XPATH", "//div[@id='approvalDialog']/div/div/div/h4", "License Approval");
			// Email
			getDriver().findElement(By.id("email")).clear();
			getDriver().findElement(By.id("approveBtn")).click();
			Thread.sleep(500);
			WebElement email = getDriver().findElement(By.name("email"));
			String validationMessage = email.getAttribute("validationMessage");
			Assert.assertEquals(validationMessage, "Please fill out this field.");
			testInfo.get().info("Empty email field: " + validationMessage);
			
			// close 
			getDriver().findElement(By.id("closeApprovalBtn")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		
		// Reject		
		TestUtils.testTitle("Filter by Status: Pending");
		if (!getDriver().findElement(By.id("status")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
	    getDriver().findElement(By.id("search")).clear();
	    Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
	    getDriver().findElement(By.xpath("//span/span/input")).sendKeys("pending");
	    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		
		 try {
		    	TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
				TestUtils.testTitle("Reject pending request");
		    	
				getDriver().findElement(By.xpath("//table[@id='licenseRequestTable']/tbody/tr/td[9]/div/a/i")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//table[@id='licenseRequestTable']/tbody/tr/td[9]/div/ul/li[2]/a")).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='bulkForm']/div/div/h4")));
				TestUtils.assertSearchText("XPATH", "//form[@id='bulkForm']/div/div/h4", "Reject License");
				// Reject reason
				getDriver().findElement(By.id("rejectionReason")).clear();
				getDriver().findElement(By.id("rejectBtn")).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reasonField")));
				TestUtils.assertSearchText("ID", "reasonField", "Reason is required");
				
				// close 
				getDriver().findElement(By.id("closeRejectionBtn")).click();
				Thread.sleep(1000);
		 } catch (Exception e) {
				TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
			}
		
	}
	
	@Test (enabled = false)
	  public void searchByDateTest() throws Exception {
		TestUtils.testTitle("Test for filter by date");

		TestUtils.scrollToElement("CSSSELECTOR", "h4.card-title");
		Thread.sleep(1000);
		getDriver().findElement(By.id("startDate")).clear();
	    getDriver().findElement(By.id("startDate")).sendKeys("2016-02-06");
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(1000);
	    if(TestUtils.isLoaderPresents()) {
	    	WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
	    }
	}
	
	@Test (groups = { "Regression" })
	@Parameters({"server", "downloadPath"})
	  public void uploadLicenseApprovalTest(String server, String downloadPath) throws Exception {
		TestUtils.testTitle("Test to upload licence approval");
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("licUploadBtn")));
		getDriver().findElement(By.id("licUploadBtn")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bulkDownloadUploadModal']/div/div/div/h4")));
	    TestUtils.assertSearchText("XPATH", "//div[@id='bulkDownloadUploadModal']/div/div/div/h4", "Bulk Approval Of License Request");
	    
	    // No file selected
		TestUtils.testTitle("Click upload without selecting a file");
		getDriver().findElement(By.id("uploadBulk")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Select a file to upload");
	    getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.cssSelector("input.form-control.inputFileVisible")).clear();

		TestUtils.uploadFile(By.id("usersExcelUpload"), "SIMROP_License_Request.xls");
		Thread.sleep(1000);
	    getDriver().findElement(By.id("uploadBulk")).click();
	    Thread.sleep(1000);
	    if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			}
	    Thread.sleep(1000);
	    getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
	    Thread.sleep(1000);
	    TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "License Request Management");
	    Assert.assertEquals(getDriver().getTitle(), "SIMROP | License Request");
	   
	  }
	
}
