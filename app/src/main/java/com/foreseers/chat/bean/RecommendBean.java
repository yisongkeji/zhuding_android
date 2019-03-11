package com.foreseers.chat.bean;

import java.util.List;

public class RecommendBean {

    /**
     * status : success
     * data : [{"id":62,"facebook":"46797950360650","username":"zheng3","sex":"F",
     * "head":"http://192.168.1.73:8080/88/1550541329742.jpg","reservedint":17,"ziwei":"天相",
     * "userscore":74,"distance":1,"numuser":0,"age":17,"lookhead":0,"desc":"郎才女貌"},{"id":66,
     * "facebook":"46797950360653","username":"王五","sex":"F",
     * "head":"http://192.168.1.73:8080/66/1551670516646.jpg","reservedint":17,"ziwei":"天相",
     * "userscore":74,"distance":1,"numuser":0,"age":17,"lookhead":0,"desc":"郎才女貌"},{"id":86,
     * "facebook":"468979503606542","username":"郑皓2","sex":"M",
     * "head":"http://192.168.1.73:8080/88/1550541329742.jpg","reservedint":25,"ziwei":"紫微,七殺",
     * "userscore":72,"distance":1,"numuser":0,"age":17,"lookhead":0,"desc":"郎才女貌"},{"id":87,
     * "facebook":"469979503606542","username":"郑皓先生","sex":"M",
     * "head":"http://192.168.1.73:8080/88/1550541329742.jpg","reservedint":30,"ziwei":"太陽",
     * "userscore":66,"distance":1,"numuser":1,"age":11,"lookhead":0,"desc":"步伐一致"}]
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
         * id : 62
         * facebook : 46797950360650
         * username : zheng3
         * sex : F
         * head : http://192.168.1.73:8080/88/1550541329742.jpg
         * reservedint : 17
         * ziwei : 天相
         * userscore : 74
         * distance : 1
         * numuser : 0

         * lookhead : 0
         * desc : 郎才女貌
         */

        private int id;
        private String facebook;
        private String username;
        private String sex;
        private String head;
        private int reservedint;
        private String ziwei;
        private int userscore;
        private int distance;
        private int numuser;
        private int lookhead;
        private String desc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getReservedint() {
            return reservedint;
        }

        public void setReservedint(int reservedint) {
            this.reservedint = reservedint;
        }

        public String getZiwei() {
            return ziwei;
        }

        public void setZiwei(String ziwei) {
            this.ziwei = ziwei;
        }

        public int getUserscore() {
            return userscore;
        }

        public void setUserscore(int userscore) {
            this.userscore = userscore;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getNumuser() {
            return numuser;
        }

        public void setNumuser(int numuser) {
            this.numuser = numuser;
        }

        public int getLookhead() {
            return lookhead;
        }

        public void setLookhead(int lookhead) {
            this.lookhead = lookhead;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
