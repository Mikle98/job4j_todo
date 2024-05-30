package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskRepository {
    Task create(Task task);

    void update(int id, Task task);

    void delete(int id);

    List<Task> findAll();

    Task findById(int id);

    List<Task> findDoneTrue();

    List<Task> findDoneFalse();
}
