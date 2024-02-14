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

public class NumberBarring extends TestBase {
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
        JSONObject envs = (JSONObject) config.get("numberBarring");

        msisdn = (String) envs.get("msisdn");
        kitTag = (String) envs.get("kitTag");
        registrationCenter = (String) envs.get("registrationCenter");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByMsisdn(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("msisdn")));
        TestUtils.testTitle("Valid MSISDN Search: "+msisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'08050000005')]")));
        TestUtils.assertSearchText("XPATH","//span[contains(text(),'08050000005')]", msisdn);
        Thread.sleep(1000);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void barUnbar(String status) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(2000);
        if(status.equals("bar number")){
            TestUtils.testTitle("Bar number Test");
            testInfo.get().info(status+" button found");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'])[11]")));
            getDriver().findElement(By.cssSelector("div:nth-child(2) > #fast_rowId\\ \\  .float-right")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
            TestUtils.assertSearchText("XPATH", "//h2", "Bar Number ?");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div/div[2]/button")));
            getDriver().findElement(By.xpath("//div/div[2]/button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'The operation is successful.')]")));
            Thread.sleep(500);
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'The operation is successful.')]",
                    "The operation is successful.");
            TestUtils.scrollToElement("XPATH", "(//button[@type='button'])[11]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//button[@type='button'])[11]")));
            Thread.sleep(2000);
            TestUtils.assertSearchText("XPATH",
                    "(//button[@type='button'])[11]",
                    "UNBAR NUMBER");

        }else{
            TestUtils.testTitle("UnBar number Test");
            testInfo.get().info(status+" button found");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'])[11]")));
            getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
            TestUtils.assertSearchText("XPATH", "//h2", "Unbar Number ?");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div/div[2]/button")));
            getDriver().findElement(By.xpath("//div/div[2]/button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Success')]")));
            TestUtils.assertSearchText("XPATH",
                    "//*[contains(text(),'Success')]",
                    "Success");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//button[@type='button'])[11]")));
            Thread.sleep(2000);
            TestUtils.assertSearchText("XPATH",
                    "(//button[@type='button'])[11]",
                    "BAR NUMBER");
        }

    }


    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void navigateToBarring(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Navigate to Number Barring Test");
        if (testEnv.equalsIgnoreCase("stagingData")) {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822932005Barring\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5822932005Barring\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                Thread.sleep(500);
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822932005Barring\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5822932005Barring\"]")).click();
                Thread.sleep(500);
            }
        }else {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"854243370Number Barring\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"854243370Number Barring\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822932005Barring\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5822932005Barring\"]")).click();
                Thread.sleep(500);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/div/div/div/div/h4")));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | Number Barring");
        TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Number Barring");
        Thread.sleep(500);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void viewDetailsTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("kitTag")));
        TestUtils.testTitle("Verify Number Barring Data Table Test:("+kitTag+")");
        getDriver().findElement(By.name("kitTag")).sendKeys(kitTag);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div/span")));
        Thread.sleep(2000);
        Assertion.assertNumberBarringTable();

    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void bulkDownloadAndUploadBarringTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Download Bulk Upload Template Test");
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        Thread.sleep(2000);
        try{
            getDriver().findElement(By.xpath("//div[3]/div/div/div/a")).click();
        }catch (Exception e){
            getDriver().findElement(By.linkText("Bulk Number Barring")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Bulk Number Barring");
        getDriver().findElement(By.xpath("//div[3]/button/span")).click();
        Thread.sleep(500);

        String invalidFile = "Invalid.xlsx";
        TestUtils.testTitle("Invalid Bulk Upload Test: "+invalidFile);
        getDriver().findElement(By.id("fileInput")).clear();
        TestUtils.uploadFile(By.id("fileInput"), invalidFile);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Error occurred while processing request. Please contact support')]")));
        Thread.sleep(500);
        String errMsg = getDriver().findElement(By.xpath("//*[contains(text(),'Error occurred while processing request. Please contact support')]")).getText();
        testInfo.get().error(errMsg);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bulkNumberBarringBtn")));
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        Thread.sleep(500);
        try{
            getDriver().findElement(By.xpath("//div[3]/div/div/div/a")).click();
        }catch (Exception e){
            getDriver().findElement(By.linkText("Bulk Number Barring")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
        String file = "Bulk_Number_Barring_Template.xlsx";
        TestUtils.testTitle("Valid Bulk Upload Test: "+file);
        getDriver().findElement(By.id("fileInput")).clear();
        TestUtils.uploadFile(By.id("fileInput"), file);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(500);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void barNumberTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().navigate().refresh();

        searchByMsisdn(msisdn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//button[@type='button'])[11]")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div:nth-child(2) > #fast_rowId\\ \\  .float-right")));
        String status = getDriver().findElement(By.cssSelector("div:nth-child(2) > #fast_rowId\\ \\  .float-right")).getText().toLowerCase();
        barUnbar(status);

        //Wait for a while before performing bar action again
        Thread.sleep(2000);

        status = getDriver().findElement(By.cssSelector("div:nth-child(2) > #fast_rowId\\ \\  .float-right")).getText().toLowerCase();
        barUnbar(status);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void bulkDownloadAndUploadUnbarringTest(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Download Bulk Upload Template Test");
        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[3]/div/div/div/a[2]")));
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[3]/div/div/div/a[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@id='fileModalTitle']")));
        Thread.sleep(500);

        TestUtils.assertSearchText("XPATH", "//h4[@id='fileModalTitle']", "Bulk Number Unbarring");
        getDriver().findElement(By.xpath("//div[3]/button/span")).click();
        Thread.sleep(500);

        String invalidFile = "Invalid.xlsx";
        TestUtils.testTitle("Invalid Bulk Upload Test: "+invalidFile);
        getDriver().findElement(By.name("fileInput")).clear();
        TestUtils.uploadFile(By.name("fileInput"), invalidFile);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Error occurred while processing request. Please contact support')]")));
        String errMsg = getDriver().findElement(By.xpath("//*[contains(text(),'Error occurred while processing request. Please contact support')]")).getText();
        testInfo.get().error(errMsg);

        getDriver().navigate().refresh();

        getDriver().findElement(By.id("bulkNumberBarringBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//div[3]/div/div/div/a[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@id='fileModalTitle']")));
        String file = "Bulk_Number_Barring_Template.xlsx";
        TestUtils.testTitle("Valid Bulk Upload Test: "+file);
        getDriver().findElement(By.name("fileInput")).clear();
        TestUtils.uploadFile(By.name("fileInput"), file);
        getDriver().findElement(By.xpath("//button[2]/span")).click();
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchTest(String testEnv) throws InterruptedException, java.text.ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("msisdn")));

        searchByMsisdn(msisdn);

        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("msisdn")));
        TestUtils.testTitle("Kit Tag Search: "+kitTag);
        getDriver().findElement(By.name("msisdn")).clear();
        Thread.sleep(1000);
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.name("kitTag")).sendKeys(kitTag);
        Thread.sleep(1000);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        ///wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn.btn-yellow.btn-sm.mr-4")));
        Thread.sleep(3000);
        String uniqueId = getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
        if (uniqueId.contains(kitTag)) {
            testInfo.get().log(Status.INFO, kitTag + " found");
        } else {
            testInfo.get().log(Status.ERROR, kitTag+" not found but found ["+uniqueId+"]");
        }

        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("kitTag")));
        TestUtils.testTitle("Search Registration Centre: "+registrationCenter);
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.id("advancedBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/label/span/select")));
        getDriver().findElement(By.xpath("//div[2]/div/div/label/span/select")).click();
        Select dropdown = new Select(getDriver().findElement(By.xpath("//div[2]/div/div/label/span/select")));
        dropdown.selectByVisibleText(registrationCenter);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(1000);
        TestUtils.assertSearchText("XPATH", "//div[2]/p[6]/span", registrationCenter);

        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advancedBtn")));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advancedBtn")));
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
        Thread.sleep(3000);
        TestUtils.convertDate(dateFound);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn.btn-yellow.btn-sm.mr-4")));
        String idFound = getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
        if (idFound.contains(kitTag)) {
            testInfo.get().log(Status.INFO, kitTag + " found");
        } else {
            testInfo.get().log(Status.ERROR, kitTag+" not found but found ["+idFound+"]");
        }
    }

}