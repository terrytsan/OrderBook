import React, { Component } from "react";
import { Chart } from "react-charts";

class TradeGraph extends Component {
  state = {};
  render() {
    const { trades } = this.props;
    return (
      <div className="container-fuild border">
        <h2>Trade Graph</h2>
      </div>
    );
  }
}

export default TradeGraph;
