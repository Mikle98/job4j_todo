package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(int id, Task task) {
        return crudRepository.queryReturnBoolean("""
                        UPDATE Task
                        SET name = :name, description = :description,
                        priority = :priority
                        WHERE id = :id
                        """,
                        Map.of("name", task.getName(),
                                "description", task.getDescription(),
                                "id", task.getId(),
                                "priority", task.getPriority(),
                                "categories", task.getCategories()));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.queryReturnBoolean("""
                        DELETE FROM Task
                        WHERE id = :id
                        """,
                Map.of("id", id));
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.queryReturnList(
                """
                SELECT DISTINCT
                f
                FROM Task f 
                JOIN FETCH f.priority
                JOIN FETCH f.categories
                """,
                Task.class);
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.queryReturnOptional(
                                            """
                                                  FROM Task f
                                                  JOIN FETCH f.priority
                                                  JOIN FETCH f.categories
                                                  where f.id = :id
                                                  """,
                                                    Task.class,
                                                    Map.of("id", id));
    }

    @Override
    public List<Task> findDoneTrue() {
        return crudRepository.queryReturnList("FROM Task WHERE done = true", Task.class);
    }

    @Override
    public List<Task> findDoneFalse() {
        return crudRepository.queryReturnList("FROM Task WHERE done = false", Task.class);
    }

    @Override
    public boolean complete(int id) {
        return crudRepository.queryReturnBoolean("UPDATE Task SET done = true WHERE id = :id",
                                                  Map.of("id", id));
    }
}
