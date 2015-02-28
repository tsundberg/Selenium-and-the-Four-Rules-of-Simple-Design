package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThinkGeekHelper {

    private WebDriver browser;

    private CartPage cartPage;

    public ThinkGeekHelper(WebDriver browser) {
        this.browser = browser;
        cartPage = new CartPage(browser);
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


        String partialProductName = "Flux Capacitor";
        cartPage.assertCart(partialProductName);
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


        String partialProductName = "Sonic Screwdriver";
        cartPage.assertCart(partialProductName);
    }
}
