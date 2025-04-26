package com.saucedemo.tests;

import com.saucedemo.core.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for cart functionality
 */
public class CartTest extends BaseTest {
    private ProductsPage productsPage;

    /**
     * Login before each test
     */
    @BeforeMethod
    public void setupTest() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.login("visual_user", "secret_sauce");
        Assert.assertTrue(productsPage.isOnProductsPage(), "Failed to navigate to products page");
    }

    /**
     * Test adding product to cart
     */
    @Test(description = "Test adding product to cart")
    public void testAddProductToCart() {
        logger.info("Starting test: testAddProductToCart");
        // Add a product to cart
        productsPage.addProductToCart("Sauce Labs Backpack");
        
        // Open cart
        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Failed to navigate to cart page");
        
        // Verify cart has one item
        Assert.assertEquals(cartPage.getNumberOfCartItems(), 1, "Cart should have 1 item");
        Assert.assertTrue(cartPage.getAllCartItemNames().contains("Sauce Labs Backpack"), 
                "Cart does not contain expected product");
    }

    /**
     * Test adding multiple products to cart
     */
    @Test(description = "Test adding multiple products to cart")
    public void testAddMultipleProductsToCart() {
        logger.info("Starting test: testAddMultipleProductsToCart");
        // Add multiple products to cart
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light");
        
        // Open cart
        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Failed to navigate to cart page");
        
        // Verify cart has two items
        Assert.assertEquals(cartPage.getNumberOfCartItems(), 2, "Cart should have 2 items");
        Assert.assertTrue(cartPage.getAllCartItemNames().contains("Sauce Labs Backpack"), 
                "Cart does not contain expected product");
        Assert.assertTrue(cartPage.getAllCartItemNames().contains("Sauce Labs Bike Light"), 
                "Cart does not contain expected product");
    }

    /**
     * Test removing product from cart
     */
    @Test(description = "Test removing product from cart")
    public void testRemoveProductFromCart() {
        logger.info("Starting test: testRemoveProductFromCart");
        // Add a product to cart
        productsPage.addProductToCart("Sauce Labs Backpack");
        
        // Open cart
        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Failed to navigate to cart page");
        
        // Verify cart has one item
        Assert.assertEquals(cartPage.getNumberOfCartItems(), 1, "Cart should have 1 item");
        
        // Remove product from cart
        cartPage.removeItem("Sauce Labs Backpack");
        
        // Verify cart is empty
        Assert.assertEquals(cartPage.getNumberOfCartItems(), 0, "Cart should be empty");
    }

    /**
     * Test continue shopping button
     */
    @Test(description = "Test continue shopping button")
    public void testContinueShopping() {
        logger.info("Starting test: testContinueShopping");
        // Open cart
        CartPage cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Failed to navigate to cart page");
        
        // Click continue shopping
        ProductsPage productsPageAfterContinue = cartPage.continueShopping();
        
        // Verify we're back on the products page
        Assert.assertTrue(productsPageAfterContinue.isOnProductsPage(), "Failed to navigate back to products page");
    }
}