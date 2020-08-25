package com.mthree.orderbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class OrderbookApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OrderbookApplication.class, args);
	}
	
	/**
	 * Default Clock object for normal operation
	 *
	 * @return Clock object
	 */
	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}
}
