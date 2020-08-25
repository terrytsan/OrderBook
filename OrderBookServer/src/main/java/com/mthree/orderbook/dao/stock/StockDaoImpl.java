package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.dao.stockexchange.StockExchangeDaoImpl;
import com.mthree.orderbook.entity.Stock;
import com.mthree.orderbook.entity.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StockDaoImpl implements StockDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Stock getStockById(int id) {
        final String GET = "SELECT * FROM stock WHERE id = ?";
        Stock stock = jdbc.queryForObject(GET, new StockMapper(), id);
        return assignStockExchange(stock);
    }

    @Override
    public List<Stock> getStocksByStockExchange(int stockExchangeId) {
        final String GET =
                "SELECT * FROM stock " +
                        "INNER JOIN stockExchange " +
                        "ON stockExchange.id = stock.stockExchangeId " +
                        "WHERE stockExchange.id = ?";
        List<Stock> stocks = jdbc.query(GET, new StockMapper(), stockExchangeId);

        for(Stock stock : stocks) {
            assignStockExchange(stock);
        }
        return stocks;
    }

    private Stock assignStockExchange(Stock stock) {
        final String GET_STOCK_EXCHANGE =
                "SELECT * FROM stockExchange " +
                        "INNER JOIN stock ON stock.stockExchangeId = stockExhange.id " +
                        "WHERE stock.id = ?";
        stock.setStockExchange(jdbc.queryForObject(GET_STOCK_EXCHANGE,
                new StockExchangeDaoImpl.StockExchangeMapper(), stock.getId()));
        return stock;
    }

    public static final class StockMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int index) throws SQLException {
            Stock stock = new Stock();
            stock.setId(rs.getInt("id"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setMaxQuantity(rs.getInt("maxQuantity"));
            stock.setTickSize(rs.getBigDecimal("tickSize"));
            return stock;
        }
    }
}
