package com.labforward.controller;

import com.labforward.controller.dto.request.CategoryRequest;
import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.CategoryResponse;
import com.labforward.controller.dto.response.ItemResponse;
import com.labforward.entity.Category;
import com.labforward.entity.Item;
import com.labforward.service.CategoryService;
import com.labforward.service.ItemService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.labforward.controller.utils.JsonConverter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryRestControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private CategoryRestController categoryRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController)
                .setControllerAdvice(new RestApiControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryService, itemService);
    }

    @Test
    void createCategory() throws Exception {
        final String id = UUID.randomUUID().toString();
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);

        final Category category = new Category()
                .setAttributeDefinition(attributeDefinition);
        when(categoryService.createCategory(category)).thenReturn(new Category()
                .setAttributeDefinition(attributeDefinition)
                .setId(id));

        final CategoryRequest request = new CategoryRequest()
                .setAttributeDefinition(attributeDefinition);
        final String result = mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final CategoryResponse actualResponse = parse(result, CategoryResponse.class);
        final CategoryResponse expectedResponse = new CategoryResponse()
                .setAttributeDefinition(attributeDefinition)
                .setId(id);

        assertEquals(expectedResponse, actualResponse);
        verify(categoryService).createCategory(category);
    }

    @Test
    void createItem() throws Exception {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final ItemRequest request = new ItemRequest()
                .setTitle(title)
                .setDescription(description);

        final String id = UUID.randomUUID().toString();
        final Item item = new Item()
                .setTitle(title)
                .setDescription(description)
                .setAttributeDefinition(attributeDefinition);
        when(categoryService.getCategory(attributeDefinition))
                .thenReturn(Optional.of(new Category().setAttributeDefinition(attributeDefinition)));
        when(itemService.createItem(item)).thenReturn(new Item()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setAttributeDefinition(attributeDefinition));

        final String result = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final ItemResponse actualResponse = parse(result, ItemResponse.class);
        final ItemResponse expectedResponse = new ItemResponse()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description)
                .setId(id);

        assertEquals(expectedResponse, actualResponse);
        verify(itemService).createItem(item);
        verify(categoryService).getCategory(attributeDefinition);
    }

    @Test
    void createItemWhenCategoryDoesNotExist() throws Exception {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        when(categoryService.getCategory(attributeDefinition)).thenReturn(Optional.empty());

        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final ItemRequest request = new ItemRequest()
                .setTitle(title)
                .setDescription(description);
        final String result = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertEquals("No category found for attribute definition [ " + attributeDefinition + " ]", result);
        verify(categoryService).getCategory(attributeDefinition);
    }

    @Test
    void getItemsOfCategory() throws Exception {
        final String attribute = RandomStringUtils.randomAlphabetic(10);
        when(categoryService.getCategory(attribute)).thenReturn(Optional.of(new Category().setAttributeDefinition(attribute)));
        final String id1 = UUID.randomUUID().toString();
        final String id2 = UUID.randomUUID().toString();
        final List<Item> items = Arrays.asList(
                new Item().setAttributeDefinition(attribute).setId(id1),
                new Item().setAttributeDefinition(attribute).setId(id2));
        when(categoryService.listItemsOfCategory(attribute)).thenReturn(items);

        final String result = mockMvc.perform(get("/categories/" + attribute + "/items"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<ItemResponse> actualResponse = parseAsList(result, ItemResponse.class);
        final List<ItemResponse> expectedResponse = Arrays.asList(
                new ItemResponse().setAttributeDefinition(attribute).setId(id1),
                new ItemResponse().setAttributeDefinition(attribute).setId(id2));

        assertEquals(expectedResponse, actualResponse);
        verify(categoryService).getCategory(attribute);
        verify(categoryService).listItemsOfCategory(attribute);
    }

    @Test
    void getItemsOfCategoryWhenCategoryNotFound() throws Exception {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        when(categoryService.getCategory(attributeDefinition)).thenReturn(Optional.empty());

        final String result = mockMvc.perform(get("/categories/" + attributeDefinition + "/items"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertEquals("No category found for attribute definition [ " + attributeDefinition + " ]", result);
        verify(categoryService).getCategory(attributeDefinition);
    }
}