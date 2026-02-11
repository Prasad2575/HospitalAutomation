//home page update
package com.hospital.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By practoLogo = By.xpath("//img[@class='practo_logo_new']");

    // Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isHomePageDisplayed() {
        return driver.getTitle().contains("Practo");
    }
}

