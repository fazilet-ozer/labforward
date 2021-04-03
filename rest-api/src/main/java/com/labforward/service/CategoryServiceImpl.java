package com.labforward.service;

import com.labforward.entity.Category;
import com.labforward.entity.Item;
import com.labforward.repository.CategoryRepository;
import com.labforward.repository.ItemCategoryRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ItemCategoryRelationRepository itemCategoryRelationRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ItemCategoryRelationRepository itemCategoryRelationRepository) {
        this.categoryRepository = categoryRepository;
        this.itemCategoryRelationRepository = itemCategoryRelationRepository;
    }

    @CacheEvict(cacheNames = "itemsOfCategory", allEntries = true)
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.createCategory(category);
    }

    @Cacheable(value = "itemsOfCategory", key = "#attribute")
    @Override
    public List<Item> listItemsOfCategory(String attribute) {
        return itemCategoryRelationRepository.listItems(attribute);
    }

    @Override
    public Optional<Category> getCategory(String attribute) {
        return Optional.ofNullable(categoryRepository.getCategory(attribute));
    }


}
