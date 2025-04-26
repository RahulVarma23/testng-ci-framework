package com.saucedemo.pages;

import com.saucedemo.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Checkout Pages
 */
public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "error-message-container")
    private WebElement errorMessage;

    @FindBy(className = "complete-header")
    private WebElement orderCompleteHeader;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
        logger.info("Checkout page initialized");
    }

    /**
     * Get page title text
     * @return page title text
     */
    public String getPageTitleText() {
        logger.info("Getting page title text");
        return waitForVisibility(pageTitle).getText();
    }

    /**
     * Enter first name
     * @param firstName first name to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        type(firstNameField, firstName);
        return this;
    }

    /**
     * Enter last name
     * @param lastName last name to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        type(lastNameField, lastName);
        return this;
    }

    /**
     * Enter postal code
     * @param postalCode postal code to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        type(postalCodeField, postalCode);
        return this;
    }

    /**
     * Fill checkout form
     * @param firstName first name to enter
     * @param lastName last name to enter
     * @param postalCode postal code to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillCheckoutForm(String firstName, String lastName, String postalCode) {
        logger.info("Filling checkout form");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    /**
     * Click continue button
     * @return CheckoutPage instance
     */
    public CheckoutPage clickContinue() {
        logger.info("Clicking continue button");
        click(continueButton);
        return this;
    }

    /**
     * Click cancel button
     * @return CartPage instance
     */
    public CartPage clickCancel() {
        logger.info("Clicking cancel button");
        click(cancelButton);
        return new CartPage(driver);
    }

    /**
     * Click finish button
     * @return CheckoutPage instance
     */
    public CheckoutPage clickFinish() {
        logger.info("Clicking finish button");
        click(finishButton);
        return this;
    }

    /**
     * Complete checkout
     * @param firstName first name to enter
     * @param lastName last name to enter
     * @param postalCode postal code to enter
     * @return CheckoutPage instance
     */
    public CheckoutPage completeCheckout(String firstName, String lastName, String postalCode) {
        logger.info("Completing checkout");
        fillCheckoutForm(firstName, lastName, postalCode);
        clickContinue();
        return clickFinish();
    }

    /**
     * Get error message text
     * @return error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return waitForVisibility(errorMessage).getText();
    }

    /**
     * Check if order is complete
     * @return true if order is complete, false otherwise
     */
    public boolean isOrderComplete() {
        logger.info("Checking if order is complete");
        return isElementDisplayed(orderCompleteHeader);
    }

    /**
     * Get order complete header text
     * @return order complete header text
     */
    public String getOrderCompleteHeaderText() {
        logger.info("Getting order complete header text");
        return waitForVisibility(orderCompleteHeader).getText();
    }
}