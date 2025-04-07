package ru.frigesty.models;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String email;
    private String password;
}
