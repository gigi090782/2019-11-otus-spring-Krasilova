package ru.krasilova.otus.spring.homework14.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.krasilova.otus.spring.homework14.config.AppProps;
import ru.krasilova.otus.spring.homework14.repositories.AuthorRepository;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.krasilova.otus.spring.homework14.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class ShellLibraryBatchCommands {

    private final AppProps appProps;
    private final Job importUserJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final AuthorRepository repository;
    Long executionId;


    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date", new Date());
        builder.addString(OUTPUT_FILE_NAME, appProps.getOutputFileName());
        JobExecution execution = jobLauncher.run(importUserJob,
                builder.toJobParameters());
        System.out.println(execution);
    }

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws JobParametersInvalidException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException, JobExecutionNotRunningException, JobParametersNotFoundException, JobExecutionAlreadyRunningException {

        if (executionId == null) {
            executionId = jobOperator.start(IMPORT_LIBRARY_JOB_NAME,
                    OUTPUT_FILE_NAME + "=" + appProps.getOutputFileName()
            );
        } else
        {
            executionId = jobOperator.startNextInstance(IMPORT_LIBRARY_JOB_NAME);

        }

        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_LIBRARY_JOB_NAME));
    }

}
