package com.labforward.controller;

import com.labforward.controller.dto.request.CategoryRequest;
import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.CategoryResponse;
import com.labforward.controller.dto.response.ItemResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.labforward.controller.utils.JsonConverter.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createCategoryAndItem() throws Exception {
        //create category
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final CategoryRequest categoryRequest = new CategoryRequest()
                .setAttributeDefinition(attributeDefinition);
        String categoryResponse = mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final CategoryResponse actualCategoryResponse = parse(categoryResponse, CategoryResponse.class);
        final CategoryResponse expectedCategoryResponse = new CategoryResponse()
                .setAttributeDefinition(attributeDefinition)
                .setId(actualCategoryResponse.getId());

        assertEquals(expectedCategoryResponse, actualCategoryResponse);

        //create item
        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final ItemRequest itemRequest = new ItemRequest()
                .setTitle(title)
                .setDescription(description);

        //create item to non-existing category
        final String nonExistingAttribute = RandomStringUtils.randomAlphabetic(10);
        String itemResponse = mockMvc.perform(post("/categories/" + nonExistingAttribute + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(itemRequest)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertEquals("No category found for attribute definition [ " + nonExistingAttribute + " ]", itemResponse);

        //create item to existing category
        itemResponse = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(itemRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ItemResponse actualItemResponse = parse(itemResponse, ItemResponse.class);
        ItemResponse expectedItemResponse = new ItemResponse()
                .setId(actualItemResponse.getId())
                .setTitle(title)
                .setDescription(description)
                .setAttributeDefinition(attributeDefinition);
        assertEquals(expectedItemResponse, actualItemResponse);
    }


    @Test
    public void getItemsOfCategory() throws Exception {
        //getting items of non-existing category
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final ItemRequest itemRequest = new ItemRequest()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDescription(RandomStringUtils.randomAlphabetic(15));
        String itemResponse = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(itemRequest)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertEquals("No category found for attribute definition [ " + attributeDefinition + " ]", itemResponse);

        //create category
        final CategoryRequest createCategoryRequest = new CategoryRequest()
                .setAttributeDefinition(attributeDefinition);
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createCategoryRequest)))
                .andExpect(status().isOk());

        //get items of the category
        final ItemRequest createItemRequest1 = new ItemRequest()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDescription(RandomStringUtils.randomAlphabetic(15));
        String response1 = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createItemRequest1)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        final ItemResponse createItemResponse1 = parse(response1, ItemResponse.class);

        final ItemRequest createItemRequest2 = new ItemRequest()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDescription(RandomStringUtils.randomAlphabetic(15));
        String response2 = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createItemRequest2)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        final ItemResponse createItemResponse2 = parse(response2, ItemResponse.class);

        String response = mockMvc.perform(get("/categories/" + attributeDefinition + "/items"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<ItemResponse> actualResponse = parseAsList(response, ItemResponse.class);
        final List<ItemResponse> expectedResponse = Arrays.asList(createItemResponse1, createItemResponse2);

        assertEquals(expectedResponse, actualResponse);
    }
}
