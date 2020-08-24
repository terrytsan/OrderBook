import React, { Component } from "react";

//Components
import Orders from "./../components/orderbook/orders.jsx";
import StockAndTrade from "./../components/stockAndTrade";

class OrderBook extends Component {
  state = {
    buyOrders: [],
    sellOrders: [],
  };

  //Need to figure out way to split orders based on types
  organiseOrders = (orders) => {
    let buyOrders = [];
    let sellOrders = [];
  };

  render() {
    const { selectedStock, stocks, selectingStock, trades } = this.props;
    this.organiseOrders(this.props.orders);
    return (
      <div>
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
            <Orders />
          </div>
          <div className="col container border m-2">
            <Orders />
          </div>
        </div>
      </div>
    );
  }
}

export default OrderBook;
