package com.labforward.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Item {

    private String attributeDefinition;
    private String id;
    private String title;
    private String description;

    public String getAttributeDefinition() {
        return attributeDefinition;
    }

    public Item setAttributeDefinition(String attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
        return this;
    }

    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(attributeDefinition, item.attributeDefinition) &&
                Objects.equals(id, item.id) &&
                Objects.equals(title, item.title) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeDefinition, id, title, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("attributeDefinition", attributeDefinition)
                .append("id", id)
                .append("title", title)
                .append("description", description)
                .toString();
    }
}
