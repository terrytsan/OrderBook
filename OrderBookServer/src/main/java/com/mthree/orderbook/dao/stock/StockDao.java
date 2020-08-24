package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.entity.Stock;

public interface StockDao {

    /**
     * Returns the stock with the given ID.
     * @param id ID of the stock to search for.
     * @return Stock with the ID provided.
     */
    Stock getStockById(int id);

    /**
     * Returns the Stock associated with the stock exchange with the id provided.
     * @param stockExchangeId ID of the stock exchange.
     * @return Stock associated with the stock exchange.
     */
    Stock getStockByStockExchange(int stockExchangeId);
}
