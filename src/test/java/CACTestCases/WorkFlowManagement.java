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

import com.aventstack.extentreports.Status;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class WorkFlowManagement extends TestBase {
	
private StringBuffer verificationErrors = new StringBuffer();
	
	
	@Test(groups = { "Regression" })
	public void navigateToWorkFlow() throws InterruptedException {
		TestUtils.testTitle("Navigate to Workflow Management");
		try {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1297Workflow Management\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"1297Workflow Management\"] > p")).click();
		} catch (Exception e) {
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"11153238262Workflow Management\"] > p");
			getDriver().findElement(By.cssSelector("a[name=\"11153238262Workflow Management\"] > p")).click();
		}
	    WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    TestUtils.assertSearchText("CSSSELECTOR", "p.px-1.pt-2.font-weight-bold.text-secondary", "Total Workflow");
		
	}
	
	@Test(groups = { "Regression" })
	  public void assertNameCountTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Assert Card Count");
		String totalValString = getDriver().findElement(By.id("total_settings")).getText();
		String ActiveValString = getDriver().findElement(By.id("total_active_settings")).getText();
		String InactiveValString = getDriver().findElement(By.id("total_inactive_settings")).getText();
		
		int totalVal = TestUtils.convertToInt(totalValString);
		int activeVal = TestUtils.convertToInt(ActiveValString);
		int inactiveVal = TestUtils.convertToInt(InactiveValString);
				
		int expectedTotalVal = activeVal + inactiveVal;
		
		try {
			Assert.assertEquals(expectedTotalVal,totalVal);
		    testInfo.get().log(Status.INFO, "Total Workflow ("+expectedTotalVal+") is equal to summation of total active ("+activeVal+") + total inactive ("+inactiveVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    }
		
		// Page size
		new Select(getDriver().findElement(By.name("workflowTable_length"))).selectByVisibleText("25");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 25");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='workflowTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of records returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByName(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
		JSONObject envs = (JSONObject) config.get("workflow");

		String name = (String) envs.get("name");
		TestUtils.testTitle("Filter by workflow name: " + name);
		getDriver().findElement(By.id("searchParamInput")).clear();
		getDriver().findElement(By.id("searchParamInput")).sendKeys(name);
		getDriver().findElement(By.id("workflowSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='workflowTable']/tbody/tr/td[4]", name);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByActivity(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
		JSONObject envs = (JSONObject) config.get("workflow");

		String activity = (String) envs.get("activity");
		TestUtils.testTitle("Filter by workflow activity: " + activity);
		getDriver().findElement(By.id("searchParamInput")).clear();
		getDriver().findElement(By.id("searchParamInput")).sendKeys(activity);
		getDriver().findElement(By.id("workflowSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='workflowTable']/tbody/tr/td[2]", activity);
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDescription(String testEnv) throws FileNotFoundException, IOException, ParseException, InterruptedException {
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
		JSONObject envs = (JSONObject) config.get("workflow");

		String description = (String) envs.get("description");
		TestUtils.testTitle("Filter by workflow activity: " + description);
		getDriver().findElement(By.id("searchParamInput")).clear();
		getDriver().findElement(By.id("searchParamInput")).sendKeys(description);
		getDriver().findElement(By.id("workflowSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='workflowTable']/tbody/tr/td[5]", description);
	}
	
	@Test(groups = { "Regression" })
	public void viewDetails() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().findElement(By.id("searchParamInput")).clear();
	    getDriver().findElement(By.id("workflowSearchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		getDriver().findElement(By.cssSelector("a.btn-icon.remove > i.material-icons")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//td[7]/div/ul/li/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalLabel")));
		Thread.sleep(1000);
		TestUtils.testTitle("View Workflow Details");
		Assertion.assertWorkflowDetail();
		
		//Close Modal
		getDriver().findElement(By.cssSelector("button.btn.btn-secondary.untrackForm")).click();
		Thread.sleep(1000);
		
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void editWorkflowOrder(String testEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		searchByDescription(testEnv);
		String order= getDriver().findElement(By.xpath("//table[@id='workflowTable']/tbody/tr/td[3]")).getText();
		TestUtils.testTitle("Edit workflow order: " + order + " to same 1");
		TestUtils.clickElement("CSSSELECTOR","a.btn-icon.remove > i.material-icons");
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//td[7]/div/ul/li[2]/a")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='editWorkflowModal']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='editWorkflowModal']/div/div/div/h4", "Edit Workflow");
		
		// Edit Order
		getDriver().findElement(By.id("editorder")).clear();
		getDriver().findElement(By.id("editorder")).sendKeys("1");
		
		// Click Edit Setting
		getDriver().findElement(By.id("editSettingBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to edit this workflow?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Congratulations, workflow was edited successfully')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Congratulations, workflow was edited successfully')]",
				"Congratulations, workflow was edited successfully");
		TestUtils.clickElement("CSSSELECTOR", "button.close > i.material-icons");
		//getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(500);
		
	}
	
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void addWorkFLow(String testEnv) throws InterruptedException, IOException, ParseException {
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
		JSONObject envs = (JSONObject) config.get("workflow");

		String addName = (String) envs.get("addName");
		String addRole = (String) envs.get("addRole");
		
		TestUtils.clickElement("CSSSELECTOR", "button.float-md-right.btn.btn-yellow");
		Thread.sleep(1000);
		TestUtils.testTitle("Add New workflow");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='addWorkflowModal']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='addWorkflowModal']/div/div/div/h4", "Add Workflow");
		
		TestUtils.testTitle("Check for form Validation and fill form");
		//Select Activity
		getDriver().findElement(By.id("addWorkflowBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Please select an activity.')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Please select an activity.')]", "Please select an activity.");
		Thread.sleep(800);
		getDriver().findElement(By.xpath("//form/div/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(addName);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		
		//Select Roles
		//getDriver().findElement(By.id("roles")).clear();
		getDriver().findElement(By.id("addWorkflowBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Please select at least a role.')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Please select at least a role.')]", "Please select at least a role.");
		//TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Sorry, You have some invalid entries in your form.')]", "Sorry, You have some invalid entries in your form.");

		Thread.sleep(800);
		new Select(getDriver().findElement(By.id("roles"))).selectByVisibleText(addRole);
		
		//Enter WorkFlow Name
		getDriver().findElement(By.id("addWorkflowBtn")).click();
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Please input a workflow name.')]")));
		//TestUtils.assertSearchText("XPATH", "//*[contains(text(),'Please input a workflow name.')]", "Please input a workflow name.");
		if (getDriver().findElement(By.xpath("/html[1]/body[1]/div[5]/span[2]")).isDisplayed() ){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[5]/span[2]")));
			TestUtils.assertSearchText("XPATH", "/html[1]/body[1]/div[5]/span[2]", "Please input a workflow name.");
		}else{
			testInfo.get().error("Workflow name error message is not displayed");
		}
		Thread.sleep(800);
		getDriver().findElement(By.id("workflowName")).sendKeys("Seamfix Sanity Test name "+System.currentTimeMillis());
		
		//Enter WorkFlow Description
		getDriver().findElement(By.id("addWorkflowBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Please input a workflow description.')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Please input a workflow description.')]", "Please input a workflow description.");
		Thread.sleep(800);
		getDriver().findElement(By.id("workflowDescription")).sendKeys("Seamfix Sanity Test Description");
		
		Thread.sleep(500);
		getDriver().findElement(By.id("addWorkflowBtn")).click();
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.swal2-content")));
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure you want to add this workflow?");
		
		if(testEnv.equalsIgnoreCase("prodData")) {
		// cancel
			getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
			Thread.sleep(500);
			// close
			getDriver().findElement(By.xpath("//button[2]")).click();
			Thread.sleep(500);
			testInfo.get().skip("Skipped confirm adding new workflow");
		} else {
			// approve
			getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Congratulations, workflow was created successfully.')]")));
			TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Congratulations, workflow was created successfully.')]",
					"Congratulations, workflow was created successfully.");
		}
		
	}
	

}
