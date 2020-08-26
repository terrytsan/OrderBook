import React, { Component } from "react";

class OrderBreakDown extends Component {
  state = {};
  render() {
    const { order } = this.props;
    return (
      <div className="container row m-2">
        <h3>Order ID: {order.id}</h3>
        <div className="container row border">
          <div className="col">
            <p>Order Side: {order.side}</p>
            <p>Current Version: {order.version}</p>
            <p>Original TimeStamp: {order.time} </p>
          </div>
          <div className="col">
            <p>Original Quantity: {order.quantity}</p>
            <p>Current Price: {order.price}</p>
            <p>Current State: {order.state}</p>
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
