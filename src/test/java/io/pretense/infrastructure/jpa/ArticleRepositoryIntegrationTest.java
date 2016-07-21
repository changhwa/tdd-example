package io.pretense.infrastructure.jpa;

import io.pretense.config.RepositoryIntegrationTestHelper;
import io.pretense.domain.Article;
import io.pretense.domain.Comment;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ArticleRepositoryIntegrationTest extends RepositoryIntegrationTestHelper {

    @Autowired
    private ArticleRepository articleRepository;

    @Before
    public void setup() {
    }

    @Test
    public void test_save_article() {

        //given
        Article article = new Article("제목입니다", "본문입니다");

        //when
        Article newArticle = articleRepository.save(article);

        //then
        assertNotNull(newArticle.getId());
    }

    @Test
    public void test_update_article() {

        //given
        Article article = givenSaveArticle();
        Article newArticle = new Article(article.getId(), "수정후제목", "수정후본문");

        //when
        Article updateArticle = articleRepository.save(newArticle);

        //then
        assertThat(updateArticle.getTitle(), is("수정후제목"));
        assertThat(updateArticle.getBody(), is("수정후본문"));
    }

    @Test
    public void test_get_one_article() {

        //given
        Article article = givenSaveArticle();

        //when
        Article newArticle = articleRepository.findOne(article.getId());

        //then
        assertThat(newArticle.getTitle(), is("수정전제목"));
    }

    @Test
    public void test_delete_one_article_by_id() {

        Article article = givenSaveArticle();

        //when
        articleRepository.delete(article.getId());

        //then
        assertNull(articleRepository.findOne(article.getId()));
    }

    @Test
    public void test_find_by_all_order_by_id_desc() {

        //given
        givenSaveArticle();
        givenSaveArticle();

        //when
        Page<Article> page = articleRepository.findAllByOrderByIdDesc(new PageRequest(0, 10));

        //then
        List<Article> articles = page.getContent();
        assertTrue(articles.get(0).getId() > articles.get(articles.size()-1).getId());
    }

    @Test
    public void 게시글과_댓글을_가져온다() {

        //given
        Long testId = 9L;

        //when
        Article article = articleRepository.findOne(testId);
        List<Comment> comments = article.getComments();

        //then
        assertThat(comments.size(), is(2));
    }


    private Article givenSaveArticle() {
        //given
        Article article = new Article("수정전제목", "수정전본문");
        article = articleRepository.save(article);
        return article;
    }
}