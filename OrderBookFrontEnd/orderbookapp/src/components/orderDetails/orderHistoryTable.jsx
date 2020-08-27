import React, { Component } from "react";

import OrderHistory from "./orderHistory.jsx";

class OrderHistoryTable extends Component {
  state = {};
  render() {
    const { order, orderRecords, allOrderTrades } = this.props;
    return (
      <div className="container border m-2">
        <div className="row m-2">
          <div className="versionNo col">Version Number</div>
          <div className="quantity col">Size</div>
          <div className="price col">{this.checkSide(order.side)}</div>
          <div className="tradePrice col">Sale Price</div>
          <div className="tradeQuantity col">Quantity Sold</div>
          <div className="tradeTime col">Time Of Trade</div>
          <div className="tradeParty col">CounterParty</div>
        </div>
        {orderRecords.map(function (orderRecord) {
          let orderTrade;
          // The counter party associated with the current trade of the order
          let orderTradeCounterParty;

          if (order.side === "BUY") {
            orderTrade = allOrderTrades.find(
                trade => trade.buyOrder.version === orderRecord.version
            ) || {};
            orderTradeCounterParty = (Object.keys(orderTrade).length !== 0) ?
                orderTrade.sellOrder.party.symbol +
                " (" +
                orderTrade.sellOrder.party.name +
                ")" : "";
          } else {
            orderTrade = allOrderTrades.find(
                trade => trade.sellOrder.version === orderRecord.version
            ) || {};
            orderTradeCounterParty =(Object.keys(orderTrade).length !== 0) ?
                orderTrade.buyOrder.party.symbol +
                " (" +
                orderTrade.buyOrder.party.name +
                ")" : "";
          }

          return <OrderHistory
              key={orderRecords.indexOf(orderRecord)}
              orderHistory={orderRecord}
              orderTrade={orderTrade}
              side={order.side}
              orderTradeCounterParty={orderTradeCounterParty}
          />;
        })}
      </div>
    );
  }

  checkSide = (side) => {
    if (side === "BUY") {
      return "BID";
    } else {
      return "ASK";
    }
  };
}

export default OrderHistoryTable;
