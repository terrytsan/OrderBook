import React, { Component } from "react";
import Ticker from "react-ticker";

class TickerFeed extends Component {
  state = {};
  render() {
    let trades = this.compileListOfTrades();
    return (
      <div>
        <br />
        <br />
        <div className="fixed-bottom tickerFeed">
          <Ticker mode="smooth" speed="20">
            {() => (
              <h1>
                {this.props.tickerFeedTrades.map(
                  (trade) =>
                    trade.buyOrder.stock.symbol + " " + trade.price + " +++ "
                )}
              </h1>
            )}
          </Ticker>
        </div>
      </div>
    );
  }
  compileListOfTrades = () => {
    let tradeStatement = "          ";
    this.props.tickerFeedTrades.map((trade) => console.log(trade));
    return tradeStatement;
  };
}

export default TickerFeed;

/*
tradeStatement.concat(
        trade.buyOrder.stock.symbol + " " + trade.price + "         |         "
      )
      */
