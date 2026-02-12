package com.hospital.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ================= LOCATORS =================

    private By cityInput = By.xpath("//input[@placeholder='Search location']");
    private By searchInput = By.xpath("//input[@placeholder='Search doctors, clinics, hospitals, etc.']");
    private By cityOption(String city) {
        return By.xpath("//div[contains(text(),'" + city + "')]");
    }

    private By hospitalTypeOption =
            By.xpath("//div[contains(@class,'c-omni-suggestion-item')]//div[normalize-space()='Hospital']");

    // ================= METHODS =================

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isHomePageDisplayed() {
        return driver.getTitle().contains("Practo");
    }

    public void selectCity(String cityName) {

        WebElement cityBox = wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput));
        cityBox.click();

        // Clear existing city
        cityBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        cityBox.sendKeys(Keys.BACK_SPACE);

        // Type city
        cityBox.sendKeys(cityName);

        // Wait and click suggestion
        wait.until(ExpectedConditions.elementToBeClickable(cityOption(cityName))).click();
    }

    public void searchHospital() {

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchBox.click();
        searchBox.sendKeys("Hospital");

        wait.until(ExpectedConditions.elementToBeClickable(hospitalTypeOption)).click();
    }
}
