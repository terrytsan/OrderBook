import React, { Component } from "react";

import Stock from "../components/stock.jsx";
import TradeGraph from "./historicTrades/tradeGraph.jsx";

class StockAndTrade extends Component {
  state = {};
  render() {
    const { selectedStock, stocks, selectingStock, trades } = this.props;
    return (
      <div className="row container-fluid border">
        <div className="col border m-2">
          <div className="row">
            <Stock
              selectedStock={selectedStock}
              stocks={stocks}
              selectingStock={selectingStock}
              trades={trades}
            />
          </div>
        </div>
        <div className="col m-2">
          <TradeGraph trades={trades} />
        </div>
      </div>
    );
  }
}

export default StockAndTrade;
