package com.foreseers.chat.bean;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Video {

    /**
     * head : http://192.168.1.73:8080/88/1550541329742.jpg
     * ziwei : 天相
     * sex : F
     * userscore : 74
     * time : 2019-02-28
     * age : 21
     * DESC:"郎才女貌"
     * username : 王五
     */

    private String head;
    private String ziwei;
    private String sex;
    private int userscore;
    private String time;
    private int age;
    private String DESC;
    private String username;

//    public Video(String head, String ziwei,String sex,int userscore,String time,int age,,String username) {
//        this.head = head;
//        this.ziwei = ziwei;
//        this.sex = sex;
//        this.userscore = userscore;
//        this.time = time;
//        this.age = age;
//        this.username = username;
//    }


    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public String getDESC() {

        return DESC;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getZiwei() {
        return ziwei;
    }

    public void setZiwei(String ziwei) {
        this.ziwei = ziwei;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserscore() {
        return userscore;
    }

    public void setUserscore(int userscore) {
        this.userscore = userscore;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
