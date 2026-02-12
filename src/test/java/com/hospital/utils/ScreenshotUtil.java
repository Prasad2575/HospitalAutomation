package com.hospital.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String fileName) {
        try {
            String folder = System.getProperty("user.dir") + "/target/screenshots";
            Files.createDirectories(Path.of(folder));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destPath = folder + "/" + fileName + ".png";

            Files.copy(src.toPath(), Path.of(destPath), StandardCopyOption.REPLACE_EXISTING);
            return destPath;

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return "";
        }
    }
}
