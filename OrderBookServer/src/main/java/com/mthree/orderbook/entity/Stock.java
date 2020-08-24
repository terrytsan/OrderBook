package com.mthree.orderbook.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Stock {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

	@ManyToOne
	@JoinColumn(name = "stockExchangeId", nullable = false)
	private StockExchange stockExchange;

	@Column(nullable = false)
	private String symbol;

	@Column(nullable = false)
	private int maxQuantity;

	@Column(nullable = false)
	private BigDecimal tickSize;
	
	public Stock() {
	}
	
	public Stock(int id, StockExchange stockExchange, String symbol, int maxQuantity, BigDecimal tickSize) {
		this.id = id;
		this.stockExchange = stockExchange;
		this.symbol = symbol;
		this.maxQuantity = maxQuantity;
		this.tickSize = tickSize;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public StockExchange getStockExchange() {
		return stockExchange;
	}
	
	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public int getMaxQuantity() {
		return maxQuantity;
	}
	
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	
	public BigDecimal getTickSize() {
		return tickSize;
	}
	
	public void setTickSize(BigDecimal tickSize) {
		this.tickSize = tickSize;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Stock stock = (Stock) o;
		return id == stock.id &&
			       maxQuantity == stock.maxQuantity &&
			       Objects.equals(stockExchange, stock.stockExchange) &&
			       Objects.equals(symbol, stock.symbol) &&
			       Objects.equals(tickSize, stock.tickSize);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, stockExchange, symbol, maxQuantity, tickSize);
	}
}
