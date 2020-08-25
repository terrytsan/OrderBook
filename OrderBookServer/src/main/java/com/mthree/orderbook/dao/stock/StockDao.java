package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.entity.Stock;

import java.util.List;

public interface StockDao {

    /**
     * Returns the stock with the given ID.
     * @param id ID of the stock to search for.
     * @return Stock with the ID provided.
     */
    Stock getStockById(int id);

    /**
     * Returns a list of stocks associated with a stock exchange.
     * @param stockExchangeId ID of the stock exchange.
     * @return List of Stocks.
     */
    List<Stock> getStocksByStockExchange(int stockExchangeId);
}
