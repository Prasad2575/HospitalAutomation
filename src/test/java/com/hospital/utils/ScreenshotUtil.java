package com.hospital.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    // ✅ OLD method (kept) - saves in /target/screenshots
    public static String takeScreenshot(WebDriver driver, String fileName) {
        try {
            String folder = System.getProperty("user.dir") + "/target/screenshots";
            Files.createDirectories(Path.of(folder));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = sanitizeFileName(fileName);
            String destPath = folder + "/" + safeName + ".png";

            Files.copy(src.toPath(), Path.of(destPath), StandardCopyOption.REPLACE_EXISTING);
            return destPath;

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return "";
        }
    }

    // ✅ NEW method - saves in custom folder (for hospital loop screenshots)
    public static String takeScreenshotToFolder(WebDriver driver, String folderPath, String fileName) {
        try {
            String base = System.getProperty("user.dir");
            String folder = base + "/" + folderPath;   // example: target/screenshots/hospitals
            Files.createDirectories(Path.of(folder));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = sanitizeFileName(fileName);
            String destPath = folder + "/" + safeName + ".png";

            Files.copy(src.toPath(), Path.of(destPath), StandardCopyOption.REPLACE_EXISTING);
            return destPath;

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return "";
        }
    }

    // ✅ helper to avoid invalid filename characters
    private static String sanitizeFileName(String name) {
        if (name == null || name.trim().isEmpty()) return "screenshot";
        return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }
}