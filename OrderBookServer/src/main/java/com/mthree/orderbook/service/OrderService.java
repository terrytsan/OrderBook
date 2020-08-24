package com.mthree.orderbook.service;

import java.math.BigDecimal;

public interface OrderService {
	/**
	 * Updates the state of the specified order to cancelled.
	 *
	 * @param orderId int representing order id of the order to cancel
	 */
	void cancelOrder(int orderId);
	
	/**
	 * Increases the price of the specified order by amount specified by order's Stock.
	 *
	 * @param orderId int representing order id of the order to tick up
	 */
	void tickUpOrder(int orderId);
	
	/**
	 * Decreases the price of the specified order by amount specified by order's Stock.
	 *
	 * @param orderId int representing order id of the order to tick down
	 */
	void tickDownOrder(int orderId);
	
	/**
	 * Creates a new order, persists the new order and checks if there is a match.
	 *
	 * @param stockId  int representing id of the stock the order is for
	 * @param partyId  int representing if of the party of the order
	 * @param side     String representing if order is {@code "buy"} or {@code "sell"}
	 * @param quantity int representing quantity of the order
	 * @param price    BigDecimal representing limit price of the order
	 */
	void createOrder(int stockId, int partyId, String side, int quantity, BigDecimal price);
	
	/**
	 * Updates the specified order with the provided parameters.
	 *
	 * @param orderId  int representing the id of the order to update
	 * @param quantity int representing the new quantity of the order
	 * @param price    BigDecimal representing the new limit price of the order
	 */
	void updateOrder(int orderId, int quantity, BigDecimal price);
}
