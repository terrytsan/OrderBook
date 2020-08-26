import React, { Component } from "react";

import OrderManaged from "./orderManaged.jsx";

class AllOrdersManaged extends Component {
  state = {};
  render() {
    const { orders, getAllOrderDetails } = this.props;
    return (
      <div className="container-fluid border m-2">
        <div className="row m-2">
          <div className="id col">ID</div>
          <div className="time col">Time</div>
          <div className="quantity col">Quantity</div>
          <div className="price col">Price</div>
          <div className="side col">Side</div>
          <div className="partySymbol col">PartyID</div>
          <div className="col"></div>
          <div className="col"></div>
          <div className="col"></div>
        </div>
        {orders.map((order) => (
          <OrderManaged
            key={orders.indexOf(order)}
            order={order}
            getAllOrderDetails={getAllOrderDetails}
          />
        ))}
      </div>
    );
  }
}

export default AllOrdersManaged;
