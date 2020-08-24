import React, { Component } from "react";
import Ticker from "react-ticker";

class TickerFeed extends Component {
  state = {};
  render() {
    return (
      <div className="fixed-bottom">
        <Ticker offset="run-in" speed={10}>
          {({ index }) => (
            <>
              <h5>Not yet Functioning!!!</h5>
            </>
          )}
        </Ticker>
      </div>
    );
  }
}

export default TickerFeed;
