import React from 'react';
import { Route } from 'react-router';
import Main from '../components/Main';
import Board from '../components/board/Board';

export default (
  <Route path="/" component={Main} >
    <Route path="/board" component={Board} />
  </Route>
);
