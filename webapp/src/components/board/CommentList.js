import React, { Component, PropTypes } from 'react';
import autobind from 'autobind-decorator';
import { FormGroup, ControlLabel, FormControl, Media, Button } from 'react-bootstrap';
import CommentItem from './CommentItem';

export default class CommentList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      comment: {}
    };
  }

  @autobind
  handleChangeComment(event) {
    const comment = {
      id: null,
      body: event.target.value
    };

    this.setState({ comment });
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps && nextProps.comments) {
      this.setState({ comments: nextProps.comments });
    }
  }

  render() {
    const makeComment = data => {
      return data.map((comment, idx) => {
        if (comment.childComments) {
          return (
            <CommentItem
              key={`comments_item${idx}`}
              comment={comment}
              saveComment={this.props.saveComment}
              deleteComment={this.props.deleteComment}
            > {makeComment(comment.childComments)} </CommentItem>
          );
        }
        return (
          <CommentItem
            key={`comments_item${idx}`}
            comment={comment}
            saveComment={this.props.saveComment}
            deleteComment={this.props.deleteComment}
          />
        );
      });
    };

    return (
      <div>
        <FormGroup>
          <ControlLabel>댓글</ControlLabel>
          &nbsp;&nbsp;<Button bsSize="xsmall" onClick={() => this.props.saveComment(this.state.comment)}>등록</Button>
          <FormControl
            type="text"
            ref="comment"
            placeholder="comment"
            value={this.state.comment.body}
            onChange={this.handleChangeComment}
          />
          <hr />
          {
            this.props.comments.length ?
              <Media.List>
                <Media.ListItem>
                  {
                    makeComment(this.props.comments)
                  }
                </Media.ListItem>
              </Media.List> : null
          }
        </FormGroup>
      </div>
    );
  }
}

CommentList.propTypes = {
  saveComment: PropTypes.func.isRequired,
  deleteComment: PropTypes.func.isRequired,
  comments: PropTypes.array
};
