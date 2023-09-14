package org.newsportal.database.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.newsportal.database.entity.User;
import org.newsportal.database.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;
    @Override
    public Optional<List<User>> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery("from User", User.class).getResultList());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    @Override
    public Optional<Long> create(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.save(user);
        }
        return Optional.ofNullable(user.getId());
    }

    @Override
    public Optional<User> updateById(Long id, User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User updateUser = session.get(User.class, id);
            if (updateUser !=null) {
                updateUser.setUsername(user.getUsername());
                updateUser.setArticles(user.getArticles());
            }
            session.update(updateUser);
            session.getTransaction().commit();

            return Optional.ofNullable(updateUser);
        }
    }

    @Override
    public void deleteById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        UserRepository userRepository = new UserRepositoryImpl(configuration.buildSessionFactory());

        System.out.println(userRepository.findAll().get());

        System.out.println(userRepository.findById(1L));
    }
}
