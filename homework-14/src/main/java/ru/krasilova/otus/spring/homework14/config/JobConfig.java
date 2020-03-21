package ru.krasilova.otus.spring.homework14.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.krasilova.otus.spring.homework14.models.Author;
import ru.krasilova.otus.spring.homework14.models.Book;
import ru.krasilova.otus.spring.homework14.models.Comment;
import ru.krasilova.otus.spring.homework14.models.Genre;


import java.util.HashMap;


@SuppressWarnings("all")
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 10;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String IMPORT_LIBRARY_JOB_NAME = "importAuthorJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemProcessor<Author, Author> processorAuthor() {
        return author -> author;
    }
    @Bean
    public ItemProcessor<Genre, Genre> processorGenre() {
        return genre -> genre;
    }

    @Bean
    public ItemProcessor<Book, Book> processorBook() {
        return book -> book;
    }

    @Bean
    public ItemProcessor<Comment, Comment> processorComment() {
        return comment -> comment;
    }

    @StepScope
    @Bean
    public MongoItemReader<Author> readerAuthor(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .template(mongoTemplate)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }


    @StepScope
    @Bean
    public MongoItemReader<Genre> readerGenre(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .template(mongoTemplate)
                .targetType(Genre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> readerComment(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .template(mongoTemplate)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> readerBook(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .template(mongoTemplate)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }


    @StepScope
    @Bean
    public FlatFileItemWriter writer(@Value("#{jobParameters['" + OUTPUT_FILE_NAME + "']}") String outputFileName) {
        return new FlatFileItemWriterBuilder<>()
                .name("itemWriter")
                .resource(new FileSystemResource(outputFileName))
                .lineAggregator(new DelimitedLineAggregator<>())
                .append(true)
                .build();
    }

    @Bean
    public Job importUserJob(Step step1, Step step2, Step step3, Step step4) {
        return jobBuilderFactory.get(IMPORT_LIBRARY_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .next(step3)
                .next(step4)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter writer, ItemReader<Author> readerAuthor, ItemProcessor<Author, Author> itemAuthorProcessor) {
        return stepBuilderFactory.get("step1")
                .<Author,Author>chunk(CHUNK_SIZE)
                //  .reader(reader)
                .reader(readerAuthor)
                .processor(itemAuthorProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2(FlatFileItemWriter writer, ItemReader<Genre> readerGenre, ItemProcessor<Genre, Genre> itemGenreProcessor) {
        return stepBuilderFactory.get("step2")
                .<Genre,Genre>chunk(CHUNK_SIZE)
                .reader(readerGenre)
                .processor(itemGenreProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step3(FlatFileItemWriter writer, ItemReader<Book> readerBook, ItemProcessor<Book, Book> itemBookProcessor) {
        return stepBuilderFactory.get("step3")
                .<Book,Book>chunk(CHUNK_SIZE)
                .reader(readerBook)
                .processor(itemBookProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step4(FlatFileItemWriter writer, ItemReader<Comment> readerComment, ItemProcessor<Comment, Comment> itemCommentProcessor) {
        return stepBuilderFactory.get("step3")
                .<Comment,Comment>chunk(CHUNK_SIZE)
                .reader(readerComment)
                .processor(itemCommentProcessor)
                .writer(writer)
                .build();
    }

}
