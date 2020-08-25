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
    this.setState({ order: this.props.location.aboutOrder.order });
  }

  render() {
    const { order } = this.props.location.aboutOrder;
    const { counterParties, stocks } = this.props;
    return (
      <div>
        <h1>Update Order</h1>
        <UpdatingCurrentOrder
          order={order}
          counterParties={counterParties}
          stocks={stocks}
        />
      </div>
    );
  }
}

export default UpdateOrder;
