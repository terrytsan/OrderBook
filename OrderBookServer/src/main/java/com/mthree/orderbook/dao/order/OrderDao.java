package com.mthree.orderbook.dao.order;

import com.mthree.orderbook.entity.Order;

import java.util.List;

public interface OrderDao {

    /**
     * Returns an order with the given ID.
     * @param id ID of the order to be returned.
     * @return Order with the ID provided.
     */
    Order getOrderById(int id);

    /**
     * Returns a list of live orders with the given stock id.
     * @param stockId id of the stock to search for orders of.
     * @return List of live orders of the stock with the id provided.
     */
    List<Order> getLiveOrders(int stockId);

    /**
     * Returns a list of live buy orders with the given stock id.
     * @param stockId id of the stock to search for live buy orders of.
     * @return List of live buy orders of the stock with the id provided.
     */
    List<Order> getLiveBuyOrders(int stockId);

    /**
     * Returns a list of live sell orders with the given stock id.
     * @param stockId id of the stock to search for live sell orders of.
     * @return List of live sell orders of the stock with the id provided.
     */
    List<Order> getLiveSellOrders(int stockId);

    /**
     * Returns a list of orders with the given stock id.
     * @param stockId id of the stock to search for orders of.
     * @return List of orders of the stock with the id provided.
     */
    List<Order> getOrdersForStock(int stockId);

    /**
     * Updates the order in the database with the Order object provided.
     * @param order Order that will be updated. Needs to have ID assigned.
     */
    void updateOrder(Order order);

    /**
     * Adds an order to the system.
     * @param order Order to be added to the system.
     */
    void addOrder(Order order);

    /**
     * Returns the full history of the order with the id provided.
     * @param orderId ID of order to search for.
     */
    void getOrderHistoryWithOrderId(int orderId);
}