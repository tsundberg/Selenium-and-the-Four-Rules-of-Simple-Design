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
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/back-to-the-future");

        String itemId = "item-1e2e";

        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);
        WebElement productRow = productPage.addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Flux Capacitor"));
        assertNotNull(productLink);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }

    @Test
    public void shouldPlaceDrWhoSonicScrewdriverInShoppingBag() throws Exception {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/doctorwho");

        String itemId = "item-ee4a";
        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);
        WebElement productRow = productPage.addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Sonic Screwdriver"));
        assertNotNull(productLink);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }

    @Test
    public void shouldPlaceGameOfThronesShootGlassInShoppingBag() throws Exception {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/gameofthrones");

        String itemId = "item-f3c0";
        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);
        WebElement productRow = productPage.addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Game of Thrones Shot Glass Set"));
        assertNotNull(productLink);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        shoppingCartPage.verifyOneItemInShoppingCart(productRow);
    }
}
