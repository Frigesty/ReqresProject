package ru.frigesty.models;

import lombok.Data;

@Data
public class CreateUserModel {
    private String name, job, id, createdAt;
}