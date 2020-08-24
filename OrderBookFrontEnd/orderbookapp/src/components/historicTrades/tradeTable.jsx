import React, { Component } from "react";
import { Chart } from "react-charts";

class TradeTable extends Component {
  state = {};
  render() {
    const { trades } = this.props;
    return <h2>Trade Table</h2>;
  }
}

export default TradeTable;
