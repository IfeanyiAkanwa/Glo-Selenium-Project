package DealerTestCases;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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

public class vtuManagementDashboard extends TestBase {
	private StringBuffer verificationErrors = new StringBuffer();
	
	public void scrollDown() throws InterruptedException {
		TestUtils.scrollToElement("ID", "airtimeDataDistribution");
	}

	public void scrollUp() throws InterruptedException {
		TestUtils.scrollToElement("ID", "startDate");
	}
	
	@Test(groups = { "Regression" })
	public void navigateTovtuManagementDashboardTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		Thread.sleep(500);
		getDriver().findElement(By.name("999999991VTU Management")).click();
		Thread.sleep(500);
		try {
			 getDriver().findElement(By.name("999999999Dashboard")).click();
			 Thread.sleep(1000);
		} catch (Exception e) {
			 getDriver().findElement(By.name("999999999Dealer Dashboard")).click();
			 Thread.sleep(1000);
		}
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | VTU Dashboard");
		
	}
	
	@Test(groups = { "Regression" })
	public void assertCardValuesTest() throws Exception {
		
		Thread.sleep(500);
		String totalAirtimeValString = getDriver().findElement(By.id("totalAirtimeAmount")).getText();
		String totalDataValString = getDriver().findElement(By.id("totalDataAmount")).getText();
		String successTransValString = getDriver().findElement(By.id("successfulTransactions")).getText();
		
		int actualTotalAirtimeVal = TestUtils.convertToInt(totalAirtimeValString);
		int actualTotalDataVal = TestUtils.convertToInt(totalDataValString);
		int actualSuccessTransVal = TestUtils.convertToInt(successTransValString);
			
		int expectedTotalAmountVal = actualTotalAirtimeVal + actualTotalDataVal;
		
		try {
			Assert.assertEquals(expectedTotalAmountVal,actualSuccessTransVal);
			 testInfo.get().log(Status.INFO, "Total Summation of Airtime Amount ("+actualTotalAirtimeVal+") and Data Amount ("+actualTotalDataVal+") = Expected Total Amount ("+expectedTotalAmountVal+") which is equal to total Successful Transactions ("+actualSuccessTransVal+").");
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
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("vtuDashboard");
		
		String startDate = (String) envs.get("startDate");
		String endDate = (String) envs.get("endDate");
		
		String filter = "Filter by start date: " + startDate +" and end date: "+endDate;
		Markup m = MarkupHelper.createLabel(filter, ExtentColor.BLUE);
		testInfo.get().info(m);
		
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		Thread.sleep(500);
	    getDriver().findElement(By.id("startDate")).clear();
	    getDriver().findElement(By.id("startDate")).sendKeys(startDate);  //Start date YYYY/MM/DD format
	    getDriver().findElement(By.id("endDate")).clear();
	    getDriver().findElement(By.id("endDate")).sendKeys(endDate);  //End date YYYY/MM/DD format
	    getDriver().findElement(By.id("searchBtn")).click();
	    Thread.sleep(500);
	    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
	    
	    String trans = "Asserting transaction values";
		Markup c = MarkupHelper.createLabel(trans, ExtentColor.ORANGE);
		testInfo.get().info(c);
	    String totalAirtimeValString = getDriver().findElement(By.id("totalAirtimeAmount")).getText();
		String totalDataValString = getDriver().findElement(By.id("totalDataAmount")).getText();
		String successTransValString = getDriver().findElement(By.id("successfulTransactions")).getText();
		String failedTransValString = getDriver().findElement(By.id("failedTransactions")).getText();
		
		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Total Airtime Amount", totalAirtimeValString);
		fields.put("Total Data Amount", totalDataValString);
		fields.put("Sucessful Transactions", successTransValString);
		fields.put("Failed Transactions", failedTransValString);
		
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, entry.getKey() + " : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error(entry.getKey() + " : " + entry.getValue());
			}
		}
		
		int actualTotalAirtimeVal = TestUtils.convertToInt(totalAirtimeValString);
		int actualTotalDataVal = TestUtils.convertToInt(totalDataValString);
		int actualSuccessTransVal = TestUtils.convertToInt(successTransValString);
		
		int expectedTotalAmountVal = actualTotalAirtimeVal + actualTotalDataVal;
		
		try {
			Assert.assertEquals(expectedTotalAmountVal,actualSuccessTransVal);
			 testInfo.get().log(Status.INFO, "Total Summation of Airtime Amount ("+actualTotalAirtimeVal+") and Data Amount ("+actualTotalDataVal+") = Expected Total Amount ("+expectedTotalAmountVal+") which is equal to total Successful Transactions ("+actualSuccessTransVal+").");
		    } catch (Error e) {
		    	  verificationErrors.append(e.toString());
		    	  String verificationErrorString = verificationErrors.toString();
		    	  testInfo.get().error("Summation not equal");
		    	 testInfo.get().error(verificationErrorString);
		    	 
		   }
	}
	
}
