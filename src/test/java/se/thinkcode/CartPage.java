package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CartPage {

    WebDriver browser;

    public CartPage(WebDriver browser) {
        this.browser = browser;
    }

    public void assertCart(String partialProductName) {
        browser.findElement(By.id("cart-table"));
        List<WebElement> cartRows = browser.findElements(By.className("cart-table-row"));
        assertThat(cartRows.size(), is(1));
        WebElement productRow = cartRows.get(0);

        WebElement productLink = productRow.findElement(By.partialLinkText(partialProductName));
        assertNotNull(productLink);

        WebElement quantityElement = productRow.findElement(By.name("0_qty"));
        String quantity = quantityElement.getAttribute("value");
        assertThat(quantity, is("1"));
    }


}
