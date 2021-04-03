package com.labforward.repository;

import com.labforward.entity.Category;

public interface CategoryRepository {

    Category createCategory(Category category);

    Category getCategory(String attribute);

}
