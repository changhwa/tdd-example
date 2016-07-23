package io.pretense.application;

import io.pretense.application.exception.ArticleNotFoundException;
import io.pretense.domain.Article;
import io.pretense.domain.Comment;
import io.pretense.infrastructure.jpa.ArticleRepository;
import io.pretense.infrastructure.jpa.CommentRepository;
import io.pretense.interfaces.api.support.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment save(Long articleId, CommentDto commentDto) {
        Article article = getArticle(articleId);
        Comment comment = commentRepository.save(new Comment(article, commentDto));
        article.addComment(comment);
        return comment;
    }

    private Article getArticle(Long articleId) {
        Article article = articleRepository.findOne(articleId);
        if (article == null) throw new ArticleNotFoundException();
        return article;
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }
}
