package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
public class SimpleUserStore implements UserStore {

    private final SessionFactory sf;

    public SimpleUserStore(TaskStoreRepository taskStoreRepository) {
        this.sf = taskStoreRepository.getSF();
    }

    @Override
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery(
                    "FROM User WHERE id = :id")
                    .setParameter("id", id);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        } finally {
            session.close();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery(
                            "FROM User WHERE login = :login")
                    .setParameter("login", login);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        } finally {
            session.close();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery(
                            "FROM User WHERE login = :login and password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
