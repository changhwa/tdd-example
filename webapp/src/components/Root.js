import React, { Component } from 'react';
import routes from '../routes/';
import { Router, browserHistory } from 'react-router';

export default class Root extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Router history={browserHistory} routes={routes} />
    );
  }
}
