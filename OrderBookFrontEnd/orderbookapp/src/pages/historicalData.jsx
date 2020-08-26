import React, { Component } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { Nav } from "react-bootstrap";

import TradeTable from "./../components/historicTrades/tradeTable.jsx";
import TradeGraph from "./../components/historicTrades/tradeGraph.jsx";

class HistoricalData extends Component {
  render() {
    let { trades, stockExchange } = this.props;
    return (
      <div>
        <h1>Historical Data</h1>
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
