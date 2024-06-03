package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleTaskService implements TaskService {

    private  final TaskRepository taskRepository;

    public SimpleTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public boolean update(int id, Task task) {
        return taskRepository.update(id, task);
    }

    @Override
    public boolean complete(int id) {
        return taskRepository.complete(id);
    }

    @Override
    public boolean delete(int id) {
         return taskRepository.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findDoneTrue() {
        return taskRepository.findDoneTrue();
    }

    @Override
    public List<Task> findDoneFalse() {
        return taskRepository.findDoneFalse();
    }
}
