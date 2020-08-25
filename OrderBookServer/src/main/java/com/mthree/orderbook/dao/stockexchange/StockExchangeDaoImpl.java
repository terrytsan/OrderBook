package com.mthree.orderbook.dao.stockexchange;

import com.mthree.orderbook.entity.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StockExchangeDaoImpl implements StockExchangeDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public StockExchange getStockExchangeById(int id) {
        final String GET = "SELECT * FROM stockExchange WHERE id = ?";
        return jdbc.queryForObject(GET, new StockExchangeMapper(), id);
    }

    @Override
    public List<StockExchange> getAll() {
        final String GET_ALL = "SELECT * FROM stockExchange";
        return jdbc.query(GET_ALL, new StockExchangeMapper());
    }

    public static final class StockExchangeMapper implements RowMapper<StockExchange> {
        @Override
        public StockExchange mapRow(ResultSet rs, int index) throws SQLException {
            StockExchange stockExchange = new StockExchange();
            stockExchange.setId(rs.getInt("id"));
            stockExchange.setName(rs.getString("name"));
            stockExchange.setCentralCounterParty(rs.getString("centralCounterParty"));
            return stockExchange;
        }
    }
}
