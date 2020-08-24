package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.entity.Trade;

import java.util.List;

public interface TradeDao {
    List<Trade> getTrades(int stockExchangeId, int count);
    List<Trade> getTradesForStock(int stockId);
    Trade getTradeByOrderId(int orderNumber);
    void addTrade(Trade trade);
}
