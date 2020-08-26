USE orderBookdb;

INSERT INTO stockExchange(name, centralCounterParty) VALUES("London Stock Exchange", "LCH");

INSERT INTO stock(stockExchangeId, name, symbol, maxQuantity, tickSize) VALUES(1, "Google", "GOOGL", 100, 0.01);

INSERT INTO party(symbol, name) VALUES("Party 1", "P1");

INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 20, 52, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 30, 48, "LIVE", 1, "2020-08-26 15:25:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 15, 57, "LIVE", 1, "2020-08-26 15:30:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 60, 32, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 100, 55, "LIVE", 1, "2020-08-26 15:50:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 250, 30, "LIVE", 1, "2020-08-26 15:23:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 32, 32, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 500, 51, "LIVE", 1, "2020-08-26 15:50:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "BUY", 250, 32, "LIVE", 1, "2020-08-26 15:23:40.218000");

INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 27, 85, "LIVE", 1, "2020-08-26 13:20:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 80, 70, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 23, 60, "LIVE", 1, "2020-08-26 15:23:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 50, 65, "LIVE", 1, "2020-08-26 15:22:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 150, 71, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 45, 59, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 500, 70, "LIVE", 1, "2020-08-26 15:22:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 232, 71, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO `order`(stockId, partyId, side, quantity, price, state, version, timestamp) VALUES(1, 1, "SELL", 122, 150, "LIVE", 1, "2020-08-26 15:20:40.218000");

INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(1, 20, 52, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(2, 30, 48, "LIVE", 1, "2020-08-26 15:25:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(3, 15, 57, "LIVE", 1, "2020-08-26 15:30:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(4, 60, 32, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(5, 100, 55, "LIVE", 1, "2020-08-26 15:50:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(6, 250, 30, "LIVE", 1, "2020-08-26 15:23:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(7, 32, 32, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(8, 500, 51, "LIVE", 1, "2020-08-26 15:50:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(9, 250, 32, "LIVE", 1, "2020-08-26 15:23:40.218000");

INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(10, 27, 85, "LIVE", 1, "2020-08-26 13:20:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(11, 80, 70, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(12, 23, 60, "LIVE", 1, "2020-08-26 15:23:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(13, 50, 65, "LIVE", 1, "2020-08-26 15:22:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(14, 150, 71, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(15, 45, 59, "LIVE", 1, "2020-08-26 15:20:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(16, 500, 70, "LIVE", 1, "2020-08-26 15:22:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(17, 232, 71, "LIVE", 1, "2020-08-26 15:10:40.218000");
INSERT INTO orderHistory(orderId, quantity, price, state, version, timestamp) VALUES(18, 122, 150, "LIVE", 1, "2020-08-26 15:20:40.218000");



