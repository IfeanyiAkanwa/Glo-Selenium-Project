package CACTestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.Assertion;
import util.TestBase;
import util.TestUtils;

public class Notification extends TestBase{
	private String global; 
	private String kitTag;
	private String user;
	private String startDate;
	private String endDate;
	private String dealerName;
	private String sender;
	
	@Parameters({ "testEnv" })
    @BeforeMethod
    public void parseJson(String testEnv) throws IOException, ParseException {
        File path = null;
        File classpathRoot = new File(System.getProperty("user.dir"));
        if (testEnv.equalsIgnoreCase("StagingData")) {
            path = new File(classpathRoot, "stagingData/data.conf.json");
        } else {
            path = new File(classpathRoot, "prodData/data.conf.json");
        }
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader(path));
        JSONObject envs = (JSONObject) config.get("notification");

        global = (String) envs.get("global");
        kitTag = (String) envs.get("kitTag");
        user = (String) envs.get("user");
        startDate = (String) envs.get("startDate");
        endDate = (String) envs.get("endDate");
        dealerName = (String) envs.get("dealerName");
        sender = (String) envs.get("sender");
    }
	
	@Test(groups = { "Regression" })
	public void navigateToNotificationTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Navigate to Notification");
	    try {
			Thread.sleep(500);
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"1239Notifications\"]");
			getDriver().findElement(By.cssSelector("a[name=\"1239Notifications\"]")).click();
		} catch (Exception e) {
			Thread.sleep(500);
			TestUtils.scrollToElement("CSSSELECTOR", "a[name=\"12877284079Notifications Management\"]");
			getDriver().findElement(By.cssSelector("a[name=\"12877284079Notifications Management\"]")).click();
		}
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Assert.assertEquals(getDriver().getTitle(), "SIMROP | Notifications Management");
		TestUtils.assertSearchText("CSSSELECTOR", "h4.card-title.font-weight-bold.text-secondary", "Notification");
		
	}
	
	@Test (groups = { "Regression" })
	public void showPageSize() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		new Select(getDriver().findElement(By.name("notificationsTable_length"))).selectByVisibleText("50");
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Change page size to: 50");
		int rowCount = getDriver().findElements(By.xpath("//table[@id='notificationsTable']/tbody/tr")).size();
		if (TestUtils.isElementPresent("XPATH", "//td")) {
			testInfo.get().info("Total number of record returned: " + rowCount);
		} else {
			testInfo.get().info("Table is empty.");
		}
	}
	
	@Test(groups = { "Regression" })
	public void downloadReport() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		TestUtils.testTitle("Download Notification Reports");
		// Download pdf
		TestUtils.testTitle("Download Pdf");
		getDriver().findElement(By.xpath("//div[2]/a/i")).click();
		Thread.sleep(500);
		// Download excel
		TestUtils.testTitle("Download Excel");
		getDriver().findElement(By.xpath("//a[2]/i")).click();
		Thread.sleep(500);
	}
	@Test(groups = { "Regression" })
	public void selectVisibleColumns() throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		
		TestUtils.testTitle("Select Visible Columns");
		TestUtils.assertSearchText("XPATH", "//div[2]/a[3]", "Select Visible Columns");
		getDriver().findElement(By.xpath("//div[2]/a[3]")).click();
		Thread.sleep(500);
	
		//Kit/User Column
		TestUtils.testTitle("Remove Kit/User Column");
		TestUtils.scrollToElement("XPATH", "//a[2]/span");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		if (getDriver().findElement(By.xpath("//th[2]")).getText().contains("Kit/User")) {
			TestUtils.assertSearchText("XPATH", "//th[2]", "Kit/User");

		}else {
			testInfo.get().info("Kit/User column removed");

		}
		TestUtils.testTitle("Add Kit/User Column");
		getDriver().findElement(By.xpath("//a[2]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[2]", "Kit/User");
		Thread.sleep(500);
		
		//Date sent Column
		TestUtils.testTitle("Remove Date sent Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Date sent Column")) {
			TestUtils.assertSearchText("XPATH", "//th[3]", "Date Sent");

		}else {
			testInfo.get().info("Date Sent column removed");

		}
		TestUtils.testTitle("Add Date sent Column");
		getDriver().findElement(By.xpath("//a[3]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[3]", "Date Sent");
		Thread.sleep(500);
		
		//Sent By Column
		TestUtils.testTitle("Remove Sent By Column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		if (getDriver().findElement(By.xpath("//th[3]")).getText().contains("Sent By")) {
			TestUtils.assertSearchText("XPATH", "//th[4]", "Sent By");

		}else {
			testInfo.get().info("Sent By column removed");

		}
		TestUtils.testTitle("Add Sent By column");
		getDriver().findElement(By.xpath("//a[4]/span")).click();
		TestUtils.assertSearchText("XPATH", "//th[4]", "Sent By");
		Thread.sleep(500);
		
		//Action Column
		TestUtils.testTitle("Remove Actions Column");
		getDriver().findElement(By.linkText("Actions")).click();
		try {
			TestUtils.assertSearchText("XPATH", "//th[5]", "Actions");

		} catch (Exception e) {
			testInfo.get().info("Actions column removed");
		}
		TestUtils.testTitle("Add Actions Column");
		getDriver().findElement(By.linkText("Actions")).click();
		TestUtils.assertSearchText("XPATH", "//th[5]", "Actions");
		Thread.sleep(500);
		
		WebElement figure = getDriver().findElement(By.xpath("//body"));
			
		Actions actions = new Actions(getDriver());
			actions.moveToElement(figure).perform(); // hover action
			figure.click();
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByUserTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by User: " + user);
		getDriver().findElement(By.name("userEmail")).clear();
		getDriver().findElement(By.name("userEmail")).sendKeys(user);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='notificationsTable']/tbody/tr/td[2]", user);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByGlobalTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Global: " + global);
		getDriver().findElement(By.name("userEmail")).clear();
		getDriver().findElement(By.name("userEmail")).sendKeys(global);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		try {
			TestUtils.assertSearchText("XPATH", "//table[@id='notificationsTable']/tbody/tr/td[2]", global);
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//td", "No data available in table");
		}
	}
	
	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchByKitTagTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Kit Tag: " + kitTag);
		getDriver().findElement(By.name("userEmail")).clear();
		getDriver().findElement(By.name("kitTag")).clear();
		getDriver().findElement(By.name("kitTag")).sendKeys(kitTag);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='notificationsTable']/tbody/tr/td[2]", kitTag);
	}

	@Parameters({ "testEnv" })
	@Test(groups = { "Regression" })
	public void searchBySenderTest(String testEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by Sender: " + sender);
		getDriver().findElement(By.name("userEmail")).clear();
		getDriver().findElement(By.name("kitTag")).clear();
		getDriver().findElement(By.name("sentBy")).clear();
		getDriver().findElement(By.name("sentBy")).sendKeys(sender);
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),"Processing..."));
		TestUtils.assertSearchText("XPATH", "//table[@id='notificationsTable']/tbody/tr/td[4]", sender);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void searchByDateTest(String testEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		TestUtils.testTitle("Filter by date range: " + startDate+ " and "+endDate);
		getDriver().findElement(By.name("userEmail")).clear();
		getDriver().findElement(By.name("kitTag")).clear();
		getDriver().findElement(By.name("sentBy")).clear();
		getDriver().findElement(By.name("startDate")).clear();
		getDriver().findElement(By.name("startDate")).sendKeys(startDate);  
		getDriver().findElement(By.name("endDate")).clear();
		getDriver().findElement(By.name("endDate")).sendKeys(endDate);  
		getDriver().findElement(By.id("searchBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"), "Processing..."));
		String table_Date = getDriver().findElement(By.xpath("//table[@id='notificationsTable']/tbody/tr/td[3]")).getText();
		testInfo.get().info("Date returned "+table_Date);
		TestUtils.checkDateBoundary(startDate, endDate, table_Date);
	}
	
	@Parameters ({"testEnv"})
	@Test (groups = { "Regression" })
	public void singleUserDeviceNotification(String testEnv) throws InterruptedException, 
		FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		getDriver().navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bulk_action_button")));
		Thread.sleep(1000);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("createLink")));
		getDriver().findElement(By.id("createLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='createNotificationModal']/div/div/div/h4")));
		
		if (TestUtils.isElementPresent("CSSSELECTOR", "span.select2-selection__clear")) {
			getDriver().findElement(By.cssSelector("span.select2-selection__clear")).click();
		}
		
		TestUtils.testTitle("Confirm that user cannot proceed without supplying all required fields");
		getDriver().findElement(By.id("saveBtn")).click();
		Thread.sleep(1000);
		Assertion.assertErrorValidationNotificationCACAdmin();
		
		// User notification
		TestUtils.testTitle("Test to Click SAVE button selecting Users target, dealer: "+dealerName +" and user:"+ user);
		singleNotificationForm("Users", dealerName, user, "Testing user notfication");
		Thread.sleep(500);
		
		// kit notification
		Thread.sleep(500);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("createLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='createNotificationModal']/div/div/div/h4")));
		TestUtils.testTitle("Test to Click SAVE button selecting Device target, dealer: "+dealerName +" and kit tag:"+ kitTag);
		singleNotificationForm("Devices", dealerName, kitTag, "Testing device notfication");
		Thread.sleep(1000);
	}
	
	@Test (groups = { "Regression" })
	public void singleGlobalNotification () throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		
		// Global notification
		Thread.sleep(500);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("createLink")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='createNotificationModal']/div/div/div/h4")));
		TestUtils.testTitle("Test to Save Notification after selecting Global target");
		
		// target group
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys("GLOBAL");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//ul[@id='select2-targetGroup-results']/li")).click();
		Thread.sleep(2000);
		
		getDriver().findElement(By.id("messageInput")).clear();
		getDriver().findElement(By.id("messageInput")).sendKeys("Testing global notfication");  
		getDriver().findElement(By.id("saveBtn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2"))); 
		TestUtils.assertSearchText("XPATH", "//h2", "Notification Creation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure that you want to proceed with this request?");
		
		// Click confirm button
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Successfully sent notification(s)')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Successfully sent notification(s)')]", "Successfully sent notification(s)");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click(); 
		
		Thread.sleep(1000);
	}
	
	@Test (groups = { "Regression" })
	@Parameters({ "server", "downloadPath" })
	public void bulkNotification (String server, String downloadPath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Create Bulk Notifications");

		Thread.sleep(500);
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//*[contains(text(),'Bulk Notifications')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bulkUpload']/div/div/div/h4")));
		TestUtils.assertSearchText("XPATH", "//div[@id='bulkUpload']/div/div/div/h4", "Bulk Notifications");
		
		TestUtils.testTitle("Click UPLOAD button without selecting a file (empty file path)");
		getDriver().findElement(By.xpath("//*[contains(text(),'Upload')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2 > h3")));
		TestUtils.assertSearchText("CSSSELECTOR", "h2 > h3", "Please ensure that the content of the document follows the specified template");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		
		// download user template
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//*[contains(text(),'Bulk Notifications')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bulkUpload']/div/div/div/h4")));
		getDriver().findElement(By.cssSelector("a.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(1000);
		if (TestUtils.isAlertPresents()) {
			getDriver().switchTo().alert().accept();
		}
		Thread.sleep(1000);
		
		String file = "Bulk_User_Notification.xls";
		String invalidFile = "image2.jpg";

		// Select an invalid file format
		TestUtils.testTitle("Select invalid file format and upload. eg jpeg " + invalidFile);
		TestUtils.uploadFile(By.id("uploadedFile"), invalidFile);
		getDriver().findElement(By.xpath("//*[contains(text(),'Upload')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2 > h3")));
		TestUtils.assertSearchText("CSSSELECTOR", "h2 > h3", "Please ensure that the content of the document follows the specified template");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		
		// download user template
		getDriver().findElement(By.id("bulk_action_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//*[contains(text(),'Bulk Notifications')]")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bulkUpload']/div/div/div/h4")));
		getDriver().findElement(By.cssSelector("a.btn.btn-link.btn-linkedin")).click();
		Thread.sleep(1000);
		
		// Select a valid file format
		TestUtils.testTitle("Select a valid file format and upload. eg xls" + file);
		TestUtils.uploadFile(By.id("uploadedFile"), file);
		getDriver().findElement(By.xpath("//*[contains(text(),'Upload')]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2 > h3")));
		Thread.sleep(800);
		TestUtils.assertSearchText("CSSSELECTOR", "h2 > h3", "Request received, See status in file downloaded.");
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("loader"), "Please wait..."));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("dataTables_processing"),
				"Processing..."));
		Thread.sleep(1000);
		
	}
	public void singleNotificationForm(String group, String dealerName, String kitUser, String message) throws InterruptedException, 
		FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
		TestUtils.testTitle("Creating new notification for: " + dealerName+ " and "+kitUser);

		// target group
		getDriver().findElement(By.xpath("//span/span/span")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//span/input")).sendKeys(group);
		getDriver().findElement(By.xpath("//ul[@id='select2-targetGroup-results']/li")).click();
		Thread.sleep(2000);
		
		// Dealer
		getDriver().findElement(By.xpath("//div[2]/div/div[2]/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//span/input")).clear();
		getDriver().findElement(By.xpath("//span/input")).sendKeys(dealerName);
		getDriver().findElement(By.xpath("//span[2]/ul/li")).click();
		Thread.sleep(1000);
		
		// Select Kit/User  
		getDriver().findElement(By.xpath("//div[3]/div/div[2]/span/span/span")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.cssSelector("input.select2-search__field")).sendKeys(kitUser);
		getDriver().findElement(By.xpath("//ul[@id='select2-devices-results']/li")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.id("messageInput")).clear();
		getDriver().findElement(By.id("messageInput")).sendKeys(message);  
		getDriver().findElement(By.id("saveBtn")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2"))); 
		
		// Click cancel button
		getDriver().findElement(By.cssSelector("button.swal2-cancel.swal2-styled")).click();
		Thread.sleep(500);
		
		// Click on Save button
		TestUtils.clickElement("ID", "saveBtn");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2"))); 
		TestUtils.assertSearchText("XPATH", "//h2", "Notification Creation");
		TestUtils.assertSearchText("CSSSELECTOR", "div.swal2-content", "Are you sure that you want to proceed with this request?");
		// Click confirm button
		getDriver().findElement(By.cssSelector("button.swal2-confirm.swal2-styled")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Successfully sent notification(s)')]")));
		TestUtils.assertSearchText("XPATH", "//span[contains(text(),'Successfully sent notification(s)')]", "Successfully sent notification(s)");
		getDriver().findElement(By.cssSelector("button.close > i.material-icons")).click();
		Thread.sleep(1000);
	}
	
	

}
