import React, { Component } from "react";

import UpdatingCurrentOrder from "./../components/updateOrder/updateCurrentOrder";

class UpdateOrder extends Component {
  state = {
    order: {
      quantity: "",
      price: "",
      side: "BUY",
      partyId: "1",
      stockId: "1",
    },
  };

  componentDidMount() {
    this.setState({ order: this.props.order });
  }

  render() {
    const { order, handleSubmit, handleChange } = this.props;
    return (
      <div className="container">
        <h1>Update Order</h1>
        <UpdatingCurrentOrder
          order={order}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
        />
      </div>
    );
  }
}

export default UpdateOrder;
