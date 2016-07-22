package io.pretense.interfaces.api;

import io.pretense.application.CommentService;
import io.pretense.interfaces.api.support.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/article/{articleId}/comment", method = RequestMethod.POST)
    public ResponseEntity saveComment(@PathVariable Long articleId,
                                      @RequestBody @Valid CommentDto commentDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(commentService.save(articleId, commentDto), HttpStatus.CREATED);
    }
}
