import React, { Component } from "react";

class Order extends Component {
  render() {
    const { id, timestamp, price, quantity } = this.props.order;
    return (
      <div className="row" id={id}>
        <div className="col">
          {this.checkSideColumn1(this.props.type, timestamp, "£" + price)}
        </div>
        <div className="shares col">{quantity}</div>
        <div className="price col">
          {this.checkSideColumn3(this.props.type, timestamp, "£" + price)}
        </div>
      </div>
    );
  }
  checkSideColumn1 = (type, time, price) => {
    if (type === "BID") {
      return time;
    } else {
      return price;
    }
  };

  checkSideColumn3 = (type, time, price) => {
    if (type === "BID") {
      return price;
    } else {
      return time;
    }
  };
}

export default Order;
