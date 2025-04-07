package ru.frigesty.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleUserModel {
    private UserData data;
    private Support support;

    @Data
    public static class UserData {
        private int id;
        private String email, avatar;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;
    }

    @Data
    public static class Support {
        private String url, text;
    }
}
