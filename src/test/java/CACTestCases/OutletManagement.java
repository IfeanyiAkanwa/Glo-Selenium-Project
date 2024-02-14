package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class OutletManagement extends TestBase {
	private static String vtuNumber = TestUtils.generatePhoneNumber();
	private static String ownerNumber = TestUtils.generatePhoneNumber();
	private static String newOutLetName = "QA test "+ System.currentTimeMillis();
	private StringBuffer verificationErrors = new StringBuffer();

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "outletTable");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "name");
	}

	@Test(groups = { "Regression" })
	  public void navigateToOutletManagementTest() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Outlet Management");
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1290Outlet Management\"] > p");
	    getDriver().findElement(By.cssSelector("a[name=\"1290Outlet Management\"] > p")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		 TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Outlet Management");
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByOutletNameTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("outlet");

		String outletName = (String) envs.get("outletName");
		TestUtils.testTitle("Filter by outlet name: " + outletName);
		getDriver().findElement(By.id("name")).clear();
	    getDriver().findElement(By.id("name")).sendKeys(outletName);  
	    Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
	    getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[2]", outletName);
	  	
	}
	
	@Test(groups = { "Regression" })
	public void searchByOutletTypeTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		getDriver().findElement(By.id("name")).clear();
		if (!getDriver().findElement(By.name("outletType")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		
		// GLOWORLD
		TestUtils.testTitle("Filter by outlet type: GLOWORLD");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("GLOWORLD");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[5]", "GLOWORLD");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// GLOZONE
		TestUtils.testTitle("Filter by outlet type: GLOZONE");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("GLOZONE");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[5]", "GLOZONE");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		
		// DEALERSHIP
		TestUtils.testTitle("Filter by outlet type: DEALERSHIP");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("DEALERSHIP");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[5]", "DEALERSHIP");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}

		// FREELANCE
		TestUtils.testTitle("Filter by outlet type: FREELANCE");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("FREELANCE");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[5]", "FREELANCE");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// AGENCY
		TestUtils.testTitle("Filter by outlet type: AGENCY");
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("AGENCY");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[5]", "AGENCY");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	}
	
	@Test(groups = { "Regression" })
	public void searchByStatusTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		if (!getDriver().findElement(By.name("outletType")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("name")).clear();
		
		// Inactive
		TestUtils.testTitle("Filter by Outlet Status: Inactive");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Inactive");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[6]/span", "INACTIVE");
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
		
		// Active
		TestUtils.testTitle("Filter by Outlet Status: Active");
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("Active");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[6]/span", "ACTIVE");
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByDealerViewDetailTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("outlet");

		String dealerName = (String) envs.get("dealerName");

		TestUtils.testTitle("Filter by dealer name: " + dealerName);
		if (!getDriver().findElement(By.name("outletType")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		getDriver().findElement(By.xpath("//table[@id='outletTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("vd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "View Outlet Details");
		TestUtils.scrollToElement("ID", "dealer-name");
		TestUtils.assertSearchText("ID", "dealer-name", dealerName);
		
		String outletName = getDriver().findElement(By.id("outlet-name")).getText();
		String dealerDetail =  outletName+": full details";
		TestUtils.testTitle(dealerDetail);
		Assertion.assertOutletDetail();
		
		// Click close button
		getDriver().findElement(By.cssSelector("button.btn.btn-link.pull-left.p")).click();
		Thread.sleep(1000);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void deactivateActivateOutletTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("outletType")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		searchByOutletNameTest(testEnv);
		action();
		searchByOutletNameTest(testEnv);
		action();
	}
	
	@Test(groups = { "Regression" })
	@Parameters({"testEnv", "server", "downloadPath"})
	public void addNewOutletTest(String testEnv, String server, String downloadPath) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("outlet");

		String dealerName = (String) envs.get("dealerName");
		TestUtils.clickElement("ID","add_outlet_button");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'Outlet creation')]")).click();
		Thread.sleep(500);
		TestUtils.testTitle("Create New Outlet");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("XPATH", "//div[4]/div/div/div/div/div/div/h4", "Create New Outlet");
		
		TestUtils.testTitle("Submit empty form");
		getDriver().findElement(By.cssSelector("button.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(500);
		Assertion.assertErrorValidationOutletManagementCACAdmin();
		
		TestUtils.scrollToElement("ID", "outletName");
		TestUtils.testTitle("Complete form and upload invalid image");
		TestUtils.uploadFile(By.id("outletLogo"), "blacklist_template.xls");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kv-avatar-errors-1")));
		String expectedText = getDriver().findElement(By.id("kv-avatar-errors-1")).getText();
		String requiredText = expectedText.substring(2);
		String value = "Invalid extension for file \"blacklist_template.xls\". Only \"jpg, png, gif\" files are supported.";

		try {
			Assert.assertEquals(requiredText, value);
			testInfo.get().log(Status.INFO, value + " found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error(value + " not found");
			testInfo.get().error(verificationErrorString);
		}
		getDriver().findElement(By.cssSelector("button.close.kv-error-close > span")).click();
		
		TestUtils.uploadFile(By.id("outletLogo"), "image2.jpg");
		Thread.sleep(1000);
		TestUtils.testTitle("Fill form and click save button");
		getDriver().findElement(By.name("outletName")).clear();
	    getDriver().findElement(By.name("outletName")).sendKeys(newOutLetName);
	    
	    getDriver().findElement(By.id("outletCenterId")).clear();
	    getDriver().findElement(By.id("outletCenterId")).sendKeys("36254376"); 
	    
	    getDriver().findElement(By.name("ownerName")).clear();
	    getDriver().findElement(By.name("ownerName")).sendKeys("New Dpt "+ newOutLetName); 
	    
	    getDriver().findElement(By.id("address")).clear();
	    getDriver().findElement(By.id("address")).sendKeys("C & I Leasing Dr, Lekki Phase I, Lagos, Nigeria"); 
	    Thread.sleep(1000);
	    getDriver().findElement(By.id("address")).sendKeys(Keys.ENTER);
	    Thread.sleep(2000);
	    String lat = getDriver().findElement(By.id("lat")).getAttribute("value");
	    String lng = getDriver().findElement(By.id("lng")).getAttribute("value");
	    
	    String lat2 = getDriver().findElement(By.id("lat")).getText();
	    String lng2 = getDriver().findElement(By.id("lng")).getText();
	    
	    testInfo.get().info("Auto-populated latitude and longitude "+lat+"  "+lng);
	    testInfo.get().info("Auto-populated latitude2 and longitude2 "+lat2+"  "+lng2);
	    
	    getDriver().findElement(By.id("updateCoordinates")).click();
	    
	    getDriver().findElement(By.id("lat")).clear();
		getDriver().findElement(By.id("lat")).sendKeys("-234");
		getDriver().findElement(By.id("lng")).clear();
		getDriver().findElement(By.id("lng")).sendKeys("10.123");
		
	    getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("GLOZONE");
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
	    
		TestUtils.scrollToElement("NAME", "vtu");
	    getDriver().findElement(By.name("vtu")).clear();
	    getDriver().findElement(By.name("vtu")).sendKeys("08047589965"); 
	    
	    getDriver().findElement(By.name("ownerPhone")).clear();
	    getDriver().findElement(By.name("ownerPhone")).sendKeys("08074512245"); 
	    
	    Thread.sleep(1000);
	    getDriver().findElement(By.xpath("//div[11]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		
		getDriver().findElement(By.cssSelector("button.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Successfully Created");
	    getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Filter by newly created outlet name: " + newOutLetName);
		getDriver().findElement(By.id("name")).clear();
	    getDriver().findElement(By.id("name")).sendKeys(newOutLetName);  
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='outletTable']/tbody/tr/td[2]", newOutLetName);
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	  public void updateOutletDetailsTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException{
		
		navigateToOutletManagementTest();
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("outlet");

		String dealerName = (String) envs.get("dealerName");
		String outletName = (String) envs.get("outletName");
		
		TestUtils.testTitle("Update outlet name: " + outletName+ " and dealer: "+dealerName);
		getDriver().findElement(By.id("name")).clear();
		getDriver().findElement(By.id("name")).sendKeys(outletName);
		Thread.sleep(2000);
		//getDriver().findElement(By.id("searchButton")).click();
		getDriver().findElement(By.xpath("//i[contains(text(),'search')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		getDriver().findElement(By.xpath("//table[@id='outletTable']/tbody/tr/td[7]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("vd")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.scrollToElement("ID", "editOutlet");
		
		// Click edit button
		getDriver().findElement(By.id("editOutlet")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.pt-3", "Update Existing Outlet");
		
		String address = getDriver().findElement(By.id("address")).getAttribute("value");
		
		// fill form
		TestUtils.testTitle("Clear form and click save button");
		TestUtils.scrollToElement("NAME", "vtu");
		getDriver().findElement(By.id("clear")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("button.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(500);
		Assertion.assertErrorValidationOutletManagementCACAdmin();
		TestUtils.scrollToElement("ID", "outletName");
		
	    getDriver().findElement(By.name("outletName")).sendKeys(outletName); 
	    
	    getDriver().findElement(By.id("outletCenterId")).clear();
	    getDriver().findElement(By.id("outletCenterId")).sendKeys("36254376"); 
	    
	    getDriver().findElement(By.name("ownerName")).clear();
	    getDriver().findElement(By.name("ownerName")).sendKeys("TestUpdate "+ System.currentTimeMillis()); 
	    
	    getDriver().findElement(By.id("address")).clear();
	    getDriver().findElement(By.id("address")).sendKeys(address); 
	    Thread.sleep(2000);
	    getDriver().findElement(By.cssSelector("span.type")).click();
	    
	    getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys("GLOWORLD");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
	    
		TestUtils.scrollToElement("NAME", "vtu");
	    getDriver().findElement(By.name("vtu")).clear();
	    getDriver().findElement(By.name("vtu")).sendKeys(vtuNumber); 
	    
	    getDriver().findElement(By.name("ownerPhone")).clear();
	    getDriver().findElement(By.name("ownerPhone")).sendKeys(ownerNumber); 
	    Thread.sleep(1000);
	    getDriver().findElement(By.xpath("//div[11]/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.cssSelector("button.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(500);
		TestUtils.testTitle("Fill form and click save button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure that you want to proceed with this process?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
	    TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Successfully Updated");
	    getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	}
	
	@Test(groups = { "Regression" })
	@Parameters({"server", "downloadPath"})
	public void bulkOutletCreation(String server, String downloadPath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		navigateToOutletManagementTest();
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			Thread.sleep(1000);
			}
		TestUtils.clickElement("ID","add_outlet_button");
		Thread.sleep(500);
		TestUtils.clickElement("ID","bulkCreateLink");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Bulk Outlet Creation");
		
    	String pic = "image2.jpg";
    	String xlsFile = "blacklist_template.xls";
    	String xlsxFile = "SIMROP_user.xlsx";
		
		// No file
		TestUtils.testTitle("Submit with empty file");
		getDriver().findElement(By.id("bocBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "No file selected");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		
		// wrong file extension jpg
		TestUtils.testTitle("Submit with wrong file format "+pic);
		getDriver().findElement(By.id("upload")).clear();
		TestUtils.uploadFile(By.id("upload"), pic);
		getDriver().findElement(By.id("bocBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Invalid file Provided. Supported file extension is .xls");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		
		// xls extension
		TestUtils.testTitle("Submit with valid file .xls extension but another file name: " + xlsFile);
		getDriver().findElement(By.id("upload")).clear();
		TestUtils.uploadFile(By.id("upload"), xlsFile);
		getDriver().findElement(By.id("bocBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Bulk Outlet Creation");
		testInfo.get().info("successful upload");
		
		// xlsx extension
		TestUtils.testTitle("Submit with valid file xlsx extension but another file name: " + xlsxFile);
		getDriver().findElement(By.id("upload")).clear();
		TestUtils.uploadFile(By.id("upload"), xlsxFile);
		getDriver().findElement(By.id("bocBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Bulk Outlet Creation");
		testInfo.get().info("successful upload");
		
	}
	
	public void action() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String status = getDriver().findElement(By.xpath("//table[@id='outletTable']/tbody/tr/td[6]/span")).getText();
		testInfo.get().info("Current Status: "+status);
		WebElement button;
		String message,action;
		if (status.equalsIgnoreCase("ACTIVE")) {
			getDriver().findElement(By.xpath("//table[@id='outletTable']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			button = getDriver().findElement(By.xpath("//td[7]/div/ul/li[2]/a"));
			message = "Outlet has been successfully deactivated";
			action = "Deactivate outlet";
			Markup c = MarkupHelper.createLabel(action, ExtentColor.BLUE);
			testInfo.get().info(c);
			Thread.sleep(500);
			button.click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", message);
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		} else {
			getDriver().findElement(By.xpath("//table[@id='outletTable']/tbody/tr/td[7]/div/a/i")).click();
			Thread.sleep(500);
			button = getDriver().findElement(By.linkText("activate"));
			message = "Outlet has been successfully activated";
			action = "Activate outlet";
			Markup c = MarkupHelper.createLabel(action, ExtentColor.BLUE);
			testInfo.get().info(c);
			Thread.sleep(500);
			button.click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
			TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", message);
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
			wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
					"Processing..."));
		}
		

	}

	@Test (groups = { "Regression" })
	public void showPageSize() throws InterruptedException {

		navigateToOutletManagementTest();

		//Download PDF
		getDriver().findElement(By.xpath("//div[@id='outletTable_wrapper']/div[2]/a")).click();
		Thread.sleep(500);

		//Download EXCEL
		getDriver().findElement(By.xpath("//div[@id='outletTable_wrapper']/div[2]/a[2]")).click();
		Thread.sleep(500);

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("outletTable_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));

		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='outletTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}

	@Test (groups = { "Regression" })
	public void selectVisibleColumns() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Select Visible Columns')]")));
		getDriver().findElement(By.xpath("//a[contains(text(),'Select Visible Columns')]")).click();
		Thread.sleep(500);
		// Outlet name Column
		TestUtils.testTitle("Remove Outlet name Column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Outlet name")) {
			TestUtils.assertSearchText("XPATH", "//th[2]", "Outlet name");

		}else {
			testInfo.get().info("Outlet name column removed");

		}
		TestUtils.testTitle("Add Outlet name  Column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[2]", "Outlet name");
		Thread.sleep(500);

		//Owner name Column
		TestUtils.testTitle("Remove Owner name Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Owner name")) {
			TestUtils.assertSearchText("XPATH", "//th[3]", "Owner name");

		}else {
			testInfo.get().info("Owner name column removed");

		}
		TestUtils.testTitle("Add Owner name Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[3]", "Owner name");
		Thread.sleep(500);

		// Phone Number Column
		TestUtils.testTitle("Remove Phone Number Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		if (getDriver().findElement(By.xpath("//th[4]")).getText().contains("Phone Number")) {
			TestUtils.assertSearchText("XPATH", "//th[4]", "Phone Number");

		}else {
			testInfo.get().info("Phone Number column removed");

		}
		TestUtils.testTitle("Add Phone Number Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[4]", "Phone Number");
		Thread.sleep(500);

		// Outlet Type Column
		TestUtils.testTitle("Remove Outlet Type Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		if (getDriver().findElement(By.xpath("//th[5]")).getText().contains("Outlet Type")) {
			TestUtils.assertSearchText("XPATH", "//th[5]", "Outlet Type");

		}else {
			testInfo.get().info("Outlet Type column removed");

		}
		TestUtils.testTitle("Add Outlet Type Column");
		getDriver().findElement(By.xpath("//a[5]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[5]", "Outlet Type");
		Thread.sleep(500);

		// Status Column
		TestUtils.testTitle("Remove Status Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		if (getDriver().findElement(By.xpath("//th[6]")).getText().contains("Status")) {
			TestUtils.assertSearchText("XPATH", "//th[6]", "Status");

		}else {
			testInfo.get().info("Status column removed");

		}
		TestUtils.testTitle("Add Status Column");
		getDriver().findElement(By.xpath("//a[6]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[6]", "Status");
		Thread.sleep(500);


		// Actions Column
		TestUtils.testTitle("Remove Actions Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		try {
			TestUtils.assertSearchText("XPATH", "//th[7]", "Action");

		} catch (Exception e) {
			testInfo.get().info("Action column removed");
		}
		TestUtils.testTitle("Add Action Column");
		getDriver().findElement(By.xpath("//a[7]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[7]", "Actions");
	}

}
