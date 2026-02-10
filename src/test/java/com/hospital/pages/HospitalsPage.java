package com.hospital.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HospitalsPage {

    private WebDriver driver;

    public HospitalsPage(WebDriver driver) {
        this.driver = driver;
    }

    // TEMP locator (we will update after you share actual locator)
    private By searchBox = By.xpath("//input[@placeholder='Search doctors, clinics, hospitals, etc.']");  // placeholder

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSearchBoxPresent() {
        return driver.findElements(searchBox).size() > 0;
    }
}
