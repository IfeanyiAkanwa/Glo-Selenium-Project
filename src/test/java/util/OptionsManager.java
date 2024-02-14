package util;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class OptionsManager {

	//Get Chrome Options
    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        //options.addArguments("--incognito");
       /* Proxy proxy = new Proxy();
        proxy.setHttpProxy("172.31.1.22"+":"+ 3128);
        proxy.setSslProxy("172.31.1.22"+":"+ 3128);
		 proxy.setSocksUsername("seamfix");
		 proxy.setSocksPassword("seamfix");
		 
		 DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		 capabilities.setCapability("proxy", proxy);
		 
        options.setCapability(CapabilityType.PROXY, proxy);*/
        /*Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setSslProxy("192.168.0.200" + ":" + 3128);
        proxy.setFtpProxy("192.168.0.200" + ":" + 3128);
        proxy.setSocksUsername("avishka");
        proxy.setSocksPassword("12345678");

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
        
           options.setCapability(ChromeOptions.CAPABILITY, desiredCapabilities);*/
        return options;
        /*ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .build();
        ChromeDriver driver = new ChromeDriver(service, options);*/
    }
 
    //Get Firefox Options
    public FirefoxOptions getFirefoxOptions () {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        //Accept Untrusted Certificates
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        //Use No Proxy Settings
        profile.setPreference("network.proxy.type", 0);
        //Set Firefox profile to capabilities
        options.setCapability(FirefoxDriver.PROFILE, profile);
        return options;
    }
}
