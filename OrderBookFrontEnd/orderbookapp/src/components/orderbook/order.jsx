import React, { Component } from "react";

class Order extends Component {
  render() {
    const { id, time, price, quantity } = this.props.order;
    return (
      <div className="row" id={id}>
        <div className="time col">{time}</div>
        <div className="shares col">{quantity}</div>
        <div className="price col">{price}</div>
      </div>
    );
  }
}

export default Order;
