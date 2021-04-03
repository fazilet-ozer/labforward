package com.labforward.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Category {

    private String attributeDefinition;
    private String id;

    public String getAttributeDefinition() {
        return attributeDefinition;
    }

    public Category setAttributeDefinition(String attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
        return this;
    }

    public String getId() {
        return id;
    }

    public Category setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(attributeDefinition, category.attributeDefinition) &&
                Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeDefinition, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("attributeDefinition", attributeDefinition)
                .append("id", id)
                .toString();
    }
}
