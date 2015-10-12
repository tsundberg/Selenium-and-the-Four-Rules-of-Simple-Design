package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GeekTestHelper {
    private WebDriver browser;

    public GeekTestHelper(WebDriver browser) {
        this.browser = browser;
    }

    public void buyFluxCapacitor() {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/back-to-the-future");

        String itemId = "item-1e2e";

        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);

        WebElement productRow = addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Flux Capacitor"));
        assertNotNull(productLink);

        verifyOneItemInShoppingCart(productRow);
    }

    public void buySonicScrewDriver() {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/doctorwho");

        String itemId = "item-ee4a";
        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);

        WebElement productRow = addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Sonic Screwdriver"));
        assertNotNull(productLink);

        verifyOneItemInShoppingCart(productRow);
    }

    public void buyGameOfThronsShotGlasses() {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/gameofthrones");

        String itemId = "item-f3c0";
        ProductPage productPage = new ProductPage(browser);
        WebDriverWait wait = productPage.locateItem(itemId);

        WebElement productRow = addItemToShoppingCart(wait);

        WebElement productLink = productRow.findElement(By.partialLinkText("Game of Thrones Shot Glass Set"));
        assertNotNull(productLink);

        verifyOneItemInShoppingCart(productRow);
    }

    private WebElement addItemToShoppingCart(WebDriverWait wait) {
        browser.findElement(By.id("submitcart")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topnav_cart")));
        browser.findElement(By.id("topnav_cart")).click();
        browser.findElement(By.id("cart-table"));
        List<WebElement> cartRows = browser.findElements(By.className("cart-table-row"));
        assertThat(cartRows.size(), is(1));
        return cartRows.get(0);
    }

    private void verifyOneItemInShoppingCart(WebElement productRow) {
        WebElement quantityElement = productRow.findElement(By.name("0_qty"));
        String quantity = quantityElement.getAttribute("value");
        assertThat(quantity, is("1"));
    }
}
