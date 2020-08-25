import React, { Component } from "react";

import NewOrder from "../components/addOrder/newOrder.jsx";

class AddOrder extends Component {
  state = {};

  render() {
    const {
      counterParties,
      types,
      stocks,
      handleSubmit,
      newOrder,
      handleChange,
    } = this.props;
    return (
      <div className="container-fluid">
        <div className="container">
          <h1 className="">New Order</h1>
        </div>
        <NewOrder
          counterParties={counterParties}
          types={types}
          stocks={stocks}
          handleChange={handleChange}
          newOrder={newOrder}
          handleSubmit={handleSubmit}
        />
      </div>
    );
  }
}
/*
Need to include the max cap on the number of shares per order extracted from the form above
*/

export default AddOrder;
