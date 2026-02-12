package com.hospital.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiagnosticsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DiagnosticsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Lab Tests menu
    private By labTestsMenu = By.xpath("//a[contains(.,'Lab Tests') or contains(.,'Lab tests')]");

    // TOP CITIES heading
    private By topCitiesHeading = By.xpath("//div[normalize-space()='TOP CITIES']");

    // ✅ Exact city names under TOP CITIES
    private By topCityNames = By.xpath(
            "//div[normalize-space()='TOP CITIES']/following-sibling::ul[1]" +
                    "//div[contains(@class,'o-f-color--primary')]"
    );

    public void openLabTests() {
        wait.until(ExpectedConditions.elementToBeClickable(labTestsMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(topCitiesHeading));
    }

    public List<String> getTopCities() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(topCitiesHeading));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(topCityNames));

        List<WebElement> elements = driver.findElements(topCityNames);

        List<String> cities = new ArrayList<>();
        for (WebElement el : elements) {
            String city = el.getText().trim();
            if (!city.isEmpty() && !cities.contains(city)) {
                cities.add(city);
            }
        }
        return cities;
    }
}
