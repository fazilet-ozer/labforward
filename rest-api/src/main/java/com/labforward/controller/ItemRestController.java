package com.labforward.controller;

import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.ItemResponse;
import com.labforward.entity.Item;
import com.labforward.exception.EntityNotFoundException;
import com.labforward.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.labforward.controller.dto.DtoMapper.convertToItem;
import static com.labforward.controller.dto.DtoMapper.convertToItemResponse;

@RestController
@RequestMapping("/items")
@Validated
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ItemResponse updateItem(@PathVariable String id, @Valid @RequestBody ItemRequest request) {
        final Item existingItem = itemService.getItem(id)
                .orElseThrow(() -> new EntityNotFoundException("Item with id [ " + id + " ] does not exist."));
        final Item toBeUpdated = convertToItem(request, existingItem.getAttributeDefinition());
        final Item updatedEntity = itemService.updateItem(existingItem, toBeUpdated);
        return convertToItemResponse(updatedEntity);
    }

}
