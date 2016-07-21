import React, { Component } from 'react';
import autobind from 'autobind-decorator';
import request from 'superagent';
import { Grid, Col } from 'react-bootstrap';
import Pager from 'react-pager';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table-all.min.css';

export default class Board extends Component {

  constructor(props) {
    super(props);
    this.state = {
      articles: [],
      totalPage: 0,
      visiblePage: 4,
      currentPage: 0
    };
  }

  componentWillMount() {
    this.loadArticles();
  }

  loadArticles() {
    const url = `/api/article?size=2&page=${this.state.currentPage}`;
    request.get(url)
      .set('Accept', 'application/json')
      .end((err, res) => {
        if (!err) {
          const resBody = res.body;
          this.setState({
            articles: resBody.content,
            totalPage: resBody.totalPages,
            currentPage: resBody.number
          });
        }
      });
  }

  @autobind
  handlePageChanged(newPage) {
    this.setState({ currentPage: newPage }, () => this.loadArticles());
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
            <div>
              <Pager
                total={this.state.totalPage}
                current={this.state.currentPage}
                visiblePages={this.state.visiblePage}
                onPageChanged={this.handlePageChanged}
              />
            </div>
          </Col>
        </Grid>
      </div>
    );
  }
}
