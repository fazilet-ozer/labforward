package com.labforward.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse {

    @JsonProperty("attributeDefinition")
    private String attributeDefinition;

    @JsonProperty("id")
    private String id;

    public String getAttributeDefinition() {
        return attributeDefinition;
    }

    public CategoryResponse setAttributeDefinition(String attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
        return this;
    }

    public String getId() {
        return id;
    }

    public CategoryResponse setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(attributeDefinition, that.attributeDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributeDefinition);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("attributeDefinition", attributeDefinition)
                .toString();
    }
}
