package com.hospital.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            String reportPath = "target/ExtentReport.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Hospital Automation Report");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "HospitalAutomation");
            extent.setSystemInfo("Tester", "Prasad");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}

