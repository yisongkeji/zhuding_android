package com.foreseers.chat.bean;

import java.util.List;

public class BlackBean {

    /**
     * status : success
     * data : [{"date":"2019-03-07","userhead":"http://192.168.1.73:8080/64/1550632145506.jpg","userid":63,"username":"张三111"},
     * {"date":"2019-03-07","userhead":"http://192.168.1.73:8080/64/1550632145506.jpg","userid":64,"username":"李四"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date : 2019-03-07
         * userhead : http://192.168.1.73:8080/64/1550632145506.jpg
         * userid : 63
         * username : 张三111
         */

        private String date;
        private String userhead;
        private int userid;
        private String username;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserhead() {
            return userhead;
        }

        public void setUserhead(String userhead) {
            this.userhead = userhead;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
