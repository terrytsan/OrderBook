package com.mthree.orderbook.dao.stockexchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockExchangeDaoImplTest {

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    public void setUp() throws Exception {
        final String DELETE_TRADES = "DELETE FROM trade";
        jdbc.update(DELETE_TRADES);

        final String DELETE_ORDER_HISTORY = "DELETE FROM orderHistory";
        jdbc.update(DELETE_ORDER_HISTORY);

        final String DELETE_ORDERS = "DELETE FROM order";
        jdbc.update(DELETE_ORDERS);

        final String DELETE_PARTIES = "DELETE FROM party";
        jdbc.update(DELETE_PARTIES);

        final String DELETE_STOCKS = "DELETE FROM stock";
        jdbc.update(DELETE_STOCKS);

        final String DELETE_STOCK_EXCHANGES = "DELETE FROM stockExchange";
        jdbc.update(DELETE_STOCK_EXCHANGES);
    }


}