package se.thinkcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BuyGeekyStuffTest {
    private WebDriver browser;
    private GeekTestHelper geekTestHelper;

    @Before
    public void setUp() {
        browser = new FirefoxDriver();
        geekTestHelper = new GeekTestHelper(browser);
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void shouldPlaceFluxCapacitorInShoppingBag() throws Exception {
        geekTestHelper.buyFluxCapacitor();
    }

    @Test
    public void shouldPlaceDrWhoSonicScrewdriverInShoppingBag() throws Exception {
        geekTestHelper.buySonicScrewDriver();
    }

    @Test
    public void shouldPlaceGameOfThronesShootGlassInShoppingBag() throws Exception {
        geekTestHelper.buyGameOfThronsShotGlasses();
    }
}
