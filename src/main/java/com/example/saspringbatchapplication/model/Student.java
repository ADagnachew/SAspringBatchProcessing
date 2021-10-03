package com.example.saspringbatchapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Double gpa;
    private LocalDate DOB;

    public Student(String firstName, String lastName, Double gpa, LocalDate DOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.DOB = DOB;
    }
}