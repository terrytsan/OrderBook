import React, { Component } from "react";

import "react-confirm-alert/src/react-confirm-alert.css";
import NewOrder from "../components/addOrder/newOrder";

class AddOrder extends Component {
  state = {};

  render() {
    const { counterParties, types, stocks } = this.props;
    return (
      <div className="container-fluid">
        <h1>New Order</h1>
        <NewOrder
          counterParties={counterParties}
          types={types}
          stocks={stocks}
        />
      </div>
    );
  }
}
/*
Need to include the max cap on the number of shares per order extracted from the form above
*/

export default AddOrder;
