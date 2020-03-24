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
import ru.krasilova.otus.spring.homework14.models.AuthorForWrite;
import ru.krasilova.otus.spring.homework14.models.BookForWrite;
import ru.krasilova.otus.spring.homework14.models.CommentForWrite;
import ru.krasilova.otus.spring.homework14.models.GenreForWrite;
import ru.krasilova.otus.spring.homework14.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework14.repositories.BookRepository;
import ru.krasilova.otus.spring.homework14.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework14.repositories.GenreRepository;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ru.krasilova.otus.spring.homework14.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class ShellLibraryBatchCommands {

    private final Job importLibraryJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;



    @SneakyThrows
    @ShellMethod(value = "runjob", key = "rj")
    public void startMigrationJobWithJobLauncher() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.deleteAll();

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date", new Date());
        JobExecution execution = jobLauncher.run(importLibraryJob,
                builder.toJobParameters());
        System.out.println(execution);
        List<AuthorForWrite> lista = authorRepository.findAll();
        lista.forEach(a -> System.out.println(a.toString()));
        List<GenreForWrite> listg = genreRepository.findAll();
        listg.forEach(a -> System.out.println(a.toString()));
        List<BookForWrite> listb = bookRepository.findAll();
        listb.forEach(a -> System.out.println(a.toString()));
        List<CommentForWrite> listc = commentRepository.findAll();
        listc.forEach(a -> System.out.println(a.toString()));

    }



    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_LIBRARY_JOB_NAME));
    }

}
