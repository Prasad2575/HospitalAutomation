package com.hospital.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    // ThreadLocal to support safe execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // This method initializes the driver based on browser name
    public WebDriver initDriver(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver());

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver.set(new EdgeDriver());

        } else {
            System.out.println("Please pass correct browser name");
        }

        // Common browser settings
        getDriver().manage().window().maximize();

        return getDriver();
    }

    // This method returns the current driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // This method quits the driver
    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
