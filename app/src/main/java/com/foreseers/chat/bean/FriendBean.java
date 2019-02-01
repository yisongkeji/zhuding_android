package com.foreseers.chat.bean;

import java.util.List;

public class FriendBean {

    /**
     * status : success
     * data : [{"userid":66,"userhead":"http://192.168.1.73:8080/467979503606542.jpg",
     * "username":"王五"},{"userid":64,"userhead":"http://192.168.1.73:8080/467979503606542.jpg",
     * "username":"李四"},{"userid":63,"userhead":"http://192.168.1.73:8080/467979503606542.jpg",
     * "username":"张三"},{"userid":62,"userhead":"http://192.168.1.73:8080/467979503606542.jpg",
     * "username":"zheng3"}]
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
         * userid : 66
         * userhead : http://192.168.1.73:8080/467979503606542.jpg
         * username : 王五
         */

        private int userid;
        private String userhead;
        private String username;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUserhead() {
            return userhead;
        }

        public void setUserhead(String userhead) {
            this.userhead = userhead;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
