package com.labforward.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CategoryRequest {

    @NotBlank
    @JsonProperty("attributeDefinition")
    private String attributeDefinition;

    public String getAttributeDefinition() {
        return attributeDefinition;
    }

    public CategoryRequest setAttributeDefinition(String attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryRequest that = (CategoryRequest) o;
        return Objects.equals(attributeDefinition, that.attributeDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeDefinition);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("attributeDefinition", attributeDefinition)
                .toString();
    }
}
