package com.hospital.base;

import com.hospital.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;

    @BeforeMethod
    public void setUp() throws IOException {

        // Load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config.properties"
        );
        prop.load(fis);
        fis.close();

        // Initialize driver
        String browser = prop.getProperty("browser");
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.initDriver(browser);


        // Apply waits
        driver.manage().timeouts().implicitlyWait(
                java.time.Duration.ofSeconds(
                        Integer.parseInt(prop.getProperty("implicitWait"))
                )
        );

        driver.manage().timeouts().pageLoadTimeout(
                java.time.Duration.ofSeconds(
                        Integer.parseInt(prop.getProperty("pageLoadTimeout"))
                )
        );

        // Launch application
        driver.get(prop.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}