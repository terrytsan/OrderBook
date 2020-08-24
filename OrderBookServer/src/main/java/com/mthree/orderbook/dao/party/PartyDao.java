package com.mthree.orderbook.dao.party;

import com.mthree.orderbook.entity.Party;

import java.util.List;

public interface PartyDao {
    Party getPartyById(int id);
    List<Party> getAll();
}
