package ru.krasilova.otus.spring.homework14.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProps {

    @Value("${spring.data.mongodb.database}")
    private String mongoDBName;

    @Value("${app.output-file:''}")
    private String outputFileName;

}
