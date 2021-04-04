package com.labforward.repository;

import com.labforward.entity.Item;

public interface ItemRepository {

    Item createItem(Item item);

    Item updateItem(Item existing, Item toBeUpdated);

    Item getItem(String id);
}
