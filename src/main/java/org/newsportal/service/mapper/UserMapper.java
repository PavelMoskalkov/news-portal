package org.newsportal.service.mapper;

import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toDto(User source);
    User toEntity(UserDto source);
    List<UserDto> toDto(List<User> source);
    List<User> toEntity(List<UserDto> source);

}
