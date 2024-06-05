package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private UserStore userStore;

    public SimpleUserService(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public User create(User user) {
        return userStore.create(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userStore.findByLogin(login);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
