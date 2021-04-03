package com.labforward.controller;

import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.ItemResponse;
import com.labforward.entity.Item;
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

import java.util.Optional;
import java.util.UUID;

import static com.labforward.controller.utils.JsonConverter.parse;
import static com.labforward.controller.utils.JsonConverter.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemRestControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemRestController itemRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemRestController)
                .setControllerAdvice(new RestApiControllerAdvice())
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void updateItemWhenItemDoesNotExist() throws Exception {
        final String id = UUID.randomUUID().toString();
        when(itemService.getItem(id)).thenReturn(Optional.empty());

        final ItemRequest request = new ItemRequest()
                .setDescription(RandomStringUtils.randomAlphabetic(10))
                .setTitle(RandomStringUtils.randomAlphabetic(5));

        final String result = mockMvc.perform(patch("/items/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertEquals("Item with id [ " + id + " ] does not exist.", result);
        verify(itemService).getItem(id);
    }

    @Test
    void updateItem() throws Exception {
        final String id = UUID.randomUUID().toString();
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final Item existingItem = new Item()
                .setId(id)
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDescription(RandomStringUtils.randomAlphabetic(15))
                .setAttributeDefinition(attributeDefinition);
        when(itemService.getItem(id)).thenReturn(Optional.of(existingItem));

        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final Item item = new Item()
                .setTitle(title)
                .setDescription(description)
                .setAttributeDefinition(attributeDefinition);
        when(itemService.updateItem(existingItem, item)).thenReturn(new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description)
                .setId(id));
        final ItemRequest request = new ItemRequest()
                .setTitle(title)
                .setDescription(description);

        final String result = mockMvc.perform(patch("/items/" + id)
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
        verify(itemService).getItem(id);
        verify(itemService).updateItem(existingItem, item);
    }
}