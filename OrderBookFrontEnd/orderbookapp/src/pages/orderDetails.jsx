import React, { Component } from "react";

import OrderHistoryTable from "../components/orderDetails/orderHistoryTable";
import OrderBreakDown from "../components/orderDetails/orderBreakDown";

class OrderDetails extends Component {
  state = {};
  render() {
    const { order, orderRecords, trades } = this.props;
    return (
      <div className="container">
        <h1 className="m-2">Order Details</h1>
        <OrderBreakDown order={order} />
        <OrderHistoryTable
          order={order}
          orderRecords={orderRecords}
          allOrderTrades={trades}
        />
      </div>
    );
  }
}

export default OrderDetails;
