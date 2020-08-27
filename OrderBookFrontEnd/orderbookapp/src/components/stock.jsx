import React, { Component } from "react";

import Select from "./input/select.jsx";

class Stock extends Component {
  state = {
    Stocks: [],
    selectedStock: "",
  };

  getListOfStocks = (allStocks) => {
    console.log("getListOfStocks called");
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
    // Return a stock object representing the selected stock from the select
    this.props.selectingStock(chosenStock);
  };

  componentDidMount() {
    // this.getListOfStocks(this.props.stocks);
  }

  render() {
    const { selectedStock, stocks, trades } = this.props;
    console.log("The selectedStock is: " + selectedStock.id);
    let stocksList = [];
    stocks.map((stock) => stocksList.push(stock.symbol));
    return (
      <React.Fragment>
        <div className="col">
          <h2>{selectedStock.symbol}</h2>
          <Select
            title={"Stock:"}
            name={"chosenStock"}
            value={selectedStock.symbol}
            placeholder={"Select Stock..."}
            options={stocksList}
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
