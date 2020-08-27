import React, { Component } from "react";
import AllTrades from "./allTrades";

class TradeTable extends Component {
  state = {};
  render() {
    let { trades, stockExchange } = this.props;
    return (
      <div className="container-fluid border">
        <h2 className="m-2">Trade Table</h2>
        <div className="border m-4">
          <AllTrades trades={trades} stockExchange={stockExchange} />
        </div>
      </div>
    );
  }
}

export default TradeTable;
