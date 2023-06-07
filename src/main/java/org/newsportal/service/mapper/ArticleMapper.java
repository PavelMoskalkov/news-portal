package org.newsportal.service.mapper;

import org.newsportal.database.entity.Article;
import org.newsportal.service.model.ArticleDto;

import java.util.List;

public interface ArticleMapper {
    ArticleDto toDto(Article source);
    Article toEntity(ArticleDto source);
    List<ArticleDto> toDto(List<Article> source);
    List<Article> toEntity(List<ArticleDto> source);

}
