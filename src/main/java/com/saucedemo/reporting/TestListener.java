package com.saucedemo.reporting;

import com.aventstack.extentreports.Status;
import com.saucedemo.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Listener for TestNG tests
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    /**
     * Called when test starts
     * @param result test result
     */
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: {}", result.getName());
        ExtentReportManager.createTest(result.getMethod().getMethodName());
    }

    /**
     * Called when test succeeds
     * @param result test result
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: {}", result.getName());
        ExtentReportManager.getTest().log(Status.PASS, "Test passed");
    }

    /**
     * Called when test fails
     * @param result test result
     */
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getName());
        logger.error("Exception: {}", result.getThrowable().getMessage());
        
        // Log exception to extent report
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
        
        // Take screenshot if WebDriver is available
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName());
            if (screenshotPath != null) {
                ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            }
        }
    }

    /**
     * Called when test is skipped
     * @param result test result
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test Skipped: {}", result.getName());
        ExtentReportManager.getTest().log(Status.SKIP, "Test skipped");
    }

    /**
     * Called when test fails but within success percentage
     * @param result test result
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test Failed Within Success Percentage: {}", result.getName());
    }

    /**
     * Called when test run starts
     * @param context test context
     */
    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: {}", context.getName());
    }

    /**
     * Called when test run finishes
     * @param context test context
     */
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: {}", context.getName());
        ExtentReportManager.flushReports();
    }

    /**
     * Get WebDriver from test instance
     * @param result test result
     * @return WebDriver instance or null if not found
     */
    private WebDriver getDriverFromTest(ITestResult result) {
        Object testInstance = result.getInstance();
        
        // Try to find a field of type WebDriver in the test instance
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (field.getType() == WebDriver.class) {
                try {
                    field.setAccessible(true);
                    return (WebDriver) field.get(testInstance);
                } catch (Exception e) {
                    logger.error("Failed to get WebDriver from test", e);
                }
            }
        }
        
        // Try to find WebDriver in parent class if not found in current class
        Class<?> superClass = testInstance.getClass().getSuperclass();
        while (superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                if (field.getType() == WebDriver.class) {
                    try {
                        field.setAccessible(true);
                        return (WebDriver) field.get(testInstance);
                    } catch (Exception e) {
                        logger.error("Failed to get WebDriver from test", e);
                    }
                }
            }
            superClass = superClass.getSuperclass();
        }
        
        logger.error("Could not find WebDriver instance in test class");
        return null;
    }
}