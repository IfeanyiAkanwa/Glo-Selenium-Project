package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.browserstack.local.Local;

import CACTestCases.SignIn;
import DealerTestCases.signIn;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestBase {
	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	private static OptionsManager optionsManager = new OptionsManager();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private Local l;
	public String local="local";
	public String remoteJenkins="remote-jenkins";
	public String remoteBrowserStack="remote-browserStack";
	public static String toAddress;
	public static final int waitTime = 60;

	@Parameters ("testEnv")
	public static String myUrl(String testEnv) {

		String myUrl = null;
		if(testEnv.equalsIgnoreCase("stagingData")) {
			myUrl = System.getProperty("instance-url", "https://glosimroptest.seamfix.com:8182/simrop");
		} else
		{
			myUrl = System.getProperty("instance-url", "https://simrop.gloworld.com/simrop/");
		}

		return myUrl;
	}
	
	public static String gridUrl = System.getProperty("grid-url", "https://selenium.seamfix.com/wd/hub");
	
	@BeforeSuite
    @Parameters({"groupReport","testEnv"})
	public void setUp( String groupReport, String testEnv) throws Exception {

		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
		// htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("TEST ENVIRONMENT", myUrl(testEnv));
		reports.attachReporter(htmlReporter);
	}
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Parameters({ "testEnv", "myBrowser", "config", "environment", "server"})
	@BeforeClass
	public void beforeClass(ITestContext iTestContext, String testEnv, String myBrowser, String config_file, String environment, String server) throws Exception{

		ExtentTest parent = reports.createTest(getClass().getName());
	    parentTest.set(parent);

		if (server.equals(remoteBrowserStack)) {

			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader("resources/conf/" + config_file));
			JSONObject envs = (JSONObject) config.get("environments");

			DesiredCapabilities capabilities = new DesiredCapabilities();

			Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
			Iterator it = envCapabilities.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
			}

			Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
			it = commonCapabilities.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				if(capabilities.getCapability(pair.getKey().toString()) == null){
					capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
				}
			}

			String username = System.getenv("BROWSERSTACK_USERNAME");
			if(username == null) {
				username = (String) config.get("user");
			}

			String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			if(accessKey == null) {
				accessKey = (String) config.get("key");
			}

			if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
				l = new Local();
				Map<String, String> options = new HashMap<String, String>();
				options.put("key", accessKey);
				l.start(options);
			}
			driver.set( new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities));
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			getDriver().get(myUrl(testEnv));

		}else if(server.equals(remoteJenkins)) {

			    // Remote Server Directory
				String browser = "chrome";
				if(browser.equalsIgnoreCase(myBrowser)) {
					DesiredCapabilities capability = DesiredCapabilities.chrome();
					capability.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", myUrl(testEnv) +" ["+ iTestContext.getName()+"]");
					capability.setPlatform(Platform.ANY);
					capability.setCapability("idleTimeout", 300);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));

				}
				else {
					DesiredCapabilities capability = DesiredCapabilities.firefox();
					capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
					capability.setCapability(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE, optionsManager.getFirefoxOptions());
					capability.setBrowserName(myBrowser);
					capability.setCapability("name", myUrl(testEnv) +" ["+ iTestContext.getName()+"]");
					capability.setPlatform(Platform.ANY);
					capability.setCapability("idleTimeout", 300);
					driver.set(new RemoteWebDriver(new URL(gridUrl), capability));
				}
				getDriver().manage().window().maximize();
				getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				getDriver().get(myUrl(testEnv));


		}else if(server.equals(local)) {
				// Local Directory
			if (myBrowser.equalsIgnoreCase("chrome")) {
					WebDriverManager.chromedriver().setup();
					driver.set(new ChromeDriver(optionsManager.getChromeOptions()));
					getDriver().manage().window().maximize();
					getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					getDriver().get(myUrl(testEnv));

				} else if (myBrowser.equalsIgnoreCase("firefox")) {

					WebDriverManager.firefoxdriver().setup();
					driver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
					getDriver().manage().window().maximize();
					getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					getDriver().get(myUrl(testEnv));
				}
			} 
	}

	@AfterClass
	public void afterClass() {
		 getDriver().quit();
	}

	public static  WebDriver getDriver(){
		return driver.get();
	}
	
	@Parameters ({"testEnv","user"})
	@Test 
	public void Login(String testEnv, String user) throws Exception {

		 SignIn.login(testEnv, user);
		 Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		 TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'ADMIN')])[2]", "ADMIN");
	}
	@Parameters ({"testEnv","superUser"})
	@Test 
	public void superAdminLogin(String testEnv, String superUser) throws Exception {

		 SignIn.login(testEnv, superUser);
		 Thread.sleep(2000);
		 getDriver().findElement(By.xpath("//a[@id='navbarDropdownMenuLink']/i")).click();
		 Thread.sleep(500);
		 getDriver().findElement(By.xpath("(//a[contains(text(),'SIMROP ADMIN')])[2]")).click();
		 Thread.sleep(1000);
		 TestUtils.assertSearchText("XPATH", "(//a[contains(text(),'SIMROP ADMIN')])[2]", "SIMROP ADMIN");
	}
	
	@Parameters ({"testEnv","user"})
	@Test 
	public void dealerLogin(String testEnv, String user) throws Exception {

		 signIn.login(testEnv, user);
		 Assert.assertEquals(getDriver().getTitle(), "SIMROP | Dashboard");
		TestUtils.assertSearchText("XPATH", "//nav/div/div/a", "DEALER");
	}
	
	@Parameters ("testEnv")
	@Test 
	public void DealerFiles(String testEnv) throws InterruptedException {

		FileLoader.upLoadFile();
		FileLoader.downLoadFile(myUrl(testEnv));
	
	}
	
	@Parameters ("testEnv")
	@Test 
	public void CACFiles(String testEnv) throws InterruptedException {

		FileLoader.upLoadFile();
		FileLoader.downLoadFile(myUrl(testEnv));
	
	}

	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) throws InterruptedException {

		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().getModel().setDescription(TestUtils.CheckBrowser());
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
			Thread.sleep(1000);
			}
		
	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		for (String group : result.getMethod().getGroups())
			testInfo.get().assignCategory(group);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = TestUtils.addScreenshot();
			 testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
			testInfo.get().fail(result.getThrowable());
			getDriver().navigate().refresh();
		}			
        else if (result.getStatus() == ITestResult.SKIP)
        	testInfo.get().skip(result.getThrowable());
        else
        	testInfo.get().pass(result.getName() +" Test passed");

		reports.flush();
	}
	
	@Parameters({"toMails", "groupReport"})
    @AfterSuite(description = "clean up report after test suite")
    public void cleanup(String toMails, String groupReport) {

        toAddress = System.getProperty("email_list", toMails);
        SendMail.ComposeGmail("SIMROP Report <seamfix.test.report@gmail.com>", toAddress, groupReport);

    }
}
