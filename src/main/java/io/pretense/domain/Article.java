package io.pretense.domain;

import io.pretense.interfaces.api.support.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String body;

    private Date updatedAt;

    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Article(ArticleDto articleDto) {
        this.id = articleDto.getId();
        this.title = articleDto.getTitle();
        this.body = articleDto.getBody();
    }

    @PrePersist
    public void prePersistUpdateAt() {
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdateUpdateAt() {
        this.updatedAt = new Date();
    }
}