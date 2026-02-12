package com.hospital.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HospitalDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HospitalDetailsPage(WebDriver driver) {
        this.driver = driver;
        // ✅ Reduced wait from 15s to 8s to make execution faster
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    // ✅ Hospital Name
    public String getHospitalName() {
        By name = By.xpath("//h1[contains(@class,'c-profile__title') or self::h1]");

        // quick try first
        List<WebElement> els = driver.findElements(name);
        if (!els.isEmpty()) return els.get(0).getText().trim();

        return wait.until(ExpectedConditions.visibilityOfElementLocated(name)).getText().trim();
    }

    // ✅ Beds Count (data-qa-id="bed_count")
    public int getBedsCount() {
        By beds = By.xpath("//*[@data-qa-id='bed_count']");

        // quick try first
        List<WebElement> els = driver.findElements(beds);
        String text;
        if (!els.isEmpty()) {
            text = els.get(0).getText().trim();
        } else {
            text = wait.until(ExpectedConditions.visibilityOfElementLocated(beds)).getText().trim();
        }

        String numberOnly = text.replaceAll("[^0-9]", "");
        return numberOnly.isEmpty() ? -1 : Integer.parseInt(numberOnly);
    }

    // ✅ Rating (use exact value span first, else star_rating title)
    public double getRating() {

        By ratingValue = By.xpath("//span[contains(@class,'common__star-rating__value')]");

        // quick try first
        List<WebElement> vals = driver.findElements(ratingValue);
        if (!vals.isEmpty()) {
            String txt = vals.get(0).getText().trim();
            if (!txt.isEmpty()) return Double.parseDouble(txt);
        }

        // wait try
        try {
            String txt = wait.until(ExpectedConditions.visibilityOfElementLocated(ratingValue)).getText().trim();
            if (!txt.isEmpty()) return Double.parseDouble(txt);
        } catch (Exception ignored) {}

        // fallback: title attribute
        try {
            By ratingDiv = By.xpath("//*[@data-qa-id='star_rating']");
            WebElement el = driver.findElements(ratingDiv).isEmpty()
                    ? wait.until(ExpectedConditions.visibilityOfElementLocated(ratingDiv))
                    : driver.findElements(ratingDiv).get(0);

            String title = el.getAttribute("title");
            if (title != null && !title.trim().isEmpty()) {
                return Double.parseDouble(title.trim());
            }
        } catch (Exception ignored) {}

        return -1;
    }

    // ✅ Phone Number (fast)
    public String getPhoneNumber() {

        By phone = By.xpath("//*[@data-qa-id='phone_number']");

        // 1) Quick check (no wait)
        List<WebElement> phoneEls = driver.findElements(phone);
        if (!phoneEls.isEmpty()) {
            String number = phoneEls.get(0).getText().trim();
            if (!number.isEmpty()) return number;
        }

        // 2) If not visible, click Call Now then wait
        try {
            By callBtn = By.xpath("//button[@data-qa-id='call_button']");
            List<WebElement> callBtns = driver.findElements(callBtn);

            if (!callBtns.isEmpty()) {
                callBtns.get(0).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(callBtn)).click();
            }

            String number = wait.until(ExpectedConditions.visibilityOfElementLocated(phone)).getText().trim();
            if (!number.isEmpty()) return number;

        } catch (Exception ignored) {}

        return "NOT_FOUND";
    }
}
