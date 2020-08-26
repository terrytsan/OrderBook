package com.mthree.orderbook.dao.trade;

import com.mthree.orderbook.dao.order.OrderDaoImpl;
import com.mthree.orderbook.dao.party.PartyDaoImpl;
import com.mthree.orderbook.dao.stock.StockDaoImpl;
import com.mthree.orderbook.dao.stockexchange.StockExchangeDaoImpl;
import com.mthree.orderbook.entity.Order;
import com.mthree.orderbook.entity.Stock;
import com.mthree.orderbook.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class TradeDaoImpl implements TradeDao {

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public List<Trade> getTrades(int stockExchangeId, int count) {
        final String GET_TRADES =
                "SELECT * FROM trade " +
                        "INNER JOIN orderHistory " +
                        "ON orderHistory.id = trade.buyOrderId " +
                        "INNER JOIN `order` " +
                        "ON orderHistory.orderId = `order`.id " +
                        "INNER JOIN stock " +
                        "ON `order`.stockId = stock.id " +
                        "WHERE stock.stockExchangeId = ?";
        List<Trade> trades = jdbc.query(GET_TRADES, new TradeMapper(), stockExchangeId);

        trades.sort(Comparator.comparing(Trade::getTimestamp));

        List<Trade> resultingTrades = new ArrayList<>();

        for(int i = 0; i < trades.size() && i < count; i++) {
            assignBuyAndSellOrders(trades.get(trades.size() - 1 - i));
            resultingTrades.add(trades.get(trades.size() - 1 - i));
        }
        return resultingTrades;
    }

    @Override
    public List<Trade> getTradesForStock(int stockId) {
        final String GET_TRADES =
                "SELECT * FROM trade " +
                        "INNER JOIN orderHistory " +
                        "ON trade.buyOrderId = orderHistory.id " +
                        "INNER JOIN `order` " +
                        "ON orderHistory.orderId = `order`.id " +
                        "WHERE `order`.stockId = ?";
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
                        "INNER JOIN orderHistory " +
                        "ON orderHistory.id = trade.buyOrderId " +
                        "OR orderHistory.id = trade.sellOrderId " +
                        "WHERE orderHistory.orderId = ?";
        List<Trade> trades = jdbc.query(GET_TRADES, new TradeMapper(), orderId);

        for(Trade trade : trades) {
            assignBuyAndSellOrders(trade);
        }
        return trades;
    }

    @Override
    public void addTrade(Trade trade) {
        final String GET_HISTORY_ID =
                "SELECT orderHistory.id FROM orderHistory " +
                        "INNER JOIN `order` " +
                        "ON `order`.id = orderId " +
                        "AND `order`.version = orderHistory.version " +
                        "WHERE orderId = ?";
        int buyOrder = jdbc.queryForObject(GET_HISTORY_ID, Integer.class, trade.getBuyOrder().getId());
        int sellOrder = jdbc.queryForObject(GET_HISTORY_ID, Integer.class, trade.getSellOrder().getId());

        final String ADD_TRADE =
                "INSERT INTO trade(buyOrderId, sellOrderId, quantity, price, timestamp) " +
                        "VALUES(?, ?, ?, ?, ?)";
        jdbc.update(ADD_TRADE, buyOrder, sellOrder, trade.getQuantity(),
                trade.getPrice(), trade.getTimestamp());

        trade.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    }

    private Trade assignBuyAndSellOrders(Trade trade) {
        final String GET_BUY =
                "SELECT `order`.id, `order`.side, orderHistory.quantity, orderHistory.price, orderHistory.state, " +
                        "orderHistory.version, orderHistory.timestamp FROM orderHistory " +
                        "INNER JOIN `order` " +
                        "ON `order`.id = orderHistory.orderId " +
                        "INNER JOIN trade " +
                        "ON trade.buyOrderId = orderHistory.id " +
                        "WHERE trade.id = ?";
        Order buyOrder = jdbc.queryForObject(GET_BUY, new OrderDaoImpl.OrderMapper(), trade.getId());
        trade.setBuyOrder(assignStockAndParty(buyOrder));


        final String GET_SELL =
                "SELECT `order`.id, `order`.side, orderHistory.quantity, orderHistory.price, orderHistory.state, " +
                        "orderHistory.version, orderHistory.timestamp FROM orderHistory " +
                        "INNER JOIN `order` " +
                        "ON `order`.id = orderHistory.orderId " +
                        "INNER JOIN trade " +
                        "ON trade.sellOrderId = orderHistory.id " +
                        "WHERE trade.id = ?";
        Order sellOrder = jdbc.queryForObject(GET_SELL, new OrderDaoImpl.OrderMapper(), trade.getId());
        trade.setSellOrder(assignStockAndParty(sellOrder));

        return trade;
    }

    private Order assignStockAndParty(Order order) {
        final String GET_STOCK =
                "SELECT * FROM stock " +
                        "INNER JOIN `order` " +
                        "ON stock.id = `order`.stockId " +
                        "WHERE `order`.id = ?";
        Stock stock =  jdbc.queryForObject(GET_STOCK, new StockDaoImpl.StockMapper(), order.getId());

        final String GET_STOCK_EXCHANGE =
                "SELECT * FROM stockExchange " +
                        "INNER JOIN stock ON stock.stockExchangeId = stockExchange.id " +
                        "WHERE stock.id = ?";
        stock.setStockExchange(jdbc.queryForObject(GET_STOCK_EXCHANGE,
                new StockExchangeDaoImpl.StockExchangeMapper(), stock.getId()));

        order.setStock(stock);

        final String GET_PARTY =
                "SELECT * FROM party " +
                        "INNER JOIN `order` " +
                        "ON `order`.partyId = party.id " +
                        "WHERE `order`.id = ?";
        order.setParty(jdbc.queryForObject(GET_PARTY, new PartyDaoImpl.PartyMapper(), order.getId()));
        return order;
    }

    public static final class TradeMapper implements RowMapper<Trade> {
        @Override
        public Trade mapRow(ResultSet rs, int index) throws SQLException {
            Trade trade = new Trade();
            trade.setId(rs.getInt("id"));
            trade.setQuantity(rs.getInt("quantity"));
            trade.setPrice(rs.getBigDecimal("price"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            trade.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            return trade;
        }
    }
}
