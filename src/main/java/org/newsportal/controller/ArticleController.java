package org.newsportal.controller;

import lombok.RequiredArgsConstructor;
import org.newsportal.service.ArticleService;
import org.newsportal.service.model.ArticleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/a")
    public String getAllAa() {
        return "ResponseEntity.ok(articleService.getAll())";
    }


}
