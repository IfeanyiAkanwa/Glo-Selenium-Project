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

public class SubscriberStatus extends TestBase {
    private String msisdn;
    private String kitTag;
    private String registrationCenter;
    private String startDate;
    private String endDate;


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
        JSONObject envs = (JSONObject) config.get("subscriberStatus");

        msisdn = (String) envs.get("subscriberMsisdn");
        kitTag = (String) envs.get("subscriberKitTag");
        registrationCenter = (String) envs.get("subscriberRegistrationCenter");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void navigateToSubscriberStatus(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Navigate to Subscriber Status Test");
        if (testEnv.equalsIgnoreCase("stagingData")) {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5824646356Subscriber Status\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5824646356Subscriber Status\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                Thread.sleep(500);
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5824646356Subscriber Status\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5824646356Subscriber Status\"]")).click();
                Thread.sleep(500);
            }
        }else {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"854250883Subscriber Status\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"854250883Subscriber Status\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5824646356Subscriber Status\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5824646356Subscriber Status\"]")).click();
                Thread.sleep(500);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/div/div/div/div/h4")));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | Subscriber Status");
        TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Subscriber Status");
        Thread.sleep(500);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void viewDetailsTest() throws InterruptedException{
        TestUtils.testTitle("Verify Subscriber Status Data Table Test");
        Thread.sleep(1000);
        Assertion.assertNumberBarringTable();
        Assertion.portraitDisplay();
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void changeSubscriberStatusTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Confirm Dropdown Status Test for MSISDN: "+msisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        TestUtils.assertSearchText("XPATH","//div[@id='fast_rowId  ']/div/div[2]/div", "Phone number: "+msisdn);

        TestUtils.scrollToElement("ID", "statusBtn0");
        String status = getDriver().findElement(By.id("statusBtn0")).getText();
        testInfo.get().log(Status.INFO, "Current Status: "+status);
        getDriver().findElement(By.id("statusBtn0")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//li/span", "VIP");
        TestUtils.assertSearchText("XPATH", "//li/span[2]", "HNI");

        if(status.equalsIgnoreCase("STATUS")){
            TestUtils.testTitle("Change to VIP Test");
            getDriver().findElement(By.xpath("//li/span")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/p", "Do you want to confirm?");
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("statusBtn0")));
            TestUtils.assertSearchText("ID", "statusBtn0", "VIP");
            TestUtils.testTitle("Change to HNI Test");
            Thread.sleep(1000);
            getDriver().findElement(By.id("statusBtn0")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//li/span[2]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("statusBtn0")));
            TestUtils.assertSearchText("ID", "statusBtn0", "HNI");

            TestUtils.testTitle("Remove Status Test");
            getDriver().findElement(By.id("statusBtn0")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//li/span[3]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("ID", "statusBtn0", "STATUS");
        }else{
            TestUtils.testTitle("Remove Status Test");
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//li/span[3]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/p", "Do you want to confirm?");
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("statusBtn0")));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("statusBtn0")));
            TestUtils.assertSearchText("ID", "statusBtn0", "STATUS");

            TestUtils.testTitle("Change to VIP Test");
            getDriver().findElement(By.id("statusBtn0")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//li/span")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("statusBtn0")));
            TestUtils.assertSearchText("ID", "statusBtn0", "VIP");

            TestUtils.testTitle("Change to HNI Test");
            Thread.sleep(1000);
            getDriver().findElement(By.id("statusBtn0")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//li/span[2]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/p")));
            getDriver().findElement(By.xpath("//modal-container/div/div/div/button[2]")).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(),'Success')]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("statusBtn0")));
            TestUtils.assertSearchText("ID", "statusBtn0", "HNI");
        }
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void bulkChangeSubscriberStatusTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        String file = "Subscriber_Status_Update_VIP.xlsx";
        TestUtils.testTitle("Bulk VIP Update Test: "+file);
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[3]/div/div/div/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileModalTitle")));
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "fileModalTitle", "Bulk Vip Status Update");
        Thread.sleep(500);
        getDriver().findElement(By.name("fileInput")).clear();
        
        String path = new File(System.getProperty("user.dir") + "/files").getAbsolutePath(); 
        getDriver().findElement(By.name("fileInput")).sendKeys(path+"/"+file);
        
        Thread.sleep(2000);
        
        
        
    //    TestUtils.uploadFile(By.name("fileInput"), file);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
       
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
 //       wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
//        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        Thread.sleep(1000);
        TestUtils.assertSearchText("XPATH","//div[@id='fast_rowId  ']/div/div[2]/div", "Phone number: "+msisdn);
      //  TestUtils.assertSearchText("XPATH","//span[contains(text(),'"+msisdn+"')]", msisdn);
        Thread.sleep(1000);
        TestUtils.scrollToElement("ID", "statusBtn0");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='statusBtn0']"))); 
        Thread.sleep(3000);
        TestUtils.assertSearchText("ID", "statusBtn0", "VIP");

        String file2 = "Subscriber_Status_Update_HNI.xlsx";
        TestUtils.testTitle("Bulk HNI Update Test: "+file2);
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[3]/div/div/div/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileModalTitle")));
        Thread.sleep(500);
        getDriver().findElement(By.name("fileInput")).clear();
        
        String path2 = new File(System.getProperty("user.dir") + "/files").getAbsolutePath(); 
        getDriver().findElement(By.name("fileInput")).sendKeys(path2+"/"+file2);
        
        Thread.sleep(2000);
        
  //    TestUtils.uploadFile(By.name("fileInput"), file2);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
    //    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
    //    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        Thread.sleep(1000);
        TestUtils.assertSearchText("XPATH","//div[@id='fast_rowId  ']/div/div[2]/div", "Phone number: "+msisdn);
      //  TestUtils.assertSearchText("XPATH","//span[contains(text(),'"+msisdn+"')]", msisdn);
        Thread.sleep(1000);
        TestUtils.scrollToElement("ID", "statusBtn0");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='statusBtn0']")));
        Thread.sleep(3000);
        TestUtils.assertSearchText("ID", "statusBtn0", "HNI");

    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchSubscriberTest(String testEnv) throws InterruptedException, java.text.ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));

        TestUtils.testTitle("Valid MSISDN Search: "+msisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH","//div[@id='fast_rowId  ']/div/div[2]/div", "Phone number: "+msisdn);

     
        getDriver().navigate().refresh();
        TestUtils.testTitle("Kit Tag Search: "+kitTag);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.name("kitTag")).sendKeys(kitTag);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/div/div/div/div[3]")));
        Thread.sleep(500);
        
        String uniqueId = getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
        if (uniqueId.contains(kitTag)) {
            testInfo.get().log(Status.INFO, kitTag + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }

        getDriver().navigate().refresh();
        TestUtils.testTitle("Search Registration Centre: "+registrationCenter);
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[2]/div/div/label/span/select")).click();
        Select dropdown = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/span/select")));
        dropdown.selectByVisibleText(registrationCenter);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.scrollToElement("XPATH", "//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[6]");
        TestUtils.assertSearchText("XPATH", "//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[6]", "Outlet: "+registrationCenter);

        getDriver().navigate().refresh();
        Thread.sleep(500);

        TestUtils.testTitle("Search by Start Date: "+startDate);
        TestUtils.scrollToElement("ID", "advancedBtn");
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(startDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/span")));
        String table_Date = getDriver().findElement(By.xpath("//div[3]/span")).getText();
        TestUtils.convertDate(table_Date);
        Thread.sleep(500);

        TestUtils.testTitle("Search by End Date: "+endDate);
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys(endDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        String table_Date2 = getDriver().findElement(By.xpath("//div[3]/span")).getText();
        TestUtils.convertDate(table_Date2);
        Thread.sleep(500);

        TestUtils.testTitle("Search by Date Range: "+ startDate + " and " + endDate);
        TestUtils.scrollToElement("XPATH", "(//input[@type='text'])[5]");
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(startDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        String table_Date3 = getDriver().findElement(By.xpath("//div[3]/span")).getText();
        String newDate = TestUtils.convertDate(table_Date3);
        TestUtils.checkDateBoundary(startDate, endDate, newDate);
        Thread.sleep(500);

        getDriver().navigate().refresh();
        TestUtils.testTitle("Search by Kit Tag: "+kitTag+" and Start Date: "+startDate);
        getDriver().findElement(By.id("advancedBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.name("kitTag")).sendKeys(kitTag);
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys(endDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/p[5]/span")));
        String dateFound = getDriver().findElement(By.xpath("//div[3]/span")).getText();
        TestUtils.convertDate(dateFound);
        String idFound = getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
        if (idFound.contains(kitTag)) {
            testInfo.get().log(Status.INFO, kitTag + " found");
        } else {
            testInfo.get().log(Status.ERROR, "not found");
        }
    }
}
