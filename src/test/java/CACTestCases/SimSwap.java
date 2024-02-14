package CACTestCases;

import com.aventstack.extentreports.Status;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import static util.Assertion.validateDemographicDetailField;
import static util.Assertion.validateFrequentlyDialedNumberField;
import static util.TestUtils.yyyymmddToDate;

public class SimSwap extends TestBase {

    private String msisdn;
    private String msisdn2;
    private String userId;
    private String startDate;
    private String endDate;
    private String simSerial;

    private StringBuffer verificationErrors = new StringBuffer();

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
        JSONObject envs = (JSONObject) config.get("simSwap");

        msisdn = (String) envs.get("msisdn");
        userId = (String) envs.get("userId");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
        simSerial = (String) envs.get("simSerial");
        msisdn2 = (String) envs.get("msisdn2");
    }


    public void statusCheck(String status) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Search by Swap Status("+ status +")");

        Select dropdown = new Select(getDriver().findElement(By.xpath("//select"))); 
        dropdown.selectByVisibleText(status);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(1000);
        if(!status.equalsIgnoreCase("Select Swap Status")){
            TestUtils.assertSearchText("XPATH", "//td[3]", status.toUpperCase());
       // 	TestUtils.assertSearchText("XPATH", "//td[contains(text(),'"+status+"')]", status.toUpperCase()); (.//*[normalize-space(text()) and normalize-space(.)='search'])[1]/preceding::select[1]
        }
        Thread.sleep(1000);
    }

    //Method to check if Requery button is displayed
    public void checkQueryButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.scrollToElement("XPATH", "//td[12]/div/a/i");
        getDriver().findElement(By.xpath("//td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.linkText("View Details")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        if(TestUtils.isElementPresent("XPATH", "//div[4]/button")){
            testInfo.get().log(Status.ERROR,"Requery button is displayed");
        } else {
            testInfo.get().info("Requery Button is not displayed");
        }
        getDriver().findElement(By.linkText("Back")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/h4")));
    }

    public void searchByCheckStatus(String status) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search by Check Status("+ status +")");

        Select dropdown = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
        dropdown.selectByVisibleText(status);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        if(!status.equalsIgnoreCase("Select Check Status")){
        	Thread.sleep(1);
            TestUtils.assertSearchText("XPATH", "//td[4]", status.toUpperCase());
        }
        Thread.sleep(1000);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void navigateToSimSwap(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Navigate to Sim Swap Test");
        if (testEnv.equalsIgnoreCase("stagingData")) {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5823303894SIM Swap\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5823303894SIM Swap\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                Thread.sleep(500);
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5823303894SIM Swap\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5823303894SIM Swap\"]")).click();
                Thread.sleep(500);
            }
        }else {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5823303894SIM Swap\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5823303894SIM Swap\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5823303894SIM Swap\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5823303894SIM Swap\"]")).click();
                Thread.sleep(500);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/h4")));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | Sim Swap");
        TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div/div/div/div/h4", "SIM Swap");
        Thread.sleep(500);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void tableDetailsTest(String testEnv) throws InterruptedException, java.text.ParseException {

        TestUtils.testTitle("Sim Swap Table Details Test");
        Assertion.assertSimSwapTableDetails();

        //confirm that the requests with UNCHECKED as the Check Status should come first
        TestUtils.testTitle("To Confirm that UNCHECKED as the Check Status should come first Test");
        int j = 0;
        String checkStatus;
        checkStatus = getDriver().findElement(By.xpath("//td[4]")).getText();
        if(checkStatus.equalsIgnoreCase("UNCHECKED")){
            j++;
        }
        for(int i = 2; i<=10; i++){
            checkStatus = getDriver().findElement(By.xpath("//tr["+i+"]/td[4]")).getText();
            if(checkStatus.equalsIgnoreCase("UNCHECKED")){
                j++;
            }
        }
        testInfo.get().info("First <b>"+j+"</b> Records are UNCHECKED");

        //Confirm that the sim swap table is ordered based on the most recent swap time and check status
        TestUtils.testTitle("To Confirm that sim swap table is ordered based on the most recent swap time Test");
        String date1 = getDriver().findElement(By.xpath("//td[6]")).getText();
        String curDate = TestUtils.convertDate(date1);
        Calendar cDate = yyyymmddToDate(curDate);
        String date2 = getDriver().findElement(By.xpath("//tr[2]/td[6]")).getText();
        String nxtDate = TestUtils.convertDate(date2);
        Calendar NDate = yyyymmddToDate(nxtDate);
        if(NDate.before(cDate)){
            testInfo.get().info("sim swap table is ordered based on the most recent swap time");
        }else{
            testInfo.get().error("sim swap table is not ordered based on the most recent swap time");
        }

    }



    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void cardsTest(String testEnv) throws InterruptedException {

        TestUtils.testTitle("Total Swap Requests Test");
        String totalSwapRequests = getDriver().findElement(By.id("pendingCount")).getText();
        if (totalSwapRequests.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalSwapRequests + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Checked Test");
        String totalChecked = getDriver().findElement(By.id("validCount")).getText();
        if (totalChecked.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalChecked + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Failed Check Test");
        String totalCheckFailed = getDriver().findElement(By.id("invalidCount")).getText();
        if (totalCheckFailed.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalCheckFailed + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Unchecked Test");
        String totalUnchecked= getDriver().findElement(By.xpath("//div[4]/div/div/h5")).getText();
        if (totalUnchecked.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalUnchecked + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Locked Test");
        String totalLocked= getDriver().findElement(By.xpath("//div[5]/div/div/h5")).getText();
        if (totalLocked.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalLocked + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Blocked Test");
        String totalBlocked= getDriver().findElement(By.xpath("//div[6]/div/div/h5")).getText();
        if (totalBlocked.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, totalBlocked + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        TestUtils.testTitle("Total Sim Swap Summation Test");
        int totalUncheck = TestUtils.convertToInt(totalUnchecked);
        int totalCheck = TestUtils.convertToInt(totalChecked);
        int totalFailChek = TestUtils.convertToInt(totalCheckFailed);
        int expectedSwapRequests = totalCheck + totalUncheck + totalFailChek;
        int actualSwapRequests = TestUtils.convertToInt(totalSwapRequests);

        try {
            Assert.assertEquals(expectedSwapRequests,actualSwapRequests);
            testInfo.get().log(Status.INFO,"Total Swap Requests <b>("+actualSwapRequests+")</b> equals to summation of Total Checked <b>("+totalCheck+")</b>, Total Failed Check <b>("+totalFailChek+")</b> and Total Unchecked <b>("+totalUncheck+")</b>. Swap");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Total Checked, Total Unchecked and Total Failed Swap Requests Summation is not equal to total Swap Requests");
            testInfo.get().error(verificationErrorString);
        }

        TestUtils.testTitle("Total Page Number Test");
        TestUtils.scrollToElement("XPATH", "//div[2]/div[4]/div");
        TestUtils.assertSearchText("XPATH", "//div[2]/div[4]/div", "SHOWING 1 TO 10 OF "+actualSwapRequests); 
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByMsisdnTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search by MSISDN Test: " + msisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByUserIdTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        getDriver().navigate().refresh();

        TestUtils.testTitle("Search by User ID Test: " + userId);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("userId")).clear();
        getDriver().findElement(By.name("userId")).sendKeys(userId);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='users']/tbody/tr/td[5]")));
        TestUtils.assertSearchText("XPATH", "//table[@id='users']/tbody/tr/td[5]", userId);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByUserIdAndMsisdnTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().navigate().refresh();
        TestUtils.testTitle("Search by User ID Test: " + userId + " and MSISDN: " + msisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("userId")).clear();
        getDriver().findElement(By.name("userId")).sendKeys(userId);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//td[5]", userId);
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchBySwapStatusTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        getDriver().navigate().refresh();

        //Test Blocked Status
        statusCheck("Blocked");
        Thread.sleep(1000);
        //Test Locked Status
        statusCheck("Locked");

        Thread.sleep(1000);
        //Test Swapped Status
        statusCheck("Swapped");

        Thread.sleep(1000);
        //Test Unblocked Status
        statusCheck("Unblocked");

        Thread.sleep(1000);
        //Test Unlocked Status
        statusCheck("Unlocked");

        Thread.sleep(1000);
        //Test N/A Status
        statusCheck("N/A");

        Thread.sleep(1000);
        //Reset Status
        statusCheck("Select Swap Status");

    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByCheckStatusTest() throws InterruptedException {
        getDriver().findElement(By.id("advancedBtn")).click();
        
        //Test Checked Status
        searchByCheckStatus("Checked");

        Thread.sleep(1000);
        //Test Failed Status
        searchByCheckStatus("Failed Check");

        Thread.sleep(1000);
        //Test Unchecked Status
        searchByCheckStatus("Unchecked");

        Thread.sleep(1000);
        //Reset Status
        searchByCheckStatus("Select Check Status");

    }


    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByDateTest(String testEnv) throws InterruptedException, java.text.ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        getDriver().navigate().refresh();
        TestUtils.testTitle("Search by Start Date: "+startDate);
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).clear();
        getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).sendKeys(startDate);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        String table_Date = getDriver().findElement(By.xpath("//td[6]")).getText();
        TestUtils.convertDate(table_Date);
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).clear();
        Thread.sleep(500);

        TestUtils.testTitle("Search by End Date: "+endDate);
        getDriver().findElement(By.xpath("//div[2]/div/div[3]/label/input")).clear();
        getDriver().findElement(By.xpath("//div[2]/div/div[3]/label/input")).sendKeys(endDate);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        String table_Date2 = getDriver().findElement(By.xpath("//td[6]")).getText();
        TestUtils.convertDate(table_Date2);
        Thread.sleep(500);

        TestUtils.testTitle("Search by Date Range: "+ startDate + " and " + endDate);
        getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).clear();
        getDriver().findElement(By.xpath("//div[2]/div/div[2]/label/input")).sendKeys(startDate);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        String table_Date3 = getDriver().findElement(By.xpath("//td[6]")).getText();
        String newDate = TestUtils.convertDate(table_Date3);
        TestUtils.checkDateBoundary(startDate, endDate, newDate);
        Thread.sleep(500);
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByNewSimSerial(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().navigate().refresh();

        TestUtils.testTitle("Search by New Sim Serial Test: "+simSerial);
        getDriver().findElement(By.name("newSerial")).clear();
        getDriver().findElement(By.name("newSerial")).sendKeys(simSerial);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("vd")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simSwapDetails']/div/div/div/table/tbody/tr[8]/td[2]")));
        TestUtils.assertSearchText("XPATH", "//div[@id='simSwapDetails']/div/div/div/table/tbody/tr[7]/td[2]", simSerial);
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void viewDetailsTest() {

        TestUtils.testTitle("Swap Details Test");
        Assertion.assertSwapDetails();
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void lockTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Lock Test");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn);

        TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        try{
            getDriver().findElement(By.id("lockElementId")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.assertSearchText("ID", "myModalLabel", "SIM Swap Locking Approval");
            TestUtils.testTitle("Lock With Empty Feedback Test");
            getDriver().findElement(By.id("APPROVED")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")));
            Thread.sleep(500);
            String Msg = getDriver().findElement(By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")).getText();
            testInfo.get().info(Msg);
           
            TestUtils.testTitle("Lock With Feedback Reason Test");
            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]", "SIM swap Lock request successfully approved. Waiting for the next level of approval");
           Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
         //   TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("lockElementId")).click();
            Thread.sleep(1000);
           wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            Thread.sleep(1000);
            TestUtils.scrollToElement("ID", "feedback");
        //    getDriver().findElement(By.id("feedback")).clear();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//textarea[@id='feedback']")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap locked successfully')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[3]")));
//            Thread.sleep(500);
//            TestUtils.assertSearchText("XPATH", "//tbody/tr[1]/td[3]", "LOCKED");
            
        }catch (Exception e){
            testInfo.get().info("MSISDN still unlocked, proceeding with lock Approval Test");
            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("lockElementId")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.assertSearchText("ID", "myModalLabel", "SIM Swap Locking Approval");
//            TestUtils.testTitle("Lock With Empty Feedback Test");
//            getDriver().findElement(By.id("APPROVED")).click();
//            Thread.sleep(500);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")));
//            Thread.sleep(500);
//            String errMsg = getDriver().findElement(By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")).getText();
//            testInfo.get().error(errMsg);
//            TestUtils.testTitle("Unlock With Feedback Reason Test");
//            getDriver().findElement(By.id("feedback")).clear();
//            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
//            getDriver().findElement(By.id("APPROVED")).click();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]")));
//            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]", "SIM swap Unlock Request is successfully approved. Waiting for the next level of approval");
//            Thread.sleep(500);
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
//            TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
//            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
//            Thread.sleep(500);
//            getDriver().findElement(By.id("unLockElementId")).click();
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
             TestUtils.scrollToElement("ID", "feedback");
             getDriver().findElement(By.id("feedback")).clear();
             getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
             getDriver().findElement(By.id("APPROVED")).click();
             wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap locked successfully')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
           
        }
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void unlockTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("UnLock Test");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.xpath("//div[5]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn);

        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        try{
            getDriver().findElement(By.id("unLockElementId")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.assertSearchText("ID", "myModalLabel", "SIM Swap Unlocking Approval");
            TestUtils.testTitle("Lock With Empty Feedback Test");
            getDriver().findElement(By.id("APPROVED")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")));
            Thread.sleep(500);
            String Msg = getDriver().findElement(By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")).getText();
            testInfo.get().info(Msg);
            TestUtils.testTitle("Unlock With Feedback Reason Test");
            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]", "SIM swap Unlock Request is successfully approved. Waiting for the next level of approval");
            Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
         //   TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("unLockElementId")).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        //    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            Thread.sleep(1000);
            TestUtils.scrollToElement("ID", "feedback");
            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap Unlocked successfully')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap Unlocked successfully')]", "Sim Swap Unlocked successfully");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[3]")));
            Thread.sleep(500);
         //   TestUtils.assertSearchText("XPATH", "//tbody/tr[1]/td[3]", "UNLOCKED");
        }catch (Exception e){
        	 testInfo.get().info("MSISDN still locked, proceeding with unlock Approval Test");
             getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
             Thread.sleep(500);
             getDriver().findElement(By.id("unLockElementId")).click();
             wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
             wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
             TestUtils.assertSearchText("ID", "myModalLabel", "SIM Swap Unlocking Approval");
//            TestUtils.testTitle("Lock With Empty Feedback Test");
//            getDriver().findElement(By.id("APPROVED")).click();
//            Thread.sleep(500);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")));
//            Thread.sleep(500);
//            String errMsg = getDriver().findElement(By.xpath("//*[contains(text(),'Please enter a reason in the Feedback section before you can proceed')]")).getText();
//            testInfo.get().error(errMsg);
//            TestUtils.testTitle("Lock With Feedback Reason Test");
//            getDriver().findElement(By.id("feedback")).clear();
//            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
//            getDriver().findElement(By.id("APPROVED")).click();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]")));
//            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]", "SIM swap Lock request successfully approved. Waiting for the next level of approval");
//            Thread.sleep(500);
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
//            TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
//            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
//            Thread.sleep(500);
//            getDriver().findElement(By.id("lockElementId")).click();
//            Thread.sleep(500);
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
             TestUtils.scrollToElement("ID", "feedback");
             getDriver().findElement(By.id("feedback")).clear();
             getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
             getDriver().findElement(By.id("APPROVED")).click();
             wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap Unlocked successfully')]")));
             TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap Unlocked successfully')]", "Sim Swap Unlocked successfully");
             
        }
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void requeryButtonTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        //Check Requery button for Failed swap request
        TestUtils.testTitle("Requery Button for Failed Swap Request Test");
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText("N/A");
        Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
        dropdown2.selectByVisibleText("Checked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        String sentToSeilbel = getDriver().findElement(By.xpath("//td[11]")).getText();
        testInfo.get().log(Status.INFO, "<b> Sent to Siebel  : </b>" + sentToSeilbel);
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("vd")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/button")));
        TestUtils.assertSearchText("XPATH", "//div[4]/button", "Requery");

        //Check requery feedback after clicking requery button
        getDriver().findElement(By.xpath("//div[4]/button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'The New SIM Card does not exist, Please check it again')]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'The New SIM Card does not exist, Please check it again')]")).getText()+ " found");

//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[contains(text(),'Please ensure that all required inputs are entered')]")));
//        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'Please ensure that all required inputs are entered')]")).getText()+ " found");
//
//        
        getDriver().findElement(By.linkText("Back")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/h4")));

        //Check Requery button for successful swap request
        TestUtils.testTitle("Requery Button for Swapped Request Test");
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        new Select(getDriver().findElement(By.xpath("//select"))).selectByVisibleText("Swapped");
        new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select"))).selectByVisibleText("Checked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        String sentToSeilbel2 = getDriver().findElement(By.xpath("//td[11]")).getText();
        testInfo.get().log(Status.INFO, "<b> Sent to Siebel  : </b>" + sentToSeilbel2);
        checkQueryButton();

        //Check Requery button for Locked swap request
        TestUtils.testTitle("Requery Button for Locked Swap Request Test");
        new Select(getDriver().findElement(By.xpath("//select"))).selectByVisibleText("Locked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        checkQueryButton();

        //Check Requery button for Failed check swap request
        TestUtils.testTitle("Requery Button for Failed Check Swap Request Test");
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select"))).selectByVisibleText("Failed Check");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        checkQueryButton();

        //Check Requery button for Blocked swap request
        TestUtils.testTitle("Requery Button for Blocked Swap Test");
        new Select(getDriver().findElement(By.xpath("//select"))).selectByVisibleText("Blocked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        checkQueryButton();

        //Check Requery button for Unchecked swap request
        TestUtils.testTitle("Requery Button for Unchecked Swap Test");
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select"))).selectByVisibleText("Unchecked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        checkQueryButton();
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void demographicDetailsTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Demographic Details and Matching Test for MSISDN: "+msisdn);
        //Search by MSISDN
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn);

        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("vd")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simSwapDetails']/div/div/div/table/tbody/tr[8]/td[2]")));
        getDriver().findElement(By.linkText("Demographic Details")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='demograhicsDetails']/div/div/div/p")));
        TestUtils.assertSearchText("XPATH", "//div[@id='demograhicsDetails']/div/div/div/p", "Demographic Information");

        //Assert Demographic Details
        testInfo.get().log(Status.INFO, "<b>Field <----> From Subscriber <---->From System <---->Validation</b>");
        validateDemographicDetailField("//div[2]/div/div/div/table/tbody/tr/td", "//div[2]/div/div/div/table/tbody/tr/td[2]", "//td[3]", "//td[4]");
        validateDemographicDetailField("//div[2]/div/div/div/table/tbody/tr[2]/td", "//div[2]/div/div/div/table/tbody/tr[2]/td[2]", "//tr[2]/td[3]", "//tr[2]/td[4]");
        for(int i =3; i<=19; i++){
            validateDemographicDetailField("//div[2]/div/div/div/table/tbody/tr["+i+"]/td", "//div[2]/div/div/div/table/tbody/tr["+i+"]/td[2]", "//tr["+i+"]/td[3]", "//tr["+i+"]/td[4]");
        }

        //Assert FDN
        TestUtils.testTitle("Frequently Dialed Numbers and Matching Test");
        testInfo.get().log(Status.INFO, "<b>Frequently Dialed Number From Subscriber <---->Number<---->Validation</b>");
        validateFrequentlyDialedNumberField("//table[2]/tbody/tr/td", "//table[2]/tbody/tr/td[2]", "//table[2]/tbody/tr/td[3]");
        for(int i =2; i<=5; i++){
            validateFrequentlyDialedNumberField("//table[2]/tbody/tr["+i+"]/td", "//table[2]/tbody/tr["+i+"]/td[2]", "//table[2]/tbody/tr["+i+"]/td[3]");
        }
        getDriver().findElement(By.linkText("Back")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/h4")));
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void lockCheckedSwapTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search for Checked and Unlocked Swap Test");
        //Search for Checked swap that has not been sent to siebel
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText("Unlocked");
        Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
        dropdown2.selectByVisibleText("Unchecked");
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        try{
            TestUtils.assertSearchText("XPATH", "//td[3]", "UNLOCKED");
            TestUtils.assertSearchText("XPATH", "//td[4]", "UNCHECKED");
            String sentToSeilbel = getDriver().findElement(By.xpath("//td[11]")).getText();
            testInfo.get().log(Status.INFO, "<b> Sent to Siebel  : </b>" + sentToSeilbel);


            TestUtils.testTitle("Lock Checked Swap Test");
            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("lockElementId")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.assertSearchText("ID", "myModalLabel", "Swap Locking Approval");

            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Lock request successfully approved. Waiting for the next level of approval')]", "SIM swap Lock request successfully approved. Waiting for the next level of approval");
            Thread.sleep(500);
            TestUtils.assertSearchText("XPATH", "//td[3]", "UNLOCKED");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
            TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
            getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("lockElementId")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.scrollToElement("ID", "feedback");
            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap locked successfully')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
        } catch (Exception e){
          //  TestUtils.assertSearchText("XPATH", "//td/div/div", "No data available in table");
           // testInfo.get().info("LOCKED");
        	 getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
             Thread.sleep(500);
             getDriver().findElement(By.id("lockElementId")).click();
             wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
             wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
             TestUtils.assertSearchText("ID", "myModalLabel", "Swap Locking Approval");
              TestUtils.scrollToElement("ID", "feedback");
              getDriver().findElement(By.id("feedback")).clear();
              getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
              getDriver().findElement(By.id("APPROVED")).click();
              wait.until(ExpectedConditions.visibilityOfElementLocated(
                     By.xpath("//*[contains(text(),'Sim Swap locked successfully')]")));
             TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap locked successfully')]", "Sim Swap locked successfully");
        }
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void unlockCheckedSwapTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search for Checked and Locked Swap Test");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("advancedBtn")));
        //Search for Checked swap that has not been sent to siebel
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText("Locked");
        try{
            Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
            dropdown2.selectByVisibleText("Checked");
        } catch (Exception e){
            getDriver().findElement(By.id("advancedBtn")).click();
            Thread.sleep(500);
            Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
            dropdown2.selectByVisibleText("Checked");
        }

        try { 
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.assertSearchText("XPATH", "//td[3]", "LOCKED");
        TestUtils.assertSearchText("XPATH", "//td[4]", "CHECKED");
        String sentToSeilbel = getDriver().findElement(By.xpath("//td[11]")).getText();
        testInfo.get().log(Status.INFO, "<b> Sent to Siebel  : </b>" + sentToSeilbel);

        TestUtils.testTitle("Unlock Checked Swap Test");
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("unLockElementId")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
        TestUtils.assertSearchText("ID", "myModalLabel", "Swap Unlocking Approval");
        getDriver().findElement(By.id("feedback")).clear();
        getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
        getDriver().findElement(By.id("APPROVED")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'SIM swap Unlock Request is successfully approved. Waiting for the next level of approval')]", "SIM swap Unlock Request is successfully approved. Waiting for the next level of approval");
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")));
        TestUtils.assertSearchText("XPATH", "//td[3]", "LOCKED");
        TestUtils.scrollToElement("XPATH", "//table[@id='users']/tbody/tr/td[12]/div/a/i");
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("unLockElementId")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
        TestUtils.scrollToElement("ID", "feedback");
        getDriver().findElement(By.id("feedback")).clear();
        getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
        getDriver().findElement(By.id("APPROVED")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Sim Swap Unlocked successfully')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap Unlocked successfully')]", "Sim Swap Unlocked successfully");
      }catch(Exception e) {
    	//  TestUtils.assertSearchText("XPATH", "//td/div/div", "No data available in table");
          // testInfo.get().info("LOCKED");
       	    getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("unLockElementId")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
            TestUtils.assertSearchText("ID", "myModalLabel", "Swap Unlocking Approval");
            TestUtils.scrollToElement("ID", "feedback");
            getDriver().findElement(By.id("feedback")).clear();
            getDriver().findElement(By.id("feedback")).sendKeys("Test by Nonso");
            getDriver().findElement(By.id("APPROVED")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Sim Swap Unlocked successfully')]")));
            TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Sim Swap Unlocked successfully')]", "Sim Swap Unlocked successfully");
      }
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void bulkUnlockUnblockTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        //check that bulk unlock/unblock button exists
        TestUtils.testTitle("Bulk Unlock/Button View Test");

        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("bulkNumberBarringBtn")));
        TestUtils.assertSearchText("ID", "bulkNumberBarringBtn", "BULK OPERATIONS");
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Bulk Unblock/Unlock')]")));
        TestUtils.assertSearchText("XPATH", "//a[contains(text(),'Bulk Unblock/Unlock')]", "Bulk Unblock/Unlock");

        //Confirm that the modal is displayed
        TestUtils.testTitle("Bulk Unlock/Button Modal View Test");

        getDriver().findElement(By.linkText("Bulk Unblock/Unlock")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("fileModalTitle")));
        TestUtils.assertSearchText("ID", "fileModalTitle", "Bulk Unblock/ Unlock");
        TestUtils.assertSearchText("XPATH", "//div[3]/button/span", "DOWNLOAD TEMPLATE");
        TestUtils.assertSearchText("XPATH", "//button[2]/span", "UPLOAD");
        getDriver().findElement(By.xpath("//div[3]/button/span")).click();
        Thread.sleep(500);

        //Confirm that the cancel button works
        TestUtils.testTitle("Cancel/Close Modal Test");
        getDriver().findElement(By.xpath("//modal-container/div/div/div/button/span")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("fileModalTitle")));
        TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div/div/div/div/h4", "SIM Swap");

        String emptyTemplate = "Bulk_UnBlock_Unlock_Empty_File_Template.xlsx";

        //Upload template with empty content
        TestUtils.testTitle("Upload Empty Template Test: "+emptyTemplate);
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Bulk Unblock/Unlock')]")));
        getDriver().findElement(By.linkText("Bulk Unblock/Unlock")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("fileModalTitle")));
        getDriver().findElement(By.name("fileInput")).clear();
        TestUtils.uploadFile(By.name("fileInput"), emptyTemplate);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//simple-notification/div/div/div[2]")));
        String errorMsg = getDriver().findElement(By.xpath("//simple-notification/div/div/div[2]")).getText();
        testInfo.get().log(Status.ERROR, errorMsg+ " found");
        
        
        Thread.sleep(1000);

        String nonEmptyTemplate = "Bulk_UnBlock_Unlock_Template.xlsx";
        
      

        //Upload template with some details
        TestUtils.testTitle("Upload Non Empty Template Test: "+nonEmptyTemplate);
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Bulk Unblock/Unlock')]")));
        getDriver().findElement(By.linkText("Bulk Unblock/Unlock")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("fileModalTitle")));
        getDriver().findElement(By.name("fileInput")).clear();
        TestUtils.uploadFile(By.name("fileInput"), nonEmptyTemplate);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//simple-notification/div/div/div[2]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//simple-notification/div/div/div[2]")).getText()+ " found");
    }

    //Check and Reject sim swap request
    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void checkSwapTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);



        //Check swap without feedback reason
        TestUtils.testTitle("Check swap request with empty feedback reason Test for MSISDN: "+msisdn2);
        
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText("Select Swap Status");
        Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/select")));
        dropdown2.selectByVisibleText("Select Check Status");

        //Search by MSISDN
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn2);
        getDriver().findElement(By.xpath("//div[6]/button")).click(); 
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait...")); 
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        Thread.sleep(500);
        TestUtils.scrollToElement("XPATH", "//td[2]");
        TestUtils.assertSearchText("XPATH", "//td[2]", msisdn2);

        //Navigate to Check/Reject view
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("vd")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));

  //      getDriver().findElement(By.xpath("//div/button[2]")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//*[contains(text(),'Approval feedback is required')]")));
//        testInfo.get().log(Status.ERROR, getDriver().findElement(By.xpath("//*[contains(text(),'Approval feedback is required')]")).getText()+ " found");
//        
        getDriver().findElement(By.xpath("//button[contains(text(),'Check')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You did not provide any Approval Feedback')]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'You did not provide any Approval Feedback')]")).getText()+ " found");
        
        //Check swap with feedback reason
        TestUtils.testTitle("Check swap request with feedback reason Test");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You did not provide any Approval Feedback')]")));
        getDriver().findElement(By.name("feedback")).clear();
        getDriver().findElement(By.name("feedback")).sendKeys("Test by Nonso");
        Thread.sleep(200);
        getDriver().findElement(By.xpath("//button[contains(text(),'Check')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You cannot approve this request again. The next approval requires a user higher in the hierarchy')]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'You cannot approve this request again. The next approval requires a user higher in the hierarchy')]")).getText()+ " found");

        getDriver().navigate().refresh();
        //Search by MSISDN
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn2);
        getDriver().findElement(By.xpath("//div[6]/button")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));

        //Navigate to Check/Reject view
        getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[12]/div/a/i")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("vd")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));

        //Reject swap without feedback reason
        TestUtils.testTitle("Reject swap request with empty feedback reason Test");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You cannot approve this request again. The next approval requires a user higher in the hierarchy')]")));
        getDriver().findElement(By.name("feedback")).clear();
        getDriver().findElement(By.xpath("//button[contains(text(),'Reject')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You did not provide any Approval Feedback')]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'You did not provide any Approval Feedback')]")).getText()+ " found");

        //Check swap with feedback reason
        TestUtils.testTitle("Reject swap request with feedback reason Test");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Approval feedback is required')]")));
        getDriver().findElement(By.name("feedback")).clear();
        getDriver().findElement(By.name("feedback")).sendKeys("Test by Nonso");
        getDriver().findElement(By.xpath("//button[contains(text(),'Reject')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'You cannot approve this request again. The next approval requires a user higher in the hierarchy')]")));
        testInfo.get().log(Status.INFO, getDriver().findElement(By.xpath("//*[contains(text(),'You cannot approve this request again. The next approval requires a user higher in the hierarchy')]")).getText()+ " found");

    }

}
