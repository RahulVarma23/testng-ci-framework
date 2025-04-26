package com.saucedemo.tests;

import com.saucedemo.core.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for login functionality
 */
public class LoginTest extends BaseTest {

    /**
     * Test valid login
     */
    @Test(description = "Test login with valid credentials")
    public void testValidLogin() {
        logger.info("Starting test: testValidLogin");
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login("visual_user", "secret_sauce");
        
        Assert.assertTrue(productsPage.isOnProductsPage(), "Failed to login with valid credentials");
        Assert.assertEquals(productsPage.getPageTitleText(), "Products", "Page title does not match expected");
    }

    /**
     * Test invalid login
     */
    @Test(description = "Test login with invalid credentials")
    public void testInvalidLogin() {
        logger.info("Starting test: testInvalidLogin");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "invalid_password");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for invalid login");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), 
                "Error message does not contain expected text");
    }

    /**
     * Test locked out user
     */
    @Test(description = "Test login with locked out user")
    public void testLockedOutUser() {
        logger.info("Starting test: testLockedOutUser");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for locked out user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"), 
                "Error message does not contain expected text");
    }

    /**
     * Test empty username
     */
    @Test(description = "Test login with empty username")
    public void testEmptyUsername() {
        logger.info("Starting test: testEmptyUsername");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "secret_sauce");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for empty username");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"), 
                "Error message does not contain expected text");
    }

    /**
     * Test empty password
     */
    @Test(description = "Test login with empty password")
    public void testEmptyPassword() {
        logger.info("Starting test: testEmptyPassword");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for empty password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"), 
                "Error message does not contain expected text");
    }
}