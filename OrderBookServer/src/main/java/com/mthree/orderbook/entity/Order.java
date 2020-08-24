package com.mthree.orderbook.entity;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@SecondaryTable(name = "orderHistory",
		pkJoinColumns = {
				@PrimaryKeyJoinColumn(name = "orderId", referencedColumnName = "id"),
				@PrimaryKeyJoinColumn(name = "version", referencedColumnName = "version")})
public class Order {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

	@ManyToOne
	@JoinColumn(name = "stockId", nullable = false)
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "partyId", nullable = false)
	private Party party;

	@Column(nullable = false)
	private Side side;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(table = "orderHistory", nullable = false)
	private LocalDateTime timestamp;

	@Column(nullable = false)
	private State state;

	@Column(nullable = false)
	private int version;

	public Order() {
	}

	public Order(Stock stock, Party party, Side side, int quantity, BigDecimal price, LocalDateTime timestamp,
				 State state, int version) {
		this.stock = stock;
		this.party = party;
		this.side = side;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
		this.state = state;
		this.version = version;
	}

	public Order(int id, Stock stock, Party party, Side side, int quantity, BigDecimal price,
				 LocalDateTime timestamp, State state, int version) {
		this.id = id;
		this.stock = stock;
		this.party = party;
		this.side = side;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
		this.state = state;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return id == order.id &&
				quantity == order.quantity &&
				version == order.version &&
				Objects.equals(stock, order.stock) &&
				Objects.equals(party, order.party) &&
				side == order.side &&
				Objects.equals(price, order.price) &&
				Objects.equals(timestamp, order.timestamp) &&
				state == order.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, stock, party, side, quantity, price, timestamp, state, version);
	}
}
