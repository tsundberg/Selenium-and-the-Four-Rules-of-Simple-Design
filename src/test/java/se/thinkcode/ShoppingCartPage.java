package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShoppingCartPage {

    public void verifyOneItemInShoppingCart(WebElement productRow) {
        WebElement quantityElement = productRow.findElement(By.name("0_qty"));
        String quantity = quantityElement.getAttribute("value");
        assertThat(quantity, is("1"));
    }

}
