import React, { Component } from 'react';
import autobind from 'autobind-decorator';
import request from 'superagent';
import { Grid, Col, Button, Row, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';
import Pager from 'react-pager';
import moment from 'moment';
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
          console.log(res.body);
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

  @autobind
  showWriteForm() {
    const flag = this.state.showWriteForm;
    this.setState({
      title: '',
      body: ''
    }, () => this.setState({
      showWriteForm: !flag
    }));
  }

  @autobind
  saveArticle() {
    request.post('/api/article')
      .send({title: this.state.title, body: this.state.body})
      .set('Accept', 'application/json')
      .end((err, res) => {
        if (!err) {
          this.setState({
            showWriteForm: false
          });
          this.handlePageChanged(0);
        }
      });
  }

  @autobind
  changeTitle(event) {
    this.setState({
      title: event.target.value
    });
  }

  @autobind
  changeBody(event) {
    this.setState({
      body: event.target.value
    });
  }

  convertDateFormat(cell) {
    return moment(cell).format('YYYY-MM-DD HH:mm:ss');
  }

  render() {
    return (
      <div>
        <Grid fluid={true}>
          <h2>게시판</h2>
          <hr/>
          <Row>
            <Col xs={1}>
              <div>
                <Button bsSize="small" onClick={this.showWriteForm}>글쓰기</Button>
              </div>
            </Col>
          </Row>
          { this.state.showWriteForm ?
            <Row>
              <Col xs={5}>
                <div>
                  <form>
                    <FormGroup controlId="formControlsText">
                      <ControlLabel>제목</ControlLabel>
                      <FormControl type="text" ref="title" placeholder="Title" value={this.state.title} onChange={this.changeTitle}/>
                    </FormGroup>

                    <FormGroup controlId="formControlsTextarea">
                      <ControlLabel>본문</ControlLabel>
                      <FormControl componentClass="textarea" ref="body" placeholder="body" value={this.state.body} onChange={this.changeBody}/>
                    </FormGroup>

                    <Button bsSize="small" onClick={this.saveArticle}>
                      Submit
                    </Button>
                  </form>
                </div>
              </Col>
            </Row> :
            <Row>
              <Col xs={12}>
                <div>
                  <BootstrapTable data={this.state.articles}>
                    <TableHeaderColumn dataField="id" isKey={true} hidden>ID</TableHeaderColumn>
                    <TableHeaderColumn dataField="title">제목</TableHeaderColumn>
                    <TableHeaderColumn dataField="updatedAt" dataFormat={this.convertDateFormat}>수정일</TableHeaderColumn>
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
            </Row>
          }
        </Grid>
      </div>
    );
  }
}
