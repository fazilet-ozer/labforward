package com.labforward.service;

import com.labforward.entity.Item;
import com.labforward.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @CacheEvict(cacheNames = {"items", "itemsOfCategory"}, allEntries = true)
    @Override
    public Item createItem(Item item) {
        return itemRepository.createItem(item);
    }

    @CacheEvict(cacheNames = {"items", "itemsOfCategory"}, allEntries = true)
    @Override
    public Item updateItem(Item existing, Item toBeUpdated) {
        Item entity = existing;
        if (!existing.equals(toBeUpdated)) {
            entity = itemRepository.updateItem(existing, toBeUpdated);
        }
        return entity;
    }

    @Cacheable(value = "items", key = "#id")
    @Override
    public Optional<Item> getItem(String id) {
        return Optional.ofNullable(itemRepository.getItem(id));
    }
}
