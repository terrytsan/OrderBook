package com.mthree.orderbook.controller;

import com.mthree.orderbook.entity.*;
import com.mthree.orderbook.service.DataFetchService;
import com.mthree.orderbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class OrderBookController {
	
	@Autowired
	DataFetchService dataFetchService;
	
	@Autowired
	OrderService orderService;
	
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
		return new ResponseEntity<>(dataFetchService.getStockExchanges(), HttpStatus.OK);
	}
	
	/**
	 * Returns a list of stocks related to a particular stock exchange.
	 *
	 * @return list of Stock objects
	 */
	@GetMapping("/stocks")
	public ResponseEntity<List<Stock>> getStocks(@RequestParam int stockExchangeId) {
//		return new ResponseEntity<>(dataFetchService.getStocks(stockExchangeId), HttpStatus.OK);
		
		StockExchange testExchange = new StockExchange(1, "XLONG", "LCH");
		Party testParty = new Party(1, "CP1", "Customer 1");
		Stock testStock = new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01"));
		
		List<Stock> stocks = new ArrayList<Stock>() {{
			add(new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01")));
			add(new Stock(1, testExchange, "GOOGL", 1000, new BigDecimal("0.01")));
			add(new Stock(1, testExchange, "AMZN", 1000, new BigDecimal("0.01")));
		}};
		return new ResponseEntity<>(stocks, HttpStatus.OK);
	}
	
	
	/**
	 * Returns a list of live orders for a particular stock.
	 *
	 * @param stockId int representing id of the stock
	 * @return a list of live Orders for the specified stock
	 */
	@GetMapping("/liveOrders")
	public ResponseEntity<List<Order>> getLiveOrders(@RequestParam int stockId) {
//		return new ResponseEntity<>(dataFetchService.getLiveOrders(stockId), HttpStatus.OK);
		
		
		StockExchange testExchange = new StockExchange(1, "XLONG", "LCH");
		Party testParty = new Party(1, "CP1", "Customer 1");
		Stock testStock = new Stock(1, testExchange, "TSLA", 1000, new BigDecimal("0.01"));
		
		List<Order> orders = new ArrayList<Order>() {{
			add(new Order(1, testStock, testParty, Side.BUY, 19, new BigDecimal("0.21"),
			              LocalDateTime.now().minusMinutes(10), State.LIVE, 1));
			add(new Order(2, testStock, testParty, Side.BUY, 41, new BigDecimal("0.19"),
			              LocalDateTime.now().minusMinutes(12), State.LIVE, 1));
			add(new Order(3, testStock, testParty, Side.BUY, 120, new BigDecimal("0.17"),
			              LocalDateTime.now().minusMinutes(5), State.LIVE, 1));
			add(new Order(4, testStock, testParty, Side.BUY, 120, new BigDecimal("0.17"), LocalDateTime.now(),
			              State.LIVE, 1));
			add(new Order(5, testStock, testParty, Side.SELL, 43, new BigDecimal("0.40"),
			              LocalDateTime.now().minusMinutes(13), State.LIVE, 1));
			add(new Order(6, testStock, testParty, Side.SELL, 21, new BigDecimal("0.36"),
			              LocalDateTime.now().minusMinutes(8), State.LIVE, 1));
			add(new Order(7, testStock, testParty, Side.SELL, 10, new BigDecimal("0.38"),
			              LocalDateTime.now().minusMinutes(4), State.LIVE, 1));
			add(new Order(8, testStock, testParty, Side.SELL, 10, new BigDecimal("0.41"), LocalDateTime.now(),
			              State.LIVE, 1));
		}};
		return new ResponseEntity<>(orders, HttpStatus.OK);
		
	}
	
	/**
	 * Returns the desired number of trades in the specified stock exchange.
	 *
	 * @param stockExchangeId int representing the id of the stock exchange
	 * @param number          int representing the desired number of trades to fetch
	 * @return a list consisting of {@code number} Trades
	 */
	@GetMapping("/trades")
	public ResponseEntity<List<Trade>> getTrades(@RequestParam int stockExchangeId, @RequestParam int number) {
		return new ResponseEntity<>(dataFetchService.getTrades(stockExchangeId, number), HttpStatus.OK);
	}
	
	/**
	 * Returns a list of past trades for a particular stock.
	 *
	 * @param stockId int representing the id of the stock
	 * @return a list of Trades for the specified stock
	 */
	@GetMapping("/tradesForStock")
	public ResponseEntity<List<Trade>> getTradesForStock(@RequestParam int stockId) {
		return new ResponseEntity<>(dataFetchService.getTradesForStock(stockId), HttpStatus.OK);
	}
	
	/**
	 * Returns a list of orders for a particular stock.
	 *
	 * @param stockId int representing the id of the stock
	 * @return a list of Orders for the specified stock
	 */
	@GetMapping("/ordersForStock")
	public ResponseEntity<List<Order>> getOrdersForStock(@RequestParam int stockId) {
		return new ResponseEntity<>(dataFetchService.getOrdersForStock(stockId), HttpStatus.OK);
	}
	
	/**
	 * Update the state of the specified order to cancelled.
	 *
	 * @param orderId int representing the id of the order to cancel
	 * @return a NO_CONTENT (204) code upon success.
	 */
	@PostMapping("/cancelOrder")
	public ResponseEntity<?> cancelOrder(@RequestParam int orderId) {
		orderService.cancelOrder(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Increases the price of the specified order by amount specified by order's Stock.
	 *
	 * @param orderId int representing order id of the order to tick up
	 * @return a NO_CONTENT (204) code upon success.
	 */
	@PostMapping("/tickUpOrder")
	public ResponseEntity<?> tickUpOrder(@RequestParam int orderId) {
		orderService.tickUpOrder(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Decreases the price of the specified order by amount specified by order's Stock.
	 *
	 * @param orderId int representing order id of the order to tick down
	 * @return a NO_CONTENT (204) code upon success.
	 */
	@PostMapping("/tickDownOrder")
	public ResponseEntity<?> tickDownOrder(@RequestParam int orderId) {
		orderService.tickDownOrder(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Returns the order with the specified id.
	 *
	 * @param orderId int representing the id of the desired order
	 * @return an Order with the specified id
	 */
	@GetMapping("/getOrder")
	public ResponseEntity<Order> getOrderWithId(@RequestParam int orderId) {
		return new ResponseEntity<>(dataFetchService.getOrderWithId(orderId), HttpStatus.OK);
	}
	
	/**
	 * Returns the history of a particular order. List will contain orders with the same id, but different versions.
	 *
	 * @param orderId int representing the id of the order to retrieve history for
	 * @return a list of Orders representing the history of the specified order
	 */
	@GetMapping("/getOrderHistory")
	public ResponseEntity<List<Order>> getOrderHistoryWithOrderId(@RequestParam int orderId) {
		return new ResponseEntity<>(dataFetchService.getOrderHistoryWithOrderId(orderId), HttpStatus.OK);
	}
	
	/**
	 * Returns a list of trades involving a particular order (an order might be involved in multiple trades).
	 *
	 * @param orderId int representing the id of the order
	 * @return a list of Trades involving a particular order
	 */
	@GetMapping("/getTradesForOrder")
	public ResponseEntity<List<Trade>> getTradesWithOrderId(@RequestParam int orderId) {
		return new ResponseEntity<>(dataFetchService.getTradesWithOrderId(orderId), HttpStatus.OK);
	}
	
	/**
	 * Returns a list of all parties.
	 *
	 * @return list of Party
	 */
	@GetMapping("/parties")
	public ResponseEntity<List<Party>> getAllParties() {
		return new ResponseEntity<>(dataFetchService.getAllParties(), HttpStatus.OK);
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
	public ResponseEntity<?> addOrder(@RequestParam int partyId, @RequestParam String side, @RequestParam int stockId,
	                                  @RequestParam int quantity, @RequestParam BigDecimal price) {
		orderService.createOrder(stockId, partyId, side, quantity, price);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@RequestParam int orderId, @RequestParam int quantity,
	                                     @RequestParam BigDecimal price) {
		orderService.updateOrder(orderId, quantity, price);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
