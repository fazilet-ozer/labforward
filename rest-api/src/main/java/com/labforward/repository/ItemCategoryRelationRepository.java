package com.labforward.repository;

import com.labforward.entity.Item;

import java.util.List;

public interface ItemCategoryRelationRepository {

    void addItem(String attribute, Item item);

    void deleteItem(String attribute, Item item);

    List<Item> listItems(String attribute);
}
