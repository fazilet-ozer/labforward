package com.labforward.service;

import com.labforward.entity.Category;
import com.labforward.entity.Item;
import com.labforward.repository.CategoryRepository;
import com.labforward.repository.ItemCategoryRelationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ItemCategoryRelationRepository itemCategoryRelationRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, itemCategoryRelationRepository);
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository, itemCategoryRelationRepository);
    }

    @Test
    void createCategory() {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final Category request = new Category()
                .setAttributeDefinition(attributeDefinition);
        final Category entity = new Category()
                .setAttributeDefinition(attributeDefinition)
                .setId(UUID.randomUUID().toString());
        when(categoryRepository.createCategory(request)).thenReturn(entity);

        final Category actualResponse = categoryService.createCategory(request);
        assertEquals(entity, actualResponse);
        verify(categoryRepository).createCategory(request);
    }

    @Test
    void listItemsOfCategory() {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final String id1 = UUID.randomUUID().toString();
        final String id2 = UUID.randomUUID().toString();
        final List<Item> items = Arrays.asList(
                new Item().setAttributeDefinition(attributeDefinition).setId(id1),
                new Item().setAttributeDefinition(attributeDefinition).setId(id2));
        when(itemCategoryRelationRepository.listItems(attributeDefinition)).thenReturn(items);

        final List<Item> actualResponse = categoryService.listItemsOfCategory(attributeDefinition);
        assertEquals(items, actualResponse);
        verify(itemCategoryRelationRepository).listItems(attributeDefinition);
    }

    @Test
    void getCategory() {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final Category entity = new Category()
                .setAttributeDefinition(attributeDefinition)
                .setId(UUID.randomUUID().toString());
        when(categoryRepository.getCategory(attributeDefinition)).thenReturn(entity);

        final Optional<Category> category = categoryService.getCategory(attributeDefinition);
        assertEquals(Optional.ofNullable(entity), category);
        verify(categoryRepository).getCategory(attributeDefinition);
    }
}