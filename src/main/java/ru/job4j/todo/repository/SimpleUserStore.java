package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleUserStore implements UserStore {

    private final CrudRepository crudRepository;

    @Override
    public Optional create(User user) {

        try {
            return Optional.of(crudRepository.insertReturnSerializable(user));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.queryReturnOptional("FROM User WHERE id = :id",
                User.class, Map.of("id", id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return crudRepository.queryReturnOptional("FROM User WHERE login = :login",
                User.class, Map.of("login", login));
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.queryReturnOptional("FROM User WHERE login = :login and password = :password",
                User.class, Map.of("login", login, "password", password));
    }
}
