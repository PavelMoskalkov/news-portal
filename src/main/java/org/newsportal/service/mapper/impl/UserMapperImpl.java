package org.newsportal.service.mapper.impl;

import org.newsportal.database.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.UserDto;

import java.util.List;
import java.util.Objects;

public class UserMapperImpl implements UserMapper {
    private final ArticleMapper articleMapper;

    public UserMapperImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public UserDto toDto(User source) {
        if (source == null) {
            return null;
        }
        final UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setUsername(source.getUsername());
        userDto.setArticles(articleMapper.toDto(source.getArticles()));
        return userDto;
    }

    @Override
    public User toEntity(UserDto source) {
        if (source == null) {
            return null;
        }
        final User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setArticles(articleMapper.toEntity(source.getArticles()));

        return user;
    }

    @Override
    public List<UserDto> toDto(List<User> source) {
        return source.stream().map(this::toDto).filter(Objects::nonNull).toList();
    }

    @Override
    public List<User> toEntity(List<UserDto> source) {
        return source.stream().map(this::toEntity).filter(Objects::nonNull).toList();
    }
}
