package com.saucedemo.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for ExtentReports
 */
public class ExtentReportManager {
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new HashMap<>();
    private static final String REPORT_PATH = "target/extent-reports/";
    private static final String REPORT_FILE = "test-report.html";

    /**
     * Private constructor to prevent instantiation
     */
    private ExtentReportManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initialize ExtentReports
     */
    public static synchronized void initReports() {
        if (extent == null) {
            // Create directory if it doesn't exist
            java.io.File directory = new java.io.File(REPORT_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH + REPORT_FILE);
            sparkReporter.config().setDocumentTitle("SauceDemo Test Report");
            sparkReporter.config().setReportName("SauceDemo Automation Test Results");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            
            logger.info("Extent Reports initialized");
        }
    }

    /**
     * Flush reports
     */
    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Reports flushed");
        }
    }

    /**
     * Create a test in the report
     * @param testName name of test
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testMap.put(Thread.currentThread().getId(), test);
        logger.info("Created test in Extent Reports: {}", testName);
        return test;
    }

    /**
     * Get the current test
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }
}