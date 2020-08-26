DROP DATABASE IF EXISTS orderBookdb;

CREATE DATABASE orderBookdb;

USE orderBookdb;

CREATE TABLE stockExchange (
    id                  INT AUTO_INCREMENT,
    name                VARCHAR(50) NOT NULL,
    centralCounterParty VARCHAR(10) NOT NULL,
    CONSTRAINT pk_stockExchange PRIMARY KEY (id)
);

CREATE TABLE stock (
    id              INT AUTO_INCREMENT,
    stockExchangeId INT NOT NULL,
    name            VARCHAR(50) NOT NULL,
    symbol          VARCHAR(10) NOT NULL,
    maxQuantity     INT NOT NULL,
    tickSize        DECIMAL(5,2) NOT NULL,
    CONSTRAINT pk_stock PRIMARY KEY (id),
    CONSTRAINT fk_stock_stockExchange FOREIGN KEY (stockExchangeId) REFERENCES stockExchange (id)
);

CREATE TABLE party (
    id     INT AUTO_INCREMENT,
    symbol VARCHAR(10) NOT NULL,
    name   VARCHAR(50) NOT NULL,
    CONSTRAINT pk_party PRIMARY KEY (id)
);

CREATE TABLE `order` (
    id       INT AUTO_INCREMENT,
    stockId  INT NOT NULL,
    partyId  INT NOT NULL,
    side     ENUM ('BUY', 'SELL') NOT NULL,
    quantity INT NOT NULL,
    price    DECIMAL NOT NULL,
    state    ENUM ('CANCELLED', 'LIVE', 'COMPLETED') NOT NULL,
    version  INT NOT NULL,
    timestamp TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id),
    CONSTRAINT fk_order_stock FOREIGN KEY (stockId) REFERENCES stock (id),
    CONSTRAINT fk_order_party FOREIGN KEY (partyId) REFERENCES party (id)
);

CREATE TABLE orderHistory (
    id        INT AUTO_INCREMENT,
    orderId   INT NOT NULL,
    quantity  INT NOT NULL,
    price     DECIMAL NOT NULL,
    state     ENUM ('CANCELLED', 'LIVE', 'COMPLETED') NOT NULL,
    version   INT NOT NULL,
    timestamp TIMESTAMP(6) NOT NULL,
    CONSTRAINT pk_orderHistory PRIMARY KEY (id),
    CONSTRAINT fk_orderHistory_order FOREIGN KEY (orderId) REFERENCES `order` (id)
);

CREATE TABLE trade (
    id          INT AUTO_INCREMENT,
    buyOrderId  INT NOT NULL,
    sellOrderId INT NOT NULL,
    quantity    INT NOT NULL,
    price       DECIMAL NOT NULL,
    timestamp   TIMESTAMP NOT NULL,
    CONSTRAINT pk_trade PRIMARY KEY (id),
    CONSTRAINT fk_trade_buyOrder FOREIGN KEY (buyOrderId) REFERENCES orderHistory (id),
    CONSTRAINT fk_trade_sellOrder FOREIGN KEY (sellOrderId) REFERENCES orderHistory (id)
);