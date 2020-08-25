import React, { Component } from "react";

class TradeTable extends Component {
  state = {};
  render() {
    let { trades } = this.props;
    return (
      <div className="container border m-4">
        <h2>Trade Table</h2>
      </div>
    );
  }
}

export default TradeTable;
