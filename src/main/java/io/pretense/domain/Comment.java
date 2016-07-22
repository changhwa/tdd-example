package io.pretense.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.pretense.interfaces.api.support.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String body;
    private Date updatedAt;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment(Article article, CommentDto commentDto) {
        this.id = commentDto.getId();
        this.body = commentDto.getBody();
        this.article = article;
    }

    @PrePersist
    public void prePersistUpdateAt() {
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdateUpdateAt() {
        this.updatedAt = new Date();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
