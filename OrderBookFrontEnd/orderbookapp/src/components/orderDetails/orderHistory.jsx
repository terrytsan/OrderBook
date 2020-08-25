import React, { Component } from "react";

class OrderHistory extends Component {
  state = {};
  render() {
    const { id, time, price, quantity, side, partyId } = this.props.order;
    const { counterParties } = this.props;
    return (
      <div className="row m-2" id={id}>
        <div className="id col">{id}</div>
        <div className="time col">{time}</div>
        <div className="quantity col">{quantity}</div>
        <div className="price col">{price}</div>
        <div className="side col">{side}</div>
      </div>
    );
  }
}

export default OrderHistory;
