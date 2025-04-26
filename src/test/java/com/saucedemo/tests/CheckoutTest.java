package com.saucedemo.tests;

import com.saucedemo.core.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for checkout functionality
 */
public class CheckoutTest extends BaseTest {
    private CartPage cartPage;

    /**
     * Setup for each test
     */
    @BeforeMethod
    public void setupTest() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        
        // Add a product to the cart
        productsPage.addProductToCart("Sauce Labs Backpack");
        
        // Go to cart
        cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Failed to navigate to cart page");
        Assert.assertEquals(cartPage.getNumberOfCartItems(), 1, "Cart should have 1 item");
    }

    /**
     * Test successful checkout
     */
    @Test(description = "Test successful checkout process")
    public void testSuccessfulCheckout() {
        logger.info("Starting test: testSuccessfulCheckout");
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout
        checkoutPage.completeCheckout("John", "Doe", "12345");
        
        // Verify order is complete
        Assert.assertTrue(checkoutPage.isOrderComplete(), "Order completion message not displayed");
        Assert.assertTrue(checkoutPage.getOrderCompleteHeaderText().contains("Thank you"), 
                "Order completion header does not contain expected text");
    }

    /**
     * Test checkout with empty first name
     */
    @Test(description = "Test checkout with empty first name")
    public void testCheckoutEmptyFirstName() {
        logger.info("Starting test: testCheckoutEmptyFirstName");
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout with empty first name
        checkoutPage.enterLastName("Doe")
                    .enterPostalCode("12345")
                    .clickContinue();
        
        // Verify error message
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"), 
                "Error message does not contain expected text");
    }

    /**
     * Test checkout with empty last name
     */
    @Test(description = "Test checkout with empty last name")
    public void testCheckoutEmptyLastName() {
        logger.info("Starting test: testCheckoutEmptyLastName");
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout with empty last name
        checkoutPage.enterFirstName("John")
                    .enterPostalCode("12345")
                    .clickContinue();
        
        // Verify error message
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Last Name is required"), 
                "Error message does not contain expected text");
    }

    /**
     * Test checkout with empty postal code
     */
    @Test(description = "Test checkout with empty postal code")
    public void testCheckoutEmptyPostalCode() {
        logger.info("Starting test: testCheckoutEmptyPostalCode");
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Complete checkout with empty postal code
        checkoutPage.enterFirstName("John")
                    .enterLastName("Doe")
                    .clickContinue();
        
        // Verify error message
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"), 
                "Error message does not contain expected text");
    }

    /**
     * Test cancel checkout
     */
    @Test(description = "Test canceling checkout")
    public void testCancelCheckout() {
        logger.info("Starting test: testCancelCheckout");
        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Cancel checkout
        CartPage cartPageAfterCancel = checkoutPage.clickCancel();
        
        // Verify we're back on the cart page
        Assert.assertTrue(cartPageAfterCancel.isOnCartPage(), "Failed to navigate back to cart page");
    }
}