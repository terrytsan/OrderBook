import React, { Component } from "react";

import Select from "./input/select.jsx";

class Stock extends Component {
  state = {
    Stocks: [],
    selectedStock: "",
  };

  getListOfStocks = (allStocks) => {
    let Stocks = [];
    allStocks.map((stock) => Stocks.push(stock.symbol));
    this.setState({ Stocks });
  };

  handleChange = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    console.log("Name: " + name);
    console.log("Value: " + value);
    const chosenStock = this.props.stocks.find(
      (stock) => stock.symbol === value
    );
    this.props.selectingStock(chosenStock);
  };

  componentDidMount() {
    this.getListOfStocks(this.props.stocks);
  }

  render() {
    const { selectedStock } = this.props;
    return (
      <React.Fragment>
        <div className="col">
          <h2>{selectedStock.symbol}</h2>
          <Select
            title={"Stock:"}
            name={"chosenStock"}
            value={selectedStock.id}
            placeholder={"Select Stock..."}
            options={this.state.Stocks}
            onChange={this.handleChange}
          />
        </div>
        <div className="col">
          <h2>Stock Details</h2>
        </div>
      </React.Fragment>
    );
  }
}

export default Stock;
