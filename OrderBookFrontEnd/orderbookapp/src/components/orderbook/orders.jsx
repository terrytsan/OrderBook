import React, { Component } from "react";

import Order from "./order.jsx";

class Orders extends Component {
  state = {};

  render() {
    const { orders } = this.props;
    return (
      <div>
        <div className="row">
          <div className="time col">Time</div>
          <div className="shares col">Shares</div>
          <div className="price col">{this.props.type}</div>
        </div>
        {orders.map((order) => (
          <Order key={orders.indexOf(order)} order={order} />
        ))}
      </div>
    );
  }
}

export default Orders;
