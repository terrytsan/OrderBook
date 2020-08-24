import React, { Component } from "react";

import Order from "./order.jsx";

class Orders extends Component {
  state = {
    orders: [
      { id: 1, time: "5:00:34", quantity: 200, price: "5.25", type: "BUY" },
      { id: 2, time: "5:00:50", quantity: 150, price: "10.25", type: "SELL" },
    ],
  };
  render() {
    return (
      <div>
        <div className="row">
          <div className="time col">Time</div>
          <div className="shares col">Shares</div>
          <div className="price col">Bid</div>
        </div>
        {this.state.orders.map((order) => (
          <Order key={this.state.orders.indexOf(order)} order={order} />
        ))}
      </div>
    );
  }
}

export default Orders;
