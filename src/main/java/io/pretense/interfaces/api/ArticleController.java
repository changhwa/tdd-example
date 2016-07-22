package io.pretense.interfaces.api;

import io.pretense.domain.Article;
import io.pretense.infrastructure.jpa.ArticleRepository;
import io.pretense.interfaces.api.support.ArticleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity save(@RequestBody @Valid ArticleDto articleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(articleRepository.save(new Article(articleDto)), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    private ResponseEntity list(Pageable pageable) {
        return new ResponseEntity<>(modelMapper.map(articleRepository.findAllByOrderByIdDesc(pageable), ArticleDto.ListDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    private ResponseEntity search(@PathVariable Long id) {
        return new ResponseEntity<>(articleRepository.findOne(id), HttpStatus.OK);
    }
}
