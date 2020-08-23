DROP DATABASE IF EXISTS orderBookdb;

CREATE DATABASE orderBookdb;

USE orderBookdb;

CREATE TABLE stockExchange (
    id                  INT AUTO_INCREMENT,
    name                VARCHAR(50),
    centralCounterParty VARCHAR(10),
    CONSTRAINT pk_stockExchange PRIMARY KEY (id)
);

CREATE TABLE stock (
    id              INT AUTO_INCREMENT,
    stockExchangeId INT,
    symbol          VARCHAR(10),
    maxQuantity     INT,
    tickSize        DECIMAL,
    CONSTRAINT pk_stock PRIMARY KEY (id),
    CONSTRAINT fk_stock_stockExchange FOREIGN KEY (stockExchangeId) REFERENCES stockExchange (id)
);

CREATE TABLE party (
    id     INT AUTO_INCREMENT,
    symbol VARCHAR(10),
    name   VARCHAR(50),
    CONSTRAINT pk_party PRIMARY KEY (id)
);

CREATE TABLE `order` (
    id       INT AUTO_INCREMENT,
    stockId  INT,
    partyId  INT,
    side     ENUM ('BUY', 'SELL'),
    quantity INT,
    price    DECIMAL,
    state    ENUM ('CANCELLED', 'LIVE', 'COMPLETED'),
    version  INT,
    CONSTRAINT pk_order PRIMARY KEY (id),
    CONSTRAINT fk_order_stock FOREIGN KEY (stockId) REFERENCES stock (id),
    CONSTRAINT fk_order_party FOREIGN KEY (partyId) REFERENCES party (id)
);

CREATE TABLE orderHistory (
    id        INT AUTO_INCREMENT,
    orderId   INT,
    quantity  INT,
    price     DECIMAL,
    state     ENUM ('CANCELLED', 'LIVE', 'COMPLETED'),
    version   INT,
    timestamp TIMESTAMP,
    CONSTRAINT pk_orderHistory PRIMARY KEY (id),
    CONSTRAINT fk_orderHistory_order FOREIGN KEY (orderId) REFERENCES `order` (id)
);

CREATE TABLE trade (
    id          INT AUTO_INCREMENT,
    buyOrderId  INT,
    sellOrderId INT,
    quantity    INT,
    price       DECIMAL,
    timestamp   TIMESTAMP,
    CONSTRAINT pk_trade PRIMARY KEY (id),
    CONSTRAINT fk_trade_buyOrder FOREIGN KEY (buyOrderId) REFERENCES orderHistory (id),
    CONSTRAINT fk_trade_sellOrder FOREIGN KEY (sellOrderId) REFERENCES orderHistory (id)
);