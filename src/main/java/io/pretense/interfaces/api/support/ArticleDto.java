package io.pretense.interfaces.api.support;

import io.pretense.domain.Article;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class ArticleDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String body;

    private Date updatedAt;

    @Getter @Setter
    public static class ListDto {
        private Long size;
        private Long totalPages;
        private Long number;
        private List<ArticleDto> content;
    }

}
