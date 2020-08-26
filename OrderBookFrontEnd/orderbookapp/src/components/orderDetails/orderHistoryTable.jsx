import React, { Component } from "react";

import OrderHistory from "./orderHistory.jsx";

class OrderHistoryTable extends Component {
  state = {};
  render() {
    const { order, orderRecords, allOrderTrades } = this.props;
    return (
      <div className="container border m-2">
        <div className="row m-2">
          <div className="versionNo col">Version Number</div>
          <div className="quantity col">Size</div>
          <div className="price col">{this.checkSide(order.side)}</div>
          <div className="tradePrice col">Sale Price</div>
          <div className="tradeQuantity col">Quantity Sold</div>
          <div className="tradeTime col">Time Of Trade</div>
          <div className="tradeParty col">CounterParty</div>
        </div>
        {orderRecords.map((orderRecord) => (
          <OrderHistory
            key={orderRecords.indexOf(orderRecord)}
            orderHistory={orderRecord}
            allOrderTrades={allOrderTrades}
            side={order.side}
          />
        ))}
      </div>
    );
  }

  checkSide = (side) => {
    if (side === "BUY") {
      return "BID";
    } else {
      return "ASK";
    }
  };
}

export default OrderHistoryTable;
