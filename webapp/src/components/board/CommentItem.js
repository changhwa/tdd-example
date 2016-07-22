import React, { Component, PropTypes } from 'react';
import autobind from 'autobind-decorator';
import { FormControl, Media, Button } from 'react-bootstrap';

export default class CommentItem extends Component {

  constructor(props) {
    super(props);
    this.state = {
      comment: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps && nextProps.comment) {
      this.setState({ comment: nextProps.comment });
    }
  }

  @autobind
  handleChangeBody(event) {
    const comment = {
      id: this.state.comment.id,
      body: event.target.value
    };

    this.setState({ comment });
  }

  @autobind
  handleClickUpdate(comment) {
    const newComment = {
      id: comment.id,
      body: this.state.body
    };

    this.setState({
      comment: newComment
    }, () => this.props.saveComment(newComment));
  }

  render() {
    const { comment } = this.props;
    return (
      <Media>
        <Media.Body>
          <p>
            <Button bsSize="xsmall" onClick={() => this.handleClickUpdate(comment)}>수정</Button> &nbsp;
            <Button bsSize="xsmall">삭제</Button></p>
          <Media.Heading>
            <FormControl
              refs="body"
              componentClass="textarea"
              placeholder="comment"
              value={this.state.comment.body}
              defaultValue={this.props.comment.body}
              onChange={this.handleChangeBody}
            />
          </Media.Heading>

        </Media.Body>
      </Media>
    );
  }
}

CommentItem.propTypes = {
  saveComment: PropTypes.func.isRequired,
  comment: PropTypes.object.isRequired
};
