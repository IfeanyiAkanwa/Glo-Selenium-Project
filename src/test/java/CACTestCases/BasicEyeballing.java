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

public class BasicEyeballing extends TestBase {
    private String validMsisdn;
    private String validKitTag;
    private String registrationCenter;
    private String startDate;
    private String endDate;
    private String validMsisdn2;

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
        JSONObject envs = (JSONObject) config.get("basicEyeballing");

        validMsisdn = (String) envs.get("validMsisdn");
        validKitTag = (String) envs.get("validKitTag");
        registrationCenter = (String) envs.get("registrationCenter");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
        validMsisdn2 = (String) envs.get("validMsisdn2");
    }


    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void navigateToBasicEyeballing(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Navigate to Basic Eyeballing");
        if (testEnv.equalsIgnoreCase("stagingData")) {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"712090EyeBalling Pool\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"712090EyeBalling Pool\"] > p")).click();
                Thread.sleep(500);
                getDriver().findElement(By.name("8934843Basic Eyeballing")).click();
                Thread.sleep(500);
            } catch (Exception e) {
            	TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"712090EyeBalling Pool\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"712090EyeBalling Pool\"] > p")).click();
                Thread.sleep(500);
                getDriver().findElement(By.name("8934843Basic Eyeballing")).click();
                Thread.sleep(500);
            }
        }else {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"324177975Eyeballing Pool\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"324177975Eyeballing Pool\"] > p")).click();
                Thread.sleep(500);
                getDriver().findElement(By.name("8934843Basic Eyeballing")).click();
                Thread.sleep(500);
            } catch (Exception e) {
            	TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"712090EyeBalling Pool\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"712090EyeBalling Pool\"] > p")).click();
                Thread.sleep(500);
                getDriver().findElement(By.name("8934843Basic Eyeballing")).click();
                Thread.sleep(500);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/p")));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | Eyeballing Records");
        //TestUtils.assertSearchText("XPATH", "//div[2]/div/div/div/div/div/div/h4", "Eyeballing Pool");
        Thread.sleep(500);
    }

    @Test(groups = { "Regression" })
    public void cardsTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(500);

        TestUtils.testTitle("Pending Card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//eyeballing-main/div/div/div/div/div/div/div")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pendingCount")));
        String pendingCount = getDriver().findElement(By.id("pendingCount")).getText();
        if (pendingCount.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, pendingCount + " found");
        } else {
            testInfo.get().log(Status.INFO, "not found");
        }

        TestUtils.testTitle("Valid Card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/div/div/div[2]/div")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("validCount")));
        String validCount = getDriver().findElement(By.id("validCount")).getText();
        if (validCount.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, validCount + " found");
        } else {
            testInfo.get().log(Status.INFO, "not found");
        }

        TestUtils.testTitle("Invalid Card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[3]/div")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("invalidCount")));
        String invalidCount = getDriver().findElement(By.id("invalidCount")).getText();
        if (invalidCount.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, invalidCount + " found");
        } else {
            testInfo.get().log(Status.INFO, "not found");
        }

        TestUtils.testTitle("Unreg Card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[4]/div")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/div/div/h5")));
        String unreg = getDriver().findElement(By.xpath("//div[4]/div/div/h5")).getText();
        if (unreg.matches(".*\\d.*")) {
            testInfo.get().log(Status.INFO, unreg + " found");
        } else {
            testInfo.get().log(Status.INFO, "not found");
        }
    }

    public void statusCheck(String status) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Status Search("+ status +")");

        getDriver().findElement(By.name("kitTag")).clear();
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText(status);
        Thread.sleep(1500);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.scrollToElement("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]");
        TestUtils.assertSearchText("XPATH", "//div[@id='fast_rowId  ']/div[3]/div/div[2]", "Eyeballing status: "+status);
        Thread.sleep(500);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByMsisdnTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(1000);
        TestUtils.testTitle("Valid MSISDN Search: "+validMsisdn);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(validMsisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]")));
        TestUtils.assertSearchText("XPATH","//div[@id='fast_rowId  ']/div/div[2]/div", "Phone number: "+validMsisdn);
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByKitTagTest() throws InterruptedException, IOException, ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(5000);
        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));

        TestUtils.testTitle("Valid Kit Tag Search: "+validKitTag);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("kitTag")).clear();
        getDriver().findElement(By.name("kitTag")).sendKeys(validKitTag);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));
        Thread.sleep(3000);
        getDriver().findElement(By.xpath("//button[@id='searchBtn']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div[3]")));
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[8]/label/span")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[2]/div/ul/li[3]/a")).click();
        TestUtils.scrollToElement("XPATH", "//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr[6]/td/b");
        TestUtils.assertSearchText("XPATH","//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr[6]/td[2]/div", validKitTag);
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByStatusTest() throws InterruptedException {
        getDriver().navigate().refresh();
        Thread.sleep(500);

        //Test Valid Status
        statusCheck("Valid");

        //Test Invalid Status
        statusCheck("Invalid");

        //Test Unreg Status
        statusCheck("Unreg");

        //Test Pending Status
        statusCheck("Pending");

        TestUtils.scrollToElement("XPATH", "//select");
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByRegistrationCenterTest(String testEnv) throws InterruptedException, IOException, ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(500);

        TestUtils.testTitle("Search Registration Centre: "+registrationCenter);
        //Reset the dropdown
        Select dropdown1 = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown1.selectByVisibleText("Select Eyeballing Status");
        TestUtils.scrollToElement("ID", "advancedBtn");
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
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void searchByDateTest(String testEnv) throws InterruptedException, IOException, ParseException, java.text.ParseException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().navigate().refresh();
        Thread.sleep(500);

        TestUtils.testTitle("Search by Start Date: "+startDate);
        TestUtils.scrollToElement("ID", "advancedBtn");
        getDriver().findElement(By.id("advancedBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[5]")));
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(startDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[3]")));
        getDriver().findElement(By.xpath("//div[8]/label/span")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
        Thread.sleep(500);
        TestUtils.scrollToElement("XPATH", "//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div");
        String table_Date = getDriver().findElement(By.xpath("//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div")).getText();
        TestUtils.convertDate(table_Date);
        Thread.sleep(500);
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        Thread.sleep(500);


        TestUtils.testTitle("Search by End Date: "+endDate);
        TestUtils.scrollToElement("XPATH", "(//input[@type='text'])[6]");
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys(endDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Enrollment Signature')]")));
        getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div");
        String table_Date2 = getDriver().findElement(By.xpath("//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div")).getText();
        TestUtils.convertDate(table_Date2);
        Thread.sleep(500);

        TestUtils.testTitle("Search by Date Range: "+ startDate + " and " + endDate);
        TestUtils.scrollToElement("XPATH", "(//input[@type='text'])[5]");
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(startDate);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
        Thread.sleep(500);
        TestUtils.scrollToElement("XPATH", "//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div");
        String table_Date3 = getDriver().findElement(By.xpath("//div[@id='enrollmentSignature  flat.bfpSyncLogPk  ']/div/div/div/table/tbody/tr/td[2]/div")).getText();
        String newDate = TestUtils.convertDate(table_Date3);
        TestUtils.checkDateBoundary(startDate, endDate, newDate);
        Thread.sleep(500);
        getDriver().findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        getDriver().findElement(By.xpath("(//input[@type='text'])[6]")).clear();
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void detailedEyeballingTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("msisdn")));
        Thread.sleep(2000);
        TestUtils.testTitle("Filter by Valid MSISDN:"+validMsisdn2);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(validMsisdn2);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.testTitle("Toggle fast Eyeballing");
        getDriver().findElement(By.xpath("//div[8]/label/span")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//div[@id='detail_rowId  ']/div/div[2]/div/div/div/span", "Registration Information");

        TestUtils.testTitle("Assert Basic Information of Registration");
        Assertion.assertBasicDetails();
        Thread.sleep(500);

        TestUtils.testTitle("Assert Biometric Details of Registration");
        getDriver().findElement(By.xpath("//a[contains(text(),'Biometric Details')]")).click();
        Assertion.assertBiometricDetails();

        TestUtils.testTitle("View Finger print images ");
        TestUtils.scrollToElement("XPATH", "//div[2]/div/div/div[2]/p");
        Assertion.viewFingerPrints();

        TestUtils.testTitle("Verify Enrolment Signature");
        TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Enrollment Signature')]");
        getDriver().findElement(By.xpath("//a[contains(text(),'Enrollment Signature')]")).click();
        TestUtils.testTitle("Assert Enrolment Signature of Registration");
        Assertion.assertEnrollmentSignature();
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//div[2]/div/ul/li[4]/a")).click();
        TestUtils.testTitle("Assert Company Details of Registration");
        Assertion.assertCompanyDetails();
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//div[2]/div/ul/li[5]/a")).click();
        TestUtils.testTitle("Assert Passport Details of Registration");
        Assertion.assertPassportDetails();
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//div[2]/div/ul/li[6]/a")).click();
        TestUtils.testTitle("Assert Documents of Registration");
        Assertion.assertDocumentDetails();
    }


    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void viewOtherUploadsTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(validMsisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(500);
        TestUtils.scrollToElement("XPATH", "//div[3]/button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/button")));
        getDriver().findElement(By.xpath("//div[3]/button")).click();

        TestUtils.testTitle("Verify if Image is displayed");
        Thread.sleep(3000);
        TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.pull-left", "View Other uploads");
        Assertion.imageDisplayViewDetails();
        getDriver().findElement(By.xpath("//div[2]/div[3]/button")).click();
        Thread.sleep(500);
    }


    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void confirmEyeballingTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(2000);
        getDriver().navigate().refresh();
        Thread.sleep(4000);
        TestUtils.testTitle("Eyeballing Confirmation Test");
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select")));
        dropdown.selectByVisibleText("Pending");
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[8]/label/span")).click();
        Thread.sleep(1000);
        TestUtils.scrollToElement("XPATH", "//div/select");
        TestUtils.assertSearchText("XPATH", "//div[2]/form/div/div", "Select Eyeballing Status:");
        TestUtils.assertSearchText("XPATH", "//form/div/div[2]", "Select Infraction:");
        Select selectByStatus = new Select(getDriver().findElement(By.xpath("//div/select")));
        selectByStatus.selectByVisibleText("Valid");
        Select selectInfraction = new Select(getDriver().findElement(By.xpath("//select[2]")));
        selectInfraction.selectByVisibleText("Gender");
        getDriver().findElement(By.xpath("(//button[@type='button'])[11]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH", "//h2", "Do you want to confirm?");
        getDriver().findElement(By.xpath("//div/div[2]/button[2]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
    }

    @Parameters({ "testEnv" })
    @Test(groups = { "Regression" })
    public void fastEyeballingTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        Thread.sleep(5000);
        if(!getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div[2]")).isDisplayed()){
            getDriver().findElement(By.xpath("//div[7]/label/span")).click();
            Thread.sleep(500);
        }
        TestUtils.testTitle("Confirm Subscriber details on Fast eyeballing search by MSISDN: "+validMsisdn);
        TestUtils.assertSearchText("XPATH", "//h4[contains(text(),'Eyeballing Pool')]", "Eyeballing Pool");
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(validMsisdn);
        Thread.sleep(3000);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        Assertion.assertFastEyeballingDetails();

        TestUtils.testTitle("Confirm Subscriber's Portrait on Fast Eyeballing Search by MSISDN: "+validMsisdn);
        Assertion.portraitDisplay();

    }
}
