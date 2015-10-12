package se.thinkcode;

import org.junit.Test;

public class BuyGeekyStuffTest {

    private GeekTestHelper geekTestHelper = new GeekTestHelper();

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
