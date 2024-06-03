package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task create(Task task);

    boolean update(int id, Task task);

    boolean delete(int id);

    boolean complete(int id);

    List<Task> findAll();

    Optional<Task> findById(int id);

    List<Task> findDoneTrue();

    List<Task> findDoneFalse();
}
