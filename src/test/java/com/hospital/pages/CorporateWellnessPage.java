package com.hospital.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CorporateWellnessPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // URL
    private final String corporateUrl = "https://www.practo.com/plus/corporate";

    // Locators (based on your inspect)
    private final By nameField = By.id("name");
    private final By orgField = By.id("organizationName"); // if not found, update to correct id
    private final By contactField = By.id("contactNumber"); // if not found, update
    private final By emailField = By.id("officialEmailId");
    private final By submitBtn = By.xpath("//button[@type='submit']");

    // Open page
    public void open() {
        driver.get(corporateUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }

    // Fill invalid details
    public void fillInvalidDetails(String name, String org, String contact, String email) {

        type(nameField, name);
        type(orgField, org);
        type(contactField, contact);
        type(emailField, email);
    }

    // Get Email field class (to verify error state)
    public String getEmailFieldClass() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        return el.getAttribute("class");
    }

    // Click submit (if enabled)
    public void clickScheduleDemo() {
        wait.until(ExpectedConditions.presenceOfElementLocated(submitBtn));
        driver.findElement(submitBtn).click();
    }

    // Check if submit button disabled
    public boolean isSubmitDisabled() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(submitBtn));
        String disabled = btn.getAttribute("disabled");
        return disabled != null;
    }

    private void type(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        el.click();
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);
        el.sendKeys(value);
    }
}
