package com.saucedemo.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for taking screenshots
 */
public class ScreenshotUtils {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    /**
     * Private constructor to prevent instantiation
     */
    private ScreenshotUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Take screenshot and save to file
     * @param driver WebDriver instance
     * @param testName name of test
     * @return path to screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            logger.error("Cannot take screenshot - driver is null");
            return null;
        }
        
        // Create screenshots directory if it doesn't exist
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Create unique filename
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String filename = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + filename;
        
        try {
            // Take screenshot
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath));
            logger.info("Screenshot saved to: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
            return null;
        }
    }
}