package io.pretense.infrastructure.jpa;

import io.pretense.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByOrderByIdDesc(Pageable pageable);


}
