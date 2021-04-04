package com.labforward.repository;

import com.labforward.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Autowired
    private ItemCategoryRelationRepository itemCategoryRelationRepository;

    private Map<String, Item> itemMap = new HashMap<>();

    @Override
    public Item createItem(Item item) {
        final String id = UUID.randomUUID().toString();
        final Item entity = item.setId(id);
        itemMap.put(id, entity);
        itemCategoryRelationRepository.addItem(item.getAttributeDefinition(), entity);
        return entity;
    }

    @Override
    public Item updateItem(Item existing, Item toBeUpdated) {
        Item updated = new Item()
                .setId(existing.getId())
                .setAttributeDefinition(toBeUpdated.getAttributeDefinition())
                .setDescription(toBeUpdated.getDescription())
                .setTitle(toBeUpdated.getTitle());
        itemMap.put(updated.getId(), updated);
        itemCategoryRelationRepository.deleteItem(existing.getAttributeDefinition(), existing);
        itemCategoryRelationRepository.addItem(toBeUpdated.getAttributeDefinition(), updated);
        return updated;
    }

    @Override
    public Item getItem(String id) {
        return itemMap.get(id);
    }
}
