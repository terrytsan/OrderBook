import React, { Component } from "react";

class OrderBreakDown extends Component {
  state = {};
  render() {
    const { order, latestOrder, firstOrder, totalQuantity} = this.props;
    return (
      <div className="container row m-2">
        <h3>Order ID: {order.id}</h3>
        <div className="container row border">
          <div className="col">
            <p>Order Side: {order.side}</p>
            <p>Current Version: {latestOrder.version}</p>
            <p>Original TimeStamp: {firstOrder.timestamp} </p>
          </div>
          <div className="col">
            <p>Total Quantity: {totalQuantity}</p>
            <p>Current Price: {"£" + latestOrder.price}</p>
            <p>Current State: {latestOrder.state}</p>
            <p>
              Customer: {order.party.name} ({order.party.symbol})
            </p>
          </div>
        </div>
      </div>
    );
  }
}

export default OrderBreakDown;
