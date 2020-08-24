package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.entity.Stock;

public interface StockDao {
    Stock getStockById(int id);
    Stock getStockByStockExchange(int stockExchangeId);
}
