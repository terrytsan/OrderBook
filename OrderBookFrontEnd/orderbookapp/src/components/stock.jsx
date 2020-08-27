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
    console.log(trades[trades.length - 1]);
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
          <h5 className="stockDetails">Stock Details</h5>
          <p>{"Stock Name: " + selectedStock.name}</p>
          <p></p>
        </div>
      </React.Fragment>
    );
  }
}

export default Stock;
