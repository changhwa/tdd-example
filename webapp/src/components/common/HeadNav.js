import React, { Component } from 'react';
import { Navbar, Nav, NavItem } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';

export default class HeadNav extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Navbar fixedTop>
        <Navbar.Header>
          <Navbar.Brand>
            Narratage
          </Navbar.Brand>
        </Navbar.Header>
        <Nav>
          <LinkContainer to={{ pathname: '/board' }}>
            <NavItem eventKey={1}>게시판</NavItem>
          </LinkContainer>
        </Nav>
      </Navbar>
    );
  }
}
