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

public class ThinkGeekHelper {

    private WebDriver browser;

    public ThinkGeekHelper(WebDriver browser) {
        this.browser = browser;
    }

    public void addFluxCapacitorToCart() throws InterruptedException {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/back-to-the-future");

        WebDriverWait wait = new WebDriverWait(browser, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item-1e2e")));
        browser.findElement(By.id("item-1e2e")).findElement(By.tagName("a")).click();

        browser.findElement(By.id("submitcart")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topnav_cart")));
        browser.findElement(By.id("topnav_cart")).click();
        browser.findElement(By.id("cart-table"));
        List<WebElement> cartRows = browser.findElements(By.className("cart-table-row"));
        assertThat(cartRows.size(), is(1));
        WebElement productRow = cartRows.get(0);

        WebElement productLink = productRow.findElement(By.partialLinkText("Flux Capacitor"));
        assertNotNull(productLink);

        WebElement quantityElement = productRow.findElement(By.name("0_qty"));
        String quantity = quantityElement.getAttribute("value");
        assertThat(quantity, is("1"));
    }

    public void addSonicScrewdriverToCart() throws InterruptedException {
        browser.get("http://www.thinkgeek.com");
        browser.get("http://www.thinkgeek.com/interests/doctorwho");

        WebDriverWait wait = new WebDriverWait(browser, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item-ee4a")));
        browser.findElement(By.id("item-ee4a")).findElement(By.tagName("a")).click();

        browser.findElement(By.id("submitcart")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topnav_cart")));
        browser.findElement(By.id("topnav_cart")).click();
        browser.findElement(By.id("cart-table"));
        List<WebElement> cartRows = browser.findElements(By.className("cart-table-row"));
        assertThat(cartRows.size(), is(1));
        WebElement productRow = cartRows.get(0);

        WebElement productLink = productRow.findElement(By.partialLinkText("Sonic Screwdriver"));
        assertNotNull(productLink);

        WebElement quantityElement = productRow.findElement(By.name("0_qty"));
        String quantity = quantityElement.getAttribute("value");
        assertThat(quantity, is("1"));
    }
}
