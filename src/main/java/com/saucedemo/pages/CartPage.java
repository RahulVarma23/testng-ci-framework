package com.saucedemo.pages;

import com.saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for Cart Page
 */
public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    private final String removeButtonFormat = "//div[text()='%s']/ancestor::div[@class='cart_item']//button";

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
        logger.info("Cart page initialized");
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
     * Check if on cart page
     * @return true if on cart page, false otherwise
     */
    public boolean isOnCartPage() {
        logger.info("Checking if on cart page");
        return getPageTitleText().equals("Your Cart");
    }

    /**
     * Get number of cart items
     * @return number of cart items
     */
    public int getNumberOfCartItems() {
        logger.info("Getting number of cart items");
        return cartItems.size();
    }

    /**
     * Get all cart item names
     * @return list of cart item names
     */
    public List<String> getAllCartItemNames() {
        logger.info("Getting all cart item names");
        return cartItems.stream()
                .map(item -> item.findElement(By.className("inventory_item_name")))
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Remove item from cart
     * @param itemName name of item to remove
     * @return CartPage instance for method chaining
     */
    public CartPage removeItem(String itemName) {
        logger.info("Removing item from cart: {}", itemName);
        By removeButton = By.xpath(String.format(removeButtonFormat, itemName));
        click(removeButton);
        return this;
    }

    /**
     * Proceed to checkout
     * @return CheckoutPage instance
     */
    public CheckoutPage proceedToCheckout() {
        logger.info("Proceeding to checkout");
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    /**
     * Continue shopping
     * @return ProductsPage instance
     */
    public ProductsPage continueShopping() {
        logger.info("Continuing shopping");
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }
}