package se.thinkcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertNotNull;

public class BuyGeekyStuffTest {
    private WebDriver browser;

    @Before
    public void setUp() {
        browser = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void shouldPlaceFluxCapacitorInShoppingBag() throws Exception {
        ProductPage productPage = new ProductPage(browser);
        productPage.goToBackToTheFuture();

        String itemName = "Flux Capacitor";

        WebElement productRow = productPage.addFluxCapacitorToShoppingCart();

        WebElement productLink = productRow.findElement(By.partialLinkText(itemName));
        assertNotNull(productLink);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }

    @Test
    public void shouldPlaceDrWhoSonicScrewdriverInShoppingBag() throws Exception {
        ProductPage productPage = new ProductPage(browser);
        productPage.goToDrWho();

        String itemName = "Sonic Screwdriver";
        String itemId = "item-ee4a";
        WebDriverWait wait = productPage.locateItem(itemId);
        WebElement productRow = productPage.addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText(itemName));
        assertNotNull(productLink);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }

    @Test
    public void shouldPlaceGameOfThronesShootGlassInShoppingBag() throws Exception {
        ProductPage productPage = new ProductPage(browser);
        productPage.goToGameOfThrones();

        String itemId = "item-f3c0";
        String itemName = "Game of Thrones Shot Glass Set";
        WebDriverWait wait = productPage.locateItem(itemId);
        WebElement productRow = productPage.addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText(itemName));
        assertNotNull(productLink);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }
}
