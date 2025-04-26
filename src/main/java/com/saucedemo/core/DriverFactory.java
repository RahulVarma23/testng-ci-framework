package com.saucedemo.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;

/**
 * Factory class for creating WebDriver instances
 */
public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final int IMPLICIT_WAIT_TIME = 10;

    /**
     * Private constructor to prevent instantiation
     */
    private DriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get a WebDriver instance for the specified browser
     * @param browserType type of browser (chrome, firefox, edge, safari)
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browserType) {
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "firefox":
                logger.info("Initializing Firefox driver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless=new");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                logger.info("Initializing Edge driver");
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;
            case "safari":
                logger.info("Initializing Safari driver");
                SafariOptions safariOptions = new SafariOptions();
                driver = new SafariDriver(safariOptions);
                break;
            case "chrome":
            default:
                logger.info("Initializing Chrome driver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
        return driver;
    }
}