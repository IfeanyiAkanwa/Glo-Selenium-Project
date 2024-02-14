package DealerTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.TestBase;
import util.TestUtils;

public class signIn extends TestBase {
	
	@Parameters ({"testEnv"})
	public static void login(String testEnv, String testVal) throws  InterruptedException, FileNotFoundException, IOException, ParseException {

		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/dealerdata.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/dealerdata.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get(testVal);
		
		String email = (String) envs.get("email");
		String bioSmartUser = System.getProperty("dealerUsername", email);
		String pw = (String) envs.get("pw");
		String userPw = System.getProperty("dealerPassword", pw);
		TestUtils.testTitle("Login with username: (" + bioSmartUser + ") and password (" + userPw + ")");
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Login");
		
		// Select Login mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
	    getDriver().findElement(By.id("email")).clear();
	    getDriver().findElement(By.id("email")).sendKeys(bioSmartUser);
	    getDriver().findElement(By.id("password")).clear();
	    getDriver().findElement(By.id("password")).sendKeys(userPw);
	    getDriver().findElement(By.id("login-btn")).click();
	    Thread.sleep(1000);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void ValidEmailPasswordTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
    
		 login(testEnv, "valid_Dealer_Login");
		 Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		TestUtils.assertSearchText("XPATH", "//nav/div/div/a", "DEALER");
	}

}
