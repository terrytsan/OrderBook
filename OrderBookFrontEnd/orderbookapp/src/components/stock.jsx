import React, { Component } from "react";
import { Form } from "react-bootstrap";

class Stock extends Component {
  render() {
    const { selectedStock, stocks } = this.props;
    return (
      <React.Fragment>
        <div className="col">
          <h2>{selectedStock.symbol}</h2>
          <Form className="row">
            <select
              id="chosenStock"
              defaultValue={selectedStock}
              className="form-control form-control-sm"
            >
              {stocks.map((stock) => (
                <option value={stock} key={stock.id}>
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
        <div className="col">
          <h2>Stock Details</h2>
        </div>
      </React.Fragment>
    );
  }
}

export default Stock;
