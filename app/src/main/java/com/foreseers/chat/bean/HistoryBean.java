package com.foreseers.chat.bean;

import java.util.List;

public class HistoryBean {

    /**
     * status : success
     * data : [[{"head":"http://192.168.1.73:8080/88/1550541329742.jpg","ziwei":"天相","sex":"F","userscore":74,"time":"2019-02-28","age":21,
     * "username":"王五"}],[{"head":"http://192.168.1.73:8080/88/1550541329742.jpg","ziwei":"破軍","sex":"M","userscore":77,"time":"2019-02-27",
     * "age":17,"username":"郑皓"},{"head":"http://192.168.1.73:8080/64/1550632145506.jpg","ziwei":"武曲,七殺","sex":"F","userscore":68,
     * "time":"2019-02-27","age":37,"username":"李四"}]]
     */

    private String status;
    private List<List<Video>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<List<Video>> getData() {
        return data;
    }

    public void setData(List<List<Video>> data) {
        this.data = data;
    }


}
