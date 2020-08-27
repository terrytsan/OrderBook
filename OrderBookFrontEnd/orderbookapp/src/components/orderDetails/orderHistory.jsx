import React, { Component } from "react";

class OrderHistory extends Component {
  state = {
    orderTrade: {
      id: 1,
      buyOrder: {
        id: 1,
        stock: {
          id: 2,
          stockExchangeId: 1,
          symbol: "TSL",
          name: "Tesla",
          maxQuantity: 200,
          tickSize: 10,
        },
        party: { id: 1, symbol: "CP1", name: "CP1" },
        side: "SELL",
        quantity: 150,
        price: 71,
        timestamp: "2020-08-26T15:10:40.218",
        state: "LIVE",
        version: 1,
      },
      sellOrder: {
        id: 1,
        stock: {
          id: 2,
          stockExchangeId: 1,
          symbol: "TSL",
          name: "Tesla",
          maxQuantity: 200,
          tickSize: 10,
        },
        party: { id: 1, symbol: "CP1", name: "CP1" },
        side: "SELL",
        quantity: 150,
        price: 71,
        timestamp: "2020-08-26T15:10:40.218",
        state: "LIVE",
        version: 1,
      },
      quantity: 14,
      price: 71.0,
      timestamp: "2020-08-26T23:07:06.750099",
    },
  };

  componentWillMount = () => {
    // this.getTradePrice(this.props.orderHistory.id, this.props.side);
  };

  render() {

    // this.getTradePrice(this.props.orderHistory.id, this.props.side);
    const { orderHistory, side, orderTrade, orderTradeCounterParty} = this.props;
    return (
      <div className="row m-2">
        <div className="versionNo col">{orderHistory.version}</div>
        <div className="quantity col">{orderHistory.quantity}</div>
        <div className="price col">{"£" + orderHistory.price}</div>
        <div className="tradePrice col">{(orderTrade.price !== undefined) ? "£" + orderTrade.price : ""}</div>
        <div className="tradeQuantity col">
          {orderTrade.quantity}
        </div>
        <div className="tradeTime col">{orderTrade.timestamp}</div>
        {/*<div className="tradeParty col">{this.getCounterParty(side)}</div>*/}
        <div className="tradeParty col">{orderTradeCounterParty}</div>
      </div>
    );
  }
}

export default OrderHistory;
