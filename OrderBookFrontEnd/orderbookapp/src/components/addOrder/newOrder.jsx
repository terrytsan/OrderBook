import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";

class NewOrder extends Component {
  state = {};

  render() {
    let {
      counterParties,
      types,
      stocks,
      handleChange,
      newOrder,
      handleSubmit,
    } = this.props;
    return (
      <Form className="container border" onSubmit={this.handleSubmit}>
        <Form.Group controlId="orderParty">
          <Form.Label>Party</Form.Label>
          <Form.Control
            as="select"
            name="Party"
            onChange={handleChange}
            defaultValue={newOrder.partyId}
          >
            {counterParties.map((party) => (
              <option key={party.id} value={party.id}>
                {party.symbol}, ({party.name})
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="orderSide">
          <Form.Label>Type Of Order (Buy/Sell)</Form.Label>
          <Form.Control
            as="select"
            name="Side"
            onChange={handleChange}
            defaultValue={newOrder.side}
          >
            {types.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="orderStock">
          <Form.Label>Stock</Form.Label>
          <Form.Control
            as="select"
            name="Stock"
            onChange={handleChange}
            defaultValue={newOrder.stockId}
          >
            {stocks.map((stock) => (
              <option key={stock.id} value={stock.id}>
                {stock.symbol}, ({stock.name})
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="orderQuantity">
          <Form.Label>Quantity</Form.Label>
          <Form.Control
            type="number"
            name="Quantity"
            placeholder="Enter number of shares"
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="orderPrice">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            name="Price"
            placeholder="Enter number of shares"
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

export default NewOrder;
