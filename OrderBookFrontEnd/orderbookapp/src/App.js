import React, { Component } from "react";
import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import Navigation from "./components/navigation.jsx";
import OrderBook from "./pages/orderbook.jsx";
import HistoricalData from "./pages/historicalData.jsx";
import AddOrder from "./pages/addOrder.jsx";
import OrderManager from "./pages/orderManager.jsx";
import Help from "./pages/help.jsx";
import UpdateOrder from "./pages/updateOrder.jsx";
import OrderDetails from "./pages/orderDetails.jsx";

class App extends Component {
  state = {};
  render() {
    return (
      <BrowserRouter>
        <div>
          <Navigation />
          <Switch>
            <Route path="/" component={OrderBook} exact />
            <Route path="/historicalTrades" component={HistoricalData} />
            <Route path="/addOrder" component={AddOrder} />
            <Route path="/orderDetails" component={OrderDetails} />
            <Route path="/updateOrder" component={UpdateOrder} />
            <Route path="/orderManager" component={OrderManager} />
            <Route path="/help" component={Help} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
