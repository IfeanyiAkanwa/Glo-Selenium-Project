package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.TestBase;
import util.TestUtils;

public class SignIn extends TestBase {
	
	private String email;
	private String pw1;
	private String userName;
	private String email1;
	private String pw;
	private String userName1;
	private String invalidPw;
	private String invalidADUsername;
	private String UserID;
	private String validPw;
	private String invalidUserID;
	
	@Parameters({"testEnv"})
    @BeforeMethod
    public void parseJson(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		} else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("AD_ValidLogin");

		email = (String) envs.get("email");
		pw1 = (String) envs.get("pw1");
		userName = (String) envs.get("userName");
		email1 = (String) envs.get("email1");
		pw = (String) envs.get("pw");
		userName1 = (String) envs.get("userName1");
		invalidPw = (String) envs.get("invalidPw");
		invalidADUsername = (String) envs.get("invalidADUsername");
		UserID = (String) envs.get("UserID");
		validPw = (String) envs.get("validPw");
		invalidUserID = (String) envs.get("invalidUserID");
        
    }
	
	@Parameters ({"testEnv"})
	public static void login (String testEnv, String testVal) throws  InterruptedException, FileNotFoundException, IOException, ParseException {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get(testVal);
		
		String email = (String) envs.get("email");
		String bioSmartUser = System.getProperty("adminUsername", email);
		String pw = (String) envs.get("pw");
		String userPw = System.getProperty("adminPassword", pw);
		
		TestUtils.testTitle("Login with username: (" + bioSmartUser + ") and password: (" + userPw + ")");
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
	@Test 
	public void blacklistedUserLoginTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		login(testEnv, "Blacklisted_User");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
	    TestUtils.assertSearchText("CSSSELECTOR", "strong", "Your account was Blacklisted. Please contact support.");
	}
	
	@Parameters ({"testEnv"})
	@Test 
	public void deactivatedUserLoginTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		login(testEnv, "Deactivated_User");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
	    TestUtils.assertSearchText("CSSSELECTOR", "strong", "Your account was deactivated. Please contact support.");
	}
	
	@Parameters ({"testEnv"})
	@Test 
	public void nonExistingEmailLoginTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		login(testEnv, "Invalid_Email_Password");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
	    TestUtils.assertSearchText("CSSSELECTOR", "strong", "Invalid username or password entered.");
	}
	
	@Test 
	public void emptyInputFieldTest() throws InterruptedException {
		TestUtils.testTitle("Login with empty fields");
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		// Select Login mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("");
		getDriver().findElement(By.id("login-btn")).click();
		WebElement email = getDriver().findElement(By.name("email")); 
		TestUtils.assertValidationMessage(validationMessages, email, "Email");
	    getDriver().findElement(By.id("email")).sendKeys("a@test.com");
	    
	    getDriver().findElement(By.id("password")).clear();
	    getDriver().findElement(By.id("password")).sendKeys("");
	    getDriver().findElement(By.id("login-btn")).click();
	    WebElement password = getDriver().findElement(By.name("password")); 
		TestUtils.assertValidationMessage(validationMessages, password, "Password");
	}
    
	@Parameters ("testEnv")
	@Test (groups = { "Regression" })
	public void validEmailPasswordLoginTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	    login(testEnv, "valid_Admin_Login");
	    Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
	}
	
	@Parameters ("testEnv")
	@Test (groups = { "Regression" })
	public void declineTermsAndConditionsLoginTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	    login(testEnv, "TermsAndConditionsLogin");
	    
	    try {
	    	if (getDriver().findElement(By.xpath("//h1")).isDisplayed()) {
		    	Assert.assertEquals(getDriver().getTitle(), "SIMROP | T&Cs");
				TestUtils.assertSearchText("XPATH", "//h1", "TERMS AND CONDITIONS");
				TestUtils.testTitle("Decline Terms and Conditions");
				TestUtils.scrollUntilElementIsVisible("ID", "disagree-btn");
				Thread.sleep(500);
				getDriver().findElement(By.id("disagree-btn")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
				TestUtils.assertSearchText("CSSSELECTOR", "strong", "Terms and Conditions must be accepted");
				Thread.sleep(500);
			}
		} catch (Exception e) {
			testInfo.get().info("<b> SIMROP TERMS AND CONDITIONS SETTING IS TURNED OFF </b>");
			Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
			TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
			logOutTest(testEnv);
		}
	}
	
	@Parameters ("testEnv")
	@Test (groups = { "Regression" })
	public void logOutTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Logout");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navbarDropdownMenuLink']/i")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//a[@id='navbarDropdownMenuLink']/i")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(@href, '/simrop/logout?language=en_US')])[2]")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("(//a[contains(@href, '/simrop/logout?language=en_US')])[2]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Login");  
		Thread.sleep(500);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void erorrMessagesValidationTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		
		// Login with Active Directory user but select Biosmart as Auth mode and biosmart email
		TestUtils.testTitle("Login with Active Directory user but select Biosmart as Auth mode and biosmart email: " + email);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw1);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Your allowed Login Mode is Active Directory. Your AD Username is; " + userName);
		Thread.sleep(500);
		
		// Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name
		TestUtils.testTitle("Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name: " + userName);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userName);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong",	"Your allowed Login Mode is Active Directory. Your AD Username is; " + userName);
		Thread.sleep(500);
		
		// Login with Active Directory user but select Active Directory as Auth mode and Biosmart email
		TestUtils.testTitle("Login with Active Directory user but select Active Directory as Auth mode and Biosmart email: " + email);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Active Directory");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw1);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong",	"Your allowed Login Mode is Active Directory. Your AD Username is; " + userName);
		Thread.sleep(500);
		
		// Login with Biosmart user but select Active Directory as Auth mode and Active Directory username
		TestUtils.testTitle("Login with Biosmart user but select Active Directory as Auth mode and Active Directory username: " + userName1);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Active Directory");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userName1);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw1);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Your allowed Login Mode is Biosmart. Your email address is; " + email1);
		Thread.sleep(500);
		
		// Login with Biosmart user but select Active Directory as Auth mode and Biosmart email
		TestUtils.testTitle("Login with Biosmart user but select Active Directory as Auth mode and Biosmart email: " + email1);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Active Directory");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email1);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw1);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Your allowed Login Mode is Biosmart. Your email address is; " + email1);
		Thread.sleep(500);
		
		// Login with Biosmart user but select Biosmart as Auth mode and Active Directory username
		TestUtils.testTitle("Login with Biosmart user but select Biosmart as Auth mode and Active Directory username: " + userName1);
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userName1);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw1);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong",	"Your allowed Login Mode is Biosmart. Your email address is; " + email1);
		Thread.sleep(500);
	}
	
	@Parameters ("testEnv")
    @Test (groups = { "Regression" })
	public void activeDirectoryValidLoginTest(String testEnv) throws Exception {

		String adUsername = System.getProperty("adminUsername", userName);
		String userPw = System.getProperty("adminPassword", pw);
		TestUtils.testTitle("Login with valid username: (" + adUsername + ") and password: (" + userPw + ")");
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Login");
		
		adSignInTest(adUsername, userPw);
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
	}

	public void adSignInTest(String adUsername, String pw) throws InterruptedException {
		
		// Select Login mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Active Directory");
		Thread.sleep(500);
		
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(adUsername);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
	}
	
	@Parameters ("testEnv")
	@Test (groups = { "Regression" })
	public void adLoginValidationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		// Login with valid AD username and invalid password 
		TestUtils.testTitle("Login with valid AD username: (" + userName + ") and  invalid password: (" + invalidPw + ")");
		adSignInTest(userName, invalidPw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "You entered an invalid username or password.");
		
		// Login with invalid AD username and valid password
		TestUtils.testTitle("Login with invalid AD username: (" + invalidADUsername + ") and valid password: (" + pw + ")");
		adSignInTest(invalidADUsername, pw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Invalid username or password entered.");

		// Login with empty AD username and valid password
		TestUtils.testTitle("Login with empty AD username: (  ) and  valid password: (" + pw + ")");
		adSignInTest("", pw);
		WebElement email = getDriver().findElement(By.name("email")); 
		TestUtils.assertValidationMessage(validationMessages, email, "Email");

		// Login with valid AD username and empty password
		TestUtils.testTitle("Login with valid AD username: (" + userName + ") and  empty password: (  )");
		adSignInTest(userName, "");
		WebElement password = getDriver().findElement(By.name("password")); 
		TestUtils.assertValidationMessage(validationMessages, password, "Password");

		// Login with empty AD username and empty password
		TestUtils.testTitle("Login with empty AD username: ( ) and empty password: ( )");
		adSignInTest("", "");
		WebElement emaill = getDriver().findElement(By.name("email")); 
		TestUtils.assertValidationMessage(validationMessages, emaill, "Email");
		
		// Login with invalid AD username and invalid password
		TestUtils.testTitle("Login with invalid AD username: (" + invalidADUsername + ") and  invalid password: (" + invalidPw + ")");
		adSignInTest(invalidADUsername, invalidPw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Invalid username or password entered.");
		
	}
	
	@Parameters ("testEnv")
    @Test (groups = { "Regression" })
	public void userIDValidLoginTest(String testEnv) throws Exception {

		String userID = System.getProperty("adminUsername", UserID);
		String userPw = System.getProperty("adminPassword", validPw);
		TestUtils.testTitle("Login with valid User ID: (" + userID + ") and valid password: (" + userPw + ")");
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Login");
		
		userIDSignInTest(userID, userPw);
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
	}
	
	public void userIDSignInTest(String userID, String pw) throws InterruptedException {

		// Select Login mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);

		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(userID);
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(pw);
		getDriver().findElement(By.id("login-btn")).click();
		Thread.sleep(1000);
	}
	
	@Parameters ("testEnv")
    @Test (groups = { "Regression" })
	public void userIDValidationTest(String testEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		// Login with valid UserID and invalid password 
		TestUtils.testTitle("Login with valid UserID: (" + UserID + ") and  invalid password: (" + invalidPw + ")");
		userIDSignInTest(UserID, invalidPw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "You entered an invalid username or password.");
		
		// Login with invalid UserID and valid password
		TestUtils.testTitle("Login with invalid UserID: (" + invalidUserID + ") and valid password: (" + pw + ")");
		userIDSignInTest(invalidUserID, pw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Please enter a valid user Id format (user ID)");

		// Login with empty UserID and valid password
		TestUtils.testTitle("Login with empty UserID: (  ) and  valid password: (" + pw + ")");
		userIDSignInTest("", pw);
		WebElement email = getDriver().findElement(By.name("email")); 
		TestUtils.assertValidationMessage(validationMessages, email, "Email");

		// Login with valid UserID and empty password
		TestUtils.testTitle("Login with valid UserID: (" + UserID + ") and  empty password: (  )");
		userIDSignInTest(UserID, "");
		WebElement password = getDriver().findElement(By.name("password")); 
		TestUtils.assertValidationMessage(validationMessages, password, "Password");

		// Login with empty UserID and empty password
		TestUtils.testTitle("Login with empty UserID: ( ) and empty password: ( )");
		userIDSignInTest("", "");
		WebElement emaill = getDriver().findElement(By.name("email")); 
		TestUtils.assertValidationMessage(validationMessages, emaill, "Email");
		
		// Login with invalid UserID and invalid password
		TestUtils.testTitle("Login with invalid UserID: (" + invalidUserID + ") and  invalid password: (" + invalidPw + ")");
		userIDSignInTest(invalidUserID, invalidPw);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Please enter a valid user Id format (user ID)");
	
	}
	
}