package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private ArticleRepository articleRepository;

    static Article articleEntity;
    static User userEntity;
    static List<Article> articleEntityList;

    static ArticleDto articleDto;

    static UserDto userDto;

    static List<ArticleDto> articleDtoList;

    @BeforeAll
    public static void init() {
        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setUsername("username");

        articleEntity = new Article();
        articleEntity.setId(1L);
        articleEntity.setTitle("title");
        articleEntity.setContent("content");
        articleEntity.setUser(userEntity);

        articleEntityList = Collections.singletonList(articleEntity);


        userDto = new UserDto();

        userDto.setUsername("username");

        articleDto = new ArticleDto();
        articleDto.setTitle("title");
        articleDto.setContent("content");
        articleDto.setUserDto(userDto);

        articleDtoList = Collections.singletonList(articleDto);
    }

    @Test
    void getAll() {
        when(articleRepository.findAll()).thenReturn(Optional.of(articleEntityList));
        when(articleMapper.toDto(articleEntityList)).thenReturn(articleDtoList);

        List<ArticleDto> result = articleService.getAll();

        assertEquals(result, articleDtoList);
        verify(articleRepository).findAll();
        verify(articleMapper).toDto(articleEntityList);
    }

    @Test
    void getById() {
        when(articleRepository.findById(articleEntity.getId())).thenReturn(Optional.of(articleEntity));
        when(articleMapper.toDto(articleEntity)).thenReturn(articleDto);

        ArticleDto result = articleService.getById(articleEntity.getId());

        assertEquals(result, articleDto);
        verify(articleRepository).findById(articleEntity.getId());
        verify(articleMapper).toDto(articleEntity);
    }

    @Test
    void add() {
        when(articleRepository.create(articleEntity)).thenReturn(Optional.of(articleEntity.getId()));
        when(articleMapper.toEntity(articleDto)).thenReturn(articleEntity);

        Long result = articleService.add(articleDto);

        assertEquals(result, articleEntity.getId());

        verify(articleRepository).create(articleEntity);
        verify(articleMapper).toEntity(articleDto);


    }

    @Test
    void changeById() {
        when(articleRepository.updateById(articleEntity.getId(), articleEntity)).thenReturn(Optional.of(articleEntity));
        when(articleMapper.toDto(articleEntity)).thenReturn(articleDto);

        ArticleDto expectedDto = articleDto;
        when(articleMapper.toEntity(articleDto)).thenReturn(articleEntity);

        ArticleDto result = articleService.changeById(articleEntity.getId(), articleDto);

        assertEquals(expectedDto, result);

        verify(articleRepository).updateById(articleEntity.getId(), articleEntity);
        verify(articleMapper).toDto(articleEntity);
        verify(articleMapper).toEntity(articleDto);


    }

    @Test
    void removeById() {
        Long articleId = articleEntity.getId();

        articleService.removeById(articleId);

        verify(articleRepository).deleteById(articleId);
    }
}