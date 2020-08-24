package com.mthree.orderbook.entity;

import java.util.Objects;

public class StockExchange {
	private int it;
	private String name;
	private String centralCounterParty;
	
	public StockExchange() {
	}
	
	public StockExchange(int it, String name, String centralCounterParty) {
		this.it = it;
		this.name = name;
		this.centralCounterParty = centralCounterParty;
	}
	
	public int getIt() {
		return it;
	}
	
	public void setIt(int it) {
		this.it = it;
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
		return it == that.it &&
			       Objects.equals(name, that.name) &&
			       Objects.equals(centralCounterParty, that.centralCounterParty);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(it, name, centralCounterParty);
	}
}
