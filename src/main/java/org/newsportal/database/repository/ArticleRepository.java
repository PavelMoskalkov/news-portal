package org.newsportal.database.repository;

import org.newsportal.database.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<List<Article>> findAll();
    Optional<Article> findById(Long id);
    Optional<Long> create(Article article);
    Optional<Article> updateById(Long id, Article article);
    void deleteById(Long id);
}
