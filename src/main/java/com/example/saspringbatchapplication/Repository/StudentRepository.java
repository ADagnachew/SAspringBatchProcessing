package com.example.saspringbatchapplication.Repository;

import com.example.saspringbatchapplication.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
