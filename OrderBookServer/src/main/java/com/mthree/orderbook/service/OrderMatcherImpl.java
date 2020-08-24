package com.mthree.orderbook.service;

import com.mthree.orderbook.entity.Order;
import com.mthree.orderbook.entity.Side;
import com.mthree.orderbook.entity.Trade;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMatcherImpl implements OrderMatcher {
	@Override
	public Trade findMatch(Order recipientOrder, List<Order> opposingOrders) {
		if (recipientOrder.getSide() == Side.BUY) {
			// Get all the sell orders and sort from low to high, older to newer
			opposingOrders = opposingOrders.stream()
				                 .sorted(Comparator.comparing(Order::getPrice)
					                         .reversed()
					                         .thenComparing(Order::getTimestamp))
				                 .collect(Collectors.toList());
			for (Order sellOrder : opposingOrders) {
				if (sellOrder.getPrice().compareTo(recipientOrder.getPrice()) <= 0) {
					System.out.println("Match found for buy order.");
					// Create a new trade
					return new Trade(recipientOrder, sellOrder,
					                 Math.min(recipientOrder.getQuantity(), sellOrder.getQuantity()),
					                 sellOrder.getPrice(),
					                 LocalDateTime.now());
				}
			}
		} else if (recipientOrder.getSide() == Side.SELL) {
			// Get all the buy orders and sort from low to high, older to newer
			opposingOrders = opposingOrders.stream()
				                 .sorted(Comparator.comparing(Order::getPrice)
					                         .reversed()
					                         .thenComparing(Order::getTimestamp))
				                 .collect(Collectors.toList());
			for (Order buyOrder : opposingOrders) {
				if (buyOrder.getPrice().compareTo(recipientOrder.getPrice()) >= 0) {
					System.out.println("Match found for sell order.");
					// Create a new trade
					return new Trade(buyOrder, recipientOrder,
					                 Math.min(buyOrder.getQuantity(), recipientOrder.getQuantity()),
					                 buyOrder.getPrice(),
					                 LocalDateTime.now());
				}
			}
		}
		
		return null;
	}
}
