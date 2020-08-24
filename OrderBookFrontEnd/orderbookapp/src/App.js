import React, { Component } from "react";
import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";

//Component Links
import Navigation from "./components/navigation.jsx";
import TickerFeed from "./components/tickerFeed.jsx";

//Page Links
import OrderBook from "./pages/orderbook.jsx";
import HistoricalData from "./pages/historicalData.jsx";
import AddOrder from "./pages/addOrder.jsx";
import OrderManager from "./pages/orderManager.jsx";
import Help from "./pages/help.jsx";
import UpdateOrder from "./pages/updateOrder.jsx";
import OrderDetails from "./pages/orderDetails.jsx";

class App extends Component {
  state = {
    selectedStock: {
      id: 2,
      stockExchangeId: 1,
      symbol: "TSL",
      maxQuantity: 200,
      tickSize: 10,
    },
    stocks: [
      {
        id: 1,
        stockExchangeId: 1,
        symbol: "GOOG.L",
        maxQuantity: 100,
        tickSize: 1,
      },
      {
        id: 2,
        stockExchangeId: 1,
        symbol: "TSL",
        maxQuantity: 200,
        tickSize: 10,
      },
    ],
    orders: [
      { id: 1, time: "5:00:34", quantity: 200, price: "5.25", type: "BUY" },
      { id: 2, time: "5:00:50", quantity: 150, price: "10.25", type: "SELL" },
    ],
    trades: [],
    counterParties: ["CP1", "CP2"],
    types: ["BUY", "SELL"],
  };

  selectingStock = (stock) => {
    const selectedStock = stock;
    this.setState({ selectedStock });
  };

  render() {
    return (
      <React.Fragment>
        <Navigation />
        <BrowserRouter>
          <Switch>
            <Route
              path="/"
              render={(props) => (
                <OrderBook
                  {...props}
                  selectedStock={this.state.selectedStock}
                  stocks={this.state.stocks}
                  selectingStock={this.selectingStock}
                  trades={this.state.trades}
                />
              )}
              exact
            />
            <Route path="/historicalTrades" component={HistoricalData} />
            <Route
              path="/addOrder"
              render={(props) => (
                <AddOrder
                  {...props}
                  counterParties={this.state.counterParties}
                  types={this.state.types}
                  stocks={this.state.stocks}
                  orders={this.state.orders}
                />
              )}
            />
            <Route path="/orderDetails" component={OrderDetails} />
            <Route path="/updateOrder" component={UpdateOrder} />
            <Route path="/manageOrders" component={OrderManager} />
            <Route path="/help" component={Help} />
          </Switch>
        </BrowserRouter>
        <TickerFeed />
      </React.Fragment>
    );
  }
}

export default App;
