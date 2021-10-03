package com.example.saspringbatchapplication.batchConfig;

import com.example.saspringbatchapplication.Repository.StudentRepository;
import com.example.saspringbatchapplication.model.Student;
import com.example.saspringbatchapplication.payload.StudentInput;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class StudentBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private StudentRepository studentRepository;

    @Bean
    public ItemReader<StudentInput> reader() {
        return new FlatFileItemReaderBuilder<StudentInput>()
                .name("StudentItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("students-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName","gpa","age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<StudentInput>() {{
                    setTargetType(StudentInput.class);
                }})
                .build();
    }
    @Bean
    public StudentProcessor processor() {
        return new StudentProcessor();
    }


    @Bean
    public RepositoryItemWriter<Student> writer() {
        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(studentRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(ItemReader<StudentInput> itemReader,
                      ItemWriter<Student> itemWriter,
                      StudentProcessor studentProcessor) throws Exception {

        return this.stepBuilderFactory
                .get("step1")
                .<StudentInput, Student>chunk(10)
                .reader(itemReader)
                .processor(studentProcessor)
                .writer(itemWriter)
                .build();

    }
    @Bean
    public Job importStudentJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importedStudentJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }

}
