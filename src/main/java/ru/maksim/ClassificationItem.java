package ru.maksim;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassificationItem implements Serializable {
    private String code;
    private String name;
    private List<ClassificationItem> items;

    @JsonCreator
    public ClassificationItem(@JsonProperty("code") String code, @JsonProperty("name") String name, @JsonProperty("items") ArrayList<ClassificationItem> items) {
        this.code = code;
        this.name = name;
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassificationItem> getItems() {
        return items;
    }

    public void setItems(List<ClassificationItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int depth) {
        String indent = "  ".repeat(depth);
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("ClassificationItem{")
                .append("code='").append(code).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", items=");

        if (items != null && !items.isEmpty()) {
            sb.append("[\n");
            for (ClassificationItem item : items) {
                sb.append(item.toString(depth + 1)).append("\n");
            }
            sb.append(indent).append("]");
        } else {
            sb.append("null");
        }
        sb.append("}");
        return sb.toString();
    }

}