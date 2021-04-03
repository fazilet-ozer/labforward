package com.labforward.service;

import com.labforward.entity.Item;
import com.labforward.repository.ItemRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl(itemRepository);
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void createItem() {
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final Item toBeCreated = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description);

        final String id = UUID.randomUUID().toString();
        final Item created = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description)
                .setId(id);
        when(itemRepository.createItem(toBeCreated)).thenReturn(created);

        final Item actualResponse = itemService.createItem(toBeCreated);
        assertEquals(created, actualResponse);
        verify(itemRepository).createItem(toBeCreated);
    }

    @Test
    void updateItem() {
        final String id = UUID.randomUUID().toString();
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final String title = RandomStringUtils.randomAlphabetic(5);
        final String description = RandomStringUtils.randomAlphabetic(15);
        final Item updateRequest = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description);
        final Item updated = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(title)
                .setDescription(description)
                .setId(id);
        final Item existing = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setTitle(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(10))
                .setId(id);
        when(itemRepository.updateItem(existing, updateRequest)).thenReturn(updated);
        final Item actualResponse = itemService.updateItem(existing, updateRequest);

        assertEquals(updated, actualResponse);
        verify(itemRepository).updateItem(existing, updateRequest);
    }

    @Test
    void getItem() {
        final String id = UUID.randomUUID().toString();
        final String attributeDefinition = RandomStringUtils.randomAlphabetic(10);
        final Item item = new Item()
                .setAttributeDefinition(attributeDefinition)
                .setId(id);
        when(itemRepository.getItem(id)).thenReturn(item);

        final Optional<Item> actualResponse = itemService.getItem(id);
        assertEquals(Optional.ofNullable(item), actualResponse);
        verify(itemRepository).getItem(id);
    }
}