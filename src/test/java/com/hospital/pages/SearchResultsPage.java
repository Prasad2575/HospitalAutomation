package com.hospital.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ✅ OLD: Returns hospital NAMES (keep it)
    public List<String> getFirstNOpen24x7HospitalNames(int limit) {

        List<String> hospitalNames = new ArrayList<>();

        By open24x7 = By.xpath("//span[text()='Open 24x7']");
        wait.until(ExpectedConditions.presenceOfElementLocated(open24x7));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        int scrollCount = 0;
        while (hospitalNames.size() < limit && scrollCount < 15) {

            List<WebElement> badges = driver.findElements(open24x7);

            for (WebElement badge : badges) {
                try {
                    WebElement container = badge.findElement(
                            By.xpath("./ancestor::div[contains(@class,'c-estb-info')]")
                    );

                    WebElement aTag = container.findElement(
                            By.xpath(".//a[contains(@href,'hospital')]")
                    );

                    String name = aTag.getText().trim();
                    if (!name.isEmpty() && !hospitalNames.contains(name)) {
                        hospitalNames.add(name);
                    }

                    if (hospitalNames.size() >= limit) break;

                } catch (Exception ignored) {}
            }

            if (hospitalNames.size() < limit) {
                js.executeScript("window.scrollBy(0,1200);");
                try { Thread.sleep(900); } catch (InterruptedException e) { /* ignore */ }
                scrollCount++;
            }
        }

        return hospitalNames;
    }

    // ✅ NEW: Returns hospital URLs (needed for details page)
    public List<String> getFirstNOpen24x7HospitalUrls(int limit) {

        Set<String> urls = new LinkedHashSet<>();

        By open24x7 = By.xpath("//span[text()='Open 24x7']");
        wait.until(ExpectedConditions.presenceOfElementLocated(open24x7));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        int scrollCount = 0;
        while (urls.size() < limit && scrollCount < 15) {

            List<WebElement> badges = driver.findElements(open24x7);

            for (WebElement badge : badges) {
                try {
                    WebElement container = badge.findElement(
                            By.xpath("./ancestor::div[contains(@class,'c-estb-info')]")
                    );

                    WebElement aTag = container.findElement(
                            By.xpath(".//a[contains(@href,'/hospital/') or contains(@href,'hospital')]")
                    );

                    String href = aTag.getAttribute("href");
                    if (href != null && !href.trim().isEmpty()) {
                        if (href.startsWith("/")) href = "https://www.practo.com" + href;
                        urls.add(href);
                    }

                    if (urls.size() >= limit) break;

                } catch (Exception ignored) {}
            }

            if (urls.size() < limit) {
                js.executeScript("window.scrollBy(0,1200);");
                try { Thread.sleep(900); } catch (InterruptedException e) { /* ignore */ }
                scrollCount++;
            }
        }

        return new ArrayList<>(urls);
    }
}
