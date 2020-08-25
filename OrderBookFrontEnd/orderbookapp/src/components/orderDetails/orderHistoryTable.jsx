import React, { Component } from "react";

import OrderHistory from "./orderHistory.jsx";

class OrderHistoryTable extends Component {
  state = {};
  render() {
    const { orders, counterParties } = this.props;
    return (
      <div className="container-fluid border m-2">
        <div className="row m-2">
          <div className="id col">ID</div>
          <div className="time col">Time</div>
          <div className="quantity col">Quantity</div>
          <div className="price col">Price</div>
          <div className="side col">Side</div>
        </div>
        {orders.map((order) => (
          <OrderHistory
            key={orders.indexOf(order)}
            order={order}
            counterParties={counterParties}
          />
        ))}
      </div>
    );
  }
}

export default OrderHistoryTable;
