package ru.job4j.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {
    private final ConvertorPriority convertorPriority;

    public WebMvcConfig(ConvertorPriority convertorPriority) {
        this.convertorPriority = convertorPriority;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(convertorPriority);
        super.addFormatters(registry);
    }

    @Bean
    public ConvertorPriority stringToCategoryConverter() {
        return new ConvertorPriority();
    }
}
