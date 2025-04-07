package ru.frigesty.models;

import lombok.Data;

@Data
public class UpdateUserModel {
    private String name, job, updatedAt;
}