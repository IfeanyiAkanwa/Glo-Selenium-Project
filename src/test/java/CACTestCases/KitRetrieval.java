package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

public class KitRetrieval extends TestBase {
	
	@Test(groups = { "Regression" })
	public void navigateToKitRetrievalTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    Thread.sleep(500);
	    try {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"223431152Kit Retrieval\"]");
			getDriver().findElement(By.cssSelector("a[name=\"223431152Kit Retrieval\"]")).click();
			Thread.sleep(500);
		} catch (Exception e) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"12877296644Retrieved Kits\"]");
			getDriver().findElement(By.cssSelector("a[name=\"12877296644Retrieved Kits\"]")).click();
			Thread.sleep(500);
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Retrieved Kits");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Retrieved Kits");
		
	}
	
	@Test (groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("retrievedKitsTable_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String pageSize = "Change page size to: 50";
		Markup b = MarkupHelper.createLabel(pageSize, ExtentColor.BLUE);
		testInfo.get().info(b);
		Thread.sleep(500);
		int rowCount = getDriver().findElements(By.xpath("//table[@id='retrievedKitsTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	public void viewDetail() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		TestUtils.clickElement("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td[7]/div/a/i");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//*[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.modal-title.font-weight-bold.text-secondary")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.modal-title.font-weight-bold.text-secondary", "Kit Details");
		Assertion.assertKitRetrievalDetail();
		
		// Click close button
		getDriver().findElement(By.cssSelector("div.modal-footer > button.btn.btn-link")).click();
		Thread.sleep(1000);
	}
	
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("kitRetrieval");

		String kitTag = (String) envs.get("kitTag");

		String kitTagFilter = "Filter by kit tag: " + kitTag;
		Markup m = MarkupHelper.createLabel(kitTagFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchKitTag")).clear();
		getDriver().findElement(By.name("searchKitTag")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		try {
		TestUtils.assertSearchText("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td[2]", kitTag);
		}
		catch(Exception e) {
			TestUtils.assertSearchText("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td", "No data available in table");

		}
		
	}
	
	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByAssignedAgentTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("kitRetrieval");

		String assignedAgent = (String) envs.get("assignedAgent");

		String agentFilter = "Filter by assigned agent: " + assignedAgent;
		Markup m = MarkupHelper.createLabel(agentFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("searchKitTag")).clear();
		getDriver().findElement(By.name("searchAssignedAgent")).clear();
		getDriver().findElement(By.name("searchAssignedAgent")).sendKeys(assignedAgent);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td[4]", assignedAgent);
		
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("kitRetrieval");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate+ " and "+endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		getDriver().findElement(By.name("searchKitTag")).clear();
		getDriver().findElement(By.name("searchAssignedAgent")).clear();
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='retrievedKitsTable']/tbody/tr/td[5]")).getText();
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date);

	}

	@Parameters ({"testEnv"})
	@Test(groups = { "Regression" })
	public void searchByReasonTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
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
;
		JSONObject envs = (JSONObject) config.get("kitRetrieval");

		String reason1 = (String) envs.get("reason1");
		String reason2 = (String) envs.get("reason2");
		
		getDriver().findElement(By.name("searchKitTag")).clear();
		getDriver().findElement(By.name("searchAssignedAgent")).clear();
		getDriver().findElement(By.id("startDate")).clear();
		getDriver().findElement(By.id("endDate")).clear();
		if (!getDriver().findElement(By.name("startDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}

		// Reason 1
		String reasonFilter = "Filter by reason: "+reason1;
		Markup m = MarkupHelper.createLabel(reasonFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(reason1);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td[6]", reason1);
		
		// Reason 2
		String reason2Filter = "Filter by reason: "+reason2;
		Markup r = MarkupHelper.createLabel(reason2Filter, ExtentColor.BLUE);
		testInfo.get().info(r);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(reason2);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='retrievedKitsTable']/tbody/tr/td[6]", reason2);
		
	}
}
