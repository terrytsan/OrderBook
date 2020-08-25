package com.mthree.orderbook.service;

import com.mthree.orderbook.dao.order.OrderDao;
import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.stockexchange.StockExchangeDao;
import com.mthree.orderbook.dao.trade.TradeDao;
import com.mthree.orderbook.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataFetchServiceImpl implements DataFetchService {
	
	private final StockExchangeDao stockExchangeDao;
	
	private final StockDao stockDao;
	
	private final OrderDao orderDao;
	
	private final TradeDao tradeDao;
	
	private final PartyDao partyDao;
	
	public DataFetchServiceImpl(StockExchangeDao stockExchangeDao, StockDao stockDao,
	                            OrderDao orderDao, TradeDao tradeDao, PartyDao partyDao) {
		this.stockExchangeDao = stockExchangeDao;
		this.stockDao = stockDao;
		this.orderDao = orderDao;
		this.tradeDao = tradeDao;
		this.partyDao = partyDao;
	}
	
	@Override
	public List<StockExchange> getStockExchanges() {
		return stockExchangeDao.getAll();
	}
	
	@Override
	public List<Stock> getStocks(int stockExchangeId) {
		return stockDao.getStocksByStockExchange(stockExchangeId);
	}
	
	@Override
	public List<Order> getLiveOrders(int stockId) {
		return orderDao.getLiveOrders(stockId);
	}
	
	@Override
	public List<Trade> getTrades(int stockExchangeId, int count) {
		return tradeDao.getTrades(stockExchangeId, count);
	}
	
	@Override
	public List<Trade> getTradesForStock(int stockId) {
		return tradeDao.getTradesForStock(stockId);
	}
	
	@Override
	public List<Order> getOrdersForStock(int stockId) {
		return orderDao.getOrdersForStock(stockId);
	}
	
	@Override
	public Order getOrderWithId(int orderId) {
		return orderDao.getOrderById(orderId);
	}
	
	@Override
	public List<Order> getOrderHistoryWithOrderId(int orderId) {
		return orderDao.getOrderHistoryWithOrderId(orderId);
	}
	
	@Override
	public List<Trade> getTradesWithOrderId(int orderId) {
		return tradeDao.getTradesByOrderId(orderId);
	}
	
	@Override
	public List<Party> getAllParties() {
		return partyDao.getAll();
	}
}
