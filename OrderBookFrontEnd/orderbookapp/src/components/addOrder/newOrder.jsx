import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";

class NewOrder extends Component {
  state = { redirect: null };

  submittedNewUpdate = () => {
    this.props.handleSubmit();
    this.setState({ redirect: "/" });
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    let { counterParties, types, stocks, handleChange, newOrder } = this.props;
    return (
      <Form
        className="container border"
        onSubmit={() => this.submittedNewUpdate()}
      >
        <Form.Group controlId="orderParty">
          <Form.Label>Party</Form.Label>
          <Form.Control
            as="select"
            name="partyId"
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
            name="side"
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
            name="stockId"
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
            name="quantity"
            placeholder="Enter number of shares"
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="orderPrice">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            name="price"
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
