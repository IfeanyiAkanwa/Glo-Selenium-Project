package util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;


public class Assertion extends TestBase {

	public static void userDetailsFormAssertion() throws Exception {

		String firstName = getDriver().findElement(By.id("firstName")).getAttribute("value");
		String lastName = getDriver().findElement(By.id("lastName")).getAttribute("value");
		String otherName = getDriver().findElement(By.id("otherName")).getAttribute("value");
		String email = getDriver().findElement(By.id("email")).getAttribute("value");
		String mobile = getDriver().findElement(By.id("mobile")).getAttribute("value");
		String gender = getDriver().findElement(By.id("select2-gender-container")).getText();
		String userType = getDriver().findElement(By.id("select2-userType-container")).getText();
		String role = getDriver().findElement(By.xpath("//span/ul")).getText();
		String zone = getDriver().findElement(By.id("select2-zone-container")).getText();
		String dealer = getDriver().findElement(By.id("select2-dealer-container")).getText();
		int count = getDriver().findElements(By.xpath("//span/ul/li")).size();
		String dealerCount = String.valueOf(count);
		String NA = "EMPTY";
		//System.out.println(count);

		String[] toList = {"First Name:" + firstName, "Last Name:" + lastName, "Other Name:" + otherName,
				"Email:" + email, "Mobile:" + mobile, "Gender:" + gender, "User:" + userType, "Role:" + role,
				"Zone:" + zone, "Dealer:" + dealer, "Dealer Count:" + dealerCount};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void dealerDetailsFormAssertion() throws Exception {

		String dealerName = getDriver().findElement(By.id("dealerName")).getAttribute("value");
		String dealerCode = getDriver().findElement(By.id("dealerCode")).getAttribute("value");
		String mobile = getDriver().findElement(By.id("mobile")).getAttribute("value");
		String email = getDriver().findElement(By.id("email")).getAttribute("value");
		String dealerType = getDriver().findElement(By.id("select2-dealerType-container")).getText();
		String dealerDivision = getDriver().findElement(By.id("select2-dealerDivision-container")).getText();
		String dealerState = getDriver().findElement(By.xpath("//span/ul")).getText();
		String dealerZone = getDriver().findElement(By.id("select2-dealerZone-container")).getText();
		String address = getDriver().findElement(By.id("address")).getText();
		String NA = "EMPTY";

		String[] toList = {"Dealer Name:" + dealerName, "Dealer Code:" + dealerCode, "Mobile Number:" + mobile,
				"Email:" + email, "Dealer Type:" + dealerType, "Dealer Division:" + dealerDivision,
				"Dealer State:" + dealerState, "Dealer Zone:" + dealerZone, "Address:" + address};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "<b> : " + val);
			}
		}
	}

	public static void viewDetailApprovedLicense() throws Exception {

		String NA = "EMPTY";

		String agentName = getDriver().findElement(By.id("agentName")).getAttribute("value");
		String approvedBy = getDriver().findElement(By.id("approvedBy")).getAttribute("value");
		String kitTag = getDriver().findElement(By.id("kitTag")).getAttribute("value");
		String macAddress = getDriver().findElement(By.id("macAddress")).getAttribute("value");
		String deviceId = getDriver().findElement(By.id("deviceId")).getAttribute("value");
		String email = getDriver().findElement(By.id("emailAddress")).getAttribute("value");
		String requestDate = getDriver().findElement(By.id("requestDate")).getAttribute("value");
		String approvedDate = getDriver().findElement(By.id("approvalDate")).getAttribute("value");

		String[] toList = {"Kit Tag:" + kitTag, "Mac Address:" + macAddress, "Device Id:" + deviceId, "Email:" + email,
				"Request Date:" + requestDate, "Approved Date:" + approvedDate, "Approved by:" + approvedBy,
				"Agent Name:" + agentName};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertDeviceDealerDetails() throws Exception {

		String NA = "EMPTY";

		String kitTag = getDriver().findElement(By.xpath("//td[2]")).getText();
		String macAddress = getDriver().findElement(By.xpath("//tr[2]/td[2]")).getText();
		String deviceType = getDriver().findElement(By.xpath("//tr[3]/td[2]")).getText();
		String deviceOwner = getDriver().findElement(By.xpath("//tr[4]/td[2]")).getText();
		String deviceID = getDriver().findElement(By.xpath("//tr[5]/td[2]")).getText();

		String dealer = getDriver().findElement(By.xpath("//div[4]/table/tbody/tr/td[2]")).getText();
		String dealerCode = getDriver().findElement(By.xpath("//div[4]/table/tbody/tr[2]/td[2]")).getText();
		String dealerType = getDriver().findElement(By.xpath("//div[4]/table/tbody/tr[3]/td[2]")).getText();
		String region = getDriver().findElement(By.xpath("//div[4]/table/tbody/tr[4]/td[2]")).getText();
		String subRegion = getDriver().findElement(By.xpath("//div[4]/table/tbody/tr[5]/td[2]")).getText();
		String state = getDriver().findElement(By.xpath("//tr[6]/td[2]")).getText();
		String lga = getDriver().findElement(By.xpath("//tr[7]/td[2]")).getText();

		String[] toList = {"Kit Tag:" + kitTag, "MacAddress:" + macAddress, "Device Type:" + deviceType,
				"Device Owner:" + deviceOwner, "Device ID:" + deviceID, "Dealer:" + dealer, "Dealer Code:" + dealerCode,
				"Dealer Type:" + dealerType, "Region:" + region, "Sub-Region:" + subRegion, "State:" + state,
				"LGA:" + lga};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " :  " + val);
			} catch (Error e) {
				testInfo.get().error(name + " :  " + val);
			}
		}
	}

	public static void assertOutletAgentDetails() throws Exception {

		String NA = "EMPTY";

		String outletName = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr/td[2]")).getText();
		String outletType = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr[2]/td[2]")).getText();
		String outletLocation = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr[3]/td[2]")).getText();
		String outLetOwnerName = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr[4]/td[2]")).getText();
		String outLetOwnerNumber = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr[5]/td[2]")).getText();
		String outletOwnerVtuNumber = getDriver().findElement(By.xpath("//div[5]/div/table/tbody/tr[6]/td[2]"))
				.getText();

		String username = getDriver().findElement(By.xpath("//div[5]/div[2]/table/tbody/tr/td[2]")).getText();
		String firstName = getDriver().findElement(By.xpath("//div[5]/div[2]/table/tbody/tr[2]/td[2]")).getText();
		String surName = getDriver().findElement(By.xpath("//div[5]/div[2]/table/tbody/tr[3]/td[2]")).getText();
		String gender = getDriver().findElement(By.xpath("//div[5]/div[2]/table/tbody/tr[4]/td[2]")).getText();
		String phoneNumber = getDriver().findElement(By.xpath("//div[5]/div[2]/table/tbody/tr[5]/td[2]")).getText();
		String agentVtuNumber = getDriver().findElement(By.xpath("//div[2]/table/tbody/tr[6]/td[2]")).getText();
		String agentDyaNunabNumber = getDriver().findElement(By.xpath("//div[2]/table/tbody/tr[7]/td[2]")).getText();

		String[] toList = {"Outlet Name:" + outletName, "Outlet Type:" + outletType,
				"Outlet Location:" + outletLocation, "OutLet Owner Name:" + outLetOwnerName,
				"Out LetOwner Number:" + outLetOwnerNumber, "Outlet Owner Vtu Number:" + outletOwnerVtuNumber,
				"Username:" + username, "First Name:" + firstName, "SurName:" + surName, "Gender:" + gender,
				"Phone Number:" + phoneNumber, "Agent VTU Number:" + agentVtuNumber,
				"Agent DYA Nunab Number:" + agentDyaNunabNumber};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " :  " + val);
			} catch (Error e) {
				testInfo.get().error(name + " :  " + val);
			}
		}
	}

	public static void assertMobileInfo() throws Exception {

		String NA = "N/A";

		String Tag = getDriver().findElement(By.xpath("//div[@id='collapseTwo']/div/div/table/tbody/tr/td[2]"))
				.getText();
		String dealer = getDriver().findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr/td[2]"))
				.getText();
		String code = getDriver().findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[2]/td[2]"))
				.getText();
		String dealerType = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[3]/td[2]")).getText();
		String division = getDriver().findElement(By.xpath("//div[@id='collapseTwo']/div/div/table/tbody/tr[2]/td[2]"))
				.getText();
		String deviceStatus = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[4]/td[2]")).getText();
		String todayRegCount = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[5]/td[2]")).getText();
		String lastHeartBeatTime = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[6]/td[2]")).getText();
		String installer = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[7]/td[2]")).getText();
		String mac = getDriver().findElement(By.xpath("//div[@id='collapseTwo']/div/div[2]/table/tbody/tr[8]/td[2]"))
				.getText();
		String networkStrength = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr/td[2]")).getText();
		String isDeviceRooted = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[2]/td[2]")).getText();
		String OS_Name = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[3]/td[2]")).getText();
		String OS_Version = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[4]/td[2]")).getText();
		String processorSpeed = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[5]/td[2]")).getText();
		String appVersion = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[6]/td[2]")).getText();
		String deviceType = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[7]/td[2]")).getText();
		String deviceID = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[3]/table/tbody/tr[8]/td[2]")).getText();
		String storageUsed = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr/td[2]")).getText();
		String storageAvailable = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[2]/td[2]")).getText();
		String totalStorage = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[3]/td[2]")).getText();
		String RAM_Size = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[4]/td[2]")).getText();
		String lastLoggedInUser = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[5]/td[2]")).getText();
		String lastSyncTime = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[6]/td[2]")).getText();
		String kitModel = getDriver()
				.findElement(By.xpath("//div[@id='collapseTwo']/div/div[4]/table/tbody/tr[7]/td[2]")).getText();

		String[] toList = {"Tag:" + Tag, "Dealer:" + dealer, "Code:" + code, "DealerType:" + dealerType,
				"Division:" + division, "deviceStatus:" + deviceStatus, "todayRegCount:" + todayRegCount,
				"lastHeartBeatTime:" + lastHeartBeatTime, "installer:" + installer, "mac:" + mac,
				"networkStrength:" + networkStrength, "isDeviceRooted:" + isDeviceRooted, "OS_Name:" + OS_Name,
				"OS_Version:" + OS_Version, "ProcessorSpeed:" + processorSpeed, "appVersion:" + appVersion,
				"deviceType:" + deviceType, "deviceID:" + deviceID, "storageUsed:" + storageUsed,
				"storageAvailable:" + storageAvailable, "totalStorage:" + totalStorage, "RAM_Size:" + RAM_Size,
				"lastLoggedInUser:" + lastLoggedInUser, "lastSyncTime:" + lastSyncTime, "kitModel:" + kitModel};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				try{
					val = fields[1];
				}catch (Exception ee){
					val = "N/A";
				}

				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void assertLocationInfo() throws Exception {

		String NA = "N/A";

		String defaultLocation = getDriver().findElement(By.id("defaultLocation")).getText();
		String currentLocation = getDriver().findElement(By.id("currentLocation")).getText();
		String lastLocationUpdate = getDriver().findElement(By.id("lastLocationUpdate")).getText();
		String locationAccuracy = getDriver().findElement(By.id("locationAccuracy")).getText();
		String outletName = getDriver().findElement(By.id("outletName")).getText();

		String[] toList = {"defaultLocation:" + defaultLocation, "currentLocation:" + currentLocation,
				"lastLocationUpdate:" + lastLocationUpdate, "locationAccuracy:" + locationAccuracy,
				"outletName:" + outletName};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				try{
					val = fields[1];
				}catch (Exception ee){
					val = "N/A";
				}

				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "<b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void assertSubscriberInfo() throws Exception {

		String firstname = getDriver().findElement(By.xpath("//td[2]")).getText();
		String surname = getDriver().findElement(By.xpath("//tr[2]/td[2]")).getText();
		String othername = getDriver().findElement(By.xpath("//tr[3]/td[2]")).getText();
//		String birthday = getDriver().findElement(By.xpath("//tr[4]/td[2]")).getText();
		
//		TestUtils.scrollToElement("XPATH", "//tr[4]/td[2]");
//		String gender = getDriver().findElement(By.xpath("//tr[5]/td[2]")).getText();
//		String nationality = getDriver().findElement(By.xpath("//tr[6]/td[2]")).getText();
//		String stateOfOrigin = getDriver().findElement(By.xpath("//tr[7]/td[2]")).getText();
//		String lgaOfOrigin = getDriver().findElement(By.xpath("//tr[8]/td[2]")).getText();
//		String occupation = getDriver().findElement(By.xpath("//tr[9]/td[2]")).getText();
//		String subscriberType = getDriver().findElement(By.xpath("//tr[10]/td[2]")).getText();
//		String registrationLga = getDriver().findElement(By.xpath("//tr[11]/td[2]")).getText();
//		String residentialAddress = getDriver().findElement(By.xpath("//tr[12]/td[2]")).getText();
//		String areaOfResidence = getDriver().findElement(By.xpath("//tr[13]/td[2]")).getText();
		String residentialAddressLga = getDriver().findElement(By.xpath("//tr[4]/td[2]")).getText();
		String residentialAddressState = getDriver().findElement(By.xpath("//tr[5]/td[2]")).getText();
		
//		TestUtils.scrollToElement("XPATH", "//tr[15]/td[2]");
//		String email = getDriver().findElement(By.xpath("//tr[16]/td[2]")).getText();
//		String registrationType = getDriver().findElement(By.xpath("//tr[17]/td[2]")).getText();
//		String companyId = getDriver().findElement(By.xpath("//tr[18]/td[2]")).getText();
//		String companyName = getDriver().findElement(By.xpath("//tr[19]/td[2]")).getText();
//		String postalCode = getDriver().findElement(By.xpath("//tr[20]/td[2]")).getText();
//		String companyAddress = getDriver().findElement(By.xpath("//tr[21]/td[2]")).getText();
//		String companyAddressLga = getDriver().findElement(By.xpath("//tr[22]/td[2]")).getText();
//		String companyAddressState = getDriver().findElement(By.xpath("//tr[23]/td[2]")).getText();
//		String companyAddressPostalCode = getDriver().findElement(By.xpath("//tr[24]/td[2]")).getText();
		String phoneNumber = getDriver().findElement(By.xpath("//tr[6]/td[2]")).getText();
//		String msisdnCategory = getDriver().findElement(By.xpath("//tr[26]/td[2]")).getText();
//		String alternatePhoneNumber = getDriver().findElement(By.xpath("//tr[27]/td[2]")).getText();
		
//		TestUtils.scrollToElement("XPATH", "//tr[27]/td[2]");
//		String uniqueId = getDriver().findElement(By.xpath("//tr[28]/td[2]")).getText();
//		String mothersMaidenName = getDriver().findElement(By.xpath("//tr[29]/td[2]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//tr[7]/td[2]")).getText();
//		String passportExpiryDate = getDriver().findElement(By.xpath("//tr[31]/td[2]")).getText();
//		String countryIssueCode = getDriver().findElement(By.xpath("//tr[32]/td[2]")).getText();
//		String passportNumber = getDriver().findElement(By.xpath("//tr[33]/td[2]")).getText();
//		String identificationType = getDriver().findElement(By.xpath("//tr[34]/td[2]")).getText();
//		String syncDateTime = getDriver().findElement(By.xpath("//tr[35]/td[2]")).getText();
		String registrationDateTime = getDriver().findElement(By.xpath("//tr[8]/td[2]")).getText();
//		String passportImageOverrideReason = getDriver().findElement(By.xpath("//tr[37]/td[2]")).getText();
		String agentName = getDriver().findElement(By.xpath("//tr[9]/td[2]")).getText();
//		String specialDataCaptured = getDriver().findElement(By.xpath("//tr[39]/td[2]")).getText();
		
		
		String empty = "";
		String NA = "N/A";
		
		Map<String, String> fields = new HashMap<>();
		fields.put("Firstname:" , firstname);
		fields.put("Surname:" , surname);
		fields.put("Othername:" , othername);
//		fields.put("Birthday:" , birthday);
//		fields.put("Gender:" , gender);
//		fields.put("Nationality:" , nationality);
//		fields.put("StateOfOrigin:" , stateOfOrigin);
//		fields.put("LgaOfOrigin:" , lgaOfOrigin);
//		fields.put("Occupation:" , occupation);
//		fields.put("SubscriberType:" , subscriberType);
//		fields.put("RegistrationLga:" , registrationLga);
//		fields.put("ResidentialAddress:" , residentialAddress);
//		fields.put("AreaOfResidence:" , areaOfResidence);
		fields.put("ResidentialAddressLga:" , residentialAddressLga);
		fields.put("ResidentialAddressState:" , residentialAddressState);
//		fields.put("Email:" , email);
//		fields.put("RegistrationType:" , registrationType);
//		fields.put("CompanyId:" , companyId);
//		fields.put("CompanyName:" , companyName);
//		fields.put("PostalCode:" , postalCode);
//		fields.put("CompanyAddress:" , companyAddress);
//		fields.put("CompanyAddressLga:" , companyAddressLga);
//		fields.put("CompanyAddressState:" , companyAddressState);
//		fields.put("CompanyAddressPostalCode:" , companyAddressPostalCode);
		fields.put("PhoneNumber:" , phoneNumber);
//		fields.put("MsisdnCategory:" , msisdnCategory);
//		fields.put("AlternatePhoneNumber:" , alternatePhoneNumber);
//		fields.put("UniqueId:" , uniqueId);
//		fields.put("MothersMaidenName:" , mothersMaidenName);
		fields.put("KitTag:" , kitTag);
//		fields.put("PassportExpiryDate:" , passportExpiryDate);
//		fields.put("CountryIssueCode:" , countryIssueCode);
//		fields.put("PassportNumber:" , passportNumber);
//		fields.put("IdentificationType:" , identificationType);
//		fields.put("SyncDateTime:" , syncDateTime);
		fields.put("RegistrationDateTime:" , registrationDateTime);
//		fields.put("PassportImageOverrideReason:" , passportImageOverrideReason);
		fields.put("AgentName:" , agentName);
//		fields.put("SpecialDataCaptured:" , specialDataCaptured);


		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), NA);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	
	public static void viewTaggingApprovalDetails() throws InterruptedException {
		String NA = "N/A";
		String deviceId = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		String deviceTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]"))
				.getText();
		String previousTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]"))
				.getText();
		String requestedBy = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]"))
				.getText();
		String appVersion = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]"))
				.getText();
		String dateRequested = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		String dealer = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]"))
				.getText();
		String dealerCode = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]"))
				.getText();
		String dealerRegion = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]"))
				.getText();
		String dealerSubRegion = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[10]/td[2]")).getText();
		String dealerState = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[11]/td[2]"))
				.getText();

		String[] toList = {"Device Id:" + deviceId, "Device Tag:" + deviceTag, "Previous Tag:" + previousTag,
				"Requested By:" + requestedBy, "App Version:" + appVersion, "Date Requested:" + dateRequested,
				"Dealer:" + dealer, "Dealer Code:" + dealerCode, "Dealer Region:" + dealerRegion,
				"Dealer SubRegion:" + dealerSubRegion, "Dealer State:" + dealerState};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void viewTaggingApprovalDetailsDown() throws InterruptedException {
		String NA = "N/A";
		String stateOfDeploy = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[12]/td[2]"))
				.getText();
		String lgaOfDeploy = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[13]/td[2]"))
				.getText();
		String dealerSalesTerritory = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[14]/td[2]"))
				.getText();
		String status = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[15]/td[2]"))
				.getText();

		String[] toList = {"State Of Deploy:" + stateOfDeploy, "Device Id:" + lgaOfDeploy, "Dealer sales Territory:" + dealerSalesTerritory, "Status:" + status};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b>: " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void assertDeviceDealerInformation(String testEnv) throws InterruptedException {
		TestUtils.testTitle("Assert Device, Dealer, Agent and Additional Information");
		String NA = "EMPTY";

		// Device Information
		String kitTag = getDriver().findElement(By.id("kitPk")).getAttribute("value");
		String macAddress = getDriver().findElement(By.id("mac")).getAttribute("value");
		String deviceType = getDriver().findElement(By.id("deviceType")).getAttribute("value");
		String deviceOwner = getDriver().findElement(By.id("deviceOwner")).getAttribute("value");
		String deviceID = getDriver().findElement(By.id("deviceId")).getAttribute("value");

		// Dealer Information
		String dealer = getDriver().findElement(By.xpath("//div[2]/div/input")).getAttribute("value");
		String dealerCode = getDriver().findElement(By.xpath("//div[3]/form/div/div[3]/div/input"))
				.getAttribute("value");
		String dealerType = getDriver().findElement(By.xpath("//div[3]/form/div/div[4]/div/input"))
				.getAttribute("value");
		if (!getDriver().findElement(By.xpath("//div[9]/div/span/span/span")).isDisplayed()) {
			TestUtils.scrollToElement("XPATH", "//div[9]/div/span/span/span");
			Thread.sleep(500);
		} else {
			TestUtils.scrollToElement("ID", "lastLogin");
			Thread.sleep(500);
		}
		String region = getDriver().findElement(By.id("select2-region-container")).getAttribute("title");
		String subRegion = getDriver().findElement(By.id("select2-zone-container")).getAttribute("title");
		String state = getDriver().findElement(By.id("select2-state-container")).getAttribute("title");
		String lga = getDriver().findElement(By.id("select2-lga-container")).getAttribute("title");
		String territory = getDriver().findElement(By.id("select2-territory-container")).getAttribute("title");
		String area = getDriver().findElement(By.id("select2-area-container")).getAttribute("title");

		// Agent Information or ID CARD
		String userName;
		if (testEnv.equalsIgnoreCase("stagingData")) {
			userName = getDriver().findElement(By.id("select2-agentPkSelect-container")).getAttribute("title");
		} else if (testEnv.equalsIgnoreCase("prodData")) {
			userName = getDriver().findElement(By.id("emailAddress")).getAttribute("value");
		} else {
			userName = getDriver().findElement(By.xpath("//div[2]/div/div[2]/div/form/div/div/div/span/span/span")).getAttribute("title");
		}
		String firstName = getDriver().findElement(By.id("firstName")).getAttribute("value");
		String surName = getDriver().findElement(By.id("surname")).getAttribute("value");
		String agentNubanNum = getDriver().findElement(By.id("agentNubanNumber")).getAttribute("value");
		String agentVtuNum = getDriver().findElement(By.id("agentVtuNumber")).getAttribute("value");
		String phoNum = getDriver().findElement(By.id("phoneNumber")).getAttribute("value");
		String outlet = getDriver().findElement(By.id("select2-outlet-container")).getAttribute("title");
		String outletName = getDriver().findElement(By.id("outletName")).getAttribute("value");
		TestUtils.scrollToElement("ID", "outletOwnerVtu");
		String centreID = getDriver().findElement(By.id("centreId")).getAttribute("value");
		String outletType = getDriver().findElement(By.id("select2-outletTypeSelect-container")).getAttribute("title");
		String locationAddress = getDriver().findElement(By.id("coordinateAddress")).getText();
		String outletOwnerName = getDriver().findElement(By.id("outletOwnerName")).getAttribute("value");
		String outletOwnerNum = getDriver().findElement(By.id("outletOwnerNumber")).getAttribute("value");
		String outletOwnerVtu = getDriver().findElement(By.id("outletOwnerVtu")).getAttribute("value");

		// Additional Information
		String deviceStatus = getDriver().findElement(By.id("deviceStatus")).getAttribute("value");
		String accActivationStatus = getDriver().findElement(By.id("activationStatus")).getAttribute("value");
		String accLockStatus = getDriver().findElement(By.id("lockStatus")).getAttribute("value");
		String lastLogin = getDriver().findElement(By.id("lastLogin")).getAttribute("value");

		TestUtils.scrollUntilElementIsVisible("ID", "firstName");
		Thread.sleep(500);

		String[] toList = {"Kit Tag:" + kitTag, "MacAddress:" + macAddress, "Device Type:" + deviceType,
				"Device Owner:" + deviceOwner, "Device ID:" + deviceID, "Dealer:" + dealer, "Dealer Code:" + dealerCode,
				"Dealer Type:" + dealerType, "Region:" + region, "Sub-Region:" + subRegion, "State:" + state,
				"LGA:" + lga, "Territory:" + territory, "Area:" + area, "Device Status:" + deviceStatus,
				"Account Activation Status:" + accActivationStatus, "Account Lock Status:" + accLockStatus,
				"Last Login:" + lastLogin, "Username:" + userName, "First Name:" + firstName, "Surname:" + surName,
				"Agent Nuban Number:" + agentNubanNum, "Agent VTU Number:" + agentVtuNum, "Phone Number:" + phoNum,
				"Outlet:" + outlet, "Outlet Name:" + outletName, "Center ID:" + centreID, "Outlet Type:" + outletType,
				"Location Address:" + locationAddress, "Outlet Owner Name:" + outletOwnerName,
				"Outlet Owner Number:" + outletOwnerNum, "Outlet Owner VTU:" + outletOwnerVtu};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> :  " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> :  " + val);
			}
		}
	}

	public static void assertOutletDetail() throws InterruptedException {
		String NA = "N/A";
		String outletName = getDriver().findElement(By.id("outlet-name")).getText();
		String outletCenterID = getDriver().findElement(By.id("outlet-center-id")).getText();
		String ownerName = getDriver().findElement(By.id("owner-name")).getText();
		String phoneNumber = getDriver().findElement(By.id("owner-phonenumber")).getText();
		String vtuNumber = getDriver().findElement(By.id("vtu-number")).getText();
		String outletType = getDriver().findElement(By.id("outlet-type")).getText();
		String address = getDriver().findElement(By.id("outlet-address")).getText();
		String longitude = getDriver().findElement(By.id("longitude")).getText();
		String latitude = getDriver().findElement(By.id("latitude")).getText();
		String dealer = getDriver().findElement(By.id("dealer-name")).getText();

		String[] toList = {"Outlet Name:" + outletName, "Outlet Center ID:" + outletCenterID, "Owner Name:" + ownerName, "Phone Number:" + phoneNumber,
				"Vtu Number:" + vtuNumber, "Outlet Type:" + outletType, "Address:" + address, "Longitude:" + longitude,
				"Latitude:" + latitude, "Dealer:" + dealer};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void assertAirtimeTransInfoCAC() throws InterruptedException {

		String NA = "N/A";

		String transactionID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		String transactionDate = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]"))
				.getText();
		String agentUsername = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]"))
				.getText();
		String agentVTUNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]"))
				.getText();
		String airtimeAmount = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		String dealerName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]"))
				.getText();
		String deviceTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]"))
				.getText();
		String deviceID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]"))
				.getText();
		String subPhoneNumber = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[10]/td[2]")).getText();

		String[] toList = {"transactionID:" + transactionID, "transactionDate:" + transactionDate, "status:" + status,
				"agentUsername:" + agentUsername, "agentVTUNumber:" + agentVTUNumber, "dealer name:" + dealerName,
				"subPhoneNumber:" + subPhoneNumber, "deviceTag:" + deviceTag, "deviceID:" + deviceID,
				"airtimeAmount:" + airtimeAmount};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertDataTransInfoCAC() throws InterruptedException {

		String NA = "N/A";
		String transactionID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		String transactionDate = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]"))
				.getText();
		String agentUsername = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]"))
				.getText();
		String agentVTUNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]"))
				.getText();
		String airtimeAmount = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		String dealerName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]"))
				.getText();
		String deviceTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]"))
				.getText();
		String deviceID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]"))
				.getText();
		String subPhoneNumber = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[10]/td[2]")).getText();
		String dataSize = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[11]/td[2]"))
				.getText();

		String[] toList = {"transactionID:" + transactionID, "transactionDate:" + transactionDate, "status:" + status,
				"agentUsername:" + agentUsername, "agentVTUNumber:" + agentVTUNumber, "dealer name:" + dealerName,
				"subPhoneNumber:" + subPhoneNumber, "deviceTag:" + deviceTag, "deviceID:" + deviceID,
				"airtimeAmount:" + airtimeAmount, "Data Size:" + dataSize};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertB2BDetail() throws InterruptedException {
		String NA = "Empty";

		String dealer = getDriver().findElement(By.id("dealer")).getText();
		String kitTag = getDriver().findElement(By.id("kit_tag")).getText();
		String macAddress = getDriver().findElement(By.id("mac_address")).getText();
		String b2bCode = getDriver().findElement(By.id("b2b_code")).getText();
		String b2bLocationDescription = getDriver().findElement(By.id("b2b_location_description")).getText();
		String address = getDriver().findElement(By.id("b2b_location")).getText();
		String reqDescription = getDriver().findElement(By.id("b2b_description")).getText();
		String dateReturned = getDriver().findElement(By.id("dateReturned")).getText();
		String approvalStatus = getDriver().findElement(By.id("status")).getText();
		String resolutionMessage = getDriver().findElement(By.id("resolutionMessage")).getText();
		String resolutionDate = getDriver().findElement(By.id("resolutionDate")).getText();

		String[] toList = {"Dealer: " + dealer, "KitTag: " + kitTag, "MacAddress: " + macAddress,
				"b2bCode: " + b2bCode, "B2B Location/Description: " + b2bLocationDescription, "Address: " + address,
				"Request Description: " + reqDescription, "Date Returned: " + dateReturned,
				"Approval Status: " + approvalStatus, "ResolutionMessage:" + resolutionMessage,
				"ResolutionDate: " + resolutionDate};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertB2BTableValue() throws InterruptedException {
		String NA = "Empty";
		String serialNo = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td")).getText();
		String dealer = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[2]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[3]")).getText();
		String status = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[4]")).getText();
		String resolvedBy = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[5]"))
				.getText();
		String dateReturned = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[6]"))
				.getText();
		String resolutionDate = getDriver().findElement(By.xpath("//table[@id='b2bRequestTable']/tbody/tr/td[7]"))
				.getText();

		String[] toList = {"Serial No:" + serialNo, "Dealer:" + dealer, "KitTag:" + kitTag, "Status:" + status,
				"ResolvedBy:" + resolvedBy, "Date Returned:" + dateReturned, "Resolution Date:" + resolutionDate};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertKitRetrievalDetail() throws InterruptedException {
		String NA = "N/A";

		String kitTag = getDriver().findElement(By.id("kit_tag")).getText();
		String deviceID = getDriver().findElement(By.id("device_id")).getText();
		String assignedAgent = getDriver().findElement(By.id("assigned_agent")).getText();
		String retrievalReason = getDriver().findElement(By.id("retrieval_reason")).getText();
		String retrievedBy = getDriver().findElement(By.id("dealer_email")).getText();
		String dealerComment = getDriver().findElement(By.id("feedback")).getText();
		String dateRetrieved = getDriver().findElement(By.id("date_retrieved")).getText();

		String[] toList = {"Kit Tag:" + kitTag, "Device ID :" + deviceID, "Assigned agent: " + assignedAgent,
				"Kit Retrieval reason:" + retrievalReason, "Kit Retrieved By:" + retrievedBy,
				"Dealer's Comments:" + dealerComment, "Date Retrieved:" + dateRetrieved};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertKitRetieval() throws Exception {

		String NA = "EMPTY";

		String kitTag = getDriver().findElement(By.id("retrieveMappingId1")).getAttribute("value");
		String deviceID = getDriver().findElement(By.id("deviceId")).getAttribute("value");
		String assignedAgent = getDriver().findElement(By.id("assignedAgent")).getAttribute("value");

		String[] toList = {"Kit Tag:" + kitTag, "Device Id:" + deviceID, "Agent Name:" + assignedAgent};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertNewKitAssignment() throws Exception {

		String NA = "EMPTY";

		String mac = getDriver().findElement(By.id("mac")).getAttribute("value");
		String deviceType = getDriver().findElement(By.id("select2-deviceType-container")).getAttribute("title");
		String deviceOwner = getDriver().findElement(By.id("select2-deviceOwner-container")).getAttribute("title");

		String[] toList = {"Mac Address:" + mac, "Device Type:" + deviceType, "Device Ownner:" + deviceOwner};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}

	public static void dealerAutopopulatedDetails() throws Exception {

		String NA = "EMPTY";

		String dealer = getDriver().findElement(By.xpath("//div[@id='dealer']/div/div/div/input"))
				.getAttribute("value");
		String deviceCode = getDriver().findElement(By.xpath("//div[@id='dealer']/div/div[2]/div/input"))
				.getAttribute("value");
		String deviceType = getDriver().findElement(By.xpath("//div[@id='dealer']/div/div[3]/div/input"))
				.getAttribute("value");
		String divisionType = getDriver().findElement(By.xpath("//div[@id='dealer']/div/div[10]/div/input"))
				.getAttribute("value");

		String[] toList = {"Dealer:" + dealer, "Device Code:" + deviceCode, "Device Type:" + deviceType, "Division Type:" + divisionType};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}

	public static void assertExistingAgentInformation() throws Exception {

		String NA = "EMPTY";
		String username = getDriver().findElement(By.id("select2-agentPk-container")).getAttribute("title");
		String firstName = getDriver().findElement(By.id("firstName")).getAttribute("value");
		String surName = getDriver().findElement(By.id("surname")).getAttribute("value");
		String phoneNumber = getDriver().findElement(By.id("phoneNumber")).getAttribute("value");
		String nubanNumber = getDriver().findElement(By.id("agentNubanNumber")).getAttribute("value");
		String vtuNumber = getDriver().findElement(By.id("agentVtuNumber")).getAttribute("value");

		String[] toList = {"username:" + username, "First Name:" + firstName, "Surname:" + surName, "Phone Number:" + phoneNumber,
				"Nuban Number:" + nubanNumber, "VTU Number:" + vtuNumber};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "<b>: " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "<b>: " + val);
			}
		}
	}

	public static void assertRequisitionDetail() throws InterruptedException {
		String NA = "N/A";
		String dealer = getDriver().findElement(By.id("dealerName")).getText();
		String dealerCode = getDriver().findElement(By.id("dealerCode")).getText();
		String dealerStates = getDriver().findElement(By.id("dealerStates")).getText();
		String stateOfDeploy = getDriver().findElement(By.id("sod")).getText();
		String lgaOfDeploy = getDriver().findElement(By.id("lod")).getText();
		String areaOfDeploy = getDriver().findElement(By.id("aod")).getText();
		String deviceReq = getDriver().findElement(By.id("deviceRequested")).getText();
		String status = getDriver().findElement(By.id("reason")).getText();

		String[] toList = {"Dealer:" + dealer, "Dealer Code:" + dealerCode, "Dealer States:" + dealerStates,
				"State Of Deployment:" + stateOfDeploy, "Lga Of Deployment:" + lgaOfDeploy,
				"Area Of Deployment:" + areaOfDeploy, "Device Req:" + deviceReq, "Status:" + status};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertKitIssueViewDetailCAC() throws InterruptedException {
		String NA = "EMPTY";
		String issueID = getDriver().findElement(By.id("issue_id")).getText();
		String deviceId = getDriver().findElement(By.xpath("//div[2]/div/table/tbody/tr[2]/td[2]")).getText();
		String issueSummary = getDriver().findElement(By.id("issue_summary")).getText();
		String issueCategory = getDriver().findElement(By.id("issue_category")).getText();
		String kitTag = getDriver().findElement(By.id("kit_tag")).getText();
		String appVersion = getDriver().findElement(By.id("app_version")).getText();
		String description = getDriver().findElement(By.id("description")).getText();
		String resolutionMessage = getDriver().findElement(By.id("resolutionMessage")).getText();
		TestUtils.scrollToElement("ID", "closeModal");
		String resolved = getDriver().findElement(By.id("resolvedBy")).getText();
		String dateLogged = getDriver().findElement(By.id("submissionDate")).getText();
		String resolutionDate = getDriver().findElement(By.id("resolutionDate")).getText();

		String[] toList = {"issue Id:" + issueID, "Device ID: " + deviceId, "Issue Summary:" + issueSummary,
				"Issue Category:" + issueCategory, "Kit Tag:" + kitTag, "Application Version: " + appVersion,
				"Description:" + description, "Resolution Message:" + resolutionMessage, "Resolved:" + resolved,
				"Date Logged:" + dateLogged, "Resolution Date:" + resolutionDate};

		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertAirtimeTransInfoDealer() throws Exception {

		String NA = "N/A";

		String transactionID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		String transactionDate = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]"))
				.getText();
		String agentUsername = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]"))
				.getText();
		String agentVTUNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]"))
				.getText();
		String subPhoneNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		String deviceTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]"))
				.getText();
		String deviceID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]"))
				.getText();
		String airtimeAmount = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]"))
				.getText();

		String[] toList = {"transactionID:" + transactionID, "transactionDate:" + transactionDate, "status:" + status,
				"agentUsername:" + agentUsername, "agentVTUNumber:" + agentVTUNumber,
				"subPhoneNumber:" + subPhoneNumber, "deviceTag:" + deviceTag, "deviceID:" + deviceID,
				"airtimeAmount:" + airtimeAmount};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertDataTransInfoDealer() throws Exception {

		String NA = "N/A";

		String transactionID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]"))
				.getText();
		String transactionDate = getDriver()
				.findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]"))
				.getText();
		String agentUsername = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]"))
				.getText();
		String agentVTUNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]"))
				.getText();
		String subPhoneNumber = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		String deviceTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]"))
				.getText();
		String deviceID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]"))
				.getText();
		String dataSize = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]"))
				.getText();
		String dataAmonut = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[10]/td[2]"))
				.getText();

		String[] toList = {"transactionID:" + transactionID, "transactionDate:" + transactionDate, "status:" + status,
				"agentUsername:" + agentUsername, "agentVTUNumber:" + agentVTUNumber,
				"subPhoneNumber:" + subPhoneNumber, "deviceTag:" + deviceTag, "deviceID:" + deviceID,
				"dataSize:" + dataSize, "dataAmonut:" + dataAmonut};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertDeviceRequisitionDetailsDealer() throws InterruptedException {
		String NA = "N/A";
		String deviceCount = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/td[2]")).getText();
		String dateReq = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[2]/td")).getText();
		String stateOfDeploy = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[3]/td[2]")).getText();
		String lgaOfDeploy = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[4]/td[2]")).getText();
		String areaOfDeploy = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[5]/td[2]")).getText();
		String reqReason = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[6]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[7]/td")).getText();

		String[] toList = {"Device Count:" + deviceCount, "Date request:" + dateReq,
				"State Of Deployment:" + stateOfDeploy, "Lga Of Deployment:" + lgaOfDeploy,
				"Area Of Deployment:" + areaOfDeploy, "Request Reason:" + reqReason, "Status:" + status};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertWorkflowDetail() throws InterruptedException {

		String NA = "N/A";

		String pk = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/td[2]")).getText();
		String activity = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[2]/td[2]")).getText();
		String order = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[3]/td[2]")).getText();
		String workflowName = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[4]/td[2]")).getText();
		String description = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[5]/td[2]")).getText();
		String date = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[6]/td[2]")).getText();
		String status = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[7]/td[2]")).getText();
		String deleted = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[8]/td[2]")).getText();
		String roles = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr[9]/td[2]")).getText();

		String[] toList = {"PK: " + pk, "Activity: " + activity, "Order: " + order, "Workflow: " + workflowName,
				"Description: " + description, "Status: " + status, "Deleted: " + deleted, "Roles: " + roles,
				"Date: " + date};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "</b> : " + val);
			}
		}
	}

	public static void assertOnboardingRequestDetailsDealer() throws InterruptedException {
		String NA = "N/A";
		String agentName = getDriver().findElement(By.id("agentName")).getText();
		String agentEmail = getDriver().findElement(By.id("agentEmail")).getText();
		String agentVtuNum = getDriver().findElement(By.id("vtuNumber")).getText();
		String agentPhoNum = getDriver().findElement(By.id("agentPhoneNumber")).getText();
		String dateReq = getDriver().findElement(By.id("dateRequested")).getText();
		String dateApproved = getDriver().findElement(By.id("dateApproved")).getText();
		String approvedBy = getDriver().findElement(By.id("approvedBy")).getText();
		String activationStatus = getDriver().findElement(By.id("activationStatus")).getText();
		String status = getDriver().findElement(By.id("vStatus")).getText();

		String[] toList = {"Agent Name:" + agentName, "Agent Email:" + agentEmail, "Agent VTU Number:" + agentVtuNum,
				"Agent Phone Number:" + agentPhoNum, "Date Requested:" + dateReq, "Date Approved:" + dateApproved,
				"Approved By:" + approvedBy, "Activation Status:" + activationStatus, "Status:" + status};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertVtuOnboardAgentDetailsDealer() throws InterruptedException {
		String NA = "N/A";
		String agentName = getDriver().findElement(By.id("agentname")).getAttribute("value");
		;
		String agentPhoNum = getDriver().findElement(By.id("agentphonenumber")).getAttribute("value");

		String[] toList = {"Agent Name:" + agentName, "Agent Phone Number:" + agentPhoNum};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void assertViewDetailsModalKitIssueLogDealer() throws InterruptedException {

		String issueID = getDriver().findElement(By.id("vIssueId")).getText();
		String deviceID = getDriver().findElement(By.id("vDeviceId")).getText();
		String status = getDriver().findElement(By.id("vStatus")).getText();
		String issueSummary = getDriver().findElement(By.id("vIssueSummary")).getText();
		String kitTag = getDriver().findElement(By.id("vkit")).getText();
		String description = getDriver().findElement(By.id("vDescription")).getText();
		String resolutionMessage = getDriver().findElement(By.id("vResolution")).getText();
		TestUtils.scrollToElement("ID", "vSlaStatus");
		String resolvedBy = getDriver().findElement(By.id("vResolvedBy")).getText();
		String dateLogged = getDriver().findElement(By.id("vDateLogged")).getText();
		String resolutionDate = getDriver().findElement(By.id("vResolutionDate")).getText();
		String slaStatus = getDriver().findElement(By.id("vSlaStatus")).getText();

		String NA = "N/A";
		String[] toList = {"Issue ID: " + issueID, "Device ID: " + deviceID, "Status: " + status,
				"Issue Summary: " + issueSummary, "Kit Tag: " + kitTag, "Description: " + description,
				"Resolution Message: " + resolutionMessage, "Resolved By: " + resolvedBy, "Date Logged: " + dateLogged,
				"Resolution Date: " + resolutionDate, "SLA Status: " + slaStatus};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}

	public static void assertViewDetailsModalUserIssueLogDealer() throws InterruptedException {

		String issueID = getDriver().findElement(By.id("vIssueId")).getText();
		String status = getDriver().findElement(By.id("vStatus")).getText();
		String issueSummary = getDriver().findElement(By.id("vIssueSummary")).getText();
		String username = getDriver().findElement(By.id("vUsername")).getText();
		String description = getDriver().findElement(By.id("vDescription")).getText();
		String resolutionMessage = getDriver().findElement(By.id("vResolution")).getText();
		TestUtils.scrollToElement("ID", "vResolutionDate");
		String resolvedBy = getDriver().findElement(By.id("vResolvedBy")).getText();
		String dateLogged = getDriver().findElement(By.id("vDateLogged")).getText();
		String resolutionDate = getDriver().findElement(By.id("vResolutionDate")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Issue ID", issueID);
		fields.put("Status", status);
		fields.put("Issue Summary", issueSummary);
		fields.put("Username", username);
		fields.put("Description: ", description);
		fields.put("Resolution Message", resolutionMessage);
		fields.put("Resolved By", resolvedBy);
		fields.put("Date Logged", dateLogged);
		fields.put("Resolution Date", resolutionDate);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertViewDetailsModalIssueLogCACAdmin(String resolutionStatus) throws InterruptedException {

		String issueID = getDriver().findElement(By.id("issue_id")).getText();
		String deviceID = getDriver().findElement(By.id("device_id")).getText();
		String issueSummary = getDriver().findElement(By.id("issue_summary")).getText();
		String issueCategory = getDriver().findElement(By.id("issue_category")).getText();
		String kitTag = getDriver().findElement(By.id("kit_tag")).getText();
		String stateOfDeployment = getDriver().findElement(By.id("stateOfDeployment")).getText();
		String applicationVersion = getDriver().findElement(By.id("app_version")).getText();
		TestUtils.scrollToElement("ID", "slaStatus");
		String description = getDriver().findElement(By.id("description")).getText();
		String slaStatus = getDriver().findElement(By.id("slaStatus")).getText();
		String dateLogged = getDriver().findElement(By.id("submissionDate")).getText();
		String resolutionDate = getDriver().findElement(By.id("resolutionDate")).getText();

		if (!resolutionStatus.equalsIgnoreCase("Pending")) {
			String resolutionMessage = getDriver().findElement(By.id("resolutionMessage")).getText();
			String resolvedBy = getDriver().findElement(By.id("resolvedBy")).getText();
			Pattern ptr = Pattern.compile(
					"(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
			String email = resolvedBy;
			if (ptr.matcher(email).matches()) {
				testInfo.get().log(Status.INFO, "Resolved By" + " : " + email);
			} else {
				testInfo.get().error("Resolved By" + " : " + email);
				testInfo.get().error("Resolved By does not shows user's email address");
			}
			try {
				if (resolutionMessage.contains("N/A") || resolutionMessage.equals("")) {
					testInfo.get().error("Resolution Message" + " : " + resolutionMessage);
				} else {
					testInfo.get().log(Status.INFO, "Resolution Message" + " : " + resolutionMessage);
				}
				if (resolutionDate.contains("N/A") || resolutionDate.equals("")) {
					testInfo.get().error("Resolution Date" + " : " + resolutionDate);
				} else {
					testInfo.get().log(Status.INFO, "Resolution Date" + " : " + resolutionDate);
				}

			} catch (Exception e) {
			}
//			if(getDriver().findElement(By.id("returnB2B").isDisplayed() && getDriver().findElement(By.id("returnB2B"))
		} else {
			String resolutionFeedback = getDriver().findElement(By.id("resolution")).getText();
			try {
				//if (resolutionFeedback.contains("N/A") || resolutionFeedback.equals("")) {
				if (resolutionFeedback.contains("N/A")) {
					testInfo.get().error("Resolution FeedBack" + " : " + resolutionFeedback);
				} else {
					testInfo.get().log(Status.INFO, "Resolution FeedBack" + " : " + resolutionFeedback);
				}

			} catch (Exception e) {
			}
		}

		String na = "N/A";
		String[] toList = {"Issue ID: " + issueID, "Device ID: " + deviceID, "Issue Summary: " + issueSummary,
				"Issue Category: " + issueCategory, "Kit Tag: " + kitTag, "State Of Deployment: " + stateOfDeployment,
				"Application Version: " + applicationVersion, "Description: " + description, "SLA Status: " + slaStatus,
				"Date Logged: " + dateLogged};
		for (String field : toList) {
			String name = "";
			String value = na;
			if (field.endsWith(":")) {
				field = field + value;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				value = fields[1];
				if (value.contains("N/A") || value.equals("")) {
					testInfo.get().error(name + " : " + value);
				} else {
					testInfo.get().log(Status.INFO, name + " : " + value);
				}
			} catch (Error e) {

			}

		}
	}

	@Parameters({"testEnv"})
	public static void imageDisplayKitIssueLog(String testEnv) throws InterruptedException {

		TestUtils.testTitle("To verify that picture and document is displayed on Kit Issue Log");
		WebElement device_img = getDriver().findElement(By.xpath("//div/div/div/div/div/div/img"));

		String deviceImg = device_img.getAttribute("src");
		//String validImage = "/simrop/picture/ISSUE_LOG_ATTACHMENT/";
		String emptyImage = "/simrop/picture/ISSUE_LOG_ATTACHMENT/0";

		if (deviceImg.contains(emptyImage)) {
			testInfo.get().error("Picture field is empty");
			testInfo.get().info("<a href='" + deviceImg + "'> Click Here </a>");
		} else {
			getDriver().findElement(By.xpath("//div[3]/div/button/i")).click();
			Thread.sleep(1000);
			TestUtils.assertSearchText("XPATH", "//div[@id='kvFileinputModal']/div/div/div/h5", "Detailed Preview");
			getDriver().findElement(By.cssSelector("i.glyphicon.glyphicon-remove")).click();
			Thread.sleep(500);
			testInfo.get().info("Picture is displayed");
			testInfo.get().info("<a href='" + deviceImg + "'> Click Here </a>");
		}

	}

	public static void imageDisplayAgentEnrolment() throws InterruptedException {

		TestUtils.testTitle("To verify that picture is displayed on Agent Enrolment");
		WebElement img = getDriver().findElement(By.id("outletImg"));

		String deviceImg = img.getAttribute("src");
//		String validImage = "/simrop/attachment/";
		String emptyImage = "/simrop/attachment/-1";

		if (deviceImg.contains(emptyImage)) {
			testInfo.get().error("Picture field is empty");
			testInfo.get().info("<a href='" + deviceImg + "'>Right-click and open in new tab</a>");
		} else {
			testInfo.get().info("Picture is displayed");
			testInfo.get().info("<a href='" + deviceImg + "'>Right-click and open in new tab</a>");
		}
	}

	public static void assertErrorValidationOutletManagementCACAdmin() throws InterruptedException {
		String NA = "N/A";

		String outletName = getDriver().findElement(By.id("outletName-error")).getText();
		String OwnerName = getDriver().findElement(By.id("ownerName-error")).getText();
		String address = getDriver().findElement(By.id("address-error")).getText();
		TestUtils.scrollToElement("NAME", "vtu");
		String latitude = getDriver().findElement(By.id("lat-error")).getText();
		String longitude = getDriver().findElement(By.id("lng-error")).getText();
		String outletType = getDriver().findElement(By.id("type-error")).getText();
		String vtuNumber = getDriver().findElement(By.id("vtu-error")).getText();
		String ownerPhoNum = getDriver().findElement(By.id("ownerPhone-error")).getText();
		String dealer = getDriver().findElement(By.id("dealer-error")).getText();

		String[] toList = {"Empty Outlet Name field: " + outletName, "Empty Owner Name field: " + OwnerName, "Empty Address field: " + address, "Empty Latitude field: " + latitude,
				"Empty Longitude field: " + longitude, "Empty Outlet Type field: " + outletType, "Empty Vtu Number field: " + vtuNumber, "Empty Owner Phone Number field: " + ownerPhoNum,
				"Empty Dealer field: " + dealer};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "<b> : " + val);
			}
		}
	}

	public static void assertErrorValidationNotificationCACAdmin() throws InterruptedException {
		String NA = "N/A";
		String targetGroup="N/A";
		try {
			 targetGroup = getDriver().findElement(By.id("targetGroup-error")).getText();
		}catch (Exception e){
			//target Group is preOccupied
			 targetGroup="N/A";
		}


		String dealer = getDriver().findElement(By.id("dealerDropDown-error")).getText();
		String device = getDriver().findElement(By.id("devices-error")).getText();
		String message = getDriver().findElement(By.id("messageInput-error")).getText();

		String[] toList = {"Empty Target group field: " + targetGroup, "Empty Dealer field: " + dealer, "Empty Kit/User field: " + device, "Empty Message field: " + message};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + "</b> : " + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + "<b> : " + val);
			}
		}
	}

	public static void assertViewDetailsModalUserIssueLogAdmin() throws InterruptedException {

		String issueID = getDriver().findElement(By.id("issue_id")).getText();
		String issueCategory = getDriver().findElement(By.id("issue_category")).getText();
		String issueSummary = getDriver().findElement(By.id("issue_summary")).getText();
		String username = getDriver().findElement(By.id("usernamePopover")).getText();
		String description = getDriver().findElement(By.id("description")).getText();
		String issueStatus = getDriver().findElement(By.id("issueStatus")).getText();
		String resolutionMessage = getDriver().findElement(By.id("resolutionMessage")).getText();
		TestUtils.scrollToElement("ID", "resolutionDate");
		String resolvedBy = getDriver().findElement(By.id("resolvedBy")).getText();
		String dateLogged = getDriver().findElement(By.id("submissionDate")).getText();
		String resolutionDate = getDriver().findElement(By.id("resolutionDate")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Issue ID", issueID);
		fields.put("Issue Category", issueCategory);
		fields.put("Issue Summary", issueSummary);
		fields.put("Username", username);
		fields.put("Description: ", description);
		fields.put("Issue Status", issueStatus);
		fields.put("Resolution Message", resolutionMessage);
		fields.put("Resolved By", resolvedBy);
		fields.put("Date Logged", dateLogged);
		fields.put("Resolution Date", resolutionDate);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertViewDetailsModalTaggingRequestAdmin() throws InterruptedException {

		String deviceID = getDriver().findElement(By.id("vDeviceId")).getText();
		String deviceTag = getDriver().findElement(By.id("vDeviceTag")).getText();
		String previousTag = getDriver().findElement(By.id("vPreviousTag")).getText();
		String requestedBy = getDriver().findElement(By.id("vInstaller")).getText();
		String appVersion = getDriver().findElement(By.id("vAppVersion")).getText();
		String dateRequested = getDriver().findElement(By.id("vInstallationDate")).getText();
		String dealer = getDriver().findElement(By.id("vDealerName")).getText();
		String dealerCode = getDriver().findElement(By.id("vDealerCode")).getText();
		TestUtils.scrollToElement("ID", "vStatus");
		String dealerRegion = getDriver().findElement(By.id("vDealerRegion")).getText();
		String dealerSubRegion = getDriver().findElement(By.id("vDealerSubRegion")).getText();
		String stateOfDep = getDriver().findElement(By.id("vDealerStates")).getText();
		String LgaOfDep = getDriver().findElement(By.id("vLod")).getText();
		String dealerSalesTerritory = getDriver().findElement(By.id("vDealerSalesTerritory")).getText();
		String status = getDriver().findElement(By.id("vStatus")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Device ID", deviceID);
		fields.put("Device Tag", deviceTag);
		fields.put("Previous Tag", previousTag);
		fields.put("Requested By", requestedBy);
		fields.put("Application Version", appVersion);
		fields.put("Date Requested", dateRequested);
		fields.put("Dealer", dealer);
		fields.put("Dealer Code", dealerCode);
		fields.put("Dealer Region", dealerRegion);
		fields.put("Dealer Sub Region", dealerSubRegion);
		fields.put("State of Deployment", stateOfDep);
		fields.put("LGA of Deployment", LgaOfDep);
		fields.put("Dealer Sales Territory", dealerSalesTerritory);
		fields.put("Status", status);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertBasicInfoEyeballingTest() throws InterruptedException {

		TestUtils.scrollToElement("XPATH", "//span[contains(text(),'Quarantine Information')]");
		Thread.sleep(1000);
		String rejectionReason = getDriver().findElement(By.cssSelector("tr:nth-child(1) div")).getText();
		String nin= "";
		try{
			nin = getDriver().findElement(By.cssSelector("tr:nth-child(2) div")).getText();
		}catch (Exception e){
			nin = getDriver().findElement(By.cssSelector("tr:nth-child(2) span")).getText();
		}
		String simSerial = getDriver().findElement(By.cssSelector("tr:nth-child(3) div")).getText();
		String firstName = getDriver().findElement(By.cssSelector("tr:nth-child(4) div")).getText();
		String otherName="";
		try{
			otherName = getDriver().findElement(By.cssSelector("tr:nth-child(5) div")).getText();
		}catch (Exception e){
			otherName = getDriver().findElement(By.cssSelector("tr:nth-child(5) span")).getText();
		}

		String surName = getDriver().findElement(By.cssSelector("tr:nth-child(6) div")).getText();
		String mothersMaidenName = getDriver().findElement(By.cssSelector("tr:nth-child(7) div")).getText();
		String gender = getDriver().findElement(By.cssSelector("tr:nth-child(8) div")).getText();
		String releaseAgent="";
		try{
			releaseAgent = getDriver().findElement(By.cssSelector("p.mb-4")).getText();
			TestUtils.scrollToElement("CSSSELECTOR", "p.mb-4");
		}catch (Exception e){
			releaseAgent = getDriver().findElement(By.xpath("//div[2]/span")).getText();
			TestUtils.scrollToElement("XPATH", "//div[2]/span");
		}


		String occupation="";
		try{
			occupation = getDriver().findElement(By.cssSelector("tr:nth-child(9) div")).getText();
		}catch (Exception e){
			occupation = getDriver().findElement(By.cssSelector("tr:nth-child(9) span")).getText();
		}
		String dateOfBirth = getDriver().findElement(By.cssSelector("tr:nth-child(10) div")).getText();
		String idTypeNumber = getDriver().findElement(By.cssSelector("tr:nth-child(11) div")).getText();
		String nationality = getDriver().findElement(By.cssSelector("tr:nth-child(12) div")).getText();
		String houseNoStreet = getDriver().findElement(By.cssSelector("tr:nth-child(13) div")).getText();
		String cityPostalCode = getDriver().findElement(By.cssSelector("tr:nth-child(14) div")).getText();
		String lgaStateOfResidence = getDriver().findElement(By.cssSelector("tr:nth-child(15) div")).getText();
		String lgaStateOfOrigin = getDriver().findElement(By.cssSelector("tr:nth-child(16) div")).getText();
		String regLga = getDriver().findElement(By.cssSelector("tr:nth-child(17) div")).getText();
		String email= "";
		try{
			email = getDriver().findElement(By.cssSelector("tr:nth-child(18) span")).getText();
		}catch (Exception e){
			email = getDriver().findElement(By.cssSelector("tr:nth-child(18) div")).getText();
		}
		String altPhoneNumber= "";
		try{
			altPhoneNumber = getDriver().findElement(By.cssSelector("tr:nth-child(19) span")).getText();
		}catch (Exception e){
			altPhoneNumber = getDriver().findElement(By.cssSelector("tr:nth-child(19) div")).getText();
		}

		String msisdnCategory= "";
		try{
			msisdnCategory = getDriver().findElement(By.cssSelector("tr:nth-child(20) span")).getText();
		}catch (Exception e){
			msisdnCategory = getDriver().findElement(By.cssSelector("tr:nth-child(20) div")).getText();
		}

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Rejection Reason", rejectionReason);
		fields.put("NIN", nin);
		fields.put("Sim Serial", simSerial);
		fields.put("First Name", firstName);
		fields.put("Other Name", otherName);
		fields.put("Surname", surName);
		fields.put("Mother's maiden name", mothersMaidenName);
		fields.put("Gender", gender);
		fields.put("Release Agent", releaseAgent);
		fields.put("Occupation", occupation);
		fields.put("Date of Birth", dateOfBirth);
		fields.put("ID Type | Number", idTypeNumber);
		fields.put("House number | Street", houseNoStreet);
		fields.put("City | Postal Code", cityPostalCode);
		fields.put("LGA | State of Residence", lgaStateOfResidence);
		fields.put("LGA | State of Origin", lgaStateOfOrigin);
		fields.put("Registration LGA", regLga);
		fields.put("Email", email);
		fields.put("Nationality", nationality);
		fields.put("ALternate Phone Number", altPhoneNumber);
		fields.put("Msisdn Category", msisdnCategory);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}

	}

	public static void assertBiometricDetailsEyeballingTest() throws InterruptedException {

		TestUtils.scrollToElement("XPATH", "//b[contains(text(),'Portrait Override:')]");
		String portraitOverride = getDriver().findElement(By.cssSelector("tr:nth-child(1) div")).getText();
		String portraitOverrideReason="";
		try {
			portraitOverrideReason = getDriver().findElement(By.cssSelector("tr:nth-child(2) div")).getText();
		}catch (Exception e){
			portraitOverrideReason = getDriver().findElement(By.cssSelector("tr:nth-child(2) div")).getText();
		}

		String rightHandOverride = getDriver().findElement(By.cssSelector("tr:nth-child(3) div")).getText();
		String righthandOverrideReason = getDriver().findElement(By.cssSelector("tr:nth-child(4) div")).getText();
		String leftHandOverride = getDriver().findElement(By.cssSelector("tr:nth-child(5) div")).getText();
		String leftHandOverrideReason = getDriver().findElement(By.cssSelector("tr:nth-child(6) div")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Portrait Override", portraitOverride);
		fields.put("Portrait Override Reason", portraitOverrideReason);
		fields.put("Right Hand Override", rightHandOverride);
		fields.put("Right Hand Override Reason", righthandOverrideReason);
		fields.put("Left Hand Override", leftHandOverride);
		fields.put("Left Hand Override Reason", leftHandOverrideReason);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}
	public static void assertAssignmenDetails(String category) throws InterruptedException {
		//Asserting assignment details
		TestUtils.testTitle("Asserting assignment details by selecting:"+category+" assignment category");
		Thread.sleep(2000);
		getDriver().findElement(By.id("assignmentCategory")).click();
		new Select(getDriver().findElement(By.id("assignmentCategory"))).selectByVisibleText(category);

		try{
			//No data found for this category
			TestUtils.assertSearchText("XPATH", "//div[contains(text(),'No data available in table')]", "No data available in table");
		}catch (Exception e){

			String sn = getDriver().findElement(By.xpath("//td")).getText();
			String assignmentCat = getDriver().findElement(By.xpath("//td[2]/div")).getText();
			String assignment = getDriver().findElement(By.xpath("//td[3]/div")).getText();
			String agent = getDriver().findElement(By.xpath("//td[4]/div")).getText();

			//Confirm the category returned on table
			TestUtils.assertSearchText("XPATH", "//td[2]/div", category);

			String empty = "N/A";
			Map<String, String> fields = new HashMap<>();
			fields.put("Assignment type", assignmentCat);
			fields.put("Assignment", assignment);
			fields.put("SN", sn);
			fields.put("Agent", agent);

			for (Map.Entry<String, String> entry : fields.entrySet()) {
				try {
					Assert.assertNotEquals(entry.getValue(), empty);
					Assert.assertNotEquals(entry.getValue(), null);
					testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
				} catch (Error ee) {
					testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
				}

			}
		}

	}

	public static void assertEnrolmentSignatureEyeballingTest() throws InterruptedException {

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Enrollment Signature')]");
		String regDate = getDriver().findElement(By.cssSelector("tr:nth-child(1) div")).getText();
		String releaseStatus = getDriver().findElement(By.cssSelector("tr:nth-child(2) div")).getText();
		String releaseReason = getDriver().findElement(By.cssSelector("tr:nth-child(3) div")).getText();
		String regAgent = getDriver().findElement(By.cssSelector("tr:nth-child(4) div")).getText();
		String uniqueID = getDriver().findElement(By.cssSelector("tr:nth-child(5) div")).getText();
		String kitTag = getDriver().findElement(By.cssSelector("tr:nth-child(6) div")).getText();
		String numberBarring = getDriver().findElement(By.cssSelector("tr:nth-child(7) div")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Registration Date", regDate);
		fields.put("Release status", releaseStatus);
		fields.put("Release Reason", releaseReason);
		fields.put("Registration Agent", regAgent);
		fields.put("Unique ID", uniqueID);
		fields.put("Kit Tag", kitTag);
		fields.put("Number Barring", numberBarring);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertCompanyDetailsEyeballingTest() throws InterruptedException {

		TestUtils.scrollUntilElementIsVisible("XPATH", "//a[contains(text(),'Company Details')]");
		String companyName = getDriver().findElement(By.cssSelector("#companyDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(1) span")).getText();
		String companyRcNo = getDriver().findElement(By.cssSelector("#companyDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(2) span")).getText();
		String companyStateLga = getDriver().findElement(By.cssSelector("#companyDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(3) div")).getText();
		String companyCityPostalCode = getDriver().findElement(By.cssSelector("#companyDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(4) div")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Company Name", companyName);
		fields.put("Company RC No", companyRcNo);
		fields.put("Company State | LGA", companyStateLga);
		fields.put("Company City | Postal Code", companyCityPostalCode);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertPassportDetailsEyeballingTest() throws InterruptedException {

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Passport Details')]");
		String passIssuingCountry = getDriver().findElement(By.cssSelector("#passportDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(1) span")).getText();
		String expiryDate = getDriver().findElement(By.cssSelector("#passportDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(2) span")).getText();
		String passNumber = getDriver().findElement(By.cssSelector("#passportDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(3) span")).getText();
		String nigerianResisdency = getDriver().findElement(By.cssSelector("#passportDetails\\ \\ flat\\.bfpSyncLogPk\\ \\  tr:nth-child(4) span")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Passport Issuing Country", passIssuingCountry);
		fields.put("Expiry Date", expiryDate);
		fields.put("Passport Number", passNumber);
		fields.put("Nigerian Residency", nigerianResisdency);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void assertDocumentsEyeballingTest() throws InterruptedException {

		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'Documents')]");
		String docType = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[1]/div[1]/span[1]")).getText();
		String docName = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[1]/div[2]/span[1]")).getText();
		String docID = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[1]/div[3]/span[1]")).getText();
		String docExpiryDate = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[1]/div[4]/span[1]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Document Type", docType);
		fields.put("Document Name", docName);
		fields.put("Document ID", docID);
		fields.put("Document Expiry Date", docExpiryDate);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

	}

	public static void imageDisplayEyeballing() throws InterruptedException {

		TestUtils.testTitle("To verify that portrait is displayed on Eyeballing");

		WebElement img = getDriver().findElement(By.xpath("//div[2]/img"));
		String deviceImg = img.getAttribute("src");
		String validImage = "/simrop/attachment/";
		String emptyImage = "/simrop/attachment/-1";

		if (deviceImg.contains(validImage)) {
			testInfo.get().info("Picture is displayed");
		} else if (deviceImg.contains(emptyImage)) {
			testInfo.get().error("Picture field is empty");
		}
	}


	public static void assertBasicDetails() throws InterruptedException {

		String phoneNumber = getDriver().findElement(By.xpath("//div/div/div/div/span")).getText().split(":")[1];
		TestUtils.scrollToElement("XPATH", "//td[2]/div");
		String simSerial = getDriver().findElement(By.xpath("//td[2]/div")).getText();
		String firstName = getDriver().findElement(By.xpath("//tr[2]/td[2]")).getText();
		String otherName = getDriver().findElement(By.xpath("//tr[3]/td[2]")).getText();
		String surname = getDriver().findElement(By.xpath("//tr[4]/td[2]")).getText();
		String mothersMaidenName = getDriver().findElement(By.xpath("//tr[5]/td[2]")).getText();
		TestUtils.scrollToElement("XPATH", "//tr[6]/td[2]");
		String gender = getDriver().findElement(By.xpath("//tr[6]/td[2]")).getText();
		String occupation = getDriver().findElement(By.xpath("//tr[7]/td[2]")).getText();
		String dob = getDriver().findElement(By.xpath("//tr[8]/td[2]")).getText();
		String nationality = getDriver().findElement(By.xpath("//tr[9]/td[2]")).getText();
		String idTypeNumber = getDriver().findElement(By.xpath("//tr[10]/td[2]")).getText();
		TestUtils.scrollToElement("XPATH", "//tr[10]/td[2]");
		String houseNumberOrStreet = getDriver().findElement(By.xpath("//tr[11]/td[2]")).getText();
		String cityPostalCode = getDriver().findElement(By.xpath("//tr[12]/td[2]")).getText();
		String lgaStateOfgResidence = getDriver().findElement(By.xpath("//tr[13]/td[2]")).getText();
		String lgaStateOfgOrigin = getDriver().findElement(By.xpath("//tr[14]/td[2]")).getText();
		String registrationLga = getDriver().findElement(By.xpath("//tr[15]/td[2]")).getText();
		String email = getDriver().findElement(By.xpath("//tr[16]/td[2]")).getText();
		TestUtils.scrollToElement("XPATH", "//tr[17]/td[2]");
		String alternatePhoneNumber = getDriver().findElement(By.xpath("//tr[17]/td[2]")).getText();
		String msisdnCategory = getDriver().findElement(By.xpath("//tr[18]/td[2]")).getText();
		String outlet = getDriver().findElement(By.xpath("//tr[19]/td[2]")).getText();
		String outletAddress = getDriver().findElement(By.xpath("//tr[20]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("MSISDN ", phoneNumber);
		fields.put("SIM Serial", simSerial);
		fields.put("First Name", firstName);
		fields.put("Other Name", otherName);
		fields.put("Surname", surname);
		fields.put("Mothers Maiden Name", mothersMaidenName);
		fields.put("Gender", gender);
		fields.put("Occupation", occupation);
		fields.put("Date of Birth", dob);
		fields.put("Nationality", nationality);
		fields.put("ID Type | Number", idTypeNumber);
		fields.put("House No | Street", houseNumberOrStreet);
		fields.put("City | Postal Code", cityPostalCode);
		fields.put("LGA | State of Residence", lgaStateOfgResidence);
		fields.put("LGA | State of Origin", lgaStateOfgOrigin);
		fields.put("Registration LGA", registrationLga);
		fields.put("Email", email);
		fields.put("Alternate Phone Number", alternatePhoneNumber);
		fields.put("Msisdn category", msisdnCategory);
		fields.put("Outlet", outlet);
		fields.put("Outlet Address", outletAddress);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				//Assert for null and Empty values-->
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertBiometricDetails() {

		String portraitOverride = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr/td[2]")).getText();
		String portraitOverrideReason = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr[2]/td[2]")).getText();
		String rightHandOverride = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr[3]/td[2]")).getText();
		String rightHandOverrideReason = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr[4]/td[2]")).getText();
		String lefthandOverride = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr[5]/td[2]")).getText();
		String lefthandOverrideReason = getDriver().findElement(By.xpath("//div[2]/div/div/div/table/tbody/tr[6]/td[2]")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Portrait Override", portraitOverride);
		fields.put("Portrait Override Reason", portraitOverrideReason);
		fields.put("Right Hand Override", rightHandOverride);
		fields.put("Right Hand Override Reason", rightHandOverrideReason);
		fields.put("Left Hand Override", lefthandOverride);
		fields.put("Left Hand Override Reason", lefthandOverrideReason);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertEnrollmentSignature() {

		String registrationDate = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr/td[2]/div")).getText();
		String activationDate = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr[2]/td[2]")).getText();
		String eyeballingStatus = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr[3]/td[2]")).getText();
		String registrationAgent = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr[4]/td[2]")).getText();
		String uniqueId = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr[5]/td[2]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//div[3]/div/div/div/table/tbody/tr[6]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Registration Date", registrationDate);
		fields.put("Activation Date", activationDate);
		fields.put("Eyeballing status", eyeballingStatus);
		fields.put("Registration Agent", registrationAgent);
		fields.put("Unique ID", uniqueId);
		fields.put("Kit Tag", kitTag);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertCompanyDetails() {

		String companyName = getDriver().findElement(By.xpath("//div[4]/div/div/div/table/tbody/tr/td[2]")).getText();
		String companyRcNo = getDriver().findElement(By.xpath("//div[4]/div/div/div/table/tbody/tr[2]/td[2]")).getText();
		String companyStateLga = getDriver().findElement(By.xpath("//div[4]/div/div/div/table/tbody/tr[3]/td[2]")).getText();
		String companyCityPostalCode = getDriver().findElement(By.xpath("//div[4]/div/div/div/table/tbody/tr[4]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Company Name", companyName);
		fields.put("Company RC No", companyRcNo);
		fields.put("Company State | LGA", companyStateLga);
		fields.put("Company City | Postal Code", companyCityPostalCode);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertPassportDetails() throws InterruptedException {

		String passportIssuingCountry = getDriver().findElement(By.xpath("//div[5]/div/div/div/table/tbody/tr/td[2]")).getText();
		String expiryDate = getDriver().findElement(By.xpath("//div[5]/div/div/div/table/tbody/tr[2]/td[2]")).getText();
		String passportNumber = getDriver().findElement(By.xpath("//div[5]/div/div/div/table/tbody/tr[3]/td[2]")).getText();
		String nigerianResidency = getDriver().findElement(By.xpath("//div[5]/div/div/div/table/tbody/tr[4]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Passport Issuing Country", passportIssuingCountry);
		fields.put("Expiry Date", expiryDate);
		fields.put("Passport Number", passportNumber);
		fields.put("Nigerian Residency", nigerianResidency);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertDocumentDetails() throws InterruptedException {

		String documentType = getDriver().findElement(By.xpath("//td/div/div/div/span")).getText();
		String documentName = getDriver().findElement(By.xpath("//td/div/div/div[2]/span")).getText();
		String documentId = getDriver().findElement(By.xpath("//td/div/div/div[3]/span")).getText();
		String documentExpiryDate = getDriver().findElement(By.xpath("//td/div/div/div[4]/span")).getText();
		String documentImage = getDriver().findElement(By.xpath("//td/div/div/div[5]/span")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Document Type", documentType);
		fields.put("Document Name", documentName);
		fields.put("Document ID", documentId);
		fields.put("Document Expiry Date", documentExpiryDate);
		fields.put("Document Image", documentImage);


		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}


	public static void imageDisplayViewDetails() {

		TestUtils.testTitle("To verify that picture is displayed on View Details");
		WebElement img = getDriver().findElement(By.xpath("//img[@alt='Portrait']"));

		String deviceImg = img.getAttribute("src");
		String emptyImage = "/noDataImage.png";

		if (!deviceImg.contains(emptyImage)) {
			testInfo.get().info("Picture is displayed");
		} else {
			testInfo.get().error("Picture field is empty");
		}
	}


	public static void assertFastEyeballingDetails() {

		String phoneNumber = getDriver().findElement(By.xpath("//div/span")).getText();
		String mothersMaidenName = getDriver().findElement(By.xpath("//div/p/span")).getText();
		String dob = getDriver().findElement(By.xpath("//p[2]/span")).getText();
		String nationality = getDriver().findElement(By.xpath("//p[3]/span")).getText();
		String idTypeNumber = getDriver().findElement(By.xpath("//p[4]/span")).getText();
		String registrationEnrolmentType = getDriver().findElement(By.xpath("//p[5]/span")).getText();
		String msisdnCategory = getDriver().findElement(By.xpath("//p[6]/span")).getText();
		String appVersion = getDriver().findElement(By.xpath("//p[7]/span")).getText();
		String eyeballingAgent = getDriver().findElement(By.xpath("//div[3]/div/div/span")).getText();

		String name = getDriver().findElement(By.xpath("//div[2]/div[2]/span")).getText();
		String houseNumberOrStreet = getDriver().findElement(By.xpath("//div[2]/p/span")).getText();
		String cityPostalCode = getDriver().findElement(By.xpath("//div[2]/p[2]/span")).getText();
		String lgaStateOfgResidence = getDriver().findElement(By.xpath("//div[2]/p[3]/span")).getText();
		String lgaStateOfgOrigin = getDriver().findElement(By.xpath("//div[2]/p[4]/span")).getText();
		String registrationLga = getDriver().findElement(By.xpath("//div[2]/p[5]/span")).getText();
		String outlet = getDriver().findElement(By.xpath("//div[2]/p[6]/span")).getText();
		String captureMode = getDriver().findElement(By.xpath("//div[2]/p[7]/span")).getText();
		String eyeballingStatus = getDriver().findElement(By.xpath("//div[3]/div/div[2]/span")).getText();


		String registrationAdded = getDriver().findElement(By.xpath("//div[3]/span")).getText();
		String activationDate = getDriver().findElement(By.xpath("//div[4]/span")).getText();
		String gender = getDriver().findElement(By.xpath("//div[5]/span")).getText();
		String activationStatus = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[6]/span")).getText();
		String portraitOverrideReason = getDriver().findElement(By.xpath("//div[3]/p/span")).getText();
		String leftHandOverrideReason = getDriver().findElement(By.xpath("//div[3]/p[3]/span")).getText();
		String rightHandOverrideReason = getDriver().findElement(By.xpath("//div[3]/p[2]/span")).getText();
		String registrationAgent = getDriver().findElement(By.xpath("//div[3]/p[4]/span")).getText();
		String uniqueId = getDriver().findElement(By.xpath("//div[3]/p[5]/span")).getText();
		String simSerial = getDriver().findElement(By.xpath("//div[3]/p[6]/span")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("MSISDN ", phoneNumber);
		fields.put("SIM Serial", simSerial);
		fields.put("Name", name);
		fields.put("Registration/Enrollment Type", registrationEnrolmentType);
		fields.put("App version", appVersion);
		fields.put("Mothers Maiden Name", mothersMaidenName);
		fields.put("Gender", gender);
		fields.put("Eyeballing Agent", eyeballingAgent);
		fields.put("Date of Birth", dob);
		fields.put("Nationality", nationality);
		fields.put("ID Type | Number", idTypeNumber);
		fields.put("House No | Street", houseNumberOrStreet);
		fields.put("City | Postal Code", cityPostalCode);
		fields.put("LGA | State of Residence", lgaStateOfgResidence);
		fields.put("LGA | State of Origin", lgaStateOfgOrigin);
		fields.put("Registration LGA", registrationLga);
		fields.put("Capture Mode", captureMode);
		fields.put("Eyeballing Status", eyeballingStatus);
		fields.put("Msisdn category", msisdnCategory);
		fields.put("Outlet", outlet);
		fields.put("Registration Added", registrationAdded);
		fields.put("Unique ID", uniqueId);
		fields.put("Activation Date", activationDate);
		fields.put("Eyeballing Status", eyeballingStatus);
		fields.put("Activation status", activationStatus);
		fields.put("Portrait override reason", portraitOverrideReason);
		fields.put("Right Hand Override Reason", rightHandOverrideReason);
		fields.put("RegistrationAgent", registrationAgent);
		fields.put("Left Hand Override Reason", leftHandOverrideReason);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void portraitDisplay() {

		WebElement img = getDriver().findElement(By.xpath("//div[4]/img"));

		String deviceImg = img.getAttribute("src");
		String emptyImage = "/IDCard-Placeholder.png/";

		if (!deviceImg.contains(emptyImage)) {
			testInfo.get().info("Portrait is displayed");
		} else {
			testInfo.get().error("Portrait field is empty");
		}
	}


	public static void viewFingerPrints() {

		String leftThumb = getDriver().findElement(By.xpath("//div/div[2]/div/div/div/div/img")).getAttribute("src");
		String leftIndex = getDriver().findElement(By.xpath("//div[2]/div/div/div/div[2]/img")).getAttribute("src");
		String leftMiddle = getDriver().findElement(By.xpath("//div[2]/div/div/div/div[3]/img")).getAttribute("src");
		String leftRing = getDriver().findElement(By.xpath("//div[2]/div/div/div/div[4]/img")).getAttribute("src");
		String leftLittle = getDriver().findElement(By.xpath("//div[2]/div/div/div/div[5]/img")).getAttribute("src");
		String rightThumb = getDriver().findElement(By.xpath("//div/div[2]/div/div[2]/div/div/img")).getAttribute("src");
		String rightIndex = getDriver().findElement(By.xpath("//div[2]/div/div[2]/img")).getAttribute("src");
		String rightMiddle = getDriver().findElement(By.xpath("//div[2]/div/div[3]/img")).getAttribute("src");
		String rightRing = getDriver().findElement(By.xpath("//div[2]/div/div[4]/img")).getAttribute("src");
		String rightLittle = getDriver().findElement(By.xpath("//div[2]/div/div[5]/img")).getAttribute("src");

		Map<String, String> fields = new HashMap<>();
		fields.put("Left Thumb ", leftThumb);
		fields.put("Left Index ", leftIndex);
		fields.put("Left Middle ", leftMiddle);
		fields.put("Left Ring ", leftRing);
		fields.put("Left Little ", leftLittle);
		fields.put("Right Thumb ", rightThumb);
		fields.put("Right Index ", rightIndex);
		fields.put("Right Middle ", rightMiddle);
		fields.put("Right Ring ", rightRing);
		fields.put("Right Little ", rightLittle);

		String emptyImage = "assets/images/noDataImage.png";

		for (Map.Entry<String, String> entry : fields.entrySet()) {

			if (!entry.getValue().contains(emptyImage)) {
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>Finger Print Displayed");
			} else {
				testInfo.get().log(Status.ERROR, "<b>" + entry.getKey() + " : </b>Finger Print Not Displayed");
			}
		}


	}

	public static void assertNumberBarringTable() throws InterruptedException {
		Thread.sleep(2000);
		String phoneNumber = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div/span")).getText();
		String mothersMaidenName = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p/span")).getText();
		String dob = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[2]/span")).getText();
		String nationality = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[3]/span")).getText();
		String idTypeNumber = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[4]/span")).getText();
		String registrationEnrolmentType = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[5]/span")).getText();
		String msisdnCategory = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[6]/span")).getText();
		String createDate = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div/p[7]/span")).getText();

		Thread.sleep(1500);
		String name = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[2]/span")).getText();
		String houseNumberOrStreet = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p/span")).getText();
		String cityPostalCode = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[2]/span")).getText();
		String lgaStateOfgResidence = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[3]/span")).getText();
		String lgaStateOfgOrigin = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[4]/span")).getText();
		String registrationLga = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[5]/span")).getText();
		String outlet = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[6]/span")).getText();
		String captureMode = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[2]/p[7]/span")).getText();


		String registrationAdded = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[3]/span")).getText();
		String activationDate = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[4]/span")).getText();
		String gender = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[5]/span")).getText();
		String activationStatus = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div/div[2]/div[6]/span")).getText();

		String portraitOverrideReason = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p/span")).getText();
		String leftHandOverrideReason = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p[3]/span")).getText();
		String rightHandOverrideReason = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p[2]/span")).getText();
		String registrationAgent = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p[4]/span")).getText();
		String uniqueId = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p[5]/span")).getText();
		String simSerial = getDriver().findElement(By.xpath("//div[@id='fast_rowId  ']/div[2]/div/div[3]/p[6]/span")).getText();
		String viewOtherUploadBtn = getDriver().findElement(By.cssSelector("button.btn.btn-yellow.btn-sm.mr-4")).getText();


		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("MSISDN ", phoneNumber);
		fields.put("SIM Serial", simSerial);
		fields.put("Name", name);
		fields.put("Registration/Enrollment Type", registrationEnrolmentType);
		fields.put("Create Date", createDate);
		fields.put("Mothers Maiden Name", mothersMaidenName);
		fields.put("Gender", gender);
		fields.put("View Other Upload Button", viewOtherUploadBtn);
		fields.put("Date of Birth", dob);
		fields.put("Nationality", nationality);
		fields.put("ID Type | Number", idTypeNumber);
		fields.put("House No | Street", houseNumberOrStreet);
		fields.put("City | Postal Code", cityPostalCode);
		fields.put("LGA | State of Residence", lgaStateOfgResidence);
		fields.put("LGA | State of Origin", lgaStateOfgOrigin);
		fields.put("Registration LGA", registrationLga);
		fields.put("Capture Mode", captureMode);
		fields.put("Msisdn category", msisdnCategory);
		fields.put("Outlet", outlet);
		fields.put("Registration Added", registrationAdded);
		fields.put("Unique ID", uniqueId);
		fields.put("Activation Date", activationDate);
		fields.put("Activation status", activationStatus);
		fields.put("Portrait override reason", portraitOverrideReason);
		fields.put("Right Hand Override Reason", rightHandOverrideReason);
		fields.put("RegistrationAgent", registrationAgent);
		fields.put("Left Hand Override Reason", leftHandOverrideReason);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertTableDataSimSwapFraudAlertsTest() throws InterruptedException {

		String firstName = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[2]")).getText();
		String lastName = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[3]")).getText();
		String msisdn = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[4]")).getText();
		String alertDate = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		String treatmentStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[6]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("First Name", firstName);
		fields.put("Last Name", lastName);
		fields.put("Msisdn", msisdn);
		fields.put("Alert Date", alertDate);
		fields.put("Treatment Status", treatmentStatus);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertSIMSwapFraudAlertDetailsTest() throws InterruptedException {

		String requestID = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr/td[2]")).getText();
		String subscriberType = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[2]/td[2]")).getText();
		String msisdn = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[3]/td[2]")).getText();
		String previousSimSerial = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[4]/td[2]")).getText();
		String previousPuk = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[5]/td[2]")).getText();
		String newMsisdn = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[6]/td[2]")).getText();
		String newSimSerial = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[7]/td[2]")).getText();
		String firstName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[8]/td[2]")).getText();
		String lastName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[9]/td[2]")).getText();
		String motherMaidenName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[10]/td[2]")).getText();

		TestUtils.scrollToElement("XPATH", "//div[@id='rowContent']/div/table/tbody/tr[18]/td[2]");
		String dob = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[11]/td[2]")).getText();
		String proxyNum = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[12]/td[2]")).getText();
		String proxyName = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[13]/td[2]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[14]/td[2]")).getText();
		String swapCategory = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[15]/td[2]")).getText();
		String nationality = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[16]/td[2]")).getText();
		String stateOfOrigin = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[17]/td[2]")).getText();
		String occupation = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[18]/td[2]")).getText();

		TestUtils.scrollToElement("ID", "APPROVED");
		String altPhoneNum = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[19]/td[2]")).getText();
		String simSwapTimestamp = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[20]/td[2]")).getText();
		String swapStatus = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[21]/td[2]")).getText();
		String regSwapStatus = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[22]/td[2]")).getText();
		String swapAgent = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[23]/td[2]")).getText();
		String gender = getDriver().findElement(By.xpath("//div[@id='rowContent']/div/table/tbody/tr[24]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Request ID ", requestID);
		fields.put("Subscriber Type", subscriberType);
		fields.put("Msisdn", msisdn);
		fields.put("Previous SIM Serial", previousSimSerial);
		fields.put("Previous PUK", previousPuk);
		fields.put("New Msisdn", newMsisdn);
		fields.put("New SIM Serial", newSimSerial);
		fields.put("First Name", firstName);
		fields.put("Last Name", lastName);
		fields.put("Mother's Maiden Name", motherMaidenName);
		fields.put("Date of Birth", dob);
		fields.put("Proxy Number", proxyNum);
		fields.put("Proxy Name", proxyName);
		fields.put("Kit Tag", kitTag);
		fields.put("Swap Category", swapCategory);
		fields.put("Nationality", nationality);
		fields.put("State of Origin", stateOfOrigin);
		fields.put("Occupation", occupation);
		fields.put("Alternate Phone Number", altPhoneNum);
		fields.put("SIM Swap Timestamp", simSwapTimestamp);
		fields.put("Swap Status", swapStatus);
		fields.put("Registration Swap Status", regSwapStatus);
		fields.put("Swap Agent", swapAgent);
		fields.put("Gender", gender);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}
	}

	public static void assertSimSwapTableDetails() {

		String msisdn = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[2]")).getText();
		String swapStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[3]")).getText();
		String checkStatus = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[4]")).getText();
		String swaAgent = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[5]")).getText();
		String swapDate = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[6]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[7]")).getText();
		String subscribersFullName = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[8]")).getText();
		String aging = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[9]")).getText();
		String sentToSiebel = getDriver().findElement(By.xpath("//table[@id='users']/tbody/tr/td[10]")).getText();
		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("MSISDN", msisdn);
		fields.put("Swap Status", swapStatus);
		fields.put("Check Status", checkStatus);
		fields.put("Swap Agent", swaAgent);
		fields.put("Swap Date", swapDate);
		fields.put("Kit Tag", kitTag);
		fields.put("Subscriber's Full Name", subscribersFullName);
		fields.put("Aging", aging);
		fields.put("Sent to Siebel", sentToSiebel);
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}
	}

	public static void assertSwapDetails() {

		String simSwapStatus = getDriver().findElement(By.xpath("//div[@id='simSwapDetails']/div/div/div/div/div/div/p/span")).getText();
		String checkStatus = getDriver().findElement(By.xpath("//div[@id='simSwapDetails']/div/div/div/div/div/div[2]/p/span")).getText();
		String subscriberType = getDriver().findElement(By.xpath("//td[2]")).getText();
		String simSwapType = getDriver().findElement(By.xpath("//tr[2]/td[2]")).getText();
		String simSwapCategory = getDriver().findElement(By.xpath("//tr[3]/td[2]")).getText();
		String primaryMsisdn = getDriver().findElement(By.xpath("//tr[4]/td[2]")).getText();
		String primarySimSerial = getDriver().findElement(By.xpath("//tr[5]/td[2]")).getText();
		String primaryPuk = getDriver().findElement(By.xpath("//tr[6]/td[2]")).getText();
		String targetMsisdn = getDriver().findElement(By.xpath("//tr[7]/td[2]")).getText();
		String targetSimSerial = getDriver().findElement(By.xpath("//tr[8]/td[2]")).getText();
		String rawSim = getDriver().findElement(By.xpath("//tr[9]/td[2]")).getText();
		String validationOverride = getDriver().findElement(By.xpath("//tr[10]/td[2]")).getText();
		String simSwapTimestamp = getDriver().findElement(By.xpath("//tr[11]/td[2]")).getText();
		String registrationStatus = getDriver().findElement(By.xpath("//tr[12]/td[2]")).getText();
		String registrationStatusDescription = getDriver().findElement(By.xpath("//tr[13]/td[2]")).getText();
		String sentToSiebel = getDriver().findElement(By.xpath("//tr[14]/td[2]")).getText();
		String ssrejectionCount = getDriver().findElement(By.xpath("//tr[15]/td[2]")).getText();
		String ssAgentId = getDriver().findElement(By.xpath("//tr[16]/td[2]")).getText();
		String ssCheckerId = getDriver().findElement(By.xpath("//tr[17]/td[2]")).getText();
		String deviceId = getDriver().findElement(By.xpath("//tr[18]/td[2]")).getText();
		String kitTag = getDriver().findElement(By.xpath("//tr[19]/td[2]")).getText();
		String aging = getDriver().findElement(By.xpath("//tr[20]/td[2]")).getText();
		String msisdnHeirarchy = getDriver().findElement(By.xpath("//tr[21]/td[2]")).getText();
		String ussdBarringStatus = getDriver().findElement(By.xpath("//tr[22]/td[2]")).getText();
		String msisdnSwapStatus = getDriver().findElement(By.xpath("//tr[23]/td[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("SIM Swap Status", simSwapStatus);
		fields.put("Check Status", checkStatus);
		fields.put("Subscriber Type", subscriberType);
		fields.put("SIM Swap Type", simSwapType);
		fields.put("SIM Swap Catergory", simSwapCategory);
		fields.put("Primary MSISDN", primaryMsisdn);
		fields.put("Primary SIM Serial", primarySimSerial);
		fields.put("Primary PUK", primaryPuk);
		fields.put("Target MSIDN", targetMsisdn);
		fields.put("Target SIM Serial", targetSimSerial);
		fields.put("RAW SIM ?", rawSim);
		fields.put("Validation Override", validationOverride);
		fields.put("SIM Swap Timestamp", simSwapTimestamp);
		fields.put("Registration Status", registrationStatus);
		fields.put("Registration Status Description", registrationStatusDescription);
		fields.put("Sent to Siebel", sentToSiebel);
		fields.put("SIM Swap Rejection Count", ssrejectionCount);
		fields.put("SIM Swap Agent ID", ssAgentId);
		fields.put("SIM Swap Checker ID", ssCheckerId);
		fields.put("Device ID", deviceId);
		fields.put("Kit Tag", kitTag);
		fields.put("Aging", aging);
		fields.put("MSISDN Hierarchy", msisdnHeirarchy);
		fields.put("USSD Barring Status", ussdBarringStatus);
		fields.put("Msisdn Swap Status", msisdnSwapStatus);
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}
	}

	public static void assertShopMappingDetails() throws InterruptedException {
		Thread.sleep(2000);
		String msisdn = getDriver().findElement(By.xpath("//td[2]/div")).getText();
		String simSerial = getDriver().findElement(By.xpath("//td[3]/div")).getText();
		Thread.sleep(500);
		String shop = getDriver().findElement(By.xpath("//td[4]/div")).getText();
		String mappingButton = getDriver().findElement(By.id("msisdnMappingBtn")).getText();
		getDriver().findElement(By.id("msisdnMappingBtn")).click();
		Thread.sleep(500);
		String mapMsisdnBtn = getDriver().findElement(By.xpath("//a[contains(text(),'Map MSISDN')]")).getText();
		String bulkMappingBtn = getDriver().findElement(By.xpath("//a[contains(text(),'Bulk Mapping')]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("MSISDN", msisdn);
		fields.put("Sim Serial", simSerial);
		fields.put("Shop", shop);
		fields.put("MSISDN Mapping Button", mappingButton);
		fields.put("Map MSISDN Link", mapMsisdnBtn);
		fields.put("Bulk Mapping Link", bulkMappingBtn);
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}
	}

	public static void assertTableDataUserMappingTest() throws InterruptedException {

		String serialNum = getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td")).getText();
		String mappingName = getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[2]")).getText();
		String numOfLevels = getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[3]")).getText();
		String numOfUsers = getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[4]")).getText();
		String dateCreated = getDriver().findElement(By.xpath("//table[@id='userMapping']/tbody/tr/td[5]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("S/N", serialNum);
		fields.put("Mapping Name", mappingName);
		fields.put("Number of Levels", numOfLevels);
		fields.put("Number of Users", numOfUsers);
		fields.put("Date Created", dateCreated);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}
	}
	public static void assertLevelsOfUserMappingTest() throws InterruptedException {

		String lev = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr/td")).getText();
		String lev1 = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td")).getText();
		String lev2 = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr[3]/td")).getText();
		String lev3 = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr[4]/td")).getText();
		String lev4 = getDriver().findElement(By.xpath("//div[2]/div/div/div/div/div/div/table/tbody/tr[5]/td")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("Level 1", lev);
		fields.put("Level 2", lev1);
		fields.put("Level 3", lev2);
		fields.put("Level 4", lev3);
		fields.put("Level 5", lev4);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}
		}
	}

	//Method to validate and log Demographic details
	public static void validateDemographicDetailField(String field, String fromSubscriber, String fromSystem, String validation) {
		String fieldName = getDriver().findElement(By.xpath(field)).getText();
		String subscriberInput = getDriver().findElement(By.xpath(fromSubscriber)).getText();
		String systemInput = getDriver().findElement(By.xpath(fromSystem)).getText();
		String val = getDriver().findElement(By.xpath(validation)).getText();
		String word;
		if (!subscriberInput.equals("N/A") && !systemInput.equals("N/A")) {
			if (subscriberInput.equalsIgnoreCase(systemInput) || val.equalsIgnoreCase("PASSED")) {
				word = "<b>" + fieldName + " : <--></b>" + subscriberInput + "<-->" + systemInput + "<-->" + val;
				Markup w = MarkupHelper.createLabel(word, ExtentColor.GREEN);
				testInfo.get().info(w);
			} else {
				word = "<b>" + fieldName + " : <--></b>" + subscriberInput + "<-->" + systemInput + "<-->" + val;
				Markup w = MarkupHelper.createLabel(word, ExtentColor.RED);
				testInfo.get().error(w);
			}
		} else {
			word = "<b>" + fieldName + " : <--></b>" + subscriberInput + "<-->" + systemInput + "<-->" + "Validation Empty";
			Markup w = MarkupHelper.createLabel(word, ExtentColor.GREY);
			testInfo.get().error(w);
		}
	}

	//Method to validate and Log FDN
	public static void validateFrequentlyDialedNumberField(String ffdn, String number, String matchingStatus) {
		String fieldName = getDriver().findElement(By.xpath(ffdn)).getText();
		String numb = getDriver().findElement(By.xpath(number)).getText();
		String matchStatus = getDriver().findElement(By.xpath(matchingStatus)).getText();
		String word;
		if (!matchStatus.isEmpty()) {
			if (matchStatus.equalsIgnoreCase("PASSED")) {
				word = "<b>" + fieldName + " : <--></b>" + numb + "<-->" + matchStatus;
				Markup w = MarkupHelper.createLabel(word, ExtentColor.GREEN);
				testInfo.get().info(w);
			} else {
				word = "<b>" + fieldName + " : <--></b>" + numb + "<-->" + matchStatus;
				Markup w = MarkupHelper.createLabel(word, ExtentColor.RED);
				testInfo.get().error(w);
			}
		} else {
			word = "<b>" + fieldName + " : <--></b>" + numb + "<-->Validation Empty";
			Markup w = MarkupHelper.createLabel(word, ExtentColor.GREY);
			testInfo.get().error(w);
		}

	}

	public static void assertNINEyeballingTest() throws InterruptedException {
		TestUtils.scrollToElement("XPATH", "//a[contains(text(),'NIN Details')]");
		String nin = getDriver().findElement(By.xpath("//app-nin-detail-modal/div/div")).getText();
		String ninStatus = getDriver().findElement(By.xpath("//app-nin-detail-modal/div/div[2]")).getText();

		String empty = "N/A";
		Map<String, String> fields = new HashMap<>();
		fields.put("NIN DATA", nin);
		fields.put("NIN STATUS", ninStatus);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
			}

		}

		try{
			TestUtils.assertSearchText("XPATH", "//app-nin-detail-modal/div[3]", "Record does not have a transaction Id");
		}catch (Exception e){

			//================Assert NIMC Data===============//
			TestUtils.testTitle("Assert NIMC DATA");
			String firstName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[2]/td[2]")).getText();
			String lastName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[3]/td[2]")).getText();
			String otherName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[4]/td[2]")).getText();
			String motherMaiden_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[5]/td[2]")).getText();
			String dateOfBirth_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[6]/td[2]")).getText();
			String birthLga_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[7]/td[2]")).getText();
			String birthState_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[8]/td[2]")).getText();
			String gender_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[9]/td[2]")).getText();
			String stateOfOrigin_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[10]/td[2]")).getText();
			String birthCountry = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[11]/td[2]")).getText();
			String centralId_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[12]/td[2]")).getText();

			String eduLeve_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[13]/td[2]")).getText();
			String empStatus_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[14]/td[2]")).getText();
			String height_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[15]/td[2]")).getText();
			String cardStatus_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[16]/td[2]")).getText();
			String docNo_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[17]/td[2]")).getText();
			String email_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[18]/td[2]")).getText();
			String nokFirstName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[19]/td[2]")).getText();
			String maritalStatus_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[20]/td[2]")).getText();
			String middlename_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[21]/td[2]")).getText();
			String nokLga_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[22]/td[2]")).getText();
			String nokMiddleName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[23]/td[2]")).getText();

			String residenceAddress1_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[24]/td[2]")).getText();
			String residenceAddress2_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[25]/td[2]")).getText();
			String residenceTown_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[26]/td[2]")).getText();
			String residenceLga_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[27]/td[2]")).getText();
			String residencePostalCode_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[28]/td[2]")).getText();
			String residenceState_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[29]/td[2]")).getText();
			String residenceStatus_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[30]/td[2]")).getText();
			String selfOriginLga_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[31]/td[2]")).getText();
			String selfOriginPlace_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[32]/td[2]")).getText();
			String selfOriginState_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[33]/td[2]")).getText();
			String telephoneNo_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[34]/td[2]")).getText();

			String nokPostalCode_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[35]/td[2]")).getText();
			String nokState_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[36]/td[2]")).getText();
			String nokSurname_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[37]/td[2]")).getText();
			String nokTown_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[38]/td[2]")).getText();
			String nokAddress1_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[39]/td[2]")).getText();
			String nokAddress2_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[40]/td[2]")).getText();
			String n_spokeLang_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[41]/td[2]")).getText();
			String o_spokeLang_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[42]/td[2]")).getText();
			String pFirstName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[43]/td[2]")).getText();
			String pMiddleName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[44]/td[2]")).getText();
			String profession_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[45]/td[2]")).getText();

			String pSurName_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[46]/td[2]")).getText();
			String religion_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[47]/td[2]")).getText();
			String title_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[48]/td[2]")).getText();
			String trackId_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[49]/td[2]")).getText();
			String employmentLevel_Nimc = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[50]/td[2]")).getText();
			//TestUtils.scrollToElement("XPATH", "//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[13]/td[2");


			Map<String, String> fields2 = new HashMap<>();
			fields2.put("First Name:", firstName_Nimc);
			fields2.put("Last Name:", lastName_Nimc);
			fields2.put("Other Name:", otherName_Nimc);
			fields2.put("Mother's Maiden Mame:", motherMaiden_Nimc);
			fields2.put("Date of Birth:", dateOfBirth_Nimc);
			fields2.put("Birth LGA:", birthLga_Nimc);
			fields2.put("Birth State:", birthState_Nimc);
			fields2.put("Gender:", gender_Nimc);
			fields2.put("State of Origin:", stateOfOrigin_Nimc);
			fields2.put("Birth Country:", birthCountry);
			fields2.put("Central ID:", centralId_Nimc);

			fields2.put("Educational Level:", eduLeve_Nimc);
			fields2.put("Employment Status:", empStatus_Nimc);
			fields2.put("Height:", height_Nimc);
			fields2.put("Card Status:", cardStatus_Nimc);
			fields2.put("Document No:", docNo_Nimc);
			fields2.put("Email:", email_Nimc);
			fields2.put("Nok Firstname:", nokFirstName_Nimc);
			fields2.put("Marital Status:", maritalStatus_Nimc);
			fields2.put("Middlename:", middlename_Nimc);
			fields2.put("NOK LGA:", nokLga_Nimc);
			fields2.put("NOK Middlename:", nokMiddleName_Nimc);

			fields2.put("Resisdence Address Line 1:", residenceAddress1_Nimc);
			fields2.put("Resisdence Address Line 2:", residenceAddress2_Nimc);
			fields2.put("Residence Town:", residenceTown_Nimc);
			fields2.put("Residence LGA:", residenceLga_Nimc);
			fields2.put("Residence Postal Code:", residencePostalCode_Nimc);
			fields2.put("Residence State:", residenceState_Nimc);
			fields2.put("Residence Status:", residenceStatus_Nimc);
			fields2.put("Self Origin LGA:", selfOriginLga_Nimc);
			fields2.put("Self Origin Place:", selfOriginPlace_Nimc);
			fields2.put("Self Origin State:", selfOriginState_Nimc);
			fields2.put("Telephone NO:", telephoneNo_Nimc);

			fields2.put("NOK Postal Code:", nokPostalCode_Nimc);
			fields2.put("NOK State:", nokState_Nimc);
			fields2.put("NOK Surname:", nokSurname_Nimc);
			fields2.put("NOK Town:", nokTown_Nimc);
			fields2.put("NOK Address 1:", nokAddress1_Nimc);
			fields2.put("NOK Address 2:", nokAddress2_Nimc);
			fields2.put("N Spoken Lang:", n_spokeLang_Nimc);
			fields2.put("O Spoken Lang:", o_spokeLang_Nimc);
			fields2.put("P FirstName:", pFirstName_Nimc);
			fields2.put("P Middlename:", pMiddleName_Nimc);
			fields2.put("Profession:", profession_Nimc);

			fields2.put("P Surname:", pSurName_Nimc);
			fields2.put("Religion:", religion_Nimc);
			fields2.put("Title:", title_Nimc);
			fields2.put("Tracking ID:", trackId_Nimc);
			fields2.put("Employment Level:", employmentLevel_Nimc);

			for (Map.Entry<String, String> entry : fields2.entrySet()) {
				try {
					Assert.assertNotEquals(entry.getValue(), empty);
					Assert.assertNotEquals(entry.getValue(), null);
					testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
				} catch (Error e1) {
					testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
				}
			}

			//================Assert SIM Reg Data===============//
			TestUtils.testTitle("Assert SIM Reg Data");
			String firstName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[2]/td[3]")).getText();
			String lastName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[3]/td[3]")).getText();
			String otherName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[4]/td[3]")).getText();
			String motherMaiden_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[5]/td[3]")).getText();
			String dateOfBirth_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[6]/td[3]")).getText();
			String birthLga_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[7]/td[3]")).getText();
			String birthState_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[8]/td[3]")).getText();
			String gender_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[9]/td[3]")).getText();
			String stateOfOrigin_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[10]/td[3]")).getText();
			String birthCountry_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[11]/td[3]")).getText();
			String centralId_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[12]/td[3]")).getText();

			String eduLeve_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[13]/td[3]")).getText();
			String empStatus_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[14]/td[3]")).getText();
			String height_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[15]/td[3]")).getText();
			String cardStatus_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[16]/td[3]")).getText();
			String docNo_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[17]/td[3]")).getText();
			String email_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[18]/td[3]")).getText();
			String nokFirstName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[19]/td[3]")).getText();
			String maritalStatus_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[20]/td[3]")).getText();
			String middlename_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[21]/td[3]")).getText();
			String nokLga_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[22]/td[3]")).getText();
			String nokMiddleName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[23]/td[3]")).getText();

			String residenceAddress1_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[24]/td[3]")).getText();
			String residenceAddress2_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[25]/td[3]")).getText();
			String residenceTown_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[26]/td[3]")).getText();
			String residenceLga_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[27]/td[3]")).getText();
			String residencePostalCode_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[28]/td[3]")).getText();
			String residenceState_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[29]/td[3]")).getText();
			String residenceStatus_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[30]/td[3]")).getText();
			String selfOriginLga_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[31]/td[3]")).getText();
			String selfOriginPlace_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[32]/td[3]")).getText();
			String selfOriginState_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[33]/td[3]")).getText();
			String telephoneNo_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[34]/td[3]")).getText();

			String nokPostalCode_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[35]/td[3]")).getText();
			String nokState_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[36]/td[3]")).getText();
			String nokSurname_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[37]/td[3]")).getText();
			String nokTown_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[38]/td[3]")).getText();
			String nokAddress1_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[39]/td[3]")).getText();
			String nokAddress2_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[40]/td[3]")).getText();
			String n_spokeLang_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[41]/td[3]")).getText();
			String o_spokeLang_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[42]/td[3]")).getText();
			String pFirstName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[43]/td[3]")).getText();
			String pMiddleName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[44]/td[3]")).getText();
			String profession_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[45]/td[3]")).getText();

			String pSurName_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[46]/td[3]")).getText();
			String religion_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[47]/td[3]")).getText();
			String title_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[48]/td[3]")).getText();
			String trackId_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[49]/td[3]")).getText();
			String employmentLevel_SimRegData = getDriver().findElement(By.xpath("//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[50]/td[3]")).getText();
			//TestUtils.scrollToElement("XPATH", "//div[@id='ninDetails  flat.bfpSyncLogPk  ']/div/div/app-nin-detail-modal/div[2]/div/table/tbody/tr[13]/td[2");


			Map<String, String> fields3 = new HashMap<>();
			fields3.put("First Name:", firstName_SimRegData);
			fields3.put("Last Name:", lastName_SimRegData);
			fields3.put("Other Name:", otherName_SimRegData);
			fields3.put("Mother's Maiden Mame:", motherMaiden_SimRegData);
			fields3.put("Date of Birth:", dateOfBirth_SimRegData);
			fields3.put("Birth LGA:", birthLga_SimRegData);
			fields3.put("Birth State:", birthState_SimRegData);
			fields3.put("Gender:", gender_SimRegData);
			fields3.put("State of Origin:", stateOfOrigin_SimRegData);
			fields3.put("Birth Country:", birthCountry_SimRegData);
			fields3.put("Central ID:", centralId_SimRegData);

			fields3.put("Educational Level:", eduLeve_SimRegData);
			fields3.put("Employment Status:", empStatus_SimRegData);
			fields3.put("Height:", height_SimRegData);
			fields3.put("Card Status:", cardStatus_SimRegData);
			fields3.put("Document No:", docNo_SimRegData);
			fields3.put("Email:", email_SimRegData);
			fields3.put("Nok Firstname:", nokFirstName_SimRegData);
			fields3.put("Marital Status:", maritalStatus_SimRegData);
			fields3.put("Middlename:", middlename_SimRegData);
			fields3.put("NOK LGA:", nokLga_SimRegData);
			fields3.put("NOK Middlename:", nokMiddleName_SimRegData);

			fields3.put("Resisdence Address Line 1:", residenceAddress1_SimRegData);
			fields3.put("Resisdence Address Line 2:", residenceAddress2_SimRegData);
			fields3.put("Residence Town:", residenceTown_SimRegData);
			fields3.put("Residence LGA:", residenceLga_SimRegData);
			fields3.put("Residence Postal Code:", residencePostalCode_SimRegData);
			fields3.put("Residence State:", residenceState_SimRegData);
			fields3.put("Residence Status:", residenceStatus_SimRegData);
			fields3.put("Self Origin LGA:", selfOriginLga_SimRegData);
			fields3.put("Self Origin Place:", selfOriginPlace_SimRegData);
			fields3.put("Self Origin State:", selfOriginState_SimRegData);
			fields3.put("Telephone NO:", telephoneNo_SimRegData);

			fields3.put("NOK Postal Code:", nokPostalCode_SimRegData);
			fields3.put("NOK State:", nokState_SimRegData);
			fields3.put("NOK Surname:", nokSurname_SimRegData);
			fields3.put("NOK Town:", nokTown_SimRegData);
			fields3.put("NOK Address 1:", nokAddress1_SimRegData);
			fields3.put("NOK Address 2:", nokAddress2_SimRegData);
			fields3.put("N Spoken Lang:", n_spokeLang_SimRegData);
			fields3.put("O Spoken Lang:", o_spokeLang_SimRegData);
			fields3.put("P FirstName:", pFirstName_SimRegData);
			fields3.put("P Middlename:", pMiddleName_SimRegData);
			fields3.put("Profession:", profession_SimRegData);

			fields3.put("P Surname:", pSurName_SimRegData);
			fields3.put("Religion:", religion_SimRegData);
			fields3.put("Title:", title_SimRegData);
			fields3.put("Tracking ID:", trackId_SimRegData);
			fields3.put("Employment Level:", employmentLevel_SimRegData);

			for (Map.Entry<String, String> entry : fields3.entrySet()) {
				try {
					Assert.assertNotEquals(entry.getValue(), empty);
					Assert.assertNotEquals(entry.getValue(), null);
					testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
				} catch (Error e1) {
					testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
				}
			}

		}
	}
}
