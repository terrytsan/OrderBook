import React, { Component } from "react";

//Components
import Orders from "./../components/orderbook/orders.jsx";
import StockAndTrade from "./../components/stockAndTrade";

class OrderBook extends Component {
  state = {};

  render() {
    const {
      selectedStock,
      stocks,
      selectingStock,
      trades,
      buyOrders,
      sellOrders,
    } = this.props;
    return (
      <div className="container-fluid">
        <h1>Order Book (Main)</h1>
        <div>
          <StockAndTrade
            selectedStock={selectedStock}
            stocks={stocks}
            selectingStock={selectingStock}
            trades={trades}
          />
        </div>
        <div className="AskBidTable row container-fluid border">
          <div className="col container border m-2">
            <Orders orders={buyOrders} type="BID" />
          </div>
          <div className="col container border m-2">
            <Orders orders={sellOrders} type="ASK" />
          </div>
        </div>
      </div>
    );
  }
}

export default OrderBook;
