package com.mthree.orderbook.service;

import com.mthree.orderbook.entity.*;

import java.util.List;

public interface DataFetchService {
	/**
	 * Returns a list of all stock exchanges.
	 *
	 * @return list of StockExchanges
	 */
	List<StockExchange> getStockExchanges();
	
	/**
	 * Returns a list of stocks in the specified stock exchange.
	 *
	 * @param stockExchangeId int representing id of the stock exchange
	 * @return a list of Stocks in the specified stock exchange
	 */
	List<Stock> getStocks(int stockExchangeId);
	
	/**
	 * Returns a list of live orders for a particular stock.
	 *
	 * @param stockId int representing id of the stock
	 * @return a list of live Orders for the specified stock
	 */
	List<Order> getLiveOrders(int stockId);
	
	/**
	 * Returns a variable number of trades in the specified stock exchange.
	 *
	 * @param stockExchangeId int representing id of the stock exchange
	 * @param count           int representing the number of trades to fetch
	 * @return a list consisting of {@code count} Trades
	 */
	List<Trade> getTrades(int stockExchangeId, int count);
	
	/**
	 * Returns a list of trades for a particular stock.
	 *
	 * @param stockId int representing the id of the stock
	 * @return a list of Trades for the specified stock
	 */
	List<Trade> getTradesForStock(int stockId);
	
	/**
	 * Returns a list of orders for a particular stock.
	 *
	 * @param stockId int representing the id of the stock
	 * @return a list of Orders for the specified stock
	 */
	List<Order> getOrdersForStock(int stockId);
	
	/**
	 * Returns an order with the specified id.
	 *
	 * @param orderId int representing the id of the desired order
	 * @return an Order with the specified id
	 */
	Order getOrderWithId(int orderId);
	
	/**
	 * Returns the history of a particular order. List will contain orders with the same id, but different versions.
	 *
	 * @param orderId int representing the id of the order
	 * @return a list of Orders representing the history of the specified order
	 */
	List<Order> getOrderHistoryWithOrderId(int orderId);
	
	/**
	 * Returns a list of trades involving a particular order (an order might be involved in multiple trades).
	 *
	 * @param orderId int representing the id of the order
	 * @return a list of Trades involving a particular order
	 */
	List<Trade> getTradesWithOrderId(int orderId);
	
	/**
	 * Returns a list of all the parties.
	 *
	 * @return list of Party
	 */
	List<Party> getAllParties();
}
