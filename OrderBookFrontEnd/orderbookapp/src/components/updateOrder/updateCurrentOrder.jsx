import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";

class UpdatingCurrentOrder extends Component {
  state = { redirect: null };

  submittedOrderUpdate = () => {
    this.props.handleSubmit();
    this.setState({ redirect: "/manageOrders" });
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    let { order, handleChange } = this.props;
    return (
      <Form
        className="container border"
        onSubmit={() => this.submittedOrderUpdate()}
      >
        <Form.Group controlId="orderParty">
          <Form.Label>Party</Form.Label>
          <output type="text" className="form-control" id="orderParty">
            {order.party.symbol}, ({order.party.name})
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
            {order.stock.symbol}, ({order.stock.name})
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
