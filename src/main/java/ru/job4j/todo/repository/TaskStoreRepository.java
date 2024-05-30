package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TaskStoreRepository {
    @Autowired
    private final SessionFactory sf;

    public SessionFactory getSF() {
        return sf;
    }
}
