package com.mthree.orderbook.dao.stockexchange;

import com.mthree.orderbook.entity.StockExchange;

import java.util.List;

public interface StockExchangeDao {

    /**
     * Returns the stock exchange with the given ID.
     * @param id ID of the stock exchange to search for.
     * @return StockExchange object with ID provided.
     */
    StockExchange getStockExchangeById(int id);

    /**
     * Returns a list of all stock exchanges.
     * @return List of all stock exchanges.
     */
    List<StockExchange> getAll();

    /**
     * Adds a stock exchange to the database.
     * @param stockExchange StockExchange to be added.
     */
    void addStockExchange(StockExchange stockExchange);
}
