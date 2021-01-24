package com.automation.testapplication;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
    private static final String BROWSER_DRIVER = "src/test/resources/browser_drivers/";

    /**
     * Factory method for getting browsers
     */
    public static WebDriver getBrowser(BrowserName browserName)
    {
        WebDriver driver = null;

        switch (browserName)
        {
            case CHROME:
                driver = drivers.get("Chrome");
                if (driver == null) {
                    System.setProperty("webdriver.chrome.driver", BROWSER_DRIVER + "chromedriver");
                    ChromeOptions options = new ChromeOptions();
//                    options.setBinary("/usr/local/bin/chromedriver");
//                    options.setBinary(BROWSER_DRIVER + "chromedriver");
                    options.setBinary("/Applications/Google Chrome 2.app/Contents/MacOS/Google Chrome");
                    options.addArguments("--no-sandbox");
                    driver = new ChromeDriver(options);
                    drivers.put("Chrome", driver);
                }
                break;

            case FIREFOX:
                driver = drivers.get("Firefox");
                if (driver == null) {
                    System.setProperty("webdriver.gecko.driver", BROWSER_DRIVER + "geckodriver");
                    FirefoxOptions ffOptions = new FirefoxOptions();
                    ffOptions.setCapability("marionette", true);
                    driver = new FirefoxDriver(ffOptions);
                    drivers.put("Firefox", driver);
                }
                break;

            case IE:
                driver = drivers.get("IE");
                if (driver == null) {
                    System.setProperty("webdriver.ie.driver", BROWSER_DRIVER + "IEDriverServer");
                    driver = new InternetExplorerDriver();
                    drivers.put("IE", driver);
                }
                break;
        }
        return driver;
    }

    public static void closeAllDrivers()
    {
        for (String key : drivers.keySet())
        {
            drivers.get(key).close();
            drivers.get(key).quit();
        }
    }
}
