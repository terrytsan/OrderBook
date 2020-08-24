import React from "react";
import { Navbar, Nav } from "react-bootstrap";

const Navigation = () => {
  return (
    <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
      <Navbar.Brand href="/">OrderBook Logo</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link href="/">OrderBook</Nav.Link>
          <Nav.Link href="/historicalTrades">Historical Data</Nav.Link>
          <Nav.Link href="/addOrder">Add Order</Nav.Link>
          <Nav.Link href="/manageOrders">Order Manager</Nav.Link>
        </Nav>
        <Nav>
          <Nav.Link href="/help">Help</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Navigation;
