package DealerTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class vtuOnboardingRequest extends TestBase {
	private static String AgentVTUNumber = TestUtils.generatePhoneNumber();

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "Requests");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "searchBtn");
	}
	
	@Test(groups = { "Regression" })
	public void navigateTovtuOnboardingRequestTest() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(500);
		getDriver().findElement(By.name("999999991VTU Management")).click();
		Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'VTU Onboarding Request')]")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | VIew Request");
        TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary > b", "VTU Onboarding Requests");
	}
	
	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("Requests_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='Requests']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Test(groups = { "Regression" })
	public void viewDetailsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.clickElement("XPATH", "//table[@id='Requests']/tbody/tr/td[7]/div/a/i");
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'View details')]")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
        String view = "View data transaction details ";
		Markup m = MarkupHelper.createLabel(view, ExtentColor.BLUE);
		testInfo.get().info(m);
		TestUtils.assertSearchText("ID", "myModalLabel", "VTU Onboarding Request Details");
		Assertion.assertOnboardingRequestDetailsDealer();
		TestUtils.scrollToElement("XPATH", "//div[2]/button"); 
		getDriver().findElement(By.xpath("//div[2]/button")).click();
		Thread.sleep(500);
      
    }
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void deactivateAndActivateAgentTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentEmail2 = (String) envs.get("agentEmail2");

		String sEmailFilter = "Filter by Agent Email: " + agentEmail2;
		Markup m = MarkupHelper.createLabel(sEmailFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(agentEmail2);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		
        // Deactivate Agent
		TestUtils.clickElement("XPATH", "//table[@id='Requests']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'Deactivate')]")).click();
        Thread.sleep(500);
        WebElement div_alert = getDriver().findElement(By.cssSelector("div.swal2-modal.swal2-show"));
		TestUtils.assertSearchText("XPATH", "//h2", "Notification");
		String expected_message = "Account was successfully deactivated";
		TestUtils.assertDivAlert(div_alert, expected_message);
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		
		// Activate Agent
		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(agentEmail2);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.clickElement("XPATH", "//table[@id='Requests']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Activate')]")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-modal.swal2-show")));
        TestUtils.assertSearchText("XPATH", "//h2", "Notification");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Account was activated successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(500);
		
	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByEmailTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentEmail = (String) envs.get("agentEmail");

		String sEmailFilter = "Filter by Agent Email: " + agentEmail;
		Markup m = MarkupHelper.createLabel(sEmailFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		scrollUp();
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(agentEmail);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='Requests']/tbody/tr/td[3]", agentEmail);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByAgentVtuNumberTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String vtuNumber = (String) envs.get("vtuNumber");

		String sVtuFilter = "Filter by Agent VTU: " + vtuNumber;
		Markup m = MarkupHelper.createLabel(sVtuFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchParam")).clear();
		getDriver().findElement(By.name("searchParam")).sendKeys(vtuNumber);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='Requests']/tbody/tr/td[4]", vtuNumber);
		
	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByAgentNameTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentName = (String) envs.get("agentName");
		
		String sNameFilter = "Filter by Agent Name: " + agentName;
		Markup m = MarkupHelper.createLabel(sNameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchParam")).clear();
		if (!getDriver().findElement(By.id("agentNameSearch")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.id("agentNameSearch")).clear();
		getDriver().findElement(By.id("agentNameSearch")).sendKeys(agentName);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		
		String name = getDriver().findElement(By.xpath("//table[@id='Requests']/tbody/tr/td[2]")).getText();
		if (name.contains(agentName)) {
            testInfo.get().log(Status.INFO, name + " found");
		} else {
			 testInfo.get().log(Status.INFO, "not found");
		}
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");
		
		String sNameFilter = "Filter by Date Range: " + startDate+ " and " +endDate;
		Markup m = MarkupHelper.createLabel(sNameFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchParam")).clear();
		if (!getDriver().findElement(By.id("agentNameSearch")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.id("agentNameSearch")).clear();
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
        getDriver().findElement(By.name("startDate")).clear();
        getDriver().findElement(By.name("startDate")).sendKeys(startDate); 
        getDriver().findElement(By.name("endDate")).clear();
        getDriver().findElement(By.name("endDate")).sendKeys(endDate); 
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='Requests']/tbody/tr/td[5]")).getText();
		table_Date = table_Date.substring(0, 10);
		testInfo.get().info("Date returned: " + table_Date);
		TestUtils.checkDateyYMDBoundary(startDate, endDate, table_Date);
		
    }

	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().findElement(By.name("searchParam")).clear();
		if (!getDriver().findElement(By.id("agentNameSearch")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.id("agentNameSearch")).clear();
		
		// Approved status
		String approved = "Search by approved status";
		Markup a = MarkupHelper.createLabel(approved, ExtentColor.BLUE);
		testInfo.get().info(a);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Approved");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-approved", "APPROVED");
		
		// Pending status
		String pending = "Search by pending status";
		Markup p = MarkupHelper.createLabel(pending, ExtentColor.BLUE);
		testInfo.get().info(p);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("PENDING");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-pending", "PENDING");
		
		// Rejected status
		String reject = "Search by Rejected status";
		Markup w = MarkupHelper.createLabel(reject, ExtentColor.BLUE);
		testInfo.get().info(w);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("REJECTED");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("CSSSELECTOR", "span.badge.badge-rejected", "REJECTED");
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='Requests']/tbody/tr/td", "No data available in table");
			Thread.sleep(500);
		}
	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void onBoardAgentTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuOnboarding");

		String agentEmail = (String) envs.get("agentEmail");
		
        getDriver().findElement(By.id("onboardbtn")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "(//h4[@id='myModalLabel'])[2]", "On-Board Agent");
        Thread.sleep(500);
        
		// Check for close button
        String cFilter = "Check for close button after filling form";
		Markup w = MarkupHelper.createLabel(cFilter, ExtentColor.BLUE);
		testInfo.get().info(w);
		Thread.sleep(500);
        getDriver().findElement(By.xpath("//form/div/div/span/span/span")).click();
        Thread.sleep(500);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(agentEmail);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		Assertion.assertVtuOnboardAgentDetailsDealer();
        getDriver().findElement(By.id("agentvtu")).clear();
        getDriver().findElement(By.id("agentvtu")).sendKeys(AgentVTUNumber);
        Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/div/div/button")).click();
		Thread.sleep(500);

		// Check for empty fields
		getDriver().findElement(By.id("onboardbtn")).click();
		Thread.sleep(500);
		String submitFilter = "Try to submit without filling the On-Board Agent form";
		Markup m = MarkupHelper.createLabel(submitFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("saveAgent")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Enter a valid vtu number, must not be less than 11 digits')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Enter a valid vtu number, must not be less than 11 digits')]", "Enter a valid vtu number, must not be less than 11 digits");
		TestUtils.clickElement("CSSSELECTOR", "button.close > i.material-icons");
       // getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);

		// fill requisition form
		String sFilter = "Fill On-Board Agent form";
		Markup e = MarkupHelper.createLabel(sFilter, ExtentColor.BLUE);
		testInfo.get().info(e);
		Thread.sleep(500);
        getDriver().findElement(By.xpath("//form/div/div/span/span/span")).click();
        Thread.sleep(500);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(agentEmail);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		Assertion.assertVtuOnboardAgentDetailsDealer();
        getDriver().findElement(By.id("agentvtu")).clear();
        getDriver().findElement(By.id("agentvtu")).sendKeys(AgentVTUNumber);
        Thread.sleep(500);
        getDriver().findElement(By.id("saveAgent")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Agent VTU request was successfully created.')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Agent VTU request was successfully created.')]", "Agent VTU request was successfully created.");
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | VIew Request");

    }
	
	@Parameters({ "downloadPath", "server" })
	@Test(groups = { "Regression" })
	  public void bulkUploadTest(String server, String downloadPath) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String pic;
		String ima;
		if (server.equals("remote-browserStack")) {
			ima = downloadPath + "image2.jpg";
			pic = downloadPath + "Bulk_Onboard_Creation.xls";

		} else if (server.equals(remoteJenkins)) {
			pic = downloadPath + "Bulk_Onboard_Creation.xls";
			ima = downloadPath + "image2.jpg";
		} else {
			pic = System.getProperty("user.dir") + "\\files\\Bulk_Onboard_Creation.xls";
			ima = System.getProperty("user.dir") + "\\files\\image2.jpg";
		}
		getDriver().findElement(By.id("bulkCreateLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div/div[2]")));
		TestUtils.assertSearchText("XPATH", "//div[@id='bulkCreation']/div/div/div/h4", "Bulk Agent Onboarding");
		
		// Check for empty upload
		String sFilter = "Try uploading without inputing any file";
		Markup e = MarkupHelper.createLabel(sFilter, ExtentColor.BLUE);
		testInfo.get().info(e);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[2]/div/div/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-modal.swal2-show")));
		TestUtils.assertSearchText("XPATH", "//h2", "Error");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "no file selected");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		
		// Download template
		getDriver().findElement(By.xpath("//form/div/div[2]/div/div/a")).click();
		Thread.sleep(500);
		
		// Invalid file format
		String iFilter = "Invalid file format";
		Markup i = MarkupHelper.createLabel(iFilter, ExtentColor.BLUE);
		testInfo.get().info(i);
		Thread.sleep(500);
		WebElement input = getDriver().findElement(By.id("upload"));
		input.sendKeys(ima);
		getDriver().findElement(By.xpath("//div[2]/div/div/button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-modal.swal2-show")));
		TestUtils.assertSearchText("XPATH", "//h2", "Error");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Please confirm the file type. Supported file extension are .xls and .xlsx");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		
		// Upload file
		String dFilter = "Upload file";
		Markup d = MarkupHelper.createLabel(dFilter, ExtentColor.BLUE);
		testInfo.get().info(d);
		Thread.sleep(500);
		WebElement input1 = getDriver().findElement(By.id("upload"));
		input1.sendKeys(pic);
		testInfo.get().info("file successfully uploaded");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[2]/div/div/button")).click();
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | VIew Request");
		
		// Close button
		getDriver().findElement(By.id("closeModal")).click();
		Thread.sleep(500);  
	  }
	
}