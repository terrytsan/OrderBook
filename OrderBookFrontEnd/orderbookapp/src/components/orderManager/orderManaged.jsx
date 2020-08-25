import React, { Component } from "react";
import { Link } from "react-router-dom";

class ManageOrder extends Component {
  state = {};
  render() {
    const { id, time, price, quantity, side, party } = this.props.order;
    const { counterParties } = this.props;
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
            onClick={() => {
              this.props.getAllOrderDetails(id);
            }}
          >
            Details
          </button>
        </div>
        <div className="col">
          <Link
            to={{
              pathname: "/updateOrder",
              aboutOrder: { order: this.props.order },
            }}
          >
            <button className="btn btn-outline-primary btn-sm">
              Update Order
            </button>
          </Link>
        </div>
        <div className="col">
          <button className="btn btn-outline-danger btn-sm">
            Cancel Order
          </button>
        </div>
      </div>
    );
  }
}

export default ManageOrder;
