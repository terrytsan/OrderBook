import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";

class UpdatingCurrentOrder extends Component {
  state = {};

  getOrderParty = () => {
    const orderParty = this.props.order.party;
    const partyDisplay = orderParty.name + " (" + orderParty.symbol + ")";
    return partyDisplay;
  };

  getOrderStock = () => {
    const orderStock = this.props.order.stock;
    const stockDisplay = orderStock.name + " (" + orderStock.symbol + ")";
    return stockDisplay;
  };

  render() {
    let { order, handleChange, handleSubmit } = this.props;
    return (
      <Form className="container border" onSubmit={handleSubmit}>
        <Form.Group controlId="orderParty">
          <Form.Label>Party</Form.Label>
          <output type="text" className="form-control" id="orderParty">
            {this.getOrderParty()}
          </output>
        </Form.Group>
        <Form.Group controlId="orderSide">
          <Form.Label>Type Of Order (Buy/Sell)</Form.Label>
          <output type="text" className="form-control" id="orderParty">
            {order.side}
          </output>
        </Form.Group>
        <Form.Group controlId="orderStock">
          <Form.Label>Stock</Form.Label>
          <output type="text" className="form-control" id="orderParty">
            {this.getOrderStock()}
          </output>
        </Form.Group>
        <Form.Group controlId="orderQuantity">
          <Form.Label>Quantity</Form.Label>
          <Form.Control
            type="number"
            name="Quantity"
            placeholder={order.quantity}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="orderPrice">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            name="Price"
            placeholder={order.price}
            onChange={handleChange}
            required
            min="0"
            step=".01"
          />
        </Form.Group>
        <Button type="reset" className="m-2" variant="outline-warning">
          Clear
        </Button>
        <Button type="submit" className="m-2" variant="outline-success">
          Submit
        </Button>
      </Form>
    );
  }
}

export default UpdatingCurrentOrder;
