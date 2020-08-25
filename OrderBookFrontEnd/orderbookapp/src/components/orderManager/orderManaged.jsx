import React, { Component } from "react";
import { Nav } from "react-bootstrap";

class ManageOrder extends Component {
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
        <div className="partySymbol col">
          {this.getOrderParty(partyId, counterParties)}
        </div>
        <div className="col">
          <Nav.Link
            href="/orderDetails"
            onClick={() => this.props.getAllOrderDetails(id)}
          >
            <button className="btn btn-outline-primary btn-sm">Details</button>
          </Nav.Link>
        </div>
        <div className="col">
          <Nav.Link href="/updateOrder">
            <button
              className="btn btn-outline-primary btn-sm"
              href="/updateOrder"
            >
              Update Order
            </button>
          </Nav.Link>
        </div>
        <div className="col">
          <button className="btn btn-outline-danger btn-sm">
            Cancel Order
          </button>
        </div>
      </div>
    );
  }

  getOrderParty = (orderPartyID, counterParties) => {
    const orderParty = counterParties.find(
      (party) => party.id === orderPartyID
    );
    return orderParty.symbol;
  };
}

export default ManageOrder;
