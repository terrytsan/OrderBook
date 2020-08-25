package com.mthree.orderbook.service;


import com.mthree.orderbook.dao.order.OrderDao;
import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.trade.TradeDao;
import com.mthree.orderbook.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderMatcher matcher;
	
	private final OrderDao orderDao;
	
	private final StockDao stockDao;
	
	private final PartyDao partyDao;
	
	private final TradeDao tradeDao;
	
	private final Clock clock;
	
	public OrderServiceImpl(OrderDao orderDao, StockDao stockDao, PartyDao partyDao,
	                        TradeDao tradeDao, Clock clock, OrderMatcher matcher) {
		this.orderDao = orderDao;
		this.stockDao = stockDao;
		this.partyDao = partyDao;
		this.tradeDao = tradeDao;
		this.clock = clock;
		this.matcher = matcher;
	}
	
	/**
	 * Updates the specified order's state and persist the changes.
	 *
	 * @param order    Order to update the state of
	 * @param newState new state of the Order
	 */
	private void updateOrderState(Order order, State newState) {
		order.setState(newState);
		// Update the version number
		order.setVersion(order.getVersion() + 1);
		order.setTimestamp(LocalDateTime.now(clock));
		orderDao.updateOrder(order);
	}
	
	@Override
	public void cancelOrder(int orderId) {
		Order order = orderDao.getOrderById(orderId);
		updateOrderState(order, State.CANCELLED);
	}
	
	@Override
	public void tickUpOrder(int orderId) {
		Order order = orderDao.getOrderById(orderId);
		BigDecimal increaseAmount = order.getStock().getTickSize();
		order.setPrice(order.getPrice().add(increaseAmount));
		updateOrder(orderId, order.getQuantity(), order.getPrice());
	}
	
	@Override
	public void tickDownOrder(int orderId) {
		Order order = orderDao.getOrderById(orderId);
		BigDecimal decreaseAmount = order.getStock().getTickSize();
		order.setPrice(order.getPrice().subtract(decreaseAmount));
		updateOrder(orderId, order.getQuantity(), order.getPrice());
	}
	
	@Override
	public void createOrder(int stockId, int partyId, String side, int quantity, BigDecimal price) {
		List<Order> opposingOrders;
		// Create the Order object
		Stock stock = stockDao.getStockById(stockId);
		Party party = partyDao.getPartyById(partyId);
		
		Order order = new Order(stock, party, Side.valueOf(side.toUpperCase()), quantity, price,
		                        LocalDateTime.now(clock),
		                        State.LIVE, 1);
		
		// add the order to the database
		orderDao.addOrder(order);
		
		processOrder(order);
	}
	
	@Override
	public void updateOrder(int orderId, int quantity, BigDecimal price) {
		// Get the current order
		Order order = orderDao.getOrderById(orderId);
		
		boolean priceChanged = !order.getPrice().equals(price);
		boolean quantityChanged = order.getQuantity() != quantity;
		
		order.setQuantity(quantity);
		order.setPrice(price);
		// Update the version number and timestamp
		order.setVersion(order.getVersion() + 1);
		order.setTimestamp(LocalDateTime.now(clock));
		
		// Update the order
		orderDao.updateOrder(order);
		
		if (priceChanged || quantityChanged) {
			processOrder(order);
		}
	}
	
	/**
	 * Check if the given order has a match.
	 * If a match is found, a new trade is generated and both orders are updated
	 *
	 * @param order Order to process.
	 */
	private void processOrder(Order order) {
		List<Order> opposingOrders;
		// get the list of opposing orders
		if (order.getSide() == Side.BUY) {
			opposingOrders = orderDao.getLiveSellOrders(order.getStock().getId());
		} else {
			opposingOrders = orderDao.getLiveBuyOrders(order.getStock().getId());
		}
		
		// try and find a match
		Trade generatedTrade = matcher.findMatch(order, opposingOrders);
		
		if (generatedTrade == null) {
			return;
		}
		tradeDao.addTrade(generatedTrade);
		
		// Check if either order was completed
		Order sellOrder = generatedTrade.getSellOrder();
		Order buyOrder = generatedTrade.getBuyOrder();
		if (sellOrder.getQuantity() == buyOrder.getQuantity()) {
			updateOrderState(sellOrder, State.COMPLETED);
			updateOrderState(buyOrder, State.COMPLETED);
		} else if (sellOrder.getQuantity() < buyOrder.getQuantity()) {
			// Mark the sell order as complete
			updateOrderState(sellOrder, State.COMPLETED);
			
			// Update the quantity
			buyOrder.setQuantity(buyOrder.getQuantity() - sellOrder.getQuantity());
			updateOrder(buyOrder.getId(), buyOrder.getQuantity(), buyOrder.getPrice());
		} else if (sellOrder.getQuantity() > buyOrder.getQuantity()) {
			// Mark the buy order as complete
			updateOrderState(buyOrder, State.COMPLETED);
			
			// Update the quantity
			sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
			updateOrder(sellOrder.getId(), sellOrder.getQuantity(), sellOrder.getPrice());
		}
	}
}
