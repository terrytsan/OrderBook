package com.mthree.orderbook.dao.order;

import com.mthree.orderbook.dao.party.PartyDaoImpl;
import com.mthree.orderbook.dao.stock.StockDaoImpl;
import com.mthree.orderbook.entity.Order;
import com.mthree.orderbook.entity.Side;
import com.mthree.orderbook.entity.State;
import com.mthree.orderbook.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Order getOrderById(int id) {
        final String GET = "SELECT * FROM order WHERE id = ?";
        return assignStockAndParty(jdbc.queryForObject(GET, new OrderMapper(), id));
    }

    @Override
    public List<Order> getLiveOrders(int stockId) {
        final String GET_ALL =
                "SELECT * FROM order " +
                        "WHERE state = 'LIVE' AND stockId = ?";
        List<Order> orders = jdbc.query(GET_ALL, new OrderMapper(), stockId);

        for(Order order: orders) {
            assignStockAndParty(order);
        }
        return orders;
    }

    @Override
    public List<Order> getLiveBuyOrders(int stockId) {
        final String GET_ALL =
                "SELECT * FROM order " +
                        "WHERE state = 'LIVE' AND side = 'BUY' AND stockId = ?";
        List<Order> orders = jdbc.query(GET_ALL, new OrderMapper(), stockId);

        for(Order order: orders) {
            assignStockAndParty(order);
        }
        return orders;    }

    @Override
    public List<Order> getLiveSellOrders(int stockId) {
        final String GET_ALL =
                "SELECT * FROM order " +
                        "WHERE state = 'LIVE' AND side = 'SELL' AND stockId = ?";
        List<Order> orders = jdbc.query(GET_ALL, new OrderMapper(), stockId);

        for(Order order: orders) {
            assignStockAndParty(order);
        }
        return orders;    }

    @Override
    public List<Order> getOrdersForStock(int stockId) {
        final String GET_ALL =
                "SELECT * FROM order " +
                        "WHERE stockId = ?";
        List<Order> orders = jdbc.query(GET_ALL, new OrderMapper(), stockId);

        for(Order order: orders) {
            assignStockAndParty(order);
        }
        return orders;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        final String UPDATE_ORDER =
                "UPDATE order SET" +
                        "quantity = ? " +
                        "price = ? " +
                        "state = ? " +
                        "version = ? " +
                        "timestamp = ? " +
                        "WHERE id = ?";

        jdbc.update(UPDATE_ORDER, order.getQuantity(), order.getPrice(), order.getState(), order.getVersion(),
                order.getTimestamp(), order.getId());

        final String ADD_HISTORY =
                "INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";

        jdbc.update(ADD_HISTORY, order.getId(), order.getQuantity(), order.getPrice(), order.getState(),
                order.getVersion(), order.getTimestamp());
    }

    @Transactional
    @Override
    public void addOrder(Order order) {
        final String ADD_ORDER =
                "INSERT INTO order(stockId, partyId, side, quantity, price, state, version, timestamp) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(ADD_ORDER, order.getStock().getId(), order.getParty().getId(), order.getSide(), order.getQuantity(),
                order.getPrice(), order.getState(), order.getVersion(), order.getTimestamp());

        order.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));

        final String ADD_HISTORY =
                "INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";

        jdbc.update(ADD_HISTORY, order.getId(), order.getQuantity(), order.getPrice(), order.getState(),
                order.getVersion(), order.getTimestamp());
    }

    @Override
    public List<Order> getOrderHistoryWithOrderId(int orderId) {
        final String GET_HISTORY =
                "SELECT orderHistory.id, order.side, orderHistory.quantity, orderHistory.price, orderHistory.state, " +
                        "orderHistory.version, orderHistory.timestamp FROM orderHistory " +
                        "INNER JOIN order " +
                        "ON order.id = orderHistory.orderId " +
                        "WHERE order.id = ?";
        List<Order> orders = jdbc.query(GET_HISTORY, new OrderMapper(), orderId);

        for(Order order : orders) {
            assignStockAndParty(order);
        }
        return orders;
    }

    private Order assignStockAndParty(Order order) {
        final String GET_STOCK =
                "SELECT * FROM stock " +
                        "INNER JOIN stock" +
                        "ON stock.id = order.stockId " +
                        "WHERE order.id = ?";
        order.setStock(jdbc.queryForObject(GET_STOCK, new StockDaoImpl.StockMapper(), order.getId()));

        final String GET_PARTY =
                "SELECT * FROM party " +
                        "INNER JOIN order" +
                        "ON order.partyId = party.id " +
                        "WHERE order.id = ?";
        order.setParty(jdbc.queryForObject(GET_PARTY, new PartyDaoImpl.PartyMapper(), order.getId()));
        return order;
    }

    public static final class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int index) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setSide(Side.valueOf(rs.getString("side")));
            order.setQuantity(rs.getInt("quantity"));
            order.setPrice(rs.getBigDecimal("price"));
            order.setState(State.valueOf(rs.getString("state")));
            order.setVersion(rs.getInt("version"));
            order.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
            return order;
        }
    }
}
