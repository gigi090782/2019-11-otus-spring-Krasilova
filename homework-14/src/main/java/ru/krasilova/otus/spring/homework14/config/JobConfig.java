package ru.krasilova.otus.spring.homework14.config;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.krasilova.otus.spring.homework14.models.*;
import ru.krasilova.otus.spring.homework14.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework14.repositories.BookRepository;
import ru.krasilova.otus.spring.homework14.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework14.repositories.GenreRepository;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("all")
@AllArgsConstructor
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 10;
    private final Logger logger = LoggerFactory.getLogger("Batch");


    public static final String IMPORT_LIBRARY_JOB_NAME = "importLibraryJob";

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public final DataSource dataSource;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;


    @StepScope
    @Bean
    RepositoryItemWriter<AuthorForWrite> writerAuthor() {

        RepositoryItemWriter<AuthorForWrite> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(authorRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;

    }

    @StepScope
    @Bean
    RepositoryItemWriter<GenreForWrite> writerGenre() {

        RepositoryItemWriter<GenreForWrite> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(genreRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;

    }


    @StepScope
    @Bean
    RepositoryItemWriter<BookForWrite> writerBook() {

        RepositoryItemWriter<BookForWrite> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(bookRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    @StepScope
    @Bean
    RepositoryItemWriter<CommentForWrite> writerComment() {

        RepositoryItemWriter<CommentForWrite> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(commentRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }


    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorForWrite> processorAuthor() {
        return author -> (new AuthorForWrite(author.getFirstName(), author.getSecondName(), author.getLastName(), author.getBirthDate()));
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreForWrite> processorGenre() {
        return genre -> (new GenreForWrite(genre.getName()));
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookForWrite> processorBook() {

        return book -> {
            GenreForWrite genre = genreRepository.findByName(book.getGenre().getName());
            List<AuthorForWrite> authors = authorRepository.findByLastName(book.getAuthor().getLastName());
            AuthorForWrite author = authors.get(0);
            BookForWrite bookNew = new BookForWrite(book.getName(), author.getId(), genre.getId());
            return bookNew;
        };
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, CommentForWrite> processorComment() {

        return comment -> {
            BookForWrite book = bookRepository.findByName(comment.getBook().getName());
            CommentForWrite commentNew = new CommentForWrite(comment.getText(), book.getId());
            return commentNew;
        };
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


    @Bean
    public Job importLibraryJob(Step stepAuthor, Step stepGenre, Step stepBook, Step stepComment) {
        return jobBuilderFactory.get(IMPORT_LIBRARY_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(stepAuthor)
                .next(stepGenre)
                .next(stepBook)
                .next(stepComment)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                        commentRepository.deleteAll();
                        bookRepository.deleteAll();
                        genreRepository.deleteAll();
                        authorRepository.deleteAll();

                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepAuthor
            (RepositoryItemWriter<AuthorForWrite> writerAuthor, ItemReader<Author> readerAuthor, ItemProcessor<Author, AuthorForWrite> itemAuthorProcessor) {
        return stepBuilderFactory.get("stepAuthor")
                .<Author, AuthorForWrite>chunk(CHUNK_SIZE)
                .reader(readerAuthor)
                .processor(itemAuthorProcessor)
                .writer(writerAuthor)
                .build();
    }

    @Bean
    public Step stepGenre(RepositoryItemWriter<GenreForWrite> writerGenre, ItemReader<Genre> readerGenre, ItemProcessor<Genre, GenreForWrite> itemGenreProcessor) {
        return stepBuilderFactory.get("step2")
                .<Genre, GenreForWrite>chunk(CHUNK_SIZE)
                .reader(readerGenre)
                .processor(itemGenreProcessor)
                .writer(writerGenre)
                .build();
    }

    @Bean
    public Step stepBook(RepositoryItemWriter<BookForWrite> writerBook, ItemReader<Book> readerBook, ItemProcessor<Book, BookForWrite> itemBookProcessor) {
        return stepBuilderFactory.get("stepBook")
                .<Book, BookForWrite>chunk(CHUNK_SIZE)
                .reader(readerBook)
                .processor(itemBookProcessor)
                .writer(writerBook)
                .build();
    }

    @Bean
    public Step stepComment(RepositoryItemWriter<CommentForWrite> writerComment, ItemReader<Comment> readerComment, ItemProcessor<Comment, CommentForWrite> itemCommentProcessor) {
        return stepBuilderFactory.get("stepComment")
                .<Comment, CommentForWrite>chunk(CHUNK_SIZE)
                .reader(readerComment)
                .processor(itemCommentProcessor)
                .writer(writerComment)
                .build();
    }


}
