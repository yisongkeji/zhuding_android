package com.foreseers.chat.bean;

public class DailyQuestionBean {

    private String question;
    private boolean isSwipe=false;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isSwipe() {
        return isSwipe;
    }

    public void setSwipe(boolean swipe) {
        isSwipe = swipe;
    }
}
