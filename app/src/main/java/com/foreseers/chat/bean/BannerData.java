package com.foreseers.chat.bean;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/28
 */
public class BannerData {

    private int url;
    private String text1;
    private String text2;
    private boolean isMovie;

    public BannerData(int url, String text1,String text2, boolean isMovie) {
        this.url = url;
        this.text1 = text1;
        this.text2 = text2;
        this.isMovie = isMovie;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getUrl() {
        return url;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText2() {

        return text2;
    }

    public String getText1() {

        return text1;
    }
}
