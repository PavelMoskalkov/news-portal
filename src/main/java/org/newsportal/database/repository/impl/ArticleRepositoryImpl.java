package org.newsportal.database.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.newsportal.database.entity.Article;
import org.newsportal.database.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<List<Article>> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery("from Article", Article.class).getResultList());
        }
    }

    @Override
    public Optional<Article> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
        return Optional.ofNullable(session.get(Article.class, id));
        }
    }

    @Override
    public Optional<Long> create(Article article) {
        try(Session session = sessionFactory.openSession()) {
            session.save(article);
        }
        return Optional.ofNullable(article.getId());
    }

    @Override
    public Optional<Article> updateById(Long id, Article article) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Article articleUpdate = session.get(Article.class, id);
            if (articleUpdate != null) {
                articleUpdate.setTitle(article.getTitle());
                articleUpdate.setContent(article.getContent());
                articleUpdate.setUser(article.getUser());
            }
            session.update(articleUpdate);
            session.getTransaction().commit();

            return Optional.ofNullable(articleUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Article article = session.get(Article.class, id);
            session.remove(article);
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        ArticleRepository articleRepository = new ArticleRepositoryImpl(configuration.buildSessionFactory());
        articleRepository.deleteById(5L);
    }
}
