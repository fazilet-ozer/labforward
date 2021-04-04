package com.labforward.service;

import com.labforward.entity.Item;

import java.util.Optional;

public interface ItemService {

    Item createItem(Item item);

    Item updateItem(Item existing, Item toBeUpdated);

    Optional<Item> getItem(String id);

}
