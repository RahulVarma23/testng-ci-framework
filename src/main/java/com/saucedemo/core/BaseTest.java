package com.saucedemo.core;

import com.saucedemo.reporting.ExtentReportManager;
import com.saucedemo.reporting.TestListener;
import com.saucedemo.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Base class for all tests
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected ConfigReader config;

    /**
     * Setup before the test class
     */
    @BeforeSuite
    public void beforeSuite() {
        ExtentReportManager.initReports();
        config = new ConfigReader();
        logger.info("Test suite started");
    }

    /**
     * Setup before each test method
     * @param browserType browser to use for the test
     */
    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserType) {
        if (config == null) {
            config = new ConfigReader();
        }
        logger.info("Setting up WebDriver for browser: {}", browserType);
        driver = DriverFactory.getDriver(browserType);
        driver.get(config.getProperty("base.url"));
        driver.manage().window().maximize();
    }

    /**
     * Teardown after each test method
     * @param result test result
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.info("Tearing down WebDriver");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Teardown after the test class
     */
    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flushReports();
        logger.info("Test suite completed");
    }
}