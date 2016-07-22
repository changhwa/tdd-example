package io.pretense.domain;

import io.pretense.interfaces.api.support.ArticleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Comment> comments;

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

    public void addComment(Comment comment) {
        if (comments == null) comments = new ArrayList<>();
        if (!comments.contains(comment)) {
            comments.add(comment);
        }
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