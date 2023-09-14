package org.newsportal.service.mapper.impl;

import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User source) {
        if (source == null) {
            return null;
        }
        final UserDto userDto = new UserDto();
        userDto.setUsername(source.getUsername());
        return userDto;
    }

    @Override
    public User toEntity(UserDto source) {
        if (source == null) {
            return null;
        }
        final User user = new User();
        user.setUsername(source.getUsername());

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
