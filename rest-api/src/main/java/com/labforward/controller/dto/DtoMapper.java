package com.labforward.controller.dto;

import com.labforward.controller.dto.request.CategoryRequest;
import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.CategoryResponse;
import com.labforward.controller.dto.response.ItemResponse;
import com.labforward.entity.Category;
import com.labforward.entity.Item;

public class DtoMapper {

    public static Category convertToCategory(CategoryRequest request) {
        return new Category()
                .setAttributeDefinition(request.getAttributeDefinition());
    }

    public static CategoryResponse convertToCategoryResponse(Category category) {
        return new CategoryResponse()
                .setAttributeDefinition(category.getAttributeDefinition())
                .setId(category.getId());
    }

    public static Item convertToItem(ItemRequest request, String attributeDefinition) {
        return new Item()
                .setDescription(request.getDescription())
                .setTitle(request.getTitle())
                .setAttributeDefinition(attributeDefinition);
    }

    public static ItemResponse convertToItemResponse(Item item) {
        return new ItemResponse()
                .setAttributeDefinition(item.getAttributeDefinition())
                .setId(item.getId())
                .setDescription(item.getDescription())
                .setTitle(item.getTitle());
    }
}
