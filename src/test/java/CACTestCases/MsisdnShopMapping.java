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

import static util.TestUtils.generatePhoneNumber;

public class MsisdnShopMapping extends TestBase {

    private String msisdn;
    private String simSerial;
    private String outlet;
    private String outlet2;


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
        JSONObject envs = (JSONObject) config.get("shopMapping");

        msisdn = (String) envs.get("msisdn");
        simSerial = (String) envs.get("simSerial");
        outlet = (String) envs.get("outlet");
        outlet2 = (String) envs.get("outlet2");
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void navigateToShopMapping(String testEnv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Navigate to MSISDN Shop Mapping Test");
        if (testEnv.equalsIgnoreCase("stagingData")) {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822954187MSISDN Mapping\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5822954187MSISDN Mapping\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                Thread.sleep(500);
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822954187MSISDN Mapping\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5822954187MSISDN Mapping\"]")).click();
                Thread.sleep(500);
            }
        }else {
            try {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822954187MSISDN Mapping\"] > p");
                getDriver().findElement(By.cssSelector("a[name=\"5822954187MSISDN Mapping\"] > p")).click();
                Thread.sleep(500);
            } catch (Exception e) {
                TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"5822954187MSISDN Mapping\"]");
                getDriver().findElement(By.cssSelector("a[name=\"5822954187MSISDN Mapping\"]")).click();
                Thread.sleep(500);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div/div/div/div/div/h4")));
        Assert.assertEquals(getDriver().getTitle(), "SIMROP | MSISDN Mapping");
        TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Shop MSISDN Mapping");
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByMsisdnTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search by MSISDN Test: " + msisdn);
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(msisdn);
        Thread.sleep(1500);
        //getDriver().findElement(By.id("searchBtn")).click();
        TestUtils.clickElement("ID", "searchBtn");
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[2]/div", msisdn);
    }


    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchBySimSerialTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Search by SIM Serial Test: " + simSerial);
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(simSerial);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[3]/div", simSerial);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByOutletTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Search by Outlet Test: "+ outlet);

        Select dropdown = new Select(getDriver().findElement(By.id("outlet")));
        dropdown.selectByVisibleText(outlet);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div", outlet);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void searchByOutletAndMsisdnTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        TestUtils.testTitle("Search by Outlet: "+ outlet +" and MSISDN: "+msisdn+" Test");
        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("msisdnOrSimSerial")));
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(msisdn);
        Select dropdown = new Select(getDriver().findElement(By.id("outlet")));
        dropdown.selectByVisibleText(outlet);
        Thread.sleep(1500);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[2]", msisdn);
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div", outlet);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void tableDetailsTest() throws InterruptedException {
        TestUtils.testTitle("Table Details Test");
        getDriver().findElement(By.id("searchBtn")).click();
        Thread.sleep(500);
        Assertion.assertShopMappingDetails();
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void mapMsisdnTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Map MSISDN Test");
        getDriver().navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msisdnMappingBtn")));
        TestUtils.clickElement("ID", "msisdnMappingBtn");
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//a[contains(text(),'Map MSISDN')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
        TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Map MSISDN");

        TestUtils.testTitle("Empty Sim Serial Test");

        //Map shop with empty Sim Serial
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(generatePhoneNumber());
        Select dropdown = new Select(getDriver().findElement(By.xpath("//div[@id='modal_field6']/div/select")));
        dropdown.selectByVisibleText(outlet);
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Sim serial must not be blank')]")));
        Thread.sleep(500);
        String errMsg = getDriver().findElement(By.xpath("//*[contains(text(),'Sim serial must not be blank')]")).getText();
        testInfo.get().info(errMsg);
        Thread.sleep(500);

        TestUtils.testTitle("Invalid MSISDN Test: &&&&&&%%%%4");

        //Map shop with Invalid MSISDN
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys("&&&&&&%%%%4()");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='modal_field5']/div/span")));
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//div[@id='modal_field5']/div/span", "Input a valid MSISDN ...");

        TestUtils.testTitle("Invalid Sim Serial Test");
        //Map shop with Invalid Sim Serial Test
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(generatePhoneNumber());
        getDriver().findElement(By.name("simSerial")).clear();
        getDriver().findElement(By.name("simSerial")).sendKeys(generatePhoneNumber());
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Invalid Sim serial. Must be 20 characters and must not have special characters')]")));
        Thread.sleep(500);
        String errMsg2 = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid Sim serial. Must be 20 characters and must not have special characters')]")).getText();
        testInfo.get().info(errMsg2);

        //Map shop with Already Mapped MSISDN
        TestUtils.testTitle("Already Mapped MSISDN: "+msisdn +" Test");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Invalid Sim serial. Must be 20 characters and must not have special characters')]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("msisdn")));
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(msisdn);
        Thread.sleep(500);
        getDriver().findElement(By.name("simSerial")).clear();
        getDriver().findElement(By.name("simSerial")).sendKeys(simSerial);
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'MSISDN has already been mapped to a shop')]")));
        Thread.sleep(500);
        String errMsg3 = getDriver().findElement(By.xpath("//*[contains(text(),'MSISDN has already been mapped to a shop')]")).getText();
        testInfo.get().info(errMsg3);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(text(),'MSISDN has already been mapped to a shop')]")));

        //Test the Cancel button
        TestUtils.testTitle("Cancel Button Test");
        getDriver().findElement(By.xpath("(//button[@type='button'])[12]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div/div/div/div/div/div/div/h4")));
        TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Shop MSISDN Mapping");

        //Map shop with Already Mapped Sim Serial
        TestUtils.testTitle("Already Mapped Sim Serial: "+simSerial+" Test");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msisdnMappingBtn")));
        Thread.sleep(500);
        getDriver().findElement(By.id("msisdnMappingBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.linkText("Map MSISDN")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
        TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Map MSISDN");
        getDriver().findElement(By.name("simSerial")).clear();
        getDriver().findElement(By.name("simSerial")).sendKeys(simSerial);
        Select dropdown2 = new Select(getDriver().findElement(By.xpath("//div[@id='modal_field6']/div/select")));
        dropdown2.selectByVisibleText(outlet);
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Sim serial has already been mapped to a shop')]")));
        Thread.sleep(500);
        String errMsg4 = getDriver().findElement(By.xpath("//*[contains(text(),'Sim serial has already been mapped to a shop')]")).getText();
        testInfo.get().info(errMsg4);

        //Generate Sim Serial and MSISDN
        String newSerial = TestUtils.generateSimSerial();
        String newMsisdn = generatePhoneNumber();

        //Map Shop with valid MSISDN and SIM Serial
        TestUtils.testTitle("Shop Mapping with new MSISDN: "+newMsisdn+" and Sim Serial: "+newSerial+" Test");
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("msisdn")).sendKeys(newMsisdn);
        getDriver().findElement(By.name("simSerial")).clear();
        getDriver().findElement(By.name("simSerial")).sendKeys(newSerial);
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Shop - MSISDN mapping was successful')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Shop - MSISDN mapping was successful')]", "Shop - MSISDN mapping was successful");
        Thread.sleep(500);
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(newMsisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[2]/div", newMsisdn);
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[3]/div", newSerial);
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div", outlet);

        //Map Shop with valid empty MSISDN and a valid SIM Serial
        newSerial = TestUtils.generateSimSerial();
        TestUtils.testTitle("Shop Mapping with only new Sim Serial: "+newSerial+" Test");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msisdnMappingBtn")));
        Thread.sleep(500);
        getDriver().findElement(By.id("msisdnMappingBtn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.linkText("Map MSISDN")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//modal-container/div/div/div/h4")));
        TestUtils.assertSearchText("XPATH", "//modal-container/div/div/div/h4", "Map MSISDN");
        getDriver().findElement(By.name("msisdn")).clear();
        getDriver().findElement(By.name("simSerial")).clear();
        getDriver().findElement(By.name("simSerial")).sendKeys(newSerial);
        Select dropdown3 = new Select(getDriver().findElement(By.xpath("//div[@id='modal_field6']/div/select")));
        dropdown3.selectByVisibleText(outlet);
        getDriver().findElement(By.xpath("(//button[@type='button'])[13]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Shop - MSISDN mapping was successful')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Shop - MSISDN mapping was successful')]", "Shop - MSISDN mapping was successful");
        Thread.sleep(500);
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(newSerial);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[2]", "N/A");
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[3]/div", newSerial);
        TestUtils.assertSearchText("XPATH", "//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div", outlet);
    }

    @Parameters("testEnv")
    @Test(groups = { "Regression" })
    public void changeShopTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        TestUtils.testTitle("Change Shop Test: "+msisdn);
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@id='actionBtn']/i")));

        //Log the values of the current record details before changing shop
        String currentOutlet = getDriver().findElement(By.xpath("//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div")).getText();
        Thread.sleep(500);
        String currentMsisdn = getDriver().findElement(By.xpath("//table[@id='msisdnMappingDetailTable']/tbody/tr/td[2]/div")).getText();
        String currentSimSerial = getDriver().findElement(By.xpath("//table[@id='msisdnMappingDetailTable']/tbody/tr/td[3]/div")).getText();
        testInfo.get().log(Status.INFO, "<b>Current Shop: </b>" + currentOutlet);
        testInfo.get().log(Status.INFO, "<b>Current MSISDN: </b>" + currentMsisdn);
        testInfo.get().log(Status.INFO, "<b>Current Sim Serial: </b>" + currentSimSerial);
        getDriver().findElement(By.xpath("//a[@id='actionBtn']/i")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Change Shop")));
        getDriver().findElement(By.linkText("Change Shop")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//modal-container/div/div/div/h4")));

        //Verify the Sim Serial and MSISDN on the Form modal is the same as that of the selected shop
        String msisdnOnForm = getDriver().findElement(By.name("msisdn")).getAttribute("value");
        if (msisdnOnForm.equalsIgnoreCase(msisdn)) {
            testInfo.get().log(Status.INFO, "<b>MSISDN displayed on change shop form: </b>" + msisdnOnForm);
        }else{
            testInfo.get().log(Status.ERROR, "<b>MSISDN displayed on change shop form: </b>" + msisdnOnForm);
        }
        String simSerialOnForm = getDriver().findElement(By.name("simSerial")).getAttribute("value");
        if (simSerialOnForm.equalsIgnoreCase(simSerial)) {
            testInfo.get().log(Status.INFO, "<b>Sim Serial displayed on change shop form: </b>" + simSerialOnForm);
        }else{
            testInfo.get().log(Status.ERROR, "<b>Sim Serial displayed on change shop form: </b>" + simSerialOnForm);
        }

        //Select new shop
        Select dropdown = new Select(getDriver().findElement(By.xpath("//div[@id='modal_field6']/div/select")));
        if(currentOutlet.equalsIgnoreCase(outlet)){
            dropdown.selectByVisibleText(outlet2);
        }else{
            dropdown.selectByVisibleText(outlet);
        }
        getDriver().findElement(By.xpath("//div[2]/div/button[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Shop change was successful')]")));
        TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Shop change was successful')]", "Shop change was successful");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("msisdnOrSimSerial")));

        //Search for the shop that was edited using MSISDN
        getDriver().findElement(By.name("msisdnOrSimSerial")).clear();
        getDriver().findElement(By.name("msisdnOrSimSerial")).sendKeys(msisdn);
        getDriver().findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));

        //Get value of the new shop
        String newShop = getDriver().findElement(By.xpath("//table[@id='msisdnMappingDetailTable']/tbody/tr/td[4]/div")).getText();

        //Compare previous shop name to the current shop name and  log
        if (!currentOutlet.equalsIgnoreCase(newShop)) {
            testInfo.get().log(Status.INFO, "<b>New Shop: </b>" + newShop);
        }else{
            testInfo.get().log(Status.ERROR, "<b>New Shop: </b>" + newShop +" Cannot be the same as the current shop");
        }

    }

}
