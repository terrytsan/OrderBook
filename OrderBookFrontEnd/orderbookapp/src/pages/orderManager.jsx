import React, { Component } from "react";

import Stock from "../components/stock.jsx";
import AllOrdersManaged from "../components/orderManager/allOrdersManaged.jsx";
import FilterOrders from "../components/orderManager/orderManagerFilter.jsx";

class OrderManager extends Component {
  state = {
    selectedFilter: "ALL ORDERS",
    orders: [],
  };

  handleFilterChange = (event) => {
    console.log(event.target.value);
    this.setState({ selectedFilter: event.target.value });
    console.log(this.state.selectedFilter);
  };

  filterOrders = () => {
    let orders = [];
    if (this.state.selectedFilter == "ALL ORDERS") {
      orders = this.props.orders;
    } else if (this.state.selectedFilter == "LIVE ORDERS") {
      orders = this.props.orders.filter((order) => order.state === "LIVE");
    } else if (this.state.selectedFilter == "CANCELLED ORDERS") {
      orders = this.props.orders.filter((order) => order.state === "CANCELLED");
    } else if (this.state.selectedFilter == "COMPLETED ORDERS") {
      orders = this.props.orders.filter((order) => order.state === "COMPLETED");
    }
    return orders;
  };

  render() {
    let displayOrders = this.filterOrders();
    const {
      selectedStock,
      stocks,
      selectingStock,
      orders,
      getAllOrderDetails,
      retrieveOrderDetails,
      handleCancelOrderClick,
      trades,
    } = this.props;
    return (
      <div className="container-fluid">
        <h1>Order Manager</h1>
        <div>
          <div className="row m-2">
            <div className="col-9 border">
              <div className="row">
                <Stock
                  selectedStock={selectedStock}
                  stocks={stocks}
                  selectingStock={selectingStock}
                  trades={trades}
                />
              </div>
            </div>
            <div className="col-3">
              <FilterOrders
                handleFilterChange={this.handleFilterChange}
                selectedFilter={this.state.selectedFilter}
              />
            </div>
          </div>
          <AllOrdersManaged
            orders={displayOrders}
            getAllOrderDetails={getAllOrderDetails}
            retrieveOrderDetails={retrieveOrderDetails}
            handleCancelOrderClick={handleCancelOrderClick}
          />
        </div>
      </div>
    );
  }
}

export default OrderManager;
