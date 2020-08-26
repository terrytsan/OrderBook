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

const SERVICE_URL =
  "http://startpage-env.eba-2npqnjuc.eu-west-2.elasticbeanstalk.com/";

let stockExchange = {
  id: 1,
  name: "LSE",
  centralCounterParty: "LCH",
};

let liveOrders = [
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
  {
    id: 3,
    time: "5:01:00",
    quantity: 1000,
    price: "10.25",
    side: "SELL",
    party: { id: 2, symbol: "CP2", name: "Counter Party 2" },
    stock: {
      id: 2,
      stockExchangeId: 1,
      symbol: "TSL",
      name: "Tesla",
      maxQuantity: 200,
      tickSize: 10,
    },
  },
];

/*
let allStocks = [
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
];
*/

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
    orders: [],
    currentOrderRecord: {
      quantity: "",
      price: "",
      side: "BUY",
      partyId: "1",
      stockId: "1",
    },
    currentOrderHistory: [
      {
        id: 3,
        time: "5:01:00",
        quantity: 1000,
        price: "10.25",
        side: "SELL",
        party: { id: 2, symbol: "CP2", name: "Counter Party 2" },
        stock: {
          id: 2,
          stockExchangeId: 1,
          symbol: "TSL",
          name: "Tesla",
          maxQuantity: 200,
          tickSize: 10,
        },
      },
    ],
    currentOrderTrades: [],
    trades: [],
    counterParties: [
      { id: -1, symbol: "FAKE", name: "FAKE IT TILL YOU MAKE IT" },
    ],
    types: ["BUY", "SELL"],
    newOrder: {
      quantity: "",
      price: "",
      side: "BUY",
      partyId: "1",
      stockId: "1",
    },
    buyOrders: [],
    sellOrders: [],
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
      .then((data) => this.setState({ counterParties: data }))
      .catch((err) => console.log("fail: " + err));
    //)
  };

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

  handleAddFormSubmit = (event) => {
    console.log("Adding contact!");
    if (event) {
      event.preventDefault();
    }
    let orders = this.state.orders;
    orders.push(this.state.newOrder);
    this.setState({ orders }, () => {
      console.log(orders);
      this.splitorders(this.state.orders);
    });
  };

  handleUpdateFormChange = (event) => {
    // The event triggering this function should be an input's onChange event
    // We need to grab the input's name & value so we can associate it with the
    // newContactData within the App's state.
    let inputName = event.target.name;
    let inputValue = event.target.value;
    let orderInfo = this.state.currentOrderRecord;

    console.log(`Updating new contact data: ${inputName} : ${inputValue}`);

    if (orderInfo.hasOwnProperty(inputName)) {
      orderInfo[inputName] = inputValue;
      this.setState({ newOrder: orderInfo });
    }
  };

  handleUpdateFormSubmit = (event) => {
    console.log("Adding contact!");
    if (event) {
      event.preventDefault();
    }
    //Will update in the backend
    let orders = this.state.orders;
    orders.push(this.state.currentOrderRecord);
    this.setState({ orders }, () => {
      console.log(orders);
      this.splitorders(this.state.orders);
    });
  };

  selectingStock = (stock) => {
    const selectedStock = stock;
    this.setState({ selectedStock }, () => {
      console.log(this.state.selectedStock);
      this.filterStockOrders(selectedStock);
      this.filterStockTrades(selectedStock);
    });
  };

  //Will fetch the orders for a stock
  filterStockOrders = (selectedStock) => {
    console.log(this.state.selectedStock);
    const allOrders = liveOrders;
    const orders = allOrders.filter(
      (order) => order.stock.id === selectedStock.id
    );
    console.log(orders);
    this.setState({ orders }, () => {
      console.log(orders);
      this.splitorders(this.state.orders);
    });
  };

  //Will fetch the trades
  filterStockTrades = (selectedStock) => {
    console.log(this.state.selectedStock);
    const allOrders = liveOrders;
    const orders = allOrders.filter(
      (order) => order.stock.id === selectedStock.id
    );
    console.log(orders);
    this.setState({ orders }, () => {
      console.log(orders);
      this.splitorders(this.state.orders);
    });
  };

  //This method is used to split the order based on the side
  splitorders = (orders) => {
    const buyOrders = orders.filter((order) => order.side === "BUY");
    const sellOrders = orders.filter((order) => order.side === "SELL");
    this.setState({ buyOrders });
    this.setState({ sellOrders });
  };

  //Used to get at orders details, for the order details page.
  getAllOrderDetails = (orderId) => {
    //INCLUDE FETCH FROM THE CONTROLLER
    //SET CURRENTORDERRECORD
    //IT TAKES A WHILE TO COMPLETE THE SET STATE FUNCTION - CHECK IT OUT!!!!!!!!!!!!!!!!!!!!!!
    this.retrieveOrderDetails(orderId);
    this.getAllOrderHistory(orderId);
    this.getAllOrderTrades(orderId);
    //Then this will retrieve all the data in the database using that order ID
    //Allocating it to this.state.retrievedOrderDetails
  };

  // Used in orderManaged to update a certain record
  retrieveOrderDetails = (orderId) => {
    const OrderRecord = this.state.orders.find((order) => order.id === orderId);
    this.setState({ currentOrderRecord: OrderRecord }, () => {
      console.log(this.state.currentOrderRecord);
    });
  };

  //This method is used to get all the orders history
  getAllOrderHistory = (orderID) => {
    //INCLUDE FETCH METHOD HERE
    //sets to the state =======>>>>>>> currentOrderHistory
  };

  //This method is used to get all the trades for an order
  getAllOrderTrades = (orderID) => {
    //INCLUDE FETCH METHOD HERE
    //sets to the state =======>>>>>>> currentOrderTrades
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
                  buyOrders={this.state.buyOrders}
                  sellOrders={this.state.sellOrders}
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
                  order={this.state.currentOrderRecord}
                  orderRecords={this.state.currentOrderHistory}
                  allOrderTrades={this.state.currentOrderTrades}
                />
              )}
            />
            <Route
              path="/updateOrder"
              render={(props) => (
                <UpdateOrder
                  {...props}
                  order={this.state.currentOrderRecord}
                  handleSubmit={this.handleUpdateFormSubmit}
                  handleChange={this.handleUpdateFormChange}
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
                  getAllOrderDetails={this.getAllOrderDetails}
                  retrieveOrderDetails={this.retrieveOrderDetails}
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
