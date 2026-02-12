package com.hospital.base;

import com.hospital.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;

    // Logger
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() throws IOException {

        log.info("===== Test Setup Started =====");

        // Load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config.properties"
        );
        prop.load(fis);
        fis.close();

        log.info("Configuration file loaded successfully");

        // Initialize driver
        String browser = prop.getProperty("browser");
        log.info("Launching browser: {}", browser);

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.initDriver(browser);

        // Apply waits
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(
                        Integer.parseInt(prop.getProperty("implicitWait"))
                )
        );

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(
                        Integer.parseInt(prop.getProperty("pageLoadTimeout"))
                )
        );

        log.info("Waits applied successfully");

        // Launch application
        String url = prop.getProperty("baseUrl");
        driver.get(url);

        log.info("Application launched: {}", url);
    }

    @AfterMethod
    public void tearDown() {

        log.info("===== Test Teardown Started =====");

        if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully");
        }
    }

    // Screenshot method for Extent Report
    public String takeScreenshot(String testName) {

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            Path screenshotDir = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDir);

            Path dest = screenshotDir.resolve(
                    testName + "_" + System.currentTimeMillis() + ".png"
            );

            Files.copy(src.toPath(), dest);

            log.info("Screenshot saved at: {}", dest.toString());

            return dest.toString();

        } catch (Exception e) {
            log.error("Screenshot capture failed: {}", e.getMessage());
            return "";
        }
    }
}
