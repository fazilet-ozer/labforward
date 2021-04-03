package com.labforward.controller;

import com.labforward.controller.dto.request.CategoryRequest;
import com.labforward.controller.dto.request.ItemRequest;
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

import java.util.UUID;

import static com.labforward.controller.utils.JsonConverter.parse;
import static com.labforward.controller.utils.JsonConverter.toJson;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndUpdateItem() throws Exception {
        //create item to non-existing category
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final ItemRequest createRequest = new ItemRequest()
                .setTitle(title)
                .setDescription(description);
        String response = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createRequest)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertEquals("No category found for attribute definition [ " + attributeDefinition + " ]", response);

        //create category
        final CategoryRequest request = new CategoryRequest()
                .setAttributeDefinition(attributeDefinition);
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isOk());

        //create item to existing category
        response = mockMvc.perform(post("/categories/" + attributeDefinition + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ItemResponse actualResponse = parse(response, ItemResponse.class);
        ItemResponse expectedResponse = new ItemResponse()
                .setId(actualResponse.getId())
                .setTitle(title)
                .setDescription(description)
                .setAttributeDefinition(attributeDefinition);
        assertEquals(expectedResponse, actualResponse);

        //update non-existing item
        final String titleUpdated = RandomStringUtils.randomAlphabetic(5);
        final String descriptionUpdated = RandomStringUtils.randomAlphabetic(15);
        final ItemRequest updateRequest = new ItemRequest()
                .setTitle(titleUpdated)
                .setDescription(descriptionUpdated);

        final String id = UUID.randomUUID().toString();
        response = mockMvc.perform(patch("/items/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateRequest)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Item with id [ " + id + " ] does not exist.", response);

        //update existing item
        response = mockMvc.perform(patch("/items/" + actualResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        actualResponse = parse(response, ItemResponse.class);
        expectedResponse = new ItemResponse()
                .setId(actualResponse.getId())
                .setTitle(titleUpdated)
                .setDescription(descriptionUpdated)
                .setAttributeDefinition(attributeDefinition);
        assertEquals(expectedResponse, actualResponse);

    }
}
