package com.mthree.orderbook.dao.order;

import com.mthree.orderbook.dao.party.PartyDao;
import com.mthree.orderbook.dao.stock.StockDao;
import com.mthree.orderbook.dao.stockexchange.StockExchangeDao;
import com.mthree.orderbook.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderDaoImplTest {

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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        assertEquals(order, orderDao.getOrderById(order.getId()));
    }

    @Test
    public void addGetLive() {
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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.BUY);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300"));
        order2.setState(State.CANCELLED);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        List<Order> orders = orderDao.getLiveOrders(stock.getId());
        assertEquals(order, orders.get(0));
        assertEquals(1, orders.size());
    }

    @Test
    public void addGetLiveBuy() {
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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        List<Order> orders = orderDao.getLiveBuyOrders(stock.getId());
        assertEquals(order, orders.get(0));
        assertEquals(1, orders.size());
    }

    @Test
    public void addGetLiveSell() {
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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        List<Order> orders = orderDao.getLiveSellOrders(stock.getId());
        assertEquals(order2, orders.get(0));
        assertEquals(1, orders.size());
    }

    @Test
    public void addGetOrdersForStock() {
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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setStock(stock);
        order2.setParty(party);
        order2.setSide(Side.SELL);
        order2.setQuantity(30);
        order2.setPrice(new BigDecimal("300"));
        order2.setState(State.LIVE);
        order2.setVersion(1);
        order2.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order2);

        List<Order> orders = orderDao.getOrdersForStock(stock.getId());
        assertEquals(order, orders.get(0));
        assertEquals(order2, orders.get(1));
    }

    @Test
    public void addUpdateGetOrderHistory() {
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
        order.setPrice(new BigDecimal("300"));
        order.setState(State.LIVE);
        order.setVersion(1);
        order.setTimestamp(LocalDateTime.now().withNano(0));
        orderDao.addOrder(order);

        order.setQuantity(50);
        order.setVersion(2);
        orderDao.updateOrder(order);

        List<Order> orderHistory = orderDao.getOrderHistoryWithOrderId(order.getId());
        assertEquals(order, orderHistory.get(1));
    }
}