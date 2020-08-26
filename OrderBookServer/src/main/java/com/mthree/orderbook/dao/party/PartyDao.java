package com.mthree.orderbook.dao.party;

import com.mthree.orderbook.entity.Party;

import java.util.List;

public interface PartyDao {

    /**
     * Returns the Party with the ID provided.
     * @param id ID of the party to search for.
     * @return Party object with ID provided.
     */
    Party getPartyById(int id);

    /**
     * Returns a List of all Parties stored on the system.
     * @return List of Party objects.
     */
    List<Party> getAll();

    /**
     * Adds a party to the database.
     * @param party Party to be added to the database.
     */
    void addParty(Party party);
}
