package io.pretense.application;

import io.pretense.application.exception.ArticleNotFoundException;
import io.pretense.domain.Article;
import io.pretense.domain.Comment;
import io.pretense.infrastructure.jpa.ArticleRepository;
import io.pretense.infrastructure.jpa.CommentRepository;
import io.pretense.interfaces.api.support.CommentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(profiles = "test")
public class CommentServiceTest {

    CommentDto givenCommentDto;
    Article givenArticle;
    Comment givenComment;
    Comment expectComment;

    CommentDto givenReCommentDto;
    Comment givenReComment;
    Comment expectReComment;

    @InjectMocks
    private CommentService commentService = new CommentService();
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
        givenCommentDto = createCommentDtoFixture();
        givenArticle = createArticleFixture();
        givenComment = new Comment(givenArticle, givenCommentDto);
        expectComment = createCommentFixture();


        //TODO : 귀찮아 그냥 만들고 리팩토링할래
        givenReCommentDto= new CommentDto();
        givenReCommentDto.setParentId(1L);
        givenReCommentDto.setBody("대댓글");

        givenReComment = new Comment(givenArticle, givenReCommentDto);
        expectReComment = new Comment(2L, "대댓글", new Date(), givenArticle, null, new ArrayList());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 게시글번호가_없는_글의_댓글을_등록을_시도하면_예외가_발생한다() {

        //given
        when(articleRepository.findOne(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        //when
        commentService.save(null, givenCommentDto);

        //then
        //exception
    }

    @Test(expected = ArticleNotFoundException.class)
    public void 게시글조회결과가_없는_글의_댓글을_등록을_시도하면_예외가_발생한다() {

        //given
        when(articleRepository.findOne(any(Long.class))).thenReturn(null);

        //when
        commentService.save(any(Long.class), givenCommentDto);

        //then
        //exception
    }

    @Test
    public void 댓글을_등록을_시도하여_성공한다() {

        //given
        when(articleRepository.findOne(1L)).thenReturn(givenArticle);
        when(commentRepository.save(givenComment)).thenReturn(expectComment);

        //when
        Comment comment = commentService.save(1L, givenCommentDto);

        //then
        assertThat(comment.getId(), is(1L));
        assertThat(comment.getBody(), is("댓글본문"));
        assertThat(comment.getArticle().getComments().size(), is(1));
    }

    @Test
    public void 댓글을_수정을_시도하여_성공한다() {

        //given
        CommentDto givenBeforeUpdateComment = new CommentDto();
        givenBeforeUpdateComment.setId(1L);
        givenBeforeUpdateComment.setBody("수정본문");
        Comment expectUpdateComment = new Comment(givenArticle, givenBeforeUpdateComment);

        when(articleRepository.findOne(1L)).thenReturn(givenArticle);
        when(commentRepository.save(givenComment)).thenReturn(expectUpdateComment);

        //when
        Comment comment = commentService.save(1L, givenCommentDto);

        //then
        assertThat(comment.getId(), is(1L));
        assertThat(comment.getBody(), is("수정본문"));
        assertThat(comment.getArticle().getComments().size(), is(1));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void 존재하지_않는_댓글을_삭제_시도하여_실패한다() {

        //given
        long givenDeleteCommentId = 9999L;
        doThrow(new EmptyResultDataAccessException(1)).when(commentRepository).delete(givenDeleteCommentId);

        //when
        commentService.delete(givenDeleteCommentId);

        //then
        //exception
    }

    @Test
    public void 댓글을_삭제_시도하여_성공한다() {

        //given
        doNothing().when(commentRepository).delete(1L);

        //when
        commentService.delete(1L);

        //then
        verify(commentRepository).delete(1L);
    }

    @Test
    public void 대댓글을_작성_시도하여_성공한다() {

        //given
        when(articleRepository.findOne(1L)).thenReturn(givenArticle);
        when(commentRepository.save(givenReComment)).thenReturn(expectReComment);
        when(commentRepository.findOne(1L)).thenReturn(expectComment);

        //when
        Comment comment = commentService.save(1L, givenReCommentDto);

        //then
        assertThat(comment.getId(), is(2L));
        assertThat(comment.getParentComment().getId(), is(1L));
        assertThat(comment.getParentComment().getChildComments().size(), is(1));
    }

    private Article createArticleFixture() {
        return new Article(1L, "제목", "본문");
    }

    private CommentDto createCommentDtoFixture() {
        CommentDto commentDto = new CommentDto();
        commentDto.setBody("댓글본문");
        return commentDto;
    }

    public Comment createCommentFixture() {
        return new Comment(1L, "댓글본문", new Date(), givenArticle, null, new ArrayList());
    }

}
