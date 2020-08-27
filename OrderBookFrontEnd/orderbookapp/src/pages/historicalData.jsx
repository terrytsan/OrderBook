import React, { Component } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { Nav } from "react-bootstrap";

import Stock from "./../components/stock.jsx";
import TradeTable from "./../components/historicTrades/tradeTable.jsx";
import TradeGraph from "./../components/historicTrades/tradeGraph.jsx";

class HistoricalData extends Component {
  render() {
    let {
      selectedStock,
      stocks,
      selectingStock,
      trades,
      stockExchange,
    } = this.props;
    return (
      <div className="container-fluid">
        <h1 className="m-2">Historical Data</h1>
        <div className="row m-2">
          <Stock
            selectedStock={selectedStock}
            stocks={stocks}
            selectingStock={selectingStock}
            trades={trades}
          />
        </div>
        <BrowserRouter>
          <Switch>
            <Route
              path="/pastTrades"
              render={(props) => (
                <TradeTable
                  trades={trades}
                  {...props}
                  stockExchange={stockExchange}
                />
              )}
              exact
            />
            <Route
              path="/pastTrades/graph"
              render={(props) => <TradeGraph trades={trades} {...props} />}
              exact
            />
          </Switch>
        </BrowserRouter>
        <div className="container row">
          <Nav.Link href="/pastTrades">
            <button className="btn btn-primary">Table</button>
          </Nav.Link>
          <Nav.Link href="/pastTrades/graph">
            <button className="btn btn-primary">Graph</button>
          </Nav.Link>
        </div>
      </div>
    );
  }
}

export default HistoricalData;
