package se.thinkcode;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BuyGeekyStuffTest {

    private ThinkGeekHelper thinkGeekHelper;

    @Before
    public void setUp(){
        WebDriver driver = new FirefoxDriver();
        thinkGeekHelper = new ThinkGeekHelper(driver);
    }

    @Test
    public void shouldAddFluxCapacitorToCart() throws Exception {
        thinkGeekHelper.addFluxCapacitorToCart();
    }

    @Test
    public void shouldAddDrWhoSonicScrewdriverToCart() throws Exception {
        thinkGeekHelper.addSonicScrewdriverToCart();
    }
}
