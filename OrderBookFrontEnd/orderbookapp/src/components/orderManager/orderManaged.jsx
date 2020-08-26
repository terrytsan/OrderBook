import React, { Component } from "react";
import { Redirect } from "react-router-dom";

class ManageOrder extends Component {
  state = { redirect: null };
  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    const { id, time, price, quantity, side, party } = this.props.order;
    return (
      <div className="row m-2" id={id}>
        <div className="id col">{id}</div>
        <div className="time col">{time}</div>
        <div className="quantity col">{quantity}</div>
        <div className="price col">{price}</div>
        <div className="side col">{side}</div>
        <div className="partySymbol col">{party.symbol}</div>
        <div className="col">
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={() => this.prepareForOrderDetails(id)}
          >
            Details
          </button>
        </div>
        <div className="col">
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={() => this.prepareForOrderUpdate(id)}
          >
            Update Order
          </button>
        </div>
        <div className="col">
          <button className="btn btn-outline-danger btn-sm">
            Cancel Order
          </button>
        </div>
      </div>
    );
  }

  prepareForOrderUpdate = (orderID) => {
    this.props.retrieveOrderDetails(orderID);
    this.setState({ redirect: "/updateOrder" });
  };

  prepareForOrderDetails = (orderID) => {
    this.props.getAllOrderDetails(orderID);
    this.setState({ redirect: "/orderDetails" });
  };
}

export default ManageOrder;
