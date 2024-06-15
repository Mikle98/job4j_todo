package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePriorityRepository implements PriorityRepository {
    private CrudRepository crudRepository;

    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.queryReturnOptional("FROM Priority WHERE id = :id",
                Priority.class,
                Map.of("id", id));
    }

    @Override
    public List<Priority> findAll() {
        return crudRepository.queryReturnList("FROM Priority", Priority.class);
    }
}
