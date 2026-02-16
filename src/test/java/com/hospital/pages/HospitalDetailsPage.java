package com.hospital.pages;

import com.hospital.model.HospitalInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HospitalDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HospitalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    // ✅ One method to fetch everything
    public HospitalInfo fetchHospitalInfo(String url, int sNo) {
        String name = getHospitalName();
        int beds = getBedsCount();
        double rating = getRating();
        String phone = getPhoneNumber();
        return new HospitalInfo(sNo, name, beds, rating, phone, url);
    }

    // ✅ Hospital Name
    public String getHospitalName() {
        By name = By.xpath("//h1[contains(@class,'c-profile__title') or self::h1]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(name)).getText().trim();
        } catch (Exception e) {
            return "NOT_FOUND";
        }
    }

    // ✅ Beds Count
    public int getBedsCount() {
        By beds = By.xpath("//*[@data-qa-id='bed_count']");
        try {
            String text = getTextIfPresent(beds);
            if (text.isEmpty()) {
                text = wait.until(ExpectedConditions.visibilityOfElementLocated(beds)).getText().trim();
            }
            String numberOnly = text.replaceAll("[^0-9]", "");
            return numberOnly.isEmpty() ? -1 : Integer.parseInt(numberOnly);
        } catch (Exception e) {
            return -1;
        }
    }

    // ✅ Rating
    public double getRating() {
        By ratingValue = By.xpath("//span[contains(@class,'common__star-rating__value')]");
        try {
            String txt = getTextIfPresent(ratingValue);
            if (txt.isEmpty()) {
                txt = wait.until(ExpectedConditions.visibilityOfElementLocated(ratingValue)).getText().trim();
            }
            return txt.isEmpty() ? -1 : Double.parseDouble(txt);
        } catch (Exception e) {
            return -1;
        }
    }

    // ✅ Phone Number (tries direct first, else clicks Call Now)
    public String getPhoneNumber() {
        By phone = By.xpath("//*[@data-qa-id='phone_number']");
        By callBtn = By.xpath("//button[@data-qa-id='call_button']");

        // 1) If already visible and text present
        String existing = getTextIfPresent(phone);
        if (!existing.isEmpty()) return existing;

        // 2) Click Call Now (if present)
        try {
            if (!driver.findElements(callBtn).isEmpty()) {
                driver.findElements(callBtn).get(0).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(callBtn)).click();
            }
        } catch (Exception ignored) {}

        // 3) Wait for phone non-empty
        try {
            wait.until(d -> {
                List<WebElement> els = d.findElements(phone);
                if (els.isEmpty()) return false;
                String t = els.get(0).getText().trim();
                return !t.isEmpty();
            });
            return driver.findElements(phone).get(0).getText().trim();
        } catch (Exception e) {
            return "NOT_FOUND";
        }
    }

    // ✅ Ensure phone is visible AND has text (for screenshot accuracy)
    public void ensurePhoneNumberVisible() {
        By phone = By.xpath("//*[@data-qa-id='phone_number']");
        By callBtn = By.xpath("//button[@data-qa-id='call_button']");

        try {
            // If phone not visible, click Call Now
            if (driver.findElements(phone).isEmpty()) {
                if (!driver.findElements(callBtn).isEmpty()) {
                    driver.findElements(callBtn).get(0).click();
                } else {
                    wait.until(ExpectedConditions.elementToBeClickable(callBtn)).click();
                }
            }

            // Wait until phone has NON-EMPTY text
            wait.until(d -> {
                List<WebElement> els = d.findElements(phone);
                if (els.isEmpty()) return false;
                String txt = els.get(0).getText().trim();
                return !txt.isEmpty();
            });

        } catch (Exception ignored) {}
    }

    // ✅ Scroll phone into center so it appears in screenshot
    public void scrollToPhoneNumber() {
        By phone = By.xpath("//*[@data-qa-id='phone_number']");
        try {
            WebElement phoneEl = wait.until(ExpectedConditions.visibilityOfElementLocated(phone));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", phoneEl
            );
            Thread.sleep(400);
        } catch (Exception ignored) {}
    }

    // ---------- helpers ----------
    private String getTextIfPresent(By locator) {
        try {
            List<WebElement> els = driver.findElements(locator);
            if (!els.isEmpty()) {
                String txt = els.get(0).getText();
                return txt == null ? "" : txt.trim();
            }
        } catch (Exception ignored) {}
        return "";
    }
}