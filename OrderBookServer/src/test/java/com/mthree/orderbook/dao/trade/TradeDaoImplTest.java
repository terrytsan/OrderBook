package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.dao.order.OrderDao;
import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.stockexchange.StockExchangeDao;
import com.mthree.orderbook.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradeDaoImplTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    OrderDao orderDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    StockExchangeDao stockExchangeDao;

    @Autowired
    PartyDao partyDao;

    @Autowired
    TradeDao tradeDao;

    @BeforeEach
    void setUp() {
        final String DELETE_TRADES = "DELETE FROM trade";
        jdbc.update(DELETE_TRADES);

        final String DELETE_ORDER_HISTORY = "DELETE FROM orderHistory";
        jdbc.update(DELETE_ORDER_HISTORY);

        final String DELETE_ORDERS = "DELETE FROM `order`";
        jdbc.update(DELETE_ORDERS);

        final String DELETE_PARTIES = "DELETE FROM party";
        jdbc.update(DELETE_PARTIES);

        final String DELETE_STOCKS = "DELETE FROM stock";
        jdbc.update(DELETE_STOCKS);

        final String DELETE_STOCK_EXCHANGES = "DELETE FROM stockExchange";
        jdbc.update(DELETE_STOCK_EXCHANGES);
    }

    @Test
    public void addGet() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("TEST");
        stockExchange.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange);

        Stock stock = new Stock();
        stock.setStockExchange(stockExchange);
        stock.setName("TEST STOCK");
        stock.setSymbol("TEST");
        stock.setMaxQuantity(100);
        stock.setTickSize(new BigDecimal("0.01"));
        stockDao.addStock(stock);

        Party party = new Party();
        party.setName("TEST PARTY");
        party.setSymbol("TEST");
        partyDao.addParty(party);

        Order order = new Order();
        order.setStock(stock);
        order.setParty(party);
        order.setSide(Side.BUY);
        order.setQuantity(30);
        order.setPrice(new BigDecimal("300.00"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300.00"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        Trade trade = new Trade();
        trade.setBuyOrder(order);
        trade.setSellOrder(order2);
        trade.setQuantity(30);
        trade.setPrice(order2.getPrice());
        trade.setTimestamp(order2.getTimestamp());
        tradeDao.addTrade(trade);

        Trade trade2 = new Trade();
        trade2.setBuyOrder(order);
        trade2.setSellOrder(order2);
        trade2.setQuantity(30);
        trade2.setPrice(order2.getPrice());
        trade2.setTimestamp(LocalDateTime.now().minusHours(1).withNano(0));
        tradeDao.addTrade(trade2);

        List<Trade> trades = tradeDao.getTrades(stockExchange.getId(), 2);
        assertEquals(trade, trades.get(0));
        assertEquals(trade2, trades.get(1));
    }

    @Test
    public void addGetByStock() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("TEST");
        stockExchange.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange);

        Stock stock = new Stock();
        stock.setStockExchange(stockExchange);
        stock.setName("TEST STOCK");
        stock.setSymbol("TEST");
        stock.setMaxQuantity(100);
        stock.setTickSize(new BigDecimal("0.01"));
        stockDao.addStock(stock);

        Party party = new Party();
        party.setName("TEST PARTY");
        party.setSymbol("TEST");
        partyDao.addParty(party);

        Order order = new Order();
        order.setStock(stock);
        order.setParty(party);
        order.setSide(Side.BUY);
        order.setQuantity(30);
        order.setPrice(new BigDecimal("300.00"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300.00"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        Trade trade = new Trade();
        trade.setBuyOrder(order);
        trade.setSellOrder(order2);
        trade.setQuantity(30);
        trade.setPrice(order2.getPrice());
        trade.setTimestamp(order2.getTimestamp());
        tradeDao.addTrade(trade);

        Trade trade2 = new Trade();
        trade2.setBuyOrder(order);
        trade2.setSellOrder(order2);
        trade2.setQuantity(30);
        trade2.setPrice(order2.getPrice());
        trade2.setTimestamp(LocalDateTime.now().minusHours(1).withNano(0));
        tradeDao.addTrade(trade2);

        List<Trade> trades = tradeDao.getTradesForStock(stock.getId());

        assertEquals(trade, trades.get(0));
        assertEquals(trade2, trades.get(1));
    }

    @Test
    public void addGetByOrder() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("TEST");
        stockExchange.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange);

        Stock stock = new Stock();
        stock.setStockExchange(stockExchange);
        stock.setName("TEST STOCK");
        stock.setSymbol("TEST");
        stock.setMaxQuantity(100);
        stock.setTickSize(new BigDecimal("0.01"));
        stockDao.addStock(stock);

        Party party = new Party();
        party.setName("TEST PARTY");
        party.setSymbol("TEST");
        partyDao.addParty(party);

        Order order = new Order();
        order.setStock(stock);
        order.setParty(party);
        order.setSide(Side.BUY);
        order.setQuantity(30);
        order.setPrice(new BigDecimal("300.00"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300.00"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        Trade trade = new Trade();
        trade.setBuyOrder(order);
        trade.setSellOrder(order2);
        trade.setQuantity(30);
        trade.setPrice(order2.getPrice());
        trade.setTimestamp(order2.getTimestamp());
        tradeDao.addTrade(trade);

        Trade trade2 = new Trade();
        trade2.setBuyOrder(order);
        trade2.setSellOrder(order2);
        trade2.setQuantity(30);
        trade2.setPrice(order2.getPrice());
        trade2.setTimestamp(LocalDateTime.now().minusHours(1).withNano(0));
        tradeDao.addTrade(trade2);

        List<Trade> trades = tradeDao.getTradesByOrderId(order.getId());

        assertEquals(trade, trades.get(0));
        assertEquals(trade2, trades.get(1));
    }
}