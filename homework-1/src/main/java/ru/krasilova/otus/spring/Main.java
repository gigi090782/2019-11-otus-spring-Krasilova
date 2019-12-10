package ru.krasilova.otus.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.service.QuizService;

@EnableAspectJAutoProxy
@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class Main {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        QuizService service = context.getBean(QuizService.class);
        Quiz quiz = service.createQuiz();
        service.runQuiz(quiz);
        service.showResult(quiz);

    }
}
