package com.example.saspringbatchapplication.batchConfig;

import com.example.saspringbatchapplication.model.Student;
import com.example.saspringbatchapplication.payload.StudentInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.Month;

public class StudentProcessor implements ItemProcessor<StudentInput, Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentProcessor.class);

    @Override
    public Student process(final StudentInput studentInput) throws Exception {

        log.info("processing student data.....{}", studentInput);

        final String firstName = studentInput.getFirstName();
        final String lastName = studentInput.getLastName();
        final Double gpa = studentInput.getGpa();
        int year = LocalDate.now().getYear()-studentInput.getAge();
        final LocalDate DOB = LocalDate.of(year, Month.JANUARY,1);

        final Student student = new Student(firstName, lastName,gpa,DOB);

        log.info("Converting (" + studentInput + ") into (" + student + ")");

        return student;
    }

}