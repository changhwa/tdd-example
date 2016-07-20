package io.pretense.interfaces.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pretense.domain.Article;
import io.pretense.infrastructure.jpa.ArticleRepository;
import io.pretense.interfaces.api.support.ArticleDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = {"","/"}, method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity save(@RequestBody @Valid ArticleDto articleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(articleRepository.save(new Article(articleDto)), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    private ResponseEntity list(Pageable pageable) {
        return new ResponseEntity<>(articleRepository.findAll(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    private ResponseEntity search(@PathVariable Long id) {
        return new ResponseEntity<>(articleRepository.findOne(id), HttpStatus.OK);
    }
}
