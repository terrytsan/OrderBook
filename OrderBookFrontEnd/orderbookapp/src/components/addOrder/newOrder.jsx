import React, { Component } from "react";
import { Form } from "react-bootstrap";

class NewOrder extends Component {
  state = {};
  render() {
    const { counterParties, types, stocks } = this.props;
    return (
      <Form className="container border" onSubmit={this.handleFormSubmission}>
        <div class="form-group">
          <label for="orderParty">Party</label>
          <select class="form-control" id="orderParty">
            {counterParties.map((party) => (
              <option key={party.id} value={party}>
                {party.symbol}, ({party.name})
              </option>
            ))}
          </select>
        </div>
        <div class="form-group">
          <label for="typeOfOrder">Type Of Order (Buy/Sell)</label>
          <select class="form-control" id="typeOfOrder">
            {types.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        <div class="form-group" onChange={this.handleStockChange}>
          <label for="stockSelection">Stock</label>
          <select class="form-control" id="stockSelection">
            {stocks.map((stock) => (
              <option key={stock.id} value={stock.symbol}>
                {stock.symbol}, ({stock.name})
              </option>
            ))}
          </select>
        </div>
        <div class="form-group">
          <label for="quantity">Quantity</label>
          <input
            type="number"
            class="form-control"
            id="quantity"
            placeholder="Number of shares"
            required
          />
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input
            type="number"
            class="form-control"
            id="price"
            placeholder="Order Price"
            required
          />
        </div>
        <button type="reset" class="btn btn-outline-warning">
          Clear
        </button>
        <button type="submit" class="btn btn-outline-success">
          Submit
        </button>
      </Form>
    );
  }
}

export default NewOrder;
