import React, { Component } from "react";
import Trade from "./trade";

class AllTrades extends Component {
  state = {};
  render() {
    let { trades, stockExchange } = this.props;
    return (
      <React.Fragment>
        <div className="row">
          <div className="id col">Trade ID</div>
          <div className="soldPrice col">Price</div>
          <div className="soldAmount col">Quantity</div>
          <div className="CCP col">Counter Party</div>
          <div className="stock col">Stock Symbol</div>
          <div className="timestamp col">Timestamp</div>
        </div>
        {trades.map((trade) => (
          <Trade key={trade.id} trade={trade} stockExchange={stockExchange} />
        ))}
      </React.Fragment>
    );
  }
}

export default AllTrades;
