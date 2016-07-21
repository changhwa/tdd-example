import React, { Component, PropTypes } from 'react';
import HeadNav from './common/HeadNav';

export default class Main extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    const { children } = this.props;

    return (
      <div>
        <HeadNav />
        <div style={{ marginTop: '60px' }}>
          {children}
        </div>
      </div>
    );
  }
}

Main.propTypes = {
  children: PropTypes.node
};
