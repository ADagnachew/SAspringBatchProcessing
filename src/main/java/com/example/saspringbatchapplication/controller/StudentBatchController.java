package com.example.saspringbatchapplication.controller;
import com.example.saspringbatchapplication.Repository.StudentRepository;
import com.example.saspringbatchapplication.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/load")
public class StudentBatchController {

    private JobLauncher jobLauncher;
    private Job job;


    private StudentRepository studentRepository;

    @Autowired
    public StudentBatchController(JobLauncher jobLauncher, Job job, StudentRepository studentRepository) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.studentRepository = studentRepository;
    }

    public List<Student> startBatch() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }

        return studentRepository.findAll();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }
}
