import React, { Component } from "react";

import OrderManaged from "./orderManaged.jsx";
import { Row, Col } from "react-bootstrap";

class AllOrdersManaged extends Component {
  state = {};
  render() {
    const {
      orders,
      getAllOrderDetails,
      retrieveOrderDetails,
      handleCancelOrderClick,
    } = this.props;
    return (
      <div className="container-fluid border m-2">
        <Row className="row m-2">
          <Col className="id header">ID</Col>
          <Col className="time header">Time</Col>
          <Col className="quantity header">Quantity</Col>
          <Col className="price header">Price</Col>
          <Col className="side header">Side</Col>
          <Col className="partySymbol header">PartyID</Col>
          <Col className="detailsButton header"></Col>
          <Col className="updateButton header"></Col>
          <Col className="cancelButton header"></Col>
        </Row>
        {orders.map((order) => (
          <OrderManaged
            key={orders.indexOf(order)}
            order={order}
            getAllOrderDetails={getAllOrderDetails}
            retrieveOrderDetails={retrieveOrderDetails}
            handleCancelOrderClick={handleCancelOrderClick}
          />
        ))}
      </div>
    );
  }
}

export default AllOrdersManaged;
