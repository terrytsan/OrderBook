package com.mthree.orderbook.service;

import com.mthree.orderbook.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class OrderMatcherImplTest {
	
	@MockBean
	private Clock clock;
	private Clock fixedClock;
	private LocalDateTime timestamp;
	
	// Holds existing (not yet matched) sell orders
	private final List<Order> sellOrders = new ArrayList<>();
	// Holds existing (not yet matched) buy orders
	private final List<Order> buyOrders = new ArrayList<>();
	private StockExchange testExchange;
	private Party testParty;
	private Stock testStock;
	
	@Autowired
	private OrderMatcher matcher;
	
	@BeforeEach
	void setUp() {
		// Mocking clock
		fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		doReturn(fixedClock.instant()).when(clock).instant();
		doReturn(fixedClock.getZone()).when(clock).getZone();
		// Set the central timestamp
		timestamp = LocalDateTime.now(fixedClock);
		
		testExchange = new StockExchange(1, "XLONG", "LCH");
		testParty = new Party(1, "CP1", "Customer 1");
		testStock = new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01"));
//		LocalDateTime timestamp = timestamp.withNano(0).withSecond(0);
		
		// Initialize sell orders
		sellOrders.add(
			new Order(1, testStock, testParty, Side.SELL, 20, new BigDecimal("0.17"), timestamp, State.LIVE,
			          1));
		sellOrders.add(
			new Order(2, testStock, testParty, Side.SELL, 50, new BigDecimal("0.15"), timestamp, State.LIVE,
			          1));
		sellOrders.add(
			new Order(3, testStock, testParty, Side.SELL, 100, new BigDecimal("0.16"), timestamp, State.LIVE,
			          1));
		
		// Initialize buy orders
		buyOrders.add(
			new Order(4, testStock, testParty, Side.BUY, 20, new BigDecimal("0.12"), timestamp, State.LIVE,
			          1));
		buyOrders.add(
			new Order(5, testStock, testParty, Side.BUY, 50, new BigDecimal("0.10"), timestamp, State.LIVE,
			          1));
		buyOrders.add(
			new Order(6, testStock, testParty, Side.BUY, 100, new BigDecimal("0.16"), timestamp, State.LIVE,
			          1));
		
		
	}
	
	@Test
	void testFindMatchBuyOrderCompleteMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.BUY, 50, new BigDecimal("0.15"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, sellOrders);
		assertEquals(recipientOrder, generatedTrade.getBuyOrder());
		assertEquals(sellOrders.get(1), generatedTrade.getSellOrder());
		assertEquals(sellOrders.get(1).getQuantity(), generatedTrade.getQuantity());
	}
	
	/**
	 * Partial match with the buy order having a lower quantity than matched sell order.
	 */
	@Test
	void testFindMatchBuyOrderLowPartialMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.BUY, 10, new BigDecimal("0.15"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, sellOrders);
		assertEquals(recipientOrder, generatedTrade.getBuyOrder());
		assertEquals(sellOrders.get(1), generatedTrade.getSellOrder());
		assertEquals(recipientOrder.getQuantity(), generatedTrade.getQuantity(),
		             "The trade's quantity should be the buy order's.");
	}
	
	/**
	 * Partial match with the buy order having a higher quantity than matched sell order.
	 */
	@Test
	void testFindMatchBuyOrderHighPartialMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.BUY, 70, new BigDecimal("0.15"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, sellOrders);
		assertEquals(recipientOrder, generatedTrade.getBuyOrder());
		assertEquals(sellOrders.get(1), generatedTrade.getSellOrder());
		assertEquals(sellOrders.get(1).getQuantity(), generatedTrade.getQuantity(),
		             "The trade's quantity should be the sell order's.");
	}
	
	@Test
	void testFindMatchBuyOrderNoMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.BUY, 100, new BigDecimal("0.05"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, buyOrders);
		assertNull(generatedTrade);
	}
	
	@Test
	void testFindMatchBuyOrderTimePriority() {
		LocalDateTime time = timestamp;
		Order order = new Order(4, testStock, testParty, Side.SELL, 100, new BigDecimal("0.12"), time,
		                        State.LIVE, 1);
		Order delayedOrder = new Order(5, testStock, testParty, Side.SELL, 100, new BigDecimal("0.12"),
		                               time.plusSeconds(10),
		                               State.LIVE, 1);
		sellOrders.add(delayedOrder);
		sellOrders.add(order);
		Order recipientOrder = new Order(1, testStock, testParty,
		                                 Side.BUY, 50, new BigDecimal("0.13"), timestamp, State.LIVE, 1);
		Trade generatedTrade = matcher.findMatch(recipientOrder, sellOrders);
		assertEquals(order, generatedTrade.getSellOrder(), "The earlier sell order should have been matched.");
	}
	
	@Test
	void testFindMatchSellOrderCompleteMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.SELL, 100, new BigDecimal("0.12"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, buyOrders);
		assertEquals(buyOrders.get(2), generatedTrade.getBuyOrder());
		assertEquals(recipientOrder, generatedTrade.getSellOrder());
		assertEquals(buyOrders.get(2).getQuantity(), generatedTrade.getQuantity());
	}
	
	@Test
	void testFindMatchSellOrderLowPartialMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.SELL, 50, new BigDecimal("0.12"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, buyOrders);
		assertEquals(buyOrders.get(2), generatedTrade.getBuyOrder());
		assertEquals(recipientOrder, generatedTrade.getSellOrder());
		assertEquals(recipientOrder.getQuantity(), generatedTrade.getQuantity(),
		             "The trade's quantity should be the sell order's.");
	}
	
	@Test
	void testFindMatchSellOrderHighPartialMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.SELL, 150, new BigDecimal("0.12"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, buyOrders);
		assertEquals(buyOrders.get(2), generatedTrade.getBuyOrder());
		assertEquals(recipientOrder, generatedTrade.getSellOrder());
		assertEquals(buyOrders.get(2).getQuantity(), generatedTrade.getQuantity(),
		             "The trade's quantity should be the buy order's.");
	}
	
	@Test
	void testFindMatchSellOrderNoMatch() {
		Order recipientOrder = new Order(10, testStock, testParty,
		                                 Side.SELL, 100, new BigDecimal("0.60"), timestamp, State.LIVE, 1);
		
		Trade generatedTrade = matcher.findMatch(recipientOrder, buyOrders);
		assertNull(generatedTrade);
	}
	
	@Test
	void testFindMatchSellOrderTimePriority() {
		LocalDateTime time = timestamp;
		Order order = new Order(10, testStock, testParty, Side.BUY, 100, new BigDecimal("0.20"), time,
		                        State.LIVE, 1);
		Order delayedOrder = new Order(11, testStock, testParty, Side.BUY, 100, new BigDecimal("0.20"),
		                               time.plusSeconds(10),
		                               State.LIVE, 1);
		sellOrders.add(delayedOrder);
		sellOrders.add(order);
		Order recipientOrder = new Order(1, testStock, testParty,
		                                 Side.SELL, 50, new BigDecimal("0.13"), timestamp, State.LIVE, 1);
		Trade generatedTrade = matcher.findMatch(recipientOrder, sellOrders);
		assertEquals(order, generatedTrade.getBuyOrder(), "The earlier buy order should have been matched.");
	}
}