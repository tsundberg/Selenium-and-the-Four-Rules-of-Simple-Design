package se.thinkcode;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ThinkGeekHelper {
    private CartPage cartPage = new CartPage();

    public void addFluxCapacitorToCart() {
        open("http://www.thinkgeek.com");
        open("http://www.thinkgeek.com/interests/back-to-the-future");

        $("#item-1e2e a").click();
        $("#submitcart").click();
        $("#topnav_cart").click();

        cartPage.assertCart("Flux Capacitor");
    }

    public void addSonicScrewdriverToCart() {
        open("http://www.thinkgeek.com");
        open("http://www.thinkgeek.com/interests/doctorwho");

        $("#item-ee4a a").click();
        $("#submitcart").click();
        $("#topnav_cart").click();

        cartPage.assertCart("Sonic Screwdriver");
    }
}
