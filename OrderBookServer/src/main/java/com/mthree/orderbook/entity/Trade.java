package com.mthree.orderbook.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {
	private int id;
	private Order buyOrder;
	private Order sellOrder;
	private int quantity;
	private BigDecimal price;
	private LocalDateTime timestamp;
	
	public Trade() {
	}
	
	public Trade(Order buyOrder, Order sellOrder, int quantity, BigDecimal price, LocalDateTime timestamp) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
	}
	
	public Trade(int id, Order buyOrder, Order sellOrder, int quantity, BigDecimal price, LocalDateTime timestamp) {
		this.id = id;
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Order getBuyOrder() {
		return buyOrder;
	}
	
	public void setBuyOrder(Order buyOrder) {
		this.buyOrder = buyOrder;
	}
	
	public Order getSellOrder() {
		return sellOrder;
	}
	
	public void setSellOrder(Order sellOrder) {
		this.sellOrder = sellOrder;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Trade trade = (Trade) o;
		return id == trade.id &&
			       quantity == trade.quantity &&
			       Objects.equals(buyOrder, trade.buyOrder) &&
			       Objects.equals(sellOrder, trade.sellOrder) &&
			       Objects.equals(price, trade.price) &&
			       Objects.equals(timestamp, trade.timestamp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, buyOrder, sellOrder, quantity, price, timestamp);
	}
}
