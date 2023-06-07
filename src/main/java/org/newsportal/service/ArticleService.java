package org.newsportal.service;

import org.newsportal.database.entity.Article;
import org.newsportal.service.model.ArticleDto;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getAll();
    ArticleDto getById(Long id);
    Long add(ArticleDto article);
    Article changeById(Long id, ArticleDto article);
    void removeById(Long id);
}
