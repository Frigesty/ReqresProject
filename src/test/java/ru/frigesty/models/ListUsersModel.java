package ru.frigesty.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersModel {

    private int page;

    @JsonProperty("per_page")
    private int perPage;

    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<UserData> data;
    private Support support;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private int id;
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String avatar;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Support {
        private String url;
        private String text;
    }
}