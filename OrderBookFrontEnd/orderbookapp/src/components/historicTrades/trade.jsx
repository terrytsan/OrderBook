import React, { Component } from "react";

class Trade extends Component {
  state = {};
  render() {
    const { trade, stockExchange } = this.props;
    return (
      <div className="row">
        <div className="id col">{trade.id}</div>
        <div className="soldPrice col">{"£" + trade.price}</div>
        <div className="soldAmount col">{trade.quantity}</div>
        <div className="CCP col">{stockExchange.centralCounterParty}</div>
        <div className="stock col">{trade.buyOrder.stock.symbol}</div>
        <div className="timestamp col">{trade.timestamp}</div>
      </div>
    );
  }
}

export default Trade;
