package org.newsportal.service;

import org.newsportal.service.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long id);
    Long add(UserDto userDto);
    UserDto changeById(Long id, UserDto userDto);
    void removeById(Long id);
}
