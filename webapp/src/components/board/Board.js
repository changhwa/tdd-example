import React, { Component } from 'react';
import autobind from 'autobind-decorator';
import request from 'superagent';
import { Grid, Col, Button, Row, FormGroup, ControlLabel, FormControl, Media, ButtonToolbar } from 'react-bootstrap';
import Pager from 'react-pager';
import moment from 'moment';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table-all.min.css';

export default class Board extends Component {

  constructor(props) {
    super(props);
    this.state = {
      showWriteBtn: '글쓰기',
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

  @autobind
  showWriteForm() {
    const flag = this.state.showWriteForm;
    this.setState({
      showWriteBtn: flag ? '글쓰기' : '리스트',
      articleId: null,
      title: '',
      body: '',
      comments: [],
      commentBody: ''
    }, () => this.setState({
      showWriteForm: !flag
    }));
  }

  @autobind
  saveArticle() {
    request.post('/api/article')
      .send({ id: this.state.articleId, title: this.state.title, body: this.state.body })
      .set('Accept', 'application/json')
      .end((err) => {
        if (!err) {
          this.setState({
            showWriteForm: false
          });
          this.handlePageChanged(0);
        }
      });
  }

  @autobind
  saveComment() {
    const url = `/api/article/${this.state.articleId}/comment`;
    request.post(url)
      .send({ body: this.state.commentBody })
      .set('Accept', 'application/json')
      .end((err, res) => {
        if (!err) {
          const comments = this.state.comments;
          console.log( comments.concat([res.body]));
          this.setState({
            comments: comments.concat([res.body])
          });
        }
      });

  }

  @autobind
  readArticle(rows) {
    const url = `/api/article/${rows.id}`;
    request.get(url)
        .set('Accept', 'application/json')
        .end((err, res) => {
          if (!err) {
            const resBody = res.body;
            this.setState({
              articleId: resBody.id,
              title: resBody.title,
              body: resBody.body,
              comments: resBody.comments,
              commentBody: '',
              showWriteForm: true,
              showWriteBtn: '리스트'
            });
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

  @autobind
  changeComment(event) {
    this.setState({
      commentBody: event.target.value
    });
  }

  convertDateFormat(cell) {
    return moment(cell).format('YYYY-MM-DD HH:mm:ss');
  }

  render() {
    const selectRow = {
      mode: 'radio',
      hideSelectColumn: true,
      clickToSelect: true,
      onSelect: this.readArticle
    };

    return (
      <div>
        <Grid fluid={true}>
          <h2>게시판</h2>
          <hr/>
          <Row>
            <Col xs={3}>
              <div>
                <ButtonToolbar>
                  <Button bsSize="small" onClick={this.showWriteForm}>{this.state.showWriteBtn}</Button>
                  { this.state.showWriteForm ? <Button bsSize="small" onClick={this.saveArticle}>저장</Button> : null }
                </ButtonToolbar>
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
                      <FormControl
                        type="text"
                        ref="title"
                        placeholder="Title"
                        value={this.state.title}
                        onChange={this.changeTitle}
                      />
                    </FormGroup>

                    <FormGroup controlId="formControlsTextarea">
                      <ControlLabel>본문</ControlLabel>
                      <FormControl
                        componentClass="textarea"
                        ref="body"
                        placeholder="body"
                        value={this.state.body}
                        onChange={this.changeBody}
                      />
                    </FormGroup>

                    {
                      this.state.comments ?
                      <FormGroup controlId="formControlsText">
                        <ControlLabel>댓글</ControlLabel>
                        &nbsp;&nbsp;<Button bsSize="xsmall" onClick={this.saveComment}>등록</Button>
                        <FormControl
                          type="text"
                          ref="comment"
                          placeholder="comment"
                          value={this.state.comment}
                          onChange={this.changeComment}
                        />
                        <hr />
                        <Media.List>
                          <Media.ListItem>
                        {
                          this.state.comments.map(comment =>
                            <Media>
                              <Media.Body>
                                <Media.Heading>{comment.body}</Media.Heading>
                                <p><Button bsSize="small">수정</Button> &nbsp;
                                <Button bsSize="small">삭제</Button></p>
                              </Media.Body>
                            </Media>
                          )
                        }
                          </Media.ListItem>
                        </Media.List>
                      </FormGroup> : null
                    }
                  </form>
                </div>
              </Col>
            </Row> :
            <Row>
              <Col xs={12}>
                <div>
                  <BootstrapTable data={this.state.articles} selectRow={selectRow}>
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
