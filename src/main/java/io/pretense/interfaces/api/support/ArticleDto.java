package io.pretense.interfaces.api.support;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class ArticleDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String body;
}
