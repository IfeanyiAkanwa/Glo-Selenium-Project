package CACTestCases;

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

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.TestBase;
import util.TestUtils;

public class VtuManagementDashboard extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "vend-amount-chart");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "advancedBtn");
	}
	
	@Test(groups = { "Regression" })
	public void navigateTovtuManagementDashboardTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		String dash = "Navigate To VTU Management Dashboard";
		Markup v = MarkupHelper.createLabel(dash, ExtentColor.BLUE);
		testInfo.get().info(v);
		Thread.sleep(500);
		getDriver().findElement(By.cssSelector("a[name=\"999999981VTU Management\"] > p")).click();
	    Thread.sleep(500);
	    getDriver().findElement(By.name("22829282Dashboard")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | CAC VTU Dashboard");
		
	}
	
	@Test(groups = { "Regression" })
	public void assertCardValuesTest() throws InterruptedException {
		
		Thread.sleep(500);
		String totalAirtimeValString = getDriver().findElement(By.id("total_airtime")).getText();
		String totalDataValString = getDriver().findElement(By.id("total_data")).getText();
		String successTransValString = getDriver().findElement(By.id("success_trans")).getText();
		String failedTransValString = getDriver().findElement(By.id("failed_trans")).getText();
		
		int actualTotalAirtimeVal = TestUtils.convertToInt(totalAirtimeValString);
		int actualTotalDataVal = TestUtils.convertToInt(totalDataValString);
		int actualSuccessTransVal = TestUtils.convertToInt(successTransValString);
		int actualFailedTransValVal = TestUtils.convertToInt(failedTransValString);
				
		int expectedTotalTransVal = actualSuccessTransVal + actualFailedTransValVal;
		int expectedTotalAmountVal = actualTotalAirtimeVal + actualTotalDataVal;
		
		try {
			Assert.assertEquals(expectedTotalAmountVal,actualSuccessTransVal);
		    testInfo.get().log(Status.INFO, "Total Summation of Airtime Amount ("+actualTotalAirtimeVal+") and Data Amount ("+actualTotalDataVal+") = ("+expectedTotalAmountVal+")  is equal to total of Successful Transaction ("+actualSuccessTransVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    	  }
	}

	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuDashboard");
		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");
		String totalAirtimeVal = (String) envs.get("totalAirtime");
		String totalDataVal = (String) envs.get("totalData");
		String successfulTransVal = (String) envs.get("successfulTrans");
		String failedTransVal = (String) envs.get("failedTrans");
		
		int totalAirtime = TestUtils.convertToInt(totalAirtimeVal);
		int totalData = TestUtils.convertToInt(totalDataVal);
		int successfulTrans = TestUtils.convertToInt(successfulTransVal);
		int failedTrans = TestUtils.convertToInt(failedTransVal);

		String filter = "Filter by start date: " + startDate +" and end date: "+endDate;
		Markup m = MarkupHelper.createLabel(filter, ExtentColor.BLUE);
		testInfo.get().info(m);
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("endDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
	    getDriver().findElement(By.id("startDate")).clear();
	    getDriver().findElement(By.id("startDate")).sendKeys(startDate);  //Start date YYYY/MM/DD format
	    getDriver().findElement(By.id("endDate")).clear();
	    getDriver().findElement(By.id("endDate")).sendKeys(endDate);  //End date YYYY/MM/DD format
	    getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    Thread.sleep(2000);
	    String totalAirtimeValString = getDriver().findElement(By.id("total_airtime")).getText();
		String totalDataValString = getDriver().findElement(By.id("total_data")).getText();
		String successTransValString = getDriver().findElement(By.id("success_trans")).getText();
		String failedTransValString = getDriver().findElement(By.id("failed_trans")).getText();
		
		int actualTotalAirtimeVal = TestUtils.convertToInt(totalAirtimeValString);
		int actualTotalDataVal = TestUtils.convertToInt(totalDataValString);
		int actualSuccessTransVal = TestUtils.convertToInt(successTransValString);
		int actualFailedTransValVal = TestUtils.convertToInt(failedTransValString);
		
		Assert.assertEquals(totalAirtime, actualTotalAirtimeVal);
		Assert.assertEquals(totalData, actualTotalDataVal);
		Assert.assertEquals(successfulTrans, actualSuccessTransVal);
		Assert.assertEquals(failedTrans, actualFailedTransValVal);
		testInfo.get().info(" Total Airtime Amount: "+actualTotalAirtimeVal+" Total Data Amount: "+actualTotalDataVal+" Successful Transaction: "+actualSuccessTransVal+" Failed Transactions: "+actualFailedTransValVal);
	    
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDealerAndDateTest(String testEnv) throws Exception {
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
		JSONObject envs = (JSONObject) config.get("vtuDashboard");
		String dealerName = (String) envs.get("dealerName");
		String endDate2 = (String) envs.get("endDate2");
		String totalAirtimeVal = (String) envs.get("totalAirtime2");
		String totalDataVal = (String) envs.get("totalData2");
		String successfulTransVal = (String) envs.get("successfulTrans2");
		String failedTransVal = (String) envs.get("failedTrans2");
		
		int totalAirtime = TestUtils.convertToInt(totalAirtimeVal);
		int totalData = TestUtils.convertToInt(totalDataVal);
		int successfulTrans = TestUtils.convertToInt(successfulTransVal);
		int failedTrans = TestUtils.convertToInt(failedTransVal);

		String filter = "Filter by dealer name: " + dealerName +" and end date: "+endDate2;
		Markup m = MarkupHelper.createLabel(filter, ExtentColor.BLUE);
		testInfo.get().info(m);
		Thread.sleep(500);
		if (!getDriver().findElement(By.id("endDate")).isDisplayed()) {
			getDriver().findElement(By.id("advancedBtn")).click();
		}
		Thread.sleep(500);
		
	    //Search by Dealer 	
	    getDriver().findElement(By.xpath("//span/span/span")).click();
	    getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(dealerName);
	    getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
	    Thread.sleep(500);
	    
	    // Search by DateRange 	
	    getDriver().findElement(By.id("startDate")).clear();
	    getDriver().findElement(By.id("endDate")).clear();
	    getDriver().findElement(By.id("endDate")).sendKeys(endDate2);  //End date YYYY/MM/DD format
	    getDriver().findElement(By.cssSelector("button.btn.btn-link")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    Thread.sleep(2000);
	    String totalAirtimeValString = getDriver().findElement(By.id("total_airtime")).getText();
		String totalDataValString = getDriver().findElement(By.id("total_data")).getText();
		String successTransValString = getDriver().findElement(By.id("success_trans")).getText();
		String failedTransValString = getDriver().findElement(By.id("failed_trans")).getText();
		
		int actualTotalAirtimeVal = TestUtils.convertToInt(totalAirtimeValString);
		int actualTotalDataVal = TestUtils.convertToInt(totalDataValString);
		int actualSuccessTransVal = TestUtils.convertToInt(successTransValString);
		int actualFailedTransValVal = TestUtils.convertToInt(failedTransValString);
		
		try {
		Assert.assertEquals(totalAirtime, actualTotalAirtimeVal);
		Assert.assertEquals(totalData, actualTotalDataVal);
		Assert.assertEquals(successfulTrans, actualSuccessTransVal);
		Assert.assertEquals(failedTrans, actualFailedTransValVal);
		testInfo.get().info(" Total Airtime Amount: "+actualTotalAirtimeVal+" Total Data Amount: "+actualTotalDataVal+" Successful Transaction: "+actualSuccessTransVal+" Failed Transactions: "+actualFailedTransValVal);
		 } catch (Error e) {
	    	  verificationErrors.append(e.toString());
	    	  String verificationErrorString = verificationErrors.toString();
	    	  testInfo.get().error("Summation not equal");
	    	 testInfo.get().error(verificationErrorString);
	    	  }
	}
}
