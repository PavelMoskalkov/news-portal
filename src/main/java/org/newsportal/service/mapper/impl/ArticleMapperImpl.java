package org.newsportal.service.mapper.impl;

import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ArticleMapperImpl implements ArticleMapper {
    @Override
    public ArticleDto toDto(Article source) {
        if (source == null) {
            return null;
        }

        UserDto userDto = null;

        if (source.getUser() != null) {
            userDto = new UserDto();
            userDto.setId(source.getUser().getId());
            userDto.setUsername(source.getUser().getUsername());
        }

        final ArticleDto articleDto = new ArticleDto();
        articleDto.setId(source.getId());
        articleDto.setTitle(source.getTitle());
        articleDto.setContent(source.getContent());
        articleDto.setUserDto(userDto);

        return articleDto;
    }

    @Override
    public Article toEntity(ArticleDto source) {
        if (source == null) {
            return null;
        }

        User user = null;

        if (source.getUserDto() != null) {
            user = new User();
            user.setId(source.getUserDto().getId());
            user.setUsername(source.getUserDto().getUsername());
        }

        final Article article = new Article();
        article.setId(source.getId());
        article.setTitle(source.getTitle());
        article.setContent(source.getContent());
        article.setUser(user);

        return article;
    }

    @Override
    public List<ArticleDto> toDto(List<Article> source) {
        return source.stream().map(this::toDto).filter(Objects::nonNull).toList();
    }

//    @Override
//    public List<ArticleDto> toDto(List<Article> source) {
//        List<ArticleDto> articles = new ArrayList<>();
//        for (Article entity : source) {
//            articles.add(toDto(entity));
//        }
//        return articles;
//    }

    @Override
    public List<Article> toEntity(List<ArticleDto> source) {
        return source.stream().map(this::toEntity).filter(Objects::nonNull).toList();
    }
}
