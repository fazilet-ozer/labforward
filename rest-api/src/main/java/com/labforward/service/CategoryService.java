package com.labforward.service;

import com.labforward.entity.Category;
import com.labforward.entity.Item;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category createCategory(Category category);

    List<Item> listItemsOfCategory(String attribute);

    Optional<Category> getCategory(String attribute);

}
