package se.thinkcode;

import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class AddGeekyStuffToCartTest {

    private ThinkGeekHelper thinkGeekHelper = new ThinkGeekHelper();

    @Before
    public void setUp() {
        clearBrowserCache();
    }

    @Test
    public void shouldAddFluxCapacitorToCart() {
        thinkGeekHelper.addFluxCapacitorToCart();
    }

    @Test
    public void shouldAddDrWhoSonicScrewdriverToCart() {
        thinkGeekHelper.addSonicScrewdriverToCart();
    }
}
