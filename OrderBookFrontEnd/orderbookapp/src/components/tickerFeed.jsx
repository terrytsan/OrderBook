import React, { Component } from "react";
import Ticker from "react-ticker";

class TickerFeed extends Component {
  state = {};
  render() {
    const { tickerFeedTrades } = this.props;
    console.log(tickerFeedTrades);
    return (
      <div>
        <br />
        <br />
        <div className="fixed-bottom">
          <Ticker>
            {() => <h1>{this.compileListOfTrades(tickerFeedTrades)}</h1>}
          </Ticker>
        </div>
      </div>
    );
  }
  compileListOfTrades = (listOfTrades) => {
    let tradeStatement = "          ";
    listOfTrades.map((trade) =>
      tradeStatement.concat(
        trade.buyOrder.stock.symbol + " " + trade.price + "         |         "
      )
    );
    console.log(tradeStatement);
    return tradeStatement;
  };
}

export default TickerFeed;
