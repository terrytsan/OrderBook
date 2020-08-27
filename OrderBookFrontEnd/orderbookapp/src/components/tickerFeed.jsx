import React, { Component } from "react";
import Ticker from "react-ticker";

class TickerFeed extends Component {
  state = {};
  render() {
      const {tickerFeedTrades} = this.props;
    return (
      <div>
        <br />
        <br />
        <div className="fixed-bottom">
          <Ticker speed={10} offset="run-in">
            {({ index }) => (
              <>
                <h5>{tickerFeedTrades[0].buyOrder.stock.symbol + " " + tickerFeedTrades[0].price}</h5>
              </>
            )}
          </Ticker>
        </div>
      </div>
    );
  }
}

export default TickerFeed;
