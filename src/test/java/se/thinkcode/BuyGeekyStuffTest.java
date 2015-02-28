package se.thinkcode;

import org.junit.Before;
import org.junit.Test;

public class BuyGeekyStuffTest {

    private ThinkGeekHelper thinkGeekHelper;

    @Before
    public void setUp(){
        thinkGeekHelper = new ThinkGeekHelper();
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
