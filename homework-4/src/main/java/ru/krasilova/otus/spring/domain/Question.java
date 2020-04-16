package ru.krasilova.otus.spring.domain;

public class Question {
    private String textQuestion;
    private String textAnswer;

    public Question(String textQuestion, String textAnswer) {
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
    }
    public void setTextQuestion(String textQuestion ) {

        this.textQuestion =  textQuestion;
    }

    public void setTextAnswer(String textAnswer ) {
        this.textAnswer =  textAnswer;
    }
    public String getTextQuestion() {

        return textQuestion;
    }

    public String getTextAnswer() {

        return textAnswer;
    }
}


