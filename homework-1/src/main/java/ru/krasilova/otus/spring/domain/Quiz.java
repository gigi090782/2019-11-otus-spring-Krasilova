package ru.krasilova.otus.spring.domain;


import java.util.List;

public class Quiz {
    private Student student;
    private int correctAnswersCount;
    private int wrongAnswersCount;
    private List<Question> questions;

    public Quiz() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void increaseCorrectAnswersCount() {
        this.correctAnswersCount++;
    }

    public int getWrongAnswersCount() {
        return wrongAnswersCount;
    }

    public void increaseWrongAnswersCount() {
        this.wrongAnswersCount++;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}


