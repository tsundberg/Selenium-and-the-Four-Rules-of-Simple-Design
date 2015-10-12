package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProductPage {
    private WebDriver browser;

    public ProductPage(WebDriver browser) {
        this.browser = browser;
        browser.get("http://www.thinkgeek.com");
    }



    public WebDriverWait locateItem(String itemId) {
        WebDriverWait wait = new WebDriverWait(browser, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(itemId)));
        browser.findElement(By.id(itemId)).findElement(By.tagName("a")).click();
        return wait;
    }

    public WebElement addItemToShoppingCart(WebDriverWait wait) {
        browser.findElement(By.id("submitcart")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topnav_cart")));
        browser.findElement(By.id("topnav_cart")).click();
        browser.findElement(By.id("cart-table"));
        List<WebElement> cartRows = browser.findElements(By.className("cart-table-row"));
        assertThat(cartRows.size(), is(1));
        return cartRows.get(0);
    }


    public void goToBackToTheFuture() {
        browser.get("http://www.thinkgeek.com/interests/back-to-the-future");
    }

    public void goToDrWho() {
        browser.get("http://www.thinkgeek.com/interests/doctorwho");
    }

    public void goToGameOfThrones() {
        browser.get("http://www.thinkgeek.com/interests/gameofthrones");
    }
}
