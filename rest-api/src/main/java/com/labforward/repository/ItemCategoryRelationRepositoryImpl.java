package com.labforward.repository;

import com.labforward.entity.Item;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemCategoryRelationRepositoryImpl implements ItemCategoryRelationRepository {

    private Map<String, List<Item>> itemCategoryRelationMap = new HashMap<>();

    @Override
    public void addItem(String attribute, Item item) {
        final List<Item> items = itemCategoryRelationMap.getOrDefault(attribute, new ArrayList<>());
        items.add(item);
        itemCategoryRelationMap.put(attribute, items);
    }

    @Override
    public void deleteItem(String attribute, Item item) {
        final List<Item> items = itemCategoryRelationMap.getOrDefault(attribute, new ArrayList<>());
        if (!CollectionUtils.isEmpty(items)) {
            items.remove(item);
        }
    }

    @Override
    public List<Item> listItems(String attribute) {
        return itemCategoryRelationMap.getOrDefault(attribute, new ArrayList<>());
    }
}
