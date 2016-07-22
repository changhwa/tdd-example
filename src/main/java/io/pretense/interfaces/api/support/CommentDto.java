package io.pretense.interfaces.api.support;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Getter
@Setter
public class CommentDto {

    private Long id;

    @NotEmpty
    private String body;
    private Date updatedAt;
}
