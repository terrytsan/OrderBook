import React, { Component } from "react";

class OrderHistory extends Component {
  state = {
    orderTrade: {},
  };

  componentWillMount = () => {
    this.getTradePrice(this.props.orderHistory.id, this.props.side);
  };

  render() {
    const { orderHistory, allOrderTrades, side } = this.props;
    return (
      <div className="row m-2">
        <div className="versionNo col">{orderHistory.version}</div>
        <div className="quantity col">{orderHistory.quantity}</div>
        <div className="price col">{orderHistory.price}</div>
        <div className="tradePrice col">{orderTrade.price}</div>
        <div className="tradeQuantity col">{orderTrade.quantity}</div>
        <div className="tradeTime col">{orderTrade.timestamp}</div>
        <div className="tradeParty col">{this.getCounterParty()}</div>
      </div>
    );
  }

  getTradePrice = (orderHistoryId, side) => {
    const allTrades = this.props.allOrderTrades;
    let orderTrade;
    if (side === "BUY") {
      orderTrade = allTrades.find(
        (trade) => trade.buyOrder.id === orderHistoryId
      );
    } else {
      orderTrade = allTrades.find(
        (trade) => trade.sellOrder.id === orderHistoryId
      );
    }
    this.setState({ orderTrade });
  };

  getCounterParty = () => {
    let counterParty;
    if (side === "BUY") {
      counterParty =
        orderTrade.sellOrder.party.symbol +
        " (" +
        orderTrade.sellOrder.party.name +
        ")";
    } else {
      counterParty =
        orderTrade.buyOrder.party.symbol +
        " (" +
        orderTrade.buyOrder.party.name +
        ")";
    }
    return counterParty;
  };
}

export default OrderHistory;
