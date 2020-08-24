package com.mthree.orderbook.dao.order;

import com.mthree.orderbook.entity.Order;

import java.util.List;

public interface OrderDao {
    Order getOrderById(int id);
    List<Order> getLiveOrders(int stockId);
    List<Order> getLiveBuyOrders(int stockId);
    List<Order> getLiveSellOrders(int stockId);
    List<Order> getOrdersForStock(int stockId);
    void updateOrder(Order order);
    void addOrder(Order order);
    void getOrderHistoryWithOrderId(int orderId);
}