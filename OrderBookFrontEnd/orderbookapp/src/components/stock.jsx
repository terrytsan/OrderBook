import React, { Component } from "react";
import { Form } from "react-bootstrap";

class Stock extends Component {
  render() {
    const { selectedStock, stocks } = this.props;
    return (
      <div className="col">
        <h2>{selectedStock.symbol}</h2>
        <Form className="row">
          <select id="chosenStock" class="form-control form-control-sm">
            {stocks.map((stock) => (
              <option value={stock} selected={stock.id == selectedStock.id}>
                {stock.symbol}
              </option>
            ))}
          </select>
          <button
            className="uk-button uk-button-mini"
            onClick={() => this.props.selectingStock(this.chosenStock.value)}
          >
            Get Stock
          </button>
        </Form>
      </div>
    );
  }

  handleChange(event) {
    this.setState({ value: event.target.value });
  }

  handleSubmit(event) {
    alert("Your favorite flavor is: " + this.state.value);
    event.preventDefault();
  }
}

export default Stock;
