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
import util.TestBase;
import util.TestUtils;
import util.Assertion;

public class TaggingRequestDeviceManagement extends TestBase {
    private String newSerialNumber = TestUtils.generateSerialNumber();
    private String newSerialNumber2 = TestUtils.generateSerialNumber();
    private StringBuffer verificationErrors = new StringBuffer();

    public void scrollDown() throws InterruptedException {
        TestUtils.scrollToElement("ID", "taggingRequest");
    }

    public void scrollUp() throws InterruptedException {
        TestUtils.scrollToElement("ID", "global_filter");
    }

    @Parameters("testEnv")
    @Test(groups = {"Regression"})
    public void navigateToTaggingRequestDeviceManagement(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Navigate to Tagging Request");

        if (testEnv.equalsIgnoreCase("stagingData")) {
            TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1590Device Management\"] > p");
            getDriver().findElement(By.cssSelector("a[name=\"1590Device Management\"] > p")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.name("1593Tagging Request")).click();
            Thread.sleep(1000);
        } else {
            TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"38683138Device Management\"]");
            getDriver().findElement(By.cssSelector("a[name=\"38683138Device Management\"]")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.name("1593Tagging Request")).click();
            Thread.sleep(1000);
        }

        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Device Tagging Requests");
    }

    @Test(groups = {"Regression"})
    public void viewTagHistoryTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Approved");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));

        // View history modal
        TestUtils.testTitle("View Tag History");
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
        
        
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}
        
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.linkText("View Tag History")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.assertSearchText("XPATH", "//div[@id='viewTagHistoriesModal']/div/div/div/h4",
                "View Tag History");

        // Change page size
        new Select(getDriver().findElement(By.name("tagHistoryTable_length"))).selectByVisibleText("50");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.testTitle("Change page size to: 50");
        double rowCount = getDriver().findElements(By.xpath("//table[@id='tagHistoryTable']/tbody/tr")).size();
        if (TestUtils.isElementPresent("XPATH", "//td")) {
            testInfo.get().info("Total number of records returned: " + rowCount);

            // Filter by kit tag
            double number = rowCount / 2;
            int num = (int) Math.ceil(number);
            String kitTag = getDriver()
                    .findElement(By.xpath("//table[@id='tagHistoryTable']/tbody/tr[" + num + "]/td[4]")).getText();
            String createDate = getDriver()
                    .findElement(By.xpath("//table[@id='tagHistoryTable']/tbody/tr[" + num + "]/td[2]")).getText();
            TestUtils.testTitle("Filter by kit tag: " + kitTag);
            getDriver().findElement(By.id("mSearchKitTag")).clear();
            getDriver().findElement(By.id("mSearchKitTag")).sendKeys(kitTag);
            getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
            Thread.sleep(1000);
            TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr/td[4]", kitTag);
            getDriver().findElement(By.id("mSearchKitTag")).clear();
            getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));

            // Filter by create start date
            TestUtils.testTitle("Filter by Create date: " + createDate);
            getDriver().findElement(By.id("mStartDate")).clear();
            getDriver().findElement(By.id("mStartDate")).sendKeys(createDate);
            getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[2]/div/div[4]/div/button")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
            Thread.sleep(1000);
            TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr/td[2]", createDate);

        } else {
            TestUtils.assertSearchText("XPATH", "//table[@id='tagHistoryTable']/tbody/tr/td", "No data available in table");
            testInfo.get().info("Table is empty.");
        }

        TestUtils.scrollToElement("XPATH", "//div[@id='viewTagHistoriesModal']/div/div/div[3]/button");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[3]/button")));
        // Click close button
        getDriver().findElement(By.xpath("//div[@id='viewTagHistoriesModal']/div/div/div[3]/button")).click();
        Thread.sleep(1000);

    }

    @Test(groups = {"Regression"})
    public void viewDetailsTest() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        // View detail modal
//        scrollDown();
    	   getDriver().findElement(By.id("searchBtn")).click();
           Thread.sleep(1000);
           wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                   "Processing..."));
    	
        String tag = getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr[3]/td[3]")).getText();
        
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
        
        
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}
        
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.linkText("View Details")).click();
        Thread.sleep(3000);
        TestUtils.testTitle("View Details of Kit Tag: " + tag);
        TestUtils.assertSearchText("CSSSELECTOR", "div.modal-dialog.modal-lg > div.modal-content > div.modal-header > #myModalLabel",
                "Tagging Request Details");
        Assertion.assertViewDetailsModalTaggingRequestAdmin();
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//div[@id='viewDeviceAssignmentModal']/div/div/form/div[2]/button");

        // Click close button
        getDriver().findElement(By.xpath("//div[@id='viewDeviceAssignmentModal']/div/div/form/div[2]/button")).click();
        Thread.sleep(1000);

    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void searchByDeviceIDTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String deviceID = (String) envs.get("deviceID");

        TestUtils.testTitle("Filter by device ID: " + deviceID);
        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
            getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
        }
        getDriver().findElement(By.id("global_filter")).clear();
        getDriver().findElement(By.id("global_filter")).sendKeys(deviceID);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr/td[2]", deviceID);

    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void searchByKitTagTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String tag = (String) envs.get("tag");

        TestUtils.testTitle("Filter by kit tag: " + tag);
        getDriver().findElement(By.id("global_filter")).clear();
        getDriver().findElement(By.id("global_filter")).sendKeys(tag);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr/td[3]", tag);

    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void searchByDealerTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String dealerName = (String) envs.get("dealerName");

        TestUtils.testTitle("Filter by dealer name: " + dealerName);
        getDriver().findElement(By.id("global_filter")).clear();
        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        getDriver().findElement(By.xpath("//span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr/td[5]", dealerName);

    }

    @Test(groups = {"Regression"})
    public void searchByStatusTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
            getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
        }
        Thread.sleep(1000);

        // Approved status
        TestUtils.testTitle("Filter by Status: Approved and list of action button options");
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Approved");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        
        TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
        
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();

		}
      //  TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr[2]/td/ul/li[3]/span[2]/span", "APPROVED");
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(1000); 
        TestUtils.assertSearchText("LINKTEXT", "Re-assign", "Re-assign");
        TestUtils.assertSearchText("LINKTEXT", "View Tag History", "View Tag History");
        TestUtils.assertSearchText("LINKTEXT", "View Details", "View Details");

        // Pending Status
        TestUtils.testTitle("Filter by Status: Pending and list of action button options");
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Pending");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        try {
       
        	 TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
             if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
     			
     			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
     		}
           //  TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr[2]/td/ul/li[3]/span[2]/span", "PENDING");
             getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
             Thread.sleep(1000); 
          //   TestUtils.assertSearchText("LINKTEXT", "View Tag History", "View Tag History");
             TestUtils.assertSearchText("LINKTEXT", "View Details", "View Details");

        } catch (Exception e) {
            TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
        }

        // Rejected status
        TestUtils.testTitle("Filter by Status: Rejected and list of action button options");
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Rejected");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        try {
        	TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
            
             if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
     			
     			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
     		}
           //  TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr[2]/td/ul/li[3]/span[2]/span", "DISAPPROVED");
             getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
             Thread.sleep(1000); 
             TestUtils.assertSearchText("LINKTEXT", "Re-assign", "Re-assign");
             TestUtils.assertSearchText("LINKTEXT", "View Tag History", "View Tag History");
             TestUtils.assertSearchText("LINKTEXT", "View Details", "View Details");
        } catch (Exception e) {
            TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
        }
        
       
        // Unassign status
        TestUtils.testTitle("Filter by Status: Unassigned and list of action button options");
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unassigned");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
        
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
		
		}
      //  TestUtils.assertSearchText("XPATH", "//table[@id='taggingRequest']/tbody/tr[2]/td/ul/li[3]/span[2]/span", "UNASSIGNED");
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(1000); 
        TestUtils.assertSearchText("LINKTEXT", "Assign", "Assign");
        TestUtils.assertSearchText("LINKTEXT", "View Details", "View Details");
    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void assignNewTaggingRequestTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String unassignDeviceID = (String) envs.get("unassignDeviceID");
        String dealerName = (String) envs.get("dealerName");
        String dealerState = (String) envs.get("dealerState");
        String dealerLga = (String) envs.get("dealerLga");
        String serialNumber = (String) envs.get("serialNumber");
        String dealerCode = (String) envs.get("dealerCode");
        String stateCode = (String) envs.get("stateCode");
        String lgaCode = (String) envs.get("lgaCode");
        String device = (String) envs.get("deviceType");
        
        getDriver().navigate().refresh();

        getDriver().findElement(By.id("global_filter")).clear();
        getDriver().findElement(By.id("global_filter")).sendKeys(unassignDeviceID);
        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Unassigned");
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        String fullDeviceID = getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td[2]"))
                .getText();
        TestUtils.testTitle("Assigned new device tagging request: " + fullDeviceID);
        
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//table[@id='taggingRequest']/tbody/tr/td");
        
        // Assign modal form
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}
        
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.linkText("Assign")).click();
        Thread.sleep(1000);

        
//        getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td[9]/div/a/i")).click();
//        Thread.sleep(1000);
//        getDriver().findElement(By.xpath("//a[contains(text(),'Assign')]")).click();
//        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kitDeviceId")));
        TestUtils.assertSearchText("ID", "kitDeviceId", fullDeviceID);

        TestUtils.testTitle("Click ASSIGN button without supplying required fields.");
        getDriver().findElement(By.id("taggingbtn")).click();
        Thread.sleep(1000);
        TestUtils.assertSearchText("ID", "deviceType-error", "Device Type is required");
        TestUtils.assertSearchText("ID", "dealer-error", "Dealer is required");
        TestUtils.assertSearchText("ID", "dealerStates-error", "Dealer state is required");
        TestUtils.assertSearchText("ID", "dealerLga-error", "Dealer LGA is required");
        TestUtils.assertSearchText("ID", "serialNumber-error", "Serial number is required}");

        TestUtils.testTitle("Click ASSIGN button for already existing kit tag.");
        fillTagging(device, dealerName, dealerState, dealerLga, serialNumber);
        String deviceType = getDriver().findElement(By.xpath("//div[2]/span")).getText();

        // Click to assign
        TestUtils.scrollToElement("ID", "serialNumber");
        TestUtils.clickElement("ID", "taggingbtn");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
        String requestTag = deviceType + "-" + stateCode + "-" + lgaCode +  "-" + dealerCode + "-" + serialNumber;
        String AltKit = deviceType + "-" + serialNumber;
        
        TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Device tag is " + requestTag);

        // Click Cancel button
        getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
        Thread.sleep(1000);

        // Click Proceed button
        getDriver().findElement(By.id("taggingbtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
        getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'This Alt kit tag already exist')]")));
        TestUtils.assertSearchText("XPATH", "//span[contains(text(),'This Alt kit tag already exist')]", "This Alt kit tag already exist ("+AltKit+")");
        getDriver().findElement(By.xpath("//div[8]/button/i")).click();

        // Click clear button
        getDriver().findElement(By.id("re_assign_clear_button")).click();
        Thread.sleep(1000);

        // Close modal
        getDriver().findElement(By.id("closeFormModalButton")).click();
        Thread.sleep(1000);
    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void reassignApprovedRequestTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String dealerName = (String) envs.get("dealerName");
        String dealerState = (String) envs.get("dealerState");
        String dealerLga = (String) envs.get("dealerLga");
        String dealerCode = (String) envs.get("dealerCode");
        String stateCode = (String) envs.get("stateCode");
        String lgaCode = (String) envs.get("lgaCode");
        String device = (String) envs.get("deviceType");
        String reassignDeviceID = (String) envs.get("reassignApprovedDeviceID");

        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }
        if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
            getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
        }
        getDriver().findElement(By.id("global_filter")).clear();
        getDriver().findElement(By.id("global_filter")).sendKeys(reassignDeviceID);
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.testTitle("Re-assign approved tagging request: " + reassignDeviceID);
        
        // Assign modal form
        if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
			getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
			TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
		}
        
        	getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        	Thread.sleep(1000);
        	getDriver().findElement(By.linkText("Re-assign")).click();
      
        
//        getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td[9]/div/a/i")).click();
//        Thread.sleep(1000);
//        getDriver().findElement(By.xpath("//a[contains(text(),'Re-assign')]")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kitDeviceId")));
        TestUtils.assertSearchText("ID", "kitDeviceId", reassignDeviceID);
        Thread.sleep(1000);
        fillTagging(device, dealerName, dealerState, dealerLga, newSerialNumber);
        String deviceType2 = getDriver().findElement(By.xpath("//div[2]/span")).getText();

        // Click to assign
        TestUtils.scrollToElement("ID", "serialNumber");
        TestUtils.clickElement("ID", "taggingbtn");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
        String requestTag2 = deviceType2 + "-" + stateCode + "-" + lgaCode + "-" + dealerCode + "-" + newSerialNumber;
        TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Device tag is " + requestTag2);

        getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
        Thread.sleep(800);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
        String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
        String requiredText = expectedText.substring(12);
        String value = "Successfully made a re-tagging request for " + reassignDeviceID + " to " + requestTag2;

        try {
            Assert.assertEquals(requiredText, value);
            testInfo.get().log(Status.INFO, value + " found");
            Thread.sleep(1000);
            getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
            Thread.sleep(1000);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void reassignDisapprovedRequestTest(String testEnv)
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

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String dealerName = (String) envs.get("dealerName");
        String dealerState = (String) envs.get("dealerState");
        String dealerLga = (String) envs.get("dealerLga");
//		String serialNumber = (String) envs.get("serialNumber");
        String dealerCode = (String) envs.get("dealerCode");
        String stateCode = (String) envs.get("stateCode");
        String lgaCode = (String) envs.get("lgaCode");
        String device = (String) envs.get("deviceTypeDroid");
        String reassignDeviceID = (String) envs.get("reassignDisapprovedDeviceID");

        getDriver().findElement(By.id("global_filter")).clear();
        getDriver().findElement(By.id("global_filter")).sendKeys(reassignDeviceID);
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        TestUtils.testTitle("Re-assigned disapproved device tagging request: " + reassignDeviceID);

        // Assign modal form
      if (getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).isDisplayed()) {
			
		getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td")).click();
		TestUtils.scrollToElement("XPATH", "//span[2]/div/a/i");
			
      }
        
        getDriver().findElement(By.xpath("//span[2]/div/a/i")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.linkText("Re-assign")).click();
        
       
//        getDriver().findElement(By.xpath("//table[@id='taggingRequest']/tbody/tr/td[9]/div/a/i")).click();
//        Thread.sleep(1000);
//        getDriver().findElement(By.xpath("//a[contains(text(),'Re-assign')]")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kitDeviceId")));
        TestUtils.assertSearchText("ID", "kitDeviceId", reassignDeviceID);

        TestUtils.testTitle("RE-ASSIGN disapproved kit tag.");
        Thread.sleep(1000);
        fillTagging(device, dealerName, dealerState, dealerLga, newSerialNumber2);
        String deviceType2 = getDriver().findElement(By.xpath("//div[2]/span")).getText();

        // Click to assign
        TestUtils.scrollToElement("ID", "serialNumber");
        TestUtils.clickElement("ID", "taggingbtn");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
        String requestTag2 = deviceType2 + "-" + stateCode + "-" + lgaCode + "-" + dealerCode + "-" + newSerialNumber2;
        TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Device tag is " + requestTag2);

        getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
        Thread.sleep(800);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
        String expectedText = getDriver().findElement(By.cssSelector("div.alert.alert-success")).getText();
        String requiredText = expectedText.substring(12);
        String value = "Successfully made a re-tagging request for " + reassignDeviceID + " to " + requestTag2;

        try {
            Assert.assertEquals(requiredText, value);
            testInfo.get().log(Status.INFO, value + " found");
            Thread.sleep(1000);
            getDriver().findElement(By.cssSelector("span > i.material-icons")).click();
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }

    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void searchByDateTest(String testEnv) throws Exception {
        File path = null;
        File classpathRoot = new File(System.getProperty("user.dir"));
        if (testEnv.equalsIgnoreCase("StagingData")) {
            path = new File(classpathRoot, "stagingData/data.conf.json");
        } else {
            path = new File(classpathRoot, "prodData/data.conf.json");
        }
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader(path));

        JSONObject envs = (JSONObject) config.get("taggingRequest");

        String startDate = (String) envs.get("startDate");
        String endDate = (String) envs.get("endDate");

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
            getDriver().findElement(By.id("toggle")).click();
        }

            Thread.sleep(1000);
            TestUtils.testTitle("Filter by Start date: " + startDate);
            getDriver().findElement(By.id("startDate")).clear();
            getDriver().findElement(By.id("startDate")).sendKeys(startDate);
            getDriver().findElement(By.id("searchBtn")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[4]")));
            String sDate = getDriver().findElement(By.xpath("//td[4]")).getText();
            TestUtils.convertToNormalDate(sDate);

            TestUtils.testTitle("Filter by End date: " + endDate);
            getDriver().findElement(By.id("startDate")).clear();
            getDriver().findElement(By.id("endDate")).clear();
            getDriver().findElement(By.id("endDate")).sendKeys(endDate);
            getDriver().findElement(By.id("searchBtn")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[4]")));
            String eDate = getDriver().findElement(By.xpath("//td[4]")).getText();
            TestUtils.convertToNormalDate(eDate);

            TestUtils.testTitle("Filter by date range: " + startDate + " and " + endDate);
            getDriver().findElement(By.id("startDate")).clear();
            getDriver().findElement(By.id("startDate")).sendKeys(startDate);
            getDriver().findElement(By.id("endDate")).clear();
            getDriver().findElement(By.id("endDate")).sendKeys(endDate);
            getDriver().findElement(By.id("searchBtn")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[4]")));
            String table_Date = getDriver().findElement(By.xpath("//td[4]")).getText();
            String newDate = TestUtils.convertToNormalDate(table_Date);
            System.out.println(newDate);
            TestUtils.checkDateBoundary(startDate, endDate, newDate);
    }

    @Parameters({"testEnv"})
    @Test(groups = {"Regression"})
    public void fillTagging(String DeviceType, String dealerName, String dealerState, String dealerLga, String sNumber)
            throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        // Fill device type
        getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(DeviceType);
        Thread.sleep(1000);
        try {
            getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        } catch (Exception e) {
            getDriver().findElement(By.cssSelector("input.select2-search__field")).clear();
            getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(DeviceType);
            Thread.sleep(1000);
            getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        }

        // Fill dealer name
        getDriver().findElement(By.xpath("//div[5]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);
        WebElement dealer = getDriver().findElement(By.id("dealerStates"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(dealer, "value"));
        // Fill dealer status
        getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerState);
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);

        // Fill dealer lga
        getDriver().findElement(By.xpath("//div[7]/div/span/span/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerLga);
        getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
        Thread.sleep(1000);

        TestUtils.scrollToElement("ID", "serialNumber");

        getDriver().findElement(By.id("serialNumber")).clear();
        getDriver().findElement(By.id("serialNumber")).sendKeys(sNumber);
    }

    @Test (groups = { "Regression" })
    public void selectVisibleColumns() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 50);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Select Visible Columns')]")));
        getDriver().findElement(By.linkText("Select Visible Columns")).click();
        Thread.sleep(1000);
        // Device ID Column
        TestUtils.testTitle("Remove Device ID Column");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[6]/a[2]")));
       // getDriver().findElement(By.xpath("//div[6]/a[2]")).click();
        TestUtils.clickElement("XPATH", "//div[6]/a[2]");
        if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Device ID")) {
            TestUtils.assertSearchText("XPATH", "//th[2]", "Device ID");

        }else {
            testInfo.get().info("Device ID column removed");

        }
        TestUtils.testTitle("Add Device ID Column");
        getDriver().findElement(By.xpath("//div[6]/a[2]")).click();
        TestUtils.assertSearchText("XPATH", "//div[6]/a[2]", "Device ID");
        Thread.sleep(1000);

        //Kit Tag Column
        TestUtils.testTitle("Remove Kit Tag Column");
        getDriver().findElement(By.xpath("//a[3]/span")).click();
        if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Kit Tag")) {
            TestUtils.assertSearchText("XPATH", "//th[3]", "Kit Tag");

        }else {
            testInfo.get().info("Kit Tag column removed");

        }
        TestUtils.testTitle("Add Kit Tag Column");
        getDriver().findElement(By.xpath("//a[3]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[3]", "Kit Tag");
        Thread.sleep(1000);

        // Date Requested Column
        TestUtils.testTitle("Remove Date Requested Column");
        getDriver().findElement(By.xpath("//a[4]/span")).click();
        if (getDriver().findElement(By.xpath("//th[4]")).getText().contains("Date Requested")) {
            TestUtils.assertSearchText("XPATH", "//th[4]", "Date Requested");

        }else {
            testInfo.get().info("Date Requested column removed");

        }
        TestUtils.testTitle("Add Date Requested Column");
        getDriver().findElement(By.xpath("//a[4]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[4]", "Date Requested");
        Thread.sleep(1000);

        // Dealer Column
        TestUtils.testTitle("Remove Dealer Column");
        getDriver().findElement(By.xpath("//a[5]/span")).click();
        if (getDriver().findElement(By.xpath("//th[5]")).getText().contains("Dealer")) {
            TestUtils.assertSearchText("XPATH", "//th[5]", "Dealer");

        }else {
            testInfo.get().info("Dealer column removed");

        }
        TestUtils.testTitle("Add Dealer Column");
        getDriver().findElement(By.xpath("//a[5]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[5]", "Dealer");
        Thread.sleep(1000);

        // State of Deployment Column
        TestUtils.testTitle("Remove State of Deployment Column");
        getDriver().findElement(By.xpath("//a[6]/span")).click();
        if (getDriver().findElement(By.xpath("//th[6]")).getText().contains("State of Deployment")) {
            TestUtils.assertSearchText("XPATH", "//th[6]", "State of Deployment");

        }else {
            testInfo.get().info("State of Deployment column removed");

        }
        TestUtils.testTitle("Add State of Deployment Column");
        getDriver().findElement(By.xpath("//a[6]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[6]", "State of Deployment");
        Thread.sleep(1000);

        // LGA of Deployment Column
        TestUtils.testTitle("Remove LGA of Deployment Column");
        getDriver().findElement(By.xpath("//a[7]")).click();
        try {
            TestUtils.assertSearchText("XPATH", "//a[7]", "LGA of Deployment");

        } catch (Exception e) {
            testInfo.get().info("LGA of Deployment column removed");
        }
        TestUtils.testTitle("Add LGA of Deployment Column");
        getDriver().findElement(By.xpath("//a[7]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[7]", "LGA of Deployment");

        // Status Column
        TestUtils.testTitle("Remove Status Column");
        getDriver().findElement(By.xpath("//a[8]/span")).click();
        try {
            TestUtils.assertSearchText("XPATH", "//a[8]/span", "Status");

        } catch (Exception e) {
            testInfo.get().info("Status column removed");
        }
        TestUtils.testTitle("Add Status Column");
        getDriver().findElement(By.xpath("//a[8]/span")).click();
        TestUtils.assertSearchText("XPATH", "//a[8]/span", "Status");

        // Actions Column
        TestUtils.testTitle("Remove Actions Column");
        getDriver().findElement(By.xpath("//a[9]/span")).click();
        try {
            TestUtils.assertSearchText("XPATH", "//a[9]/span", "Actions");

        } catch (Exception e) {
            testInfo.get().info("Actions column removed");
        }
        TestUtils.testTitle("Add Actions Column");
        getDriver().findElement(By.xpath("//a[9]/span")).click();
        TestUtils.assertSearchText("XPATH", "//th[9]", "Actions");
    }

    @Test(groups = {"Regression"})
    @Parameters({"server", "downloadPath"})
    public void bulkTaggingRequestTest(String server, String downloadPath) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Create Bulk Tagging Request");

        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("a.btn.btn-link.btn-linkedin")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.modal-dialog.modal-600 > div.modal-content > div.modal-header > h4.modal-title.font-weight-bold.text-secondary")));
        TestUtils.assertSearchText("CSSSELECTOR", "div.modal-dialog.modal-600 > div.modal-content > div.modal-header > h4.modal-title.font-weight-bold.text-secondary", "Bulk Tagging or Re-Tagging");
        Thread.sleep(1000);

        // Upload empty file
        TestUtils.testTitle("Click UPLOAD button without selecting a file (empty file path)");
        getDriver().findElement(By.id("uploadBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Invalid file Provided. Supported file extension is')]")));
        TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Invalid file Provided. Supported file extension is')]", "Invalid file Provided. Supported file extension is .xls");
        Thread.sleep(1000);

        // download tagging request template
        getDriver().findElement(By.cssSelector("div.modal-footer > div.col-sm-12 > a.btn.btn-link.btn-linkedin")).click();
        Thread.sleep(1000);

        String file = "tagging_template.xls";

        // Select a valid file format
        TestUtils.testTitle("Select a valid file format and upload. eg xls " + file);
        TestUtils.uploadFile(By.id("taggingFile"), file);
        getDriver().findElement(By.id("uploadBtn")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your request has been processed.')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Your request has been processed.')]", "Your request has been processed.");
        Thread.sleep(1000);
        getDriver().findElement(By.cssSelector("div.modal-header > div > button.close")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
                "Processing..."));
        Thread.sleep(1000);

    }

}
