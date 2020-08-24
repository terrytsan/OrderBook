package com.mthree.orderbook.dao;

import com.mthree.orderbook.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeDao extends JpaRepository<Integer, Trade> {
}
