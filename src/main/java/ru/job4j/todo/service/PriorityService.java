package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

public interface PriorityService {
    public Optional<Priority> findById(int id);

    public List<Priority> findAll();
}
