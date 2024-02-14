package util;

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

public class ForgotPassword extends TestBase {

	private String email;
	private String blacklistedEmail;
	private String deactivatedEmail;
	private String wroCode;
	private String emailFormat;
	private String nonExistingUser;
	private String validUserID;
	private String invalidUserID;
	
	@Parameters({"testEnv"})
    @BeforeMethod
    public void parseJson(String testEnv) throws Exception {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if(testEnv.equalsIgnoreCase("StagingData")) {
			path = new File(classpathRoot, "stagingData/data.conf.json");
		}else {
			path = new File(classpathRoot, "prodData/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

		JSONObject envs = (JSONObject) config.get("Forgot_Password");

		 email = (String) envs.get("email");
		 blacklistedEmail = (String) envs.get("blacklistedEmail");
		 deactivatedEmail = (String) envs.get("deactivatedEmail");
		 wroCode = (String) envs.get("wroCode");
		 emailFormat = (String) envs.get("emailFormat");
		 nonExistingUser = (String) envs.get("nonExistingUser");
		 validUserID = (String) envs.get("validUserID");
		 invalidUserID = (String) envs.get("invalidUserID");
        
    }
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void forgotPasswordTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		TestUtils.testTitle("Navigate to Forgot Password");
		getDriver().findElement(By.id("forgot-href")).click();
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Login");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Forgot Password");
		
		// Select Login Mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		
		// Empty email
		TestUtils.testTitle("Click Continue button without supplying email");
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		WebElement emailField = getDriver().findElement(By.name("email"));
		TestUtils.assertValidationMessage(validationMessages, emailField, "Email");

		// Wrong email format
		TestUtils.testTitle("Click Continue button after supplying a wrong email format: " + emailFormat);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(emailFormat);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "No account is associated with this username," + emailFormat);

		// Non existing user
		TestUtils.testTitle("Click Continue button after supplying an invalid email: "+nonExistingUser);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(nonExistingUser);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("strong")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "No account is associated with this username," + nonExistingUser);

		// Blacklisted user
		TestUtils.testTitle("Click Continue button after supplying a blacklisted email: " + blacklistedEmail);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(blacklistedEmail);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.text-dark")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "The account associated with this username is inactive");
		
		// Deactivated user
		TestUtils.testTitle("Click Continue button after supplying a deactivated email: " + deactivatedEmail);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(deactivatedEmail);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.text-dark")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "The account associated with this username is inactive");
		
		// Valid user
		TestUtils.testTitle("Click Continue button after supplying a valid user: " + email);
		Thread.sleep(500);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(email);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.text-dark")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Verification");
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "SIMROP sent a verification code to ***...7430. Code will expire in 5 minutes.");

		// Empty verification code
		TestUtils.testTitle("Click Continue button without supplying verification code");
		getDriver().findElement(By.id("forgot-password-btn")).click();
		WebElement codeField = getDriver().findElement(By.name("verificationCode"));
		TestUtils.assertValidationMessage(validationMessages, codeField, "Verification Code");

		// Wrong verification code
		TestUtils.testTitle("Click Continue button with wrong verification code: " + wroCode);
		getDriver().findElement(By.id("verificationCode")).sendKeys(wroCode);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Unable to validate provided code. Try again or contact support.");

		// Back to login
		TestUtils.testTitle("Click Login button to navigate back to login view page");
		getDriver().findElement(By.id("login-href")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Login");
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void userIDForgotPasswordTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		List<String> validationMessages = new ArrayList<>();
		validationMessages.add("Please fill out this field.");
		validationMessages.add("Please fill in this field.");
		
		TestUtils.testTitle("Navigate to Forgot Password");
		getDriver().findElement(By.id("forgot-href")).click();
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Login");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Forgot Password");
		
		// Select Login Mode
		getDriver().findElement(By.id("loginMode")).click();
		new Select(getDriver().findElement(By.id("loginMode"))).selectByVisibleText("Biosmart");
		Thread.sleep(500);
		
		// Invalid UserID
		TestUtils.testTitle("Click Continue button after supplying a Invalid UserID: " + invalidUserID);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(invalidUserID);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.text-dark")));
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Please enter a valid Username format (email)");
		
		// Valid UserID
		TestUtils.testTitle("Click Continue button after supplying a Valid UserID: " + validUserID);
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys(validUserID);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.text-dark")));
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Verification");
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "SIMROP sent a verification code to ***...7430. Code will expire in 5 minutes.");

		// Empty verification code
		TestUtils.testTitle("Click Continue button without supplying verification code");
		getDriver().findElement(By.id("forgot-password-btn")).click();
		WebElement codeField = getDriver().findElement(By.name("verificationCode"));
		TestUtils.assertValidationMessage(validationMessages, codeField, "Verification Code");

		// Wrong verification code
		TestUtils.testTitle("Click Continue button with wrong verification code: " + wroCode);
		getDriver().findElement(By.id("verificationCode")).sendKeys(wroCode);
		getDriver().findElement(By.id("forgot-password-btn")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "strong", "Unable to validate provided code. Try again or contact support.");

		// Back to login
		TestUtils.testTitle("Click Login button to navigate back to login view page");
		getDriver().findElement(By.id("login-href")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("CSSSELECTOR", "h4.text-dark", "Login");
		
	}
	
}
