package com.saucedemo.tests;

import com.saucedemo.core.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Tests for products page functionality
 */
public class ProductsTest extends BaseTest {
    private ProductsPage productsPage;

    /**
     * Login before each test
     */
    @BeforeMethod
    public void setupTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isOnProductsPage(), "Failed to navigate to products page");
    }

    /**
     * Test products are displayed
     */
    @Test(description = "Test products are displayed correctly")
    public void testProductsDisplayed() {
        logger.info("Starting test: testProductsDisplayed");
        int numberOfProducts = productsPage.getNumberOfProducts();
        
        Assert.assertTrue(numberOfProducts > 0, "No products displayed on products page");
        Assert.assertEquals(numberOfProducts, 6, "Incorrect number of products displayed");
    }

    /**
     * Test product names
     */
    @Test(description = "Test product names are correct")
    public void testProductNames() {
        logger.info("Starting test: testProductNames");
        List<String> productNames = productsPage.getAllProductNames();
        
        Assert.assertTrue(productNames.contains("Sauce Labs Backpack"), "Expected product not found");
        Assert.assertTrue(productNames.contains("Sauce Labs Bike Light"), "Expected product not found");
        Assert.assertTrue(productNames.contains("Sauce Labs Bolt T-Shirt"), "Expected product not found");
    }

    /**
     * Test sorting products
     */
    @Test(description = "Test sorting products by name")
    public void testSortProductsByNameAZ() {
        logger.info("Starting test: testSortProductsByNameAZ");
        productsPage.sortProductsBy("Name (A to Z)");
        List<String> productNames = productsPage.getAllProductNames();
        
        // Verify first and last products after sorting A-Z
        Assert.assertEquals(productNames.get(0), "Sauce Labs Backpack", "Products not sorted correctly");
        Assert.assertEquals(productNames.get(productNames.size() - 1), "Test.allTheThings() T-Shirt (Red)", 
                "Products not sorted correctly");
    }

    /**
     * Test sorting products
     */
    @Test(description = "Test sorting products by name in reverse")
    public void testSortProductsByNameZA() {
        logger.info("Starting test: testSortProductsByNameZA");
        productsPage.sortProductsBy("Name (Z to A)");
        List<String> productNames = productsPage.getAllProductNames();
        
        // Verify first and last products after sorting Z-A
        Assert.assertEquals(productNames.get(0), "Test.allTheThings() T-Shirt (Red)", "Products not sorted correctly");
        Assert.assertEquals(productNames.get(productNames.size() - 1), "Sauce Labs Backpack", 
                "Products not sorted correctly");
    }

    /**
     * Test logging out
     */
    @Test(description = "Test logging out")
    public void testLogout() {
        logger.info("Starting test: testLogout");
        LoginPage loginPage = productsPage.logout();
        
        // Verify we're back on the login page
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "Not redirected to login page after logout");
    }
}