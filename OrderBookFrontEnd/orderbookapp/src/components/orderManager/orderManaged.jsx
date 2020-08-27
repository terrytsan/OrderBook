import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { Row, Col } from "react-bootstrap";

class ManageOrder extends Component {
  state = { redirect: null };
  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    const { handleCancelOrderClick } = this.props;
    const {
      id,
      timestamp,
      price,
      quantity,
      side,
      party,
      state,
    } = this.props.order;
    let orderDetails = (
      <button
        className="btn btn-outline-primary btn-sm"
        onClick={() => this.prepareForOrderDetails(id)}
      >
        Details
      </button>
    );
    let updateDetails = (
      <button
        className="btn btn-outline-primary btn-sm"
        onClick={() => this.prepareForOrderUpdate(id)}
      >
        Update Order
      </button>
    );
    let cancelOrder = (
      <button
        className="btn btn-outline-danger btn-sm"
        onClick={() => handleCancelOrderClick(id)}
      >
        Cancel Order
      </button>
    );
    if (state == "COMPLETED") {
      cancelOrder = " ";
      updateDetails = " ";
      orderDetails = " ";
    }
    return (
      <Row className="m-2" id={id}>
        <Col className="id">{id}</Col>
        <Col className="time">{timestamp}</Col>
        <Col className="quantity">{quantity}</Col>
        <Col className="price">{"£" + price}</Col>
        <Col className="side">{side}</Col>
        <Col className="partySymbol">{party.symbol}</Col>
        <Col className="orderDetailsButton">{orderDetails}</Col>
        <Col className="orderUpdateButton">{updateDetails}</Col>
        <Col className="orderCancelButton">{cancelOrder}</Col>
      </Row>
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
