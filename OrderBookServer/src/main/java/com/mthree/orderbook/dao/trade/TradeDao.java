package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.entity.Trade;

import java.util.List;

public interface TradeDao {

    /**
     * Returns a list of size count, of the most recent trades on a stock exchange.
     * @param stockExchangeId ID of the stock exchange.
     * @param count Number of trades in the list.
     * @return List of size count, of the most recent trades on a stock exchange.
     */
    List<Trade> getTrades(int stockExchangeId, int count);

    /**
     * Returns a list of trades for a given stock.
     * @param stockId ID of the stock to search for trades of.
     * @return List of trades for a given stock.
     */
    List<Trade> getTradesForStock(int stockId);

    /**
     * Returns a list of trades associated with a particular order.
     * @param orderId ID of the order to search for trades of.
     * @return List of trades associated with a particular order.
     */
    List<Trade> getTradesByOrderId(int orderId);

    /**
     * Adds a trade to the system.
     * @param trade Trade to be added to the system.
     */
    void addTrade(Trade trade);

}
