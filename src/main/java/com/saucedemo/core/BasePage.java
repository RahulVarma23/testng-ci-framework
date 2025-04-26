package com.saucedemo.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Objects
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static final int DEFAULT_TIMEOUT = 10;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Get the title of the page
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Wait for element to be clickable and click
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        logger.debug("Clicking on element: {}", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Wait for element to be visible and click
     * @param by locator for the element
     */
    protected void click(By by) {
        logger.debug("Clicking on element by locator: {}", by);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.click();
    }

    /**
     * Type text into a field
     * @param element WebElement to type into
     * @param text text to type
     */
    protected void type(WebElement element, String text) {
        logger.debug("Typing '{}' into element: {}", text, element);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Type text into a field
     * @param by locator for the element
     * @param text text to type
     */
    protected void type(By by, String text) {
        logger.debug("Typing '{}' into element by locator: {}", text, by);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @return the visible WebElement
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible
     * @param by locator for the element
     * @return the visible WebElement
     */
    protected WebElement waitForVisibility(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Check if element is displayed
     * @param element WebElement to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not displayed: {}", element);
            return false;
        }
    }

    /**
     * Scroll to element
     * @param element WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        logger.debug("Scrolling to element: {}", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Get text of element
     * @param element WebElement to get text from
     * @return text of element
     */
    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    /**
     * Get text of element
     * @param by locator for the element
     * @return text of element
     */
    protected String getText(By by) {
        WebElement element = waitForVisibility(by);
        return element.getText();
    }
}