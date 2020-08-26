package com.mthree.orderbook.dao.party;

import com.mthree.orderbook.entity.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PartyDaoImpl implements PartyDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Party getPartyById(int id) {
        final String GET = "SELECT * FROM party WHERE id = ?";
        return jdbc.queryForObject(GET, new PartyMapper(), id);
    }

    @Override
    public List<Party> getAll() {
        final String GET_ALL = "SELECT * FROM party";
        return jdbc.query(GET_ALL, new PartyMapper());
    }

    @Override
    public void addParty(Party party) {
        final String ADD_PARTY = "INSERT INTO party(symbol, name) VALUES(?, ?)";
        jdbc.update(ADD_PARTY, party.getSymbol(), party.getName());
        party.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    }

    public static final class PartyMapper implements RowMapper<Party> {
        @Override
        public Party mapRow(ResultSet rs, int index) throws SQLException {
            Party party = new Party();
            party.setId(rs.getInt("id"));
            party.setSymbol(rs.getString("symbol"));
            party.setName(rs.getString("name"));
            return party;
        }
    }
}
