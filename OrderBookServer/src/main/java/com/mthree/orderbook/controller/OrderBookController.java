package com.mthree.orderbook.controller;

import com.mthree.orderbook.entity.Stock;
import com.mthree.orderbook.entity.StockExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OrderBookController {
	
	/**
	 * Root page for Order book web service.
	 *
	 * @return welcome message
	 */
	@GetMapping("/")
	public ResponseEntity<String> getHome() {
		return new ResponseEntity<>("<h1>Welcome to the root of the Order Book Web Service</h1>", HttpStatus.OK);
	}
	
	/**
	 * Returns a list of all StockExchanges.
	 *
	 * @return list of StockExchange objects
	 */
	@GetMapping("/stockExchanges")
	public ResponseEntity<List<StockExchange>> getStockExchanges() {
		return null;
	}
	
	/**
	 * Returns a list of stocks related to a particular stock exchange.
	 *
	 * @return list of Stock objects
	 */
	@GetMapping("/stocks")
	public ResponseEntity<List<Stock>> getStocks(@RequestParam int stockExchangeId) {
		return null;
	}
	
	/**
	 * Add a new order. Returns 204 if successful (empty body). Returns 500 code if operation fails.
	 *
	 * @param partyId  int representing id of the party placing the order
	 * @param stockId  int representing id of the stock order is for
	 * @param quantity int representing the quantity for the order
	 * @param price    BigDecimal representing the price of the order
	 * @return ResponseEntity with the relevant response code and empty body
	 */
	@PostMapping("/addOrder")
	public ResponseEntity<?> addOrder(@RequestParam int partyId, @RequestParam int stockId,
	                                  @RequestParam int quantity, @RequestParam BigDecimal price) {
		return null;
	}
}
