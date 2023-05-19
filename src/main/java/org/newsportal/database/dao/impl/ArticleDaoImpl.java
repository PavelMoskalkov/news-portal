package org.newsportal.database.dao.impl;

import org.newsportal.database.dao.ArticleDao;
import org.newsportal.database.dao.entity.Article;
import org.newsportal.database.dao.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {

    private final ConnectionPool connectionPool;

    public ArticleDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = null;
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM articles");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            articles = new ArrayList<>();
            while (resultSet.next()) {
                final Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getLong("user_id"));
                articles.add(article);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }

    @Override
    public Article findById(Long id) {
        Article article = null;
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement("select * from articles where id=?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getLong("id" ));
                article.setTitle(resultSet.getString("title" ));
                article.setContent(resultSet.getString("content" ));
                article.setUserId(resultSet.getLong("user_id" ));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }

    @Override
    public void create(Article article) {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into articles(title, content, user_id) values(?,?,?)");
                ) {
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setLong(3, article.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Article updateById(Article requestArticle, Long id) {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "update articles set title=?, content=?, user_Id=? where id=?");
        ) {

            preparedStatement.setString(1, requestArticle.getTitle());
            preparedStatement.setString(2, requestArticle.getContent());
            preparedStatement.setLong(3, requestArticle.getUserId());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findById(id);
    }

    @Override
    public void deleteById(Long id) {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "delete from articles where id=?");
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
