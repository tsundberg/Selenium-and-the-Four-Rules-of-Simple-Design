package se.thinkcode;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {

    public void assertCart(String partialProductName) {
        $$("#cart-table .cart-table-row").shouldHave(size(1));
      
        SelenideElement productRow = $("#cart-table .cart-table-row");

        productRow.find(By.partialLinkText(partialProductName)).shouldBe(visible);
        productRow.find(By.name("0_qty")).shouldHave(value("1"));
    }
}
