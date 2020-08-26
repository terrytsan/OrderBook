import React, { Component } from "react";
import "./App.css";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

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

const SERVICE_URL =
  "http://startpage-env.eba-2npqnjuc.eu-west-2.elasticbeanstalk.com/";

let stockExchange = {
  id: 1,
  name: "LSE",
  centralCounterParty: "LCH",
};

class App extends Component {
  state = {
    selectedStock: {
      id: "",
      stockExchangeId: "",
      symbol: "",
      name: "",
      maxQuantity: "",
      tickSize: "",
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
        party: { id: 1, symbol: "CP1", name: "Counter Party 1" },
        stock: {
          id: 1,
          stockExchangeId: 1,
          symbol: "GOOG.L",
          name: "Google (London)",
          maxQuantity: 100,
          tickSize: 1,
        },
      },
      {
        id: 2,
        time: "5:00:50",
        quantity: 150,
        price: "10.25",
        side: "SELL",
        party: { id: 2, symbol: "CP2", name: "Counter Party 2" },
        stock: {
          id: 1,
          stockExchangeId: 1,
          symbol: "GOOG.L",
          name: "Google (London)",
          maxQuantity: 100,
          tickSize: 1,
        },
      },
    ],
    currentOrderRecord: {},
    trades: [],
    counterParties: [
      { id: -1, symbol: "FAKE", name: "FAKE IT TILL YOU MAKE IT" },
    ],
    types: ["BUY", "SELL"],
    toOrderDetails: false,
    newOrder: {
      quantity: "",
      price: "",
      side: "BUY",
      partyId: "1",
      stockId: "1",
    },
  };

  componentDidMount() {
    this.updatePartiesList();
  }

  updateStockExchangeList = () => {
    fetch(SERVICE_URL + "stockExchanges")
      .then((data) => data.json())
      .then((data) => console.log(data))
      .then(console.log(this.state.counterParties))
      .catch((err) => console.log("fail: " + err));
  };

  updatePartiesList = () => {
    fetch(SERVICE_URL + "parties")
      .then((data) => data.json())
      .then((data) => console.log(data))
      .then(console.log(this.state.counterParties))
      .catch((err) => console.log("fail: " + err));
  };

  //this.setState({ counterParties: data })
  handleAddFormChange = (event) => {
    // The event triggering this function should be an input's onChange event
    // We need to grab the input's name & value so we can associate it with the
    // newContactData within the App's state.
    let inputName = event.target.name;
    let inputValue = event.target.value;
    let orderInfo = this.state.newOrder;

    console.log(`Updating new contact data: ${inputName} : ${inputValue}`);

    if (orderInfo.hasOwnProperty(inputName)) {
      orderInfo[inputName] = inputValue;
      this.setState({ newOrder: orderInfo });
    }
  };

  handleUpdateFormChange = (event) => {
    // The event triggering this function should be an input's onChange event
    // We need to grab the input's name & value so we can associate it with the
    // newContactData within the App's state.
    let inputName = event.target.name;
    let inputValue = event.target.value;
    let orderInfo = this.state.newOrder;

    console.log(`Updating new contact data: ${inputName} : ${inputValue}`);

    if (orderInfo.hasOwnProperty(inputName)) {
      orderInfo[inputName] = inputValue;
      this.setState({ newOrder: orderInfo });
    }
  };

  shouldComponentUpdate() {
    return false; // Will cause component to never re-render.
  }

  handleAddFormSubmit = (event) => {
    console.log("Adding contact!");
    if (event) {
      event.preventDefault();
    }
    let orders = this.state.orders;
    orders.push(this.state.newOrder);
    this.setState({ orders });

    /* THIS WILL LINK TO THE BACK-END!!!!
    fetch(SERVICE_URL + "/contact/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(this.state.newContactData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Add Contact - Success:", data);
        this.setState({
          newContactData: {
            firstName: "",
            lastName: "",
            company: "",
            phone: "",
            email: "",
          },
        });
        this.loadContactData();
      })
      .catch((error) => {
        console.log("Add Contact - Error:");
        console.log(error);
      });
      */
  };

  selectingStock = (stock) => {
    const selectedStock = stock;
    this.setState({ selectedStock }, () => {
      console.log(this.state.selectedStock);
    });
  };

  getAllOrderDetails = (orderId) => {
    //INCLUDE FETCH FROM THE CONTROLLER
    //SET CURRENTORDERRECORD
    //IT TAKES A WHILE TO COMPLETE THE SET STATE FUNCTION - CHECK IT OUT!!!!!!!!!!!!!!!!!!!!!!
    this.retrieveOrderDetails(orderId);

    /*
    this.setState(() => ({ toOrderDetails: true }));
    if (this.state.toOrderDetails === true) {
      return <Redirect to="/orderDetails" />;
    }
    */
  };

  retrieveOrderDetails = (orderId) => {
    const OrderRecord = this.state.orders.find((order) => order.id === orderId);
    this.setState({ currentOrderRecord: OrderRecord });
  };

  componentDidUpdate() {}

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
                  selectedStock={this.state.selectedStock}
                  stocks={this.state.stocks}
                  selectingStock={this.selectingStock}
                  trades={this.state.trades}
                  orders={this.state.orders}
                  {...props}
                />
              )}
              exact
            />
            <Route
              path="/pastTrades"
              render={(props) => (
                <HistoricalData
                  {...props}
                  trades={this.state.trades}
                  stockExchange={stockExchange}
                />
              )}
            />
            <Route
              path="/addOrder"
              render={(props) => (
                <AddOrder
                  {...props}
                  counterParties={this.state.counterParties}
                  types={this.state.types}
                  stocks={this.state.stocks}
                  orders={this.state.orders}
                  handleSubmit={this.handleAddFormSubmit}
                  handleChange={this.handleAddFormChange}
                  newOrder={this.state.newOrder}
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
              render={(props) => (
                <UpdateOrder
                  {...props}
                  counterParties={this.state.counterParties}
                  stocks={this.state.stocks}
                />
              )}
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
