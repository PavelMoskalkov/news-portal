package org.newsportal.database.dao;

import org.newsportal.database.dao.entity.Article;

import java.util.List;

public interface ArticleDao {
    List<Article> findAll();
    Article findById(Long id);
    void create(Article article);
    Article updateById(Article article, Long id);
    void deleteById(Long id);
}
