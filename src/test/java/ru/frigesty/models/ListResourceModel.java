package ru.frigesty.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResourceModel {
    private int page, total;
    private List<ColorData> data;
    private Support support;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total_pages")
    private int totalPages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ColorData {
        int id, year;
        String name, color;

        @JsonProperty("pantone_value")
        String pantoneValue;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Support {
        String url, text;
    }
}
