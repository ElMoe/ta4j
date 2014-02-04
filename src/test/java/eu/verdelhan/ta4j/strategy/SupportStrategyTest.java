package eu.verdelhan.ta4j.strategy;

import eu.verdelhan.ta4j.strategy.SupportStrategy;
import eu.verdelhan.ta4j.mocks.MockStrategy;
import eu.verdelhan.ta4j.Operation;
import eu.verdelhan.ta4j.OperationType;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.mocks.MockIndicator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SupportStrategyTest {

	private MockIndicator<Double> indicator;

	@Before
	public void setUp() throws Exception {
		indicator = new MockIndicator<Double>(new Double[] { 96d, 90d, 94d, 97d, 95d, 110d });
	}

	@Test
	public void testSupportShouldBuy() {
		Operation[] enter = new Operation[] { null, null, null, null, null, null };

		Strategy neverBuy = new MockStrategy(enter, enter);

		Strategy support = new SupportStrategy(indicator, neverBuy, 95);

		Trade trade = new Trade();

		assertFalse(support.shouldOperate(trade, 0));
		assertTrue(support.shouldOperate(trade, 1));
		trade.operate(1);
		assertEquals(new Operation(1, OperationType.BUY), trade.getEntry());
		trade = new Trade();
		assertTrue(support.shouldOperate(trade, 2));
		trade.operate(2);
		assertEquals(new Operation(2, OperationType.BUY), trade.getEntry());
		trade = new Trade();
		assertFalse(support.shouldOperate(trade, 3));
		assertTrue(support.shouldOperate(trade, 4));
		trade.operate(4);
		assertEquals(new Operation(4, OperationType.BUY), trade.getEntry());
		assertFalse(support.shouldOperate(trade, 5));
	}
}
