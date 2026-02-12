package com.hospital.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.hospital.base.BaseTest;
import com.hospital.utils.ExtentManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        log.info("===== TEST SUITE STARTED: {} =====", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("START TEST: {}", testName);

        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        test.get().info("Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("PASS TEST: {}", result.getMethod().getMethodName());
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("FAIL TEST: {}", testName, result.getThrowable());

        test.get().fail(result.getThrowable());

        // Screenshot from BaseTest
        try {
            Object obj = result.getInstance();
            if (obj instanceof BaseTest) {
                String path = ((BaseTest) obj).takeScreenshot(testName);
                test.get().addScreenCaptureFromPath(path);
            }
        } catch (Exception e) {
            log.warn("Could not attach screenshot to Extent report: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("SKIP TEST: {}", result.getMethod().getMethodName());
        test.get().skip("Test skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("===== TEST SUITE FINISHED: {} =====", context.getName());
        extent.flush();
    }
}
