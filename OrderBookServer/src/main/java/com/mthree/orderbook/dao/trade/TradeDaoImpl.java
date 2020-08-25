package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.dao.order.OrderDaoImpl;
import com.mthree.orderbook.entity.Side;
import com.mthree.orderbook.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TradeDaoImpl implements TradeDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Trade> getTrades(int stockExchangeId, int count) {
        final String GET_TRADES =
                "SELECT * FROM trade " +
                        "INNER JOIN order " +
                        "ON trade.buyOrderId = order.id " +
                        "INNER JOIN stock " +
                        "ON order.stockId = stock.id " +
                        "WHERE stock.stockExchangeId = ?";
        List<Trade> trades = jdbc.query(GET_TRADES, new TradeMapper(), stockExchangeId);

        for(Trade trade : trades) {
            assignBuyAndSellOrders(trade);
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesForStock(int stockId) {
        final String GET_TRADES =
                "SELECT * FROM trade " +
                        "INNER JOIN order " +
                        "ON trade.buyOrderId = order.id " +
                        "WHERE order.stockId = ?";
        List<Trade> trades = jdbc.query(GET_TRADES, new TradeMapper(), stockId);

        for(Trade trade : trades) {
            assignBuyAndSellOrders(trade);
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesByOrderId(int orderId) {
        final String GET_TRADES =
                "SELECT * FROM trade " +
                        "WHERE buyOrderId = ? " +
                        "OR sellOrderId = ? ";
        List<Trade> trades = jdbc.query(GET_TRADES, new TradeMapper(), orderId, orderId);

        for(Trade trade : trades) {
            assignBuyAndSellOrders(trade);
        }
        return trades;
    }

    @Override
    public void addTrade(Trade trade) {
        final String ADD_TRADE =
                "INSERT INTO trade(buyOrderId, sellOrderId, quantity, price, timestamp) " +
                        "VALUES(?, ?, ?, ?, ?)";
        jdbc.update(ADD_TRADE);

        trade.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    }

    private Trade assignBuyAndSellOrders(Trade trade) {
        final String GET_BUY =
                "SELECT * FROM order " +
                "INNER JOIN trade " +
                        "ON trade.buyOrderId = order.id " +
                        "WHERE trade.id = ?";
        trade.setBuyOrder(jdbc.queryForObject(GET_BUY, new OrderDaoImpl.OrderMapper(), trade.getId()));

        final String GET_SELL =
                "SELECT * FROM order " +
                        "INNER JOIN trade " +
                        "ON trade.sellOrderId = order.id " +
                        "WHERE trade.id = ?";
        trade.setSellOrder(jdbc.queryForObject(GET_SELL, new OrderDaoImpl.OrderMapper(), trade.getId()));

        return trade;
    }

    public static final class TradeMapper implements RowMapper<Trade> {
        @Override
        public Trade mapRow(ResultSet rs, int index) throws SQLException {
            Trade trade = new Trade();
            trade.setId(rs.getInt("id"));
            trade.setQuantity(rs.getInt("quantity"));
            trade.setPrice(rs.getBigDecimal("price"));
            trade.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
            return trade;
        }
    }
}
