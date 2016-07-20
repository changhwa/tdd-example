package io.pretense.infrastructure.jpa;

import io.pretense.config.RepositoryIntegrationTestHelper;
import io.pretense.domain.Article;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Article givenSaveArticle() {
        //given
        Article article = new Article("수정전제목", "수정전본문");
        article = articleRepository.save(article);
        return article;
    }
}