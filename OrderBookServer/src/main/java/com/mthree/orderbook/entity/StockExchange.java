package com.mthree.orderbook.entity;

import java.util.Objects;

public class StockExchange {
	private int id;
	private String name;
	private String centralCounterParty;
	
	public StockExchange() {
	}
	
	public StockExchange(int id, String name, String centralCounterParty) {
		this.id = id;
		this.name = name;
		this.centralCounterParty = centralCounterParty;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCentralCounterParty() {
		return centralCounterParty;
	}
	
	public void setCentralCounterParty(String centralCounterParty) {
		this.centralCounterParty = centralCounterParty;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StockExchange that = (StockExchange) o;
		return id == that.id &&
			       Objects.equals(name, that.name) &&
			       Objects.equals(centralCounterParty, that.centralCounterParty);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name, centralCounterParty);
	}
}
