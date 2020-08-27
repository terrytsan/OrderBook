import React, { Component } from "react";

import Stock from "../components/stock.jsx";
import AllOrdersManaged from "../components/orderManager/allOrdersManaged.jsx";

class OrderManager extends Component {
  state = {};
  render() {
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
            <Stock
              selectedStock={selectedStock}
              stocks={stocks}
              selectingStock={selectingStock}
              trades={trades}
            />
          </div>
          <AllOrdersManaged
            orders={orders}
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
