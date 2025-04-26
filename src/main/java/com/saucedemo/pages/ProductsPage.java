package com.saucedemo.pages;

import com.saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for Products Page
 */
public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    private final String addToCartButtonFormat = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public ProductsPage(WebDriver driver) {
        super(driver);
        logger.info("Products page initialized");
    }

    /**
     * Get page title
     * @return page title text
     */
    public String getPageTitleText() {
        logger.info("Getting page title text");
        waitForVisibility(pageTitle);
        return pageTitle.getText();
    }

    /**
     * Check if we're on the products page
     * @return true if on products page, false otherwise
     */
    public boolean isOnProductsPage() {
        logger.info("Checking if on products page");
        return waitForVisibility(pageTitle).getText().equals("Products");
    }

    /**
     * Get number of products
     * @return number of products
     */
    public int getNumberOfProducts() {
        logger.info("Getting number of products");
        return inventoryItems.size();
    }

    /**
     * Get all product names
     * @return list of product names
     */
    public List<String> getAllProductNames() {
        logger.info("Getting all product names");
        return inventoryItems.stream()
                .map(item -> item.findElement(By.className("inventory_item_name")))
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Add product to cart
     * @param productName name of product to add
     * @return ProductsPage instance for method chaining
     */
    public ProductsPage addProductToCart(String productName) {
        logger.info("Adding product to cart: {}", productName);
        By addToCartButton = By.xpath(String.format(addToCartButtonFormat, productName));
        click(addToCartButton);
        return this;
    }

    /**
     * Open cart
     * @return CartPage instance
     */
    public CartPage openCart() {
        logger.info("Opening cart");
        click(cartIcon);
        return new CartPage(driver);
    }

    /**
     * Open menu
     * @return ProductsPage instance for method chaining
     */
    public ProductsPage openMenu() {
        logger.info("Opening menu");
        click(menuButton);
        return this;
    }

    /**
     * Click logout
     * @return LoginPage instance
     */
    public LoginPage logout() {
        logger.info("Logging out");
        openMenu();
        waitForVisibility(logoutLink);
        click(logoutLink);
        return new LoginPage(driver);
    }

    /**
     * Select sort option
     * @param sortOption sort option to select
     * @return ProductsPage instance for method chaining
     */
    public ProductsPage sortProductsBy(String sortOption) {
        logger.info("Sorting products by: {}", sortOption);
        click(sortDropdown);
        click(By.xpath("//option[text()='" + sortOption + "']"));
        return this;
    }
}