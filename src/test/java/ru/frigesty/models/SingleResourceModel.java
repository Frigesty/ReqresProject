package ru.frigesty.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleResourceModel {
    private ResourceData data;
    private Support support;

    @Data
    public static class ResourceData {
        private int id, year;
        private String name, color;

        @JsonProperty("pantone_value")
        private String pantoneValue;
    }

    @Data
    public static class Support {
        private String url, text;
    }
}