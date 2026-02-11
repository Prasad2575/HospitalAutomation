package com.hospital.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    private WebDriver driver;



    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= Existing validation =================

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isHomePageDisplayed() {
        return driver.getTitle().contains("Practo");
    }

    // ================= New locators (from UI) =================

    // City input (left box)
    private By cityInput = By.xpath("//input[@placeholder='Search location']");

    // Generic city suggestion
    private By citySuggestion(String city) {
        return By.xpath("//div[contains(@class,'c-omni-suggestion-item')]//span[contains(@class,'c-omni-suggestion-item__content') and normalize-space()='" + city + "']");
    }

    // Search box (right box)
    private By searchInput =
            By.xpath("//input[contains(@placeholder,'Search doctors')]");

    // Hospitals suggestion
    private By hospitalsSuggestion =
            By.xpath("//span[contains(@class,'c-omni-suggestion-item__content') and normalize-space()='Hospital']");

    // ================= Helper =================
    private void waitFor(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }

    // ================= Actions =================

    // Operation 1: Select City
    public void selectCity(String city) {

        driver.findElement(cityInput).clear();
        driver.findElement(cityInput).sendKeys(city);

        waitFor(1500);

        driver.findElement(citySuggestion(city)).click();
    }

    public HospitalsPage searchHospitals() {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys("Hospital");

        waitFor(1500);
        driver.findElement(hospitalsSuggestion).click();

        return new HospitalsPage(driver);
    }

}

