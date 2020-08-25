package com.mthree.orderbook.service;

import com.mthree.orderbook.dao.order.OrderDao;
import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.trade.TradeDao;
import com.mthree.orderbook.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
	
	@Autowired
	private OrderService orderService;
	
	private Order testOrder;
	private Party testParty;
	private Stock testStock;
	// Counter to count number of times a method is invoked
	private Integer invocationCount;
	
	
	@BeforeEach
	void setUp() {
		// Initialize test order
		StockExchange testExchange = new StockExchange(1, "XLONG", "LCH");
		testParty = new Party(1, "CP1", "Customer 1");
		testStock = new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01"));
		
		
		LocalDateTime timestamp = LocalDateTime.now();
		testOrder = new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"), timestamp, State.LIVE,
		                      1);

//		 Set up stub implementations of daos
//		Mockito.when(orderDao.getOrderById(anyInt()))
//			.thenReturn(new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"),
//			                      timestamp, State.LIVE,
//			                      1));
		Mockito.when(orderDao.getOrderById(anyInt()))
			.thenAnswer(i -> new Order(1, testStock, testParty, Side.BUY, 20, new BigDecimal("0.17"),
			                           timestamp, State.LIVE,
			                           1));
		invocationCount = 0;
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
		                                 LocalDateTime.now(),
		                                 State.LIVE, 1);
		dummySellOrders.add(dummySellOrder);
		Mockito.when(orderDao.getLiveSellOrders(anyInt())).thenReturn(dummySellOrders);
//		InOrder inOrder = Mockito.inOrder(orderDao);
		
		doAnswer(invocationOnMock -> {
			invocationCount++;
			
			// Check arguments for each updateOrder call is correct
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
		}).when(orderDao).updateOrder(any());
		
		
		// Update the price of the order
		orderService.updateOrder(1, 20, new BigDecimal("0.12"));


//		Mockito.verify(orderDao, atLeastOnce()).updateOrder(testOrder);
//		inOrder.verify(orderDao).updateOrder(testOrder);


//		ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
//		Mockito.verify(orderDao, atLeastOnce()).updateOrder(argument.capture());

//		List<Order> values = argument.getAllValues();

//		System.out.println(values);

//		dummySellOrder.setState(State.COMPLETED);
//		dummySellOrder.setVersion(2);
//		inOrder.verify(orderDao).updateOrder(dummySellOrder);
//		Mockito.verify(orderDao, atLeastOnce()).updateOrder(dummySellOrder);
//
//
//		testOrder.setState(State.COMPLETED);
//		testOrder.setVersion(3);
//		inOrder.verify(orderDao).updateOrder(testOrder);
//		Mockito.verify(orderDao, atLeastOnce()).updateOrder(testOrder);
		
		// Check updateOrder was only called 3 times
		verify(orderDao, times(3)).updateOrder(any(Order.class));
		
	}
	
	
	/**
	 * Update an order to a new price which will partially match with two orders
	 */
	@Test
	void testUpdateOrderPriceTwoPartialMatch() {
		// "Add" 2 partial matches to the mock dao. The sum of the quantities will match testOrder
		
		List<Order> dummySellOrders = new ArrayList<Order>() {{
			add(new Order(2, testStock, testParty, Side.SELL, 15, new BigDecimal("0.12"), LocalDateTime.now(),
			              State.LIVE, 1));
			add(new Order(3, testStock, testParty, Side.SELL, 5, new BigDecimal("0.12"), LocalDateTime.now(),
			              State.LIVE, 1));
		}};
		Mockito.when(orderDao.getLiveSellOrders(anyInt())).thenAnswer(i -> new ArrayList<Order>() {{
			add(new Order(2, testStock, testParty, Side.SELL, 15, new BigDecimal("0.12"), LocalDateTime.now(),
			              State.LIVE, 1));
			add(new Order(3, testStock, testParty, Side.SELL, 5, new BigDecimal("0.12"), LocalDateTime.now(),
			              State.LIVE, 1));
		}});
		
		doAnswer(invocationOnMock -> {
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
//					assertEquals(dummySellOrders.get(0), invocationOnMock.getArgument(0));
					break;
			}
			
			return null;
		}).when(orderDao).updateOrder(any());
		
		
		// Update the price of the order
//		orderService.updateOrder(1, 20, new BigDecimal("0.12"));
		
		System.out.println(invocationCount);
	}
}