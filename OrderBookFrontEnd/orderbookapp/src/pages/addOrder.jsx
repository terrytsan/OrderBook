import React, { Component } from "react";
import { Form } from "react-bootstrap";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";

class AddOrder extends Component {
  state = {
    tempStock: "",
  };

  handleStockChange = (event) => {
    this.setState({ tempStock: event.target.value });
  };

  handleFormSubmission = () => {
    confirmAlert({
      customUI: ({ onClose }) => {
        return (
          <div className="custom-ui">
            <h1>Are you sure?</h1>
            <p>You want to delete this file?</p>
            <button onClick={onClose}>No</button>
            <button
              onClick={() => {
                this.handleClickDelete();
                onClose();
              }}
            >
              Yes, Delete it!
            </button>
          </div>
        );
      },
    });
  };

  render() {
    const { counterParties, types, stocks } = this.props;
    return (
      <div className="container-fluid">
        <h1>New Order</h1>
        <Form className="container border" onSubmit={this.handleFormSubmission}>
          <div class="form-group">
            <label for="orderParty">Party</label>
            <select class="form-control" id="orderParty">
              {counterParties.map((party) => (
                <option value={party}>{party}</option>
              ))}
            </select>
          </div>
          <div class="form-group">
            <label for="typeOfOrder">Type Of Order (Buy/Sell)</label>
            <select class="form-control" id="typeOfOrder">
              {types.map((type) => (
                <option value={type}>{type}</option>
              ))}
            </select>
          </div>
          <div class="form-group" onChange={this.handleStockChange}>
            <label for="stockSelection">Stock</label>
            <select class="form-control" id="stockSelection">
              {stocks.map((stock) => (
                <option value={stock.symbol}>{stock.symbol}</option>
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
              max={this.state.tempStock.maxQuantity}
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
          <button type="submit" class="btn btn-primary">
            Submit
          </button>
        </Form>
      </div>
    );
  }
}
/*
Need to include the max cap on the number of shares per order extracted from the form above
*/

export default AddOrder;
