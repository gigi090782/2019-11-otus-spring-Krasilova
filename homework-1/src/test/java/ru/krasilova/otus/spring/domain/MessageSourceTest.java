package ru.krasilova.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

public class MessageSourceTest {

    @DisplayName("корректно получает локализацию")
    @Test
    public void testReloadableResource() throws InterruptedException {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        assertEquals("Введите Ваше имя: ", ms.getMessage("question.firstname", null, Locale.getDefault()));

    }
}
