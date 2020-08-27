USE orderBookdb;

INSERT INTO stockExchange(name, centralCounterParty) VALUES("London Stock Exchange", "LCH");

INSERT INTO stock(stockExchangeId, name, symbol, maxQuantity, tickSize) VALUES(1, "Google", "GOOGL", 100, 0.01);
INSERT INTO stock(stockExchangeId, name, symbol, maxQuantity, tickSize) VALUES(1, "Tesla", "TSLA", 100, 0.01);

INSERT INTO party(symbol, name) VALUES("P1", "Party 1");
INSERT INTO party(symbol, name) VALUES("P2", "Party 2");

insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (1, 2, 1, 'BUY', 20, 52.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (2, 2, 1, 'BUY', 30, 48.00, 'LIVE', 1, '2020-08-26 15:25:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (3, 1, 1, 'BUY', 15, 57.00, 'COMPLETED', 2, '2020-08-27 12:42:29.262000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (4, 1, 1, 'BUY', 60, 32.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (5, 1, 1, 'BUY', 65, 55.00, 'LIVE', 2, '2020-08-27 12:42:29.352000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (6, 1, 1, 'BUY', 250, 30.00, 'LIVE', 1, '2020-08-26 15:23:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (7, 1, 1, 'BUY', 32, 32.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (8, 1, 1, 'BUY', 500, 51.00, 'LIVE', 1, '2020-08-26 15:50:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (9, 1, 1, 'BUY', 250, 32.00, 'LIVE', 1, '2020-08-26 15:23:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (10, 2, 1, 'SELL', 27, 85.00, 'LIVE', 1, '2020-08-26 13:20:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (11, 2, 1, 'SELL', 80, 70.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (12, 1, 1, 'SELL', 23, 60.00, 'COMPLETED', 2, '2020-08-27 12:48:24.220000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (13, 1, 1, 'SELL', 50, 65.00, 'LIVE', 1, '2020-08-26 15:22:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (14, 1, 1, 'SELL', 150, 71.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (15, 1, 1, 'SELL', 8, 59.00, 'LIVE', 2, '2020-08-27 12:48:24.382000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (16, 1, 1, 'SELL', 500, 70.00, 'LIVE', 1, '2020-08-26 15:22:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (17, 1, 1, 'SELL', 232, 71.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (18, 1, 1, 'SELL', 122, 150.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (19, 1, 1, 'SELL', 35, 55.00, 'COMPLETED', 3, '2020-08-27 12:42:29.335000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (20, 1, 1, 'SELL', 40, 60.00, 'COMPLETED', 2, '2020-08-27 12:48:24.300000');
insert into orderBookdb.order (id, stockId, partyId, side, quantity, price, state, version, timestamp) values (21, 1, 1, 'BUY', 37, 60.00, 'COMPLETED', 4, '2020-08-27 12:48:24.369000');

insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (1, 1, 20, 52.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (2, 2, 30, 48.00, 'LIVE', 1, '2020-08-26 15:25:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (3, 3, 15, 57.00, 'LIVE', 1, '2020-08-26 15:30:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (4, 4, 60, 32.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (5, 5, 100, 55.00, 'LIVE', 1, '2020-08-26 15:50:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (6, 6, 250, 30.00, 'LIVE', 1, '2020-08-26 15:23:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (7, 7, 32, 32.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (8, 8, 500, 51.00, 'LIVE', 1, '2020-08-26 15:50:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (9, 9, 250, 32.00, 'LIVE', 1, '2020-08-26 15:23:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (10, 10, 27, 85.00, 'LIVE', 1, '2020-08-26 13:20:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (11, 11, 80, 70.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (12, 12, 23, 60.00, 'LIVE', 1, '2020-08-26 15:23:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (13, 13, 50, 65.00, 'LIVE', 1, '2020-08-26 15:22:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (14, 14, 150, 71.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (15, 15, 45, 59.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (16, 16, 500, 70.00, 'LIVE', 1, '2020-08-26 15:22:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (17, 17, 232, 71.00, 'LIVE', 1, '2020-08-26 15:10:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (18, 18, 122, 150.00, 'LIVE', 1, '2020-08-26 15:20:40.218000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (19, 19, 50, 55.00, 'LIVE', 1, '2020-08-27 12:42:29.133000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (20, 3, 15, 57.00, 'COMPLETED', 2, '2020-08-27 12:42:29.262000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (21, 19, 35, 55.00, 'LIVE', 2, '2020-08-27 12:42:29.279000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (22, 19, 35, 55.00, 'COMPLETED', 3, '2020-08-27 12:42:29.335000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (23, 5, 65, 55.00, 'LIVE', 2, '2020-08-27 12:42:29.352000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (24, 20, 40, 60.00, 'LIVE', 1, '2020-08-27 12:44:25.757000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (25, 21, 100, 60.00, 'LIVE', 1, '2020-08-27 12:48:24.144000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (26, 12, 23, 60.00, 'COMPLETED', 2, '2020-08-27 12:48:24.220000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (27, 21, 77, 60.00, 'LIVE', 2, '2020-08-27 12:48:24.241000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (28, 20, 40, 60.00, 'COMPLETED', 2, '2020-08-27 12:48:24.300000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (29, 21, 37, 60.00, 'LIVE', 3, '2020-08-27 12:48:24.315000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (30, 21, 37, 60.00, 'COMPLETED', 4, '2020-08-27 12:48:24.369000');
insert into orderBookdb.orderHistory (id, orderId, quantity, price, state, version, timestamp) values (31, 15, 8, 59.00, 'LIVE', 2, '2020-08-27 12:48:24.382000');

insert into orderBookdb.trade (id, buyOrderId, sellOrderId, quantity, price, timestamp) values (1, 3, 19, 15, 57.00, '2020-08-27 12:42:29.224000');
insert into orderBookdb.trade (id, buyOrderId, sellOrderId, quantity, price, timestamp) values (2, 5, 21, 35, 55.00, '2020-08-27 12:42:29.326000');
insert into orderBookdb.trade (id, buyOrderId, sellOrderId, quantity, price, timestamp) values (3, 25, 12, 23, 60.00, '2020-08-27 12:48:24.210000');
insert into orderBookdb.trade (id, buyOrderId, sellOrderId, quantity, price, timestamp) values (4, 27, 24, 40, 60.00, '2020-08-27 12:48:24.291000');
insert into orderBookdb.trade (id, buyOrderId, sellOrderId, quantity, price, timestamp) values (5, 29, 15, 37, 59.00, '2020-08-27 12:48:24.360000');