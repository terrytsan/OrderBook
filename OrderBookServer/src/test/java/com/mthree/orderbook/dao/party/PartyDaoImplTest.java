package com.mthree.orderbook.dao.party;

import com.mthree.orderbook.entity.Party;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PartyDaoImplTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PartyDao partyDao;

    @BeforeEach
    void setUp() {
        final String DELETE_TRADES = "DELETE FROM trade";
        jdbc.update(DELETE_TRADES);

        final String DELETE_ORDER_HISTORY = "DELETE FROM orderHistory";
        jdbc.update(DELETE_ORDER_HISTORY);

        final String DELETE_ORDERS = "DELETE FROM `order`";
        jdbc.update(DELETE_ORDERS);

        final String DELETE_PARTIES = "DELETE FROM party";
        jdbc.update(DELETE_PARTIES);

        final String DELETE_STOCKS = "DELETE FROM stock";
        jdbc.update(DELETE_STOCKS);

        final String DELETE_STOCK_EXCHANGES = "DELETE FROM stockExchange";
        jdbc.update(DELETE_STOCK_EXCHANGES);
    }

    @Test
    public void addGetParty() {
        Party party = new Party();
        party.setName("TEST PARTY");
        party.setSymbol("TEST");
        partyDao.addParty(party);

        assertEquals(party, partyDao.getPartyById(party.getId()));
    }

    @Test
    public void addGetAll() {
        Party party = new Party();
        party.setName("TEST PARTY");
        party.setSymbol("TEST");
        partyDao.addParty(party);

        Party party2 = new Party();
        party2.setName("TEST PARTY 2");
        party2.setSymbol("TEST 2");
        partyDao.addParty(party2);

        List<Party> parties = partyDao.getAll();

        assertEquals(party, parties.get(0));
        assertEquals(party2, parties.get(1));
    }

    @Test
    public void getAllEmpty() {
        List<Party> parties = new ArrayList<>();

        assertEquals(parties, partyDao.getAll());
    }
}