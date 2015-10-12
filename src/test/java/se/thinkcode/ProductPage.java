package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private WebDriver browser;

    public ProductPage(WebDriver browser) {
        this.browser = browser;
    }

    public WebDriverWait locateItem(String itemId) {
        WebDriverWait wait = new WebDriverWait(browser, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(itemId)));
        browser.findElement(By.id(itemId)).findElement(By.tagName("a")).click();
        return wait;
    }

}
