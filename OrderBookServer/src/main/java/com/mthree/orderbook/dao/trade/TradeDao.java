package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.entity.Trade;

import java.util.List;

public interface TradeDao {
    List<Trade> getTrades(int stockExchangeId, int count);
    List<Trade> getTradesForStock(int stockId);
    List<Trade> getTradesByOrderId(int orderId);
    void addTrade(Trade trade);
}
