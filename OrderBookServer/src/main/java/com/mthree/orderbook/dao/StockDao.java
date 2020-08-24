package com.mthree.orderbook.dao;

import com.mthree.orderbook.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDao extends JpaRepository<Integer, Stock> {
}
