package DealerTestCases;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.TestBase;
import util.TestUtils;

public class AgentCreation extends TestBase{
	
	private static String NubanPhoneNumber = TestUtils.generatePhoneNumber();
	private static String VTUNumber = TestUtils.generatePhoneNumber();
	private static String phoneNumber = TestUtils.generatePhoneNumber();
	
	@Test(groups = { "Regression" })
	public void navigateToAgentEnrollmentTest() throws InterruptedException {
		
		TestUtils.testTitle("Navigate to Agent Enrollment");
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		try {
			getDriver().findElement(By.name("1036Agent Enrollment")).click();
		} catch (Exception e) {
			getDriver().findElement(By.name("7883203270Agent Enrollment")).click();
		}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");
	}
	
/*	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void fillForm(String testEnv, String kitTag) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("newKitAssignment");

		String region = (String) envs.get("region");
		String subRegion = (String) envs.get("subRegion");
		String state = (String) envs.get("state");
		String territory = (String) envs.get("territory");
		String LGA = (String) envs.get("LGA");
		String area = (String) envs.get("area");
		String devicetype = (String) envs.get("devicetype");
		String deviceOwner = (String) envs.get("deviceOwner");

		TestUtils.testTitle("KitTag used for new kit assignment: " + kitTag);
		getDriver().findElement(By.cssSelector("button.btn.btn-yellow")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		TestUtils.assertSearchText("XPATH", "//h3", "New Kit Assignment");

		// For Device
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(kitTag);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("li.select2-results__option.select2-results__message"),"Searching..."));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Assert autopopulated details of kit");
		Assertion.assertNewKitAssignment();
		
		// For Device type
		getDriver().findElement(By.xpath("//div[3]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(devicetype);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);

		// For Device Owner
		getDriver().findElement(By.xpath("//div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/span/input")).sendKeys(deviceOwner);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// For Dealer
		TestUtils.testTitle("Assert autopopulated details of Dealer");
		TestUtils.assertSearchText("XPATH", "//h4", "Dealer Information");
		Assertion.dealerAutopopulatedDetails();
		Thread.sleep(500);
		TestUtils.scrollToElement("XPATH", "//div[2]/div/div[4]/div/span/span/span");
		
		// Select region
		getDriver().findElement(By.xpath("//div[2]/div/div[4]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(region);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select sub region
		getDriver().findElement(By.xpath("//span[2]/span/span")).click();
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(subRegion);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select State
		getDriver().findElement(By.xpath("//div[6]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(state);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select Territory
		getDriver().findElement(By.xpath("//div[7]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(territory);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select LGA
		getDriver().findElement(By.xpath("//div[8]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(LGA);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);

		// Select Area
		getDriver().findElement(By.xpath("//div[9]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(area);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		getDriver().findElement(By.name("next")).click();
		Thread.sleep(500);
		
	}*/
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void newAgentCreationTest(String testEnv, String firstName, String surName, String email, String kitTag) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("emailValidation");  

		String gender = (String) envs.get("gender");
		String outlet = (String) envs.get("outlet");
		String pic = "image2.jpg";
		
		AgentEnrollment.fillForm(testEnv, kitTag);
		
		// For Agent
		TestUtils.testTitle("Assign new kit to new user");
		TestUtils.assertSearchText("XPATH", "//div[@id='agent']/div/div/h4", "Agent Information");
		// Select new user
		TestUtils.clickElement("XPATH", "//label/span/span");
		Thread.sleep(500);

		// New user details
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys(firstName);
		Thread.sleep(500);
		getDriver().findElement(By.id("surname")).clear();
		getDriver().findElement(By.id("surname")).sendKeys(surName);
		Thread.sleep(500);
		getDriver().findElement(By.id("phoneNumber")).clear();
		getDriver().findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
		Thread.sleep(500);
		
		Thread.sleep(500);
		getDriver().findElement(By.id("agentNubanNumber")).clear();
		getDriver().findElement(By.id("agentNubanNumber")).sendKeys(NubanPhoneNumber);
		Thread.sleep(500);
		getDriver().findElement(By.id("agentVtuNumber")).clear();
		getDriver().findElement(By.id("agentVtuNumber")).sendKeys(VTUNumber);
		Thread.sleep(500);
		
		String userName =  getDriver().findElement(By.id("emailAddress")).getAttribute("value");
		if(userName.contains(email)){
			TestUtils.testTitle(userName + " found");
		}
		getDriver().findElement(By.xpath("//div[12]/div/div/span/span/span")).click();
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(gender);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "finish");
		Thread.sleep(500);
		TestUtils.uploadFile(By.id("editIdCard"), pic);
		testInfo.get().info("Picture successfully updated");
		Thread.sleep(500);
		TestUtils.scrollToElement("ID", "agentVtuNumber");

		// Select Outlet
		getDriver().findElement(By.xpath("//div[13]/div/span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(outlet);
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.id("finish")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//h2", "Success");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit was successfully assigned to agent");
		Thread.sleep(500);
		try{
			getDriver().findElement(By.cssSelector("button.swal2-confirm.success.swal2-styled")).click();
		}catch (Exception e){
			getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		}

		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		try{
			TestUtils.assertSearchText("XPATH", "//div/div/div/div/div/div/div/h4", "Assigned Kits");
		}catch (Exception e){

		}

	}
	
	@Parameters({ "testEnv"})
	@Test(groups = { "Regression" })
	public void emailWithoutNumTest(String testEnv) throws Exception {

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("emailValidation");  

		String firstName = (String) envs.get("firstName");
		String surName = (String) envs.get("surName");
		String kitTag = (String) envs.get("kitTag");
		
		String email = firstName+"."+surName+"@gloagent.com";
		
		newAgentCreationTest(testEnv, firstName, surName, email, kitTag);
		logoutTest();
		disapproveUserOnCACAdminTest(testEnv, kitTag);
	}

	@Parameters({ "testEnv"})
	@Test(groups = { "Regression" })
	public void emailWithNumTest(String testEnv) throws Exception {
		
		dealerLogin(testEnv, "valid_Dealer_Login");
		navigateToAgentEnrollmentTest();
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("emailValidation");  

		String firstName1 = (String) envs.get("firstName1");
		String surName1 = (String) envs.get("surName1");
		String kitTag1 = (String) envs.get("kitTag1");
		
		String email = firstName1+"."+surName1+"2@gloagent.com";
		
		newAgentCreationTest(testEnv, firstName1, surName1, email, kitTag1);
		logoutTest();
		disapproveUserOnCACAdminTest(testEnv, kitTag1);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void disapproveUserOnCACAdminTest(String testEnv, String kitTag) throws Exception {
	
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		Login(testEnv, "valid_Admin_Login");
			
		TestUtils.testTitle("Disapprove Agent on CAC Admin");
		
		// Navigate to Agent Enrollment CAC Admin
		try {
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("a[name=\"1235Agent Enrollment\"] > p")).click();
		} catch (Exception e) {
			Thread.sleep(500);
			getDriver().findElement(By.cssSelector("a[name=\"1235Agent Enrollment\"]")).click();
		}
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Kits");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title", "Kit Mapping");
		    
		// Search for kitTag
		TestUtils.testTitle("Filter by kit tag: " + kitTag);
		getDriver().findElement(By.name("kitTagParam")).clear();
		getDriver().findElement(By.name("kitTagParam")).sendKeys(kitTag);
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
	    
		// View details
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//table[@id='assignmentDetail']/tbody/tr/td[10]/div/a/i")).click();
		Thread.sleep(500);
	    getDriver().findElement(By.linkText("View Mapping Details")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));

	    TestUtils.assertSearchText("XPATH", "//h4", "View details");
	    TestUtils.scrollToElement("XPATH", "//h4");
	    
	    // Reject request
		getDriver().findElement(By.id("resolution")).clear();
		getDriver().findElement(By.id("resolution")).sendKeys("Seamfix Sanity Test");
		getDriver().findElement(By.id("rejectRequest")).click();
		Thread.sleep(500);
		
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure that you want to Reject this request?");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.cssSelector("div.swal2-content")));
		Thread.sleep(500);
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Kit Mapping Request was rejected successfully");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.btn.btn-simple.btn-blue")).click();
		Thread.sleep(500);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			Thread.sleep(1000);
			}
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	
	}
	
	@Test(groups = { "Regression" })
	public void logoutTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Logout");
		TestUtils.scrollToElement("ID", "navbarDropdownMenuLink");
		Thread.sleep(500);
		getDriver().findElement(By.id("navbarDropdownMenuLink")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("Logout")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Login");
	}

}
