import React, { Component } from 'react';
import request from 'superagent';
import { Grid, Col } from 'react-bootstrap';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table-all.min.css';

export default class Board extends Component {

  constructor(props) {
    super(props);
    this.state = {
      articles: []
    };
  }

  componentWillMount() {
    this.loadArticles();
  }

  loadArticles() {
    request.get('/api/article')
      .set('Accept', 'application/json')
      .end((err, res) => {
        if (!err) {
          this.setState({
            articles: res.body.content
          });
        }
      });
  }

  render() {
    return (
      <div>
        <Grid fluid={true}>
          <h2>게시판</h2>
          <hr/>
          <Col xs={12}>
            <div>
              <BootstrapTable data={this.state.articles}>
                <TableHeaderColumn dataField="id" isKey={true} hidden>ID</TableHeaderColumn>
                <TableHeaderColumn dataField="title">제목</TableHeaderColumn>
              </BootstrapTable>
            </div>
          </Col>
        </Grid>
      </div>
    );
  }
}
