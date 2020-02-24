package ru.krasilova.otus.spring.homework11.config;


import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ApplicationConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.krasilova.otus.spring.homework11.changelogs";

    /*
        @Bean
        public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
            return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                    .build();
        }

        @Bean
        public Mongock mongock(MongoTemplate mongoTemplate) {
            return new SpringMongockBuilder(mongoTemplate, MyChangeLog.class.getPackage().getName())
                    .build();
        }

     */
    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }

}
