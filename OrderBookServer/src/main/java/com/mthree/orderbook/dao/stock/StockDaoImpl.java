package com.mthree.orderbook.dao.stock;

import com.mthree.orderbook.dao.stockexchange.StockExchangeDaoImpl;
import com.mthree.orderbook.entity.Stock;
import com.mthree.orderbook.entity.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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

    @Override
    public void addStock(Stock stock) {
        final String ADD_STOCK =
                "INSERT INTO stock(stockExchangeId, name, symbol, maxQuantity, tickSize) " +
                        "VALUES(?, ?, ?, ?, ?)";
        jdbc.update(ADD_STOCK, stock.getStockExchange().getId(), stock.getName(), stock.getSymbol(),
                stock.getMaxQuantity(), stock.getTickSize().toString());

        stock.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    }

    private Stock assignStockExchange(Stock stock) {
        final String GET_STOCK_EXCHANGE =
                "SELECT * FROM stockExchange " +
                        "INNER JOIN stock ON stock.stockExchangeId = stockExchange.id " +
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
            stock.setName(rs.getString("name"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setMaxQuantity(rs.getInt("maxQuantity"));
            stock.setTickSize(new BigDecimal(rs.getString("tickSize")));
            return stock;
        }
    }
}
