package io.pretense.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
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

    Comment(String body) {
        this.body = body;
    }

    Comment(Long id, String body) {
        this.id = id;
        this.body = body;
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
