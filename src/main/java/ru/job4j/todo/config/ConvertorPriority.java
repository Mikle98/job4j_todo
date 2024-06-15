package ru.job4j.todo.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.SimplePriorityRepository;


@Component
public class ConvertorPriority implements Converter<String, Priority> {
    @Autowired
    private SimplePriorityRepository priorityRepository;

    @Override
    public Priority convert(String source) {
        return priorityRepository.findById(Integer.parseInt(source)).get();
    }
}
