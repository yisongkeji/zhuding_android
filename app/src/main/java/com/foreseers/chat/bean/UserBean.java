package com.foreseers.chat.bean;

public class UserBean {

    /**
     * status : success
     * data : {"head":"http://192.168.1.73:8080/99/1553064588942.jpg","vip":0,"userid":99,"username":"hsjs"}
     */

    private String status;
    private DataBean data;

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean {
        /**
         * head : http://192.168.1.73:8080/99/1553064588942.jpg
         * vip : 0
         * userid : 99
         * username : hsjs
         */

        private String head;
        private int vip;
        private int userid;
        private String username;

        public String getHead() { return head;}

        public void setHead(String head) { this.head = head;}

        public int getVip() { return vip;}

        public void setVip(int vip) { this.vip = vip;}

        public int getUserid() { return userid;}

        public void setUserid(int userid) { this.userid = userid;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}
    }
}
