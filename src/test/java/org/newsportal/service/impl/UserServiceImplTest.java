package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    static User userEntity;
    static List<User> userEntityList;
    static UserDto userDto;
    static List<UserDto> userDtoList;

    @BeforeAll
    public static void init() {
        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setUsername("username");

        userEntityList = Collections.singletonList(userEntity);


        userDto = new UserDto();
        userDto.setUsername("username");

        userDtoList = Collections.singletonList(userDto);



    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(Optional.of(userEntityList));
        when(userMapper.toDto(userEntityList)).thenReturn(userDtoList);

        List<UserDto> result = userService.getAll();

        assertEquals(result, userDtoList);

        verify(userRepository).findAll();
        verify(userMapper).toDto(userEntityList);

    }

    @Test
    void getById() {
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.getById(userEntity.getId());

        assertEquals(result, userDto);

        verify(userRepository).findById(userEntity.getId());
        verify(userMapper).toDto(userEntity);
    }

    @Test
    void add() {
        when(userRepository.create(userEntity)).thenReturn(Optional.of(userEntity.getId()));
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);

        Long result = userService.add(userDto);

        assertEquals(result, userEntity.getId());

        verify(userRepository).create(userEntity);
        verify(userMapper).toEntity(userDto);


    }

    @Test
    void changeById() {
        when(userRepository.updateById(userEntity.getId(), userEntity)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);

        UserDto expectedDto = userDto;

        UserDto result = userService.changeById(userEntity.getId(), userDto);

        assertEquals(result, expectedDto);

        verify(userRepository).updateById(userEntity.getId(), userEntity);
        verify(userMapper).toDto(userEntity);
        verify(userMapper).toEntity(userDto);
     }

    @Test
    void removeById() {
        Long userId = userEntity.getId();

        userService.removeById(userId);

        verify(userRepository).deleteById(userId);
    }
}