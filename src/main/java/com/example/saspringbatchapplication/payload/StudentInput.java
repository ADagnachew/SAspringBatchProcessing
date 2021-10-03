package com.example.saspringbatchapplication.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInput {

    private String firstName;
    private String lastName;
    private Double gpa;
    private int age;
}