import React, { Component } from "react";
import Trade from "./trade";

class AllTrades extends Component {
  state = {};
  render() {
    let { trades, stockExchange } = this.props;
    return (
      <React.Fragment>
        <div className="row">
          <div className="id header col">Trade ID</div>
          <div className="soldPrice header col">Price</div>
          <div className="soldAmount header col">Quantity</div>
          <div className="CCP header col">Counter Party</div>
          <div className="stock header col">Stock Symbol</div>
          <div className="timestamp header col">Timestamp</div>
        </div>
        {trades.map((trade) => (
          <Trade key={trade.id} trade={trade} stockExchange={stockExchange} />
        ))}
      </React.Fragment>
    );
  }
}

export default AllTrades;
