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

public class DeviceRequisitionDeviceManagement extends TestBase {

	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "requisitionApprovalTable");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "sStartDate");
	}

	@Test(groups = { "Regression" })
	public void navigateToDeviceRequisitionDeviceManagement() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"195532450Device Management\"] > p");
		getDriver().findElement(By.cssSelector("a[name=\"195532450Device Management\"] > p")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("195532384Device Requisition")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Device Requisition Approval");
	}

	@Test(groups = { "Regression" })
	public void viewDetailsTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		// View detail modal
		getDriver().findElement(By.xpath("//table[@id='requisitionApprovalTable']/tbody/tr/td[9]/div/a/i")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		TestUtils.assertSearchText("ID", "myModalLabel", "Device Requisition Approval");
		Assertion.assertRequisitionDetail();
		getDriver().findElement(By.cssSelector("button.close.untrackForm.all")).click();
		Thread.sleep(1000);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDateTest(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("requisition");

		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");

		String dateFilter = "Filter by date range: " + startDate + " and " + endDate;
		Markup m = MarkupHelper.createLabel(dateFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.name("sStartDate")).clear();
		getDriver().findElement(By.name("sStartDate")).sendKeys(startDate);
		getDriver().findElement(By.name("sEndDate")).clear();
		getDriver().findElement(By.name("sEndDate")).sendKeys(endDate);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='requisitionApprovalTable']/tbody/tr/td[3]"))
				.getText();
		testInfo.get().info("Date returned " + table_Date);
		TestUtils.checkDateyYMDBoundary(startDate, endDate, table_Date);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
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
		JSONObject envs = (JSONObject) config.get("requisition");

		String dealerName = (String) envs.get("dealerName");

		String dealerFilter = "Filter by dealer name: " + dealerName;
		Markup m = MarkupHelper.createLabel(dealerFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		getDriver().findElement(By.id("sStartDate")).clear();
		getDriver().findElement(By.id("sEndDate")).clear();
		if (!getDriver().findElement(By.name("sDealerPk")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionApprovalTable']/tbody/tr/td[4]", dealerName);

	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByStateLgaArea(String testEnv)
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
		JSONObject envs = (JSONObject) config.get("requisition");

		String state = (String) envs.get("state");
		String lga = (String) envs.get("lga");
		String area = (String) envs.get("area");

		String dealerFilter = "Filter by State: " + state + " LGA: " + lga + " and Area: " + area;
		Markup m = MarkupHelper.createLabel(dealerFilter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.name("sDealerPk")).isDisplayed()) {
			getDriver().findElement(By.id("toggle")).click();
		}
		Thread.sleep(500);

		// Search by State
		getDriver().findElement(By.xpath("//div[2]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionApprovalTable']/tbody/tr/td[5]", state);

		// Search by LGA
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(lga);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionApprovalTable']/tbody/tr/td[6]", lga);

		// Search by Area
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='requisitionApprovalTable']/tbody/tr/td[7]", area);

	}

}
