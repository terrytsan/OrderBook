import React, { Component } from "react";

//Components
import Orders from "./../components/orderbook/orders.jsx";
import StockAndTrade from "./../components/stockAndTrade";

class OrderBook extends Component {
  state = {
    buyOrders: [],
    sellOrders: [],
  };

  splitorders = (orders) => {
    const buyOrders = orders.filter((order) => order.side === "BUY");
    const sellOrders = orders.filter((order) => order.side === "SELL");
    this.setState({ buyOrders });
    this.setState({ sellOrders });
  };

  componentDidMount() {
    this.splitorders(this.props.orders);
  }

  render() {
    const { selectedStock, stocks, selectingStock, trades } = this.props;
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
            <Orders orders={this.state.buyOrders} type="BID" />
          </div>
          <div className="col container border m-2">
            <Orders orders={this.state.sellOrders} type="ASK" />
          </div>
        </div>
      </div>
    );
  }
}

export default OrderBook;
