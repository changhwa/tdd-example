package io.pretense.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.pretense.interfaces.api.support.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Comment> childComments = new ArrayList<>();

    public Comment(Article article, CommentDto commentDto) {
        this.id = commentDto.getId();
        this.body = commentDto.getBody();
        this.article = article;
    }

    public void addChildComment(Comment childComment) {
        if (isNotExistChildComment(childComment)) {
            childComments.add(childComment);
            childComment.setParentComment(this);
        }
    }

    private void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    private boolean isNotExistChildComment(Comment childComment) {
        return childComment != null && !childComments.contains(childComment);
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
