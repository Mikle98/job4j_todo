package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleCategoryRepository implements CategoryRepository {
    private CrudRepository crudRepository;

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.queryReturnOptional("FROM Category WHERE id = :id",
                Category.class,
                Map.of("id", id));
    }

    @Override
    public List<Category> findAll() {
        return crudRepository.queryReturnList("FROM Category", Category.class);
    }
}
