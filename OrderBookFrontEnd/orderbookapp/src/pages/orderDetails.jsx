import React, { Component } from "react";

import OrderHistoryTable from "../components/orderDetails/orderHistoryTable";
import OrderBreakDown from "../components/orderDetails/orderBreakDown";

class OrderDetails extends Component {
  state = {};
  render() {
    const { order, orderRecords } = this.props;
    return (
      <div className="container">
        <h1 className="m-2">Order Details</h1>
        <OrderBreakDown order={order} />
        <OrderHistoryTable orderRecords={orderRecords} />
      </div>
    );
  }
}

export default OrderDetails;
