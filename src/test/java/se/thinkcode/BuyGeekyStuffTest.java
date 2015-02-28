package se.thinkcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BuyGeekyStuffTest {

    private WebDriver browser;
    private ThinkGeekHelper thinkGeekHelper;

    @Before
    public void setUp() {
        browser = new FirefoxDriver();
        thinkGeekHelper = new ThinkGeekHelper(browser);
    }

    @After
    public void tearDown() {
        browser.quit();
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
