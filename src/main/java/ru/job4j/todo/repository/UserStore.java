package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserStore {
    public User create(User user);

    public Optional<User> findById(int id);

    public Optional<User> findByLogin(String login);

    public Optional<User> findByLoginAndPassword(String login, String password);
}
