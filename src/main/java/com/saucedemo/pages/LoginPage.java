package com.saucedemo.pages;

import com.saucedemo.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("Login page initialized");
    }

    /**
     * Enter username
     * @param username username to enter
     * @return LoginPage instance for method chaining
     */
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameField, username);
        return this;
    }

    /**
     * Enter password
     * @param password password to enter
     * @return LoginPage instance for method chaining
     */
    public LoginPage enterPassword(String password) {
        logger.info("Entering password: {}", password);
        type(passwordField, password);
        return this;
    }

    /**
     * Click login button
     * @return ProductsPage instance
     */
    public ProductsPage clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);
        return new ProductsPage(driver);
    }

    /**
     * Login with credentials
     * @param username username to enter
     * @param password password to enter
     * @return ProductsPage instance
     */
    public ProductsPage login(String username, String password) {
        logger.info("Logging in with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    /**
     * Get error message text
     * @return error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        waitForVisibility(errorMessage);
        return errorMessage.getText();
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isElementDisplayed(errorMessage);
    }
}