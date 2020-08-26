package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.dao.stockexchange.StockExchangeDao;
import com.mthree.orderbook.entity.Stock;
import com.mthree.orderbook.entity.StockExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockDaoImplTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    StockDao stockDao;

    @Autowired
    StockExchangeDao stockExchangeDao;

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
    public void testAddGet() {
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

        Stock result = stockDao.getStockById(stock.getId());
        assertEquals(stock, result);
    }

    @Test
    public void addGetByStockExchange() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("TEST");
        stockExchange.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange);

        StockExchange stockExchange2 = new StockExchange();
        stockExchange2.setName("TEST 2");
        stockExchange2.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange2);

        Stock stock = new Stock();
        stock.setStockExchange(stockExchange);
        stock.setName("TEST STOCK");
        stock.setSymbol("TEST");
        stock.setMaxQuantity(100);
        stock.setTickSize(new BigDecimal("0.01"));
        stockDao.addStock(stock);

        Stock stock2 = new Stock();
        stock2.setStockExchange(stockExchange2);
        stock2.setName("TEST STOCK 2");
        stock2.setSymbol("TEST2");
        stock2.setMaxQuantity(80);
        stock2.setTickSize(new BigDecimal("0.02"));
        stockDao.addStock(stock2);

        assertEquals(stock, stockDao.getStocksByStockExchange(stockExchange.getId()).get(0));
        assertEquals(stock2, stockDao.getStocksByStockExchange(stockExchange2.getId()).get(0));
    }

    @Test
    public void testGetByStockExchangeEmpty() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("TEST");
        stockExchange.setCentralCounterParty("TESTING");
        stockExchangeDao.addStockExchange(stockExchange);

        List<Stock> stocks = new ArrayList<>();
        assertEquals(stocks, stockDao.getStocksByStockExchange(stockExchange.getId()));
    }
}