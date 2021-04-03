package com.labforward.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse {

    @JsonProperty("attributeDefinition")
    private String attributeDefinition;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    public String getAttributeDefinition() {
        return attributeDefinition;
    }

    public ItemResponse setAttributeDefinition(String attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
        return this;
    }

    public String getId() {
        return id;
    }

    public ItemResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ItemResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemResponse that = (ItemResponse) o;
        return Objects.equals(attributeDefinition, that.attributeDefinition) &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
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
