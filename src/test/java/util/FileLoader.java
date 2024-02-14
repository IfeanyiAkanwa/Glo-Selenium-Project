package util;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * @author soli
 * 
 * Using herokuapp for image uploading and download for webDriver remote.
 * http://the-internet.herokuapp.com/upload
 * http://the-internet.herokuapp.com/download
 *
 */
public class FileLoader extends TestBase{
	
	
	
	public static void upLoadFile () throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		String image = System.getProperty("user.dir") + "/files/image2.jpg";
		String blacklist = System.getProperty("user.dir") + "/files/blacklist_template.xls";
		String license = System.getProperty("user.dir") + "/files/SIMROP_License_Request.xls";
		String bulk = System.getProperty("user.dir") + "/files/Bulk_Onboard_Creation.xls";
		String bulk2 = System.getProperty("user.dir") + "/files/SIMROP_user.xlsx";
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(image);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(blacklist);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(license);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(bulk);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(bulk2);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
	}
	
	@Test 
	public static void downLoadFile (String myURL) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/download");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		getDriver().findElement(By.linkText("image2.jpg")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("blacklist_template.xls")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("SIMROP_License_Request.xls")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("Bulk_Onboard_Creation.xls")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.linkText("SIMROP_user.xlsx")).click();
		Thread.sleep(1000);
		
		// Navigate back to url
	    Thread.sleep(500);
	    getDriver().get(myURL);
	}
	

}
