package ru.job4j.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {
    private final ConvertorPriority convertorPriority;
    private final ConvertCategory convertCategory;
    private final ConvertCategorySingl convertCategorySingl;

    public WebMvcConfig(ConvertorPriority convertorPriority,
                        ConvertCategory convertCategory,
                        ConvertCategorySingl convertCategorySingl) {
        this.convertorPriority = convertorPriority;
        this.convertCategory = convertCategory;
        this.convertCategorySingl = convertCategorySingl;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(convertorPriority);
        registry.addConverter(convertCategory);
        registry.addConverter(convertCategorySingl);
        super.addFormatters(registry);
    }

    @Bean
    public ConvertorPriority stringToConvertorPriority() {
        return new ConvertorPriority();
    }

    @Bean
    public ConvertCategory stringToConvertCategory() {
        return new ConvertCategory();
    }

    @Bean
    public ConvertCategorySingl stringToConvertCategorySingl() {
        return new ConvertCategorySingl();
    }
}
