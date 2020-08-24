package com.mthree.orderbook.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Party {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

	@Column(nullable = false)
	private String symbol;

	@Column(nullable = false)
	private String name;
	
	public Party() {
	}
	
	public Party(int id, String symbol, String name) {
		this.id = id;
		this.symbol = symbol;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Party party = (Party) o;
		return id == party.id &&
			       Objects.equals(symbol, party.symbol) &&
			       Objects.equals(name, party.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, symbol, name);
	}
}
