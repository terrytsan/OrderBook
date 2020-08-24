package com.mthree.orderbook.service;

import com.mthree.orderbook.entity.Order;
import com.mthree.orderbook.entity.Trade;

import java.util.List;

public interface OrderMatcher {
	
	/**
	 * Attempts to find a match for the given order in a list of orders with opposite sides.
	 * Returns a trade if a match was found. Null otherwise.
	 *
	 * @param order          the Order to find a match for
	 * @param opposingOrders a list of existing live Orders from the opposite side to find a match for
	 * @return the resulting Trade if a match if found. Null otherwise.
	 */
	Trade findMatch(Order order, List<Order> opposingOrders);
}
