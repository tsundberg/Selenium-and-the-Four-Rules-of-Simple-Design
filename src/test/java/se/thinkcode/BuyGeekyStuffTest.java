package se.thinkcode;

import org.junit.Test;

public class BuyGeekyStuffTest {

    private GeekTestHelper geekTestHelper = new GeekTestHelper();

    @Test
    public void shouldBuyFluxCapacitor() throws Exception {
        geekTestHelper.buyFluxCapacitor();
    }

    @Test
    public void shouldBuyDrWhoSonicScrewdriver() throws Exception {
        geekTestHelper.buySonicScrewDriver();
    }

    @Test
    public void shouldBuyGameOfThronesShootGlass() throws Exception {
        geekTestHelper.buyGameOfThronsShotGlasses();
    }
}
