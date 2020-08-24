package com.mthree.orderbook.dao;

import com.mthree.orderbook.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Integer, Order> {
}
