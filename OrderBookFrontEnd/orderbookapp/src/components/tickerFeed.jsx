import React, { Component } from "react";
import Ticker from "react-ticker";

class TickerFeed extends Component {
  state = {};
  render() {
    return (
      <div>
        <br />
        <br />
        <div className="fixed-bottom">
          <Ticker speed={10}>
            {({ index }) => (
              <>
                <h5>Not yet Functioning!!!</h5>
              </>
            )}
          </Ticker>
        </div>
      </div>
    );
  }
}

export default TickerFeed;
