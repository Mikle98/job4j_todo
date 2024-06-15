package ru.job4j.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;

@Component
public class ConvertCategorySingl implements Converter<String, List<Category>> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> convert(String source) {
        return List.of(categoryRepository.findById(Integer.parseInt(source)).get());
    }
}
