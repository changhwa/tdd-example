package io.pretense.domain;

import io.pretense.interfaces.api.support.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String body;

    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article(ArticleDto articleDto) {
        this.id = articleDto.getId();
        this.title = articleDto.getTitle();
        this.body = articleDto.getBody();
    }
}