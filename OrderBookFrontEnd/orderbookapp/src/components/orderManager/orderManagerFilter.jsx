import React, { Component } from "react";
import Select from "../input/select";
import { Form } from "react-bootstrap";

class FilterOrders extends Component {
  state = {
    states: [
      "LIVE ORDERS",
      "COMPLETED ORDERS",
      "CANCELLED ORDERS",
      "ALL ORDERS",
    ],
  };
  render() {
    let { handleFilterChange, selectedFilter } = this.props;
    return (
      <div className="container">
        <Form>
          <Form.Group>
            <Form.Label>Filter Options:</Form.Label>
            <Form.Control
              as="select"
              name="state"
              onChange={handleFilterChange}
              defaultValue={selectedFilter}
            >
              {this.state.states.map((state) => (
                <option value={state} key={this.state.states.indexOf(state)}>
                  {state}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
        </Form>
      </div>
    );
  }
}

export default FilterOrders;

/*<Select
          title={"Filter Options:"}
          name={"chosenFilter"}
          value={selectedFilter}
          placeholder={"Select Filter..."}
          options={this.state.states}
          
        />*/
