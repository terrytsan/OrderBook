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
      id: 1,
      stockExchangeId: 1,
      symbol: "GOOG.L",
      name: "Google (London)",
      maxQuantity: 100,
      tickSize: 1,
    },
    stocks: [
      {
        id: 1,
        stockExchangeId: 1,
        symbol: "GOOG.L",
        name: "Google (London)",
        maxQuantity: 100,
        tickSize: 1,
      },
      {
        id: 2,
        stockExchangeId: 1,
        symbol: "TSL",
        name: "Tesla",
        maxQuantity: 200,
        tickSize: 10,
      },
    ],
    orders: [
      {
        id: 1,
        time: "5:00:34",
        quantity: 200,
        price: "5.25",
        side: "BUY",
        partyId: 1,
        stockId: 1,
      },
      {
        id: 2,
        time: "5:00:50",
        quantity: 150,
        price: "10.25",
        side: "SELL",
        partyId: 2,
        stockId: 1,
      },
    ],
    currentOrderRecord: [
      {
        id: 1,
        time: "5:00:34",
        quantity: 200,
        price: "5.25",
        side: "BUY",
        partyId: 1,
        stockId: 1,
      },
    ],
    trades: [],
    counterParties: [
      { id: 1, symbol: "CP1", name: "Counter Party 1" },
      { id: 2, symbol: "CP2", name: "Counter Party 2" },
    ],
    types: ["BUY", "SELL"],
  };

  selectingStock = (stock) => {
    const selectedStock = stock;
    this.setState({ selectedStock });
  };

  getAllOrderDetails = (orderId) => {
    //INCLUDE FETCH FROM THE CONTROLLER
    //SET CURRENTORDERRECORD
    const currentOrderRecord = this.state.orders.find(
      (order) => order.id === orderId
    );
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
                  orders={this.state.orders}
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
            <Route
              path="/orderDetails"
              render={(props) => (
                <OrderDetails
                  {...props}
                  counterParties={this.state.counterParties}
                  orderRecord={this.state.currentOrderRecord}
                />
              )}
            />
            <Route
              path="/updateOrder"
              render={(props) => <UpdateOrder {...props} />}
            />
            <Route
              path="/manageOrders"
              render={(props) => (
                <OrderManager
                  {...props}
                  selectedStock={this.state.selectedStock}
                  stocks={this.state.stocks}
                  selectingStock={this.selectingStock}
                  orders={this.state.orders}
                  counterParties={this.state.counterParties}
                  getAllOrderDetails={this.getAllOrderDetails}
                />
              )}
            />
            <Route path="/help" component={Help} />
            <Route component={Error} />
          </Switch>
        </BrowserRouter>
        <TickerFeed />
      </React.Fragment>
    );
  }
}

export default App;
