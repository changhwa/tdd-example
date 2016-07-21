import React, { Component } from 'react';
import { Panel } from 'react-bootstrap';

export default class Board extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Panel style={{ width: '500', margin: '0 auto' }}>
        <div className="text-center">
          <h4 className="content-group">게시판!</h4>
        </div>
        <hr />
        <div>
          Hello World
        </div>
      </Panel>
    );
  }
}
