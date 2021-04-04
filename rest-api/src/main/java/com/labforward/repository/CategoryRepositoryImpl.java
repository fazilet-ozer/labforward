package com.labforward.repository;

import com.labforward.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private Map<String, Category> categoryMap = new HashMap<>();

    @Override
    public Category createCategory(Category category) {
        Category entity = new Category()
                .setAttributeDefinition(category.getAttributeDefinition())
                .setId(UUID.randomUUID().toString());
        categoryMap.put(category.getAttributeDefinition(), entity);
        return entity;
    }

    @Override
    public Category getCategory(String attribute) {
        return categoryMap.get(attribute);
    }

}
