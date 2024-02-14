package CACTestCases;

import java.io.File;
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

import com.aventstack.extentreports.Status;

import util.TestBase;
import util.TestUtils;

public class Dashboard extends TestBase { 
	
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Test (groups = { "Regression" })
	public void assertCardCountTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Thread.sleep(1000);
	    Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
	    TestUtils.assertSearchText("CSSSELECTOR", "a.navbar-brand", "ADMIN");
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("total_agents-loader")));
	    String totalAgents = getDriver().findElement(By.id("total_agents")).getText();
	    int actualTotalAgents = TestUtils.convertToInt(totalAgents);
	    Thread.sleep(3000);
	    String totalKits = getDriver().findElement(By.id("total_kits")).getText();
	    int actualTotalKits = TestUtils.convertToInt(totalKits);
		
		String todayNewRegValString = getDriver().findElement(By.id("new-registration_today")).getText();
	    String reRegistrationTodayValString = getDriver().findElement(By.id("re-registration_today")).getText();
	    String totalRegistrationTodayValString = getDriver().findElement(By.id("total_registration_today")).getText();
	    
	    int actualTodayNewRegVal = TestUtils.convertToInt(todayNewRegValString);
	    int actualReRegistrationTodayVal = TestUtils.convertToInt(reRegistrationTodayValString);
	    int actualTotalRegistrationTodayVal = TestUtils.convertToInt(totalRegistrationTodayValString);
		
	    int expectedTotalRegistrationTodayVal = actualTodayNewRegVal + actualReRegistrationTodayVal ;
	    
	    TestUtils.testTitle("Today's Registration");
	    try {
			Assert.assertEquals(expectedTotalRegistrationTodayVal,actualTotalRegistrationTodayVal);
		    testInfo.get().log(Status.INFO,"Today's total registration <b>("+actualTotalRegistrationTodayVal+")</b> is summation of today's New Reg <b>("+actualTodayNewRegVal+")</b> and Re-Reg <b>("+actualReRegistrationTodayVal+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation is not equal");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
		
	    // CSS button text
	    String whiteListedKitB = "#kitStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Whitelisted-Kits > rect";
	    String blacklistedKitB = "#kitStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Blacklisted-Kits > rect";
	    String monthlyActiveKitsB ="#monthlyKitActivityStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Active-Kits > rect";
	    String monthlyInactiveKitsB = "#monthlyKitActivityStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Inactive-Kits > rect";
	    String todayActiveKitsB = "#kitActivityStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Active-Kits > rect";
	    String todayInactiveKitsB = "#kitActivityStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Inactive-Kits > rect";
	    String activeAgentB = "#agentStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Active-Agents > rect";
	    String inactiveAgentsB = "#agentStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Inactive-Agents > rect";
	    String assignedKitsB = "#agentAssignmentStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Assigned-Kits > rect";
	    String unassignedKitsB = "#agentAssignmentStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Unassigned-Kits > rect";
	    String lockedOutagentsB = "#agentLockedAccountStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Locked-Out-Agents > rect";
	    String unlockedOutAgentsB = "#agentLockedAccountStatus-chart > svg > g:nth-child(4) > g.c3-legend-item.c3-legend-item-Unlocked-Out-Agents > rect";
	    
	    // Kit Status
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kitStatus-chart-loader->ajax-indicator")));
	    getDriver().findElement(By.cssSelector(whiteListedKitB)).click();
	    Thread.sleep(500);
	  	String blacklistedKits = present("#kitStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Blacklisted-Kits > text");
	  	getDriver().findElement(By.cssSelector(whiteListedKitB)).click();
	  	getDriver().findElement(By.cssSelector(blacklistedKitB)).click();
	    Thread.sleep(500);
	    String whitelistedKits = present("#kitStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Whitelisted-Kits > text");
	    getDriver().findElement(By.cssSelector(blacklistedKitB)).click();
	    
	  	int actualWhitelistedKits = TestUtils.convertToInt(whitelistedKits);
	  	int actualBlacklistedKits = TestUtils.convertToInt(blacklistedKits);
	  	int expectedTotalKits = actualWhitelistedKits + actualBlacklistedKits ;
  		
	  	TestUtils.testTitle("Kit Status");
	  	try {
			Assert.assertEquals(expectedTotalKits,actualTotalKits);
		    testInfo.get().log(Status.INFO,"Total Kits <b>("+actualTotalKits+")</b> equals to summation of blacklisted kits <b>("+blacklistedKits+")</b> and Whitelisted kits <b>("+whitelistedKits+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("whitelist and blacklist kits Summation is not equal to total kits");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	  	
	  	// Monthly Kit Activity
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("monthlyKitActivityStatus-chart-loader->ajax-indicator")));
	  	getDriver().findElement(By.cssSelector(monthlyActiveKitsB)).click();
	    Thread.sleep(500);
	    String monthlyInactiveKits;
	    monthlyInactiveKits = present("#monthlyKitActivityStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Inactive-Kits > text");
	    getDriver().findElement(By.cssSelector(monthlyActiveKitsB)).click();
	    getDriver().findElement(By.cssSelector(monthlyInactiveKitsB)).click();
	  	String monthlyActiveKits;
	  	monthlyActiveKits = present("#monthlyKitActivityStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Active-Kits > text");
	  	getDriver().findElement(By.cssSelector(monthlyInactiveKitsB)).click();
	
	  	int actualMonthlyActiveKits = TestUtils.convertToInt(monthlyActiveKits);
	  	int actualMonthlyInactiveKits = TestUtils.convertToInt(monthlyInactiveKits);
	  	int expectedTotalMonthlyKits = actualMonthlyActiveKits + actualMonthlyInactiveKits;
	  	TestUtils.testTitle("Monthly Kit Activity");
	  	try {
			Assert.assertEquals(expectedTotalMonthlyKits,actualTotalKits);
		    testInfo.get().log(Status.INFO,"Total Kits <b>("+actualTotalKits+")</b> equals to summation of Active kits <b>("+actualMonthlyActiveKits+")</b> and Inactive kits <b>("+actualMonthlyInactiveKits+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Monthly Active and Inactive kits Summation is not equal to total kits");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	  	
	  	// Today's Kit Activity 
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kitActivityStatus-chart-loader->ajax-indicator")));
	  	getDriver().findElement(By.cssSelector(todayActiveKitsB)).click();
	  	Thread.sleep(500);
	  	String todayInactiveKits = present("#kitActivityStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Inactive-Kits > text");
	  	getDriver().findElement(By.cssSelector(todayActiveKitsB)).click();
	  	getDriver().findElement(By.cssSelector(todayInactiveKitsB)).click();
	  	Thread.sleep(500);
	  	String todayActiveKits = present("#kitActivityStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Active-Kits > text");
	  	getDriver().findElement(By.cssSelector(todayInactiveKitsB)).click();
	    
	  	int actualTodayActiveKits = TestUtils.convertToInt(todayActiveKits);
	  	int actualTodayInactiveKits = TestUtils.convertToInt(todayInactiveKits);
	  	int expectedTotalTodayKits = actualTodayActiveKits + actualTodayInactiveKits;
  		
	  	TestUtils.testTitle("Daily Kit Activity");
	  	try {
			Assert.assertEquals(expectedTotalTodayKits,actualTotalKits);
		    testInfo.get().log(Status.INFO,"Total Kits <b>("+actualTotalKits+")</b> equals to summation of Active kits <b>("+actualTodayActiveKits+")</b> and Inactive kits <b>("+actualTodayInactiveKits+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Today's Active and Inactive kits Summation is not equal to total kits");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	  	
	  	TestUtils.scrollToElement("ID", "agentStatus-chart");
	  	
	  	// Agent Status
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("agentStatus-chart-loader->ajax-indicator")));
	  	getDriver().findElement(By.cssSelector(activeAgentB)).click();
	  	Thread.sleep(500);
	  	String inactiveAgent = present("#agentStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Inactive-Agents > text");
	  	getDriver().findElement(By.cssSelector(activeAgentB)).click();
	  	getDriver().findElement(By.cssSelector(inactiveAgentsB)).click();
	  	Thread.sleep(500);
	  	String activeAgent = present("#agentStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Active-Agents > text");
	  	getDriver().findElement(By.cssSelector(inactiveAgentsB)).click();
	  	
	  	int actualActiveAgent = TestUtils.convertToInt(activeAgent);
	  	int actualInactiveAgent = TestUtils.convertToInt(inactiveAgent);
	  	int expectedTotalAgent = actualActiveAgent + actualInactiveAgent;
	  	
  		TestUtils.testTitle("Agent Status");
	  	try {
			Assert.assertEquals(expectedTotalAgent,actualTotalAgents);
		    testInfo.get().log(Status.INFO,"Total Agents <b>("+actualTotalAgents+")</b> equals to summation of Active Agents <b>("+actualActiveAgent+")</b> and Inactive Agent <b>("+actualInactiveAgent+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Active and Inactive Agents Summation is not equal to total agents");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	  	
	  	// Device Assignment Status
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("agentAssignmentStatus-chart-loader->ajax-indicator")));
	  	getDriver().findElement(By.cssSelector(assignedKitsB)).click();
	  	Thread.sleep(500);
	  	String unassignedKits = present("#agentAssignmentStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Unassigned-Kits > text");
	  	getDriver().findElement(By.cssSelector(assignedKitsB)).click();
	  	getDriver().findElement(By.cssSelector(unassignedKitsB)).click();
	  	Thread.sleep(500);
	  	String assignedKits = present("#agentAssignmentStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Assigned-Kits > text");
	  	getDriver().findElement(By.cssSelector(unassignedKitsB)).click();
	  	
	  	int actualAssignedKits = TestUtils.convertToInt(assignedKits);
	  	int actualUnassignedKits = TestUtils.convertToInt(unassignedKits);
	  	int expectedTotalKitAssignment = actualAssignedKits + actualUnassignedKits;
	  	
  		TestUtils.testTitle("Device Assignment Status");

	  	try {
			Assert.assertEquals(expectedTotalKitAssignment,actualTotalKits);
		    testInfo.get().log(Status.INFO,"Total Kits <b>("+actualTotalKits+")</b> equals to summation of Assigned Kits <b>("+actualAssignedKits+")</b> and Unassigned Kits <b>("+actualUnassignedKits+")<b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Assigned and Unassigned kits Summation is not equal to total kits");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	  	
	  	// Account Status
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("agentLockedAccountStatus-chart-loader->ajax-indicator")));
	  	getDriver().findElement(By.cssSelector(lockedOutagentsB)).click();
	  	Thread.sleep(500);
	  	String unlockedOutAgents = present("#agentLockedAccountStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Unlocked-Out-Agents > text");
	  	getDriver().findElement(By.cssSelector(lockedOutagentsB)).click();
	  	getDriver().findElement(By.cssSelector(unlockedOutAgentsB)).click();
	  	Thread.sleep(500);
	  	String lockedOutAgent = present("#agentLockedAccountStatus-chart > svg > g:nth-child(2) > g.c3-chart > g.c3-chart-arcs > g.c3-chart-arc.c3-target.c3-target-Locked-Out-Agents > text");
	  	getDriver().findElement(By.cssSelector(unlockedOutAgentsB)).click();
	  	
	  	int actualLockedOutAgent = TestUtils.convertToInt(lockedOutAgent);
	  	int actualUnlockedOutAgents = TestUtils.convertToInt(unlockedOutAgents);
	  	int expectedTotalAccount = actualLockedOutAgent + actualUnlockedOutAgents;
	  	
  		TestUtils.testTitle("Account Status");
	  	try {
			Assert.assertEquals(expectedTotalAccount,actualTotalAgents);
		    testInfo.get().log(Status.INFO,"Total Agents <b>("+actualTotalAgents+")</b> equals to summation of Locked Out Agents <b>("+actualLockedOutAgent+")</b> and Unlocked Out Agents <b>("+actualUnlockedOutAgents+")</b>.");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Locked Out and Unlocked Out1 Agents Summation is not equal to total agents");
		    	 testInfo.get().error(verificationErrorString);
		    }
	}
	
	public String present (String locator) {
		String element;
	    if(!getDriver().findElement(By.cssSelector(locator)).isDisplayed()){
	    	element = "0";
	    } else {
	    	element = getDriver().findElement(By.cssSelector(locator)).getText();
	    }
	    
	    return element;
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByDealerTest(String testEnv)
			throws InterruptedException, IOException, ParseException {
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

		JSONObject envs = (JSONObject) config.get("dashboard");

		String dealerName = (String) envs.get("dealerName");

		TestUtils.testTitle("Filter by dealer name: " + dealerName);
		Thread.sleep(500);
	  	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("agentStatus-chart-loader->ajax-indicator")));
		getDriver().findElement(By.xpath("//span/span/span")).click();
	    getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
	    getDriver().findElement(By.xpath("//ul[@id='select2-dealerId-results']/li")).click();
	    getDriver().findElement(By.id("filterByDealerBtn")).click();
	    Thread.sleep(2000);
		assertCardCountTest();
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void selectDateTest() throws InterruptedException {
		TestUtils.testTitle("Select Start Date Test");
		TestUtils.scrollToElement("ID", "startDate");
		getDriver().findElement(By.id("startDate")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[@id='ui-datepicker-div']/div/div/select[2]")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("1")).click();
		testInfo.get().info("<b>Selected Start Date: </b>"+getDriver().findElement(By.id("startDate")).getAttribute("value"));

		TestUtils.testTitle("Select End Date Test");
		getDriver().findElement(By.id("endDate")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.linkText("10")).click();
		testInfo.get().info("<b>Selected End Date: </b>"+getDriver().findElement(By.id("endDate")).getAttribute("value"));
	}
}
