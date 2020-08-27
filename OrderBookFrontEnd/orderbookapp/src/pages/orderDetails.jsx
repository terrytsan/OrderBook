import React, { Component } from "react";

import OrderHistoryTable from "../components/orderDetails/orderHistoryTable";
import OrderBreakDown from "../components/orderDetails/orderBreakDown";

class OrderDetails extends Component {
  state = {};
  render() {
    const { order, orderRecords, trades } = this.props;

    let latestOrder = orderRecords.reduce((max, order) =>
      max.version > order.version ? max : order
    );
    let firstOrder = orderRecords.reduce((max, order) =>
      max.version < order.version ? max : order
    );
    // Calculate the totalQuantity
    let totalQuantity = 0;
    for (let trade of trades) {
      totalQuantity += trade.quantity;
    }
    totalQuantity += latestOrder.quantity;

    return (
      <div className="container">
        <h1 className="m-2">Order Details</h1>
        <OrderBreakDown
          order={order}
          latestOrder={latestOrder}
          firstOrder={firstOrder}
          totalQuantity={totalQuantity}
        />
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
