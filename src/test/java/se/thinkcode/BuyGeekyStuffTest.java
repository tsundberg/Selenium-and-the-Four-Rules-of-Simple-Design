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
    public void shouldBuyFluxCapacitor() throws Exception {
        thinkGeekHelper.buyBackToTheFutureFluxCapacitor();
    }

    @Test
    public void shouldBuyDrWhoSonicScrewdriver() throws Exception {
        thinkGeekHelper.buyDrWhoSonicScrewdriver();
    }
}
