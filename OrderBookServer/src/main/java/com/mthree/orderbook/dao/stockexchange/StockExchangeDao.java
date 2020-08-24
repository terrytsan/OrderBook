package com.mthree.orderbook.dao.stockexchange;

import com.mthree.orderbook.entity.StockExchange;

import java.util.List;

public interface StockExchangeDao {
    StockExchange getStockExchangeById(int id);
    List<StockExchange> getAll();
}
