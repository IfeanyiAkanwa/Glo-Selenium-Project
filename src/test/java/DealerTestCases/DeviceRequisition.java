package DealerTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class DeviceRequisition extends TestBase {
	
	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "requisitionTable_info");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "searchBtn");
	}
	
	@Test(groups = { "Regression" })
	public void navigateToDeviceRequisition() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    Thread.sleep(500);
	    try {
			TestUtils.scrollToElement("NAME", "211672160Device Requisition");
			getDriver().findElement(By.name("211672160Device Requisition")).click();
		} catch (Exception e) {
			TestUtils.scrollToElement("NAME", "195532385Device Requisition");
		    getDriver().findElement(By.name("195532385Device Requisition")).click();
		}
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    Assert.assertEquals(getDriver().getTitle(), "SIMROP | Device Requisition");
	    TestUtils.assertSearchText("XPATH", "//h4", "Device Requisition");
	}
	
	@Test(groups = { "Regression" })
	public void viewDetails() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='requisitionTable']/tbody/tr/td[8]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[@id='myModalLabel'])[2]")));
		TestUtils.assertSearchText("XPATH", "(//h4[@id='myModalLabel'])[2]", "Device Requisition");
		Assertion.assertDeviceRequisitionDetailsDealer();
		TestUtils.scrollToElement("XPATH", "(//button[@type='button'])[6]");
		TestUtils.clickElement("XPATH", "(//button[@type='button'])[6]");
	}
	
	@Test(groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("requisitionTable_length"))).selectByVisibleText("50");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='requisitionTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDate(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("deviceRequisition");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate+ " and "+endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.id("endDate")).clear();
		getDriver().findElement(By.id("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='requisitionTable']/tbody/tr[2]/td[3]")).getText();
		table_Date = table_Date.substring(0, 10);
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateyYMDBoundary(startDate, endDate, table_Date);
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByState(String testEnv) throws Exception {

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
		JSONObject envs = (JSONObject) config.get("deviceRequisition");

		String state = (String) envs.get("state");
		
		String stateFilter = "Filter by State: " + state;
		Markup m = MarkupHelper.createLabel(stateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionTable']/tbody/tr/td[4]", state);
		Thread.sleep(500);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByLGA(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("deviceRequisition");

		String lga = (String) envs.get("lga");
		
		String lgaFilter = "Filter by LGA: " + lga;
		Markup m = MarkupHelper.createLabel(lgaFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);		
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(lga);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionTable']/tbody/tr/td[5]", lga);
		Thread.sleep(500);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByArea(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("deviceRequisition");

		String area = (String) envs.get("area");
		
		String areaFilter = "Filter by Area: " + area;
		Markup m = MarkupHelper.createLabel(areaFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionTable']/tbody/tr/td[6]", area);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		Thread.sleep(500);
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void newDeviceRequisition(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("deviceRequisition");
		
		String state = (String) envs.get("state");
		String area = (String) envs.get("area");
		String lga = (String) envs.get("lga");
		String count = (String) envs.get("count");
		String reason = (String) envs.get("reason");
		
		TestUtils.clickElement("XPATH", "(//button[@type='submit'])[2]");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "Device Requisition Request");
		
		// Check for cancel button
		getDriver().findElement(By.xpath("//button[2]")).click();
		Thread.sleep(500);
		
		// Check for empty fields
		TestUtils.clickElement("XPATH", "(//button[@type='submit'])[2]");
		Thread.sleep(500);
		String submitFilter = "Try to submit requisition request without filling the form";
		Markup m = MarkupHelper.createLabel(submitFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("submitRequisition")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//label[@id='state-error']/p", "Please select state");
		TestUtils.assertSearchText("XPATH", "//label[@id='lga-error']/p", "Please select lga");
		TestUtils.assertSearchText("XPATH", "//label[@id='areaofDep-error']/p", "Please select area of deployment");
		TestUtils.assertSearchText("XPATH", "//label[@id='count-error']/p", "This field is required");
		TestUtils.assertSearchText("XPATH", "//label[@id='reason-error']/p", "Enter a reason");
		Thread.sleep(500);
		
		// fill requisition form
		String sFilter = "Fill requisition request form";
		Markup e = MarkupHelper.createLabel(sFilter, ExtentColor.BLUE);
		testInfo.get().info(e);
		Thread.sleep(500);
		
		//Select State Of Deployment
		scrollUp();
		getDriver().findElement(By.xpath("//div[2]/div/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);;
		
		//Select LGA of Deployment
		getDriver().findElement(By.xpath("//div[2]/div/div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(lga);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		//Select Area of Deployment
		getDriver().findElement(By.xpath("//div[2]/div/div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		//Select Count
		getDriver().findElement(By.id("count")).clear();
		getDriver().findElement(By.id("count")).sendKeys(count);
		Thread.sleep(500);
		
		//Select Reason
		getDriver().findElement(By.id("reason")).clear();
		getDriver().findElement(By.id("reason")).sendKeys(reason);
		Thread.sleep(500);
		getDriver().findElement(By.id("submitRequisition")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h2", "Device Requisition Request");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content",
				"Are you sure that you want to proceed with this request?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		/*//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Device requistion was successffully completed')]")));
		TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Device requistion was successffully completed')]", "Device requistion was successffully completed");
		Thread.sleep(500);*/
		
	}
	
}
