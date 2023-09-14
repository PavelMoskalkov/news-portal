package org.newsportal.controller;

import lombok.RequiredArgsConstructor;
import org.newsportal.database.entity.Article;
import org.newsportal.service.ArticleService;
import org.newsportal.service.model.ArticleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news-portal")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @PostMapping("/articles")
    public ResponseEntity<Long> createArticle(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.add(articleDto));
    }
    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable("id") Long id) {
        articleService.removeById(id);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDto request) {
        return ResponseEntity.ok(articleService.changeById(id, request));
    }




}
