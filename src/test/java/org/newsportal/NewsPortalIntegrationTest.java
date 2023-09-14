package org.newsportal;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.newsportal.database.entity.Article;
import org.newsportal.database.entity.User;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.impl.ArticleRepositoryImpl;
import org.newsportal.database.repository.impl.UserRepositoryImpl;
import org.newsportal.service.ArticleService;
import org.newsportal.service.UserService;
import org.newsportal.service.impl.ArticleServiceImpl;
import org.newsportal.service.impl.UserServiceImpl;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.mapper.impl.ArticleMapperImpl;
import org.newsportal.service.mapper.impl.UserMapperImpl;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class NewsPortalIntegrationTest {
    @Container
    private static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer<>("postgres");
    private static ArticleService articleService;
    private static ArticleRepository articleRepository;
    private static ArticleMapper articleMapper;
    private static UserService userService;
    private static UserRepository userRepository;
    private static UserMapper userMapper;
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static UserDto userDto;
    private static ArticleDto articleDto;
    private static List<UserDto> userDtoList;
    private static List<ArticleDto> articleDtoList;

    private static UserDto changeUserDto;
    private static ArticleDto changeArticleDto;
    private static List<UserDto> changeUserDtoList;
    private static List<ArticleDto> changeArticleDtoList;

    @BeforeAll
    public static void init() {
        CONTAINER.start();
        configuration = new Configuration();

        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", CONTAINER.getJdbcUrl());
        properties.put("hibernate.connection.username", CONTAINER.getUsername());
        properties.put("hibernate.connection.password", CONTAINER.getPassword());
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Article.class);
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();

        articleRepository = new ArticleRepositoryImpl(sessionFactory);
        articleMapper = new ArticleMapperImpl();
        articleService = new ArticleServiceImpl(articleMapper, articleRepository);

        userRepository = new UserRepositoryImpl(sessionFactory);
        userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userMapper, userRepository);

        userDto = new UserDto("test username", articleDtoList);
        articleDto = new ArticleDto("test title", "test content", userDto);
        userDtoList = Collections.singletonList(userDto);
        articleDtoList = Collections.singletonList(articleDto);

        changeUserDto = new UserDto("change username", articleDtoList);
        changeArticleDto = new ArticleDto("change title", "change content", userDto);
    }

    @Test
    public void newsPortalIntegrationTest() {
        Long userId = userService.add(userDto);
        UserDto userFromDB = userService.getById(userId);
        assertEquals(userFromDB, userDto);

        Long articleId = articleService.add(articleDto);
        ArticleDto articleFromDB = articleService.getById(articleId);
        assertEquals(articleFromDB.getTitle(), articleDto.getTitle());

        UserDto updateUserDto = userService.changeById(userId, changeUserDto);

        assertEquals(updateUserDto.getUsername(), changeUserDto.getUsername());


    }

    @AfterAll
    public static void shutDown() {
        CONTAINER.close();
    }
}
