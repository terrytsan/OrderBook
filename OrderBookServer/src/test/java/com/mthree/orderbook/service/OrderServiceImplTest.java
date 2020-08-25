package com.mthree.orderbook.service;

import com.mthree.orderbook.dao.order.OrderDao;
import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.trade.TradeDao;
import com.mthree.orderbook.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {
	
	@MockBean
	private OrderDao orderDao;
	
	@MockBean
	private StockDao stockDao;
	
	@MockBean
	private PartyDao partyDao;
	
	@MockBean
	private TradeDao tradeDao;
	
	// Central timestamp used for all tests
	LocalDateTime timestamp;
	
	@Autowired
	private OrderService orderService;
	@MockBean
	private Clock clock;
	private Party testParty;
	private Stock testStock;
	// Objects used within tests
	private Order testOrder;
	
	@BeforeEach
	void setUp() {
		// Mocking clock
		Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		doReturn(fixedClock.instant()).when(clock).instant();
		doReturn(fixedClock.getZone()).when(clock).getZone();
		// Set the central timestamp
		timestamp = LocalDateTime.now(fixedClock);
		
		// Initialize test order
		StockExchange testExchange = new StockExchange(1, "XLONG", "LCH");
		testParty = new Party(1, "CP1", "Customer 1");
		testStock = new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01"));
		
		testOrder = new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"), timestamp, State.LIVE,
		                      1);
		
		Mockito.when(orderDao.getOrderById(anyInt()))
			.thenAnswer(i -> new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"),
			                           timestamp, State.LIVE,
			                           1));
	}
	
	@Test
	void testCancelOrder() {
		orderService.cancelOrder(1);
		testOrder.setState(State.CANCELLED);
		testOrder.setVersion(2);
		// Ensure updateOrder() in orderDao is called with the order's new state and version
		Mockito.verify(orderDao).updateOrder(testOrder);
	}
	
	@Test
	void testTickUpOrder() {
		orderService.tickUpOrder(1);
		testOrder.setPrice(new BigDecimal("0.18"));
		testOrder.setVersion(2);
		// Ensure updateOrder() in orderDao is called with the order's new price and version
		Mockito.verify(orderDao).updateOrder(testOrder);
	}
	
	@Test
	void testTickDownOrder() {
		orderService.tickDownOrder(1);
		testOrder.setPrice(new BigDecimal("0.16"));
		testOrder.setVersion(2);
		// Ensure updateOrder() in orderDao is called with the order's new price and version
		Mockito.verify(orderDao).updateOrder(testOrder);
	}
	
	@Test
	void testCreateOrder() {
		Mockito.when(stockDao.getStockById(anyInt())).thenReturn(testStock);
		Mockito.when(partyDao.getPartyById(anyInt())).thenReturn(testParty);
		Mockito.when(orderDao.getLiveSellOrders(anyInt())).thenReturn(new ArrayList<>());
		
		orderService.createOrder(1, 1, "buy", 20, new BigDecimal("0.17"));
		
		testOrder.setId(0);
		Mockito.verify(orderDao).addOrder(testOrder);
	}
	
	@Test
	void testUpdateOrderQuantity() {
		orderService.updateOrder(1, 40, new BigDecimal("0.17"));
		testOrder.setQuantity(40);
		testOrder.setVersion(2);
		// Ensure updateOrder() in orderDao is called with the order's new quantity and version
		Mockito.verify(orderDao).updateOrder(testOrder);
	}
	
	@Test
	void testUpdateOrderPriceNoMatch() {
		// Update the price of an order
		BigDecimal newPrice = new BigDecimal("0.20");
		orderService.updateOrder(1, 20, newPrice);
		testOrder.setPrice(newPrice);
		testOrder.setVersion(2);
		// Ensure updateOrder() in orderDao is called only once with the order's new price and version
		Mockito.verify(orderDao).updateOrder(testOrder);
	}
	
	/**
	 * Test method where a complete match is formed (buy order and sell order quantity are the same)
	 */
	@Test
	void testUpdateOrderPriceOneCompleteMatch() {
		// "Add" a matching sell order to the mock dao
		List<Order> dummySellOrders = new ArrayList<>();
		Order dummySellOrder = new Order(2, testStock, testParty, Side.SELL, 20, new BigDecimal("0.12"),
		                                 timestamp,
		                                 State.LIVE, 1);
		dummySellOrders.add(dummySellOrder);
		Mockito.when(orderDao.getLiveSellOrders(anyInt())).thenReturn(dummySellOrders);
		
		// Ensure all updateOrder() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			//Counter to count number of times a method is invoked
			int invocationCount = 0;
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				// Check arguments for each updateOrder() call is correct
				switch (invocationCount) {
					case 1:
						// Prepare testOrder to what it should be
						testOrder.setPrice(new BigDecimal("0.12"));
						testOrder.setVersion(2);
						assertEquals(testOrder, invocationOnMock.getArgument(0));
						break;
					case 2:
						dummySellOrder.setState(State.COMPLETED);
						dummySellOrder.setVersion(2);
						assertEquals(dummySellOrder, invocationOnMock.getArgument(0));
						break;
					case 3:
						testOrder.setState(State.COMPLETED);
						testOrder.setVersion(3);
						assertEquals(testOrder, invocationOnMock.getArgument(0));
						break;
				}
				return null;
			}
		}).when(orderDao).updateOrder(any());
		
		// Ensure all addTrade() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			int invocationCount = 0;
			final Trade correctTrade = new Trade(
				new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.12"),
				          timestamp, State.LIVE, 2),
				new Order(2, testStock, testParty, Side.SELL, 20, new BigDecimal("0.12"),
				          timestamp, State.LIVE, 1),
				20,
				new BigDecimal("0.12"),
				timestamp);
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				if (invocationCount == 1) {
					// Check correct trade is created
					assertEquals(correctTrade, invocationOnMock.getArgument(0));
				}
				return null;
			}
		}).when(tradeDao).addTrade(any());
		
		
		// Update the price of the order
		orderService.updateOrder(1, 20, new BigDecimal("0.12"));
		
		// Check updateOrder() is only called 3 times
		verify(orderDao, times(3)).updateOrder(any());
		
		// Check addTrade() is only called once
		verify(tradeDao).addTrade(any(Trade.class));
	}
	
	
	/**
	 * Update an order to a new price which will partially match with two orders and then be completed itself.
	 */
	@Test
	void testUpdateOrderPriceTwoPartialMatch() {
		// "Add" 2 partial matches to the mock dao. The sum of the quantities will match testOrder
		List<Order> dummySellOrders = new ArrayList<Order>() {{
			add(new Order(2, testStock, testParty, Side.SELL, 15, new BigDecimal("0.12"), timestamp,
			              State.LIVE, 1));
			add(new Order(3, testStock, testParty, Side.SELL, 5, new BigDecimal("0.12"), timestamp,
			              State.LIVE, 1));
		}};
		Mockito.when(orderDao.getLiveSellOrders(anyInt())).thenAnswer(new Answer<List<Order>>() {
			private final List<Order> sellOrders = new ArrayList<Order>() {{
				add(new Order(2, testStock, testParty, Side.SELL, 15, new BigDecimal("0.12"), timestamp,
				              State.LIVE, 1));
				add(new Order(3, testStock, testParty, Side.SELL, 5, new BigDecimal("0.12"), timestamp,
				              State.LIVE, 1));
			}};
			int invocationCount = 0;
			
			public List<Order> answer(InvocationOnMock invocation) {
				invocationCount++;
				// Only return one order when function is called for the second time (id=2 will have been completed)
				if (invocationCount == 2) {
					sellOrders.remove(0);
				}
				return sellOrders;
			}
		});
		// Simulate order dao contents being updated
		Mockito.when(orderDao.getOrderById(anyInt())).thenAnswer(new Answer<Order>() {
			final Order testBuyOrder = new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"),
			                                     timestamp, State.LIVE,
			                                     1);
			int invocationCount = 0;
			
			@Override
			public Order answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				
				// this function is only called twice
				if (invocationCount == 2) {
					testBuyOrder.setQuantity(20);
				}
				return testBuyOrder;
			}
		});
		
		// Ensure all updateOrder() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			int invocationCount = 0;
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				switch (invocationCount) {
					case 1:
						testOrder.setPrice(new BigDecimal("0.12"));
						testOrder.setVersion(2);
						assertEquals(testOrder, invocationOnMock.getArgument(0));
						break;
					case 2:
						dummySellOrders.get(0).setState(State.COMPLETED);
						dummySellOrders.get(0).setVersion(2);
						assertEquals(dummySellOrders.get(0), invocationOnMock.getArgument(0));
						break;
					case 3:
						testOrder.setQuantity(5);
						testOrder.setVersion(3);
						assertEquals(testOrder, invocationOnMock.getArgument(0));
						break;
					case 4:
						dummySellOrders.get(1).setState(State.COMPLETED);
						dummySellOrders.get(1).setVersion(2);
						assertEquals(dummySellOrders.get(1), invocationOnMock.getArgument(0));
						break;
					case 5:
						testOrder.setState(State.COMPLETED);
						testOrder.setVersion(4);
						assertEquals(testOrder, invocationOnMock.getArgument(0));
						break;
				}
				return null;
			}
		}).when(orderDao).updateOrder(any());
		
		// Ensure all addTrade() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			final Trade[] correctTrades = new Trade[]{
				new Trade(
					new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.12"), timestamp, State.LIVE, 2),
					new Order(2, testStock, testParty, Side.SELL, 15, new BigDecimal("0.12"), timestamp, State.LIVE, 1),
					15,
					new BigDecimal("0.12"),
					timestamp),
				new Trade(
					new Order(1, testStock, testParty, Side.BUY, 5, new BigDecimal("0.12"), timestamp, State.LIVE, 3),
					new Order(3, testStock, testParty, Side.SELL, 5, new BigDecimal("0.12"), timestamp, State.LIVE, 1),
					5,
					new BigDecimal("0.12"),
					timestamp)};
			int invocationCount = 0;
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				switch (invocationCount) {
					case 1:
						assertEquals(correctTrades[0], invocationOnMock.getArgument(0));
						break;
					case 2:
						assertEquals(correctTrades[1], invocationOnMock.getArgument(0));
				}
				return null;
			}
		}).when(tradeDao).addTrade(any());
		
		// Update the price of the order
		orderService.updateOrder(1, 20, new BigDecimal("0.12"));
		
		// Check the number of calls to each method
		verify(orderDao, times(5)).updateOrder(any());
		verify(orderDao, times(2)).getOrderById(anyInt());
		verify(orderDao, times(2)).getLiveSellOrders(anyInt());
		verify(tradeDao, times(2)).addTrade(any());
	}
	
	/**
	 * Create a new sell order which will partially match with two orders and then be completed itself.
	 */
	@Test
	void testCreateSellOrderTwoPartialMatch() {
		// Create an order. Values reflect what it will be when it is first inputted to the system.
		Order orderToAdd = new Order(3, testStock, testParty, Side.SELL, 50, new BigDecimal("0.18"), timestamp,
		                             State.LIVE, 1);
		
		// "Add" 2 partial matches to the mock dao. The sum of the quantities will match the orderToAdd
		List<Order> dummyBuyOrders = new ArrayList<Order>() {{
			add(new Order(1, testStock, testParty, Side.BUY, 35, new BigDecimal("0.21"), timestamp,
			              State.LIVE, 1));
			add(new Order(2, testStock, testParty, Side.BUY, 15, new BigDecimal("0.24"), timestamp,
			              State.LIVE, 1));
		}};
		Mockito.when(orderDao.getLiveBuyOrders(anyInt())).thenAnswer(new Answer<List<Order>>() {
			private final List<Order> buyOrders = new ArrayList<Order>() {{
				add(new Order(1, testStock, testParty, Side.BUY, 35, new BigDecimal("0.21"), timestamp,
				              State.LIVE, 1));
				add(new Order(2, testStock, testParty, Side.BUY, 15, new BigDecimal("0.24"), timestamp,
				              State.LIVE, 1));
			}};
			int invocationCount = 0;
			
			public List<Order> answer(InvocationOnMock invocation) {
				invocationCount++;
				// Only return one order when function is called for the second time (id=2 order will have been completed)
				if (invocationCount == 2) {
					buyOrders.remove(1);
				}
				return buyOrders;
			}
		});
		
		// For creation of the order (used in createOrder())
		Mockito.when(stockDao.getStockById(anyInt())).thenReturn(testStock);
		Mockito.when(partyDao.getPartyById(anyInt())).thenReturn(testParty);
		
		// Called when sell order is updated
		Mockito.when(orderDao.getOrderById(anyInt())).thenAnswer(new Answer<Order>() {
			int invocationCount = 0;
			
			@Override
			public Order answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				orderToAdd.setId(3);
				return orderToAdd;
			}
		});
		
		// Ensure all updateOrder() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			int invocationCount = 0;
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				switch (invocationCount) {
					
					case 1:
						dummyBuyOrders.get(1).setState(State.COMPLETED);
						dummyBuyOrders.get(1).setVersion(2);
						assertEquals(dummyBuyOrders.get(1), invocationOnMock.getArgument(0));
						break;
					case 2:
						// Order already changed within updateOrder()
						assertEquals(orderToAdd, invocationOnMock.getArgument(0));
						break;
					case 3:
						assertEquals(orderToAdd, invocationOnMock.getArgument(0));
						break;
					case 4:
						dummyBuyOrders.get(0).setState(State.COMPLETED);
						dummyBuyOrders.get(0).setVersion(2);
						assertEquals(dummyBuyOrders.get(0), invocationOnMock.getArgument(0));
				}
				return null;
			}
		}).when(orderDao).updateOrder(any());
		
		// Ensure all addTrade() calls are done with the correct arguments
		doAnswer(new Answer<Void>() {
			final Trade[] correctTrades = new Trade[]{
				new Trade(
					new Order(2, testStock, testParty, Side.BUY, 15, new BigDecimal("0.24"), timestamp, State.LIVE, 1),
					new Order(3, testStock, testParty, Side.SELL, 50, new BigDecimal("0.18"), timestamp, State.LIVE, 1),
					15,
					new BigDecimal("0.24"),
					timestamp),
				new Trade(
					new Order(1, testStock, testParty, Side.BUY, 35, new BigDecimal("0.21"), timestamp, State.LIVE, 1),
					new Order(3, testStock, testParty, Side.SELL, 35, new BigDecimal("0.18"), timestamp, State.LIVE, 2),
					35,
					new BigDecimal("0.21"),
					timestamp)};
			int invocationCount = 0;
			
			@Override
			public Void answer(InvocationOnMock invocationOnMock) {
				invocationCount++;
				switch (invocationCount) {
					case 1:
						assertEquals(correctTrades[0], invocationOnMock.getArgument(0));
						break;
					case 2:
						assertEquals(correctTrades[1], invocationOnMock.getArgument(0));
				}
				return null;
			}
		}).when(tradeDao).addTrade(any());
		
		// Ensure call to addOrder is done with the correct argument
		doAnswer((Answer<Void>) invocationOnMock -> {
			Object[] arguments = invocationOnMock.getArguments();
			
			assertEquals(
				new Order(0, testStock, testParty, Side.SELL, 50, new BigDecimal("0.18"), timestamp, State.LIVE, 1),
				invocationOnMock.getArgument(0));
			// Update the id of the argument
			((Order) arguments[0]).setId(3);
			return null;
		}).when(orderDao).addOrder(any());
		
		// Create the sell order
		orderService.createOrder(orderToAdd.getStock().getId(), orderToAdd.getParty().getId(),
		                         orderToAdd.getSide().toString(), orderToAdd.getQuantity(), orderToAdd.getPrice());
		
		
		// Check the number of calls to each method
		verify(orderDao, times(1)).addOrder(any());
		verify(orderDao, times(4)).updateOrder(any());
		verify(orderDao, times(1)).getOrderById(anyInt());
		verify(orderDao, times(2)).getLiveBuyOrders(anyInt());
		verify(tradeDao, times(2)).addTrade(any());
	}
}