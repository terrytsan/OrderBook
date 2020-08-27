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

const SERVICE_URL = window['runConfig'].apiUrl;

let stockExchange = {
  id: 1,
  name: "LSE",
  centralCounterParty: "LCH",
};

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
    // Holds the data for updating an order
    currentOrderRecord: {
      id: "",
      quantity: "",
      price: "",
      side: "BUY",
      partyId: "1",
      stockId: "1",
    },
    orderHistory: [
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
          version: 1,
        },
      },
    ],
    stockTrades: [],
    orderTrades: [
      {
        id: 1,
        buyOrder: {
          id: 1,
          stock: {
            id: 2,
            stockExchangeId: 1,
            symbol: "TSL",
            name: "Tesla",
            maxQuantity: 200,
            tickSize: 10,
          },
          party: { id: 1, symbol: "CP1", name: "CP1" },
          side: "SELL",
          quantity: 150,
          price: 71,
          timestamp: "2020-08-26T15:10:40.218",
          state: "LIVE",
          version: 1,
        },
        sellOrder: {
          id: 1,
          stock: {
            id: 2,
            stockExchangeId: 1,
            symbol: "TSL",
            name: "Tesla",
            maxQuantity: 200,
            tickSize: 10,
          },
          party: { id: 1, symbol: "CP1", name: "CP1" },
          side: "SELL",
          quantity: 150,
          price: 71,
          timestamp: "2020-08-26T15:10:40.218",
          state: "LIVE",
          version: 1,
        },
        quantity: 14,
        price: 71.0,
        timestamp: "2020-08-26T23:07:06.750099",
      },
    ],
    // Holds the trades to display in the ticker feed
    tickerFeedTrades: [
      {
        id: 1,
        buyOrder: {
          id: 1,
          stock: {
            id: 2,
            stockExchangeId: 1,
            symbol: "TSL",
            name: "Tesla",
            maxQuantity: 200,
            tickSize: 10,
          },
          party: { id: 1, symbol: "CP1", name: "CP1" },
          side: "SELL",
          quantity: 150,
          price: 71,
          timestamp: "2020-08-26T15:10:40.218",
          state: "LIVE",
          version: 1,
        },
        sellOrder: {
          id: 1,
          stock: {
            id: 2,
            stockExchangeId: 1,
            symbol: "TSL",
            name: "Tesla",
            maxQuantity: 200,
            tickSize: 10,
          },
          party: { id: 1, symbol: "CP1", name: "CP1" },
          side: "SELL",
          quantity: 150,
          price: 71,
          timestamp: "2020-08-26T15:10:40.218",
          state: "LIVE",
          version: 1,
        },
        quantity: 14,
        price: 71.0,
        timestamp: "2020-08-26T23:07:06.750099",
      },
    ],
    // Holds the number of trades to store in tickerFeedTrades
    tickerFeedTradeCount: 10,
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
    // Holds all the orders for the order manager
    allOrders: [],
  };

  componentDidMount() {
    this.updatePartiesList();
    this.updateStocksList();
    this.getTradesForTickerFeed();
  }

  // Gets a list of stocks from the endpoint - using the "global" stockExchange object
  updateStocksList = () => {
    fetch(SERVICE_URL + "stocks?stockExchangeId=" + stockExchange.id)
      .then((data) => data.json())
      .then((data) => {
        this.setState({ stocks: data });
        // set the first stock to the the default selected one
        this.setState({ selectedStock: data[0] }, () => {
          // fetch the list of live orders related to the first stock
          this.filterStockOrders(data[0]);
          // fetch all orders relating to the first stock
          this.getAllOrders(data[0]);
        });
        console.log("received: " + data);
        console.log("stocks state is now:" + this.state.stocks);
      })
      // .then(console.log(this.state.stocks))
      .catch((err) => console.log("fail: " + err));
  };

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

    console.log(`Updating new order data: ${inputName} : ${inputValue}`);

    if (orderInfo.hasOwnProperty(inputName)) {
      orderInfo[inputName] = inputValue;
      this.setState({ newOrder: orderInfo });
    }
  };

  handleAddFormSubmit = (event) => {
    console.log("Adding new order!");

    if (event) {
      event.preventDefault();
    }

    // Combine the params to perform the addOrder
    let params = {
      partyId: this.state.newOrder.partyId,
      side: this.state.newOrder.side,
      stockId: this.state.newOrder.stockId,
      quantity: this.state.newOrder.quantity,
      price: this.state.newOrder.price,
    };

    fetch(SERVICE_URL + "addOrder?" + new URLSearchParams(params), {
      method: "post",
    }).then((response) => response.text());

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

    console.log(`Updating order data: ${inputName} : ${inputValue}`);

    if (orderInfo.hasOwnProperty(inputName)) {
      orderInfo[inputName] = inputValue;
      this.setState({ currentOrderRecord: orderInfo });
    }
  };

  handleUpdateFormSubmit = (event) => {
    console.log("Updating order");
    if (event) {
      event.preventDefault();
    }

    // Combine the params to perform the updateOrder
    let params = {
      orderId: this.state.currentOrderRecord.id,
      quantity: this.state.currentOrderRecord.quantity,
      price: this.state.currentOrderRecord.price,
    };

    fetch(SERVICE_URL + "updateOrder?" + new URLSearchParams(params), {
      method: "post",
    }).then((response) => response.text());
  };

  handleCancelOrderClick = (idToCancel) => {
    console.log("Cancelling order");

    // let idToCancel = event.target.value;

    fetch(SERVICE_URL + "cancelOrder?orderId=" + idToCancel, {
      method: "post",
    })
      .then((response) => response.text())
      .then(() => this.filterStockOrders(this.state.selectedStock));
  };

  // Called when the stock combo-box is changed
  selectingStock = (stock) => {
    const selectedStock = stock;
    this.setState({ selectedStock: stock }, () => {
      console.log(this.state.selectedStock + "has been selected");
      this.filterStockOrders(selectedStock);
      this.filterStockTrades(selectedStock);
      this.getAllOrders(selectedStock);
    });
  };

  //Will fetch the orders for a stock
  filterStockOrders = (selectedStock) => {
    fetch(SERVICE_URL + "liveOrders?stockId=" + selectedStock.id)
      .then((data) => data.json())
      .then((data) => {
        this.setState({ orders: data }, () => {
          this.splitorders(this.state.orders);
        });
      });
  };

  // Gets all the orders for the order manager page
  getAllOrders = (selectedStock) => {
    fetch(SERVICE_URL + "ordersForStock?stockId=" + selectedStock.id)
        .then((data) => data.json())
        .then((data) => {
          this.setState({ allOrders: data }, () => {
          });
        });
  };

  //Will fetch the trades for a particular stock used in orderbook and historical data page
  filterStockTrades = (selectedStock) => {
    // Gets all the trades for the stock exchange
    console.log(selectedStock);
    fetch(SERVICE_URL + "tradesForStock?stockId=" + selectedStock.id)
      .then((data) => data.json())
      .then((data) => this.setState({ stockTrades: data }))
      .then(console.log("These are all the stocks trades"))
      .then(console.log(this.state.stockTrades));
  };

  //Will fetch the trades for a particular order used in order details
  filterOrderTrades = (orderId) => {
    // Gets all the trades for a particular order
    console.log(orderId);
    fetch(SERVICE_URL + "getTradesForOrder?orderId=" + orderId)
      .then((data) => data.json())
      .then((data) => this.setState({ orderTrades: data }))
      .then(console.log("These are all the orders trades"))
      .then(console.log(this.state.orderTrades));
  };

  //Will fetch the order history of a particular order used in order details
  filterOrderHistory = (orderId) => {
    // Gets all the order histor for a particular order
    console.log(orderId);
    fetch(SERVICE_URL + "getOrderHistory?orderId=" + orderId)
      .then((data) => data.json())
      .then((data) => this.setState({ orderHistory: data }))
      .then(console.log("These are all the orders, order history"))
      .then(console.log(this.state.orderHistory));
  };

  getTradesForTickerFeed = () => {
    fetch(
      SERVICE_URL +
        "trades?stockExchangeId=" +
        stockExchange.id +
        "&number=" +
        this.state.tickerFeedTradeCount
    )
      .then((data) => data.json())
      .then((data) => this.setState({ tickerFeedTrades: data }));
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
    this.filterOrderTrades(orderId);
    this.filterOrderHistory(orderId);
    //Then this will retrieve all the data in the database using that order ID
    //Allocating it to this.state.orderHistory
  };

  // Used in orderManaged to update a certain record
  retrieveOrderDetails = (orderId) => {
    const OrderRecord = this.state.allOrders.find((order) => order.id === orderId);
    this.setState({ currentOrderRecord: OrderRecord }, () => {
      console.log(this.state.currentOrderRecord);
    });
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
                  trades={this.state.stockTrades}
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
                  selectedStock={this.state.selectedStock}
                  stocks={this.state.stocks}
                  selectingStock={this.selectingStock}
                  trades={this.state.stockTrades}
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
                  orderRecords={this.state.orderHistory}
                  trades={this.state.orderTrades}
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
                  orders={this.state.allOrders}
                  trades={this.state.stockTrades}
                  getAllOrderDetails={this.getAllOrderDetails}
                  retrieveOrderDetails={this.retrieveOrderDetails}
                  handleCancelOrderClick={this.handleCancelOrderClick}
                />
              )}
            />
            <Route path="/help" component={Help} />
            <Route component={Error} />
          </Switch>
        </BrowserRouter>
        <TickerFeed tickerFeedTrades={this.state.tickerFeedTrades} />
      </React.Fragment>
    );
  }
}

export default App;
