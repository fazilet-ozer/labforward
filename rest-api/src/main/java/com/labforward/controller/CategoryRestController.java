package com.labforward.controller;

import com.labforward.controller.dto.DtoMapper;
import com.labforward.controller.dto.request.CategoryRequest;
import com.labforward.controller.dto.request.ItemRequest;
import com.labforward.controller.dto.response.CategoryResponse;
import com.labforward.controller.dto.response.ItemResponse;
import com.labforward.entity.Category;
import com.labforward.entity.Item;
import com.labforward.exception.EntityNotFoundException;
import com.labforward.service.CategoryService;
import com.labforward.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.labforward.controller.dto.DtoMapper.*;

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        final Category toBeCreated = convertToCategory(request);
        final Category createdEntity = categoryService.createCategory(toBeCreated);
        return convertToCategoryResponse(createdEntity);
    }

    @RequestMapping(value = "/{attribute}/items", method = RequestMethod.POST)
    public ItemResponse createItem(@PathVariable String attribute, @Valid @RequestBody ItemRequest request) {
        categoryService.getCategory(attribute)
                .orElseThrow(() -> new EntityNotFoundException("No category found for attribute definition [ " + attribute + " ]"));
        final Item toBeCreated = convertToItem(request, attribute);
        final Item createdEntity = itemService.createItem(toBeCreated);
        return convertToItemResponse(createdEntity);
    }


    @RequestMapping(value = "/{attribute}/items", method = RequestMethod.GET)
    public List<ItemResponse> getItemsOfCategory(@PathVariable String attribute) {
        categoryService.getCategory(attribute)
                .orElseThrow(() -> new EntityNotFoundException("No category found for attribute definition [ " + attribute + " ]"));
        return categoryService.listItemsOfCategory(attribute)
                .stream()
                .map(DtoMapper::convertToItemResponse)
                .collect(Collectors.toList());
    }
}
