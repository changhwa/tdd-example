package io.pretense.interfaces.api.support;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CommentDto {

    private Long id;
    private String body;
    private Date updatedAt;
}
