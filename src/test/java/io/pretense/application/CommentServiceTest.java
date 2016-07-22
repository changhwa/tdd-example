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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(profiles = "test")
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService = new CommentService();

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;

    CommentDto givenCommentDto;
    Article givenArticle;
    Comment givenComment;
    Comment expectComment;

    @Before
    public void setUp() {
        givenCommentDto = createCommentDtoFixture();
        givenArticle = createArticleFixture();
        givenComment = new Comment(givenArticle, givenCommentDto);
        expectComment = createCommentFixture();
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

    private Article createArticleFixture() {
        return new Article(1L, "제목", "본문");
    }

    private CommentDto createCommentDtoFixture() {
        CommentDto commentDto = new CommentDto();
        commentDto.setBody("댓글본문");
        return commentDto;
    }

    public Comment createCommentFixture() {
        return new Comment(1L, "댓글본문", new Date(), givenArticle);
    }

}
