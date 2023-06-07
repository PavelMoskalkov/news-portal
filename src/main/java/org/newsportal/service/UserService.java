package org.newsportal.service;

import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long id);
    Long add(UserDto userDto);
    User changeById(Long id, UserDto userDto);
    void removeById(Long id);
}
