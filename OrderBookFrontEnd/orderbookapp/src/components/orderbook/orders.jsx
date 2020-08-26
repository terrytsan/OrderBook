import React, { Component } from "react";

import Order from "./order.jsx";

class Orders extends Component {
  state = {};

  render() {
    const { orders, type } = this.props;
    return (
      <div>
        <div className="row">
          <div className="time col">{this.checkSideColumn1(type)}</div>
          <div className="shares col">SHARES</div>
          <div className="price col">{this.checkSideColumn3(type)}</div>
        </div>
        {orders.map((order) => (
          <Order key={orders.indexOf(order)} order={order} type={type} />
        ))}
      </div>
    );
  }

  checkSideColumn1 = (type) => {
    if (type === "BID") {
      return "TIME";
    } else {
      return type;
    }
  };

  checkSideColumn3 = (type) => {
    if (type === "BID") {
      return type;
    } else {
      return "TIME";
    }
  };
}

export default Orders;
