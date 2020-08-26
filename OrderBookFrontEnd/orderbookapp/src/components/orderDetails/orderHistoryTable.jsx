import React, { Component } from "react";

import OrderHistory from "./orderHistory.jsx";

class OrderHistoryTable extends Component {
  state = {};
  render() {
    const { orderRecords } = this.props;
    return (
      <div className="container border m-2">
        <div className="row m-2">
          <div className="versionNo col">Version Number</div>
          <div className="size col">Size</div>
          <div className="quantity col">Bid</div>
          <div className="price col">Sale Price</div>
          <div className="price col">Quantity Sold</div>
          <div className="price col">Time Of Trade</div>
          <div className="price col">CounterParty</div>
        </div>
        {orderRecords.map((orderRecord) => (
          <OrderHistory
            key={orderRecords.indexOf(orderRecord)}
            order={orderRecord}
          />
        ))}
      </div>
    );
  }
}

export default OrderHistoryTable;
