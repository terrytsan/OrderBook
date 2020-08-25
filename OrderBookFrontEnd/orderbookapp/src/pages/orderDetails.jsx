import React, { Component } from "react";

import OrderHistoryTable from "../components/orderDetails/orderHistoryTable";

class OrderDetails extends Component {
  state = {};
  render() {
    const { orderRecord, counterParties } = this.props;
    return (
      <div>
        <h1>Order Details</h1>
        <OrderHistoryTable
          orders={orderRecord}
          counterParties={counterParties}
        />
      </div>
    );
  }
}

export default OrderDetails;
