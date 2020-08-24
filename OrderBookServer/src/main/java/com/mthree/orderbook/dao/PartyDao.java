package com.mthree.orderbook.dao;

import com.mthree.orderbook.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyDao extends JpaRepository<Integer, Party> {
}
